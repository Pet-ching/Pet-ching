package com.mandarin.petching.domain;

public enum GenderType {
    MALE("남자"), FEMALE("여자");

    private final String description;

    GenderType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
