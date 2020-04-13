package com.damgor.foodapp.service.serviceImpl;

import com.damgor.foodapp.controller.DiaryPageController;
import com.damgor.foodapp.controller.ProfileController;
import com.damgor.foodapp.exception.EntityNotFoundException;
import com.damgor.foodapp.model.DiaryPage;
import com.damgor.foodapp.model.DiaryPageId;
import com.damgor.foodapp.model.Message;
import com.damgor.foodapp.repository.DiaryPageRepository;
import com.damgor.foodapp.service.DiaryPageService;
import com.damgor.foodapp.service.FoodDiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class DiaryPageServiceImpl implements DiaryPageService {

    @Autowired
    private DiaryPageRepository diaryPageRepository;


    @Autowired
    private FoodDiaryService foodDiaryService;

    @Override
    public List<DiaryPage> getAllDiaryPages(Long profileId) {
        foodDiaryService.getFoodDiary(profileId);
        List<DiaryPage> pages = diaryPageRepository.findByIdProfileId(profileId);
        addBasicLinks(pages);
        return pages;
    }

    @Override
    public DiaryPage getDiaryPage(Long profileId, String stringDate) {
        DiaryPage diaryPage = getDiaryPageIfExist(profileId, Date.valueOf(stringDate));
        addBasicLinks(diaryPage);
        return diaryPage;
    }

    @Override
    public DiaryPage addDiaryPage(Long profileId, String stringDate) {
        DiaryPage diaryPage = new DiaryPage();

        if (stringDate == null) {
            Date currentDate = new Date(System.currentTimeMillis());
            diaryPage.setId(new DiaryPageId(profileId, currentDate));
        } else {
            diaryPage.setId(new DiaryPageId(profileId, Date.valueOf(stringDate)));
        }

        diaryPageRepository.save(diaryPage);

        diaryPage = getDiaryPageIfExist(profileId, diaryPage.getId().getDate());
        addBasicLinks(diaryPage);
        return diaryPage;
    }

    @Override
    public Message removeDiaryPage(Long profileId, String stringDate) {
        Date date = Date.valueOf(stringDate);
        getDiaryPageIfExist(profileId, date);

        diaryPageRepository.deleteById(new DiaryPageId(profileId, date));

        Message message = new Message();
        message.setLink(linkTo(methodOn(DiaryPageController.class).getAllDiaryPages(profileId)).withRel("Get all profile's diary pages"));
        message.setMessage("Diary page has been removed successfully. To see others profile's diary pages click on the following link.");

        return message;
    }

    /////////////// SUPPORTING METHODS /////////////

    private DiaryPage getDiaryPageIfExist(Long profileId, Date date) {
        foodDiaryService.getFoodDiary(profileId);

        Optional<DiaryPage> optional = diaryPageRepository.findById(new DiaryPageId(profileId, date));
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new EntityNotFoundException(DiaryPage.class, "id", profileId.toString(), "date", date.toString());
        }
    }

    private void addBasicLinks(DiaryPage diaryPage) {
        diaryPage.add(
                linkTo(methodOn(DiaryPageController.class).getDiaryPage(diaryPage.getId().getProfileId(), diaryPage.getId().getDate().toString())).withSelfRel(),
                linkTo(methodOn(DiaryPageController.class).getAllDiaryPages(diaryPage.getId().getProfileId())).withRel("Get all profile's diary pages"),
                linkTo(methodOn(ProfileController.class).getProfile(diaryPage.getId().getProfileId())).withRel("Back to profile")
        );
    }

    private void addBasicLinks(List<DiaryPage> diaryPages) {
        diaryPages.forEach(diaryPage -> {
            diaryPage.add(
                    linkTo(methodOn(DiaryPageController.class).getDiaryPage(diaryPage.getId().getProfileId(), diaryPage.getId().getDate().toString())).withSelfRel(),
                    linkTo(methodOn(DiaryPageController.class).getAllDiaryPages(diaryPage.getId().getProfileId())).withRel("Get all profile's diary pages"),
                    linkTo(methodOn(ProfileController.class).getProfile(diaryPage.getId().getProfileId())).withRel("Back to profile")
            );
        });
    }
}
