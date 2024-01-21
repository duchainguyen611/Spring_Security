package com.ra.model.dto.request;

import jakarta.persistence.Column;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserToUpdatePassword {
    String oldPassword;
    String newPassword;
    String newPasswordConfirm;
}
