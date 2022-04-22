package com.mandarin.petching.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public abstract class CertificateType {

    public static List<String> getCertificateList() {
        List<String> list = new ArrayList<>();

        list.add("훈련사 자격증");
        list.add("반려동물행동상담사");
        list.add("반려동물목욕관리사");
        list.add("반려동물행동교정사");
        list.add("반려동물 관리자 자격증");
        list.add("동물간호복지사");
        list.add("수의간호 교원자격증");

        return list;
    }
}
