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
        this.currentDate = currentDate;
    }

    public LocalDateTime getCurrentDateTime() {
        return currentDateTime;
    }

    public void setCurrentDateTime(LocalDateTime currentDateTime) {
        this.currentDateTime = currentDateTime;
    }

    public int getHatchedEggs() {
        return hatchedEggs;
    }

    public void setHatchedEggs(int hatchedEggs) {
        this.hatchedEggs = hatchedEggs;
    }

    public int getDeadChicken() {
        return deadChicken;
    }

    public void setDeadChicken(int deadChicken) {
        this.deadChicken = deadChicken;
    }

    public int getDiscardedEggs() {
        return discardedEggs;
    }

    public void setDiscardedEggs(int discardedEggs) {
        this.discardedEggs = discardedEggs;
    }

    public int getDiscardedChicken() {
        return discardedChicken;
    }

    public void setDiscardedChicken(int discardedChicken) {
        this.discardedChicken = discardedChicken;
    }
}
