package qa.aifirst;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class LoginTest extends TestBase {

    @Test
    @DisplayName("Успешный вход с валидными учётными данными")
    void successfulLogin() {
        open("https://svasenkov.github.io/ai-first-simple/index.html");
        $("#username").setValue("admin");
        $("#password").setValue("admin123");
        $("button[type=submit]").click();
        $("#message").shouldHave(text("Вход выполнен успешно"));
    }

    @Test
    @DisplayName("Неверный пароль не пускает в систему и показывает ошибку")
    void loginWithWrongPassword() {
        open("https://svasenkov.github.io/ai-first-simple/index.html");
        $("#username").setValue("admin");
        $("#password").setValue("wrong-password");
        $("button[type=submit]").click();
        $("#message").shouldHave(text("Неверное имя пользователя или пароль"));
    }

    @Test
    @DisplayName("Пустые логин и пароль показывают ошибку")
    void loginWithEmptyCredentials() {
        open("https://svasenkov.github.io/ai-first-simple/index.html");
        $("button[type=submit]").click();
        $("#message").shouldHave(text("Неверное имя пользователя или пароль"));
    }

    @Test
    @DisplayName("Пустой пароль при введённом логине показывает ошибку")
    void loginWithoutPassword() {
        open("https://svasenkov.github.io/ai-first-simple/index.html");
        $("#username").setValue("admin");
        $("button[type=submit]").click();
        $("#message").shouldHave(text("Неверное имя пользователя или пароль"));
    }

    @Test
    @DisplayName("Пустой логин при введённом пароле показывает ошибку")
    void loginWithoutUsername() {
        open("https://svasenkov.github.io/ai-first-simple/index.html");
        $("#password").setValue("admin123");
        $("button[type=submit]").click();
        $("#message").shouldHave(text("Неверное имя пользователя или пароль"));
    }
}
