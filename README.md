# Property-Sales-API

This is a Java based REST API that receives data and handles resource queries via HTTP requests. The server utilises Apache Spark along with SQLite and ORMlite for persistent storage. Unit and Integration testing is achieved using JUnit5.

- New Agents are created with an auto-generated id, along with a given name and commission. 
- New Properties can be registered with an auto-generated id along with a given type, address and agent-id.
- New Sales are produced with an auto-generated id, auto-generated date and a given property-id.
- The respective Resources can then be queried using various parameters.

## Installation
```bash
git clone https://github.com/AntoMoore/Property-Sales-API
cd Property-Sales-API
mvn clean install
```

## Running the Program (Windows CLI)
```bash
java -jar .\target\Property-Sales-API-0.0.1-SNAPSHOT-jar-with-dependencies.jar
```

## Using an IDE
The application must be imported as a Maven project. The main method is located in the <b>Application.java</b> class and is contained within the <b>com.app</b> package.

## Testing
The unit and integration tests are implemented using JUnit and are provided in the <b>TestDataBase.java</b>, <b>TestResources.java</b> and <b>TestWebServer.java</b> classes respectively, these can be found inside the <b>com.tests</b> package.

## Example API Calls

#### GET Server Status
```bash
http://localhost:4567/openproperty/status

Returns: Status Code
```

#### POST Agent
```bash
http://localhost:4567/openproperty/agents?name=John Doe&commission=0.5

Returns: Status Code
```

#### GET Agents (No Paramaters)
```bash
http://localhost:4567/openproperty/agents/

Returns: All Agents
```

#### GET Agents using ID Parameter
```bash
http://localhost:4567/openproperty/agents/?id=1

Returns: Agent with matching ID
```

#### POST Property
```bash
http://localhost:4567/openproperty/properties?type=House&address=123 Fake Street&value=95000.00&agentId=1

Returns: Status Code
```

#### GET Properties (No Paramaters)
```bash
http://localhost:4567/openproperty/properties/

Returns: All Properties
```

#### GET Properties using Value Parameter
```bash
http://localhost:4567/openproperty/properties/?value=95000

Returns: All Properties that are less than the given value
```

#### POST Sale
```bash
http://localhost:4567/openproperty/sales?propertyId=1

Returns: Status Code
```

#### GET Sales (No Paramaters)
```bash
http://localhost:4567/openproperty/sales/

Returns: All Sales
```

#### GET Sales using Date Parameter
```bash
http://localhost:4567/openproperty/sales/?date=year

Returns: All Sales made within the previous year
```

## Technology Stack

### Apache Maven 3.8.0
Maven is an automation tool used to build and manage projects. It uses the project object model (POM) to manage dependencies and streamline deployment.

### Apache Spark 2.7.2
Spark is a web application and data processing framework. It can be used to distribute data processing tasks across clusters via mirco-services.

### ORMLite 4.48
ORMLite is a lightweight Object Relational Mapping tool between Java classes and SQL databases.

### SQLite 3.7.2
SQLite is a compact, self-contained SQL database engine. It provides embedded persistent storage that is cross-platform and highly reliable.

### JUnit 5.4.2
Junit is a java based unit testing framework. It is ubiquitous with test driven development that provides an easy to use framework that supports high productivity via test automation.

### Apache HttpComponents 4.5.13
HttpComponents provides a robust and flexible set of tools to easily produce Http requests. 

### Gson 2.3.1
Gson is a Java library used to convert Java Objects into JSON format.

## Future Enhancements
- Re-factor the RequestController class to make it easier to maintain.
- Add image URLs to Properties.
- Implement additional functionality to provide various Sales metrics.
- Write additional test cases to provide better coverage on certain edge cases.
