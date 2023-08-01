package com.springboot.springbatch.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="CUSTOMER")
public class Customer {

    //id,firstName,lastName,email,gender,contactNo,country,dob

    @Id
    @Column(name="CUSTOMER_ID")
    private int id;

    @Column(name="First_Name")
    private String firstName;

    @Column(name="Last_name")
    private String lastName;

    @Column(name="email")
    private String emailId;

    @Column(name = "gender")
    private String gender;

    @Column(name="contactNo")
    private String contactNo;

    @Column(name="country")
    private String country;

    @Column(name="Date_of_birth")
    private String dob;

}
