package com.mandarin.petching.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class AbleServiceType {

    public static List<String> getAbleServiceList() {
        List<String> list = new ArrayList<>();

        list.add("퍼피 케어");
        list.add("실내놀이");
        list.add("응급 처치");
        list.add("집앞 픽업");
        list.add("노견 케어");
        list.add("매일 산책");
        list.add("약물 복용");
        list.add("장기 예약");
        list.add("목욕 가능");
        list.add("모발 관리");

        return list;
    }
}
