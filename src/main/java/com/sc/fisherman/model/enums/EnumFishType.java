package com.sc.fisherman.model.enums;

public enum EnumFishType {
    // Tatlı su balıkları
    SAZAN(0L),
    TURNA(1L),
    ALABALIK(2L),
    LEVREK_TATLI_SU(3L),
    YAYIN(4L),
    KEREVIT(5L),
    KADI_NEZIHI(6L),
    TAHTA_BALIGI(7L),
    TATLISU_MERCANI(8L),
    KARA_BALIK(9L),

    // Tuzlu su balıkları
    ISTAVRIT(10L),
    PALAMUT(11L),
    LUFER(12L),
    HAMSI(13L),
    SARDALYA(14L),
    MEZGIT(15L),
    CIPURA(16L),
    LEVREK_TUZLU_SU(17L),
    MERCAN(18L),
    BARBUN(19L),
    TEKIR(20L),
    KALKAN(21L),
    ZARGANA(22L),
    AKYA(23L),
    KUPES(24L),
    GRANYOZ(25L),
    FANGRI(26L),
    TRANKA(27L),
    ORKINOS(28L),
    DIGER(29L);

    private final long value;

    EnumFishType(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    public static EnumFishType fromValue(long value) {
        for (EnumFishType fish : EnumFishType.values()) {
            if (fish.value == value) {
                return fish;
            }
        }
        throw new IllegalArgumentException("Invalid value for FishType: " + value);
    }
}