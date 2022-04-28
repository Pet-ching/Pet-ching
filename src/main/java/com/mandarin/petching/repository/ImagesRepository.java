package com.mandarin.petching.repository;

import com.mandarin.petching.domain.Board;
import com.mandarin.petching.domain.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagesRepository extends JpaRepository<Images, Long> {
    public List<Images> findByBoard(Board board);

    //삭제
    public void deleteByBoard(Board board);


}
