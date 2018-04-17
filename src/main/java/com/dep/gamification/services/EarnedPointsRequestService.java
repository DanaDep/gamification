package com.dep.gamification.services;

import com.dep.gamification.models.EarnedPointsRequest;
import com.dep.gamification.models.EarnedPointsRequestResponse;
import com.dep.gamification.models.User;
import com.dep.gamification.repositories.EarnedPointsRequestRepository;
import com.dep.gamification.util.MailHelper;
import com.dep.gamification.util.RequestState;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EarnedPointsRequestService {

    private static final Logger logger = Logger.getLogger(EarnedPointsRequestService.class);

    @Autowired
    private EarnedPointsRequestRepository earnedPointsRequestRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private MailService mailService;
    @Autowired
    private MailHelper mailHelper;

    public EarnedPointsRequest createEarnedPointsRequest(EarnedPointsRequest earnedPointsRequest) {
        logger.info("LOG: " + earnedPointsRequest.getBeneficiary());
        User beneficiary = getUserByCompleteName(earnedPointsRequest.getBeneficiary());
        logger.info("LOG: Beneficiary's id is: " + beneficiary.getUserId());

        User requester = getUserByCompleteName(earnedPointsRequest.getRequester());
        logger.info("LOG: Requester's id is: " + requester.getUserId());

        int points = extractPointsFromReason(earnedPointsRequest.getReason());
        earnedPointsRequest.setNumberOfPoints(points);

        String requestId = UUID.randomUUID().toString();
        earnedPointsRequest.setEarnedPointsRequestId(requestId);

        boolean sentToSupervisor = notifySupervisor(beneficiary, requester, requestId);
        if (sentToSupervisor) {
            logger.info("LOG: The supervisor was notified");
            earnedPointsRequest.setSendToSupervisor(true);
        }

        logger.info("LOG: The earnedPointsRequest will be saved in db");
        return earnedPointsRequestRepository.save(earnedPointsRequest);

    }

    private int extractPointsFromReason(String reason) {
        logger.info("LOG: " + reason);
        //boolean noOfPoints = reason.matches(".*\\d+.*");
        int points = Integer.parseInt(reason.replaceAll("[\\D]", ""));
        logger.info("LOG: " + points);

        return points;
    }

    private User getUserByCompleteName(String name) {
        // split name in firstname and lastname
        String[] splited = name.split("\\s+");
        String firstName = splited[0];
        String lastName = splited[1];
        logger.info(splited[0]);
        logger.info(splited[1]);

        User user = userService.getUserByFirstNameAndLastName(firstName, lastName);
        logger.info("LOG: " + user.getUserId());

        return user;
    }

    private boolean notifySupervisor(User beneficiary, User requester, String requestId) {
        logger.info("LOG: Creating supervisor notification");

        User supervisor = userService.getUserByUserId(beneficiary.getSupervisor());
        logger.info("LOG: Beneficiary's supervisor is " + beneficiary.getSupervisor());

        boolean messageIsSent = false;
        try {
            String emailContent = mailHelper.createEarnedPointsRequestEmailTemplate(supervisor, requester, requestId);
            mailService.sendMessage(supervisor.getEmail(), "Gamification: Earned points request", emailContent);
            messageIsSent = true;
            logger.info("LOG: Message was sent to supervisor.");
        } catch (Exception e) {
            logger.info("LOG: Error in sending email: " + e);
        }

        return messageIsSent;
    }

    public EarnedPointsRequestResponse createEarnedPointsRequestResponse(String earnedPointsRequestId, EarnedPointsRequestResponse earnedPointsRequestResponse) {
        logger.info("LOG: Getting supervisor response for earned points request with id: " + earnedPointsRequestId);

        EarnedPointsRequest earnedPointsRequest = earnedPointsRequestRepository.findByEarnedPointsRequestId(earnedPointsRequestId);
        User beneficiary = userService.getUserByUserId(earnedPointsRequest.getBeneficiary());
        User requester = userService.getUserByUserId(earnedPointsRequest.getRequester());

        if ("accepted".equals(earnedPointsRequestResponse.getStatus())) {
            earnedPointsRequest.setState(RequestState.ACCEPTED);
            earnedPointsRequestRepository.save(earnedPointsRequest);
            logger.info("LOG: An email will be sent to the beneficiary to announce her/him that she/he earned points");
            notifyBeneficiary(beneficiary);

            return earnedPointsRequestResponse;

        }

        earnedPointsRequest.setState(RequestState.REJECTED);
        earnedPointsRequest.setSupervisorResponse(earnedPointsRequestResponse.getComment());
        earnedPointsRequestRepository.save(earnedPointsRequest);
        notifyRequester(requester, earnedPointsRequestResponse.getComment());

        return earnedPointsRequestResponse;
    }

    private void notifyBeneficiary(User beneficiary) {
        logger.info("LOG: Creating beneficiary's notification");

        try {
            String emailContent = mailHelper.createEarnedPointsAcceptedResponseEmailTemplate(beneficiary);
            mailService.sendMessage(beneficiary.getEmail(), "Gamification: points earned :D", emailContent);
        } catch (Exception e) {
            logger.info("LOG: Error in sending email: " + e);
        }

        logger.info("LOG: Ending beneficiary's notification");
    }

    private void notifyRequester(User requester, String comment) {
        logger.info("LOG: An email will be sent to the requester to announce her/him that earned points request was rejected");

        try {
            String emailContent = mailHelper.createEarnedPointsRejectedResponseEmailTemplate(requester, comment);
            mailService.sendMessage(requester.getEmail(), "Gamification: request rejected", emailContent);
        } catch (Exception e) {
            logger.info("LOG: Error in sending email: " + e);
        }

        logger.info("LOG: Ending requester's notification");
    }

    public List<EarnedPointsRequest> getAllEarnedPointsRequestsByUserId(String userId) {
        logger.info("LOG: Find all beneficiary's earned points request");
//        User user = userService.getUserByUserId(userId);
//        return earnedPointsRequestRepository.findAllByBeneficiaryName(user.getFirstName() + " " + user.getLastName());
        return earnedPointsRequestRepository.findAllByBeneficiaryName(userId);
    }

    public int getAllEarnedPointsWhichAUserHas(String requesterName) {
        int totalEarnedPoints = 0;
        List<EarnedPointsRequest> earnedPointsRequestList = earnedPointsRequestRepository.findAllByBeneficiaryName(requesterName);
        for (EarnedPointsRequest request : earnedPointsRequestList) {
            logger.info("LOG: REASON FOR EARNED POINTS: " + request.getReason());
            if (RequestState.ACCEPTED.equals(request.getState())) {
                logger.info("LOG: NO OF EARNED POINTS: " + request.getNumberOfPoints());
                totalEarnedPoints += request.getNumberOfPoints();
            }
        }

        return totalEarnedPoints;
    }
}
