Social Media Application

Description:
-----------------
This is a simple social media application built with Spring Boot, Hibernate, and PostgreSQL. Users can create posts, follow other users, and like posts. The application uses RESTful APIs to handle various functionalities.

Requirements:
-----------------
- Java 8 or later
- Maven
- PostgreSQL database

Setup:
-----------------
Database Configuration:
1. Create a PostgreSQL database for the application.
2. Update the database configuration in src/main/resources/application.properties with your database details:

   spring.datasource.url=jdbc:postgresql://your-database-host:your-database-port/your-database-name
   spring.datasource.username=your-database-username
   spring.datasource.password=your-database-password

Running the Application:

Using Maven:
1. Open a terminal and navigate to the project root directory.
2. Run the following command to build the project:

   mvn clean install

3. Run the application:

   mvn spring-boot:run

4. The application will be accessible at http://localhost:8080.

Using IDE:
1. Import the project into your favorite IDE (Eclipse, IntelliJ, etc.).
2. Run the SocialMediaApplication class as a Java application.

API Endpoints:
-----------------
Users:
- Create User: POST /api/users
- Get User by ID: GET /api/users/{userId}
- Get User by Username: GET /api/users/by-username/{username}

Posts:
- Create Post: POST /api/posts
- Get Post by ID: GET /api/posts/{postId}
- Get Posts by Author ID: GET /api/posts/by-author/{authorId}
- Get All Posts: GET /api/posts/all
- Delete Post: DELETE /api/posts/{postId}

Example Requests:
-----------------
Create User:
curl -X POST -H "Content-Type: application/json" -d '{"username":"john_doe"}' http://localhost:8080/api/users

Create Post:
curl -X POST -H "Content-Type: application/json" -d '{"title":"New Post","body":"This is a new post.","author":{"id":1,"username":"john_doe"}}' http://localhost:8080/api/posts

Get User by ID:
curl http://localhost:8080/api/users/1

Get Post by ID:
curl http://localhost:8080/api/posts/1

Contributing:
-----------------
Feel free to contribute to the development of this application by opening issues and pull requests.

License:
-----------------
This project is licensed under the MIT License.

- Was it easy to complete the task using AI?
It wasn't necessary easy as some information/code provided had to be modified and there was a bit of going back and forth to receive the desired answer.

- How long did task take you to complete? (Please be honest, we need it to gather anonymized statistics)
It took me about 5 hours to fully complete it.

- Was the code ready to run after generation? What did you have to change to make it usable?
Had to change some of the tests (in a small percentage) and had to make sure the Lombok is used correctly (GPT initially advised upon using @Data on Entities which is incorrect).

- Which challenges did you face during completion of the task?
Mostly it was the fact-checking of the answers and ensuring the code-coverage is sufficient.

- Which specific prompts you learned as a good practice to complete the task?
Not sure if I would call it a specific prompt, but I noticed that ChatGPT provides more accurate answers when asked a granular question. Thus, I have used the "Please generate x" or "Please advise upon y" (x and y refer to specific topics like generate a unit test or generate a controller class).
