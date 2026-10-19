import React, { useRef, useMemo, useEffect } from 'react';
import { Canvas, useFrame, useThree } from '@react-three/fiber';
import * as THREE from 'three';
import { useThemeStore } from '../../store/useThemeStore';

interface TerrainProps {
  isDark: boolean;
}

// Generate a clean circular alpha sprite for intersection nodes (never square points)
function createCircleTexture(): THREE.Texture {
  const canvas = document.createElement('canvas');
  canvas.width = 64;
  canvas.height = 64;
  const ctx = canvas.getContext('2d')!;

  ctx.beginPath();
  ctx.arc(32, 32, 28, 0, Math.PI * 2);
  ctx.fillStyle = '#ffffff';
  ctx.fill();

  const texture = new THREE.CanvasTexture(canvas);
  texture.minFilter = THREE.LinearFilter;
  texture.magFilter = THREE.LinearFilter;
  return texture;
}

function WireframeTerrain({ isDark }: TerrainProps) {
  const meshRef = useRef<THREE.Mesh>(null!);
  const pointsRef = useRef<THREE.Points>(null!);
  const { camera, pointer } = useThree();

  // Grid resolution: 44 x 44 segments (1,936 vertices, optimal for 60fps+ CPU deformation)
  const [gridWidth, gridHeight, segmentsX, segmentsY] = [28, 28, 44, 44];

  const circleTexture = useMemo(() => createCircleTexture(), []);

  // Base positions buffer
  const baseGeometry = useMemo(() => {
    return new THREE.PlaneGeometry(gridWidth, gridHeight, segmentsX, segmentsY);
  }, [gridWidth, gridHeight, segmentsX, segmentsY]);

  // Original flat vertex coordinates
  const originalPositions = useMemo(() => {
    return (baseGeometry.attributes.position.array as Float32Array).slice();
  }, [baseGeometry]);

  // Dynamic colors and materials for dark vs light mode
  const { lineMaterial, pointsMaterial, horizonColor } = useMemo(() => {
    if (isDark) {
      return {
        lineMaterial: new THREE.MeshBasicMaterial({
          color: new THREE.Color('#27272A'),
          wireframe: true,
          transparent: true,
          opacity: 0.85,
        }),
        pointsMaterial: new THREE.PointsMaterial({
          color: new THREE.Color('#FFFFFF'),
          size: 0.12,
          map: circleTexture,
          transparent: true,
          opacity: 0.7,
          alphaTest: 0.05,
          blending: THREE.AdditiveBlending,
        }),
        horizonColor: new THREE.Color('#09090B'),
      };
    } else {
      return {
        lineMaterial: new THREE.MeshBasicMaterial({
          color: new THREE.Color('#A1A1AA'),
          wireframe: true,
          transparent: true,
          opacity: 0.65,
        }),
        pointsMaterial: new THREE.PointsMaterial({
          color: new THREE.Color('#18181B'),
          size: 0.12,
          map: circleTexture,
          transparent: true,
          opacity: 0.55,
          alphaTest: 0.05,
          blending: THREE.NormalBlending,
        }),
        horizonColor: new THREE.Color('#FFFFFF'),
      };
    }
  }, [isDark, circleTexture]);

  // Smooth mouse inertia tracking
  const mouseTarget = useRef({ x: 0, y: 0 });

  useFrame((state, delta) => {
    const time = state.clock.getElapsedTime();

    // Lerp mouse target for smooth inertia
    mouseTarget.current.x += (pointer.x * 6 - mouseTarget.current.x) * 0.08;
    mouseTarget.current.y += (pointer.y * 6 - mouseTarget.current.y) * 0.08;

    // Camera subtle parallax
    camera.position.x += (pointer.x * 0.9 - camera.position.x) * 0.05;
    camera.position.y += (3.2 + pointer.y * 0.4 - camera.position.y) * 0.05;
    camera.lookAt(0, -0.6, -3);

    const positions = baseGeometry.attributes.position.array as Float32Array;
    const count = positions.length / 3;

    for (let i = 0; i < count; i++) {
      const i3 = i * 3;
      const x = originalPositions[i3];
      const y = originalPositions[i3 + 1];

      // Sinusoidal wave ripple
      let z =
        Math.sin(x * 0.35 + time * 0.9) * 0.32 +
        Math.cos(y * 0.4 + time * 0.7) * 0.35 +
        Math.sin((x + y) * 0.25 + time * 0.5) * 0.2;

      // Distance to magnetic mouse cursor in plane space
      const dx = x - mouseTarget.current.x;
      const dy = y - (mouseTarget.current.y - 2);
      const distSq = dx * dx + dy * dy;

      // Smooth Gaussian cursor elevation
      if (distSq < 25) {
        const elevation = Math.exp(-distSq * 0.18) * 1.4;
        z += elevation;
      }

      positions[i3 + 2] = z;
    }

    baseGeometry.attributes.position.needsUpdate = true;

    // Apply same geometry to points layer
    if (pointsRef.current) {
      pointsRef.current.geometry.attributes.position.needsUpdate = true;
    }
  });

  return (
    <group rotation={[-Math.PI * 0.42, 0, 0]} position={[0, -1.2, -3]}>
      {/* Wireframe Mesh */}
      <mesh ref={meshRef} geometry={baseGeometry} material={lineMaterial} />

      {/* Intersection Node Points Layer */}
      <points ref={pointsRef} geometry={baseGeometry} material={pointsMaterial} />
    </group>
  );
}

function Scene({ isDark }: TerrainProps) {
  const { scene } = useThree();

  useEffect(() => {
    // Horizon fog to blend the back edges smoothly into the background
    scene.fog = new THREE.FogExp2(isDark ? '#09090B' : '#FFFFFF', 0.085);
  }, [scene, isDark]);

  return (
    <>
      <ambientLight intensity={isDark ? 0.8 : 1.2} />
      <directionalLight position={[0, 10, 5]} intensity={isDark ? 1.5 : 2.0} />
      <WireframeTerrain isDark={isDark} />
    </>
  );
}

interface TerrainWireframeCanvasProps {
  className?: string;
}

export function TerrainWireframeCanvas({ className }: TerrainWireframeCanvasProps) {
  const { isDark } = useThemeStore();

  return (
    <div
      className={
        className ||
        'pointer-events-none absolute inset-0 h-[850px] w-full overflow-hidden'
      }
    >
      {/* Specular Horizon Glow Beam */}
      <div
        className={`pointer-events-none absolute top-1/4 left-1/2 -translate-x-1/2 -translate-y-1/2 w-[700px] h-[300px] rounded-full blur-3xl transition-opacity duration-700 ${
          isDark
            ? 'bg-gradient-to-b from-white/10 via-white/5 to-transparent opacity-80'
            : 'bg-gradient-to-b from-black/5 via-black/2 to-transparent opacity-60'
        }`}
      />

      {/* Topographic 3D Canvas */}
      <Canvas
        camera={{ position: [0, 3.2, 7.5], fov: 55 }}
        gl={{ alpha: true, antialias: true, powerPreference: 'high-performance' }}
        dpr={[1, 2]}
      >
        <Scene isDark={isDark} />
      </Canvas>

      {/* Horizon & Bottom Vignette Mask to blend with page content */}
      <div
        className={`pointer-events-none absolute inset-0 bg-gradient-to-b ${
          isDark
            ? 'from-background/20 via-transparent to-background'
            : 'from-background/20 via-transparent to-background'
        }`}
      />
    </div>
  );
}

export default TerrainWireframeCanvas;
