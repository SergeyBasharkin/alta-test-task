# alta-test-task

Описание вакансии
---
| № | Наименование атрибута | Тип |
|---|:---------------------|:----|
| 1 |Уникальный идентификатор (ID)| Целое число|
| 2 |Наименование|Строка|
| 3 | Уровень зарплаты|Целое число|
| 4 | Требуемый опыт работы|Строка|
| 5 | Город|Строка|

Методы веб-службы
---
| № | Метод | Описание |
|---|:-----|:---------|
| 1 |PUT /vacancy| Создание новой вакансии|
| 2 |GET /vacancy|Список вакансий отсортированных по наименованию|
| 3 | GET /vacancy/{id}|Вакансия с ID {id}|
| 4 | DELETE /vacancy/{id}|удаление вакансии с ID {id}|

Хранение данных
---
Для хранения данных необходимо использовать встроенную БД (HSQLDB или H2)

Формат данных
---
При взаимодействии с веб-службой данные передаются в XML формате.
