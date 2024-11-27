package edu.unc.cs.BookSwap.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailId;


}
