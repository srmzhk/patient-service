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

Добавить в файле строчку `127.0.0.1 keycloak`. Должно получиться:

![Application Screenshot](https://github.com/srmzhk/patient-service/blob/main/images/1.png)

После чего сохранить и выйти.

Когда docker контейнеры развернуться, можно переходить по адресу 
`http://keycloak:8080/admin/master/console/` и авторизоваться в панели администратора keycloak.

```
username: admin
password: admin
```
![Application Screenshot](https://github.com/srmzhk/patient-service/blob/main/images/2.png)

После авторизации кликаем на выпадающий список и создаём новый realm. Задаём название OAuth или же любое другое, но тогда придётся менять конфигурацию Spring Boot приложения.

Далее создаём нового Сlient с именем myclient:

![Application Screenshot](https://github.com/srmzhk/patient-service/blob/main/images/3.png)

Включаем авторизацию и аутентификацию:

![Application Screenshot](https://github.com/srmzhk/patient-service/blob/main/images/4.png)

Задаём адрес нашего сервис:

![Application Screenshot](https://github.com/srmzhk/patient-service/blob/main/images/5.png)

Сохраняем. После переходим на вкладку Credentials и копируем client_secret:

![Application Screenshot](https://github.com/srmzhk/patient-service/blob/main/images/6.png)

Скопированный Client Secret вставляем в соотвестующее поле client_secret в переменной KEYCLOAK_BODY в разработанном консольном генераторе 100 сущностей PatientGenerator:

![Application Screenshot](https://github.com/srmzhk/patient-service/blob/main/images/7.png)

Далее переходим на владку Roles и создаём все перечисленные ниже роли:
```
Admin
Patient
Practitioner
Patient.Write
Patient.Read
Patient.Delete
```

Переходим на вкладку Users и создаём 3 пользователей:
```
admin
user1
user2
```

У каждого пользователя необходимо перейти на вкладку Credentials и задать пароль, при этом снять маркер, что пароль временный.

![Application Screenshot](https://github.com/srmzhk/patient-service/blob/main/images/8.png)

Также каждому пользователю задать соответсвующие роли на вкладке Role mapping. 

Пример добавленных ролей для user1:

![Application Screenshot](https://github.com/srmzhk/patient-service/blob/main/images/9.png)

На этом конфигурация Keycloak закончена.

Следующим шагом собираем docker-image Spring Boot приложения из Dockerfile в корне проекта:
```
docker build -t patient-service:latest
```

После успешной сборки можно запускать docker-container:
```
docker run -d --name SpringApp --network patient-service_keycloak-network -p 8083:8083 patient-service:latest
```

На этом этап установки и развёртывания закончен.

## Postman 

Для удобного тестирования API есть коллекция postman. Для использования созданных методов следует использовать Auth Type: OAuth 2.0.

![Application Screenshot](https://github.com/srmzhk/patient-service/blob/main/images/10.png)

Вместе со следующими параметрами:

![Application Screenshot](https://github.com/srmzhk/patient-service/blob/main/images/11.png)

При этом Token Name может быть любым, Client Secret берём при настройке Keycloak клиента, Username и Password используем для соответствующих пользователей, которых создали ранее.

Ниже будет кнопка для генерации токена. После успешного выполнения запроса на Keycloak, можно будет использовать сгенерированный токен для отправки запроса. Нажимаем Send и если у пользователя нужная роль и разрешения, то метод должен выполниться успешно.

## Swagger

Есть возможность тестирования и просомотра описания методов API при помощи Swagger UI, который доступен по ссылке `localhost:8083/swagger-ui/index.html` при запущенном patient-service.

При тестировании с помощью Swagger следует изначально получить токен через Postman используя OAuth 2.0, которая сгенерирует JWT токен. На основе данного токена можно будет авторизоваться в Swagger UI и отправлять соответсвующие запросы.
