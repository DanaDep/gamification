package com.dep.gamification.repositories;

import com.dep.gamification.models.SpentPointsRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpentPointsRequestRepository extends MongoRepository<SpentPointsRequest, String> {

    List<SpentPointsRequest> findAllByRequester(String requester);
    SpentPointsRequest findBySpentPointsRequestId(String spentPointsRequestId);
}
