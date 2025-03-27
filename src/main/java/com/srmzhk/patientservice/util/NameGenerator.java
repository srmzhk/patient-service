package com.srmzhk.patientservice.util;

import java.util.Random;

// generate names for PatientGenerator
public class NameGenerator {

    private static final Random rand = new Random();

    private static final String[] LAST_NAMES = {
            "Иванов","Петров", "Сидоров", "Кузнецов", "Смирнов", "Попов", "Васильев",
            "Михайлов", "Новиков", "Фёдоров", "Ободов", "Панасенко", "Шульга", "Богданов"
    };

    private static final String[] FIRST_NAMES = {
            "Иван", "Пётр", "Сергей", "Александр", "Дмитрий", "Андрей", "Николай", "Михаил",
            "Владимир", "Артём", "Тимофей"
    };

    private static final String[] PATRONYMICS = {
            "Иванович", "Петрович", "Сергеевич", "Александрович", "Дмитриевич", "Андреевич", "Николаевич",
            "Михайлович", "Владимирович", "Артёмович"
    };

    public static String generateFullName() {
        String lastName = LAST_NAMES[rand.nextInt(LAST_NAMES.length)];
        String firstName = FIRST_NAMES[rand.nextInt(FIRST_NAMES.length)];
        String patronymic = PATRONYMICS[rand.nextInt(PATRONYMICS.length)];
        return lastName + " " + firstName + " " + patronymic;
    }
}
