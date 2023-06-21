# ITNewSite

*sudo apt update && sudo apt upgrade*

# Требования:
Java 17:  **sudo apt install openjdk-17-jdk**<br/>
Maven: **sudo apt install maven**<br/>
MySQL: **sudo apt install mysql-server**<br/>
GIT:  **sudo apt install git**

Создайте папку mkdir /var/<Название_Папки><br/>

# Создать файл application.properties
В папке **src/main/resources/** найти файл application-properties.example. Содержимое файла скопировать в созданный в этом каталоге application.properties<br/>
Заполнить логин и пароль от базы данных в поля **spring.datasource.username/password**.
Заменить **db_name** на существующую базу данных.
