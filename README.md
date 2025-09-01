This project is a complete, full-stack web application for managing tasks, built from the ground up to showcase a complete development workflow. It provides a clean and responsive interface for creating, editing, and deleting tasks. The application features live updates for task completion statuses, demonstrating a cohesive and functional web solution.

Technology Stack:
Frontend: The user interface is built with HTML, CSS, and vanilla JavaScript. It uses asynchronous fetch requests for live updates without page reloads.
Backend: A robust Java backend, powered by Servlets, handles all business logic, processing requests, and communicating with the database.
Database: MySQL is used for persistent data storage. All database interactions are managed securely using JDBC.
Deployment: The application is packaged as a WAR file and deployed on an Apache Tomcat server.

Database Structure
The application's data is stored in a single table named tasks. Below is the schema and a brief description of each column.
Table: tasks
Field	Type	Attributes	Description
id	int	PRIMARY KEY, AUTO_INCREMENT	A unique identifier for each task.
title	varchar(255)	NOT NULL	The title of the task.
description	varchar(255)		A brief description of the task.
completed	tinyint(1)	NOT NULL, DEFAULT 0	A boolean value (0 or 1) indicating if the task is completed.
