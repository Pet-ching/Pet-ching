package com.mandarin.petching.domain;


import lombok.Getter;

@Getter
public enum SearchType {
    title("제목"), content("내용"), titleOrContent("제목+내용");

    private final String korName;

    SearchType(String korName) {
        this.korName = korName;
    }
}
