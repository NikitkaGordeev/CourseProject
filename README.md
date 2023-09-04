# Курсовой проект по модулю «Автоматизация тестирования» для профессии «Инженер по тестированию»

# Описание проекта

Наше приложение — это веб-сервис, который предлагает купить тур по определённой цене двумя способами:

- Обычная оплата по дебетовой карте.  
- Уникальная технология: выдача кредита по данным банковской карты.  
![image](https://github.com/NikitkaGordeev/CourseProject/assets/130284238/7c2745b6-3b40-4311-accd-2231a8077c76)


## Документация

1. [План](https://github.com/NikitkaGordeev/CourseProject/blob/main/docs/Plan.md)
2. [Отчет по итогам тестирования](https://github.com/NikitkaGordeev/CourseProject/blob/main/docs/Report.md)
3. [Отчет по итогам автоматизации](https://github.com/NikitkaGordeev/CourseProject/blob/main/docs/Summary.md)


## Установка и запуск
1. Клонировать [репризиторий](https://github.com/NikitkaGordeev/CourseProject) командой `git clone`;
2. Запустить Docker Desktop;
3. Открыть проект в IDEA;
4. Запустить БД командой `docker-compose up`;
5. Запустить приложение командой `java -jar ./artifacts/aqa-shop.jar`;
6. Открыть браузер и ввести в адресную строку `http://localhost:8080/`. 

## Запуск тестов
1. Открыть IDEA;
2. Ввести в терминал команду: `./gradlew clean test`.

## Генерация и открытие отчетов
1. Для генерации отчета ввести в терминал команду: `./gradlew allureServe`;
2. После генерации он появится в окне браузера.

