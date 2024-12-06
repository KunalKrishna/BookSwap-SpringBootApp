package edu.unc.cs.BookSwap.dto;


import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty.")
    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Invalid email format",
            regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    @NotEmpty(message = "Email should not be empty!")
    private String email;

    //@NotEmpty(message = "Password should not be empty!")
    @Size(min = 6, message = "Password must be at least have 6 characters!!!")
    private String password;
}
