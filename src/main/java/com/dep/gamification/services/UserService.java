package com.dep.gamification.services;

import com.dep.gamification.models.EarnedPointsRequest;
import com.dep.gamification.models.SpentPointsRequest;
import com.dep.gamification.models.User;
import com.dep.gamification.repositories.UserRepository;
import com.dep.gamification.util.Helper;
import com.dep.gamification.util.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.rmi.CORBA.Util;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;
    @Autowired private EarnedPointsRequestService earnedPointsRequestService;
    @Autowired private SpentPointsRequestService spentPointsRequestService;

    public User createUser(User user){
        return userRepository.save(user);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
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
