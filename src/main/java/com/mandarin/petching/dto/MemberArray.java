package com.mandarin.petching.dto;

import com.mandarin.petching.domain.Member;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class MemberArray {
    private List<FormDTO> list;
}
