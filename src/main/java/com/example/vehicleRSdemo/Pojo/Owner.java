package com.example.vehicleRSdemo.Pojo;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
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

    public Owner() {
    }

    public Owner(Integer ownerId, String firstName, String lastName,
                 LocalDate dateOfBirth, String placeOfBirth, Gender gender, LocalDate licenseIssueDate) {
        this.ownerId = ownerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.placeOfBirth = placeOfBirth;
        this.gender = gender;
        this.licenseIssueDate = licenseIssueDate;
    }

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getLicenseIssueDate() {
        return licenseIssueDate;
    }

    public void setLicenseIssueDate(LocalDate licenseIssueDate) {
        this.licenseIssueDate = licenseIssueDate;
    }

    @Override
    public String toString() {
        return "Owner{" +
                "SSN=" + ownerId +
                ", First Name='" + firstName + '\'' +
                ", Last Name='" + lastName + '\'' +
                ", Date Of Birth=" + dateOfBirth +
                ", Place Of Birth='" + placeOfBirth + '\'' +
                ", Gender='" + gender + '\'' +
                ", License Issue Date=" + licenseIssueDate +
                '}';
    }
}
