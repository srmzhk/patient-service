package com.srmzhk.patientservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.srmzhk.patientservice.model.Patient;
import com.srmzhk.patientservice.util.Gender;
import com.srmzhk.patientservice.util.NameGenerator;
import lombok.extern.slf4j.Slf4j;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

@Slf4j
public class PatientGenerator { // generate 100 new Patients and adding them to DB throw PatientController

    private static final String API_URL = "http://localhost:8083/patients/generate";
    private static final String KEYCLOAK_URL =
            "http://keycloak:8080/realms/OAuth/protocol/openid-connect/token";
    // using for build request to your realm to get JWT for admin
    private static final String KEYCLOAK_BODY =
            "client_id=myclient&" +
                    "username=admin&" +
                    "password=admin&" +
                    "grant_type=password&" +
                    "client_secret=qDtt8RI8IKPLNeqcrnx0sAgwOiqO1TZl";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static void main(String[] args) {
        String token = getAdminToken();
        for (int i = 0; i < 100; i++) {
            try {
                Patient patient = generateRandomPatient();
                String requestBody = objectMapper.writeValueAsString(patient);

                // build request with Authorization token from keycloak for PatientController
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL))
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Bearer " + token)
                        .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200)
                    log.info("Patient added: {}", response.body());
                else
                    log.info("Failed to add patient: {}", response.body());
            } catch (Exception ex) {
                log.error("Exception occurred in Class: PatientGenerator | Method: main | Exception: {}",
                        ex.getStackTrace());
            }
        }
    }

    // fill all values to Patient unless UUID
    private static Patient generateRandomPatient() {
        String name = NameGenerator.generateFullName();

        // generate various gender, but there are only men in test data
        // Gender gender = Math.random() < 0.5 ? Gender.male : Gender.female;
        Gender gender =  Gender.male;
        LocalDateTime birthDate = LocalDateTime.now().minusYears((long) (Math.random() * 70));

        return new Patient(null, name, gender, birthDate);
    }

    // get jwt token from keycloak for authorization while using generatePatient in API
    private static String getAdminToken() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(KEYCLOAK_URL))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(KEYCLOAK_BODY))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                var jsonNode = objectMapper.readTree(response.body());
                return jsonNode.get("access_token").asText();
            } else
                throw new RuntimeException("Failed to get admin token: " + response.body());
        } catch (Exception ex) {
            log.error("Exception occurred in Class: PatientGenerator | Method: getAdminToken | Exception: {}",
                    ex.getStackTrace());
            return null;
        }
    }
}