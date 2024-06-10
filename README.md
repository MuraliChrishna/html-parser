# Project Setup Instructions

## Requirements

IDE: Preferably IntelliJ IDEA

Build Management Tool: Maven

## Installation Instructions

### Install Homebrew

If Homebrew is not already installed on your MacBook, install it using the following command:

```sh
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"
```

### Install Maven

After installing Homebrew, you can install Maven using Homebrew with the following command:

```sh
brew install maven
```

### Verify Maven Installation

To check if Maven has been installed correctly, verify the Maven version using the command:

```sh
mvn -version
```

You should see output similar to the following:

```sh
bpolisetti@Balas-MBP ~ % mvn -version 
Apache Maven 3.9.7 (8b094c9513efc1b9ce2d952b3b9c8eaedaf8cbf0)
Maven home: /opt/homebrew/Cellar/maven/3.9.7/libexec
Java version: 21.0.3, vendor: Homebrew, runtime: /opt/homebrew/Cellar/openjdk/21.0.3/libexec/openjdk.jdk/Contents/Home
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "14.5", arch: "aarch64", family: "mac"
```

## Clone the repo

Clone the repo using

```git 
git@github.com:MuraliChrishna/html-parser.git
```

## Update file paths and log path.

### Update Logback Configuration

Open the logback.xml file located in the src/main/resources directory.

Locate line 5 and update it to your preferred log location. Example:

```xml
<file>/Users/bpolisetti/IdeaProjects/logs/html-parser/html-parser.log</file>
```

### Update FileReader.java

Open FileReader.java located in the html-parser/src/main/java/com/bala/parser/filereader director

Update the default file read path to your local path where you will be providing the file to be parsed.

Example:

```java
private static final String DEFAULT_READ_PATH = "/Users/bpolisetti/IdeaProjects/parserExamples/input.txt";
```

### Update FileWriter.java

Open FileWriter.java located in the html-parser/src/main/java/com/bala/parser/filewriter director

Update the default file write path to your local path where you will be getting the output file after parsed.

Example:

```java
private static final String DEFAULT_WRITE_PATH = "/Users/bpolisetti/IdeaProjects/parserExamples/output.html";
```

## Run Instructions

Open IntelliJ IDEA : Launch your IDE.

Import Project : Open the project as a Maven project (or) select Get From VCS on the open prompt and use git ssh to clone the project.

Run Maven Commands : Use Maven commands like mvn clean install to build.

## Sampe rest calls:

### API Usage Instructions
To use the API, follow these steps:

Run the Application: Start your Spring Boot application using your IDE or by running the following command in the terminal:

```sh
mvn spring-boot:run
```

Access the API Endpoint: Open a web browser or use a tool like curl or Postman to access the following endpoint:

```sh
http://localhost:8080/api/parse
```

Make sure your FileReader.java is set up to read the input file from the correct location.

View Output: You should see the output based on the input provided on your screen.

Example using curl:

```sh
curl -X GET http://localhost:8080/api/parse
```
