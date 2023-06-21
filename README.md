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
**$ mv apache-maven-3.6.3 /opt/** <br/>

**Шаг 2:** Установка пути maven
Введите следующие строки в профиль пользоватьля. **/root/.profile** (.profile).

**M2_HOME='/opt/apache-maven-3.6.3'
PATH="$M2_HOME/bin:$PATH"
export PATH**
Вызовите  **source /root/.profile**

**Шаг 3:** Проверка установки
Введите **mvn -version**. Должна поивиться информация об установленной версии Maven 


# Клонирование проекта:
**Шаг 1:**
Создайте папку в которую будет клонирован проект<br/> 
**mkdir /var/<Название_Папки>**<br/>
**Шаг 2:**
Клоннируйте проект коммандой<br/>
**git clone (link)**
# Создать файл application.properties
В папке **src/main/resources/** найти файл application-properties.example. Содержимое файла скопировать в созданный в этом каталоге application.properties<br/>
Заполнить логин и пароль от базы данных в поля **spring.datasource.username/password**.
Заменить **db_name** на существующую базу данных.
# Компиляция проекта
В папке проекта "ItNewsSite" запустить сборщик maven командой
<br/>
**mvn package** 
<br/>
После сборки приложения запустить коммандой<br/>
**java -jar (Корень проекта)/target/www-0.0.1-snapshot.jar**






