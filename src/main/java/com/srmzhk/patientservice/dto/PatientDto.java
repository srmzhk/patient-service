package com.srmzhk.patientservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.srmzhk.patientservice.util.Gender;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {

    UUID id;

    String name;

    @Enumerated(EnumType.STRING)
    Gender gender;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    LocalDateTime birthDate;
}
