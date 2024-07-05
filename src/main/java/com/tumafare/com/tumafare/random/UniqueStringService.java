package com.tumafare.com.tumafare.random;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class UniqueStringService {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int LENGTH = 10;
    private static final Random random = new Random();

    private final UniqueStringRepository repository;

    public String generateUniqueSmsID() {
        while (true) {
            StringBuilder stringBuilder = new StringBuilder(LENGTH);
            for (int i = 0; i < LENGTH; i++) {
                stringBuilder.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
            }
            String randomString = stringBuilder.toString();
            if (!repository.existsByValue(randomString)) {
                UniqueString uniqueString = new UniqueString();
                uniqueString.setValue(randomString);
                return repository.save(uniqueString).getValue();
            }
        }
    }

}
