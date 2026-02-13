package com.pragma.mensajeria.infrastructure.constant;

import java.util.regex.Pattern;

public final class ValidationConstants {

    private ValidationConstants() {
        throw new AssertionError("Cannot instantiate ValidationConstants");
    }

    public static final Pattern PHONE_PATTERN = Pattern.compile(
            "^\\+?\\d{1,15}$"
    );

    public static final int MAX_PHONE_LENGTH = 15;
    public static final int MIN_PHONE_LENGTH = 7;
    public static final int MAX_MESSAGE_LENGTH = 160;
}
