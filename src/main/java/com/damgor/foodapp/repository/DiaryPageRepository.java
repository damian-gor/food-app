package com.damgor.foodapp.repository;

import com.damgor.foodapp.model.DiaryPage;
import com.damgor.foodapp.model.DiaryPageId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.sql.Date;
import java.util.List;

public interface DiaryPageRepository extends JpaRepository <DiaryPage, DiaryPageId> {
    List<DiaryPage> findByIdProfileId(Long profileId);
    DiaryPage findByIdProfileIdAndIdDate(Long profileId, Date date);
}
