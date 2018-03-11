package com.dep.gamification.controllers;

import com.dep.gamification.models.EarnedPointsRequest;
import com.dep.gamification.models.EarnedPointsRequestResponse;
import com.dep.gamification.services.EarnedPointsRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/earnedpointsrequest")
public class EarnedPointsRequestController {

    @Autowired private EarnedPointsRequestService earnedPointsRequestService;

    @PostMapping("/")
    public ResponseEntity<EarnedPointsRequest> createEarnedPointsRequest(@RequestBody EarnedPointsRequest earnedPointsRequest) {
        EarnedPointsRequest response = null;
        // TODO: implement a method which will check the payload
        if (earnedPointsRequest != null) {
            response = earnedPointsRequestService.createEarnedPointsRequest(earnedPointsRequest);
            return new ResponseEntity<EarnedPointsRequest>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<EarnedPointsRequest>(response, HttpStatus.I_AM_A_TEAPOT);
    }

    @PutMapping("/{earnedPointsRequestId}/response")
    public ResponseEntity<EarnedPointsRequestResponse> createEarnedPointsRequestResponse(@PathVariable String earnedPointsRequestId, @RequestBody EarnedPointsRequestResponse earnedPointsRequestResponse){
        EarnedPointsRequestResponse response = null;
        if(earnedPointsRequestId != null && earnedPointsRequestResponse != null){
            response = earnedPointsRequestService.createEarnedPointsRequestResponse(earnedPointsRequestId, earnedPointsRequestResponse);
            return new ResponseEntity<EarnedPointsRequestResponse>(response, HttpStatus.OK);
        }
        return new ResponseEntity<EarnedPointsRequestResponse>(response, HttpStatus.I_AM_A_TEAPOT);
    }

    @GetMapping("/earnedpointsrequest/{userId}")
    public @ResponseBody ResponseEntity<List<EarnedPointsRequest>> getAllEarnedPointsRequestsByUserId(@PathVariable String userId){
        List<EarnedPointsRequest> earnedPointsRequestList = null;
        // userId should be the id of the logged user
        if(userId != null){
            earnedPointsRequestList = earnedPointsRequestService.getAllEarnedPointsRequestsByUserId(userId);
            return new ResponseEntity<List<EarnedPointsRequest>>(earnedPointsRequestList,HttpStatus.OK);
        }
        return new ResponseEntity<List<EarnedPointsRequest>>(earnedPointsRequestList,HttpStatus.NOT_FOUND);
    }
}
