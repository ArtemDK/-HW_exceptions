import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные в формате: Фамилия Имя Отчество датарождения номертелефона пол");
        String input = scanner.nextLine();
        String[] data = input.split(" ");
        if (data.length != 6) {
            System.out.println("Ошибка: неверное количество данных");
            return;
        }
        String lastName = data[0];
        String firstName = data[1];
        String middleName = data[2];
        String birthDateStr = data[3];
        String phoneNumberStr = data[4];
        String genderStr = data[5];
        LocalDate birthDate;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            birthDate = LocalDate.parse(birthDateStr, formatter);
        } catch (Exception e) {
            System.out.println("Ошибка: неверный формат даты рождения");
            return;
        }
        long phoneNumber;
        try {
            phoneNumber = Long.parseLong(phoneNumberStr);
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: неверный формат номера телефона");
            return;
        }
        Gender gender;
        try {
            gender = Gender.valueOf(genderStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: неверный формат пола");
            return;
        }
        Person person = new Person(lastName, firstName, middleName, birthDate, phoneNumber, gender);
        try {
            writeToFile(person);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(Person person) throws IOException {
        String fileName = person.getLastName() + ".txt";
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
        writer.write(person.toString());
        writer.newLine();
        writer.close();
        System.out.println("Данные успешно записаны в файл " + fileName);
    }

    private enum Gender {
        M, F
    }

    private static class Person {
        private String lastName;
        private String firstName;
        private String middleName;
        private LocalDate birthDate;
        private long phoneNumber;
        private Gender gender;

        public Person(String lastName, String firstName, String middleName, LocalDate birthDate, long phoneNumber, Gender gender) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.middleName = middleName;
            this.birthDate = birthDate;
            this.phoneNumber = phoneNumber;
            this.gender = gender;
        }

        public String getLastName() {
            return lastName;
        }

        @Override
        public String toString() {
            return lastName + firstName + middleName + birthDate + " " + phoneNumber + gender;
        }
    }
}
