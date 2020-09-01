# AutoCompletionService

This repo implements a REST API with exception handling and testing that provides auto-complete suggestions for cities.

## How to run?
Step-1: Clone the project
Step-2: Go inside the project directory, then run the follwoing commands.
Step-3: $mvn package
Step-4: ./mvnw spring-boot:run


This is a simple project but covers multiple things such as collecting raw data, writing RESTful API, handling exception, and doing few unit tests. First, it collects raw data from a CSV file automatically using **spring-boot-starter-batch** module. The conversion from a text file to csv file is done manually. Thereafter, the required processed data is saved into in-memory **h2database**. 

Then the project is desinged using layred architecture such as web layer, service layer, data layer. The custom error and exception handling are managed seperately keeping a view in mind that we can handle any type of errors in more managed way. The unit testing is done using the tool **Junit 5** and other testing library.

Using the following REST API endpoint, you can test the project.
    GET http://localhost:8080/suggestions?q=Londo
    GET GET /suggestions?q=Londo&latitude=43.70011&longitude=-79.4163
 
In any other formats except the above two, the server will respond with a custom error message.

To provide auto-complete suggestion, I calculate confidence score in a scale between 0 and 1. The confidence score is the sum of **user distance** score and  **text maching** score. In this case, user distance has been given more priority(7) and text-matching has been given less priority(3). To find out the number of matching and different characters, I use **diff-match-patch** library. We can consider more field and need to invest time in algorithm to get the better confidence score which will ultimately provide a better customer service.  
    



If you have any questions and suggestion, please feel free contact me at azizur.ice@gmail.com

Thank you
