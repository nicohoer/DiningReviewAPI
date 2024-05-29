# Portfolio Project Dining Review API
Hello everybody, to my little repository where I've put all my code from the Portfolio Project of the Codecademy Course "Create REST APIs with Spring and Java". 
## Background
Codecademy is an interactive online-learning platform, that offers courses on many different topics with regards to coding and software development. These courses consist of coding tasks, quizzes and usually one bigger project called Portfolio Project. The point of these projects is to deepen the knowledge of the course contents by applying them in a bigger final coding project. Unlike the smaller coding tasks in the course, the final project is done in an **unsupervised fashion**, meaning there's no explicit instructions on what code to write and how to solve certain questions. Instead there's only a mock Kanban board with more general instructions and requirements for what your code should achieve. Codecademy also provides their own example solution for the project.
This course was my *first experience* with the **Spring framework** or any kind of Rest API programming, and I can say that I've learned a lot and can absolutely recommend it to beginners. I had a lot of fun with this course and especially the final project which I would  say taught me the most, since I wrote everything myself, fixed my bugs and got the code to run in the end. While working on the project I researched a lot of my problems online or used the course materials such as their cheatsheets and the example solution. Though I didn't come up with every part of the program myself, I do understand all of it now and could **explain** and **replicate everything**, which was my goal for this project to begin with. To test my API endpoints I used Postman, which I found online.
## What this program does:
- Provide the most basic backend program for handling **HTTP requests** for a Restaurant Review Application with **Java** and **Spring Boot**
- perform **CRUD** operations for users, restaurants and reviews and some search functionality
- use the **embedded H2 database** during runtime
## What this program doesn't do:
- provide any frontend application
- perform any software testing
- connect to a real database to permanently store data
- and much more! This is only practice program and doesn't achieve anything aside from me learning and improving.
## Explaining functionality in my own words
The program consists of three packages and one Java class called DiningReviewAPIApplication that contains the main function and the @SpringBootApplication annotation. It vaguely follows the MVC architecture, though there's no real Views.
### Model Package
This folder consists of all the Java Class files used to model this problem in Object-Oriented style. The main entities here are Restaurant, Review and User, whose desired attributes were described in the mock Kanban board mentioned earlier. There's also another class AdminApproval who only has one attribute, as well as an Enum ReviewStatus. All classes have getter and setter methods, which I generated with the Lombok plugin, as well as **JPA annotations** so the program can turn theses Java Classes into SQL tables using **Object-Relational Mapping**. Here, I ran into one of my first problems: a table called User was created from the User class, however "User" is already a defined variable name in the H2 database, so I had to add a custom name in the @Entity annotation, for it to not confuse the two.
- **Restaurant**: has attributes id, name and zip as basic information as well as overallScore, peanutScore, dairyScore, eggScore to show how good the restaurants menu is in relation to certain allergies.
- **User**:  also has basic information such as id, username, city, state, zip code and info on whether the user is interested in certain allergies.
- **Review**: has attributes id, the id for the Restaurant the Review was written for, submittedFrom for the username, the review text itself, scores for all allergies and the ReviewStatus of the review
### Repository package
This package serves as a *data access layer* and consists of repository interfaces for the three main model classes. These repositories have functions to easily perform operations on the databases created by the JPA annotations of the model classes. They all extend the Spring Data *CrudRepository* which allows them to execute the basic CRUD methods, *create, read, update, delete*. Aside from those basic functions Spring allows us to write our own ones using a certain syntax in the function name (explained here: https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html ) which it then automatically turns into SQL queries. Since they're interfaces it's only necessary to write the method head, its returned data type and the argument data types and Spring will automatically create the specified implementation. For example, a method in UserRepository called *findByUsername* will later be converted into a program which queries the User database similarly to an SQL statement *"SELECT FROM _ WHERE "username" = _ "*.
- **RestaurantRepository**: *additionally* has a function to find restaurants by name and zip, as well as functionality to find restaurants with a certain zip and then sorted descendingly by an allergy score.
- **UserRepository**: has a function to find Users by username.
- **ReviewRepository**: find reviews by ReviewStatus as well as find reviews by restaurant id and ReviewStatus.
### Controller Package
 Here the classes RestaurantController, ReviewController, UserController and AdminController are stored. These classes contain the most functionality of the program, especially their main use which is *handling HTTP requests* like GET, PUT, POST and DELETE. 
To walk through what these classes do, I will take the code of **RestaurantController** as an example. The class is marked with the @RestController annotation which allows it to handle said HTTP requests and implement the REST API, as well as the @RequestMapping annotation which, at the class level, sets a *base path* for which requests this classes methods should respond to, which will be "/restaurants" in this case. This class also makes use of the RestaurantRepository, which is an instance variable of this class and also a required argument for the RestuarantController constructor. Via **dependency-injection** Spring Boot will make sure that at runtime an implementation of the RestaurantRepository interface will be created before the RestaurantController.

To create new Restaurants the submitRestaurant function exists which is linked to POST requests to the base path mentioned above. It requires a Restaurant object as an argument and then checks whether a restaurant with the same name and ZIP code already exists. If it does the program throws a ResponseStatusException and if not the restaurant is saved to the repository making use of the CrudRepository interface methods. The **@RequestBody** annotation is used to signal that the restaurant object which should be saved needs to be passed in the body of the HTTP request. To *test my API* with Postman I added a restaurant in very simple *JSON notation to the body*.  
Theres a GET method to return all restaurants as well as one to look for a restaurant with an id, where the id is given as a path variable(using **@PathVariable**). Path variables are added to the path and take on specific values. If I wanted to get the restaurant with id = "5", the path "/restaurants/5" would need to be accessed. The method tells the repository to look for the restaurant with that id and either returns it or throws a ResponseStatusException if it couldn't be found. 
Another option to include in GET methods is request parameters (using **@RequestParam**) which can be given in the request and then used by the program to, for example, return restaurants with certain attributes. Here, accessing the path "/restaurants/search?zip=86159&allergy=peanut" would return all restaurants with the given zip code, sorted descendingly by the (not null) allergy, here "peanut". Part of the task for the project was also to write some functionality to validate input and make sure to throw RepsonseStatusExceptions and therefore this class also includes a method to validate zip codes.

- **UserController**: has functions to add, get and update users as well as some helper functions.
- **ReviewController**: has a function to add a review, which also checks first if the restaurant for which the review is given, exists. All reviews get the ReviewStatus PENDING as default.
- **AdminController**: can get the pending reviews and has a function to give approval to pending reviews. Everytime a review is accepted the scores for that restaurant are updated using another helper function defined in this class.
