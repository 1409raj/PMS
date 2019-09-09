-- MySQL dump 10.13  Distrib 5.6.24, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: test
-- ------------------------------------------------------
-- Server version	5.6.26-log

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
-- Table structure for table `appraisal_year`
--

DROP TABLE IF EXISTS `appraisal_year`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appraisal_year` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `MODIFIED_BY` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appraisal_year`
--

LOCK TABLES `appraisal_year` WRITE;
/*!40000 ALTER TABLE `appraisal_year` DISABLE KEYS */;
INSERT INTO `appraisal_year` VALUES (1,'2017 - 2018','2016-05-14 16:57:23',NULL,'admin',NULL,0),(2,'2018 - 2019','2018-05-14 16:57:23','2018-05-16 12:30:31','admin','admin',1),(5,'2019 - 2020','2018-06-04 14:17:57','2018-06-04 14:18:36','admin','admin',2),(6,'2020-2021','2018-07-04 14:20:45',NULL,'admin',NULL,2);
/*!40000 ALTER TABLE `appraisal_year` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appraisal_year_details`
--

DROP TABLE IF EXISTS `appraisal_year_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `appraisal_year_details` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMP_CODE` varchar(200) CHARACTER SET utf8mb4 NOT NULL,
  `EMAIL` varchar(500) CHARACTER SET utf8mb4 DEFAULT NULL,
  `APPRAISAL_YEAR_ID` int(11) NOT NULL,
  `INITIALIZATION_YEAR` int(11) DEFAULT NULL,
  `MID_YEAR` int(11) DEFAULT NULL,
  `FINAL_YEAR` int(11) DEFAULT NULL,
  `EMPLOYEE_ISVISIBILE` int(11) DEFAULT NULL,
  `FIRSTLEVEL_ISVISIBILE` int(11) DEFAULT NULL,
  `SECONDLEVEL_ISVISIBILE` int(11) DEFAULT NULL,
  `SECONDLEVEL_ISVISIBILE_CHECK` int(11) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL,
  `MODIFIED_BY` varchar(200) CHARACTER SET utf8mb4 DEFAULT NULL,
  `INITIALIZATION_START_DATE` datetime DEFAULT NULL,
  `INITIALIZATION_END_DATE` datetime DEFAULT NULL,
  `MID_YEAR_START_DATE` datetime DEFAULT NULL,
  `MID_YEAR_END_DATE` datetime DEFAULT NULL,
  `FINAL_YEAR_START_DATE` datetime DEFAULT NULL,
  `FINAL_YEAR_END_DATE` datetime DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  `ACKNOWLEDGEMENT_CHECK` tinyint(4) DEFAULT NULL,
  `KRA_FORWARD_CHECK` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appraisal_year_details`
--

LOCK TABLES `appraisal_year_details` WRITE;
/*!40000 ALTER TABLE `appraisal_year_details` DISABLE KEYS */;
INSERT INTO `appraisal_year_details` VALUES (9,'1211002','sheetu.garg@herofutureenergies.com',2,0,1,0,1,0,0,0,'2018-09-05 12:02:59','2018-09-08 09:28:23','admin','admin','2018-10-05 00:00:00','2018-09-30 00:00:00','2018-09-08 00:00:00','2018-09-30 00:00:00','2018-06-24 00:00:00','2018-09-30 00:00:00',1,0,0),(10,'1301009','bhawnak.mital@herofutureenergies.com',2,0,1,0,1,0,0,0,'2018-09-05 12:02:59','2018-09-08 09:28:23','admin','admin','2018-10-05 00:00:00','2018-09-30 00:00:00','2018-09-08 00:00:00','2018-09-30 00:00:00','2018-06-24 00:00:00','2018-09-30 00:00:00',1,0,0),(11,'1606145','shailesh.pandey@herofutureenergies.com',2,0,1,0,1,0,0,0,'2018-09-05 12:02:59','2018-09-08 09:28:23','admin','admin','2018-10-05 00:00:00','2018-09-30 00:00:00','2018-09-08 00:00:00','2018-09-30 00:00:00','2018-06-24 00:00:00','2018-09-30 00:00:00',1,1,0),(12,'1808341','pravendra.singh@herofutureenergies.com',2,0,1,0,1,0,0,0,'2018-09-05 12:02:59','2018-09-08 09:28:23','admin','admin','2018-10-05 00:00:00','2018-09-30 00:00:00','2018-09-08 00:00:00','2018-09-30 00:00:00','2018-06-24 00:00:00','2018-09-30 00:00:00',1,0,NULL),(13,'1712284','sheetu.garg@herofutureenergies.com',2,0,0,1,1,0,0,0,'2018-09-05 12:02:59','2018-09-08 09:28:23','admin','admin','2018-10-05 00:00:00','2018-09-30 00:00:00','2018-09-08 00:00:00','2018-09-30 00:00:00','2018-06-24 00:00:00','2018-09-30 00:00:00',1,1,1);
/*!40000 ALTER TABLE `appraisal_year_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `behavioural_competence`
--

DROP TABLE IF EXISTS `behavioural_competence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `behavioural_competence` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) CHARACTER SET utf8 NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `APPRAISAL_YEAR_ID` int(11) NOT NULL,
  `WEIGHTAGE` varchar(50) NOT NULL,
  `TYPE` varchar(100) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(45) DEFAULT NULL,
  `MODIFIED_BY` varchar(45) DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `behavioural_competence`
--

LOCK TABLES `behavioural_competence` WRITE;
/*!40000 ALTER TABLE `behavioural_competence` DISABLE KEYS */;
INSERT INTO `behavioural_competence` VALUES (1,'INTEGRITY','Basis honesty of charracter, adhere to the moral system of SELF and the\nCompany.',2,'15','EMPLOYEE',NULL,'2018-05-14 16:57:23',NULL,'admin',1),(2,'ORGANIZATIONAL AND PLANNING','Set priorities to optimize time usage .Engages in short and long term planning proposes milestones which allow progress to be adequately measured.Adheres to schedules and plans.',2,'20','EMPLOYEE',NULL,'2018-05-14 15:37:51',NULL,'admin',1),(3,'ANALYTICAL AND PROBLEM SOLVING','Understands and defines problem clearly. Formulates realistic implementable solutions. Participates constructively in group problem solving Anticipates and prevnts occurance of problems',2,'10','EMPLOYEE',NULL,'2018-05-08 10:57:49',NULL,NULL,1),(4,'JUDGEMENT AND DECISION MAKING','Consider relevent alternatives and the background before making decisions. Is able to take good decision within the time frame',2,'10','EMPLOYEE',NULL,'2018-05-08 10:50:18',NULL,NULL,1),(5,'SELF IMPROVEMENT AND INITIATIVE','Responds positively after receiving feedback from his/her reporting manager,peers and team mates. Takes initiatives to propoe improvement in self work,team work and others.',2,'10','EMPLOYEE',NULL,'2018-05-08 10:57:40',NULL,NULL,1),(6,'INNOVATION AND CREATIVITY','Genrates good and feasible (implementable) ideas, concepts and techniques.Willing to attempt new approches. Simplifies and/or improves,techniques and process',2,'10','EMPLOYEE',NULL,'2018-05-08 10:52:57',NULL,NULL,1),(7,'PASSION','Is passionate about the work and the organization.Degree to which employee is emotionally connected',2,'10','EMPLOYEE',NULL,'2018-05-08 10:59:03',NULL,NULL,1),(8,'COMMUNICATION','Articulates ideas in a clear,concise and assertive manner so that ithers understand the talk.Is able to clearly communicate and understand the written and oral communication',2,'5','EMPLOYEE',NULL,NULL,NULL,NULL,1),(9,'TEAMWORK','Assisstes others when needed .Participates effectively in the team work by offering ideas and implementing agreed plans.Is a patient Listener.Prevents or resolves conflict.Effectively manages team when needed',2,'10','EMPLOYEE',NULL,'2018-05-11 14:36:47',NULL,NULL,1),(11,'INTEGRITY','Basis honesty of charracter, adhere to the moral system of SELF and the\nCompany.',2,'15','HOD',NULL,'2018-05-14 16:57:23',NULL,'admin',1),(12,'ORGANIZATIONAL AND PLANNING','Set priorities to optimize time usage .Engages in short and long term planning proposes milestones which allow progress to be adequately measured.Adheres to schedules and plans.',2,'20','HOD',NULL,'2018-05-14 15:37:51',NULL,'admin',1),(13,'ANALYTICAL AND PROBLEM SOLVING','Understands and defines problem clearly. Formulates realistic implementable solutions. Participates constructively in group problem solving Anticipates and prevnts occurance of problems',2,'10','HOD',NULL,'2018-05-08 10:57:49',NULL,NULL,1),(14,'JUDGEMENT AND DECISION MAKING','Consider relevent alternatives and the background before making decisions. Is able to take good decision within the time frame',2,'10','HOD',NULL,'2018-05-08 10:50:18',NULL,NULL,1),(15,'SELF IMPROVEMENT AND INITIATIVE','Responds positively after receiving feedback from his/her reporting manager,peers and team mates. Takes initiatives to propoe improvement in self work,team work and others.',2,'10','HOD',NULL,'2018-05-08 10:57:40',NULL,NULL,1),(16,'INNOVATION AND CREATIVITY','Genrates good and feasible (implementable) ideas, concepts and techniques.Willing to attempt new approches. Simplifies and/or improves,techniques and process',2,'10','HOD',NULL,'2018-05-08 10:52:57',NULL,NULL,1),(17,'PASSION','Is passionate about the work and the organization.Degree to which employee is emotionally connected',2,'10','HOD',NULL,'2018-05-08 10:59:03',NULL,NULL,1),(18,'COMMUNICATION','Articulates ideas in a clear,concise and assertive manner so that ithers understand the talk.Is able to clearly communicate and understand the written and oral communication',2,'5','HOD',NULL,NULL,NULL,NULL,1),(19,'TEAMWORK','Assisstes others when needed .Participates effectively in the team work by offering ideas and implementing agreed plans.Is a patient Listener.Prevents or resolves conflict.Effectively manages team when needed',2,'5','HOD',NULL,'2018-06-20 10:23:22',NULL,'admin',1),(38,'Testing','Test',2,'5','HOD','2018-06-20 10:23:43',NULL,'admin',NULL,1),(39,'INTEGRITY','Basis honesty of charracter, adhere to the moral system of SELF and the\nCompany.',5,'15','EMPLOYEE','2018-08-31 10:53:41',NULL,'admin',NULL,1),(40,'ORGANIZATIONAL AND PLANNING','Set priorities to optimize time usage .Engages in short and long term planning proposes milestones which allow progress to be adequately measured.Adheres to schedules and plans.',5,'20','EMPLOYEE','2018-08-31 10:53:41',NULL,'admin',NULL,1),(41,'ANALYTICAL AND PROBLEM SOLVING','Understands and defines problem clearly. Formulates realistic implementable solutions. Participates constructively in group problem solving Anticipates and prevnts occurance of problems',5,'10','EMPLOYEE','2018-08-31 10:53:41',NULL,'admin',NULL,1),(42,'JUDGEMENT AND DECISION MAKING','Consider relevent alternatives and the background before making decisions. Is able to take good decision within the time frame',5,'10','EMPLOYEE','2018-08-31 10:53:41',NULL,'admin',NULL,1),(43,'SELF IMPROVEMENT AND INITIATIVE','Responds positively after receiving feedback from his/her reporting manager,peers and team mates. Takes initiatives to propoe improvement in self work,team work and others.',5,'10','EMPLOYEE','2018-08-31 10:53:41',NULL,'admin',NULL,1),(44,'INNOVATION AND CREATIVITY','Genrates good and feasible (implementable) ideas, concepts and techniques.Willing to attempt new approches. Simplifies and/or improves,techniques and process',5,'10','EMPLOYEE','2018-08-31 10:53:41',NULL,'admin',NULL,1),(45,'PASSION','Is passionate about the work and the organization.Degree to which employee is emotionally connected',5,'10','EMPLOYEE','2018-08-31 10:53:41',NULL,'admin',NULL,1),(46,'COMMUNICATION','Articulates ideas in a clear,concise and assertive manner so that ithers understand the talk.Is able to clearly communicate and understand the written and oral communication',5,'5','EMPLOYEE','2018-08-31 10:53:41',NULL,'admin',NULL,1),(47,'TEAMWORK','Assisstes others when needed .Participates effectively in the team work by offering ideas and implementing agreed plans.Is a patient Listener.Prevents or resolves conflict.Effectively manages team when needed',5,'10','EMPLOYEE','2018-08-31 10:53:41',NULL,'admin',NULL,1),(48,'INTEGRITY','Basis honesty of charracter, adhere to the moral system of SELF and the\nCompany.',5,'15','HOD','2018-08-31 10:53:41',NULL,'admin',NULL,1),(49,'ORGANIZATIONAL AND PLANNING','Set priorities to optimize time usage .Engages in short and long term planning proposes milestones which allow progress to be adequately measured.Adheres to schedules and plans.',5,'20','HOD','2018-08-31 10:53:41',NULL,'admin',NULL,1),(50,'ANALYTICAL AND PROBLEM SOLVING','Understands and defines problem clearly. Formulates realistic implementable solutions. Participates constructively in group problem solving Anticipates and prevnts occurance of problems',5,'10','HOD','2018-08-31 10:53:41',NULL,'admin',NULL,1),(51,'JUDGEMENT AND DECISION MAKING','Consider relevent alternatives and the background before making decisions. Is able to take good decision within the time frame',5,'10','HOD','2018-08-31 10:53:41',NULL,'admin',NULL,1),(52,'SELF IMPROVEMENT AND INITIATIVE','Responds positively after receiving feedback from his/her reporting manager,peers and team mates. Takes initiatives to propoe improvement in self work,team work and others.',5,'10','HOD','2018-08-31 10:53:41',NULL,'admin',NULL,1),(53,'INNOVATION AND CREATIVITY','Genrates good and feasible (implementable) ideas, concepts and techniques.Willing to attempt new approches. Simplifies and/or improves,techniques and process',5,'10','HOD','2018-08-31 10:53:41',NULL,'admin',NULL,1),(54,'PASSION','Is passionate about the work and the organization.Degree to which employee is emotionally connected',5,'10','HOD','2018-08-31 10:53:41',NULL,'admin',NULL,1),(55,'COMMUNICATION','Articulates ideas in a clear,concise and assertive manner so that ithers understand the talk.Is able to clearly communicate and understand the written and oral communication',5,'5','HOD','2018-08-31 10:53:41',NULL,'admin',NULL,1),(56,'TEAMWORK','Assisstes others when needed .Participates effectively in the team work by offering ideas and implementing agreed plans.Is a patient Listener.Prevents or resolves conflict.Effectively manages team when needed',5,'5','HOD','2018-08-31 10:53:41',NULL,'admin',NULL,1),(57,'Testing','Test',5,'5','HOD','2018-08-31 10:53:41',NULL,'admin',NULL,1);
/*!40000 ALTER TABLE `behavioural_competence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `behavioural_competence_details`
--

DROP TABLE IF EXISTS `behavioural_competence_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `behavioural_competence_details` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMP_CODE` varchar(200) NOT NULL,
  `BEHAVIOURAL_DETAILS_ID` int(11) NOT NULL,
  `APPRAISAL_YEAR_ID` int(11) NOT NULL,
  `COMMENTS` longtext,
  `MID_YEAR_SELF_RATING` int(11) DEFAULT NULL,
  `MID_YEAR_ASSESSOR_RATING` int(11) DEFAULT NULL,
  `FINAL_YEAR_SELF_RATING` int(11) DEFAULT NULL,
  `FINAL_YEAR_ASSESSOR_RATING` int(11) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(45) DEFAULT NULL,
  `MODIFIED_BY` varchar(45) DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  `ISVALIDATEDBY_EMPLOYEE` tinyint(4) NOT NULL,
  `ISVALIDATEDBY_FIRSTLEVEL` tinyint(4) NOT NULL,
  `ISVALIDATEDBY_SECONDLEVEL` tinyint(4) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `behavioural_competence_details`
--

LOCK TABLES `behavioural_competence_details` WRITE;
/*!40000 ALTER TABLE `behavioural_competence_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `behavioural_competence_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `career_aspirations_details`
--

DROP TABLE IF EXISTS `career_aspirations_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `career_aspirations_details` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMP_CODE` varchar(200) NOT NULL,
  `APPRAISAL_YEAR_ID` int(11) NOT NULL,
  `INITIALIZATION_COMMENTS` longtext CHARACTER SET utf8,
  `INITIALIZATION_MANAGER_REVIEW` longtext,
  `MID_YEAR_COMMENTS` longtext,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(200) DEFAULT NULL,
  `MODIFIED_BY` varchar(200) DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  `ISVALIDATEDBY_EMPLOYEE` tinyint(4) NOT NULL,
  `ISVALIDATEDBY_FIRSTLEVEL` tinyint(4) NOT NULL,
  `ISVALIDATEDBY_SECONDLEVEL` tinyint(4) NOT NULL,
  `MID_YEAR_COMMENTS_STATUS` tinyint(4) DEFAULT NULL,
  `MID_YEAR_COMMENTS_STATUS_FIRST_LEVEL` tinyint(4) DEFAULT NULL,
  `MID_YEAR_COMMENTS_STATUS_SECOND_LEVEL` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `career_aspirations_details`
--

LOCK TABLES `career_aspirations_details` WRITE;
/*!40000 ALTER TABLE `career_aspirations_details` DISABLE KEYS */;
INSERT INTO `career_aspirations_details` VALUES (1,'1712284',2,'vjfdlsjfjlsjsf','jkljjj',NULL,'2018-09-07 14:19:21','2018-09-08 09:27:02','1712284','1712284',1,1,1,1,0,0,0);
/*!40000 ALTER TABLE `career_aspirations_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `department`
--

DROP TABLE IF EXISTS `department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `department` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(1000) CHARACTER SET utf8 NOT NULL,
  `DESCRIPTION` longtext,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(200) DEFAULT NULL,
  `MODIFIED_BY` varchar(200) DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `department`
--

LOCK TABLES `department` WRITE;
/*!40000 ALTER TABLE `department` DISABLE KEYS */;
INSERT INTO `department` VALUES (1,'Human Resource','This is Human Resource',NULL,'2018-05-03 17:46:08',NULL,NULL,1),(2,'IT','This is IT',NULL,'2018-05-03 17:46:11',NULL,NULL,1),(3,'Finance','This is Finance',NULL,'2018-05-03 17:46:14',NULL,NULL,1),(4,'Sales','This is Sales',NULL,'2018-05-03 17:46:17',NULL,NULL,1),(5,'Marketing','This is marketing field.','2018-06-04 14:30:06',NULL,'admin',NULL,1),(6,'Management','Management','2018-09-05 11:22:36',NULL,'admin',NULL,1);
/*!40000 ALTER TABLE `department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `designation`
--

DROP TABLE IF EXISTS `designation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `designation` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(1000) CHARACTER SET utf8 NOT NULL,
  `DESCRIPTION` longtext,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(200) DEFAULT NULL,
  `MODIFIED_BY` varchar(200) DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `designation`
--

LOCK TABLES `designation` WRITE;
/*!40000 ALTER TABLE `designation` DISABLE KEYS */;
INSERT INTO `designation` VALUES (1,'Software Engineer','Post is Software Engineer',NULL,'2018-05-21 14:19:32',NULL,'admin',1),(2,'Designer','Post is Designer',NULL,'2018-05-21 14:19:24',NULL,'admin',1),(3,'Developer','Post is developer',NULL,NULL,NULL,NULL,1),(4,'Junior Developer','Post is Junior Developer.','2018-06-04 14:31:21',NULL,'admin',NULL,1),(5,'HR Manager','HR Manager','2018-09-05 11:07:53',NULL,'admin',NULL,1),(6,'General Manager - HR','GM - HR','2018-09-05 11:08:12',NULL,'admin',NULL,1),(7,'Assistant Manager - HR','AM - HR','2018-09-05 11:08:23',NULL,'admin',NULL,1),(8,'Senior Executive - HR','Sr Executive - HR','2018-09-05 11:08:40',NULL,'admin',NULL,1),(9,'Officer - HR','Officer - HR','2018-09-05 11:08:56',NULL,'admin',NULL,1),(10,'CEO & ED','CEO & ED','2018-09-05 11:17:47',NULL,'admin',NULL,1);
/*!40000 ALTER TABLE `designation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMP_CODE` varchar(200) NOT NULL,
  `EMP_TYPE` varchar(100) NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  `EMP_NAME` mediumtext NOT NULL,
  `MOBILE` varchar(25) NOT NULL,
  `EMAIL` varchar(500) NOT NULL,
  `DATE_OF_JOINING` datetime NOT NULL,
  `DEPARTMENT_ID` int(11) NOT NULL,
  `QUALIFICATION` mediumtext NOT NULL,
  `DESIGNATION_ID` int(11) NOT NULL,
  `ORGANIZATION_ROLE_ID` int(11) NOT NULL,
  `FIRST_LEVEL_SUPERIOR_EMP_ID` varchar(200) DEFAULT NULL,
  `SECOND_LEVEL_SUPERIOR_EMP_ID` varchar(200) DEFAULT NULL,
  `LOCATION` mediumtext,
  `JOB_DESCRIPTION` varchar(500) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(200) DEFAULT NULL,
  `MODIFIED_BY` varchar(200) DEFAULT NULL,
  `DOCUMENT` varchar(100) DEFAULT NULL,
  `FIELD_1` longtext,
  `FIELD_2` longtext,
  `FIELD_3` longtext,
  `FIELD_4` longtext,
  `FIELD_5` longtext,
  `FIELD_6` longtext,
  `FIELD_7` longtext,
  `FIELD_8` longtext,
  `FIELD_9` longtext,
  `FIELD_10` longtext,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (1,'DEFAULT_CODE','NON-HOD',3,'DEFAULT_NAME','9999999999','default@gmail.com','2018-05-03 12:09:55',0,'MBA',0,0,'0','0',NULL,NULL,'2018-05-03 12:09:55',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(2,'EMP1','NON-HOD',3,'Tishu Singh','9999999988','tishusingh008@gmail.com','2002-06-11 00:00:00',3,'MBA',3,3,'DEFAULT_CODE','DEFAULT_CODE','Gurgaon','Testfgsjbdfjghg','2018-08-07 10:09:37',NULL,'admin',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(3,'EMP2','NON-HOD',3,'Ankit Kumar','9999999988','ankit25496.kumar@gmail.com','2014-06-19 00:00:00',3,'MBA',3,3,'DEFAULT_CODE','DEFAULT_CODE','Gurgaon','Ankit jfhkjafdsaf','2018-08-07 10:30:49',NULL,'admin',NULL,'',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(4,'EMP3','NON-HOD',2,'Pankaj Signh negi','7855555555','pankaj.negi505@gmail.com','2018-05-05 00:00:00',1,'MBA',1,1,'EMP1','EMP2','dfdfdfd','fddff','2018-08-07 10:34:23','2018-08-08 09:28:09','admin','admin','/PMS/images?image=0946b2fd-f1dd-47ef-8c53-8e5dff95cffb.docx&folder=document',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(5,'EMP4','NON-HOD',2,'Rajat Mehra','7855555555','rajatmehra1054@gmail.com','2018-05-05 00:00:00',1,'MBA',1,1,'EMP1','EMP2','dfdfdfd','fddff','2018-08-07 10:34:23',NULL,'admin',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(6,'1211002','HOD',3,'Sunil','9810518308','sheetu.garg@herofutureenergies.com','2012-11-15 00:00:00',6,'MBA',10,8,'DEFAULT_CODE','DEFAULT_CODE','Delhi','CEO','2018-09-05 11:22:03','2018-09-05 11:23:31','admin','admin','/PMS/images?image=null&folder=document',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(7,'1301009','HOD',3,'Bhawna Kirpal Mital','9810309105','bhawnak.mital@herofutureenergies.com','2013-01-02 00:00:00',1,'MBA',6,7,'1211002','1211002','Delhi','Head HR','2018-09-05 11:45:53',NULL,'admin',NULL,'/PMS/images?image=null&folder=document',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(8,'1606145','NON-HOD',3,'SHAILESH CHANDRA PANDEY','9560519056','shailesh.pandey@herofutureenergies.com','2016-01-20 00:00:00',1,'MBA',5,6,'1301009','1211002','Delhi','HR Manager','2018-09-05 11:48:56',NULL,'admin',NULL,'/PMS/images?image=null&folder=document',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(9,'1808341','NON-HOD',2,'Pravendra Pratap Singh','9910259321','pravendra.singh@herofutureenergies.com','2018-08-07 00:00:00',1,'MBA',8,6,'1606145','1301009','Delhi','HR Generlist','2018-09-05 11:51:07',NULL,'admin',NULL,'/PMS/images?image=null&folder=document',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(10,'1712284','NON-HOD',3,'Sheetu','8800500965','sheetu.garg@herofutureenergies.com','2017-12-18 00:00:00',1,'MBA',5,6,'1301009','1211002','Delhi','HR Generlist','2018-09-05 11:52:30',NULL,'admin',NULL,'/PMS/images?image=null&folder=document',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(11,'1234','NON-HOD',2,'Hari','9999999999','tishusingh008@gmail.com','2017-02-07 00:00:00',1,'B.Com, MBA, PHD',7,1,'EMP1','EMP2','New Delhi','dkfkladh dakfsdkhfksad','2018-09-06 11:22:15','2018-09-06 11:25:30','admin','admin','/PMS/images?image=null&folder=document',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,1),(12,'56789','NON-HOD',2,'Pal','9999999999','tishusingh008@gmail.com','2015-02-10 00:00:00',2,'B.Tech,MBA',3,2,'EMP2','EMP1','New Delhi','dkasndnksa','2018-09-10 09:48:47','2018-09-10 09:54:30','admin','admin','/PMS/images?image=null&folder=document','Field1','Field2','Field3','Field4','Field5','Field66','Field77','Field88','Field99','Field1010',1);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_dynamic_form_details`
--

DROP TABLE IF EXISTS `employee_dynamic_form_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_dynamic_form_details` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LABEL_NAME` mediumtext NOT NULL,
  `NAME` mediumtext NOT NULL,
  `DESCRIPTION` mediumtext,
  `APPRAISAL_YEAR_ID` int(11) NOT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(200) DEFAULT NULL,
  `MODIFIED_BY` varchar(200) DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_dynamic_form_details`
--

LOCK TABLES `employee_dynamic_form_details` WRITE;
/*!40000 ALTER TABLE `employee_dynamic_form_details` DISABLE KEYS */;
INSERT INTO `employee_dynamic_form_details` VALUES (1,'Field-1','Field-1','Field-1',2,'2018-09-10 09:43:10','2018-09-10 09:43:15',NULL,'admin',1),(2,'Field-2','Field-2','Field-2',2,'2018-05-03 17:46:08','2018-09-10 09:43:21',NULL,'admin',1),(3,'Field-3','Field-3','Field-3',2,'2018-05-03 17:46:08','2018-09-10 09:43:24',NULL,'admin',1),(4,'Field-4','Field-4','Field-4',2,'2018-09-10 09:43:18','2018-09-10 09:46:22',NULL,'admin',1),(5,'Field-5','Field-5','Field-5',2,'2018-05-03 17:46:08','2018-09-10 09:46:27',NULL,'admin',1),(6,'Field-6','Field-6','Field-6',2,'2018-05-03 17:46:08','2018-09-10 09:46:30',NULL,'admin',1),(7,'Field-7','Field-7','Field-7',2,'2018-05-03 17:46:08','2018-09-10 09:46:34',NULL,'admin',1),(8,'Field-8','Field-8','Field-8',2,'2018-05-03 17:46:08','2018-09-10 09:46:37',NULL,'admin',1),(9,'Field-9','Field-9','Field-9',2,'2018-05-03 17:46:08','2018-09-10 09:46:40',NULL,'admin',1),(10,'Field-10','Field-10','Field-10',2,'2018-05-03 17:46:08','2018-09-10 09:46:43',NULL,'admin',1);
/*!40000 ALTER TABLE `employee_dynamic_form_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_emails`
--

DROP TABLE IF EXISTS `employee_emails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_emails` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `APPRAISAL_YEAR_ID` int(11) NOT NULL,
  `EMPLOYEE_PASSWORD` varchar(100) DEFAULT NULL,
  `EMPLOYEE_NAME` varchar(500) DEFAULT NULL,
  `SEND_TO` varchar(500) NOT NULL,
  `SEND_FROM` varchar(500) NOT NULL,
  `CREATED_ON` varchar(400) NOT NULL,
  `STATUS` tinyint(4) DEFAULT NULL,
  `DELIVERY_STATUS` varchar(100) DEFAULT NULL,
  `EMAIL_TYPE` varchar(100) NOT NULL,
  `EMAIL_SUBJECT` mediumtext,
  `EMAIL_CONTENT` longtext,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_emails`
--

LOCK TABLES `employee_emails` WRITE;
/*!40000 ALTER TABLE `employee_emails` DISABLE KEYS */;
INSERT INTO `employee_emails` VALUES (1,2,NULL,'Sheetu','sheetu.garg@herofutureenergies.com','ankit25496.kumar@gmail.com','2018-09-07 14:26:45.0',1,'Succesfully Delivered','EMPLOYEE APPRAISAL APPROVAL','Appraisal Approval ','<p style=\'padding-bottom:5px;font-size: 10.5pt;color: #315773;\'>Best,<br><span style=\"color:red;font-weight: bold;\">SHEETU ARYA GARG </span><br>Manager - HR<br><strong>M</strong> +91 8800500965     <b>P</b> +91 11 49598033     <strong>F</strong> +91 11 49598022<br><b>W</b> www.herofutureenergies.com </p>'),(2,2,NULL,'Sheetu','bhawnak.mital@herofutureenergies.com','ankit25496.kumar@gmail.com','2018-09-07 14:26:45.0',1,'Succesfully Delivered','MANAGER APPRAISAL APPROVAL','Appraisal Approval ','<p style=\'padding-bottom:5px;font-size: 10.5pt;color: #315773;\'>Best,<br><span style=\"color:red;font-weight: bold;\">SHEETU ARYA GARG </span><br>Manager - HR<br><strong>M</strong> +91 8800500965     <b>P</b> +91 11 49598033     <strong>F</strong> +91 11 49598022<br><b>W</b> www.herofutureenergies.com </p>'),(3,2,NULL,'Sheetu','bhawnak.mital@herofutureenergies.com','ankit25496.kumar@gmail.com','2018-09-08 09:22:23.0',1,'Succesfully Delivered','MID FIRST LEVEL MANAGER APPRAISAL APPROVAL','Appraisal Approval ','<p style=\'padding-bottom:5px;font-size: 10.5pt;color: #315773;\'>Best,<br><span style=\"color:red;font-weight: bold;\">SHEETU ARYA GARG </span><br>Manager - HR<br><strong>M</strong> +91 8800500965     <b>P</b> +91 11 49598033     <strong>F</strong> +91 11 49598022<br><b>W</b> www.herofutureenergies.com </p>'),(4,2,NULL,'Sheetu','sheetu.garg@herofutureenergies.com','ankit25496.kumar@gmail.com','2018-09-08 09:22:23.0',1,'Succesfully Delivered','MID SECOND LEVEL MANAGER APPRAISAL APPROVAL','Appraisal Approval ','<p style=\'padding-bottom:5px;font-size: 10.5pt;color: #315773;\'>Best,<br><span style=\"color:red;font-weight: bold;\">SHEETU ARYA GARG </span><br>Manager - HR<br><strong>M</strong> +91 8800500965     <b>P</b> +91 11 49598033     <strong>F</strong> +91 11 49598022<br><b>W</b> www.herofutureenergies.com </p>'),(5,2,NULL,'Sheetu','sheetu.garg@herofutureenergies.com','ankit25496.kumar@gmail.com','2018-09-08 09:27:18.0',1,'Succesfully Delivered','FINAL EMPLOYEE APPRAISAL APPROVAL','Appraisal Approval ','<p style=\'padding-bottom:5px;font-size: 10.5pt;color: #315773;\'>Best,<br><span style=\"color:red;font-weight: bold;\">SHEETU ARYA GARG </span><br>Manager - HR<br><strong>M</strong> +91 8800500965     <b>P</b> +91 11 49598033     <strong>F</strong> +91 11 49598022<br><b>W</b> www.herofutureenergies.com </p>'),(6,2,NULL,'Sheetu','sheetu.garg@herofutureenergies.com','ankit25496.kumar@gmail.com','2018-09-08 09:27:18.0',1,'Succesfully Delivered','FINAL SECOND LEVEL MANAGER APPRAISAL APPROVAL','Appraisal Approval ','<p style=\'padding-bottom:5px;font-size: 10.5pt;color: #315773;\'>Best,<br><span style=\"color:red;font-weight: bold;\">SHEETU ARYA GARG </span><br>Manager - HR<br><strong>M</strong> +91 8800500965     <b>P</b> +91 11 49598033     <strong>F</strong> +91 11 49598022<br><b>W</b> www.herofutureenergies.com </p>'),(7,2,NULL,NULL,'sheetu.garg@herofutureenergies.com','ankit25496.kumar@gmail.com','2018-09-08 09:28:23.0',1,'Succesfully Delivered','EMPLOYEE APPRAISAL','Mid Year Appraisal','<p>Hi,</p>\n\n<p>Mid year appraisal started.</p>\n'),(8,2,NULL,NULL,'bhawnak.mital@herofutureenergies.com','ankit25496.kumar@gmail.com','2018-09-08 09:28:23.0',1,'Succesfully Delivered','EMPLOYEE APPRAISAL','Mid Year Appraisal','<p>Hi,</p>\n\n<p>Mid year appraisal started.</p>\n'),(9,2,NULL,NULL,'shailesh.pandey@herofutureenergies.com','ankit25496.kumar@gmail.com','2018-09-08 09:28:23.0',1,'Succesfully Delivered','EMPLOYEE APPRAISAL','Mid Year Appraisal','<p>Hi,</p>\n\n<p>Mid year appraisal started.</p>\n'),(10,2,NULL,NULL,'pravendra.singh@herofutureenergies.com','ankit25496.kumar@gmail.com','2018-09-08 09:28:23.0',1,'Succesfully Delivered','EMPLOYEE APPRAISAL','Mid Year Appraisal','<p>Hi,</p>\n\n<p>Mid year appraisal started.</p>\n'),(11,2,NULL,NULL,'sheetu.garg@herofutureenergies.com','ankit25496.kumar@gmail.com','2018-09-08 09:28:23.0',1,'Succesfully Delivered','EMPLOYEE APPRAISAL','Mid Year Appraisal','<p>Hi,</p>\n\n<p>Mid year appraisal started.</p>\n'),(12,2,'[C@1da8f124','Pal','tishusingh008@gmail.com','ankit25496.kumar@gmail.com','2018-09-10 09:48:47.0',1,'Succesfully Delivered','NEW EMPLOYEE','Verify One Time Password',NULL);
/*!40000 ALTER TABLE `employee_emails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `employee_promotions`
--

DROP TABLE IF EXISTS `employee_promotions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employee_promotions` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMP_CODE` varchar(200) NOT NULL,
  `APPRAISAL_YEAR_ID` int(11) NOT NULL,
  `FIRST_LEVEL_MANAGER_ID` varchar(200) DEFAULT NULL,
  `SECOND_LEVEL_MANAGER_ID` varchar(200) DEFAULT NULL,
  `RECOMMENDED_DESIGNATION` longtext,
  `SPECIFIC_ACHIEVEMENTS` longtext,
  `EXPECTATIONS` longtext,
  `PROMOTION_IMPACT` longtext,
  `JOB_RESPONSIBILITY` longtext,
  `DEPARTMENTAL_LEVEL` longtext,
  `ORGANISATIONAL_LEVEL` longtext,
  `ADDITONAL_TRAINING` longtext,
  `NEXT_FIVE_YEARS` longtext,
  `FIRST_LEVEL_SUPERIOR_COMMENTS` longtext,
  `FIRST_LEVEL_SUPERIOR_COMMENTS_DATE` datetime DEFAULT NULL,
  `SECOND_LEVEL_SUPERIOR_COMMENTS` longtext,
  `SECOND_LEVEL_SUPERIOR_COMMENTS_DATE` datetime DEFAULT NULL,
  `HR_COMMENTS` longtext,
  `HR_COMMENTS_DATE` datetime DEFAULT NULL,
  `APPROVED_BY_COMMENTS` longtext,
  `APPROVED_BY_DATE` datetime DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `MODIFIED_BY` varchar(100) DEFAULT NULL,
  `STATUS` tinyint(4) DEFAULT NULL,
  `FIRST_LEVEL_CHECK` tinyint(4) DEFAULT NULL,
  `SECOND_LEVEL_CHECK` tinyint(4) DEFAULT NULL,
  `HR_CHECK` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee_promotions`
--

LOCK TABLES `employee_promotions` WRITE;
/*!40000 ALTER TABLE `employee_promotions` DISABLE KEYS */;
/*!40000 ALTER TABLE `employee_promotions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `extra_ordinary`
--

DROP TABLE IF EXISTS `extra_ordinary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `extra_ordinary` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LABEL_NAME` varchar(1000) NOT NULL,
  `NAME` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `APPRAISAL_YEAR_ID` int(11) NOT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(45) DEFAULT NULL,
  `MODIFIED_BY` varchar(45) DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `extra_ordinary`
--

LOCK TABLES `extra_ordinary` WRITE;
/*!40000 ALTER TABLE `extra_ordinary` DISABLE KEYS */;
INSERT INTO `extra_ordinary` VALUES (1,'Field_1','Field_1','Field_1',2,'2018-06-08 15:06:30','2018-06-08 16:03:17',NULL,'admin',1),(2,'Field_2','Field_2','Field_2',0,NULL,'2018-05-30 14:15:37',NULL,'',0),(3,'Field_3','Field_3','Field_3',0,NULL,'2018-05-11 14:06:53',NULL,NULL,0),(4,'Field_4','Field_4','Field_4',0,NULL,'2018-05-17 14:10:42',NULL,'',0),(5,'Field_5','Field_5','Field_5',0,NULL,'2018-05-11 14:09:50',NULL,NULL,0);
/*!40000 ALTER TABLE `extra_ordinary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `extra_ordinary_details`
--

DROP TABLE IF EXISTS `extra_ordinary_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `extra_ordinary_details` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMP_CODE` varchar(200) NOT NULL,
  `APPRAISAL_YEAR_ID` int(11) DEFAULT NULL,
  `CONTRIBUTIONS` longtext CHARACTER SET utf8,
  `CONTRIBUTION_DETAILS` longtext CHARACTER SET utf8,
  `WEIGHTAGE` varchar(45) DEFAULT NULL,
  `FINAL_YEAR_SELF_RATING` int(11) DEFAULT NULL,
  `FINAL_YEAR_APPRAISAR_RATING` int(11) DEFAULT NULL,
  `FINAL_SCORE` longtext,
  `REMARKS` longtext,
  `FIELD_1` longtext,
  `FIELD_2` longtext,
  `FIELD_3` longtext,
  `FIELD_4` longtext,
  `FIELD_5` longtext,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(200) DEFAULT NULL,
  `MODIFIED_BY` varchar(200) DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL,
  `ISVALIDATEDBY_EMPLOYEE` tinyint(4) DEFAULT NULL,
  `ISVALIDATEDBY_FIRSTLEVEL` tinyint(4) DEFAULT NULL,
  `ISVALIDATEDBY_SECONDLEVEL` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `extra_ordinary_details`
--

LOCK TABLES `extra_ordinary_details` WRITE;
/*!40000 ALTER TABLE `extra_ordinary_details` DISABLE KEYS */;
INSERT INTO `extra_ordinary_details` VALUES (1,'1712284',2,'vknlfnslvndfls','nasnvnklnvkandflv','10',0,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-09-10 10:03:41',NULL,'1712284',NULL,1,1,0,0);
/*!40000 ALTER TABLE `extra_ordinary_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `final_review_details`
--

DROP TABLE IF EXISTS `final_review_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `final_review_details` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMP_CODE` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `APPRAISAL_YEAR_ID` int(11) NOT NULL,
  `FIRST_LEVEL_SUPERIOR_COMMENTS` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `SECOND_LEVEL_SUPERIOR_COMMENTS` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `ASSESSEE_COMMENTS` varchar(1000) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `MODIFIED_BY` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `final_review_details`
--

LOCK TABLES `final_review_details` WRITE;
/*!40000 ALTER TABLE `final_review_details` DISABLE KEYS */;
/*!40000 ALTER TABLE `final_review_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kra_details`
--

DROP TABLE IF EXISTS `kra_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `kra_details` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMP_CODE` varchar(200) NOT NULL,
  `APPRAISAL_YEAR_ID` int(11) NOT NULL,
  `SECTION_NAME` varchar(500) CHARACTER SET utf8 NOT NULL,
  `SMART_GOAL` longtext CHARACTER SET utf8,
  `TARGET` longtext CHARACTER SET utf8,
  `ACHIEVEMENT_DATE` datetime DEFAULT NULL,
  `WEIGHTAGE` varchar(45) DEFAULT NULL,
  `MID_YEAR_ACHIEVEMENT` longtext CHARACTER SET utf8,
  `MID_YEAR_SELF_RATING` int(11) DEFAULT NULL,
  `MID_YEAR_APPRAISAR_RATING` int(11) DEFAULT NULL,
  `MID_YEAR_ASSESSMENT_REMARKS` longtext,
  `FINAL_YEAR_ACHIEVEMENT` longtext,
  `FINAL_YEAR_SELF_RATING` int(11) DEFAULT NULL,
  `FINAL_YEAR_APPRAISAR_RATING` int(11) DEFAULT NULL,
  `REMARKS` longtext CHARACTER SET utf8,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(100) DEFAULT NULL,
  `MODIFIED_BY` varchar(100) DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  `ISVALIDATEDBY_EMPLOYEE` tinyint(4) NOT NULL DEFAULT '0',
  `ISVALIDATEDBY_FIRSTLEVEL` tinyint(4) NOT NULL DEFAULT '0',
  `ISVALIDATEDBY_SECONDLEVEL` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kra_details`
--

LOCK TABLES `kra_details` WRITE;
/*!40000 ALTER TABLE `kra_details` DISABLE KEYS */;
INSERT INTO `kra_details` VALUES (1,'1712284',2,'Section A','FSFKGSKDFS','FHDASFHDJKHFHSDFKHSDLFHLSDHFLDHSFHDKLSHFKLHSDFHKLSDFHKLDHFKHSDKLFHDHFKLDHKLFHSDKLHFLSHDLFHSDKLHFKLSDHFKLHSDFHALSDHFLDHSFKLHSDKLFHKLASHFLDHSAFHSDKLFHASDLH','2018-09-07 00:00:00','50',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-09-07 10:32:07','2018-09-08 09:26:51','1712284',NULL,1,0,0,0),(2,'1712284',2,'Section B',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-09-07 10:32:08','2018-09-08 09:26:51','1712284',NULL,1,0,0,0),(3,'1712284',2,'Section C','FKASHFLHFLSDA','NSD,NFASDN','2018-09-13 00:00:00','45',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-09-07 10:32:08','2018-09-08 09:26:51','1712284',NULL,1,0,0,0),(4,'1712284',2,'Section D','Ethics','100% Compliance with the Policies','2018-09-12 00:00:00','2',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-09-07 10:32:08','2018-09-08 09:26:51','1712284',NULL,1,0,0,0),(5,'1712284',2,'Section D','Organisational culture','Compliance with the Policies','2018-09-26 00:00:00','2',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-09-07 10:32:08','2018-09-08 09:26:51','1712284',NULL,1,0,0,0),(6,'1712284',2,'Section D','Code of Conduct, Green Code, Branding and Internal Communication','Compliance with the Policies','2018-09-20 00:00:00','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-09-07 10:32:08','2018-09-08 09:26:51','1712284',NULL,1,0,0,0);
/*!40000 ALTER TABLE `kra_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `menus_item_details`
--

DROP TABLE IF EXISTS `menus_item_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `menus_item_details` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LINK` varchar(100) NOT NULL,
  `ICON` varchar(45) NOT NULL,
  `TITLE` varchar(100) NOT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(45) DEFAULT NULL,
  `MODIFIED_BY` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menus_item_details`
--

LOCK TABLES `menus_item_details` WRITE;
/*!40000 ALTER TABLE `menus_item_details` DISABLE KEYS */;
INSERT INTO `menus_item_details` VALUES (1,'dashboard','glyphicon glyphicon-th','Dashboard',NULL,NULL,NULL,NULL,NULL,1),(2,'master-page','glyphicon glyphicon-th','Master Page',NULL,NULL,NULL,NULL,NULL,1),(3,'appraisal-cycle','glyphicon glyphicon-th','Appraisal Cycle',NULL,NULL,NULL,NULL,NULL,1),(4,'employee-list','glyphicon glyphicon-th','Employee List',NULL,NULL,NULL,NULL,NULL,1),(5,'employee-kra','glyphicon glyphicon-th','My KRA',NULL,NULL,NULL,NULL,NULL,1),(6,'master-department-list','glyphicon glyphicon-th','Department List',NULL,NULL,NULL,NULL,NULL,1),(7,'master-designation-list','glyphicon glyphicon-th','Designation List',NULL,NULL,NULL,NULL,NULL,1),(8,'master-organization-role-list','glyphicon glyphicon-th','Organization Role List',NULL,NULL,NULL,NULL,NULL,1),(9,'master-roles-list','glyphicon glyphicon-th','Role List',NULL,NULL,NULL,NULL,NULL,1),(10,'master-qualification-list','glyphicon glyphicon-th','Qualification List',NULL,NULL,NULL,NULL,NULL,1),(11,'master-training-needs-list','glyphicon glyphicon-th','Training Needs List',NULL,NULL,NULL,NULL,NULL,1),(12,'master-behavioural-list','glyphicon glyphicon-th','Behavioural List',NULL,NULL,NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `menus_item_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `organization_roles`
--

DROP TABLE IF EXISTS `organization_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `organization_roles` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) DEFAULT NULL,
  `DEPARTMENT_ID` int(11) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(45) DEFAULT NULL,
  `MODIFIED_BY` varchar(45) DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `organization_roles`
--

LOCK TABLES `organization_roles` WRITE;
/*!40000 ALTER TABLE `organization_roles` DISABLE KEYS */;
INSERT INTO `organization_roles` VALUES (1,'ROLE_JUNIOR_HR',1,'This is junior HR role.','2018-06-04 14:29:33',NULL,'admin',NULL,1),(2,'ROLE_SENIOR_SOFTWARE_ENGINEER',2,'Post is SENIOR SOFTWARE ENGINEER.','2018-06-04 15:30:03',NULL,'admin',NULL,1),(3,'ROLE_JUNIOR_FINANCE',3,'JUNIOR FINANCE','2018-06-12 17:46:25',NULL,'admin',NULL,1),(4,'ROLE_SALES_EXECUTIVE',5,'Business development','2018-06-22 10:15:48',NULL,'admin',NULL,1),(5,'ROLE_JUNIOR_SALE_PERSON',4,'sfgdfgf','2018-08-07 10:41:51',NULL,'admin',NULL,1),(6,'ROLE_HR_GENERALIST',1,'HR Generalist','2018-09-05 11:11:29',NULL,'admin',NULL,1),(7,'ROLE_HR_HEAD',1,'HR Head','2018-09-05 11:11:41',NULL,'admin',NULL,1),(8,'ROLE_CEO_&_ED',6,'CEO & ED','2018-09-05 11:22:48',NULL,'admin',NULL,1);
/*!40000 ALTER TABLE `organization_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parameters`
--

DROP TABLE IF EXISTS `parameters`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parameters` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(45) NOT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(45) DEFAULT NULL,
  `MODIFIED_BY` varchar(45) DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parameters`
--

LOCK TABLES `parameters` WRITE;
/*!40000 ALTER TABLE `parameters` DISABLE KEYS */;
INSERT INTO `parameters` VALUES (1,'Department',NULL,NULL,NULL,NULL,1),(2,'Designation',NULL,NULL,NULL,NULL,1),(3,'Qualification',NULL,NULL,NULL,NULL,1),(4,'Organizational Role',NULL,NULL,NULL,NULL,1),(5,'Application Role',NULL,NULL,NULL,NULL,1);
/*!40000 ALTER TABLE `parameters` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `password_reset`
--

DROP TABLE IF EXISTS `password_reset`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `password_reset` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `TOKEN` varchar(100) NOT NULL,
  `CREATED_ON` datetime NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `password_reset`
--

LOCK TABLES `password_reset` WRITE;
/*!40000 ALTER TABLE `password_reset` DISABLE KEYS */;
/*!40000 ALTER TABLE `password_reset` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `qualification`
--

DROP TABLE IF EXISTS `qualification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `qualification` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) CHARACTER SET utf8 NOT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(45) DEFAULT NULL,
  `MODIFIED_BY` varchar(45) DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `qualification`
--

LOCK TABLES `qualification` WRITE;
/*!40000 ALTER TABLE `qualification` DISABLE KEYS */;
INSERT INTO `qualification` VALUES (1,'B.Tech','This is B.tech','2018-06-04 14:31:39','2018-06-04 14:31:50','admin','admin',1),(2,'Graduction','This is Graduction.','2018-06-04 14:32:11',NULL,'admin',NULL,1),(3,'MBA','MBA','2018-09-05 11:09:28',NULL,'admin',NULL,1),(4,'B Com','B Com','2018-09-05 11:09:47',NULL,'admin',NULL,1),(5,'BSC','BSC','2018-09-05 11:09:57',NULL,'admin',NULL,1);
/*!40000 ALTER TABLE `qualification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_menus_item_details_mapping`
--

DROP TABLE IF EXISTS `role_menus_item_details_mapping`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_menus_item_details_mapping` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROLE_ID` int(11) NOT NULL,
  `MENUS_ITEM_DETAILS_ID` int(11) NOT NULL,
  `CAN_VIEW` int(11) DEFAULT NULL,
  `CAN_EDIT` int(11) DEFAULT NULL,
  `CAN_ADD` int(11) DEFAULT NULL,
  `CAN_APPROVE_AUTHENTICATION` int(11) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(45) DEFAULT NULL,
  `MODIFIED_BY` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_menus_item_details_mapping`
--

LOCK TABLES `role_menus_item_details_mapping` WRITE;
/*!40000 ALTER TABLE `role_menus_item_details_mapping` DISABLE KEYS */;
INSERT INTO `role_menus_item_details_mapping` VALUES (1,1,1,1,1,1,1,NULL,NULL,NULL,NULL),(2,1,3,1,1,1,1,NULL,NULL,NULL,NULL),(3,1,6,1,1,1,1,NULL,NULL,NULL,NULL),(4,1,7,1,1,1,1,NULL,NULL,NULL,NULL),(5,1,8,1,1,1,1,NULL,NULL,NULL,NULL),(6,1,9,1,1,1,1,NULL,NULL,NULL,NULL),(7,1,10,1,1,1,1,NULL,NULL,NULL,NULL),(8,1,11,1,1,1,1,NULL,NULL,NULL,NULL),(9,1,12,1,1,1,1,NULL,NULL,NULL,NULL),(10,2,4,1,1,1,1,NULL,NULL,NULL,NULL),(11,1,4,1,1,1,1,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `role_menus_item_details_mapping` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(100) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(45) DEFAULT NULL,
  `MODIFIED_BY` varchar(45) DEFAULT NULL,
  `DESCRIPTION` varchar(500) DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_ADMIN',NULL,'2018-05-07 14:29:29',NULL,NULL,'Ljhljkl',1),(2,'ROLE_EMPLOYEE','2018-06-04 14:28:38',NULL,'admin',NULL,'This is employee role.',1),(3,'ROLE_MANAGER','2018-06-18 13:16:32',NULL,'admin',NULL,'This is Manager role',1),(4,'ROLE_HOD','2018-06-18 13:16:54',NULL,'admin',NULL,'This is HOD',0);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_needs`
--

DROP TABLE IF EXISTS `training_needs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training_needs` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `LABEL_NAME` varchar(1000) NOT NULL,
  `NAME` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(500) NOT NULL,
  `APPRAISAL_YEAR_ID` int(11) NOT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(45) DEFAULT NULL,
  `MODIFIED_BY` varchar(45) DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_needs`
--

LOCK TABLES `training_needs` WRITE;
/*!40000 ALTER TABLE `training_needs` DISABLE KEYS */;
INSERT INTO `training_needs` VALUES (1,'Field_1','Field_1','Field_1',0,'2018-06-04 17:14:46','2018-06-04 17:14:49',NULL,'',0),(2,'Field_2','Field_2','Field_2',0,'2018-05-17 17:52:13','2018-05-17 17:52:21',NULL,'',0),(3,'Field_3','Field_3','Field_3',0,NULL,'2018-05-17 14:28:43',NULL,'',0),(4,'Field_4','Field_4','Field_4',0,NULL,'2018-05-11 14:08:04',NULL,NULL,0),(5,'Field_5','Field_5','Field_5',0,NULL,'2018-05-11 14:08:11',NULL,NULL,0);
/*!40000 ALTER TABLE `training_needs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `training_needs_details`
--

DROP TABLE IF EXISTS `training_needs_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `training_needs_details` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `EMP_CODE` varchar(45) NOT NULL,
  `APPRAISAL_YEAR_ID` int(11) NOT NULL,
  `TRAINING_TOPIC` varchar(500) DEFAULT NULL,
  `TRAINING_REASONS` varchar(1000) DEFAULT NULL,
  `MAN_HOURS` varchar(45) DEFAULT NULL,
  `REMARKS` varchar(500) DEFAULT NULL,
  `APPROVED_REJECT` varchar(45) DEFAULT NULL,
  `FIELD_1` varchar(1000) DEFAULT NULL,
  `FIELD_2` varchar(1000) DEFAULT NULL,
  `FIELD_3` varchar(1000) DEFAULT NULL,
  `FIELD_4` varchar(1000) DEFAULT NULL,
  `FIELD_5` varchar(1000) DEFAULT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(45) DEFAULT NULL,
  `MODIFIED_BY` varchar(45) DEFAULT NULL,
  `STATUS` tinyint(4) NOT NULL DEFAULT '1',
  `ISVALIDATEDBY_EMPLOYEE` tinyint(4) NOT NULL,
  `ISVALIDATEDBY_FIRSTLEVEL` tinyint(4) NOT NULL,
  `ISVALIDATEDBY_SECONDLEVEL` tinyint(4) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `training_needs_details`
--

LOCK TABLES `training_needs_details` WRITE;
/*!40000 ALTER TABLE `training_needs_details` DISABLE KEYS */;
INSERT INTO `training_needs_details` VALUES (1,'1712284',2,'cndcnadnscndncndam','.nc.dsncndndncnadndFSDHFHSDFLHDSLFHDLFHLDSHFLSDHLKFHSDKLHFKLDHSKLFHDKSFHSDHFLHSFHSDLHFKLDHFHDASKLFHDHFKLHDAFHSDKLHFKLSDHFKLHDASKLFHKLSDHFKLASDHFLHDSHFKLADHSFKLHSDKLHFKLSDHFKHASKLDFHSDKLHFKLASDHFKLHDAKLFHASKLDHFKLSDHFKLHSDKLFHSLDAHFLSDHFHASD','6','HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH','Approve',NULL,NULL,NULL,NULL,NULL,'2018-09-07 14:15:29','2018-09-08 09:27:09','1712284','1211002',1,1,1,1);
/*!40000 ALTER TABLE `training_needs_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `USERNAME` varchar(100) CHARACTER SET utf8 NOT NULL,
  `PASSWORD` varchar(100) CHARACTER SET utf8 NOT NULL,
  `ROLE_ID` int(11) NOT NULL,
  `CREATED_ON` datetime DEFAULT NULL,
  `MODIFIED_ON` datetime DEFAULT NULL,
  `CREATED_BY` varchar(45) DEFAULT NULL,
  `MODIFIED_BY` varchar(45) DEFAULT NULL,
  `ENABLED` tinyint(4) NOT NULL DEFAULT '1',
  `FIRST_CHECK` int(11) DEFAULT NULL,
  PRIMARY KEY (`ID`,`USERNAME`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.',1,NULL,NULL,NULL,NULL,1,NULL),(2,'EMP1','$2a$10$nIJGvDNfJZv8OjN1fGVEqeWoinfIxq2paMzkO0JZNG0.02v2vqWp6',3,'2018-08-07 10:09:37','2018-08-07 10:43:27','admin','EMP1',1,1),(3,'EMP2','$2a$10$zWHm.KGo7Mo1FaCVecyuluVL4Vm0bLapcKhdhaWWAQb/zSrdeOHPO',3,'2018-08-07 10:30:49','2018-08-07 10:47:00','admin','EMP2',1,1),(4,'EMP3','$2a$10$uqtEihD2efGi2K3yBUuSDejv4WleO107Y1Szbgt3jwb0zBON7/sKK',2,'2018-08-07 10:34:23','2018-08-08 09:28:09','admin','admin',1,1),(5,'EMP4','$2a$10$qgy8aqG2XEcnkHxK2WTMVOLjj6.UBVHXtkME90CwbIp/Rp2guEd16',2,'2018-08-07 10:34:23','2018-08-07 10:56:21','admin','EMP4',1,1),(6,'1211002','$2a$10$d4CG9D2wusdcCiC5qHKAreEwyW2KZi2t1TEu4x4i7DSBJ8rdyw2G6',3,'2018-09-05 11:22:03','2018-09-05 12:36:16','admin','1211002',1,1),(7,'1301009','$2a$10$fdGB3whnQk/FAA8E9oBQVePjFl2lDSFpEKWk9JYbg4VWlb30DogBu',3,'2018-09-05 11:45:53','2018-09-05 12:26:27','admin','1301009',1,1),(8,'1606145','$2a$10$j0s6EDH/Jwe3KzeXV3C/euIWwa36nGv3XmAAm6UiuC2dgoCRJUDDW',3,'2018-09-05 11:48:56','2018-09-05 12:51:02','admin','1606145',1,1),(9,'1808341','$2a$10$mYvvuB/K3YRsMJtKtr1WpuZniyU.d05nS1jwP7/sgd8Ha2pHccz6O',2,'2018-09-05 11:51:07',NULL,'admin',NULL,1,2),(10,'1712284','$2a$10$vvme5MFv4O5pfTiA9uO9wOxsZUBxoRLtCcTgD80UddWLtX7rGZ0SO',3,'2018-09-05 11:52:30','2018-09-05 12:05:46','admin','1712284',1,1),(11,'1234','$2a$10$JCYDWmV.jz1ztoE/7pOE5uFiJZLXTd7VvVPb/FCfZTVqOukM0pArO',2,'2018-09-06 11:22:15','2018-09-06 11:25:30','admin','admin',1,2),(12,'56789','$2a$10$IlXMkEeeGNovkLtYKDVpjOgbYWWUpuWFMfDrvTSw6xPXFJvCxmlx.',2,'2018-09-10 09:48:47','2018-09-10 09:54:30','admin','admin',1,2);
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

-- Dump completed on 2018-09-10 10:56:49
