package com.damgor.foodapp.service.serviceImpl;

import com.damgor.foodapp.controller.DiaryPageController;
import com.damgor.foodapp.controller.FoodDiaryController;
import com.damgor.foodapp.controller.ProfileController;
import com.damgor.foodapp.exception.EntityNotFoundException;
import com.damgor.foodapp.model.DiaryPage;
import com.damgor.foodapp.model.DiaryPageId;
import com.damgor.foodapp.model.FoodDiary;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.repository.DiaryPageRepository;
import com.damgor.foodapp.service.DiaryPageService;
import com.damgor.foodapp.service.FoodDiaryService;
import com.damgor.foodapp.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class DiaryPageServiceImpl implements DiaryPageService {

    @Autowired
    private DiaryPageRepository diaryPageRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private FoodDiaryService foodDiaryService;

    @Autowired
    private static SimpleDateFormat formatter;

    @Override
    public List<DiaryPage> getAllDiaryPages(Long profileId) {
        foodDiaryService.getFoodDiary(profileId);
        List<DiaryPage> pages = diaryPageRepository.findByIdProfileId(profileId);
        addBasicLinks(pages);
        return pages;
    }

    @Override
    public DiaryPage getDiaryPage(Long profileId, String stringDate) throws ParseException {
        DiaryPage diaryPage = getDiaryPageIfExist(profileId, stringDate);
        System.out.println("getDataPage, string date: " +stringDate);
        System.out.println("getDataPage, id date: " +diaryPage.getId().getDate());

        addBasicLinks(diaryPage);
        return diaryPage;
    }

    @Override
    public DiaryPage addDiaryPage(Long profileId, String stringDate) {
        DiaryPage diaryPage = new DiaryPage();
        System.out.println("string date: " + stringDate);
        if (stringDate.equals("1900-01-01")) {
            System.out.println("wpadlem do generatora dat");
//            String currentDate;
            diaryPage.setId(new DiaryPageId(profileId, formatDate(getCurrentDate())));
//            stringDate=formatter.format(currentDate)

        }
        else {
            System.out.println("wpadlem do formatera dat");
            diaryPage.setId(new DiaryPageId(profileId, formatDate(stringDate)));
        }
        System.out.println("get setted date: "+ diaryPage.getId().getDate());

        diaryPageRepository.save(diaryPage);
        try {
            diaryPage=getDiaryPage(diaryPage.getId().getProfileId(), stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        addBasicLinks(diaryPage);
        return diaryPage;
    }

    @Override
    public DiaryPage updateDiaryPageDate(Long profileId, String stringDate, Date newDate) {
        return null;
    }

    @Override
    public Message removeDiaryPage(Long profileId, String stringDate) {
        return null;
    }


    /////////////// SUPPORTING METHODS /////////////

    private DiaryPage getDiaryPageIfExist(Long profileId, String stringDate) {
        foodDiaryService.getFoodDiary(profileId);

        List<DiaryPage> diaryPages = diaryPageRepository.findByIdProfileId(profileId);

        DiaryPage diaryPage = null;
        for (DiaryPage d : diaryPages) {
            if (d.getId().getDate().toString().equals(stringDate)) {
                diaryPage = d;
                break;
            }
        }
        if (diaryPage != null) return diaryPage;
        else {
            throw new EntityNotFoundException(DiaryPage.class, "id", profileId.toString());
        }
    }

    private static String getCurrentDate() {
        formatter.setTimeZone(TimeZone.getDefault());
            Date currentDate = new Date (System.currentTimeMillis());
            System.out.println("nowa data: "+currentDate);
            String stringCurrentDate = formatter.format(currentDate);
            System.out.println("stringCurrentDate: " +stringCurrentDate);
//            currentDate = formatter.parse(stringCurrentDate);
//            System.out.println("after parsing: " +currentDate);
            return stringCurrentDate;
    }

    private static String dateToString(Date date) {
        try {
            String stringDate = date.toString();
            return stringDate;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    private static Date formatDate (String stringDate) {

        try {
            Date date = formatter.parse(stringDate);
            return date;
        } catch (ParseException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void addBasicLinks(DiaryPage diaryPage) {
        try {
            diaryPage.add(
                    linkTo(methodOn(DiaryPageController.class).getDiaryPage(diaryPage.getId().getProfileId(), diaryPage.getId().getDate().toString())).withSelfRel(),
                    linkTo(methodOn(DiaryPageController.class).getAllDiaryPages(diaryPage.getId().getProfileId())).withRel("Get all profile's diary pages"),
                    linkTo(methodOn(ProfileController.class).getProfile(diaryPage.getId().getProfileId())).withRel("Back to profile")
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void addBasicLinks(List<DiaryPage> diaryPages)  {
        diaryPages.forEach(diaryPage -> {
            try {
                diaryPage.add(
                        linkTo(methodOn(DiaryPageController.class).getDiaryPage(diaryPage.getId().getProfileId(), diaryPage.getId().getDate().toString())).withSelfRel(),
                        linkTo(methodOn(DiaryPageController.class).getAllDiaryPages(diaryPage.getId().getProfileId())).withRel("Get all profile's diary pages"),
                        linkTo(methodOn(ProfileController.class).getProfile(diaryPage.getId().getProfileId())).withRel("Back to profile")
                );
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }
}
