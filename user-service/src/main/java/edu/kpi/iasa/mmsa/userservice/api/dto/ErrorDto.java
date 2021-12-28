package edu.kpi.iasa.mmsa.userservice.api.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDto {

    String code;
    String description;
}
