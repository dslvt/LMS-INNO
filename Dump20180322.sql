-- MySQL dump 10.13  Distrib 5.7.21, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: mydbtest
-- ------------------------------------------------------
-- Server version	5.7.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `av_materials`
--

DROP TABLE IF EXISTS `av_materials`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `av_materials` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `author` varchar(100) NOT NULL,
  `cost` int(11) NOT NULL,
  `keywords` varchar(100) NOT NULL,
  `number` int(11) NOT NULL,
  `reference` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=512 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `av_materials`
--

LOCK TABLES `av_materials` WRITE;
/*!40000 ALTER TABLE `av_materials` DISABLE KEYS */;
INSERT INTO `av_materials` VALUES (510,'Null References: The Billion Dollar Mistake','[Tony Hoare]',0,'[]',0,'\0'),(511,'Information Entropy','[Claude Shannon]',0,'[]',1,'\0');
/*!40000 ALTER TABLE `av_materials` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `booking`
--

DROP TABLE IF EXISTS `booking`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `booking` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `document_id` int(11) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `time` datetime DEFAULT NULL,
  `is_renew` tinyint(4) NOT NULL DEFAULT '0',
  `returnTime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `document_id_idx` (`document_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `document_id` FOREIGN KEY (`document_id`) REFERENCES `documents` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=678 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (673,2025,741,'2018-02-05 00:00:00',0,'2018-02-26 00:00:00'),(674,2030,741,'2018-02-17 00:00:00',0,'2018-03-03 00:00:00');
/*!40000 ALTER TABLE `booking` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books`
--

DROP TABLE IF EXISTS `books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `books` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  `author` varchar(100) NOT NULL,
  `publisher` varchar(45) NOT NULL,
  `edition` varchar(45) NOT NULL,
  `publish_year` int(11) NOT NULL,
  `cost` int(11) NOT NULL,
  `keywords` varchar(100) NOT NULL,
  `number` int(11) NOT NULL,
  `reference` bit(1) NOT NULL,
  `isBestSeller` bit(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=780 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (777,'Introduction to Algorithms','[Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein]','MIT Press','Third edition',2009,0,'[]',2,'\0','\0'),(778,'Design Patterns: Elements of Reusable Object-Oriented Software','[Erich Gamma, Ralph Johnson, John Vlissides, Richard Helm]','Addison-Wesley Professional','First edition',2003,0,'[]',2,'\0',''),(779,'The Mythical Man-month','[Brooks,Jr., Frederick P]','Addison-Wesley Longman Publishing Co., Inc.','Second edition',1995,0,'[]',1,'','\0');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `document_queue`
--

DROP TABLE IF EXISTS `document_queue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `document_queue` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL DEFAULT '0',
  `id_document` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `DocID_idx` (`id_document`),
  KEY `UserID_idx` (`id_user`),
  CONSTRAINT `DocID` FOREIGN KEY (`id_document`) REFERENCES `documents` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `UserID` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `document_queue`
--

LOCK TABLES `document_queue` WRITE;
/*!40000 ALTER TABLE `document_queue` DISABLE KEYS */;
/*!40000 ALTER TABLE `document_queue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `documents`
--

DROP TABLE IF EXISTS `documents`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `documents` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_av_materials` int(11) DEFAULT NULL,
  `id_books` int(11) DEFAULT NULL,
  `id_journals` int(11) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  `location` varchar(45) DEFAULT NULL,
  `isActive` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_av_materials_idx` (`id_av_materials`),
  KEY `id_books_idx` (`id_books`),
  KEY `id_journals_idx` (`id_journals`),
  CONSTRAINT `id_av_materials` FOREIGN KEY (`id_av_materials`) REFERENCES `av_materials` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_books` FOREIGN KEY (`id_books`) REFERENCES `books` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `id_journals` FOREIGN KEY (`id_journals`) REFERENCES `journals` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2032 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES (2024,NULL,777,NULL,'books','lib',''),(2025,NULL,777,NULL,'books','lib',''),(2026,NULL,777,NULL,'books','lib',''),(2027,NULL,778,NULL,'books','lib',''),(2028,NULL,778,NULL,'books','lib',''),(2029,NULL,779,NULL,'books','lib',''),(2030,510,NULL,NULL,'av_materials','loc',''),(2031,511,NULL,NULL,'av_materials','loc','');
/*!40000 ALTER TABLE `documents` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `journal_articles`
--

DROP TABLE IF EXISTS `journal_articles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journal_articles` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `author` varchar(45) NOT NULL,
  `title` varchar(45) NOT NULL,
  `journal_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `journal_id_idx` (`journal_id`),
  CONSTRAINT `journal_id` FOREIGN KEY (`journal_id`) REFERENCES `journals` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `journal_articles`
--

LOCK TABLES `journal_articles` WRITE;
/*!40000 ALTER TABLE `journal_articles` DISABLE KEYS */;
/*!40000 ALTER TABLE `journal_articles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `journals`
--

DROP TABLE IF EXISTS `journals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `journals` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(45) NOT NULL,
  `author` varchar(100) NOT NULL,
  `issue` varchar(45) NOT NULL,
  `editor` varchar(45) NOT NULL,
  `cost` int(11) NOT NULL,
  `keywords` varchar(100) NOT NULL,
  `number` int(11) NOT NULL,
  `reference` bit(1) NOT NULL,
  `publicationDate` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `journals`
--

LOCK TABLES `journals` WRITE;
/*!40000 ALTER TABLE `journals` DISABLE KEYS */;
/*!40000 ALTER TABLE `journals` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `libtasks`
--

DROP TABLE IF EXISTS `libtasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `libtasks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) DEFAULT '0',
  `id_document` int(11) DEFAULT '0',
  `type` varchar(45) DEFAULT NULL,
  `id_librarian` int(11) DEFAULT NULL,
  `queue` int(11) DEFAULT NULL,
  `unic_key` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `document_id_idx` (`id_document`),
  KEY `user_id_idx` (`id_user`),
  KEY `librarian_idx` (`id_librarian`),
  CONSTRAINT `document` FOREIGN KEY (`id_document`) REFERENCES `documents` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `librarian` FOREIGN KEY (`id_librarian`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `libtasks`
--

LOCK TABLES `libtasks` WRITE;
/*!40000 ALTER TABLE `libtasks` DISABLE KEYS */;
/*!40000 ALTER TABLE `libtasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `request`
--

DROP TABLE IF EXISTS `request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `request` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(11) NOT NULL,
  `id_document` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user_idx` (`id_user`),
  KEY `id_document_idx` (`id_document`),
  CONSTRAINT `id_document` FOREIGN KEY (`id_document`) REFERENCES `documents` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_user` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request`
--

LOCK TABLES `request` WRITE;
/*!40000 ALTER TABLE `request` DISABLE KEYS */;
INSERT INTO `request` VALUES (3,741,2025);
/*!40000 ALTER TABLE `request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `phoneNumber` varchar(45) NOT NULL,
  `address` varchar(45) NOT NULL,
  `debt` int(11) DEFAULT NULL,
  `isFacultyMember` bit(1) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `isLibrarian` bit(1) DEFAULT NULL,
  `type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=743 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'All cash','1','1',0,'\0','1','','lib'),(740,'Sergey Afonso','30001','Via Margutta, 3',0,'','1','\0','student'),(741,'Elvira Espindola','30002','Via Sacra, 13',0,'\0','1','\0','student'),(742,'Nadia Teixeira','30003','Via del Corso, 22',0,'\0','1','\0','student');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-03-22 16:30:22
