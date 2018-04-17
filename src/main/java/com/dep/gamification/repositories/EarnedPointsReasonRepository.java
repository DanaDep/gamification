package com.dep.gamification.repositories;

import com.dep.gamification.models.EarnedPointsReason;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EarnedPointsReasonRepository extends MongoRepository<EarnedPointsReason, String>{
}
