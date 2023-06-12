# GitHub Repository Viewer

GitHub Repository Viewer is a simple Spring Boot application that retrieves a list of repositories for a specific GitHub user.

## Prerequisites

- Java 8 or later
- Maven

## Usage

1. Clone the repository
2. Build the project using Maven: `mvn clean install`
3. Run the application: `java -jar target/your-jar-file.jar`
4. Use the endpoint: `GET /api/repositories/username` to fetch repositories for a GitHub user

Please replace `your-jar-file.jar` with the actual jar file of your project and `username` with the GitHub username.

With the header "Accept: application/xml".

<img width="634" alt="406" src="https://github.com/BialasPiotr/GitHub_Repository_Viewer/assets/96840701/c011e834-52d4-4f17-a30a-ecce5c1d366c">


After entering an existing user

<img width="627" alt="ok" src="https://github.com/BialasPiotr/GitHub_Repository_Viewer/assets/96840701/fc2c2fe2-d6f4-4c6d-8fed-6e581b20b3cf">


When you type in a non-existent user:

<img width="630" alt="404" src="https://github.com/BialasPiotr/GitHub_Repository_Viewer/assets/96840701/b54e12fb-845c-4899-aa71-a2e7107516be">

