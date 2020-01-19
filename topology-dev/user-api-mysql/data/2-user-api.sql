-- phpMyAdmin SQL Dump
-- version 5.0.0
-- https://www.phpmyadmin.net/
--
-- Host: user-api-db
-- Generation Time: Jan 19, 2020 at 11:14 AM
-- Server version: 5.7.28
-- PHP Version: 7.4.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `user_api`
--
CREATE DATABASE IF NOT EXISTS `user_api` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `user_api`;

-- --------------------------------------------------------

--
-- Table structure for table `password_reset_entity`
--

CREATE TABLE IF NOT EXISTS `password_reset_entity` (
  `uuid` varchar(255) NOT NULL,
  `user_email` varchar(255) NOT NULL,
  PRIMARY KEY (`user_email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `password_reset_entity`
--

TRUNCATE TABLE `password_reset_entity`;
-- --------------------------------------------------------

--
-- Table structure for table `user_entity`
--

CREATE TABLE IF NOT EXISTS `user_entity` (
  `email` varchar(255) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `user_entity`
--

TRUNCATE TABLE `user_entity`;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `password_reset_entity`
--
ALTER TABLE `password_reset_entity`
  ADD CONSTRAINT `FKj9wq0qj4q90g2naaf77h6onks` FOREIGN KEY (`user_email`) REFERENCES `user_entity` (`email`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

