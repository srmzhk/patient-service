package com.srmzhk.patientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredentialsDto {
    String username;
    String password;
    String clientId;
    String clientSecret;
}
