package com.srmzhk.patientservice.util;

import com.srmzhk.patientservice.exception.InvalidNameFormatException;
import org.springframework.stereotype.Component;

@Component
public class NameValidator {

    public void isValidName(String name) {

        // check if name is not null
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidNameFormatException("Name should not be null!");
        }

        // remove spaces from right and left, then check if name consist of 3 words
        String[] words = name.trim().split("\\s+");

        if (words.length != 3) {
            throw new InvalidNameFormatException("Name should consists of 3 words!");
        }

        // check if name consists of only letters
        for (String word : words) {
            if (!word.matches("[A-Za-zА-Яа-яЁё]+")) {
                throw new InvalidNameFormatException("Name should contains only letters!");
            }
        }
    }
}
