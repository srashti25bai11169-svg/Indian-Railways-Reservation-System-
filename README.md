# 🚆 Core Java Rail Reservation System

## 📝 Project Overview

The **Rail Reservation System** is a terminal-based railway management application developed using **Core Java**. The system provides essential railway reservation facilities for passengers and administrative controls for managing trains and monitoring reservations.

The project does not depend on MySQL or any external database. Instead, **Java File Handling** is used to store application data in text files. This allows user, train, passenger, and ticket information to remain stored even after the application is closed.

## 🎯 Project Objectives

The primary objectives of this project are to:

* Develop a practical railway reservation system using Core Java.
* Apply important Object-Oriented Programming concepts in a real-world application.
* Separate passenger and administrator functionalities.
* Enable users to search trains and book railway tickets.
* Generate unique PNR numbers automatically.
* Assign available seats during ticket booking.
* Store application records using text-based file storage.
* Implement custom exceptions for railway-specific errors.

## 👤 User Functionalities

A registered user can perform the following operations:

* Register a new account.
* Log in with valid credentials.
* Search for trains.
* View the list of registered trains.
* Check seat availability.
* Book a railway ticket.
* Obtain an automatically generated PNR number.
* Get an automatically assigned seat number.
* Search ticket information using PNR.
* View previous booking details.
* Cancel a reservation.

## 👑 Administrator Functionalities

The administrator module provides control over railway and reservation data. An administrator can:

* Log in using administrator credentials.
* Add new trains to the system.
* Update existing train information.
* Delete train records.
* View all registered trains.
* View booking and reservation records.

## 🧠 Java Concepts Used

The project combines several Core Java concepts, including:

* Classes and Objects
* Encapsulation
* Inheritance
* Abstraction
* Polymorphism
* Interfaces
* Method Overriding
* Comparable Interface
* ArrayList
* Collections Framework
* File Input/Output
* Exception Handling
* User-Defined Exceptions
* User-Defined Packages

## 🚆 Train Management

Train information is maintained through the administrator module. Administrators can add, edit, and delete train records according to the requirements of the system.

Every train contains information such as:

* Train Number
* Train Name
* Source Station
* Destination Station
* Total Seat Capacity
* Ticket Fare

The `Train` class implements `Comparable<Train>`, which allows train objects to be sorted according to their train numbers in ascending order.

## 🎫 Ticket Reservation

During the reservation process, the system stores important ticket-related information, including:

* PNR Number
* User ID
* Train Number
* Passenger ID
* Seat Number
* Fare
* Booking Status

For every successful booking, the system automatically creates a unique PNR. It also searches the selected train for an available seat and assigns it to the passenger.

## 💺 Seat Management

Before completing a reservation, the application checks the confirmed bookings for the selected train.

If no seat is available, the system generates a `SeatNotAvailableException` rather than allowing an invalid reservation.

When a ticket is cancelled, its seat becomes available again and may be assigned to another passenger in a future booking.

## ⚠️ Exception Handling

Custom exceptions are used to handle railway-specific problems and make error handling easier to understand.

The project includes:

* `TrainNotFoundException`
* `SeatNotAvailableException`
* `TicketNotFoundException`

These exceptions are used when a train, seat, or ticket requested by the user cannot be found or is unavailable.

## 💾 Data Storage

The project uses Java's built-in file handling mechanism instead of an external database.

The following Java classes are used for file operations:

* `File`
* `FileReader`
* `FileWriter`
* `BufferedReader`
* `BufferedWriter`

All application records are stored as text files inside the `data` directory. This provides simple persistence without requiring database installation or configuration.

## 📂 Project Structure

```text
RailReservationSystem/

│
├── src/
│   ├── Main.java
│   ├── model/
│   │   ├── Person.java
│   │   ├── Train.java
│   │   ├── Passenger.java
│   │   ├── Ticket.java
│   │   └── User.java
│   │
│   ├── service/
│   │   ├── ReservationSystem.java
│   │   ├── TrainService.java
│   │   ├── TicketService.java
│   │   └── UserService.java
│   │
│   ├── exception/
│   │   ├── SeatNotAvailableException.java
│   │   ├── TrainNotFoundException.java
│   │   └── TicketNotFoundException.java
│   │
│   └── util/
│       ├── FileManager.java
│       ├── InputHelper.java
│       └── PNRGenerator.java
│
├── data/
│   ├── users.txt
│   ├── trains.txt
│   ├── tickets.txt
│   └── passengers.txt
│
└── README.md
```

## 📦 Package Organization

### `model`

The `model` package contains the primary entities used by the railway system. These include:

* `Person`
* `User`
* `Passenger`
* `Train`
* `Ticket`

These classes represent the data and properties associated with users, trains, passengers, and reservations.

### `service`

The `service` package contains the main business logic of the application.

It includes functionality for:

* User management
* Train management
* Ticket reservation
* Reservation processing

### `exception`

The `exception` package contains custom exception classes designed specifically for handling errors related to trains, seats, and tickets.

### `util`

The `util` package contains supporting classes used throughout the application, such as:

* File management
* Input processing
* PNR generation

## 💻 Technologies and Tools

| Technology / Concept | Purpose                               |
| -------------------- | ------------------------------------- |
| Core Java            | Main programming language             |
| OOP                  | Designing the application structure   |
| ArrayList            | Storing and managing records          |
| Comparable           | Sorting train objects                 |
| File Handling        | Saving and retrieving persistent data |
| Exception Handling   | Handling application errors           |
| Terminal             | Providing the command-line interface  |

## 🚀 How to Run the Application

Open the terminal inside the `src` directory and compile all Java files using:

```text
javac Main.java model\*.java service\*.java exception\*.java util\*.java
```

After compilation is completed successfully, execute the application with:

```text
java Main
```

## 🔐 Default Administrator Credentials

The application provides a default administrator account:

* **User ID:** `A001`
* **Username:** `admin`
* **Password:** `admin123`
* **Role:** `ADMIN`

## 📊 Data Files

Different categories of information are maintained in separate files:

* `users.txt` — stores registered users and administrator information.
* `trains.txt` — contains train details.
* `tickets.txt` — stores reservation and ticket information.
* `passengers.txt` — maintains passenger records.

## 🔮 Future Enhancements

The project can be further developed by adding features such as:

* Integration with MySQL or another database.
* Development of a graphical user interface.
* Conversion into a web-based railway reservation system.
* Password encryption for improved security.
* Online payment integration.
* Complete train timetable and schedule management.
* Different classes and seating categories.
* Email or SMS notifications for reservations.
* Group booking for multiple passengers.

## 📌 Conclusion

The **Rail Reservation System** demonstrates how Core Java can be applied to develop a practical command-line application based on a real-world railway reservation scenario.

The project combines **Object-Oriented Programming, Collections, Comparable, File Handling, Exception Handling, Custom Exceptions, and User-Defined Packages** into a single modular application.

Its package-based architecture makes the system easier to understand, maintain, and extend with additional railway-related functionality in the future.

## 👩‍💻 Author

**Name: Srashti Tomar**

**Registration No.: 25BAI11169**
