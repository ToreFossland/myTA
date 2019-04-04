-- phpMyAdmin SQL Dump
-- version 4.5.4.1deb2ubuntu2.1
-- http://www.phpmyadmin.net
--
-- Host: mysql.stud.ntnu.no
-- Generation Time: Apr 04, 2019 at 10:24 PM
-- Server version: 5.7.25-0ubuntu0.16.04.2
-- PHP Version: 7.0.33-0ubuntu0.16.04.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `davidaan_bookingsystem`
--
CREATE DATABASE IF NOT EXISTS `davidaan_bookingsystem` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `davidaan_bookingsystem`;

-- --------------------------------------------------------

--
-- Table structure for table `Assignment`
--

DROP TABLE IF EXISTS `Assignment`;
CREATE TABLE `Assignment` (
  `idAssignment` int(11) NOT NULL,
  `title` varchar(45) DEFAULT NULL,
  `timestamp` datetime NOT NULL,
  `Student_email` varchar(45) NOT NULL,
  `courseCode` char(7) NOT NULL,
  `file` mediumblob,
  `fileName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Booking`
--

DROP TABLE IF EXISTS `Booking`;
CREATE TABLE `Booking` (
  `HallTime_idHallTime` int(11) NOT NULL,
  `TeachingAssistant_email` varchar(45) NOT NULL,
  `Student_email` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Course`
--

DROP TABLE IF EXISTS `Course`;
CREATE TABLE `Course` (
  `courseCode` char(7) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  `description` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Evaluation`
--

DROP TABLE IF EXISTS `Evaluation`;
CREATE TABLE `Evaluation` (
  `idEvaluation` int(11) NOT NULL,
  `score` int(3) NOT NULL,
  `note` text,
  `Assignment_idAssignment` int(11) NOT NULL,
  `TA_email` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `HallTime`
--

DROP TABLE IF EXISTS `HallTime`;
CREATE TABLE `HallTime` (
  `idHallTime` int(11) NOT NULL,
  `Course_courseCode` char(7) NOT NULL,
  `week` tinyint(4) NOT NULL,
  `day` tinyint(4) NOT NULL,
  `timeStart` time NOT NULL,
  `timeEnd` time NOT NULL,
  `availablePlaces` smallint(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `Message`
--

DROP TABLE IF EXISTS `Message`;
CREATE TABLE `Message` (
  `idMessage` int(11) NOT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `text` text NOT NULL,
  `Sender` varchar(45) NOT NULL,
  `Receiver` varchar(45) NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `User`
--

DROP TABLE IF EXISTS `User`;
CREATE TABLE `User` (
  `email` varchar(45) NOT NULL,
  `firstName` varchar(45) NOT NULL,
  `lastName` varchar(45) NOT NULL,
  `password` char(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `User_has_Course`
--

DROP TABLE IF EXISTS `User_has_Course`;
CREATE TABLE `User_has_Course` (
  `Course_courseCode` char(7) NOT NULL,
  `User_email` varchar(45) NOT NULL,
  `role` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `Assignment`
--
ALTER TABLE `Assignment`
  ADD PRIMARY KEY (`idAssignment`),
  ADD KEY `fk_Assignment_User1_idx` (`Student_email`),
  ADD KEY `fk_courseCode` (`courseCode`);

--
-- Indexes for table `Booking`
--
ALTER TABLE `Booking`
  ADD PRIMARY KEY (`HallTime_idHallTime`,`TeachingAssistant_email`),
  ADD KEY `fk_Booking_User1_idx` (`TeachingAssistant_email`),
  ADD KEY `fk_Booking_User2_idx` (`Student_email`);

--
-- Indexes for table `Course`
--
ALTER TABLE `Course`
  ADD PRIMARY KEY (`courseCode`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `Evaluation`
--
ALTER TABLE `Evaluation`
  ADD PRIMARY KEY (`idEvaluation`),
  ADD KEY `fk_Evaluation_Assignment1_idx` (`Assignment_idAssignment`),
  ADD KEY `fk_Evaluation_User1_idx` (`TA_email`);

--
-- Indexes for table `HallTime`
--
ALTER TABLE `HallTime`
  ADD PRIMARY KEY (`idHallTime`),
  ADD UNIQUE KEY `Course_courseCode` (`Course_courseCode`,`week`,`day`,`timeStart`),
  ADD KEY `fk_HallTime_Course1_idx` (`Course_courseCode`);

--
-- Indexes for table `Message`
--
ALTER TABLE `Message`
  ADD PRIMARY KEY (`idMessage`),
  ADD KEY `fk_Message_User1_idx` (`Sender`),
  ADD KEY `fk_Message_User2_idx` (`Receiver`);

--
-- Indexes for table `User`
--
ALTER TABLE `User`
  ADD PRIMARY KEY (`email`);

--
-- Indexes for table `User_has_Course`
--
ALTER TABLE `User_has_Course`
  ADD PRIMARY KEY (`Course_courseCode`,`User_email`),
  ADD KEY `fk_User_has_Course_Course1_idx` (`Course_courseCode`),
  ADD KEY `fk_User_has_Course_User1_idx` (`User_email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `Assignment`
--
ALTER TABLE `Assignment`
  MODIFY `idAssignment` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Evaluation`
--
ALTER TABLE `Evaluation`
  MODIFY `idEvaluation` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `HallTime`
--
ALTER TABLE `HallTime`
  MODIFY `idHallTime` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT for table `Message`
--
ALTER TABLE `Message`
  MODIFY `idMessage` int(11) NOT NULL AUTO_INCREMENT;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `Assignment`
--
ALTER TABLE `Assignment`
  ADD CONSTRAINT `fk_Assignment_User1` FOREIGN KEY (`Student_email`) REFERENCES `User` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_courseCode` FOREIGN KEY (`courseCode`) REFERENCES `Course` (`courseCode`);

--
-- Constraints for table `Booking`
--
ALTER TABLE `Booking`
  ADD CONSTRAINT `fk_Booking_HallTime1` FOREIGN KEY (`HallTime_idHallTime`) REFERENCES `HallTime` (`idHallTime`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_Booking_User1` FOREIGN KEY (`TeachingAssistant_email`) REFERENCES `User` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_Booking_User2` FOREIGN KEY (`Student_email`) REFERENCES `User` (`email`) ON DELETE SET NULL ON UPDATE CASCADE;

--
-- Constraints for table `Evaluation`
--
ALTER TABLE `Evaluation`
  ADD CONSTRAINT `fk_Evaluation_Assignment1` FOREIGN KEY (`Assignment_idAssignment`) REFERENCES `Assignment` (`idAssignment`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_Evaluation_User1` FOREIGN KEY (`TA_email`) REFERENCES `User` (`email`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `HallTime`
--
ALTER TABLE `HallTime`
  ADD CONSTRAINT `fk_HallTime_Course1` FOREIGN KEY (`Course_courseCode`) REFERENCES `Course` (`courseCode`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `Message`
--
ALTER TABLE `Message`
  ADD CONSTRAINT `fk_Message_User1` FOREIGN KEY (`Sender`) REFERENCES `User` (`email`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_Message_User2` FOREIGN KEY (`Receiver`) REFERENCES `User` (`email`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `User_has_Course`
--
ALTER TABLE `User_has_Course`
  ADD CONSTRAINT `fk_User_has_Course_Course1` FOREIGN KEY (`Course_courseCode`) REFERENCES `Course` (`courseCode`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `fk_User_has_Course_User1` FOREIGN KEY (`User_email`) REFERENCES `User` (`email`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
