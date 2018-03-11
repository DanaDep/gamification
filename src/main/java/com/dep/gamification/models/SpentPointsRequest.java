package com.dep.gamification.models;

import com.dep.gamification.util.RequestState;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "spentpointsrequests")
public class SpentPointsRequest {

    @Id
    private String spentPointsRequestId;

    @NotNull
    private String requester;

    private int pointsToBeSpent;
    private String reason;
    private String comment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date crated = new Date();

    private boolean sentToSupervisor;

    private RequestState state = RequestState.PENDING;

    private String supervisorResponse;

    public String getSpentPointsRequestId() {
        return spentPointsRequestId;
    }

    public void setSpentPointsRequestId(String spentPointsRequestId) {
        this.spentPointsRequestId = spentPointsRequestId;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public int getPointsToBeSpent() {
        return pointsToBeSpent;
    }

    public void setPointsToBeSpent(int pointsToBeSpent) {
        this.pointsToBeSpent = pointsToBeSpent;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCrated() {
        return crated;
    }

    public void setCrated(Date crated) {
        this.crated = crated;
    }

    public boolean isSentToSupervisor() {
        return sentToSupervisor;
    }

    public void setSentToSupervisor(boolean sentToSupervisor) {
        this.sentToSupervisor = sentToSupervisor;
    }

    public RequestState getState() {
        return state;
    }

    public void setState(RequestState state) {
        this.state = state;
    }

    public String getSupervisorResponse() {
        return supervisorResponse;
    }

    public void setSupervisorResponse(String supervisorResponse) {
        this.supervisorResponse = supervisorResponse;
    }
}
