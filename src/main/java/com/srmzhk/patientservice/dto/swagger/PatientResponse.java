package com.srmzhk.patientservice.dto.swagger;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.srmzhk.patientservice.util.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Schema(description = "Response for patient CRU operations ")
@Data
public class PatientResponse {

    @Schema(description = "UUID for patient", example = "d281e67a-1f88-4175-92c7-a4dda6f63ef9")
    private UUID id;

    @Schema(description = "Patient full name", example = "Иванов Иван Иванович")
    private String name;

    @Schema(description = "Patient gender", example = "male")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Schema(description = "Patient birthday date in special format", example = "1980-03-25T13:06:46")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime birthDate;
}
