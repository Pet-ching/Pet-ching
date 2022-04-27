package com.mandarin.petching.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class WorkingDay {

    public static List<String> getWorkingList() {
        List<String> list = new ArrayList<>();

        list.add("월요일");
        list.add("화요일");
        list.add("수요일");
        list.add("목요일");
        list.add("금요일");
        list.add("토요일");
        list.add("일요일");

        return list;
    }
}
