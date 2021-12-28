package edu.kpi.iasa.mmsa.blockaccountservice.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BlockedUserDto {

    private Long id;
    private String username;
    private String name;
    private String firstName;
    private Integer age;
    private String city;
    private String country;
    private String sex;
    private String orientation;
}
