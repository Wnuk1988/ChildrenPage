# ChildrenPage
Project for ***TeachMeSkills***

### Descriptions:
The service provides entertaining and educational collections of fairy tales, audio tales, coloring books, songs and more that are familiar to us from childhood, and fairy tales by modern authors. You can also download it for free if you wish.

## Tests
Before starting to use the application, it is recommended to run tests to check its functionality.
This can be done by selecting the <u>ChildrenPage-> src-> test-> java</u> folder and pressing Ctrl + Shift + F10, or by right-clicking and selecting “Run ‘All Tests ’”.

## Database
The project is connected to the <u>ChildrenPage</u> in-memory database. Contains 4 tables.  
+ *user_info* – stores information about users. At the time of creation there were 2 users.  
+ *description_file* – stores information about the file itself. At the time of creation it contains 5 files.  
+ *l_user_file* – stores the user's favorite files.  
+ *security_credentials* – stores data about login, password (in encrypted form), user data for identification.  


To run a project using the open **Docker** platform, you need to configure the <u>application.properties</u> file. Change the value of the lines:  
+ spring.jpa.hibernate.ddl-auto=update on create;  
+ spring.datasource.url=jdbc:postgresql://localhost:5432/ChildrenPage on jdbc:postgresql://${DB_HOST}/ChildrenPage

## Registration
To register, you must go to "http://localhost:8080/registration".
We have to pass <u>json</u> format (**POST** method:  
`{
"firstName": "firstName",
"lastName": "lastName",
"dateOfBirth": "dateOfBirth",
"email": "email",
"userLogin": "userLogin",
"userPassword": "userPassword"
}`)
to "http://localhost:8080/registration", after which the user will be created and placed in the database.  
When registering, the user is saved in the database with the *USER* role and is assigned a random activation code for mail and an activation status of *false* . The fields email, userLogin, userPassword cannot be *NULL*, and email, userLogin must be **unique**.  

## Authentication
Access endpoints (except:
"http://localhost:8080/registration",
"http://localhost:8080/authentication"
) we can only authenticate.
After registration, the user receives the role USER or ADMIN.
There are 2 users in the database with different roles:  
+ Login: Admin, Password: password  
+ Login: User, Password: password

## User capabilities
### Available endpoints:
+ "http://localhost:8080/user/{id}" – Getting information about the user.  
  Example:( **GET** method: "http://localhost:8080/user/1" )  
+ "http://localhost:8080/update" – Changing Security data.  
  We have to pass <u>json</u> format (**PUT** method:  
  `{
  "email": "email",
  "userLogin": "userLogin",
  "userPassword": "userPassword"
  }`)  
  Example:( **PUT** method: "http://localhost:8080/update" )  
+ "http://localhost:8080/email/{email}" – Mail activation.  
  Example:( **GET** method: "http://localhost:8080/email/childrenpage65@gmail.ru" )  
+ "http://localhost:8080/activate/{code}" – Getting the activation code.  
  Example:( **GET** method: "http://localhost:8080/activate/e0b27347-bb1f-44bc-93bc-b58742ca0a1a" )  
+ "http://localhost:8080/user/favorites" – Adding a file to the user's favorites.  
  We have to pass <u>json</u> format (**POST** method:  
  `{
  "userId": userId,
  "fileId": fileId
  }`)  
  Example:( **POST** method: "http://localhost:8080/user/favorites" )  
+ "http://localhost:8080/user" – Changing the user.  
  We have to pass <u>json</u> format (**PUT** method:  
  `{
  "id": id,
  "firstName": "firstName",
  "lastName": "lastName",
  "dateOfBirth": "dateOfBirth",
  "favoritesFile": []
  }`)  
  Example:( **PUT** method: "http://localhost:8080/user" )  
+ "http://localhost:8080/user/{id}" – Deleting a user.  
  Example:( **DELETE** method: "http://localhost:8080/user/1" )  
+ "http://localhost:8080/user/favorites" – Removing a file from a favorite user.  
  We have to pass <u>json</u> format (**DELETE** method:  
  `{
  "userId": userId,
  "fileId": fileId
  }`)  
  Example:( **DELETE** method: "http://localhost:8080/user/favorites" )  
+  "http://localhost:8080/file" – We get a description of all files.  
   Example:( **GET** method: "http://localhost:8080/file" )  
+ "http://localhost:8080/file/pagination" – We get file descriptions by pagination.  
  We have to pass <u>json</u> format (**GET** method:  
  `{
  "offset": offset,
  "limit": limit,
  "sort": "sort"
  }`)  
  Example:( **GET** method: "http://localhost:8080/file/pagination" )  
+ "http://localhost:8080/file/filename/{filename}" – We receive the file.  
  Example:( **GET** method: "http://localhost:8080/file/filename/Spiderman.jpg" )  
+ "http://localhost:8080/file/{id}" – Getting a file description.  
  Example:( **GET** method: "http://localhost:8080/file/1" )  
+ "http://localhost:8080/file/categories/{categories}" – We get a description of files by category.  
  Example:( **GET** method: "http://localhost:8080/file/categories/NATURE" )  
+ "http://localhost:8080/file/genres/{genres}" – We get a description of files by genre.  
  Example:( **GET** method: "http://localhost:8080/file/genres/FAIRY_TALES" )  


## Admin capabilities
### Has access to all functions of the service, in addition to those available to the user (see above), and can additionally:  

