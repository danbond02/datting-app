package edu.kpi.iasa.mmsa.messageservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {

    private Long firstParticipantId;
    private Long secondParticipantId;
}
