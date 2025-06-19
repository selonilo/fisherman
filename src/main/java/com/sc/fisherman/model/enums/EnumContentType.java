package com.sc.fisherman.model.enums;

public enum EnumContentType {
    POST(0L),
    SALES(1L),
    LOCATION(2L);

    private final long value;

    EnumContentType(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static EnumContentType fromValue(long value) {
        for (EnumContentType fish : EnumContentType.values()) {
            if (fish.value == value) {
                return fish;
            }
        }
        throw new IllegalArgumentException("Invalid value for FishType: " + value);
    }
}