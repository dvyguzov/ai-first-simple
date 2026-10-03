# 0007. opencode.json как адаптер harness: skills и always-on правила

Дата: 2026-10-03
Статус: принято

## Context

Harness написан под конвенции Devin: skills в `.devin/skills/`,
scoped-правила с `trigger: glob` в `.devin/rules/`. Проверка на
opencode показала: из четырёх skills не виден ни один, а ни одно
правило не попадает в контекст — в списке доступных скиллов только
встроенный `customize-opencode`.

Причины у opencode свои: skills он ищет только в `.opencode/skills/`,
`.claude/skills/`, `.agents/skills/` и в путях из `skills.paths`;
механизма «правило по glob» нет вообще — есть целиком загружаемый
`AGENTS.md` и поле `instructions`.

Варианты:

- Дублировать harness под каждую среду (`.opencode/skills/` +
  копия правил) — две копии правил разъезжаются, «факт в одном месте»
  из PROMPT.md нарушается.
- Симлинки `.agents/skills` → `.devin/skills` — работает, но это
  неявная зависимость от поведения сканера, и пути в git-дереве
  перестают читаться.
- Невидимый для других агентов harness — правило не применяется тихо.

## Decision

Добавить в корень репозитория `opencode.json`:

- `skills.paths: [".devin/skills"]` — skills остаются в одном месте,
  opencode сканирует канонический каталог;
- `instructions: [".devin/rules/git-commits.md"]` — только
  always-on-правило, чтобы не держать в контексте все семь.

Scoped-правила агент открывает сам: в `AGENTS.md` вместо утверждения
«подгружаются автоматически» добавлена таблица «Правила по glob» —
путь файла → файл правила. Формат правил не менялся, он описан в
`.devin/rules/rules-format.md` с оговоркой, что `trigger`/`globs`
читает только Devin.

## Consequences

- Один harness работает и в Devin, и в opencode: skills видны как
  `skill(<имя>)`, always-on правило — в каждой сессии.
- Токен-политика из PROMPT.md соблюдена: в контексте всегда одно
  правило, остальные — по необходимости.
- Новое правило требует правки в двух местах: строка в таблице
  `AGENTS.md` и, для always-on, в `instructions`. Проверка:
  `opencode debug skill` и `opencode debug config`.
- Файлы harness читали агенты, которые знают про `opencode.json`;
  без него инструкции в `AGENTS.md` остаются верными только для Devin.