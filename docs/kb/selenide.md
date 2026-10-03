# Selenide: как устроены тесты

## Где лежат тесты и конфиг?

Тесты — в `src/test/java/qa/aifirst/` (`TestBase.java`, `LoginTest.java`).
Версии стека (Java, JUnit, Selenide, Allure) — в `build.gradle`
(`toolchain` и `dependencies`), здесь не дублируются.

## Зачем TestBase и что в нём?

`TestBase` — общий базовый класс: `LoginTest extends TestBase`.
В `@BeforeAll setUp()` две настройки:

- `Configuration.headless = true` — браузер без окна, тест работает
  на CI без дисплея;
- `SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true))` —
  лисенер прикрепляет скриншот к шагам в Allure (см. `allure.md`).

Не дублируй эту настройку в тестах — конвенция зафиксирована
в `.devin/rules/tests.md`.

## Откуда тест берёт страницу?

`open("https://dvyguzov.github.io/ai-first-simple/index.html")` —
тест открывает форму, опубликованную на GitHub Pages из форка
(ADR-0009). Локальные правки `index.html` прогон увидит только после
пуша в форк и перевыклада Pages: до этого тесты честно проверяют
старую страницу и остаются зелёными.

## Как выглядят шаги теста?

Selenide-стиль: `$("css")` находит элемент, `.setValue()/.click()` —
действия, `.shouldHave(text("..."))` — проверка. Весь сценарий
из `LoginTest.successfulLogin`:

```java
open("https://dvyguzov.github.io/ai-first-simple/index.html");
$("#username").setValue("admin");
$("#password").setValue("admin123");
$("button[type=submit]").click();
$("#message").shouldHave(text("Вход выполнен успешно"));
```

Локаторы и тексты берутся из `index.html` (`#username`, `#password`,
`#remember-me`, `button[type=submit]`, `#message`), валидные креды —
`admin`/`admin123`. Тексты ошибок проверяются дословно, список
актуальных — в ADR-0008.

## Почему цепочка из `click()` не собирается?

В Selenide 7.9.3 у `SelenideElement` две перегрузки `click()`:
`SelenideElement click(ClickOptions)` и `void click()`. Без аргументов
вызывается `void`-версия, поэтому `click().shouldBe(...)` не
компилируется («void cannot be dereferenced»). Цепочку строят от
`shouldBe`, а `click()` вызывают отдельной строкой:

```java
SelenideElement checkbox = $("#remember-me");
checkbox.shouldNotBe(selected);
checkbox.click();
checkbox.shouldBe(selected);
```

## Где ожидания? Почему нет sleep/wait?

Явных ожиданий нет и не нужно: Selenide сам ждёт элементы и условия
до таймаута (по умолчанию 4 с) — `shouldHave`, `click`, `setValue`
уже «умные». `Thread.sleep` и `WebDriverWait` в проекте не используются.

## Как запустить?

```bash
./gradlew test --rerun-tasks
```

Gradle Wrapper в git, установленный Gradle не нужен. Драйвер
браузера скачивает Selenium Manager автоматически.

`--rerun-tasks` обязателен: без него повторный локальный запуск
пометит задачу `up-to-date` и не выполнит ни одного теста — за
0.5 с вы получите `BUILD SUCCESSFUL`, который ничего не проверяет.
В CI и на Jenkins флаг не нужен: там свежий checkout, кэша нет.

## Как добавить новый тест?

Один класс — одна страница. Сценарии формы логина лежат в
`LoginTest`, новый сценарий — это новый метод там же, а не новый
класс. Новый класс заводи только под новую страницу.

Метод наследует `TestBase`, один метод = один сценарий,
`@DisplayName` на русском. Процедура — skill
`.devin/skills/add-ui-test/SKILL.md`.
