package com.dep.gamification.controllers;

import com.dep.gamification.models.EarnedPointsReason;
import com.dep.gamification.services.EarnedPointsReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/earnedpointsreason")
public class EarnedPointsReasonController {
    @Autowired
    private EarnedPointsReasonService earnedPointsReasonService;

    @PostMapping("/")
    public ResponseEntity<EarnedPointsReason> addEarnedReason(@RequestBody EarnedPointsReason reason) {
        EarnedPointsReason response = null;
        if (reason != null) {
            response = earnedPointsReasonService.addEarnedReason(reason);
            return new ResponseEntity<EarnedPointsReason>(reason, HttpStatus.CREATED);
        }
        return new ResponseEntity<EarnedPointsReason>(response, HttpStatus.I_AM_A_TEAPOT);
    }

    @CrossOrigin( origins = "http://localhost:4200")
    @GetMapping("/")
    public ResponseEntity<List<String>> getAllEarnedPointsReasons() {
        List<String> reasons = earnedPointsReasonService.getAllEarnedPointsReasons();
        if (reasons != null) {
            return new ResponseEntity<>(reasons, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
