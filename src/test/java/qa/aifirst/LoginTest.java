package qa.aifirst;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.cssClass;
import static com.codeborne.selenide.Condition.selected;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class LoginTest extends TestBase {

    @Test
    @DisplayName("Успешный вход с валидными учётными данными")
    void successfulLogin() {
        open("https://dvyguzov.github.io/ai-first-simple/index.html");
        $("#username").setValue("admin");
        $("#password").setValue("admin123");
        $("button[type=submit]").click();
        $("#message").shouldHave(text("Вход выполнен успешно"));
    }

    @Test
    @DisplayName("Неверный пароль не пускает в систему и показывает ошибку")
    void loginWithWrongPassword() {
        open("https://dvyguzov.github.io/ai-first-simple/index.html");
        $("#username").setValue("admin");
        $("#password").setValue("wrong-password");
        $("button[type=submit]").click();
        $("#message").shouldHave(text("Неверное имя пользователя или пароль"));
    }

    @Test
    @DisplayName("Пустые логин и пароль показывают ошибку")
    void emptyCredentialsShowError() {
        open("https://dvyguzov.github.io/ai-first-simple/index.html");
        $("button[type=submit]").click();
        $("#message").shouldBe(visible).shouldHave(cssClass("error"));
    }

    @Test
    @DisplayName("Пустой пароль при введённом логине показывает ошибку")
    void emptyPasswordShowsError() {
        open("https://dvyguzov.github.io/ai-first-simple/index.html");
        $("#username").setValue("admin");
        $("button[type=submit]").click();
        $("#message").shouldBe(visible).shouldHave(cssClass("error"));
    }

    @Test
    @DisplayName("Пустой логин при введённом пароле показывает ошибку")
    void emptyUsernameShowsError() {
        open("https://dvyguzov.github.io/ai-first-simple/index.html");
        $("#password").setValue("admin123");
        $("button[type=submit]").click();
        $("#message").shouldBe(visible).shouldHave(cssClass("error"));
    }

    @Test
    @DisplayName("Пустые логин и пароль показывают своё сообщение об ошибке")
    void emptyCredentialsShowOwnMessage() {
        open("https://dvyguzov.github.io/ai-first-simple/index.html");
        $("button[type=submit]").click();
        $("#message").shouldHave(text("Введите имя пользователя и пароль"));
    }

    @Test
    @DisplayName("Пустой пароль при введённом логине показывает своё сообщение об ошибке")
    void emptyPasswordShowsOwnMessage() {
        open("https://dvyguzov.github.io/ai-first-simple/index.html");
        $("#username").setValue("admin");
        $("button[type=submit]").click();
        $("#message").shouldHave(text("Введите пароль"));
    }

    @Test
    @DisplayName("Пустой логин при введённом пароле показывает своё сообщение об ошибке")
    void emptyUsernameShowsOwnMessage() {
        open("https://dvyguzov.github.io/ai-first-simple/index.html");
        $("#password").setValue("admin123");
        $("button[type=submit]").click();
        $("#message").shouldHave(text("Введите имя пользователя"));
    }

    @Test
    @DisplayName("Чекбокс «Запомнить меня» есть на форме")
    void rememberMeCheckboxExists() {
        open("https://dvyguzov.github.io/ai-first-simple/index.html");
        $("#remember-me").shouldBe(visible);
    }

    @Test
    @DisplayName("Чекбокс «Запомнить меня» переключается кликом")
    void rememberMeCheckboxToggles() {
        open("https://dvyguzov.github.io/ai-first-simple/index.html");
        SelenideElement checkbox = $("#remember-me");
        checkbox.shouldNotBe(selected);
        checkbox.click();
        checkbox.shouldBe(selected);
        checkbox.click();
        checkbox.shouldNotBe(selected);
    }
}