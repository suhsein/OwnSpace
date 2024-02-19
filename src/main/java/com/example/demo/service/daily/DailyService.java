package com.example.demo.service.daily;

import com.example.demo.controller.daily.DailyDto;
import com.example.demo.domain.daily.Daily;
import com.example.demo.repository.daily.DailyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
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

    @Transactional
    public void editDaily(Long id, DailyDto form){
        dailyRepository.updateDailyById(id, form);
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
