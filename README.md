# Многопользовательский сервис хранения файлов
Файлы загружаются через web интерфейс и хранятся локально в папке.
![alt text](https://github.com/maratimaev/FileStore/blob/master/pics/upload.JPG)
Для зугрузки файлов необходимо создать учетную запись и активировать по высланному на адрес пользователя письму.
Ссылка для активации валидна в течение 1 дня.
![alt text](https://github.com/maratimaev/FileStore/blob/master/pics/login.JPG)

![alt text](https://github.com/maratimaev/FileStore/blob/master/pics/create_new_user.JPG)
Реализована возможность предоставления прав на просмотр и скачивание фалов другим пользователям.
Доступна возможность запроса доступа на просмотр или скачивание файлов другого пользователя через подсистему обмена 
сообщениями.
![alt text](https://github.com/maratimaev/FileStore/blob/master/pics/sharing.JPG)

![alt text](https://github.com/maratimaev/FileStore/blob/master/pics/files.JPG)
Пользователям могут быть делегированы следующие роли:
"Администратор" - просмотр, скачивание и удаление файлов всех пользователей
![alt text](https://github.com/maratimaev/FileStore/blob/master/pics/managing.JPG)
"Аналитик" - просмотр файлов всех пользователей, статистика по количеству пользователей, файлов, числу скачиваний файлов
![alt text](https://github.com/maratimaev/FileStore/blob/master/pics/statistics.JPG)

## Используемые фреймворки и библиотеки
***Проект построен на базе:***
- Spring Boot
- Spring Security
- Spring AOP
- Hibernate, JPA, PostgreSQL
- Freemarker
- Bootstrap
- JavaMailSender
- Orika

***Иструмент сборки:***
- Maven

***Инструменты тестирования:***
- Junit
- GreenMail
- Selenium

## Настраиваемые параметры файла "aplication.properties"
**file-store-folder=service** - *папка хранения загруженных файлов*
	
**spring.mail.host=smtp.yandex.ru** - *адрес сервера с которого отправляюися письма на активацию аккаунтов*

**spring.mail.port=465**

**spring.mail.protocol=smtps**

**spring.mail.username=activation-code-sender@yandex.ru** - *почтовый адрес для отправки писем*

**spring.datasource.url=jdbc:postgresql://localhost:5432/FileStore** - *параметры подключения к базе данных*

## Сборка
Проект собирается в виде **war** файла с помощью **Maven** с последующим разворачиванием на сервере приложений **Tomcat**

## Автор 
Марат Имаев
