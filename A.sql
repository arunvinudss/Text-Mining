-- phpMyAdmin SQL Dump
-- version 4.7.2
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Oct 06, 2017 at 06:08 AM
-- Server version: 10.1.26-MariaDB
-- PHP Version: 7.1.8

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `vehicle management`
--

-- --------------------------------------------------------

--
-- Table structure for table `BuyVehicle`
--

CREATE TABLE `BuyVehicle` (
  `vin` int(10) NOT NULL,
  `SSN` int(20) NOT NULL,
  `price` int(10) DEFAULT NULL,
  `byear` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `BuyVehicle`
--

INSERT INTO `BuyVehicle` (`vin`, `SSN`, `price`, `byear`) VALUES
(2222, 1015, 89000, 2009),
(4234, 1013, 20900, 2005),
(5434, 1017, 100000, 2006),
(5542, 1013, 20001, 2005),
(6312, 1011, 5000, 2016),
(7098, 1001, 19000, 2000),
(7655, 1003, 10000, 2010),
(7866, 1005, 90000, 2010),
(8075, 1001, 27000, 2009),
(8090, 1001, 250000, 2006),
(8976, 1012, 20000, 2007),
(10000, 1009, 17000, 2006),
(10906, 1020, 2000000, 2015),
(45454, 1005, 29000, 2010),
(67890, 1017, 156000, 2010),
(77777, 1002, 20000, 2007),
(89765, 1014, 10000, 2010),
(99999, 1012, 25000, 2004),
(100422, 1008, 26000, 2008);

-- --------------------------------------------------------

--
-- Table structure for table `Customer`
--

CREATE TABLE `Customer` (
  `SSN` int(20) NOT NULL,
  `cname` char(35) DEFAULT NULL,
  `cgender` char(9) DEFAULT NULL,
  `ccity` char(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Customer`
--

INSERT INTO `Customer` (`SSN`, `cname`, `cgender`, `ccity`) VALUES
(1001, 'Arun', 'Male', 'Virginia'),
(1002, 'John', 'Male', 'Chicago'),
(1003, 'Alex', 'Male', 'Chicago'),
(1004, 'Hannah', 'Female', 'Chicago'),
(1005, 'Jessica', 'Female', 'Chicago'),
(1007, 'Harvey', 'Male', 'Chicago'),
(1008, 'Rachel', 'Female', 'Chicago'),
(1009, 'Louis', 'Male', 'NY'),
(1010, 'Dinesh', 'Male', 'NY'),
(1011, 'Vipul', 'Male', 'NY'),
(1012, 'Gautam', 'Male', 'DC'),
(1013, 'Donna', 'Female', 'Texas'),
(1014, 'Katrina', 'Female', 'Arkansas'),
(1015, 'Zane', 'Male', 'Minneapolis'),
(1016, 'Roy', 'Male', 'Chicago'),
(1017, 'Olivia', 'Female', 'Sugarland'),
(1018, 'Gretchen', 'Female', 'Arkansas'),
(1019, 'Robert', 'Male', 'DC'),
(1020, 'Shradha', 'Female', 'Virginia');

-- --------------------------------------------------------

--
-- Table structure for table `Vehicle`
--

CREATE TABLE `Vehicle` (
  `vin` int(10) NOT NULL,
  `vmaker` varchar(20) DEFAULT NULL,
  `vmodel` varchar(15) DEFAULT NULL,
  `vyear` int(4) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `Vehicle`
--

INSERT INTO `Vehicle` (`vin`, `vmaker`, `vmodel`, `vyear`) VALUES
(2222, 'Honda', 'Accord', 1996),
(4234, 'Honda', 'Accord', 1996),
(5434, 'Ford', 'Mustang', 2003),
(5542, 'Ford', 'Mustang', 2003),
(6312, 'Nissan', 'Sunny', 2009),
(7098, 'Nissan', 'Sunny', 2005),
(7655, 'Nissan', 'Polo', 2009),
(7866, 'Nissan', 'Sunny', 2003),
(8075, 'Honda', 'Accord', 2003),
(8090, 'Volkswagen', 'Passat', 2005),
(8976, 'GMC', 'Capree', 2008),
(10000, 'Nissan', 'Polo', 2009),
(10906, 'GMC', 'Capree', 2006),
(89765, 'GMC', 'Capree', 2008),
(100422, 'Honda', 'Accord', 1996),
(77777, 'Honda', 'Accord', 2005),
(99999, 'Honda', 'Civic', 2009),
(45454, 'Nissan', 'Polo', 2003),
(67890, 'Nissan', 'Polo', 2003);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `BuyVehicle`
--
ALTER TABLE `BuyVehicle`
  ADD PRIMARY KEY (`vin`),
  ADD KEY `SSN` (`SSN`);

--
-- Indexes for table `Customer`
--
ALTER TABLE `Customer`
  ADD PRIMARY KEY (`SSN`);

--
-- Indexes for table `Vehicle`
--
ALTER TABLE `Vehicle`
  ADD KEY `vin` (`vin`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `BuyVehicle`
--
ALTER TABLE `BuyVehicle`
  ADD CONSTRAINT `buyvehicle_ibfk_1` FOREIGN KEY (`SSN`) REFERENCES `Customer` (`SSN`);

--
-- Constraints for table `Vehicle`
--
ALTER TABLE `Vehicle`
  ADD CONSTRAINT `vehicle_ibfk_1` FOREIGN KEY (`vin`) REFERENCES `BuyVehicle` (`vin`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
