package com.damgor.foodapp.repository;

import com.damgor.foodapp.model.DiaryPage;
import com.damgor.foodapp.model.DiaryPageId;
import com.damgor.foodapp.model.FoodDiary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface DiaryPageRepository extends JpaRepository <DiaryPage, DiaryPageId> {
    List<DiaryPage> findByIdProfileId(Long profileId);
//    List<DiaryPage> findByIdProfileIdAndIdDate(Long profileId, Date date);
//    DiaryPage findByIdProfileIdAndIdDate(Long profileId, String stringDate);
//    DiaryPage findByIdProfileIdAndIdDate(Long profileId, Date date);
//    DiaryPage findByIdDate(Date date);
//    DiaryPage findByDate(Date date);
//    DiaryPage findByDate(String stringDate);
}
