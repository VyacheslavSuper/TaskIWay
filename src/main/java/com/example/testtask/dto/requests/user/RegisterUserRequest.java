package com.example.testtask.dto.requests.user;

import com.example.testtask.dto.requests.RequestBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RegisterUserRequest implements RequestBase {
    @NotNull
    @NotBlank
    @Size(min = 4, max = 20)
    private String login;
}
