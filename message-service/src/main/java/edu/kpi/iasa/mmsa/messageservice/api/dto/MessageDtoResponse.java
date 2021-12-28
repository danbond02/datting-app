package edu.kpi.iasa.mmsa.messageservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@Data
public class MessageDtoResponse {

    private Date date;
    private Time time;
    private String senderName;
    private String receiverName;
    private String text;
}
