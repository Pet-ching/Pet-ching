// code by. hyeok
package com.mandarin.petching.service;

import com.mandarin.petching.domain.Member;
import com.mandarin.petching.repository.ListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ListService {

    @Autowired
    private ListRepository listRepository;

    public ListService() {
    }

    public Page<Member> sitterList(Pageable pageable) {
        return listRepository.findAll(pageable);
    }

    public Member findById(Long id) {
        return listRepository.findById(id).get();
    }


}
