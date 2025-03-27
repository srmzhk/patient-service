# patient-service

Проект представляет собой REST-сервис, который написан на Java и Spring Framework для работы с сущностью Patient. Для выполнения технического задания backend часть проекта, СУБД PostgreSQL и OAuth2 сервис для авторизации Keycloak развёртывается в Docker контейнерах. Запросы можно отправлять при помощи Postman или встроенного в сервис Swagger UI.

## PatientGenerator

PatientGenerator - это консольное приложение на Java, которое можно запустить для наполнения БД 100 записями Patient через API patient-service. Консольный генератор содержит в себе хардкодные ссылки на keycloak и метод API сервиса для генерации. При необходимости следует изменить некоторые заначения для корректной работы генератора. 


## Задание

Необходимо создать REST-сервис с авторизацией по протоколу OAuth2
используя OpenSource решение для сервера авторизации.

### 1. Создать REST-сервис, который содержит CRUD-методы для сущности Patient
Пример сущности в формате JSON:
```
{
	"id" : "d8ff176f-bd0a-4b8e-b329-871952e32e1f",
	"name": "Иванов Иван Иванович",
	"gender": "male",
	"birthDate": "1965-03-25T19:06:00"
}
```


### 2. Каждый метод сервиса должен быть доступен только авторизованному пользователю с правами на выполнение метода:
- методы GET - > Role: Practitioner -> Permission: Patient.Read
- методы GET - > Role: Patient -> Permission: Patient.Read
- методы POST/PUT- > Role: Practitioner -> Permission: Patient.Write
- методы Delete- > Role: Practitioner -> Permission: Patient.Delete
### 3. Добавить в проект описание методов API с помощью swagger.
### 4. Разработать консольное приложения для добавления через API 100 сгенерированных сущностей Patient.
### 5. Сделать Postman-коллекцию для демонстрации методов
### 6. Создать Dockerfile и реализовать запуск разработанного программного обеспечения (включая БД) в виде docker-контейнеров.
### 7. Результат выполнения тестового задания положить в открытый репозитарий git-сервера (GitHub, GitLab, ect.).

## Установка

Клонируйте репозиторий к себе на рабочую машину или же скачайте в формате .zip архива и распакуйте в удобное для Вас место.

После перейдите в корневую папку проекта и в консоли запустите команду:

```
docker-compose up docker-compose.yml
```

Далее необходимо пререйти по пути: 
```
C:\Windows\System32\drivers\etc\hosts
```

Добавить в файле строчку `127.0.0.1 keycloak`.

После чего сохранить и выйти.

Когда docker контейнеры развернуться, можно переходить по адресу 
`http://keycloak:8080/admin/master/console/` и авторизоваться в панели администратора keycloak.

```
username: admin
password: admin
```
