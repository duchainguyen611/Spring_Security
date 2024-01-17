package com.ra.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class UserLogin {
    @NotNull(message = "not Null")
    @NotEmpty(message = "not Empty")
    @NotBlank(message = "not Blank")
    private String username;
    @NotNull(message = "not Null")
    @NotEmpty(message = "not Empty")
    @NotBlank(message = "not Blank")
    private String password;
}
