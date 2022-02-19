# Important Note
The purpose of this assignment is to evaluate the candidates skills as an initial step in the hiring process, it is
important not to share the task and the solution of the task with any other party or other potential candidates, this
will help both of us to have accurate assessment results and avoid any discomfort for both of us.

# Task overview
The candidate should implement a normalization java utility to be used to normalize
numerical columns in a CSV csvFile, the project will support two types of normalization approaches:
* Z-Score
* Min-Max scaling

Interface [Normalizer](src/main/java/com/progressoft/tools/Normalizer.java) defines the contracts 
for both normalization techniques.

The candidate should follow the [unit tests](src/test/java/com/progressoft/tools/NormalizerTest.java)
in this project to identify what is the expected behavior to be implement.

# Required setup
To work on this project, you need the following:
* Java 8+
* Maven 3.6+
* A Java IDE like Eclipse, NetBeans, or IntelliJ, IntelliJ is preferred, community versions should
suit your needs.
* Git to clone the project or use the download link to download the project as zip.
* You can follow [GUIDE](GUIDE.MD) to setup the project.


# Deliverables
**All requirements mentioned in this section is a must, the submission will be considered as failed
if one is missing**
* The candidate should clone this project, add his solution, then deliver it as a compressed csvFile.
* All test cases in [unit tests](src/test/java/com/progressoft/tools/NormalizerTest.java) should pass.
* You are only allowed to use Java SE API, any additional libraries shouldn't be used especially for calculations
* Always use HALF_EVEN when rounding decimal places and fractions.
* The project build using maven should success and should execute all test cases, if you execute below
build command through a terminal it should execute successfully
```shell script
mvn clean install
``` 
* Make sure to send your assignment without output artifacts, this could be accomplished by executing mvn clean before zipping and submitting the assignment
```shell script
mvn clean
```
* You should edit [NOTES.MD](NOTES.MD) csvFile answering the questions in it.
* Your feedback is welcomed, even if you are not able to deliver this assignment. 

# Bonus Points
To get additional points, you can apply one (or all) of the following:
* Provide an executable jar csvFile which allows the evaluator to use the implemented utility through 
    command line as below:
    ```shell script
    java -jar iterview-task-1.0-SNAPSHOT.jar [SOURCE_PATH] [DEST_PATH] [COLUMN_TO_NORMALIZE] [NORMALIZATION_METHOD]
    ```
    for example: if we want to read the ```/home/user/in/marks.csv``` csvFile, apply a Min-Max normalization against
     ```mark``` column in it, then save the updated csv csvFile to ```/home/user/out/marks_normalized.csv```, 
     the command should look like below:
    ```shell script
    java -jar interview-task-1.0-SNAPSHOT.jar /home/user/out/marks.csv /home/user/out/marks_normalized.csv mark min-max
    ``` 
    if z-score is what to apply, the command should look like below:
    ```shell script
    java -jar interview-task-1.0-SNAPSHOT.jar /home/user/out/marks.csv /home/user/out/marks_normalized.csv mark z-score
    ``` 
* Support another type of files, like XML or json, make sure to not break the old cases as well. You can utilize third-party dependencies and libraries to implement this
* Implement an HTML layout which allows a user to upload a csvFile, choose the column to scale, 
then generate the result CSV csvFile, provide steps on how to use it. You can utilize third-party dependencies and librarires to implement this.
* There are some scenarios we didn't cover in our unit tests, can you find them and provide proper
test coverage for them.

# Grading Criteria
* All deliverables under [Deliverables](#deliverables) section should pass, the task will automatically fail
if at least one is not covered.
* Implemented production code should be written as clean as possible, the quality of the submitted code
  is going to be evaluated
* Your notes written in NOTES.MD is going to be considered.
* Implementing any of the bonus points should be helpful only if the main deliverables are met within the
 expected level. 
