### Flink Job Counting words "Ola"

This is an example of a Flink Application to count the number of words Ola that was received by the web socket connection established to the application.

## Project Structure

The project was developed using scala 2.12 and maven.

## Generate application binary

To generate the application binary you can execute: 
```
mvn clean package
```
in in the folder `/target` will exists the jars generated and could be deployed in the Flink Cluster.

## Send text to the application
After the application is up and running in the cluster you can interact with the job using the command tool netcat:

```
nc -lk 9999
```
