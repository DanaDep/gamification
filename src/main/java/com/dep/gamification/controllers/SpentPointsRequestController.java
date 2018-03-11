package com.dep.gamification.controllers;

import com.dep.gamification.models.SpentPointsRequest;
import com.dep.gamification.models.SpentPointsRequestResponse;
import com.dep.gamification.services.SpentPointsRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spentpointsrequest")
public class SpentPointsRequestController {

    @Autowired private SpentPointsRequestService spentPointsRequestService;

    @PostMapping("/")
    public ResponseEntity<String> createSpentPointsRequest(@RequestBody SpentPointsRequest spentPointsRequest){
        String response = null;
        // TODO: implement a method that will check the payload
        if(spentPointsRequest != null){
            response = spentPointsRequestService.createSpendPointsRequest(spentPointsRequest);
            return new ResponseEntity<String>(response, HttpStatus.CREATED);
        }
        return new ResponseEntity<String>(response, HttpStatus.I_AM_A_TEAPOT);
    }

    @PutMapping("/{spentPointsRequestId}/response")
    public ResponseEntity<SpentPointsRequestResponse> createSpentPointsRequestResponse(@PathVariable String spentPointsRequestId, @RequestBody SpentPointsRequestResponse spentPointsRequestResponse){
        SpentPointsRequestResponse response = null;
        if(spentPointsRequestId != null && spentPointsRequestResponse != null){
            response = spentPointsRequestService.createSpentPointsRequestResponse(spentPointsRequestId, spentPointsRequestResponse);
            return new ResponseEntity<SpentPointsRequestResponse>(response, HttpStatus.OK);
        }
        return new ResponseEntity<SpentPointsRequestResponse>(response, HttpStatus.I_AM_A_TEAPOT);
    }

    @GetMapping("/{userId}")
    public @ResponseBody ResponseEntity<List<SpentPointsRequest>> getAllSpentPointsRequestsByUserId(@PathVariable String userId){
        List<SpentPointsRequest> spentPointsRequestList = null;
        // userId should be the id of the logged user
        if(userId != null){
//            spentPointsRequestList = spentPointsRequestService.getAllSpentPointsRequestsByUserId(userId);
            return new ResponseEntity<List<SpentPointsRequest>>(spentPointsRequestList, HttpStatus.OK);
        }
        return new ResponseEntity<List<SpentPointsRequest>>(spentPointsRequestList, HttpStatus.NOT_FOUND);
    }
}
