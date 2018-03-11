package com.dep.gamification.models;

import com.dep.gamification.util.RequestState;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "earnedpointsrequests")
public class EarnedPointsRequest {
    @Id
    private String earnedPointsRequestId;

    @NotNull
    private String requester;

    @NotNull
    private String beneficiary;

    private String reason;
    private int numberOfPoints;
    private String comment;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date created = new Date();

    private boolean sendToSupervisor = false;

    private RequestState state = RequestState.PENDING;

    private String supervisorResponse;

    public String getEarnedPointsRequestId() {
        return earnedPointsRequestId;
    }

    public void setEarnedPointsRequestId(String earnedPointsRequestId) {
        this.earnedPointsRequestId = earnedPointsRequestId;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getNumberOfPoints() {
        return numberOfPoints;
    }

    public void setNumberOfPoints(int numberOfPoints) {
        this.numberOfPoints = numberOfPoints;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isSendToSupervisor() {
        return sendToSupervisor;
    }

    public void setSendToSupervisor(boolean sendToSupervisor) {
        this.sendToSupervisor = sendToSupervisor;
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
