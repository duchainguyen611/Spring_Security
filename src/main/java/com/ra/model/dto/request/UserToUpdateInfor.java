package com.ra.model.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserToUpdateInfor {
    private String fullName;
    private String Email;
    private String avatar;
    private String phone;
    private String address;
}
