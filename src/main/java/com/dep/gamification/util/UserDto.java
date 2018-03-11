package com.dep.gamification.util;

import com.dep.gamification.models.EarnedPointsRequest;
import com.dep.gamification.models.SpentPointsRequest;

import java.util.List;

public class UserDto {

    private List<EarnedPointsRequest> earnedPointsRequest;
    private List<SpentPointsRequest> spentPointsRequest;
    private int totalNoOfPoints;

    public List<EarnedPointsRequest> getEarnedPointsRequest() {
        return earnedPointsRequest;
    }

    public void setEarnedPointsRequest(List<EarnedPointsRequest> earnedPointsRequest) {
        this.earnedPointsRequest = earnedPointsRequest;
    }

    public List<SpentPointsRequest> getSpentPointsRequest() {
        return spentPointsRequest;
    }

    public void setSpentPointsRequest(List<SpentPointsRequest> spentPointsRequest) {
        this.spentPointsRequest = spentPointsRequest;
    }

    public int getTotalNoOfPoints() {
        return totalNoOfPoints;
    }

    public void setTotalNoOfPoints(int totalNoOfPoints) {
        this.totalNoOfPoints = totalNoOfPoints;
    }
}
