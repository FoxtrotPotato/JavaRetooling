package com.foxtrotpotato.chickentest.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class GlobalData {
    public static LocalDate currentDate = LocalDate.now();
    public static LocalDateTime currentDateTime = LocalDateTime.now();
    public static int hatchedEggs;
    public static int deadChicken;
    public static int discardedEggs;
    public static int discardedChicken;


    public LocalDate getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(LocalDate currentDate) {
        GlobalData.currentDate = currentDate;
    }

    public LocalDateTime getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(LocalDateTime currentDateTime) {
        GlobalData.currentDateTime = currentDateTime;
    }

    public int getHatchedEggs() {
        return hatchedEggs;
    }

    public void setHatchedEggs(int hatchedEggs) {
        GlobalData.hatchedEggs = hatchedEggs;
    }

    public int getDeadChicken() {
        return deadChicken;
    }

    public void setDeadChicken(int deadChicken) {
        GlobalData.deadChicken = deadChicken;
    }

    public int getDiscardedEggs() {
        return discardedEggs;
    }

    public void setDiscardedEggs(int discardedEggs) {
        GlobalData.discardedEggs = discardedEggs;
    }

    public int getDiscardedChicken() {
        return discardedChicken;
    }

    public void setDiscardedChicken(int discardedChicken) {
        GlobalData.discardedChicken = discardedChicken;
    }
}
