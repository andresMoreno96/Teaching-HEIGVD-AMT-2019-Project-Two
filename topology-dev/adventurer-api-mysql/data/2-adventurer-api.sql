-- phpMyAdmin SQL Dump
-- version 5.0.0
-- https://www.phpmyadmin.net/
--
-- Host: adventurer-api-db
-- Generation Time: Jan 19, 2020 at 07:43 PM
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
-- Database: `adventurer_api`
--
CREATE DATABASE IF NOT EXISTS `adventurer_api` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `adventurer_api`;

-- --------------------------------------------------------

--
-- Table structure for table `adventurer_entity`
--

CREATE TABLE IF NOT EXISTS `adventurer_entity` (
  `name` varchar(255) NOT NULL,
  `job` varchar(255) DEFAULT NULL,
  `user_email` varchar(255) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `adventurer_entity`
--

TRUNCATE TABLE `adventurer_entity`;
-- --------------------------------------------------------

--
-- Table structure for table `adventurer_entity_participation`
--

CREATE TABLE IF NOT EXISTS `adventurer_entity_participation` (
  `participants_name` varchar(255) NOT NULL,
  `participation_id` bigint(20) NOT NULL,
  PRIMARY KEY (`participants_name`,`participation_id`),
  KEY `FKa5nguyq6xfse0e6yytq2hsfef` (`participation_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `adventurer_entity_participation`
--

TRUNCATE TABLE `adventurer_entity_participation`;
-- --------------------------------------------------------

--
-- Table structure for table `quest_entity`
--

CREATE TABLE IF NOT EXISTS `quest_entity` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `ended` bit(1) NOT NULL DEFAULT b'0',
  `title` varchar(255) NOT NULL,
  `owner_name` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_pbnri24eq1htvox2x2y6bewya` (`owner_name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Truncate table before insert `quest_entity`
--

TRUNCATE TABLE `quest_entity`;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `adventurer_entity_participation`
--
ALTER TABLE `adventurer_entity_participation`
  ADD CONSTRAINT `FKa5nguyq6xfse0e6yytq2hsfef` FOREIGN KEY (`participation_id`) REFERENCES `quest_entity` (`id`),
  ADD CONSTRAINT `FKdbx4sutob910uw3f0mnbkmwu4` FOREIGN KEY (`participants_name`) REFERENCES `adventurer_entity` (`name`);

--
-- Constraints for table `quest_entity`
--
ALTER TABLE `quest_entity`
  ADD CONSTRAINT `FK3a9hn7172k2qd14c10pcbf0om` FOREIGN KEY (`owner_name`) REFERENCES `adventurer_entity` (`name`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

