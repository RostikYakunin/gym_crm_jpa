package com.crm.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class UserDto {
    private long id;

    @NotBlank(message = "First name is mandatory")
    @Size(min = 2, max = 255, message = "First name must be between 2 and 255 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "First name must contain only letters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(min = 2, max = 255, message = "Last name must be between 2 and 255 characters")
    @Pattern(regexp = "^[A-Za-z]+$", message = "Last name must contain only letters")
    private String lastName;

    @NotBlank(message = "Username is mandatory")
    @Size(min = 5, max = 255, message = "Username must be between 5 and 255 characters")
    @Pattern(regexp = "^[a-zA-Z\\d._-]+$", message = "Username can only contain letters, numbers, dots, underscores, and dashes")
    private String username;

    @NotBlank(message = "Password is mandatory")
    @Size(min = 8, max = 10, message = "Password must be between 8 and 10 characters")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).*$",
            message = "Password must contain at least one uppercase letter, one lowercase letter, and one number"
    )
    private String password;

    @NotNull(message = "Active status is mandatory")
    private Boolean isActive;
}
