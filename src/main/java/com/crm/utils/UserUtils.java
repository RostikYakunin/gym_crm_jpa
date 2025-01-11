package com.crm.utils;

import com.crm.models.users.User;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.RandomStringGenerator;

import java.util.function.Function;

@UtilityClass
@Slf4j
public class UserUtils {
    private static final String USERNAME_SEPARATOR = ".";

    public static String generateUniqueUsername(
            User user,
            Function<String, Boolean> usernameExistsChecker
    ) {
        log.info("Stated creating unique username... ");

        var baseUsername = user.getFirstName() + USERNAME_SEPARATOR + user.getLastName();
        var uniqueUsername = baseUsername;
        var counter = 1;

        while (usernameExistsChecker.apply(uniqueUsername)) {
            log.info("Username=" + uniqueUsername + " already exists, starting generating new username... ");
            uniqueUsername = baseUsername + counter;
            counter++;
        }

        log.info("Unique username was created: " + uniqueUsername);
        return uniqueUsername;
    }

    public static String generatePassword() {
        log.info("Stated generating password... ");
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .build();

        log.info("Password was successfully generated... ");
        return generator.generate(10);
    }
}
