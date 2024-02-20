package com.example.demo.service.daily;

import com.example.demo.controller.daily.DailyDto;
import com.example.demo.domain.daily.Daily;
import com.example.demo.repository.daily.DailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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

    public List<Daily> findAll(){
        return dailyRepository.findAll();
    }

    public Page<Daily> paging(Pageable pageable){
        Page<Daily> dailyPages = dailyRepository.findAll(pageable);
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
}
