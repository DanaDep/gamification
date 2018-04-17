package com.dep.gamification.controllers;

import com.dep.gamification.models.SpentPointsReason;
import com.dep.gamification.services.SpentPointsReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/spentpointsreason")
public class SpentPointsReasonController {

    @Autowired
    public SpentPointsReasonService spentPointsReasonService;

    @PostMapping("/")
    public ResponseEntity<SpentPointsReason> addEarnedReason(@RequestBody SpentPointsReason reason) {
        SpentPointsReason response = null;
        if (reason != null) {
            response = spentPointsReasonService.addSpentReason(reason);
            return new ResponseEntity<SpentPointsReason>(reason, HttpStatus.CREATED);
        }
        return new ResponseEntity<SpentPointsReason>(response, HttpStatus.I_AM_A_TEAPOT);
    }

    @CrossOrigin( origins = "http://localhost:4200")
    @GetMapping("/")
    public ResponseEntity<List<String>> getAllEarnedPointsReasons() {
        List<String> reasons = spentPointsReasonService.getAllEarnedPointsReasons();
        if (reasons != null) {
            return new ResponseEntity<>(reasons, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
