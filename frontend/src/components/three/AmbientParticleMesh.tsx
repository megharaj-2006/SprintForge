import React, { useRef, useMemo } from 'react';
import { Canvas, useFrame } from '@react-three/fiber';
import * as THREE from 'three';
import { useThemeStore } from '../../store/useThemeStore';

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

function ParticleField({ isDark }: { isDark: boolean }) {
  const count = 180;
  const meshRef = useRef<THREE.Points>(null!);
  const circleTexture = useMemo(() => createCircleTexture(), []);

  const [positions, colors] = useMemo(() => {
    const pos = new Float32Array(count * 3);
    const col = new Float32Array(count * 3);

    const primaryColor = isDark ? new THREE.Color('#FFFFFF') : new THREE.Color('#18181B');
    const secondaryColor = isDark ? new THREE.Color('#71717A') : new THREE.Color('#A1A1AA');

    for (let i = 0; i < count; i++) {
      pos[i * 3] = (Math.random() - 0.5) * 12;
      pos[i * 3 + 1] = (Math.random() - 0.5) * 12;
      pos[i * 3 + 2] = (Math.random() - 0.5) * 8;

      const mixed = secondaryColor.clone().lerp(primaryColor, Math.random() * 0.85);
      col[i * 3] = mixed.r;
      col[i * 3 + 1] = mixed.g;
      col[i * 3 + 2] = mixed.b;
    }
    return [pos, col];
  }, [isDark]);

  useFrame((state) => {
    if (meshRef.current) {
      meshRef.current.rotation.y = state.clock.getElapsedTime() * 0.04;
      meshRef.current.rotation.x = Math.sin(state.clock.getElapsedTime() * 0.02) * 0.1;
    }
  });

  return (
    <points ref={meshRef}>
      <bufferGeometry>
        <bufferAttribute
          attach="attributes-position"
          args={[positions, 3]}
        />
        <bufferAttribute
          attach="attributes-color"
          args={[colors, 3]}
        />
      </bufferGeometry>
      <pointsMaterial
        size={0.1}
        map={circleTexture}
        vertexColors
        transparent
        opacity={isDark ? 0.7 : 0.45}
        sizeAttenuation
        alphaTest={0.05}
        blending={isDark ? THREE.AdditiveBlending : THREE.NormalBlending}
      />
    </points>
  );
}

export function AmbientParticleMesh({ className }: { className?: string }) {
  const { isDark } = useThemeStore();

  return (
    <div className={className || 'absolute inset-0 pointer-events-none'}>
      <Canvas
        camera={{ position: [0, 0, 6], fov: 60 }}
        gl={{ alpha: true, antialias: true }}
        dpr={[1, 2]}
      >
        <ambientLight intensity={0.5} />
        <ParticleField isDark={isDark} />
      </Canvas>
    </div>
  );
}

export default AmbientParticleMesh;
