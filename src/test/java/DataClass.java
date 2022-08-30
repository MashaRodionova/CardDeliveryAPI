import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class DataClass {

    private String login;
    private String password;
    private String status;

    public DataClass() {
        this.login = generateLogin();
        this.password = generatePassword();
        this.status = generateStatus();
    }

    public static String generateLogin() {
        Faker faker = new Faker(new Locale("ru"));
        String login = faker.name().firstName();
        return login;
    }

    public static String generatePassword() {
        Faker faker = new Faker(new Locale("eng"));
        String password = faker.name().firstName();
        return password;
    }

    public static String generateStatus() {
        String[] statuses = {"active", "blocked"};
        Random randomStatuses = new Random();
        int indexOfCity = randomStatuses.nextInt(statuses.length);
        String status = statuses[indexOfCity];
        return status;
    }
}

