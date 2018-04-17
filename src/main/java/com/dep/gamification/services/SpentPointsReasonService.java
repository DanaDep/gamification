package com.dep.gamification.services;

import com.dep.gamification.models.SpentPointsReason;
import com.dep.gamification.repositories.SpentPointsReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SpentPointsReasonService {

    @Autowired
    public SpentPointsReasonRepository spentPointsReasonRepository;

    public SpentPointsReason addSpentReason(SpentPointsReason reason){
        return spentPointsReasonRepository.save(reason);
    }

    public List<String> getAllEarnedPointsReasons(){
        List<String> reasons = new ArrayList<>();
        List<SpentPointsReason> spentPointsReasons = spentPointsReasonRepository.findAll();

        for(SpentPointsReason reason : spentPointsReasons){
            reasons.add(reason.getReason());
        }
        return reasons;
    }
}
