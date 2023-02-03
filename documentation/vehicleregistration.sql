-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 03, 2023 at 11:11 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vehicleregistration`
--

-- --------------------------------------------------------

--
-- Table structure for table `insurance`
--

CREATE TABLE `insurance` (
  `insuranceid` int(11) NOT NULL,
  `dateregistered` date DEFAULT NULL,
  `expirationdate` date DEFAULT NULL,
  `company` int(11) DEFAULT NULL,
  `fee` float DEFAULT NULL,
  `vehicle_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `insurance`
--

INSERT INTO `insurance` (`insuranceid`, `dateregistered`, `expirationdate`, `company`, `fee`, `vehicle_id`) VALUES
(1, '2022-11-15', '2023-11-15', 1, 2500, 1),
(2, '2023-02-01', '2024-02-01', 0, 2700, 2),
(3, '2022-12-21', '2023-12-21', 2, 5200, 3),
(4, '2023-01-03', '2024-01-03', 3, 3800, 4),
(5, '2022-08-24', '2023-08-24', 2, 5200, 5),
(6, '2023-01-20', '2023-01-20', 0, 3700, 6),
(7, '2021-08-01', '2022-08-01', 0, 2500, 7),
(8, '2022-09-20', '2023-09-20', 0, 2500, 8),
(9, '2022-03-02', '2023-03-02', 3, 4700, 9),
(10, '2023-01-23', '2024-01-23', 0, 3400, 10),
(11, '2023-01-29', '2024-01-29', 1, 5300, 11),
(12, '2021-11-24', '2022-11-24', 1, 2700, 12),
(13, '2022-12-21', '2023-12-21', 0, 2500, 13),
(14, '2022-12-21', '2023-12-15', 2, 5400, 14),
(15, '2022-01-15', '2023-01-15', 0, 2000, 16);

-- --------------------------------------------------------

--
-- Table structure for table `owner`
--

CREATE TABLE `owner` (
  `ownerid` int(11) NOT NULL,
  `dateofbirth` date DEFAULT NULL,
  `firstname` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `licensedate` date DEFAULT NULL,
  `placeofbirth` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `owner`
--

INSERT INTO `owner` (`ownerid`, `dateofbirth`, `firstname`, `gender`, `lastname`, `licensedate`, `placeofbirth`) VALUES
(1, '2000-05-01', 'Vullnet', 'Male', 'Azizi', '2018-10-28', 'Gostivar'),
(2, '1999-04-12', 'Veton', 'Male', 'Fisniku', '2020-12-01', 'Tetovo'),
(3, '2001-01-13', 'Azra', 'Female', 'Xhelili', '2022-03-08', 'Skopje'),
(4, '1990-06-30', 'Albert', 'Male', 'Nesimi', '2008-05-18', 'Gostivar'),
(5, '1995-12-09', 'Marko', 'Male', 'Petrov', '2015-01-08', 'Ohrid'),
(6, '2002-11-19', 'Milena', 'Female', 'Nikolov', '2022-02-15', 'Skopje'),
(7, '1997-10-12', 'Fati', 'Male', 'Kamberi', '2016-09-29', 'Debar'),
(8, '1998-07-25', 'Elife', 'Female', 'Sadiku', '2021-10-30', 'Tetovo'),
(9, '2000-08-29', 'Elena', 'Female', 'Ivanova', '2018-12-15', 'Kumanovo'),
(10, '1980-10-20', 'Artan', 'Male', 'Ibraimi', '1995-03-07', 'Struga'),
(11, '1999-01-03', 'Aleksandar', 'Male', 'Ristevski', NULL, 'Prilep'),
(12, '2001-05-15', 'Petrit', 'Male', 'Naseri', '2020-08-07', 'Tetovo'),
(13, '2003-06-07', 'Jehona', 'Female', 'Xhaferi', NULL, 'Gostivar'),
(14, '2002-03-17', 'Hakan', 'Male', 'Musliu', NULL, 'Struga'),
(15, '1992-11-27', 'Fjolla', 'Female', 'Rexhepi', '2022-11-15', 'Gostivar');

-- --------------------------------------------------------

--
-- Table structure for table `vehicle`
--

CREATE TABLE `vehicle` (
  `vehicleid` int(11) NOT NULL,
  `category` varchar(255) DEFAULT NULL,
  `dateregistered` date DEFAULT NULL,
  `expirationdate` date DEFAULT NULL,
  `fuel` varchar(255) DEFAULT NULL,
  `licenseplate` varchar(255) DEFAULT NULL,
  `manufacturer` varchar(255) DEFAULT NULL,
  `model` varchar(255) DEFAULT NULL,
  `power` int(11) DEFAULT NULL,
  `transmission` varchar(255) DEFAULT NULL,
  `year` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vehicle`
--

INSERT INTO `vehicle` (`vehicleid`, `category`, `dateregistered`, `expirationdate`, `fuel`, `licenseplate`, `manufacturer`, `model`, `power`, `transmission`, `year`, `user_id`) VALUES
(1, 'Hatchback', '2022-11-15', '2023-11-15', 'Diesel', 'GV5712AD', 'Opel', 'Corsa', 90, 'Manual', 2007, 1),
(2, 'Hatchback', '2023-02-01', '2024-02-01', 'Petrol', 'TE1111WW', 'Audi', 'A3', 120, 'Automatic', 2009, 5),
(3, 'SUV', '2022-12-21', '2023-12-21', 'Petrol', 'ST0099AK', 'BMW', 'X1', 150, 'Semiautomatic', 2015, 10),
(4, 'Sedan', '2023-01-03', '2024-01-03', 'Petrol', 'TE9876QQ', 'Mercedes-Benz', 'GLCA', 220, 'Automatic', 2012, 2),
(5, 'SUV', '2022-08-24', '2023-08-24', 'Diesel', 'OH6645AD', 'Nissan', 'Tilda', 170, 'CVT', 2010, 5),
(6, 'Sedan', '2023-01-20', '2024-01-20', 'Petrol', 'KU1717AD', 'Audi', 'A6', 190, 'Automatic', 2018, 9),
(7, 'Hatchback', '2021-08-01', '2022-08-01', 'Petrol', 'GV4341AK', 'Volkswagen', 'Golf 6', 90, 'Manual', 2009, 15),
(8, 'Hatchback', '2022-09-20', '2023-09-20', 'Diesel', 'DB5016AD', 'Renault', 'Clio', 70, 'Manual', 2002, 7),
(9, 'Van', '2022-03-02', '2023-03-02', 'Diesel', 'GV8111AD', 'Mercedes-Benz', 'Sprinter', 130, 'Manual', 2020, 4),
(10, 'Coupe', '2023-01-23', '2024-01-23', 'BioDiesel', 'TE9377AD', 'BMW', '2-Series', 380, 'Automatic', 2022, 12),
(11, 'SUV', '2023-01-29', '2024-01-29', 'Petrol', 'SK8771AK', 'Audi', 'A7', 250, 'Automatic', 2020, 6),
(12, 'Hatchback', '2021-11-24', '2022-11-24', 'Diesel', 'SK2331AK', 'Toyota', 'Yaris', 110, 'Automatic', 2016, 3),
(13, 'Hatchback', '2022-12-21', '2023-12-21', 'Petrol', 'TE5411AK', 'Opel', 'Astra', 90, 'Manual', 2013, 8),
(14, 'SUV', '2022-12-15', '2023-12-15', 'Petrol', 'OH23455AK', 'BMW', 'X5', 330, 'Automatic', 2012, 5),
(16, 'Motorcycle', '2022-01-15', '2023-01-15', 'Diesel', 'TE1565AA', 'Yamaha', 'FJR', 550, 'Automatic', 2012, 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `insurance`
--
ALTER TABLE `insurance`
  ADD PRIMARY KEY (`insuranceid`),
  ADD KEY `FKsm0ii1uxjqx9am3xf216r0a63` (`vehicle_id`);

--
-- Indexes for table `owner`
--
ALTER TABLE `owner`
  ADD PRIMARY KEY (`ownerid`);

--
-- Indexes for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD PRIMARY KEY (`vehicleid`),
  ADD KEY `FKl5ytl7noobtlh5e8vrksra98t` (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `insurance`
--
ALTER TABLE `insurance`
  MODIFY `insuranceid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `owner`
--
ALTER TABLE `owner`
  MODIFY `ownerid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `vehicle`
--
ALTER TABLE `vehicle`
  MODIFY `vehicleid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `insurance`
--
ALTER TABLE `insurance`
  ADD CONSTRAINT `FKsm0ii1uxjqx9am3xf216r0a63` FOREIGN KEY (`vehicle_id`) REFERENCES `vehicle` (`vehicleid`);

--
-- Constraints for table `vehicle`
--
ALTER TABLE `vehicle`
  ADD CONSTRAINT `FKl5ytl7noobtlh5e8vrksra98t` FOREIGN KEY (`user_id`) REFERENCES `owner` (`ownerid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
