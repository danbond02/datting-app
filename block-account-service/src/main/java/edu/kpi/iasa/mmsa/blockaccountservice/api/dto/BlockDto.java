package edu.kpi.iasa.mmsa.blockaccountservice.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlockDto {

    private Long userId;
    private Long blockedUserId;
}
