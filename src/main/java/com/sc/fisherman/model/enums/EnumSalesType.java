package com.sc.fisherman.model.enums;

public enum EnumSalesType {
    BALIKCI_KIYAFETLERI(0L),
    OLTA_MAKINELERI(1L),
    CAPARILER(2L),
    MISINA(3L),
    OLTA_KAMISLARI(4L),
    EKIPMANLAR(5L),
    SAHTE_YEMLER(6L),
    OLTA_SETLERI(7L),
    KURSUN_FIRDONDU_AKSESUARLAR(8L),
    DIGER(29L);

    private final long value;

    EnumSalesType(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static EnumSalesType fromValue(long value) {
        for (EnumSalesType fish : EnumSalesType.values()) {
            if (fish.value == value) {
                return fish;
            }
        }
        throw new IllegalArgumentException("Invalid value for FishType: " + value);
    }
}