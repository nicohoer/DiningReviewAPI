# Portfolio Project Dining Review API
Hello everybody, to my little repository where I've put all my code from the Portfolio Project of the Codecademy Course "Create REST APIs with Spring and Java"! 
## Background
Codecademy is an interactive online-learning platform, that offers courses on many different topics with regards to coding and software development. These courses consist of coding tasks, quizzes and usually one bigger project called Portfolio Project. The point of these projects is to deepen the knowledge of the course contents by applying them in a bigger final coding project. Unlike the smaller coding tasks in the course, the final project is done in an **unsupervised fashion**, meaning there's no explicit instructions on what code to write and how to solve certain questions. Instead there's only a mock Kanban board with more general instructions and requirements for what your code should achieve. Codecademy also provides their own example solution for the project.

This course was my *first experience* with the **Spring framework** or any kind of Rest API programming, and I can say that I've learned a lot and can absolutely recommend it to beginners. I had a lot of fun with this course and especially the final project which I would  say taught me the most, since I wrote everything myself, fixed my bugs and got the code to run in the end. While working on the project I researched a lot of my problems online or used the course materials such as their cheatcheets and the example solution. Though I didn't come up with every part of the program myself, I do understand it all now and could <b>explain</b> and <b>replicate everything</b>, which was my goal for this project to begin with. To test my API endpoints I used Postman, which I found online.
## What this program does:
- Provide the most basic backend program for handling **HTTP requests** for a Restaurant Review Application with **Java** and **Spring Boot**
- perform **CRUD** operations for users, restaurants and reviews and some search functionality
- use the **embedded H2 database** during runtime
## What this program doesn't do:
- provide any frontend apllication
- perform any software testing
- connect to a real database to permanently store data
- and much more! This is only practice program and doesn't achieve anything aside from me learning and improving.
## Explaining functionality in my own words
The program consists of three packages and one Java class called DiningReviewAPIApplication that contains the main function and the @SpringBootApplication annotation. 
### Model Package
This folder consists of all the Java Class files used to model this problem in Object-Oriented style. The main entities of interest here are Restaurant, Review and User, whose desired attributes were described in the mock Kanban board mentioned earlier. There's also another class AdminApproval which only has one attribute, as well as an Enum ReviewStatus. 

All classes have getter and setter methods which I generated with Lombok as well as **JPA annotations** so the program can turn theses Java Classes into SQL tables using **Object-Relational Mapping**. 
Here I ran into one of my first problems: a table called User was created from the User class, however "User" is already a defined variable name in the H2 system, so I had to add a custom name in the @Entity annotation.
- **Restaurant**: has attributes id, name and zip as basic informatation as well as overallScore, peanutScore, dairyScore, eggScore to show how good the restaurants menu is in relation to certain allergies.
- **User**:  also has basic information such as id, username, city, state, zipcode and info on whether the user is interested in certain allergies.
- **Review**: has attributes id, restaurantId for Restaurant the Review was written for, submittedFrom for the username, the review text itself, scores for all allergies and the ReviewStatus of the review
### Repository package
