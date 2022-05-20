package com.mandarin.petching.domain;


import lombok.Getter;


@Getter
public enum Role {
   ADMIN("관리자"), USER("일반 사용자");

   private final String korName;

   Role(String korName) {
      this.korName = korName;
   }
}