package edu.kpi.iasa.mmsa.userservice.api.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

    private String username;
    private String name;
    private String lastName;
    private String firstName;
    private Integer age;
    private String hobby;
    private String occupation;
    private String city;
    private String country;
    private String sex;
    private String orientation;
    private String religion;
    private String education;
    private String phoneNumber;
    private String email;
    private String status;
}
