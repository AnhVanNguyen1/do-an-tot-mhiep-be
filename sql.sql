-- MySQL dump 10.13  Distrib 8.0.13, for Win64 (x86_64)
--rrrdgsfhgdfhdfg
-- Host: 127.0.0.1    Database: tttn_database
-- ------------------------------------------------------
-- Server version	8.0.13

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cart`
--

DROP TABLE IF EXISTS `cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL,
  `status` smallint(6) NOT NULL DEFAULT '0',
  `firstName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lastName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mobile` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `city` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `province` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `country` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `idx_cart_user` (`userId`),
  CONSTRAINT `fk_cart_user` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart`
--

LOCK TABLES `cart` WRITE;
/*!40000 ALTER TABLE `cart` DISABLE KEYS */;
INSERT INTO `cart` VALUES (3,7,0,'Thuy','L??','03213245656','thuylengu@gmail.com',NULL,NULL,NULL,'2021-03-18 09:33:57',NULL,NULL),(4,8,0,'Nghiem','Trong','03213245656','trongnghiempham@gmail.com',NULL,NULL,NULL,'2021-03-26 03:01:31',NULL,NULL),(5,9,0,'Qu???nh','?????','03213245656','dothinhuquynh@gmail.com',NULL,NULL,NULL,'2021-03-26 11:38:47',NULL,NULL),(6,10,0,'L?? Th???','Th??y','0343430737','thuy.le2@ntq-solution.com.vn',NULL,NULL,NULL,'2021-04-04 04:53:33',NULL,NULL);
/*!40000 ALTER TABLE `cart` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cart_item`
--

DROP TABLE IF EXISTS `cart_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `cart_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `productId` bigint(20) NOT NULL,
  `cartId` bigint(20) NOT NULL,
  `price` float NOT NULL DEFAULT '0',
  `discount` float NOT NULL DEFAULT '0',
  `quantity` smallint(6) NOT NULL DEFAULT '0',
  `active` tinyint(1) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `quatity` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_cart_item_product` (`productId`),
  KEY `idx_cart_item_cart` (`cartId`),
  CONSTRAINT `fk_cart_item_cart` FOREIGN KEY (`cartId`) REFERENCES `cart` (`id`),
  CONSTRAINT `fk_cart_item_product` FOREIGN KEY (`productId`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cart_item`
--

LOCK TABLES `cart_item` WRITE;
/*!40000 ALTER TABLE `cart_item` DISABLE KEYS */;
INSERT INTO `cart_item` VALUES (4,3,3,0,0,0,0,'2021-03-19 08:42:14',NULL,NULL,3),(9,3,4,0,0,0,0,'2021-03-26 03:34:03',NULL,NULL,2);
/*!40000 ALTER TABLE `cart_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `category` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `meta_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'??o biker','Ki???u ??o kho??c da c?? kh??a k??o kim lo???i, ?????c tr??ng c???a d??n ch???y xe moto.','Ki???u ??o kho??c da c?? kh??a k??o kim lo???i, ?????c tr??ng c???a d??n ch???y xe moto.'),(2,'??o peterpan','??o peterpan','L?? ki???u ??o ???????c ?????t t??n theo nh??n v???t anh h??ng trong b??? truy???n Peter Pan c???a nh?? v??n J.M. Barrie, v???i ph???n c??? ???????c thi???t k??? h??nh b??o ho???c h??nh l??, may s??t v???i ph???n th??n ??o.'),(7,'??o sheer','??o sheer','L?? ki???u ??o ???????c may b???ng ch???t li???u v???i sheer (lo???i v???i ???????c d???t m???ng m???nh, trong su???t).'),(8,'??o cape','??o cape','L?? ??o cho??ng kh??ng tay, c???t may su??n theo b??? vai.'),(9,'??o keyhole','??o keyhole ','??o c?? thi???t k??? kho??t ng???c v???i nh???ng ???????ng c???t c??p hay chi ti???t trang tr?? ????n gi???n, nh??? nh??ng ??? ph???n c???.'),(10,'??o croptop','L?? ki???u ??o c?? ph???n v???t ch??? d??i qua ch??n ng???c.','L?? ki???u ??o c?? ph???n v???t ch??? d??i qua ch??n ng???c.'),(11,'??o kho??c peacoat','??o kho??c peacoat','Ki???u ??o peacoat th?????ng l??m b???ng ch???t li???u n??? ho???c da l???n v???i l???p l??t b??n trong b???ng cotton ho???c l??ng c???u. Phom ??o x??e nh??? v?? ph???n c??? ??o to b???n, kh??ng qu?? b???i b???m v?? c??ng kh??ng qu?? ??i???u ????.'),(12,'??o kho??c Kimono','??o kho??c Kimono','Ki???u ??o kho??c bi???n t???u ?????c s???c t??? ??o cho??ng Kimono c???a ng?????i Nh???t.'),(13,'Trang s???c b???c','B???c nguy??n ch???t c??ng r???t d???o ','B???c nguy??n ch???t c??ng r???t d???o v'),(14,'Trang s???c titan','   Titan l?? kim lo???i c???ng v?? r???t b???n trong c??c ch???t li???u d??ng l??m trang s???c','   Titan l?? kim lo???i c???ng v?? r???t b???n trong c??c ch???t li???u d??ng l??m trang s???c'),(15,'Trang s???c b???ch kim','B???ch kim l?? kim lo???i r???t c???ng v?? c?? m??u s??ng tr???ng,','B???ch kim l?? kim lo???i r???t c???ng v?? c?? m??u s??ng tr???ng,');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hibernate_sequence`
--

DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hibernate_sequence`
--

LOCK TABLES `hibernate_sequence` WRITE;
/*!40000 ALTER TABLE `hibernate_sequence` DISABLE KEYS */;
INSERT INTO `hibernate_sequence` VALUES (18);
/*!40000 ALTER TABLE `hibernate_sequence` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  `sub_total` float NOT NULL DEFAULT '0',
  `shipping` float NOT NULL DEFAULT '0',
  `total` float NOT NULL DEFAULT '0',
  `discount` float NOT NULL DEFAULT '0',
  `grand_total` float NOT NULL DEFAULT '0',
  `firstName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `lastName` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mobile` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `address` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `idx_order_user` (`userId`),
  CONSTRAINT `fk_order_user` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order`
--

LOCK TABLES `order` WRITE;
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
INSERT INTO `order` VALUES (15,7,0,1213,17.82,0,0,1213,'Nghiem','Trong','03213245656','thuylengu@gmail.com','s??? 6 ng?? 337-??Ph?????ng Na Lay-??Th??? X?? M?????ng Lay-??T???nh ??i???n Bi??n','2021-03-26 05:57:47',NULL,NULL),(16,8,4,-271.48,17.82,0,0,-253.66,'Nghiem','Trong','03213245656','thuylengu@gmail.com','s??? 6 ng?? 337-??Ph?????ng Hi???p Th??nh-??Qu???n 12-??Th??nh ph??? C???n Th??','2021-03-26 11:08:13',NULL,NULL),(17,8,1,-271.48,17.82,0,0,-253.66,'Nghiem','Trong','03213245656','thuylengu@gmail.com','-??Ch???n Ph?????ng/X??...-??Ch???n Qu???n/Huy???n...-??Ch???n T???nh/Th??nh Ph???...','2021-03-26 11:08:29',NULL,NULL),(18,8,2,-88000,25.122,0,0,-87974.9,'Qu???nh','?????','03213245656','dothinhuquynh@gmail.com','s??? 6 ng?? 337-??X?? S??n X??? H???-??Huy???n Ho??ng Su Ph??-??T???nh H?? Giang','2021-03-26 11:42:31',NULL,NULL),(19,8,3,-88000,17.82,0,0,-87982.2,'Qu???nh','?????','03213245656','dothinhuquynh@gmail.com','-??Ch???n Ph?????ng/X??...-??Ch???n Qu???n/Huy???n...-??Ch???n T???nh/Th??nh Ph???...','2021-03-26 11:42:38',NULL,NULL);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `order_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `productId` bigint(20) NOT NULL,
  `orderId` bigint(20) NOT NULL,
  `price` float NOT NULL DEFAULT '0',
  `discount` float NOT NULL DEFAULT '0',
  `quantity` smallint(6) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  PRIMARY KEY (`id`),
  KEY `idx_order_item_product` (`productId`),
  KEY `idx_order_item_order` (`orderId`),
  CONSTRAINT `fk_order_item_order` FOREIGN KEY (`orderId`) REFERENCES `order` (`id`),
  CONSTRAINT `fk_order_item_product` FOREIGN KEY (`productId`) REFERENCES `product` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (5,3,15,1234,12,3,'2021-03-26 05:57:47',NULL,NULL),(6,3,16,1234,12,2,'2021-03-26 11:08:13',NULL,NULL),(7,3,17,1234,12,2,'2021-03-26 11:08:29',NULL,NULL),(8,3,18,400000,12,2,'2021-03-26 11:42:31',NULL,NULL),(9,3,19,400000,12,2,'2021-03-26 11:42:38',NULL,NULL);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `photo_product`
--

DROP TABLE IF EXISTS `photo_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `photo_product` (
  `id` bigint(20) NOT NULL,
  `productId` bigint(20) DEFAULT NULL,
  `file_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `file_name_blank` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `upload_dir` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `PRODUCT_CATEGORY_FK` (`productId`),
  CONSTRAINT `PRODUCT_CATEGORY_FK` FOREIGN KEY (`productId`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `photo_product`
--

LOCK TABLES `photo_product` WRITE;
/*!40000 ALTER TABLE `photo_product` DISABLE KEYS */;
INSERT INTO `photo_product` VALUES (4,1,'8f5ccf40-9f09-49cc-bf6d-1931170ae333.jpg','default.jpg',NULL,NULL,NULL),(8,2,'1f1c552f-d4fc-4449-b3c9-d114e03cf587.jpg','default.jpg',NULL,NULL,NULL),(9,3,'63604cda-ef0a-4d07-9fdc-62a07bfc8323.jpg','default.jpg',NULL,NULL,NULL),(12,5,'6f9dd30a-d7f7-40f0-a52a-96355a373b3f.jpg','default.jpg',NULL,NULL,NULL),(13,6,'92705059-8338-4b16-bd00-3b893e22c2cc.jpg','default.jpg',NULL,NULL,NULL),(14,7,'c053e13d-b0db-4366-b14f-4f710ad7228e.jpg','default.jpg',NULL,NULL,NULL),(15,8,'ee4ea017-956c-4c3d-bb6c-3080d396223d.jpg','default.jpg',NULL,NULL,NULL),(16,9,'e6ee7cd1-9071-44ab-b2d3-5dcb37ca2c0b.jpg','default.jpg',NULL,NULL,NULL),(17,10,'9084ea4e-5432-405f-a224-e31d15086681.jpg','default.jpg',NULL,NULL,NULL);
/*!40000 ALTER TABLE `photo_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product`
--

DROP TABLE IF EXISTS `product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `meta_title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `summary` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `type` smallint(6) DEFAULT '0',
  `price` float NOT NULL DEFAULT '0',
  `discount` float NOT NULL DEFAULT '0',
  `quantity` smallint(6) NOT NULL DEFAULT '0',
  `created_at` datetime NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `published_at` datetime DEFAULT NULL,
  `starts_at` datetime DEFAULT NULL,
  `end_at` datetime DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `category_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_category_product_idx` (`category_id`),
  CONSTRAINT `fk_category_product` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product`
--

LOCK TABLES `product` WRITE;
/*!40000 ALTER TABLE `product` DISABLE KEYS */;
INSERT INTO `product` VALUES (1,'??o biker','Ki???u ??o kho??c da c?? kh??a k??o kim lo???i, ?????c tr??ng c???a d??n ch???y xe moto.','Ki???u ??o kho??c da c?? kh??a k??o kim lo???i, ?????c tr??ng c???a d??n ch???y xe moto.',12,300000,20,20,'2021-03-15 04:33:40','2021-03-26 11:20:33',NULL,NULL,NULL,'dfhfh',1),(2,'??o peterpan','L?? ki???u ??o ???????c ?????t t??n theo nh??n v???t anh h??ng','L?? ki???u ??o ???????c ?????t t??n theo nh??n v???t anh h??ng trong b??? truy???n Peter Pan c???a nh?? v??n J.M. Barrie, v???i ph???n c??? ???????c thi???t k??? h??nh b??o ho???c h??nh l??, may s??t v???i ph???n th??n ??o.',11,3000000,12,13,'2021-03-15 04:39:58','2021-03-26 11:21:40',NULL,NULL,NULL,'dfhfh',1),(3,'??o sheer','L?? ki???u ??o ???????c may b???ng ch???t li???u v???i sheer (lo???i v???i ???????c d???t m???ng m???nh, trong su???t).','s???n ph???m t???t',1313,400000,12,12,'2021-03-15 04:41:39','2021-03-26 11:22:15',NULL,NULL,NULL,'dfhfh',2),(5,'??o cape','L?? ??o cho??ng kh??ng tay, c???t may su??n theo b??? vai.,L?? ??o cho??ng kh??ng tay, c???t may su??n theo b??? vai.',NULL,12,1500000,10,20,'2021-03-26 11:23:00',NULL,NULL,NULL,NULL,'L?? ??o cho??ng kh??ng tay, c???t may su??n theo b??? vai.',1),(6,'??o keyhole','??o c?? thi???t k??? kho??t ng???c v???i nh???ng ???????ng c???t c??p,cho n???',NULL,12,500000,15,30,'2021-03-26 11:25:48',NULL,NULL,NULL,NULL,'kh??ng content',1),(7,'??o croptop','L?? ki???u ??o c?? ph???n v???t ch??? d??i qua ch??n ng???c,L?? ki???u ??o c?? ph???n v???t ch??? d??i qua ch??n ng???c',NULL,12,250000,19,23,'2021-03-26 11:26:27',NULL,NULL,NULL,NULL,'Shop chuy??n v??? qu???n ??o cho tu???i teen n??n ??a s??? qu???n ??o mang phong c??ch cute d??? th????ng',1),(8,'??o blouse','ch???t li???u t???t, m???m d??? ph???i ????? c?? 3 size s,m,l,??o kho??c d??nh cho c??? nam l???n n???. ',NULL,12,450000,30,70,'2021-03-26 11:27:34',NULL,NULL,NULL,NULL,'??o kho??c d??nh cho c??? nam l???n n???.',1),(9,'??o kho??c peacoat','ch???t li???u t???t, m???m d??? ph???i ????? c?? 3 size s,m,l,Ki???u ??o peacoat th?????ng l??m b???ng ch???t li???u n??? ',NULL,12,600000,23,56,'2021-03-26 11:29:37',NULL,NULL,NULL,NULL,'Tho???i m??i, v???i m???m',1),(10,'diamond ring','B???ch kim l?? kim lo???i r???t c???ng v?? c?? m??u s??ng tr???ng, v?? th??? t??? b???n th??n n?? ???? c?? th??? d??ng ????? ch??? t??c trang s???c m?? kh??ng c???n ph???i pha tr???n b???t k?? kim lo???i n??o kh??c. V???i nh???ng ai y??u th??ch m??u tr???ng tinh khi???t th?? trang s???c b???ch kim l?? s??? l???a ch???n ho???n h???o,,b???ch kim lu??n l?? kim lo???i ???????c l???a ch???n h??ng ?????u ????? ch??? t??c trang s???c c?? kim c????ng.',NULL,12,120000000,12,60,'2021-03-26 11:37:10',NULL,NULL,NULL,NULL,'kh??ng content',13);
/*!40000 ALTER TABLE `product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_meta`
--

DROP TABLE IF EXISTS `product_meta`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product_meta` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `productId` bigint(20) NOT NULL,
  `key` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_product_meta` (`productId`,`key`),
  KEY `idx_meta_product` (`productId`),
  CONSTRAINT `fk_meta_product` FOREIGN KEY (`productId`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_meta`
--

LOCK TABLES `product_meta` WRITE;
/*!40000 ALTER TABLE `product_meta` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_meta` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_review`
--

DROP TABLE IF EXISTS `product_review`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product_review` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `productId` bigint(20) NOT NULL,
  `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `rating` smallint(6) NOT NULL DEFAULT '0',
  `published` tinyint(1) NOT NULL DEFAULT '0',
  `createdAt` datetime NOT NULL,
  `publishedAt` datetime DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_review_product` (`productId`),
  CONSTRAINT `fk_review_product` FOREIGN KEY (`productId`) REFERENCES `product` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_review`
--

LOCK TABLES `product_review` WRITE;
/*!40000 ALTER TABLE `product_review` DISABLE KEYS */;
/*!40000 ALTER TABLE `product_review` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `transaction` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userId` bigint(20) NOT NULL,
  `orderId` bigint(20) NOT NULL,
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` smallint(6) NOT NULL DEFAULT '0',
  `mode` smallint(6) NOT NULL DEFAULT '0',
  `status` smallint(6) NOT NULL DEFAULT '0',
  `createdAt` datetime NOT NULL,
  `updatedAt` datetime DEFAULT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idx_transaction_user` (`userId`),
  KEY `idx_transaction_order` (`orderId`),
  CONSTRAINT `fk_transaction_order` FOREIGN KEY (`orderId`) REFERENCES `order` (`id`),
  CONSTRAINT `fk_transaction_user` FOREIGN KEY (`userId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `mobile` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `user_role` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '0',
  `registered_at` datetime NOT NULL,
  `last_login` datetime DEFAULT NULL,
  `intro` tinytext CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `profile` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci,
  `reset_password_token` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (7,'Thuy','L??','03213245656','thuylengu@gmail.com','$2a$10$M7L9XnCER5lL98Nl7WKjuOsfCIBPqtwBNTYhahlChfqChkEfCMP02','ROLE_EMPLOYEE','2021-03-18 09:33:57',NULL,NULL,NULL,'HbVEoyndQcOKV9EeJK29HHOsXaWeo1'),(8,'Nghiem','Trong','03213245656','trongnghiempham@gmail.com','$2a$10$hcvscp/WhAMT2iYtXcKYKOKCi8xtwC4Fsk5RMvasNeKY4yIEgl1me','ROLE_MANAGER','2021-03-26 03:01:31',NULL,NULL,NULL,NULL),(9,'Qu???nh','?????','03213245656','dothinhuquynh@gmail.com','$2a$10$/EMny9j2SV91Vnk0hb1uBeZz.ulDWt.0Jo97LkA5rhAhdWCaLaVVe','ROLE_EMPLOYEE','2021-03-26 11:38:47',NULL,NULL,NULL,NULL),(10,'L?? Th???','Th??y','0343430737','thuy.le2@ntq-solution.com.vn','$2a$10$JqiPVKy6Vk9qRXW3pIyfruaKSiADHW67CgfLGPecEGGyuUb9ayLoC','ROLE_EMPLOYEE','2021-04-04 04:53:32',NULL,NULL,NULL,'qEiUVSYGHi4NPQSdFqdmv5kyV5cA5C');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'tttn_database'
--

--
-- Dumping routines for database 'tttn_database'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-04-06  7:28:27


-- Dump completed on 2021-04-06  7:28:27

--thuy1