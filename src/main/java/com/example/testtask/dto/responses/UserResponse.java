package com.example.testtask.dto.responses;

import com.example.testtask.dto.ResponseBase;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponse implements ResponseBase {
    private Long id;
    private String login;
}
