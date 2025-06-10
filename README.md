🔧 Prerequisites
Java 17+

Maven 3.5.0

Postgrel 

IDE (IntelliJ IDEA )

(Optional) Postman for API testing


Deployed Url

DB Deployment Url : https://dashboard.render.com/d/dpg-d13v49ggjchc73fjpim0-a
Front End Deployment ur  : https://employee-management-vodx.onrender.com
Swagger = https://employee-management-vodx.onrender.com/swagger-ui/index.html

⚙️ 1. Project Setup & Configuration
Clone the repository


git clone https://github.com/PradeepCodes/Employee_Management
cd employee-management
Create the MySQL database

sql
Copy
Edit
CREATE DATABASE employee_management;
Configure src/main/resources/application.properties

properties
Copy
Edit
# MySQL connection
spring.application.name=Employee_Management

spring.datasource.url=jdbc:mysql://localhost:3306/employeemanagement
spring.datasource.username=root
spring.datasource.password=1234
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


# JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# PostgrelConnection for Deployed
spring.datasource.url=jdbc:postgresql://dpg-d13v49ggjchc73fjpim0-a.oregon-postgres.render.com:5432/employee_management_am10?sslmode=require
spring.datasource.username=pradeep
spring.datasource.password=jDo54X1Qm2YmiOZSvgs2nvtzZTKGcVYh

▶️ 2. Running the Application

mvn clean install
mvn spring-boot:run
Thymeleaf UI: http://localhost:8080/

Swagger UI: http://localhost:8080/swagger-ui/index.html

OpenAPI JSON: http://localhost:8080/v3/api-docs

🔗 3. API Endpoints
All REST endpoints are under /api/employees.

Method	Endpoint	Description	Request Body (JSON)	Response
GET	/api/employees	List all employees	
GET	/api/employees/{id}	Get employee by ID	
POST	/api/employees	Create new employee	{ firstName, lastName, mobile, gender, salary }	201 Created 
PUT	/api/employees/{id}	Update existing employee	{ firstName, lastName, mobile, gender, salary }	200 OK 
DELETE	/api/employees/{id}	Delete employee by ID	―	204 No Content

🔄 Sample Requests & Responses
POST /api/employees

json
Copy
Edit
Request:
{
  "firstName": "Pradeep",
  "lastName":  "Palanisamy",
  "mobile":    "9876543210",
  "gender":    "Female",
  "salary":    75000
}

Response (201 Created):
{
  "id": 1,
  "firstName": "Pradeep",
  "lastName":  "Palanisamy",
  "mobile":    "9876543210",
  "gender":    "Female",
  "salary":    75000
}
PUT /api/employees/1

json
Copy
Edit
Request:
{
  "firstName": "Pradeep",
  "lastName":  "Palanisamy",
  "mobile":    "9876543210",
  "gender":    "Female",
  "salary":    100000
}

Response (200 OK):
{
  "id": 1,
  "firstName": "Pradeep",
  "lastName":  "Palanisamy",
  "mobile":    "9876543210",
  "gender":    "Female",
  "salary":    100000
}
✅ 4. Data Validation Rules
Use Hibernate Validator annotations on your Employee entity:

Field	Annotations	Description
firstName	@NotBlank, @Size(min=2, max=50)	Required, 2–50 characters
lastName	@NotBlank, @Size(min=2, max=50)	Required, 2–50 characters
mobile	@NotBlank, @Pattern(regexp="\\d{10}")	Exactly 10 digits
gender	@NotBlank, `@Pattern(regexp="Male	Female
salary	@NotNull, @PositiveOrZero	Zero or positive number

When validation fails, the API returns 400 Bad Request with a JSON body like:

{
  "timestamp": "2025-06-09T21:00:00",
  "status": 400,
  "errors": [
    "firstName: must not be blank",
    "mobile: must match \"\\d{10}\""
  ]
}
🖥 5. Swagger (OpenAPI) Documentation
5.1 Add to pom.xml

<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.7.0</version>
</dependency>

5.2 Access
Swagger UI: http://localhost:8080/swagger-ui/index.html

OpenAPI JSON: http://localhost:8080/v3/api-docs

Swagger UI allows you to explore endpoints, view models, and execute requests interactively.

📬 6. Postman Collection
Export from Swagger UI

In Swagger UI, click “Export” → “Download JSON” to get the OpenAPI spec.

Import into Postman

In Postman: File → Import → Upload the downloaded JSON.

You’ll get a collection with folders for each endpoint.

Or manually create requests in Postman:

GET All

GET http://localhost:8080/api/employees

GET by ID

GET http://localhost:8080/api/employees/{{id}}

POST

POST http://localhost:8080/api/employees

Body: raw JSON

PUT

PUT http://localhost:8080/api/employees/{{id}}

Body: raw JSON

DELETE

DELETE http://localhost:8080/api/employees/{{id}}

Headers for POST/PUT:
Content-Type: application/json
Accept: application/json

🚀 7. Running Tests

mvn test
Service tests: use Mockito

Repository tests: @DataJpaTest against a MySQL test schema (or H2)

Controller tests: @SpringBootTest + @AutoConfigureMockMvc (Thymeleaf)

RestController tests: @WebMvcTest + MockMvc

