package com.srmzhk.patientservice.service;

import com.srmzhk.patientservice.dto.PatientDto;

import java.util.List;
import java.util.UUID;

public interface IPatientService {
    PatientDto getPatientById(UUID id);
    List<PatientDto> getAllPatients();
    PatientDto createPatient(PatientDto patientDto);
    PatientDto updatePatient(PatientDto patientDto);
    void deletePatient(UUID id);
}
