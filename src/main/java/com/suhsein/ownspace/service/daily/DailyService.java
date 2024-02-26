package com.suhsein.ownspace.service.daily;

import com.suhsein.ownspace.controller.daily.DailyDto;
import com.suhsein.ownspace.domain.daily.Daily;
import com.suhsein.ownspace.domain.daily.SearchCodeName;
import com.suhsein.ownspace.repository.daily.DailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyService {
    private final DailyRepository dailyRepository;

    @Transactional
    public void save(Daily daily){
        dailyRepository.save(daily);
    }

    public Optional<Daily> findOne(Long dailyId){
        return dailyRepository.findById(dailyId);
    }

    public Daily findPrevDaily(Long dailyId){
        return dailyRepository.findPrevDaily(dailyId);
    }
    public Daily findNxtDaily(Long dailyId){
        return dailyRepository.findNxtDaily(dailyId);
    }
    public List<Daily> findAll(){
        return dailyRepository.findAll();
    }

    public Page<Daily> paging(Pageable pageable){
        Page<Daily> dailyPages = dailyRepository.findAll(pageable);
        return dailyPages;
    }

    public Page<Daily> searchPaging(Pageable pageable, SearchCodeName code, String keyword) {
        Page<Daily> dailyPages = new PageImpl<>(Collections.emptyList());
        if (code == SearchCodeName.TITLE_CONTENT) {
            dailyPages = dailyRepository.findAllByTitleAndContent(pageable, keyword);
        } else if (code == SearchCodeName.TITLE) {
            dailyPages = dailyRepository.findAllByTitle(pageable, keyword);
        } else if (code == SearchCodeName.CONTENT) {
            dailyPages = dailyRepository.findAllByContent(pageable, keyword);
        } else if (code == SearchCodeName.WRITER) {
            dailyPages = dailyRepository.findAllByWriter(pageable, keyword);
        }
        return dailyPages;
    }

    @Transactional
    public void editDaily(Long id, DailyDto form, LocalDateTime updateDate){
        dailyRepository.updateDailyById(id, form, updateDate);
    }

    @Transactional
    public void remove(Long dailyId){
        dailyRepository.delete(findOne(dailyId).get());
    }

    @Transactional
    public void increaseView(Long dailyId) {
        Daily daily = dailyRepository.findById(dailyId).get();
        daily.setViews(daily.getViews() + 1L);
    }

    @Transactional
    public void increaseComment(Long dailyId){
        Daily daily = dailyRepository.findById(dailyId).get();
        daily.setActiveCommentCount(daily.getActiveCommentCount() + 1L);
    }

    @Transactional
    public void decreaseComment(Long dailyId){
        Daily daily = dailyRepository.findById(dailyId).get();
        daily.setActiveCommentCount(daily.getActiveCommentCount() - 1L);
    }

}
