package com.damgor.foodapp.service;

import com.damgor.foodapp.model.DiaryPage;
import com.damgor.foodapp.model.Message;

import java.util.List;

public interface DiaryPageService {
    List<DiaryPage> getAllDiaryPages(Long profileId);
    DiaryPage getDiaryPage(Long profileId, String stringDate);
    DiaryPage addDiaryPage(Long profileId, String stringDate);
    Message removeDiaryPage(Long profileId, String stringDate);
}
