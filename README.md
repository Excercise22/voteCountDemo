# evote project

A demo of a evoting system implementation in java and built using maven. Tests written using Mockito

## Instructions

### How to install and run from command line
1. Change the current working directory to the location where you want the cloned directory.
2. Clone github repository <https://github.com/Excercise22/voteCountDemo.git> using following command
   $ git clone https://github.com/Excercise22/voteCountDemo.git
3. Press Enter to create your local clone.
4. Change the directory to voteCounterDemo directory where you got the cloned artifacts.
5. compile the project and generate target folder
   mvn compile
5. Run following command to do clean build and to install it into local maven repository
   mvn clean install
6. Execute the project
   mvn exec:java -Dexec.mainClass=com.oracle.interview.evote.VotingTallyApplication -Dexec.args="src/main/resources/candidates.txt"

### Test scenarios
1. ballot validation and creation
2. tally service running end to process on variety of ballots set.
3. src/test/java/com/oracle/interview/evote/data - contains variety of ballots (dataset to test different scenarios)
4. src/main/resources/candidates.txt - contains candidates(options) presented in assignment. 

## Notes
1. Filename provided in program argument at startup is a path from content root of voteCountDemo project
2. Test scenarios covers use cases outlined in assignment and not from the code coverage standpoint as this is just a prototype.
3. Please visit src/test/java/com/oracle/interview/evote/data to get more context around covered use cases.
4. Versions/Tools used on local
      ###### Java 18.0.2 SDK
      ###### Maven 3.8.1
      ###### Eclipse
5. Ballots sample that triggers exhausted vote scenario
ABDC
BAD
BAC
CABD
CDAB
CBAD 
DA 
DB
Tally
