# Asset-Management
- **_This project is implemented for the learning and practice purpose only._**


- **Project Description :** This project would be helpful for an organization
where many employees get some assets like- **laptop, data-card, printer, monitor,
car, bike etc.** To manage assets efficiently, it should be **recorded** in the system with
details like **assetNo, assetDescription, isAvailable/allocatedToWhom etc.** 
  - **There are three available roles:**
    - **Admin :** An admin has full control of the application. Admin can **register users/assets/allocate/deallocate** etc.
    - **Support :** A support person has **limited access** to the application.
                He can **register a user, search existing user, see allocated assets.**
    - **User :** An employee having user role has **limited access** to **see his profile details** and **all allocated assets to him only.** 


- **Software Needed :** 
  - Java-8 or above
  - Mysql/Mariadb-5 or above


____________________________________________________________________________________________



## This application has capability to run in two different modes -
**(I) In-Memory Mode :** It means without the database. This can be opted, If you do not have mysql db installed in your
                    computer but wanted to explore the functionalities of the application or want to do the manual / 
                    automation testing. This is the default mode.

**(II) DB Mode :** It means with the database like mysql. This is the preferred mode as you would be able to see the data
                in-flow/out-flow from your database with the standard sql.


### (A.) To run the application using in-memory db, steps for this -
- **step-1 :** open the command prompt or terminal
- **step-2 :** go to the unzipped folder where am.jar reside, you can use "cd" command for this.
- **step-3 :** run the application using below command.
  - `java -jar am.jar  --spring.config.location=file:///application.properties`
- **step-4 :** open the browser and hit this url
  - [http://localhost:7060/](http://localhost:7060/)
- **step-5 :** start doing the manual testing, refer the screenshot folder.
- **step-6 :** now you can start writing the automation testing using selenium.
- **step-7 :** [Optional] you can find the rest apis used in this project by hitting this url
  - [http://localhost:7060/swagger-ui.html](http://localhost:7060/)


____________________________________________________________________________________________


### (B.) To run the application using mysql db, for this - firstly create the database and tables, steps for this-
- **step-1 :** Execute this database script file(database.sql) in your mysql workbench and update 
 these db properties inside the application.properties file.
  - `db.host=localhost`
  - `db.port=3306`
  - `db.name=am_db`
  - `spring.datasource.username=root`
  - `spring.datasource.password=mysql_password`
  - ~~Uncomment the below properties by removing the '#' symbol~~
  - `spring.datasource.url=jdbc:mysql://${db.host}:${db.port}/${db.name}`

- **step-2:** open the command prompt or terminal.
- **step-3 :** go to the unzipped folder where am.jar reside, you can use "cd" command for this.
- **step-4 :** run the application using below command. 
  - `java -jar am.jar  --spring.config.location=file:///application.properties`
- **step-5 :** open the browser and hit this url
  - [http://localhost:7060/](http://localhost:7060/)
- **step-6 :** start doing the manual testing, refer the screenshot folder.
- **step-7 :** now you can start writing the automation testing using selenium.
- **step-8 :** [Optional] you can find the rest apis used in this project by hitting this url
  - [http://localhost:7060/swagger-ui.html](http://localhost:7060/)


 ____________________________________________________________________________________________



