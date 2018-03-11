package com.dep.gamification.services;

import com.dep.gamification.models.SpentPointsRequest;
import com.dep.gamification.models.SpentPointsRequestResponse;
import com.dep.gamification.models.User;
import com.dep.gamification.repositories.SpentPointsRequestRepository;
import com.dep.gamification.util.Helper;
import com.dep.gamification.util.MailHelper;
import com.dep.gamification.util.RequestState;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SpentPointsRequestService {

    private static final Logger logger = Logger.getLogger(SpentPointsRequestService.class);

    @Autowired
    private SpentPointsRequestRepository spentPointsRequestRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private MailHelper mailHelper;

    public String createSpendPointsRequest(SpentPointsRequest spentPointsRequest) {
        User requester = userService.getUserByUserId(spentPointsRequest.getRequester());
        logger.info("LOG: Requester's id is: " + requester.getUserId());

        boolean requesterHasEnoughPoints = checkRequesterHasEnoughPointsToSpend(requester.getUserId(), spentPointsRequest.getPointsToBeSpent());
        if(requesterHasEnoughPoints){
            String requestId = UUID.randomUUID().toString();
            spentPointsRequest.setSpentPointsRequestId(requestId);

            boolean sentToSupervisor = notifySupervisor(requester, requestId);
            if (sentToSupervisor) {
                logger.info("LOG: The supervisor was notified");
                spentPointsRequest.setSentToSupervisor(true);

                logger.info("LOG: The request will be saved in db");
                spentPointsRequestRepository.save(spentPointsRequest);

                return "The request was sucessful";
            }

        }

        return "The request was unsuccessful because there are not enough points to be spent";
    }

    private boolean notifySupervisor(User requester, String requestId) {
        logger.info("LOG: Creating supervisor notification");

        User supervisor = userService.getUserByUserId(requester.getSupervisor());
        logger.info("LOG: Requester's supervisor is " + requester.getSupervisor());

        boolean messageIsSent = false;
        try {
            String emailContenet = mailHelper.createSpentPointsRequestEmailTemplate(supervisor, requester, requestId);
            mailService.sendMessage(supervisor.getEmail(), "Gamification: spent points request", emailContenet);
            messageIsSent = true;
            logger.info("LOG: Message was sent to supervisor.");
        } catch (Exception e) {
            logger.info("LOG: Error in sending email: " + e);
            messageIsSent = false;
        }
        return messageIsSent;
    }

    public SpentPointsRequestResponse createSpentPointsRequestResponse(String spentPointsRequestId, SpentPointsRequestResponse spentPointsRequestResponse) {
        logger.info("LOG: Getting supervisor response for spent points request with id: " + spentPointsRequestId);

        SpentPointsRequest spentPointsRequest = spentPointsRequestRepository.findBySpentPointsRequestId(spentPointsRequestId);
        User requester = userService.getUserByUserId(spentPointsRequest.getRequester());

        if ("accepted".equals(spentPointsRequestResponse.getStatus())) {
            spentPointsRequest.setState(RequestState.ACCEPTED);
            spentPointsRequestRepository.save(spentPointsRequest);

            logger.info("LOG: An email will be sent to the requester to announce her/him that she/he can spend coins");
            notifyRequester(requester);

            return spentPointsRequestResponse;
        }
        spentPointsRequest.setState(RequestState.REJECTED);
        spentPointsRequest.setSupervisorResponse(spentPointsRequestResponse.getComment());
        spentPointsRequestRepository.save(spentPointsRequest);
        notifyRequester(requester, spentPointsRequestResponse.getComment());

        return spentPointsRequestResponse;
    }

    private void notifyRequester(User requester) {
        logger.info("LOG: Creating requester's notification");

        try {
            String emailContent = mailHelper.createSpentPointsAcceptedResponseEmailTemplate(requester);
            mailService.sendMessage(requester.getEmail(), "Gamification: spent points accepted :D", emailContent);
        } catch (Exception e) {
            logger.info("LOG: Error in sending email: " + e);
        }

        logger.info("LOG: Ending beneficiary's notification");
    }

    private void notifyRequester(User requester, String comment) {
        logger.info("LOG: An email will be sent to the requester to announce her/him that spent points request was rejected");
        logger.info("LOG: Creating requester's notification");
        User supervisor = userService.getUserByUserId(requester.getSupervisor());
        try {
            String emailContent = mailHelper.createSpentPointsRejectedResponseEmailTemplate(requester, comment);
            mailService.sendMessage(requester.getEmail(), "Gamification: spent points request rejected", emailContent);
        } catch (Exception e) {
            logger.info("LOG: Error in sending email: " + e);
        }
        logger.info("LOG: Ending requester's notification");
    }

    public int getAllSpentPointsWhichAUserHas(String requesterId) {
        int totalSpentPoints = 0;
        List<SpentPointsRequest> earnedPointsRequestList = spentPointsRequestRepository.findAllByRequester(requesterId);
        for (SpentPointsRequest request : earnedPointsRequestList) {
            if (RequestState.ACCEPTED.equals(request.getState())) {
                totalSpentPoints += request.getPointsToBeSpent();
            }
        }

        return totalSpentPoints;
    }

    public List<SpentPointsRequest> getAllSpentPointsRequestsByUserId(String requesterId) {
        logger.info("LOG: Find all requester's spent points requests");
        return spentPointsRequestRepository.findAllByRequester(requesterId);
    }

    private boolean checkRequesterHasEnoughPointsToSpend(String userId, int pointsToBeSpent) {
        int totalPoints = userService.getAllPointsWhichAUserCanSpent(userId);
        if (totalPoints > pointsToBeSpent) {
            return true;
        }
        return false;
    }

}
