-- MariaDB dump 10.19  Distrib 10.4.24-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: tientv34
-- ------------------------------------------------------
-- Server version	10.4.24-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bill` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_user` int(50) DEFAULT NULL,
  `mahd` varchar(100) NOT NULL,
  `date` varchar(100) NOT NULL,
  `status` int(11) NOT NULL,
  `note` varchar(250) DEFAULT NULL,
  `phone` int(20) NOT NULL,
  `address` varchar(250) NOT NULL,
  `user_id` int(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `id_user` (`id_user`),
  KEY `id_user_2` (`id_user`),
  CONSTRAINT `FK6qvanc7ss938l25j6i4ekqfit` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (70,2,'#1653406597853','2022-05-24 22:36:37',2,'Không giao hàng vào chủ nhật\r\n                                            ',987968597,'admin',NULL),(71,NULL,'#1653407051424','2022-05-24 22:44:11',3,'Không giao hàng giờ hàng chính\r\n                                            ',989878789,'Hà Nội',-155357536),(104,NULL,'#1654217862182','2022-06-03 07:57:42',2,'\r\n                                            ',989898989,'Ha Noi',655453222),(105,NULL,'#1654224813501','2022-06-03 09:53:33',3,'\r\n                                            ',111,'Hà Nội',662404541),(106,67,'#1654227230322','2022-06-03 10:33:50',1,'\r\n                                            ',989878780,'Hải Phòng',NULL),(107,NULL,'#1654228113242','2022-06-03 10:48:33',1,'\r\n                                            ',989898767,'Hà Nội',665704282),(108,NULL,'#1654229314763','2022-06-03 11:08:34',1,'\r\n                                            ',989878789,'Hà Nội',666905803),(109,69,'#1654229540182','2022-06-03 11:12:20',1,'\r\n                                            ',989878789,'Hà Nội',NULL);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `billdetails`
--

DROP TABLE IF EXISTS `billdetails`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `billdetails` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_hd` int(11) NOT NULL,
  `id_prd` int(11) NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_hd` (`id_hd`),
  KEY `id_prd` (`id_prd`),
  CONSTRAINT `FKjhdu041wa3my92xitixx97sph` FOREIGN KEY (`id_prd`) REFERENCES `products` (`id_prd`),
  CONSTRAINT `billdetails_ibfk_1` FOREIGN KEY (`id_hd`) REFERENCES `bill` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=119 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `billdetails`
--

LOCK TABLES `billdetails` WRITE;
/*!40000 ALTER TABLE `billdetails` DISABLE KEYS */;
INSERT INTO `billdetails` VALUES (65,70,32,3,798000),(66,70,37,1,750000),(67,70,10,3,2000000),(68,71,37,1,750000),(69,71,10,1,2000000),(109,104,32,3,798000),(110,104,59,4,1500000),(111,105,42,3,1090000),(112,105,59,3,1500000),(113,106,59,1,1500000),(114,107,59,1,1500000),(115,107,60,1,700000),(116,108,32,1,798000),(117,108,59,4,1500000),(118,109,59,1,1500000);
/*!40000 ALTER TABLE `billdetails` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (70);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id_prd` int(11) NOT NULL AUTO_INCREMENT,
  `name_prd` varchar(300) CHARACTER SET utf8 NOT NULL,
  `import_price` double NOT NULL,
  `price` double NOT NULL,
  `quantity` int(11) NOT NULL,
  `size` int(11) NOT NULL,
  `type` varchar(100) CHARACTER SET utf8 NOT NULL,
  `image` varchar(250) CHARACTER SET utf8 DEFAULT NULL,
  `details_prd` varchar(1000) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id_prd`)
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (10,'Nike Air 4 Retro Off-White Sail Like Auth',1500000,2000000,93,42,'1','Jordan2.jpg','Sản phẩm mới'),(32,'Nike Air 1 Low Travis Scott x Fragment Rep 1:1',3000000,798000,88,42,'1','Jordan5.jpg','Rep 1:1'),(33,'Giày Nike Air 1 Retro High Dior Like Auth',2500000,1190000,92,43,'1','Jordan6.jpg','New'),(34,'Nike Air 1 High Travis Scott Rep 1:1',3000000,1090000,100,42,'1','Jordan7.jpg','Rep 1:1'),(35,'Nike Air Force 1 LV8 GS ‘Double Swoosh’ Rep 1:1',3000000,1190000,99,43,'1','Jordan8.jpg','Rep 1:1'),(36,'Nike Air Force 1 LV8 GS ‘Double Swoosh’',3500000,1150000,100,40,'2','Nike1.jpg','Rep 1:1'),(37,'Nike Air Force 1 07 QS Valentine’s Day Love ',2200000,750000,100,44,'2','Nike2.jpg','New'),(38,'Nike Air Force 1 Low Paisley Swoosh Rep 1:1',3000000,868000,100,44,'2','Nike3.jpg','Rep 1:1'),(39,'Nike Air Force 1 Low Off White Volt Rep 1:1',3000000,1190000,100,44,'2','Nike4.jpg','Rep 1:1'),(40,'Adidas Yeezy 350 V2 Sesame',3000000,798000,100,43,'3','Adidas1.jpg','New'),(41,'Adidas Yeezy 350 V2 Cream',2500000,1190000,99,44,'3','Adidas2.jpg','New'),(42,'Adidas Yeezy 350 V2 Static Đen',3000000,1090000,100,44,'3','Adidas3.jpg','New'),(43,'Adidas Yeezy 350 V2 Static Reflective',3000000,1190000,100,42,'3','Adidas4.jpg','New'),(44,'Converse Chuck 1970s Đen Thấp Cổ',1200000,648000,100,42,'4','Converse2.jpg','Cổ Thấp'),(45,'Converse Chuck Taylor 1970s High Fear Of God',1200000,798000,97,44,'4','Converse3.jpeg','New'),(46,'Converse 1970s Xanh Navy Cao Cổ',1000000,648000,100,42,'4','Converse4.jpg','Cổ Thấp'),(57,'Nike Air Jordan 1 Retro High University Blue',1500000,780000,100,41,'4','Converse1.jpg','New'),(59,'Nike Air 4 Retro Off-White Sail Like Auth',2000000,1500000,69,41,'1','Jordan2.jpg','New'),(60,'Nike Air 1 Low Travis Scott x Fragment Rep 1:1',3000000,700000,99,41,'1','Jordan5.jpg','New'),(61,'Giày Nike Air 1 Retro High Dior Like Auth',2500000,1000000,100,42,'1','Jordan6.jpg','Rep 1:1'),(62,'Nike Air 1 High Travis Scott Rep 1:1',3000000,1000000,100,41,'1','Jordan3.jpg','New'),(64,'Converse 1970s Xanh Navy Cao Cổ',1000000,700000,50,41,'4','Converse4.jpg','New'),(65,'Nike Air Force 1 07 QS Valentine’s Day Love',2200000,749000,30,43,'2','Nike2.jpg','Rep 1 :1'),(66,'Nike Air Force 1 Low Paisley Swoosh Rep 1:1',3000000,799000,20,43,'2','Nike3.jpg','Rep 1:1'),(67,'Nike Air 4 Retro Off-White Sail Like Auth',1000000,500000,10,40,'Jordan','Adidas4.jpg','Sản phẩm mới');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) CHARACTER SET utf8 NOT NULL,
  `firstname` varchar(50) CHARACTER SET utf8 NOT NULL,
  `password` varchar(100) CHARACTER SET utf8 NOT NULL,
  `address` varchar(100) CHARACTER SET utf32 NOT NULL,
  `lastname` varchar(255) DEFAULT NULL,
  `roles` varchar(50) DEFAULT NULL,
  `reset_password_token` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'admin@gmail.com','admin','$2a$10$qkJcMM2fNGhN4sIrDnEJeO4BPoOwH.0lNEwEpvtoy9lN7rFq42L6q','admin','admin','admin','SzriXug1iqbJ6NwxdaCKNNEP0JAIfzVLYJjm9VprdNCGAptbPKPK2YxI4SuITdjaB0bF0A85REYsF8G5lgfMlweEKdWxVTyXZzDY'),(3,'aaa@gmail.com','tien','$2a$10$Q2ibAcbG3W45xkI7kdHKqe8C1BaG.SzdDgrkw3Na3MDcNZRGbgzme','Hai Duong','tran','disable',NULL),(9,'tientvph18954@gmail.com','tran','$2a$10$.QzJGyFWYabtjdPaZ7KHzu6QnhnBkmNxJafyDMAXo4GFoSGb8iXAi','Hai Duong','van','user','jA2d7T9WuYqncFUWnfkffuOThGoswojN92WoleTXhBQIBGffHoey7SmopgYz8vGXeWimkNJbNE6mQabWZwrW8V4XVgOU27Myl63c'),(10,'tien@gmail.com','tran','$2a$10$DhMwLmfyE7ZlnZ2kIOk4kO0Nh4DeNro3Wpu/PyexJK2ulVWJRNOWC','Hai Phong','admin','user',''),(11,'ttt@gmail.com','ttt','$2a$10$bKOkRcYzMxOB2Qfw2pFCOOIkTAm8f.JBg.IR4lrnmaX6EUVgPg7/C','Hai Duong','ttt','user',NULL),(12,'acb@gmail.com','acb','$2a$10$BuNIoDS5lOVnp87JCETdiu9BayMZD/G5BeaXT01iTfcqrDJK19VAO','Hà Nội','acb','user',''),(22,'123@gmail.com','111','$2a$10$1Blppt0otG2Ix6GwOl82Ee/BB5oQoGSPxsQOZRTgmnsUDP7u1aDFi','Hải Phòng','111','user',''),(23,'ddd@gmail.com','aaa','$2a$10$VAXS8CuliyRTS9nmQRWMX.H5.Mi7l51HT9.C7IIEeoSxqgGKaPpBC','aaaa','aaa','user',''),(66,'admin1998@gmail.com','Trần ','$2a$10$qW0Y1yIF0./FVKMlGMFAhe0a4IyvDDZPfXpyCHNMpBMkcw9LE1UBK','Hải Phòng','Tiến','user',NULL),(67,'trantien1998@gmail.com','Trần','$2a$10$wXWhGEQ9Gyp0/etPC8j42uZsRbTnMG1.6wNPFUU4PlrKKDLhzdlIS','Hải Phòng','Tiến','user',NULL),(68,'tientran1998@gmail.com','Trần','$2a$10$GX4XirML4vF3dt6fghNKhu8EDJeg3KX5d4xuYdy/yr8m4pwJfMjKe','Hà Nội','Tiến','user',NULL),(69,'tientran@gmail.com','Tiến','$2a$10$rJ3RgRf/O5efVt.IsijRKemSTJb0mID.GI3AOxcJEoR5C55gDETgG','Hà Nội','Tiến','disable',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-06 17:15:12
