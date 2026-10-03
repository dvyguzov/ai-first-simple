---
description: "Формат scoped-правил в .devin/rules/"
trigger: glob
globs: [".devin/**"]
---

# Формат файлов правил

Новое правило — файл `.devin/rules/<имя>.md` с frontmatter:

```markdown
---
description: "<короткое описание на русском>"
trigger: glob
globs: ["<паттерн>", "<ещё паттерн>"]
---

<текст правила>
```

- `globs` — YAML-список (`["src/test/**"]`), не строка:
  `globs: "a,b"` и `globs: "a"` не парсятся, правило молча не работает.
  Проверка: `devin rules list` не должен показывать YAML parse error.

- Один файл — одно правило.
- `trigger: glob` — правило нужно только при работе с файлами по `globs`;
  `always_on` — в каждой сессии.
- Frontmatter `trigger`/`globs` — разметка для Devin. opencode их
  игнорирует: scoped-правило агент открывает сам по таблице
  «Правила по glob» в `AGENTS.md`, always-on файл должен быть
  перечислен в `instructions` в `opencode.json`. Новое правило →
  строка в таблице `AGENTS.md`, always-on → строка в `opencode.json`.
- Текст правила короткий и конкретный.
