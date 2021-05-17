## Дипломный проект профессии «Тестировщик»
### Документация
* [План тестирования](https://github.com/Mikhail9030/QADiplom/blob/master/documents/Plan.md/ "План тестирования")
* [Отчет по итогам тестирования](https://github.com/Mikhail9030/QADiplom/blob/master/documents/Report.md/ "Отчет по итогам тестирования")
* [Комплексный отчет по итогам процесса автоматизации](https://github.com/Mikhail9030/QADiplom/blob/master/documents/Summary.md/ "Комплексный отчет по итогам процесса автоматизации")
### О проекте
#### Проект представляет собой комплексное автоматизированное тестирование сервиса по покупке туров через интернет-банк. Купить тур можно с помощью двух способов:
* Покупка по дебетовой карте
* Покупка в кредит
#### Само приложение не обрабатывает данные по картам, а пересылает их банковским сервисам:
* сервису платежей (Payment Gate)
* кредитному сервису (Credit Gate)
#### Приложение в собственной СУБД сохраняет информацию о том, каким способом был совершён платёж и успешно ли он был совершён.

### Запуск Авто-Тестов.
* Запустить в Docker контейнеры СУБД MySQl, PostgerSQL и Node.js
* Запустить контейнеры в терминале
1. Нужно ввести команду в терминале: 
**docker-compose up**
2. Зпускаем SUT, для этого в новой вкладке в терминале вводим следующую команду:
* Для СУБД MySQL: **java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar**
* Для СУБД PostgreSQL: **java -Dspring.datasource.url=jdbc:postgresql://localhost:5433/app -jar artifacts/aqa-shop.jar**
3. Запускаем автотесты, следующе командой:
* Для MySQL:
**gradlew clean test -Durl=jdbc:mysql://localhost:3306/app**
* Для PostgreSQL: **gradlew clean test -Durl=jdbc:postgresql://localhost:5433/app**
4. Сгенерировать отчеты: 
* gradlew allureReport
* gradlew allureServe
5. Для завершения работы allureServe выполнить команду: **Ctrl + С далее Y**
