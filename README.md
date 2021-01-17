# Travelers-telegram-bot/Test project
* Test task for a vacancy [Junior Java developer](https://rabota.by/vacancy/41423078)
    * Сreated 15-17 January 2021

##Task condition
Необходимо создать web приложение по управлению собственным туристическим телеграм ботом.
1) Телеграм бот выдает пользователю справочную информацию о введенном городе. Например, пользователь вводит: «Москва», чат-бот отвечает: «Не забудьте посетить Красную Площадь. Ну а в ЦУМ можно и не заходить)))».
2) Данные о городах должны храниться в базе данных.
3) Управлять данными о городах (добавлять новые города и информацию о них, изменять и удалять любую информацию) необходимо через REST WebService.

Используемые технологии: SpringBoot, SpringMVC, SpringData, Hibernate, Java не ниже 1.8. Для сборки проекта использовать Maven.
Исходный код приложения должен быть залит на GitHub, в файле readme указать, что необходимо для запуска (в том числе имя телеграм бота и его токен).

## Technologies
* Multi-module maven project (3-level three-module development architecture + bot-level)
* Version Control System - _Git_
* Technologies:
    * _Java 11_
    * _Log4j2_
    * _Spring boot 2_
    * _Spring 5_
    * _MySQL 8_
    * _Telegram Bots_
* Unit and Integration application tests
* Using _Liquibase_ for Migration

## Getting started

### _for the project start it is required:_
* Git
* JDK11
* MySQL8.0

### _application.properties_

* add your database settings
```
spring.datasource.username=
spring.datasource.password=
```

### _bot.properties_

* (for reference) does not need to be changed
```
bot.name=YBtestTravelersBot
bot.token=1585672359:AAFVSS5_M4_p8G6KklJ44-Vmdx7FK5pXca4
```
## Authors

* **Yauheni Belanovich** - [YauhenBelanovich](https://github.com/YauhenBelanovich)