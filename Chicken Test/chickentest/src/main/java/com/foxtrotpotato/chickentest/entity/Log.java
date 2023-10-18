package com.foxtrotpotato.chickentest.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "log_id")
    private int logId;

    @Column(name = "log_detail")
    private String logDetail;

    @Column(name = "log_timestamp")
    private LocalDateTime logTimestamp;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "farm_id")
    private Farm farm;

    public Log() {
    }

    public Log(String logDetail, LocalDateTime logTimestamp, User user, Farm farm) {
        this.logDetail = logDetail;
        this.logTimestamp = logTimestamp;
        this.user = user;
        this.farm = farm;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public String getLogDetail() {
        return logDetail;
    }

    public void setLogDetail(String logDetail) {
        this.logDetail = logDetail;
    }

    public LocalDateTime getLogTimestamp() {
        return logTimestamp;
    }

    public void setLogTimestamp(LocalDateTime logTimestamp) {
        this.logTimestamp = logTimestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public Farm getFarm() {
        return farm;
    }

    public void setFarm(Farm farm) {
        this.farm = farm;
    }

    @Override
    public String toString() {
        return "Log{" +
                "logId=" + logId +
                ", logDetail='" + logDetail + '\'' +
                ", logTimestamp=" + logTimestamp +
                ", user=" + user +
                '}';
    }

}
