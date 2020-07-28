<h1 align = "center"> WEB APP Booking apartments </h1>

## About

This is an web application for booking apartments and rooms with reliance on the Jersey RESTful Web Services framework on backend, and the Progressive JavaScript Framework-Vue.js on frontend.  

## Specification

Web application for a system that supports apartment reservations (such as Airbnb
application). Using three user groups (roles): Guest, Host and Administrator.
The application handles the following entities:

#### ● User

- Username (unique)

- Password

- Name

- Surname

- Pol

- Role (Administrator, Home, Away)

- Apartments for rent (if the user is the Host)

- Rented apartments (if the user is a Guest)

- Reservation list (if the user is a Guest)

#### ● Location

- Latitude

- Longitude

- Address

#### ● Address

- in the format: Street and number, City, Postal code (eg Sutjeska 3, Novi Sad
21000)

#### ● Apartment

- Tip (whole apartment, room)

- Number of rooms

- Number of guests

- Location

- Release dates (set by Hosts)

- Availability by dates (automatically generated by the system after each date change for
issuance, creation of reservations or cancellation of reservations, system of forming lists on bases
date of issue and created provisions) 

- Host

- Comments for the apartment offered by guests who have visited the apartment

- Pictures

- Price per night

- Check-in time (initially 14:00)

- Check-out time (initial 10:00)

- Status (Active or Inactive)

- List of apartment contents (Benefits)

- Reservation list


#### ● Contents of the apartment 

- id

- Name (eg parking, kitchen, iron, washing machine, etc.)


#### ● Reservation

- Apartment that is reserved

- Booking start date

- Number of nights (initially 1 night)

- Total price

- Booking message

- Guest

- Status (Created, Rejected, Cancel, Accepted, Completed)

Note: The guest can book a number of free / available dates only if they do not have them
interrupts

#### ● Comment for the apartment

- Guest who left a comment

- Apartment to which the comment refers

- Text

- Rating for the apartment

## Non-functional requirements and notes

● Administrators are programmatically loaded from a text file and cannot be retrieved later
add. Hosts can only be created by Administrators.

● All data changes must be saved in a file immediately after the operation is performed

● Functionality is available to a user type only if there is a described functionality for that
user type

● Deletion is logical.

● If invalid arguments are sent from the client, there must be a response

  ○ Status 400 Bad Request

  ○ Error message in the body (HTML with error for JSP / Servlet, JSON for everything else)

● If a client tries to access a resource for which it does not have permission, it must have an answer

  ○ Status 403 Forbidden
  
  ○ Example: A guest tries to access a page to add the contents of an apartment that
    is the sole responsibility of the administrator.
  

## Functionalities

#### ● Registration
![SingUp](https://user-images.githubusercontent.com/49925421/88675446-3e56f680-d0eb-11ea-9e86-44c1947a8d59.jpg)

● As a non-logged in user:

○ If I want to register:

■ It is necessary to enter a unique username, first name, last name, gender, password
and a password control field

■ If a field is left blank or the passwords do not match, next to
an error message is printed in the corresponding field

■ Pressing the send button sends a request to the server

■ In case of successful creation of a new Guest (username is
unique and the Guest has been successfully created and logged into the application):

● The user is redirected to view the page accordingly
his role

■ In case of failure to create a new Guest, an error message is displayed


#### Log in

![LogIn](https://user-images.githubusercontent.com/49925421/88675620-63e40000-d0eb-11ea-9635-0d2568c4fe70.jpg)

● As a non-logged in user:

○ If I want to log in:

■ Enter the username and password in the fields provided for logging in

■ Both fields must be filled in, otherwise they are next to an empty field
prints an error message

■ Pressing the send button sends a request to the server

■ In case of successful login, the user is redirected to the overview
pages in accordance with its role

■ If the login fails, an error message and the user are displayed
remains on the login page

#### Logout

![logOut](https://user-images.githubusercontent.com/49925421/88675872-b6252100-d0eb-11ea-8254-f955c0bcacd6.jpg)

● As a logged-in user of any type

  ○ I have the option to log out of the system

  ○ Pressing the send button sends a session invalidation request to the server

  ○ Upon successful logout, the login page opens
  
#### Modification of personal data

![Profile](https://user-images.githubusercontent.com/49925421/88676776-c7226200-d0ec-11ea-84f6-d47596cceb5a.jpg)

 ● As a logged in user of any type:

 ○ I have insight into my personal data

 ○ I can change my personal data as well as my password (except username)

 ■ All changes must be valid - if a field is not filled in or passwords
are not appropriate (old is not good or new and control are not the same), in addition
an error message is printed in the corresponding field

 ■ Pressing the send button sends a change request to the server

 ■ In the event of a successful change, the user is notified

 ■ In case of unsuccessful change of the data to the user, an error is printed
