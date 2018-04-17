package com.dep.gamification.services;

import com.dep.gamification.models.EarnedPointsReason;
import com.dep.gamification.repositories.EarnedPointsReasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EarnedPointsReasonService {

    @Autowired
    private EarnedPointsReasonRepository earnedPointsReasonRepository;

    public EarnedPointsReason addEarnedReason(EarnedPointsReason reason){
        return earnedPointsReasonRepository.save(reason);
    }

    public List<String> getAllEarnedPointsReasons(){
        List<String> earnedPointsReasons = new ArrayList<>();
        List<EarnedPointsReason> reasons = earnedPointsReasonRepository.findAll();

        for(EarnedPointsReason reason : reasons){
            earnedPointsReasons.add(reason.getReason() + " - " + reason.getPoints() + " points");
        }
        return earnedPointsReasons;
    }
}
