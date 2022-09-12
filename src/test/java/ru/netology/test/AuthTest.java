package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import io.restassured.internal.common.assertion.Assertion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.netology.data.RegistrationDto;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.data.DataGenerator.Registration.getUser;
import static ru.netology.data.DataGenerator.getRandomLogin;
import static ru.netology.data.DataGenerator.getRandomPassword;

class AuthTest {

    @BeforeEach
    void setup() {

        open("http://localhost:9999");
        Configuration.holdBrowserOpen = true;
    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        RegistrationDto registeredUser = getRegisteredUser("active");
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login'] span.button__text").click();
        $("h2").shouldHave(Condition.exactText("  Личный кабинет"));
    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        RegistrationDto notRegisteredUser = getUser("active");
        $("[data-test-id='login'] input").setValue(notRegisteredUser.getLogin());
        $("[data-test-id='password'] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login'] span.button__text").click();
        String actual = $("div [data-test-id='error-notification'] div.notification__content").getText();
        String expected = "Неверно указан логин или пароль";
        actual.equals(expected);
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        RegistrationDto blockedUser = getRegisteredUser("blocked");
        $("[data-test-id='login'] input").setValue(blockedUser.getLogin());
        $("[data-test-id='password'] input").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login'] span.button__text").click();
        $("div [data-test-id='error-notification']").shouldBe(Condition.visible);
        String actual = $("div [data-test-id='error-notification'] div.notification__content").getText();
        String expected = "Пользователь заблокирован";
        actual.equals(expected);
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        RegistrationDto registeredUser = getRegisteredUser("active");
        String wrongLogin = getRandomLogin();
        $("[data-test-id='login'] input").setValue(wrongLogin);
        $("[data-test-id='password'] input").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login'] span.button__text").click();
        String actual = $("div [data-test-id='error-notification'] div.notification__content").getText();
        String expected = "Неверно указан логин или пароль";
        actual.equals(expected);
    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        RegistrationDto registeredUser = getRegisteredUser("active");
        String wrongPassword = getRandomPassword();
        $("[data-test-id='login'] input").setValue(registeredUser.getLogin());
        $("[data-test-id='password'] input").setValue(wrongPassword);
        $("[data-test-id='action-login'] span.button__text").click();
        String actual = $("div [data-test-id='error-notification'] div.notification__content").getText();
        String expected = "Неверно указан логин или пароль";
        actual.equals(expected);
    }
}