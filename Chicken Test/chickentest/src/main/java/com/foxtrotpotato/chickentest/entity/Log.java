package com.foxtrotpotato.chickentest.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="logs")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="log_id")
    private int logId;

    @Column(name="log_detail")
    private String logDetail;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="log_timestamp")
    private Timestamp logTimestamp;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    public Log(){}

    public Log(String logDetail, Timestamp logTimestamp) {
        this.logDetail = logDetail;
        this.logTimestamp = logTimestamp;
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

    public Timestamp getLogTimestamp() {
        return logTimestamp;
    }

    public void setLogTimestamp(Timestamp logTimestamp) {
        this.logTimestamp = logTimestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
