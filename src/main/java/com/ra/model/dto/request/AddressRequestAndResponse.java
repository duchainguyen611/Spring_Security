package com.ra.model.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddressRequestAndResponse {
    private String fullAddress;
    private String phone;
    private String receiveName;
}
