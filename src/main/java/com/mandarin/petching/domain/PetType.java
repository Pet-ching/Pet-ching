package com.mandarin.petching.domain;

public enum PetType {
    SMALL_DOG("소형견"), MIDDLE_DOG("중형견"), LARGE_DOG("대형견"), CAT("고양이"), ETC("기타 동물");

    private final String description;

    PetType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
