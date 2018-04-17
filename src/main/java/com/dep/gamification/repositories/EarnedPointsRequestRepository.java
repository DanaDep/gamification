package com.dep.gamification.repositories;

import com.dep.gamification.models.EarnedPointsRequest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EarnedPointsRequestRepository extends MongoRepository<EarnedPointsRequest, String> {

    List<EarnedPointsRequest> findAllByBeneficiary(String beneficiary);

    EarnedPointsRequest findByEarnedPointsRequestId(String earnedPointsRequestId);

    @Query("{ 'beneficiary' : ?0 }")
    List<EarnedPointsRequest> findAllByBeneficiaryName(String beneficiaryName);

    //@Query("{'beneficiary': ?0, totalEarnedPoints: {$sum: '$numberOfPoints'}")
//    @Query("db.earnedpointsrequests.aggregate([{ $project: { total: { $sum: '$numberOfPoints'}}}])")
//    int getAllEarnedPointsByUserId(String userId);
}
