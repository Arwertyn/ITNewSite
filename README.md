# ITNewSite

*sudo apt update && sudo apt upgrade*

# Требования:
Java 17:  **sudo apt install openjdk-17-jdk**<br/>
MySQL: **sudo apt install mysql-server**<br/>
GIT:  **sudo apt install git**


# Установка Maven:
**Шаг 1** Выполнить комманды<br/>
**$ wget https://mirrors.estointernet.in/apache/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz**<br/>
**$ tar -xvf apache-maven-3.6.3-bin.tar.gz**<br/>
**$ mv apache-maven-3.6.3 /opt/ ** <br/>

Создайте папку mkdir /var/<Название_Папки><br/>

# Создать файл application.properties
В папке **src/main/resources/** найти файл application-properties.example. Содержимое файла скопировать в созданный в этом каталоге application.properties<br/>
Заполнить логин и пароль от базы данных в поля **spring.datasource.username/password**.
Заменить **db_name** на существующую базу данных.






Step 2: Setting M2_HOME and Path Variables
Add the following lines to the user profile file (.profile).

M2_HOME='/opt/apache-maven-3.6.3'
PATH="$M2_HOME/bin:$PATH"
export PATH
Relaunch the terminal or execute source .profile to apply the changes.

Step 3: Verify the Maven installation
Execute mvn -version command and it should produce the following output.
