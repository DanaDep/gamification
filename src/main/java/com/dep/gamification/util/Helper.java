package com.dep.gamification.util;

public class Helper {

    public static boolean comparePoints(int totalEarnedPoints, int totalSpentPoints){
        if(totalEarnedPoints > totalSpentPoints){
            return true;
        }
        return false;
    }
}
