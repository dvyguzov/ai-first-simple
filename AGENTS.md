# AGENTS.md — навигация для AI-агента

Учебный репозиторий «AI-first QA»: минимальный продукт (форма логина)
и автотесты к нему. Этот файл — роутер: здесь только указатели,
детали живут в файлах по ссылкам.

## Команды

```bash
./gradlew test --rerun-tasks         # запуск тестов (без флага — up-to-date)
allure serve build/allure-results   # локальный Allure-отчёт
```

## Карта репозитория

| Что | Где |
| --- | --- |
| Продукт (форма логина) | `index.html` |
| Тесты | `src/test/` |
| CI | `.github/workflows/ci.yml`, `Jenkinsfile` |
| Harness (правила для агента) | `.devin/rules/` |
| Skills (вызываемые процедуры) | `.devin/skills/` |
| Конфиг агента (opencode) | `opencode.json` |
| База знаний (вход — `INDEX.md`) | `docs/kb/` |
| Журнал архитектурных решений (ADR) | `docs/adr/` |
| Описание для человека | `README.md` |
| Спека, по которой собрано репо | `PROMPT.md` |
| Домашнее задание студенту | `docs/homework.md` |

## Правило навигации

1. Сначала читай этот файл.
2. Затем открывай только нужный файл по ссылке из карты выше.
3. Scoped-правила агент сам не получает: открой правило по glob файла
   из таблицы ниже. Любое изменение стека/архитектуры → новый ADR
   по шаблону `docs/adr/0000-template.md`.

## Правила по glob

| Файлы | Правило |
| --- | --- |
| `AGENTS.md` | `.devin/rules/agents-md.md` |
| `index.html` | `.devin/rules/index-html.md` |
| `src/test/**` | `.devin/rules/tests.md` |
| `Jenkinsfile`, `.github/workflows/**` | `.devin/rules/ci-sync.md` |
| `docs/**` | `.devin/rules/docs-kb.md` |
| `.devin/**` | `.devin/rules/rules-format.md` |

Always-on: `.devin/rules/git-commits.md` — приходит в контекст сам
через `instructions` в `opencode.json`. Skills: `skill(<имя>)`.
