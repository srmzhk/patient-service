package com.srmzhk.patientservice.controller;

import com.srmzhk.patientservice.dto.PatientDto;
import com.srmzhk.patientservice.service.IPatientService;
import com.srmzhk.patientservice.util.NameValidator;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@Tag(name = "Patients", description = "API for managing patient data")
@RequiredArgsConstructor
public class PatientController {

    private final IPatientService patientService;
    private final NameValidator nameValidator;

    @Operation(
            summary = "Get all patients",
            description = "Returns a list of all registered patients."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of patients retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = PatientDto.class),
                            examples = @ExampleObject(value =
                                    "{\"id\":\"d281e67a-1f88-4175-92c7-a4dda6f63ef9\"," +
                                    " \"name\":\"Иванов Иван Иванович\"," +
                                    " \"gender\":\"male\"," +
                                    " \"birthDate\":\"1980-03-25T13:06:46\"}")
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(value = "Internal server error: ...")
                    )
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("/all")
    @PreAuthorize("hasRole('Practitioner') and hasAuthority('Patient.Read')")
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        List<PatientDto> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patients);
    }

    @Operation(
            summary = "Get a patient by ID",
            description = "Returns the patient information based on the provided patient ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient retrieved successfully",
                    content = @Content(
                            schema = @Schema(implementation = PatientDto.class),
                            examples = @ExampleObject(value =
                                    "{\"id\":\"d281e67a-1f88-4175-92c7-a4dda6f63ef9\"," +
                                    " \"name\":\"Иванов Иван Иванович\"," +
                                    " \"gender\":\"male\"," +
                                    " \"birthDate\":\"1980-03-25T13:06:46\"}")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content(
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(value = "Item not found: ...")
                    )),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(value = "Internal server error: ...")
                    )
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    @GetMapping("{id}")
    @PreAuthorize("hasRole('Patient') and hasAuthority('Patient.Read')")
    public ResponseEntity<PatientDto> getPatientById(
            @Parameter(description = "UUID of the patient to retrieve", required = true)
            @PathVariable UUID id) {
        PatientDto patient = patientService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @Operation(
        summary = "Create a new patient",
        description = "Creates a new patient record using the provided patient data.",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Patient data to create a new record",
                required = true,
                content = @Content(
                        examples = @ExampleObject(value =
                                "{\"name\": \"Иванов Иван Иванович\", " +
                                "\"gender\": \"male\", " +
                                "\"birthDate\": \"1980-03-25T13:06:46\"}")
                )
        ),
        responses = {
                @ApiResponse(responseCode = "200", description = "Patient created successfully",
                        content = @Content(
                                schema = @Schema(implementation = PatientDto.class),
                                examples = @ExampleObject(value =
                                        "{\"id\":\"d281e67a-1f88-4175-92c7-a4dda6f63ef9\"," +
                                        " \"name\":\"Иванов Иван Иванович\"," +
                                        " \"gender\":\"male\"," +
                                        " \"birthDate\":\"1980-03-25T13:06:46\"}")
                        )
                ),
                @ApiResponse(responseCode = "400", description = "Invalid input data",
                        content = @Content(
                                schema = @Schema(type = "string"),
                                examples = @ExampleObject(value = "Invalid input data: ...")
                        )),
                @ApiResponse(responseCode = "500", description = "Internal server error",
                        content = @Content(
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(value = "Internal server error: ...")
                        )
                )
        }
    )
    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/create")
    @PreAuthorize("hasRole('Practitioner') and hasAuthority('Patient.Write')")
    public ResponseEntity<PatientDto> createPatient(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Patient data to create a new record",
                    required = true
            )
            @RequestBody PatientDto patientDto) {

        // validate input name
        nameValidator.isValidName(patientDto.getName());

        PatientDto patient = patientService.createPatient(patientDto);
        return ResponseEntity.ok(patient);
    }

    @Operation(
            summary = "Update patient information",
            description = "Updates an existing patient record with the provided data.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Patient data to update an existing record",
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(value =
                                    "{\"id\":\"d281e67a-1f88-4175-92c7-a4dda6f63ef9\"," +
                                    " \"name\":\"Иванов Иван Иванович\"," +
                                    " \"gender\":\"male\"," +
                                    " \"birthDate\":\"1980-03-25T13:06:46\"}")
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient updated successfully",
                    content = @Content(
                            schema = @Schema(implementation = PatientDto.class),
                            examples = @ExampleObject(value =
                                    "{\"id\":\"d281e67a-1f88-4175-92c7-a4dda6f63ef9\"," +
                                    " \"name\":\"Иванов Иван Иванович\"," +
                                    " \"gender\":\"male\"," +
                                    " \"birthDate\":\"1980-03-25T13:06:46\"}")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content(
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(value = "Item not found: ...")
                    )),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(value = "Internal server error: ...")
                    )
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/update")
    @PreAuthorize("hasRole('Practitioner') and hasAuthority('Patient.Write')")
    public ResponseEntity<PatientDto> updatePatient(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Updated patient data",
                    required = true
            )
            @RequestBody PatientDto patientDto) {

        nameValidator.isValidName(patientDto.getName());

        PatientDto patient = patientService.updatePatient(patientDto);
        return ResponseEntity.ok(patient);
    }

    @Operation(
            summary = "Delete a patient",
            description = "Deletes a patient record based on the ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Patient deleted successfully",
                    content = @Content(
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(value = "Patient deleted successfully")
                    )),
            @ApiResponse(responseCode = "404", description = "Item not found",
                    content = @Content(
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(value = "Item not found: ...")
                    )),
            @ApiResponse(responseCode = "500", description = "Internal server error",
                    content = @Content(
                            schema = @Schema(type = "string"),
                            examples = @ExampleObject(value = "Internal server error: ...")
                    )
            )
    })
    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('Practitioner') and hasAuthority('Patient.Delete')")
    public ResponseEntity<String> deletePatient(
            @Parameter(description = "UUID of the patient to delete", required = true)
            @PathVariable UUID id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok("Success");
    }

    // using for generating 100 new Patients throw console PatientGenerator without authorization
    @Hidden
    @PutMapping("/generate")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<PatientDto> generatePatients(@RequestBody PatientDto patientDto) {
        PatientDto patient = patientService.createPatient(patientDto);
        return ResponseEntity.ok(patient);
    }
}
