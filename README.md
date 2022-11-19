# JsonFileParse

JsonFileParser - Recruitment task - 3 days (+ 3 days post deadline)

### Program purpose

Parse data in file `src/main/resources/json/original/world_bank.json` and answer following question:

1. Which top 10 Regions had (descending):
    1. most Projects (Amount of Projects)
    2. largest sum of Total Projects' Cost (Total Project Cost Amount)
    3. largest sum of Lending Projects' Cost (Lending Project Cost)
    4. largest sum of Projects' Total IDA and IBRD Commitment Amount
    5. largest sum of Projects' IDA Commitment Amount
    6. largest sum of Projects' IBRD Commitment Amount
2. Which top 10 Countries had (ascending):
    1. least Projects (Amount of Projects)
    2. smallest sum of Total Projects' Cost (Total Project Cost Amount)
    3. smallest sum of Lending Projects' Cost (Lending Project Cost)
    4. smallest sum of Projects' Total IDA and IBRD Commitment Amount
    5. smallest sum of Projects' IDA Commitment Amount
    6. smallest sum of Projects' IBRD Commitment Amount

### How to run

1. run tests:  
   `mvn clean test`

2. create jar file:  
   `mvn clean package -DskipTests`

3. execute jar file:  
   `java -jar target/JsonFileParser_complete_standalone.jar`

### Tech stack

1. Java 11
2. Maven 3.6.3
3. Jackson
4. JUnit 5