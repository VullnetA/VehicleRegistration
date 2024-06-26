package com.example.vehicleRSdemo.Pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "owner")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ownerid")
    private Integer ownerId;
    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    @Column(name = "dateofbirth")
    private LocalDate dateOfBirth;
    @Column(name = "placeofbirth")
    private String placeOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(name = "licensedate")
    private LocalDate licenseIssueDate;

    public Owner () {

    }
}
