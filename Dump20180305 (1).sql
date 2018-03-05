-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: new_version
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `av_materials`
--

LOCK TABLES `av_materials` WRITE;
/*!40000 ALTER TABLE `av_materials` DISABLE KEYS */;
INSERT INTO `av_materials` VALUES (1,'av','author',100,'key',0,'\0');
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
  `time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `is_renew` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `document_id_idx` (`document_id`),
  KEY `user_id_idx` (`user_id`),
  CONSTRAINT `document_id` FOREIGN KEY (`document_id`) REFERENCES `documents` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=229 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `booking`
--

LOCK TABLES `booking` WRITE;
/*!40000 ALTER TABLE `booking` DISABLE KEYS */;
INSERT INTO `booking` VALUES (224,57,6,'2018-03-04 19:26:51',0),(225,58,6,'2018-03-04 19:27:33',0),(226,59,6,'2018-03-04 19:28:17',0),(227,60,6,'2018-03-04 19:28:53',0),(228,61,6,'2018-03-04 19:29:38',0);
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
  `title` varchar(45) NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books`
--

LOCK TABLES `books` WRITE;
/*!40000 ALTER TABLE `books` DISABLE KEYS */;
INSERT INTO `books` VALUES (1,'test1','author1','publisher1','3',2009,100,'key',2,'\0',''),(3,'Shigirler','[Djalil]','pub','first',2016,10,'[Poem, Tatar]',2,'','\0'),(7,'Tuchay Shigirler','[Tuckay]','pub','first',2016,10,'[Poem, Tatar]',2,'\0','\0'),(8,'Alish Shigirler','[Alish]','pub','first',2016,10,'[Poem, Tatar]',0,'\0','\0'),(18,'New book','[Alish]','pub','first',2016,10,'[Poem, Tatar]',1,'\0','\0'),(19,'Old book','[Alish]','pub','first',2016,10,'[Poem, Tatar]',1,'\0','\0'),(20,'Old book','[Alish]','pub','first',2016,10,'[Poem, Tatar]',1,'\0','\0'),(26,'NewName','[author]','publisher','editor',2008,100,'[author]',1,'\0','\0'),(31,'Name','[author]','publisher','editor',2008,100,'[author]',2,'\0','\0'),(32,'Name','[author]','publisher','editor',2008,100,'[author]',1,'\0','\0'),(34,'Extra','[author]','publisher','edition',2001,1000,'[key]',1,'\0',''),(35,'Noname','[author]','publisher','editor',2008,100,'[author]',1,'\0','\0'),(36,'Noname','[author]','publisher','editor',2008,100,'[author]',1,'\0','\0'),(37,'Noname','[author]','publisher','editor',2008,100,'[author]',1,'\0','\0'),(38,'Noname','[author]','publisher','editor',2008,100,'[author]',1,'\0','\0'),(39,'Noname','[author]','publisher','editor',2008,100,'[author]',1,'\0','\0'),(40,'Noname','[author]','publisher','editor',2008,100,'[author]',1,'\0','\0');
/*!40000 ALTER TABLE `books` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `documents`
--

LOCK TABLES `documents` WRITE;
/*!40000 ALTER TABLE `documents` DISABLE KEYS */;
INSERT INTO `documents` VALUES (19,NULL,1,NULL,'book','twsfw','\0'),(20,NULL,3,NULL,'book',NULL,'\0'),(24,NULL,7,NULL,'books',NULL,'\0'),(25,NULL,8,NULL,'books',NULL,'\0'),(36,NULL,18,NULL,'books','shelf #1','\0'),(37,NULL,19,NULL,'books','shelf #1','\0'),(38,NULL,20,NULL,'books','shelf #1','\0'),(45,NULL,26,NULL,'books','location','\0'),(50,NULL,31,NULL,'books','location',''),(51,NULL,32,NULL,'books','location',''),(55,NULL,34,NULL,'books','local',''),(56,NULL,35,NULL,'books','location',''),(57,NULL,36,NULL,'books','location',''),(58,NULL,37,NULL,'books','location',''),(59,NULL,38,NULL,'books','location',''),(60,NULL,39,NULL,'books','location',''),(61,NULL,40,NULL,'books','location','');
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
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
  PRIMARY KEY (`id`),
  KEY `document_id_idx` (`id_document`),
  KEY `user_id_idx` (`id_user`),
  KEY `librarian_idx` (`id_librarian`),
  CONSTRAINT `document` FOREIGN KEY (`id_document`) REFERENCES `documents` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `librarian` FOREIGN KEY (`id_librarian`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `user` FOREIGN KEY (`id_user`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `request`
--

LOCK TABLES `request` WRITE;
/*!40000 ALTER TABLE `request` DISABLE KEYS */;
INSERT INTO `request` VALUES (1,6,60),(2,6,61);
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (3,'Ilsur','89991697701','def',NULL,NULL,'root',NULL),(4,'Vagiz','89991697702','defdd',NULL,NULL,'root',NULL),(5,'Ilgiz','9991697006','address',0,'\0','root',''),(6,'Ilgiz','9991697006','address',10,'',NULL,NULL),(7,'Vildan','777','Chalny',10,'','root',NULL);
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

-- Dump completed on 2018-03-05 11:17:58
