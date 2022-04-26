package com.mandarin.petching.domain;

import lombok.*;
import org.springframework.stereotype.Component;

@Getter
@Setter
@RequiredArgsConstructor
@Component
public class Search {

    private SearchType type = SearchType.title;
    private  String keyword = "";



}

