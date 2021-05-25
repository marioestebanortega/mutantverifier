package com.mercadolibre.mutantverifier.util;

public enum AdnLettersEnum {
    A, T, C, G;


    public static boolean contains(String val) {

        for (AdnLettersEnum c : AdnLettersEnum.values()) {
            if (c.name().equals(val)) {
                return true;
            }
        }

        return false;
    }
}
