package com.marzhiievskyi.home_notes.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class EncryptProcessor {

    public String encryptPassword(String password) {
        return DigestUtils.md5DigestAsHex(password.getBytes());
    }

    public String generateAccessToken() {
        return UUID.randomUUID().toString().replace("-", "") + System.currentTimeMillis();
    }
}
