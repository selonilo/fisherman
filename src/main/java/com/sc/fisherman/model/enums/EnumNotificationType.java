package com.sc.fisherman.model.enums;

public enum EnumNotificationType {
    LIKE(0L),
    COMMENT(1L),
    FOLLOW(2L),
    NEW_POST(3L);

    private final long value;

    EnumNotificationType(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static EnumNotificationType fromValue(long value) {
        for (EnumNotificationType fish : EnumNotificationType.values()) {
            if (fish.value == value) {
                return fish;
            }
        }
        throw new IllegalArgumentException("Invalid value for FishType: " + value);
    }
}