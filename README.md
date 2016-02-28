# To Do Task Manager

#### A database app of tasks

#### By Abby Rolling & Kevin Mattison

## Description

This website allows a user to add, delete, and organize tasks by category and due date.

## Setup/Installation Requirements

* Clone this repository.
* In a postgres database named to_do:
- CREATE TABLE categories (id serial PRIMARY KEY, name varchar);
- CREATE TABLE tasks (id serial PRIMARY KEY, description varchar, categoryid int, duedate timestamp);
* Make sure you have Java and Gradle installed.
    * For Java:
        * Download and install [Java SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
        * Download and install [Java JRE](http://www.java.com/en/)
    * For Gradle: if you are using Homebrew on Mac:
        * $ brew update
        * $ brew install gradle
* In the top level of the cloned directory, run the following command in your terminal:
    * $ gradle run
* Open your web browser of choice to localhost:4567

## Technologies Used

* Java
* Gradle
* Spark
* Postgres
* psql
* junit

### License

Licensed under the MIT license.

Copyright (c) 2016 **Abigail Rolling & Kevin Mattison**
