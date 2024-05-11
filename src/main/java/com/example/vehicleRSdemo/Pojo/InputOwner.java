package com.example.vehicleRSdemo.Pojo;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class InputOwner {
    private Integer ownerId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String placeOfBirth;
    private Gender gender;
    private LocalDate licenseIssueDate;
}
