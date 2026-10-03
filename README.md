# ai-first-simple

[![CI](https://github.com/dvyguzov/ai-first-simple/actions/workflows/ci.yml/badge.svg)](https://github.com/dvyguzov/ai-first-simple/actions/workflows/ci.yml)

Учебный репозиторий «AI-first QA»: минимальный продукт + один автотест.

## Продукт

`index.html` — форма логина. Валидные креды: `admin` / `admin123`.
Страница опубликована на GitHub Pages:
https://dvyguzov.github.io/ai-first-simple/index.html — тесты
открывают именно её.

## Тесты

Стек: Java 21, Gradle Wrapper, JUnit 5, Selenide, Allure.

Gradle Wrapper хранится в git, установленный Gradle не нужен —
на чистом клоне просто запускайте тесты:

```bash
./gradlew test --rerun-tasks
```

Флаг обязателен при повторных локальных запусках: без него Gradle
пометит задачу `up-to-date` и не выполнит ни одного теста.

## Где взять отчёт

CI собирает Allure-отчёт на каждый push и pull request в `main`:
вкладка **Actions** → нужный прогон → блок **Artifacts** внизу страницы →
скачать `allure-report` → распаковать zip → открыть `index.html`.

Локально сырые результаты Allure складываются в `build/allure-results/`.
Посмотреть отчёт (если установлен Allure CLI):

```bash
allure serve build/allure-results
```
