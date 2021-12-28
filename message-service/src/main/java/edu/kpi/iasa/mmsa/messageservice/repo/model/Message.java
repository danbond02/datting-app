package edu.kpi.iasa.mmsa.messageservice.repo.model;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;


@Entity
@Table(name="messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="date")
    private Date date;

    @Column(name="time")
    private Time time;

    @Column(name="sender_id")
    private Long senderId;

    @Column(name="receiver_id")
    private Long receiverId;

    @Column(name="text")
    private String text;

    public Message() {}

    public Message(Date date, Time time, Long senderId, Long receiverId, String text) {
        this.date = date;
        this.time = time;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
    }
}
