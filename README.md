# Rest Countries

REST API using Java, Spring Boot

### How to run

To run Spring Boot application. In project root directory

```shell
mvn spring-boot:run
```

To switch between saving data to file and reading from it to getting data from API  
Navigate to :
```src/main/resources/application.properties```  
Change:```Spring.sample.source-property=file``` to ```Spring.sample.source-property=api```

Choosing file path: ```spring.sample.path-property=```"path"

### Endpoints

```http://localhost:8080/write``` writes data to file  
```http://localhost:8080/top-population``` get Top 10 EU Countries by population  
```http://localhost:8080/top-area``` get Top 10 EU Countries by area  
```http://localhost:8080/top-density``` get Top 10 EU Countries by density


# restCountries
