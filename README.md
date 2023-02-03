# **VehicleRegistrationSystem**


![spring-boot-logo (1)](https://user-images.githubusercontent.com/74537719/216723070-8fe527ac-8c64-4ef9-a4b0-f534b7ed5c3f.jpg)
## Spring Boot Project
This Spring Boot project will represent the BackEnd of a Vehicle Registration System, where the users of the application will have options to register different vehicles corresponding to different owners and will be able to manipulate the data in order to retrieve information based on different requests. Each vehicle that will be registered will be automatically given a registration date and an expiration date on its registration.
In the same way Insurances can be registered to different vehicles, and the payment for each insurance will depend on the vehicle type and horsepower.

This system is similar to the one used in North Macedonia, where each owner can have more than one vehicle to their name, and a vehicle can be registered to only one individual who possesses a driver’s license. Both the vehicles and owners will be identified with a set of attributes, for example a vehicle can be one of many types like a car, a motorcycle, a bus etc.; and the application will not allow certain individuals to have a vehicle registered to their name.

This Spring Boot project is connected to a database using MySQL where the corresponding tables are created, and where the data is stored. When it comes testing the functionality of the project, it will be possible to use open-source API development platforms such as Postman or Insomnia.

## A few things to consider:

**-Before using this Spring Boot project, you need to have a basic understanding of Java, Spring framework, and web development concepts.**

**-This project was developed using Java Development Kit version 11, however it also runs on versions 8, 17 and 19**

**-This project was developed using Spring Boot version 2.7.8**

## Requirements
1. **Java Development Kit** version 8 or later

2. One of the following IDE's:
   * **IntelliJ IDEA**
   * **Eclipse IDE**
   * **Apache NetBeans**
   * **Visual Studio Code**
	 
4. **Spring Boot** version 2.7.8 using **Gradle - Groovy**

5. **MySQL Server** either standalone or through XAMPP

6. **Postman or Insomnia** for testing

## Download
 - You can use this project via Github in two ways:
     1. By **downloading the zip folder** and extracting it to your local machine
		 
     2. By **cloning the repository** to your IDE:
		 
		 In the terminal write:
		 ```
		 git clone https://github.com/VullnetA/VehicleRegistration.git
		 ```
 - After that you can **build** the project using automatically with your IDE or manually via the command:
 
		 gradle build

## Usage
Before running the application make sure you have **MySQL Server** running and ready to go

Go to **```application.properties```** and insert your login username and password (if you have one) to the configuration

And finally you can now **RUN** your project and the tables will be stored in the database

By using **POSTMAN** or **INSOMNIA** you can now test all the various endpoints listed below:

**url = http://localhost:8080**

### Vehicle Controller endpoints:
* Get All Vehicles ```GET url/vehicles```
* Get Vehicle By Id ```GET url/vehicles/{id}```
* Register Vehicle ```POST url/register```
* Delete Vehicle By Id ```DELETE url/delete{id}```
* Edit Vehicle By Id ```PUT url/vehicles/{id}```
* Get Vehicles By Owner ```GET url/ownervehicles/{id}```
* Get Vehicles By Year ```GET url/vehicleyear/{year}```
* Get Vehicles By Power ```GET url/vehiclepower/{power}```
* Get Vehicles By Fuel ```GET url/fuel/{fuel}```
* Get Vehicles By Brand ```GET url/brand/{manufacturer}```

### Owner Controller endpoints:
* Get All Owners ```GET url/owners```
* Get Owner By Id ```GET url/owners/{id}```
* Input Owner ```POST url/input```
* Remove Owner By Id ```DELETE url/remove{id}```
* Get Owner By Vehicle ```GET url/findbycar```
* Get Owner By City ```GET url/owners/{city}```

### Insurance Controller endpoints:
* Get All Insurances ```GET url/insurances```
* Get Insurance By Id ```GET url/insurance/{id}```
* Add Insurance ```POST url/addinsurance```
* Cancel Insurance ```DELETE url/cancel{id}```

### Services Controller endpoints:
* Count Vehicles By Brand ```GET url//countbrand/{manufacturer}```
* Count Unregistered Vehicles ```GET url/countunregistered```
* Count Registered Vehicles ```GET url/countregistered```
* Check Vehicle Registration ```GET url/checkregistration/{id}```
* Count Licenses By City ```GET url/licensebycity/{placeofbirth}```
* Count Insurances ```GET url/countinsurance```
* Count Vehicles by Transmission ```GET url/counttransmission/{transmission}```
* Find Vehicle by License Plate ```GET url/findplate/{licenseplate}```



[CLICK HERE TO VIEW THE DDL SCRIPTS](https://github.com/VullnetA/VehicleRegistration/tree/master/documentation "DDL SCRIPTS")
