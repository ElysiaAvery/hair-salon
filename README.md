# Sweet Tamarind Hair Salon
![project screenshot](/screenshot.png)

Version 0.0.0: September 23, 2016

by [Elysia Avery Nason](https://github.com/elysiaavery)

## Description
A program that allows the user to add a word, as well as multiple definitions. [here.](https://elysiaavery.github.io/hair-salon/)

### Specifications
| Behaviour                                                  | Input                                                                | Output                                                               |
|------------------------------------------------------------|----------------------------------------------------------------------|----------------------------------------------------------------------|
| Initiates Stylist & Client Objects.                        | n/a                                                                  | n/a                                                                  |
| Stylist & Client object returns property values.           | Stylist: String "name", Client: String "name", String "phone number" | Stylist: String "name", Client: String "name", String "phone number" |
| Stylist & Client return all instances of Stylist & Client. | Stylist1, Stylist2, Stylist3...                                      | "Corinne","Maureen","Selena"...                                      |
| Instantiates instances of Stylist & Client with ID's.      | stylist.getId() , client.getId()                                     | "Maureen" , "Robert", "503-333-3333"                                 |
| Add Clients to Stylist                                     | stylist.getClients()                                                 | "Maureen" , "Robert", "503-333-3333"                                 |
| Add updates to instances of Stylist & Client.              | stylist.update()                                                     | name: "Mo", name: "Maureen"                                          |
| Able to delete instances of Stylist & Clients.             | stylist.delete()                                                     | name:"Maureen", null                                                 |


## Setup/Installation
* Clone directory
* In PSQL: CREATE DATABASE hair_salon;
* Now Enter this command in your terminal: psql media < media.sql
* In PSQL: \c hair_salon;
* In Terminal: gradle run
* navigate to localhost:4567 in your choice of browser(Chrome is preferred).

## Support & Contact
For questions, concerns, or suggestions please email elysia.avery@gmail.com

## Known Issues
* None known of yet.

## Technologies Used
Java, Gradle, junit, Spark, Velocity Template Engine

## Legal
*Licensed under MIT open source*

Copyright (c) 2016 Copyright _Elysia Avery Nason_ All Rights Reserved.
