package com.sc.fisherman.model.enums;

public enum EnumPostType {
    TEKNOLOJI(0L),
    EGITIM(1L),
    SAGLIK(2L),
    SEYAHAT(3L),
    MODA(4L),
    YEMEK(5L),
    TASARIM(6L),
    SANAT(7L),
    HABER(8L),
    SPOR(9L),
    FINANS(10L),
    YAZILIM(11L),
    KARIYER(12L),
    GELISIM(13L),
    FOTOGRAFCILIK(14L),
    DIJITAL(15L),
    OYUN(16L),
    EGLENCE(17L),
    DOGA(18L),
    PSIKOLOJI(19L),
    FIKIRLER(20L),
    GIRISIMCILIK(21L),
    CEVRE(22L),
    EDEBIYAT(23L),
    MUZIK(24L);

    private final long value;

    EnumPostType(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static EnumPostType fromValue(long value) {
        for (EnumPostType department : EnumPostType.values()) {
            if (department.value == value) {
                return department;
            }
        }
        throw new IllegalArgumentException("Invalid value for Department: " + value);
    }
}
