-- MySQL dump 10.13  Distrib 8.0.36, for Win64 (x86_64)
--
-- Host: localhost    Database: crm
-- ------------------------------------------------------
-- Server version	8.0.37

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
-- Table structure for table `offertaaccettata`
--

DROP TABLE IF EXISTS `offertaaccettata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `offertaaccettata` (
  `CodiceOfferta` char(6) NOT NULL,
  `CodiceFiscaleCliente` char(16) NOT NULL,
  `DataDiAccettazione` date NOT NULL,
  `CodiceOperatore` char(4) NOT NULL,
  PRIMARY KEY (`CodiceOfferta`,`CodiceFiscaleCliente`),
  KEY `CF_OffertaAccettata_idx` (`CodiceFiscaleCliente`),
  KEY `DataDiAccettazione_idx` (`DataDiAccettazione`),
  CONSTRAINT `CF_OffertaAccettata` FOREIGN KEY (`CodiceFiscaleCliente`) REFERENCES `cliente` (`CodiceFiscale`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `CodiceOfferta_OffertaAccettata` FOREIGN KEY (`CodiceOfferta`) REFERENCES `offerta` (`CodiceOfferta`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `offertaaccettata`
--

LOCK TABLES `offertaaccettata` WRITE;
/*!40000 ALTER TABLE `offertaaccettata` DISABLE KEYS */;
/*!40000 ALTER TABLE `offertaaccettata` ENABLE KEYS */;
UNLOCK TABLES;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`localhost`*/ /*!50003 TRIGGER `offertaaccettata_BEFORE_INSERT` BEFORE INSERT ON `offertaaccettata` FOR EACH ROW BEGIN
	DECLARE scadenza DATE;
    SELECT DataDiScadenza INTO scadenza
    FROM offerta
    WHERE offerta.CodiceOfferta= NEW.CodiceOfferta;
    IF NEW.DataDiAccettazione > scadenza THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Errore: offerta già scaduta!';
    END IF;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-28 22:43:24
