package com.srmzhk.patientservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError {

    @Schema(description = "Time", example = "2024-04-25T13:01:43.664+00:00")
    private Timestamp timestamp;

    @Schema(description = "Status code", example = "500")
    private int status;

    @Schema(description = "Статус текст", example = "Bad Request")
    private String error;

    @Schema(description = "Error", example = "Failed to load SpringContext")
    private String message;

    @Schema(description = "Path", example = "http://localhost:8083")
    private String path;
}
