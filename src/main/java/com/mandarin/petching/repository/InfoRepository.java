// code by. hyeok
package com.mandarin.petching.repository;

import com.mandarin.petching.domain.PetSitter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InfoRepository extends JpaRepository<PetSitter, Long> {

    Page<PetSitter>  findByWorkingAreaContaining(String searchKeyword, Pageable pageable);
//    PetSitter findByArea(String area);
}
