package edu.kpi.iasa.mmsa.messageservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class MessageDtoRequest {

    private Date date;
    private Time time;
    private Long senderId;
    private Long receiverId;
    private String text;
}
