package org.SprintForge.modules.auth.service;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Random;

@Service
public class OtpServiceImpl implements OtpService {

    private final ConcurrentHashMap<String, String> otpStorage = new ConcurrentHashMap<>();
    private final Random random = new Random();

    @Override
    public String generateOtp(String key) {
        String otp = String.format("%06d", random.nextInt(1000000));
        otpStorage.put(key, otp);
        return otp;
    }

    @Override
    public boolean validateOtp(String key, String otp) {
        String storedOtp = otpStorage.get(key);
        return storedOtp != null && storedOtp.equals(otp);
    }

    @Override
    public void clearOtp(String key) {
        otpStorage.remove(key);
    }
}
