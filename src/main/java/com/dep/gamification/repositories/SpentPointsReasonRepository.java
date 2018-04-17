package com.dep.gamification.repositories;

import com.dep.gamification.models.SpentPointsReason;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpentPointsReasonRepository extends MongoRepository<SpentPointsReason, String>{
}
