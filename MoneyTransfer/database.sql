-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: moneytransfer
-- ------------------------------------------------------
-- Server version	8.0.28

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `connection`
--

DROP TABLE IF EXISTS `connection`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `connection` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user` int DEFAULT NULL,
  `friend` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id_idx` (`id`),
  KEY `user_idx` (`user`),
  KEY `friend_idx` (`friend`),
  CONSTRAINT `friend` FOREIGN KEY (`friend`) REFERENCES `user` (`id`),
  CONSTRAINT `user` FOREIGN KEY (`user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=89 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `connection`
--

LOCK TABLES `connection` WRITE;
/*!40000 ALTER TABLE `connection` DISABLE KEYS */;
INSERT INTO `connection` VALUES (74,23,1),(75,23,22),(76,23,2),(77,23,3),(78,23,4),(79,23,5),(80,23,6),(81,23,7),(84,37,1),(85,38,1),(86,39,1),(87,1,23),(88,1,2);
/*!40000 ALTER TABLE `connection` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `persistent_logins`
--

DROP TABLE IF EXISTS `persistent_logins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp(6) NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `persistent_logins`
--

LOCK TABLES `persistent_logins` WRITE;
/*!40000 ALTER TABLE `persistent_logins` DISABLE KEYS */;
/*!40000 ALTER TABLE `persistent_logins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `role` (
  `id` int NOT NULL,
  `role_name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (1,'ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` int NOT NULL AUTO_INCREMENT,
  `transaction_date` datetime DEFAULT NULL,
  `amount` double NOT NULL,
  `description` varchar(20) DEFAULT NULL,
  `sender` int DEFAULT NULL,
  `receiver` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `sender_idx` (`sender`),
  KEY `receiver_idx` (`receiver`),
  CONSTRAINT `receiver` FOREIGN KEY (`receiver`) REFERENCES `user` (`id`),
  CONSTRAINT `sender` FOREIGN KEY (`sender`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (12,'2010-12-12 00:00:00',10,'d1',23,2),(13,'2010-12-12 00:00:00',20,'d2',23,3),(14,'2010-12-12 00:00:00',30,'d3',23,4),(15,'2010-12-12 00:00:00',40,'d4',23,5),(16,'2010-12-12 00:00:00',10,'d5',23,6),(17,'2010-12-12 00:00:00',20,'d6',23,5),(26,'2022-07-28 11:32:26',10,'d40',23,2),(27,'2022-08-04 07:07:07',10,'des',23,1),(28,'2022-08-11 08:54:22',10,'gh',23,1),(30,'2022-11-15 12:41:18',5,'cvcb',1,23),(31,'2022-11-15 12:41:26',8,'bjhb',1,23),(32,'2022-11-15 12:41:34',9,'lkl',1,23),(33,'2022-11-15 12:41:44',6,'jhj',1,23),(34,'2022-11-15 14:16:50',8,'dd',38,1),(35,'2022-11-15 14:20:18',5,'fg',37,1),(36,'2022-11-15 14:27:35',2,'cd',37,1),(39,'2022-11-30 11:43:42',8,'ghgj',1,23),(40,'2022-11-30 16:33:37',22,'jhj',1,23);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `firstname` varchar(20) DEFAULT NULL,
  `lastname` varchar(20) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `balance` double DEFAULT NULL,
  `swift` varchar(45) DEFAULT NULL,
  `account_number` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'fatima@gmail.com','$2a$10$ByhI.MnM8yzpgCGnS6YrIunKaJhE4./JnG6jAQa7.mEi2wZWxs4AS','Fatima','Bar',NULL,'Address','phone',7,'',''),(2,'john@gmail.com','$10$V6kPXXZpxFPuH5llPbCVae8vM8A1xqBHGyrV2m35OHijOs2I/X/0.','John','Donales','2010-06-11','adress 2','pp',144,NULL,NULL),(3,'aa@gmail.com','$10$V6kPXXZpxFPuH5llPbCVae8vM8A1xqBHGyrV2m35OHijOs2I/X/0.','Arnauld','last','2010-05-11','Address 3','yy',42,NULL,NULL),(4,'jonathan@gmail.com','$10$V6kPXXZpxFPuH5llPbCVae8vM8A1xqBHGyrV2m35OHijOs2I/X/0.','Jonathan','Morisky','2010-05-11','Address 4','dfd',89,NULL,NULL),(5,'najat@gmail.com','$10$V6kPXXZpxFPuH5llPbCVae8vM8A1xqBHGyrV2m35OHijOs2I/X/0.','Najat','Lili','2010-05-12','Address 5','gjg',44,NULL,NULL),(6,'noura@gmail.com','$10$V6kPXXZpxFPuH5llPbCVae8vM8A1xqBHGyrV2m35OHijOs2I/X/0.','Noura','Mimi','2010-05-12','Address 6','fh',45,NULL,NULL),(7,'asma@gmail.com','$10$V6kPXXZpxFPuH5llPbCVae8vM8A1xqBHGyrV2m35OHijOs2I/X/0.','asma','Lifti','2022-07-20','Adress 7','dsf',10,NULL,NULL),(8,'anas@gmail.com','$10$V6kPXXZpxFPuH5llPbCVae8vM8A1xqBHGyrV2m35OHijOs2I/X/0.','anas','Bouda','2022-07-20','Adress 8','fdsdf',20,NULL,NULL),(9,'salma@gmail.com','$10$V6kPXXZpxFPuH5llPbCVae8vM8A1xqBHGyrV2m35OHijOs2I/X/0.','salma','Nounou','2022-07-20','Adress 9','fsf',30,NULL,NULL),(10,'zak@gmail.com','$2a$10$V6kPXXZpxFPuH5llPbCVae8vM8A1xqBHGyrV2m35OHijOs2I/X/0.','zak',NULL,NULL,NULL,NULL,0,NULL,NULL),(15,'new1@gmail.com','$2a$10$ENSAHfeEEDq9ftD5OKIaA.70t2NAk6kvxoIPFIPXB9NxIBx6Teu5u','new1',NULL,NULL,NULL,NULL,0,NULL,NULL),(17,'bb@gmail.com','$2a$10$eMe/yZUH0RtP6UiHq4gyZeVo7Wiqh7w82B85Zhei2xzrTO5etqpJi','b',NULL,NULL,NULL,NULL,0,NULL,NULL),(22,'f@gmail.com','$2a$10$9KN4O.KfQuVZobWAHpqtoeH3smsAB3Uehuj56BpIB9cSAGlXN47Pm','f','ffff',NULL,NULL,NULL,0,NULL,NULL),(23,'aa@aa.com','$2a$10$R8IPt0ppmIH2eApe/1X97.r2ShxXvXaocpKNNnbB0jpuTNsmaSroK','aamodm2','bbmod2',NULL,'adress33mod2','05226699882',70,'swift2','1234562'),(24,'zz@zz.com','$2a$10$JhmWrclroophsmEocqEhwupFDzYgcpKD/i6r4J5W.qvUprCSh14.G','zizou',NULL,NULL,NULL,NULL,0,NULL,NULL),(37,'sophie@gmail.com','$2a$10$efyInKktIL0pf96bTr0u3ew/d5G9Q8pxHySMkHEASo/AHi5.Nlp2i','sophie','',NULL,'','',13,'',''),(38,'alice@gmail.com','$2a$10$kMJIEBZ52YrOYM1p9GlMwu9XOOADhJ20jmavdPCFogK.dqMfClNPC','Alice','Nada',NULL,'','',92,'',''),(39,'email@gmail.com','$2a$10$zv2vsOZDJAntOWXFhLTh.OX1m4gFjliQzbcZXEwjy10EYEaXcVKDi','first','last',NULL,NULL,NULL,0,NULL,NULL),(42,'moncomptepro.fb@gmail.com',NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id_role` int NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKa68196081fvovjhkek5m97n3y` (`role_id`),
  KEY `FK64i80m793j4ur4mwxsq3dqypi` (`user_id_role`),
  CONSTRAINT `FK64i80m793j4ur4mwxsq3dqypi` FOREIGN KEY (`user_id_role`) REFERENCES `user` (`id`),
  CONSTRAINT `FKa68196081fvovjhkek5m97n3y` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,22,1),(2,23,1),(4,2,1),(5,3,1),(6,4,1),(21,37,1),(23,38,1),(24,39,1),(25,42,1),(28,1,1);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'moneytransfer'
--

--
-- Dumping routines for database 'moneytransfer'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-01 14:42:18
