package com.srmzhk.patientservice.service.impl;

import com.srmzhk.patientservice.dto.PatientDto;
import com.srmzhk.patientservice.exception.ItemNotFoundException;
import com.srmzhk.patientservice.model.Patient;
import com.srmzhk.patientservice.repository.PatientRepository;
import com.srmzhk.patientservice.service.IPatientService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements IPatientService {

    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    @Override
    public PatientDto getPatientById(UUID id) {
        Optional<Patient> patient = patientRepository.findPatientById(id);
        if (patient.isEmpty())
            throw new ItemNotFoundException("Patient with id: %s not found in DB.".formatted(id));

        return modelMapper.map(patient, PatientDto.class);
    }

    @Override
    public List<PatientDto> getAllPatients() {
        List<Patient> patients = patientRepository.findAll();
        if (patients.isEmpty())
            throw new ItemNotFoundException("Patient table is empty.");

        return patients
                .stream()
                .map(patient -> modelMapper.map(patient, PatientDto.class))
                .toList();
    }

    @Override
    @Transactional
    public PatientDto createPatient(PatientDto patientDto) {
        Patient patient;
        try {
            patient = modelMapper.map(patientDto, Patient.class);
            patient = patientRepository.save(patient);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return modelMapper.map(patient, PatientDto.class);
    }

    @Override
    @Transactional
    public PatientDto updatePatient(PatientDto patientDto) {
        Patient patient;
        try {
            patient = patientRepository.findPatientById(patientDto.getId())
                    .orElseThrow(ItemNotFoundException::new);

            patient.setBirthDate(patientDto.getBirthDate());
            patient.setGender(patientDto.getGender());
            patient.setName(patientDto.getName());
            patient = patientRepository.save(patient);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return modelMapper.map(patient, PatientDto.class);
    }

    @Override
    @Transactional
    public void deletePatient(UUID id) {
        Optional<Patient> patient = patientRepository.findPatientById(id);
        if (patient.isEmpty())
            throw new ItemNotFoundException("Error while deleting. Patient with id: %s not found in DB."
                    .formatted(id));

        patientRepository.deleteById(id);
    }
}
