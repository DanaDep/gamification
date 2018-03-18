package com.dep.gamification.services;

import com.dep.gamification.models.EarnedPointsRequest;
import com.dep.gamification.models.SpentPointsRequest;
import com.dep.gamification.models.User;
import com.dep.gamification.repositories.UserRepository;
import com.dep.gamification.util.Helper;
import com.dep.gamification.util.UserDto;
import com.dep.gamification.util.UserStatus;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private static final Logger logger = Logger.getLogger(UserService.class);

    @Autowired private UserRepository userRepository;
    @Autowired private EarnedPointsRequestService earnedPointsRequestService;
    @Autowired private SpentPointsRequestService spentPointsRequestService;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public List<UserStatus> getStatusForAllUsers(){
        List<UserStatus> usersStatusList = new ArrayList<>();

        logger.info("LOG: Get all users from database");
        List<User> users = userRepository.findAll();

        for(User u : users){
            logger.info("LOG: Set up status for current user: " + u.getEmail());
            UserStatus userStatus = new UserStatus();
            userStatus.setFirstname(u.getFirstName());
            userStatus.setLastname(u.getLastName());
            userStatus.setPoints(getAllPointsWhichAUserCanSpent(u.getUserId()));

            usersStatusList.add(userStatus);
        }

        logger.info("LOG: Return current status for all users form database");
        return usersStatusList;
    }

    public User getUserByUserId(String userId){
        return userRepository.getUserByUserId(userId);
    }

    public UserDto getStatusByUserId(String userId){
        List<EarnedPointsRequest> earnedPointsRequestList = earnedPointsRequestService.getAllEarnedPointsRequestsByUserId(userId);
        List<SpentPointsRequest> spentPointsRequestList = spentPointsRequestService.getAllSpentPointsRequestsByUserId(userId);

        UserDto userDto = new UserDto();
        userDto.setEarnedPointsRequest(earnedPointsRequestList);
        userDto.setSpentPointsRequest(spentPointsRequestList);
        userDto.setTotalNoOfPoints(getAllPointsWhichAUserCanSpent(userId));

        return userDto;
    }

    public int getAllPointsWhichAUserCanSpent(String userId){
        int totalPoints = 0;
        int earnedPoints = earnedPointsRequestService.getAllEarnedPointsWhichAUserHas(userId);
        int spentPoints = spentPointsRequestService.getAllSpentPointsWhichAUserHas(userId);

        if(Helper.comparePoints(earnedPoints, spentPoints)){
            totalPoints = earnedPoints - spentPoints;
            return totalPoints;
        }
        return totalPoints;
    }
}
