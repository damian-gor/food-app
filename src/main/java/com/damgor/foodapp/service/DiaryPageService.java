package com.damgor.foodapp.service;

import com.damgor.foodapp.model.DiaryPage;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.model.Profile;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

public interface DiaryPageService {

    List<DiaryPage> getAllDiaryPages(Long profileId);
    DiaryPage getDiaryPage(Long profileId, String stringDate) throws ParseException;
    DiaryPage addDiaryPage(Long profileId, String stringDate);
    DiaryPage updateDiaryPageDate (Long profileId, String stringDate, Date newDate);
    Message removeDiaryPage(Long profileId, String stringDate);
}
