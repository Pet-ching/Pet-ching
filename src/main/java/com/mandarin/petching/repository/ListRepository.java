// code by. hyeok
package com.mandarin.petching.repository;

import com.mandarin.petching.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface
ListRepository extends JpaRepository<Member, Long> {

}
