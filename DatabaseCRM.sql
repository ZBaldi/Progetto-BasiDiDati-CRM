-- --------------------------------------------------------
-- Host:                         localhost
-- Versione server:              11.4.2-MariaDB - mariadb.org binary distribution
-- S.O. server:                  Win64
-- HeidiSQL Versione:            12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dump della struttura del database crm
CREATE DATABASE IF NOT EXISTS `crm` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;
USE `crm`;

-- Dump della struttura di tabella crm.appuntamento
CREATE TABLE IF NOT EXISTS `appuntamento` (
  `CodiceFiscaleCliente` char(16) NOT NULL,
  `CodiceOfferta` char(6) NOT NULL,
  `Sede` varchar(30) NOT NULL,
  `Data` date NOT NULL,
  `Orario` time NOT NULL,
  PRIMARY KEY (`CodiceFiscaleCliente`,`CodiceOfferta`),
  KEY `CodiceOfferta_appuntamento_idx` (`CodiceOfferta`),
  KEY `DataAppuntamento_idx` (`Data`),
  CONSTRAINT `CF_appuntamento` FOREIGN KEY (`CodiceFiscaleCliente`) REFERENCES `nota` (`CodiceFiscaleCliente`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `CodiceOfferta_appuntamento` FOREIGN KEY (`CodiceOfferta`) REFERENCES `nota` (`CodiceOfferta`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dump dei dati della tabella crm.appuntamento: ~0 rows (circa)

-- Dump della struttura di tabella crm.cliente
CREATE TABLE IF NOT EXISTS `cliente` (
  `CodiceFiscale` char(16) NOT NULL,
  `Nome` varchar(30) NOT NULL,
  `Cognome` varchar(30) NOT NULL,
  `Indirizzo` varchar(50) NOT NULL,
  `Città` varchar(30) NOT NULL,
  `Provincia` char(2) NOT NULL,
  `Paese` varchar(30) NOT NULL,
  `DataDiNascita` date NOT NULL,
  `DataDiRegistrazione` date NOT NULL,
  PRIMARY KEY (`CodiceFiscale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dump dei dati della tabella crm.cliente: ~1 rows (circa)
INSERT INTO `cliente` (`CodiceFiscale`, `Nome`, `Cognome`, `Indirizzo`, `Città`, `Provincia`, `Paese`, `DataDiNascita`, `DataDiRegistrazione`) VALUES
	('PSQKNM33A01B565J', 'KEVIN MARIO', 'PASQUETTI', 'VIA BOH 1 80030', 'Camposano', 'NA', 'Italia', '1933-01-01', '2024-06-29');

-- Dump della struttura di vista crm.clienti_in_ordine_di_contatto
-- Creazione di una tabella temporanea per risolvere gli errori di dipendenza della vista
CREATE TABLE `clienti_in_ordine_di_contatto` (
	`CodiceFiscale` CHAR(16) NOT NULL COLLATE 'utf8mb4_general_ci',
	`Nome` VARCHAR(30) NOT NULL COLLATE 'utf8mb4_general_ci',
	`Cognome` VARCHAR(30) NOT NULL COLLATE 'utf8mb4_general_ci',
	`Indirizzo` VARCHAR(50) NOT NULL COLLATE 'utf8mb4_general_ci',
	`Città` VARCHAR(30) NOT NULL COLLATE 'utf8mb4_general_ci',
	`Provincia` CHAR(2) NOT NULL COLLATE 'utf8mb4_general_ci',
	`Paese` VARCHAR(30) NOT NULL COLLATE 'utf8mb4_general_ci',
	`DataDiNascita` DATE NOT NULL,
	`DataDiRegistrazione` DATE NOT NULL
) ENGINE=MyISAM;

-- Dump della struttura di procedura crm.elimina_offerte
DELIMITER //
CREATE PROCEDURE `elimina_offerte`(
	IN `codiceOfferta` CHAR(6)
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		ROLLBACK;
		RESIGNAL;
	END;
   SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
   START TRANSACTION;
   IF (SELECT COUNT(*) FROM `offerte_scadute_non_accettate` 
         	WHERE `CodiceOfferta`= codiceOfferta)>0 THEN
			DELETE FROM `offerta` WHERE `CodiceOfferta`= codiceOfferta;
   ELSE 
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Offerta non scaduta o 
         ancora attiva dai clienti.';
   END IF;
   COMMIT;
END//
DELIMITER ;

-- Dump della struttura di tabella crm.email
CREATE TABLE IF NOT EXISTS `email` (
  `NomeEmail` varchar(255) NOT NULL,
  `CodiceFiscaleCliente` char(16) NOT NULL,
  PRIMARY KEY (`NomeEmail`),
  KEY `CF_Email_idx` (`CodiceFiscaleCliente`),
  CONSTRAINT `CF_Email` FOREIGN KEY (`CodiceFiscaleCliente`) REFERENCES `cliente` (`CodiceFiscale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dump dei dati della tabella crm.email: ~1 rows (circa)
INSERT INTO `email` (`NomeEmail`, `CodiceFiscaleCliente`) VALUES
	('kevin@gmail.com', 'PSQKNM33A01B565J');

-- Dump della struttura di funzione crm.input_variadico
DELIMITER //
CREATE FUNCTION `input_variadico`(`valori` TEXT,
	`posizione` INT
) RETURNS varchar(255) CHARSET utf8mb4 COLLATE utf8mb4_general_ci
    DETERMINISTIC
BEGIN
	DECLARE campo VARCHAR(255);
	DECLARE delimitatore_max INT;
	SET delimitatore_max = LENGTH(valori) - LENGTH(REPLACE(valori, ';', '')) + 1;
	IF posizione <= delimitatore_max THEN 
						SET campo = REPLACE(SUBSTRING(SUBSTRING_INDEX(valori,';', posizione),
                                   LENGTH(SUBSTRING_INDEX(valori,';', posizione-1)) + 1),
                         ';','');
						SET campo= REPLACE(campo, ' ', '');
	ELSE 
						SET campo= NULL;
	END IF;
   RETURN campo;

END//
DELIMITER ;

-- Dump della struttura di procedura crm.inserisci_offerta
DELIMITER //
CREATE PROCEDURE `inserisci_offerta`(
	IN `codiceOfferta` CHAR(6),
	IN `nomeOfferta` VARCHAR(30),
	IN `descrizione` TEXT,
	IN `dataScadenza` DATE
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		ROLLBACK;
		RESIGNAL;
	END;

   SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
   START TRANSACTION;
	INSERT INTO `offerta`(CodiceOfferta,NomeOfferta,Descrizione,DataDiScadenza) 
   VALUES(codiceOfferta,nomeOfferta,descrizione,dataScadenza);
   COMMIT;
END//
DELIMITER ;

-- Dump della struttura di procedura crm.inserisci_offerta_accettata
DELIMITER //
CREATE PROCEDURE `inserisci_offerta_accettata`(
	IN `codiceOfferta` CHAR(6),
	IN `cf` CHAR(16),
	IN `dataAccettazione` DATE,
	IN `codiceOperatore` CHAR(4)
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		ROLLBACK;
		RESIGNAL;
	END;

   SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
   START TRANSACTION;
	INSERT INTO `offertaaccettata`(CodiceOfferta,CodiceFiscaleCliente,
      			DataDiAccettazione,CodiceOperatore) 
  	VALUES(codiceOfferta,cf,dataAccettazione,codiceOperatore);
   COMMIT;
END//
DELIMITER ;

-- Dump della struttura di procedura crm.lista_note
DELIMITER //
CREATE PROCEDURE `lista_note`(
	IN `cf` CHAR(16)
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		ROLLBACK;
		RESIGNAL;
	END;
    
    	SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
   	SET TRANSACTION READ ONLY;
    	START TRANSACTION;
    	SELECT *
    	FROM `nota` LEFT JOIN `appuntamento` 
				ON (`nota`.CodiceFiscaleCliente,`nota`.CodiceOfferta)=
       		(`appuntamento`.CodiceFiscaleCliente,`appuntamento`.CodiceOfferta)
    	WHERE `nota`.CodiceFiscaleCliente=cf;
   	COMMIT;
END//
DELIMITER ;

-- Dump della struttura di procedura crm.login
DELIMITER //
CREATE PROCEDURE `login`(
	IN `var_username` VARCHAR(30),
	IN `var_pass` VARCHAR(30),
	OUT `var_role` INT
)
BEGIN
	DECLARE var_user_role ENUM('operatore', 'segreteria');
	SELECT `Ruolo` 
	FROM `utenti` 
	WHERE `Username` = var_username AND `Password` = MD5(var_pass) 
	INTO var_user_role;
	IF var_user_role = 'operatore' THEN SET var_role = 1;
	ELSEIF var_user_role = 'segreteria' THEN SET var_role = 2;
	ELSE SET var_role = 3;
	END IF;
END//
DELIMITER ;

-- Dump della struttura di procedura crm.mostra_clienti
DELIMITER //
CREATE PROCEDURE `mostra_clienti`()
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		ROLLBACK;
		RESIGNAL;
	END;
   	
	SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
   SET TRANSACTION READ ONLY;
   START TRANSACTION;
   SELECT *
   FROM `clienti_in_ordine_di_contatto`;
   COMMIT;
END//
DELIMITER ;

-- Dump della struttura di procedura crm.mostra_email
DELIMITER //
CREATE PROCEDURE `mostra_email`(
	IN `cf` CHAR(16)
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		ROLLBACK;
		RESIGNAL;
	END;
    
   SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
   SET TRANSACTION READ ONLY;
   START TRANSACTION;
   SELECT NomeEmail
   FROM `email`
   WHERE `email`.CodiceFiscaleCliente=cf;
   COMMIT;
END//
DELIMITER ;

-- Dump della struttura di procedura crm.mostra_offerte
DELIMITER //
CREATE PROCEDURE `mostra_offerte`()
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		ROLLBACK;
		RESIGNAL;
	END;
    
   SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
   SET TRANSACTION READ ONLY;
   START TRANSACTION;
   SELECT *
   FROM `offerte_valide`;
   COMMIT;
END//
DELIMITER ;

-- Dump della struttura di procedura crm.mostra_offerte_scadute
DELIMITER //
CREATE PROCEDURE `mostra_offerte_scadute`()
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		ROLLBACK;
		RESIGNAL;
	END;
   SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
   SET TRANSACTION READ ONLY;
   START TRANSACTION;
   SELECT *
   FROM `offerte_scadute_non_accettate`;
   COMMIT;
END//
DELIMITER ;

-- Dump della struttura di procedura crm.mostra_telefoni
DELIMITER //
CREATE PROCEDURE `mostra_telefoni`(
	IN `cf` CHAR(16)
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		ROLLBACK;
		RESIGNAL;
	END;
    
   SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
   SET TRANSACTION READ ONLY;
   START TRANSACTION;
   SELECT NumeroDiTelefono
   FROM `telefono`
   WHERE `telefono`.CodiceFiscaleCliente=cf;
   COMMIT;
END//
DELIMITER ;

-- Dump della struttura di tabella crm.nota
CREATE TABLE IF NOT EXISTS `nota` (
  `CodiceFiscaleCliente` char(16) NOT NULL,
  `CodiceOfferta` char(6) NOT NULL,
  `Esito` text NOT NULL,
  `CodiceOperatore` char(4) NOT NULL,
  `DataDiModifica` date NOT NULL,
  PRIMARY KEY (`CodiceFiscaleCliente`,`CodiceOfferta`),
  KEY `CodOfferta_nota_idx` (`CodiceOfferta`),
  KEY `DataDiModifica_nota_idx` (`DataDiModifica`),
  CONSTRAINT `CF_nota` FOREIGN KEY (`CodiceFiscaleCliente`) REFERENCES `cliente` (`CodiceFiscale`) ON UPDATE CASCADE,
  CONSTRAINT `CodOfferta_nota` FOREIGN KEY (`CodiceOfferta`) REFERENCES `offerta` (`CodiceOfferta`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dump dei dati della tabella crm.nota: ~0 rows (circa)

-- Dump della struttura di evento crm.Note_AppuntamentiCleanup
DELIMITER //
CREATE EVENT `Note_AppuntamentiCleanup` ON SCHEDULE EVERY 1 DAY STARTS '2024-06-29 00:00:00' ON COMPLETION PRESERVE ENABLE DO DELETE FROM `nota`
		WHERE (`CodiceFiscaleCliente`,`CodiceOfferta`) IN
 				( SELECT `CodiceFiscaleCliente`,`CodiceOfferta`
   				FROM `appuntamento`
   	   	   WHERE `Data` <    
             	          DATE_SUB(CURRENT_DATE,  INTERVAL 1 YEAR))//
DELIMITER ;

-- Dump della struttura di evento crm.Note_SoloCleanup
DELIMITER //
CREATE EVENT `Note_SoloCleanup` ON SCHEDULE EVERY 1 DAY STARTS '2024-06-29 00:00:00' ON COMPLETION PRESERVE ENABLE DO DELETE FROM `nota`
		WHERE (`CodiceFiscaleCliente`,`CodiceOfferta`) NOT IN
 				( SELECT `CodiceFiscaleCliente`,`CodiceOfferta`
   				FROM `appuntamento`)
                AND `DataDiModifica` <   DATE_SUB(CURRENT_DATE,  INTERVAL 
                                                             1 YEAR)//
DELIMITER ;

-- Dump della struttura di tabella crm.offerta
CREATE TABLE IF NOT EXISTS `offerta` (
  `CodiceOfferta` char(6) NOT NULL,
  `NomeOfferta` varchar(30) NOT NULL,
  `Descrizione` text NOT NULL,
  `DataDiScadenza` date NOT NULL,
  PRIMARY KEY (`CodiceOfferta`),
  KEY `DataDiScadenza_idx` (`DataDiScadenza`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dump dei dati della tabella crm.offerta: ~0 rows (circa)

-- Dump della struttura di tabella crm.offertaaccettata
CREATE TABLE IF NOT EXISTS `offertaaccettata` (
  `CodiceOfferta` char(6) NOT NULL,
  `CodiceFiscaleCliente` char(16) NOT NULL,
  `DataDiAccettazione` date NOT NULL,
  `CodiceOperatore` char(4) NOT NULL,
  PRIMARY KEY (`CodiceOfferta`,`CodiceFiscaleCliente`),
  KEY `CF_OffertaAccettata_idx` (`CodiceFiscaleCliente`),
  KEY `DataDiAccettazione_idx` (`DataDiAccettazione`),
  CONSTRAINT `CF_OffertaAccettata` FOREIGN KEY (`CodiceFiscaleCliente`) REFERENCES `cliente` (`CodiceFiscale`) ON UPDATE CASCADE,
  CONSTRAINT `CodiceOfferta_OffertaAccettata` FOREIGN KEY (`CodiceOfferta`) REFERENCES `offerta` (`CodiceOfferta`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dump dei dati della tabella crm.offertaaccettata: ~0 rows (circa)

-- Dump della struttura di vista crm.offerte_scadute_non_accettate
-- Creazione di una tabella temporanea per risolvere gli errori di dipendenza della vista
CREATE TABLE `offerte_scadute_non_accettate` (
	`CodiceOfferta` CHAR(6) NOT NULL COLLATE 'utf8mb4_general_ci',
	`NomeOfferta` VARCHAR(30) NOT NULL COLLATE 'utf8mb4_general_ci',
	`Descrizione` TEXT NOT NULL COLLATE 'utf8mb4_general_ci',
	`DataDiScadenza` DATE NOT NULL
) ENGINE=MyISAM;

-- Dump della struttura di vista crm.offerte_valide
-- Creazione di una tabella temporanea per risolvere gli errori di dipendenza della vista
CREATE TABLE `offerte_valide` (
	`CodiceOfferta` CHAR(6) NOT NULL COLLATE 'utf8mb4_general_ci',
	`NomeOfferta` VARCHAR(30) NOT NULL COLLATE 'utf8mb4_general_ci',
	`Descrizione` TEXT NOT NULL COLLATE 'utf8mb4_general_ci',
	`DataDiScadenza` DATE NOT NULL
) ENGINE=MyISAM;

-- Dump della struttura di procedura crm.registra_cliente
DELIMITER //
CREATE PROCEDURE `registra_cliente`(
	IN `cf` CHAR(16),
	IN `nome` VARCHAR(30),
	IN `cognome` VARCHAR(30),
	IN `indirizzo` VARCHAR(50),
	IN `città` VARCHAR(30),
	IN `provincia` CHAR(2),
	IN `paese` VARCHAR(30),
	IN `dataNascita` DATE,
	IN `dataRegistrazione` DATE,
	IN `telefoni` TEXT,
	IN `email` TEXT
)
BEGIN
	DECLARE posizione INT DEFAULT 1;
  	DECLARE campo VARCHAR(255);
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		ROLLBACK;
		RESIGNAL;
	END;
    
   SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
   START TRANSACTION;
  	INSERT INTO `cliente`(CodiceFiscale,Nome,Cognome,Indirizzo,Città,Provincia,Paese,
      DataDiNascita,DataDiRegistrazione) 
	VALUES(cf,nome,cognome,indirizzo,città,provincia,paese,dataNascita,dataRegistrazione);
   WHILE input_variadico(telefoni, posizione) IS NOT NULL DO
       		SET campo = input_variadico(telefoni, posizione);
        	 	INSERT INTO `telefono`(NumeroDiTelefono,CodiceFiscaleCliente) 
				VALUES(campo,cf);
   			SET posizione = posizione + 1;
   END WHILE;
   SET posizione=1;
   WHILE input_variadico(email, posizione) IS NOT NULL DO
        		SET campo = input_variadico(email, posizione);
        		INSERT INTO `email`(nomeEmail,CodiceFiscaleCliente) VALUES(campo,cf);
        		SET posizione = posizione + 1;
   END WHILE;
   COMMIT;
END//
DELIMITER ;

-- Dump della struttura di procedura crm.report_clienti
DELIMITER //
CREATE PROCEDURE `report_clienti`(
	IN `dataInizio` DATE,
	IN `dataFine` DATE
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		ROLLBACK;
		RESIGNAL;
	END;
    
	SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
  	SET TRANSACTION READ ONLY;
  	START TRANSACTION;

   SELECT CodiceFiscale,Nome,Cognome, COUNT(DataDiAccettazione) AS Contattato
   FROM `nota` JOIN `cliente` ON `nota`.`CodiceFiscaleCliente` = `cliente`.`CodiceFiscale` LEFT JOIN `offertaaccettata` ON   (`nota`.`CodiceFiscaleCliente`,`nota`.`CodiceOfferta`) = (`offertaaccettata`.`CodiceFiscaleCliente`,`offertaaccettata`.`CodiceOfferta`)
	WHERE (`nota`.`DataDiModifica`>dataInizio AND `nota`.`DataDiModifica`<dataFine) AND (`offertaaccettata`.`DataDiAccettazione`>dataInizio AND `offertaaccettata`.`DataDiAccettazione`<dataFine)
   GROUP BY `CodiceFiscale`,`Nome`,`Cognome`;
   COMMIT;
END//
DELIMITER ;

-- Dump della struttura di procedura crm.scrivi_nota
DELIMITER //
CREATE PROCEDURE `scrivi_nota`(
	IN `cf` CHAR(16),
	IN `codiceOfferta` CHAR(6),
	IN `esito` TEXT,
	IN `codiceOperatore` CHAR(4),
	IN `dataDiModifica` DATE,
	IN `sede` VARCHAR(30),
	IN `dataAppuntamento` DATE,
	IN `orarioAppuntamento` TIME
)
BEGIN
	DECLARE EXIT HANDLER FOR SQLEXCEPTION
	BEGIN
		ROLLBACK;
		RESIGNAL;
	END;

   SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED;
   START TRANSACTION;
	INSERT INTO `nota`(CodiceFiscaleCliente,CodiceOfferta,Esito,
				CodiceOperatore,DataDiModifica)
	VALUES(cf,codiceOfferta,esito,codiceOperatore,dataDiModifica);
	IF (sede IS NOT NULL AND dataAppuntamento IS NOT NULL
     		AND orarioAppuntamento IS NOT NULL) THEN
						INSERT INTO `appuntamento`(CodiceFiscaleCliente, CodiceOfferta, 
    					Sede, `Data`, Orario) 
						VALUES(cf,codiceOfferta,sede,dataAppuntamento,orarioAppuntamento);
	END IF;
   COMMIT;
END//
DELIMITER ;

-- Dump della struttura di tabella crm.telefono
CREATE TABLE IF NOT EXISTS `telefono` (
  `NumeroDiTelefono` varchar(30) NOT NULL,
  `CodiceFiscaleCliente` char(16) NOT NULL,
  PRIMARY KEY (`NumeroDiTelefono`),
  KEY `CF_Telefono_idx` (`CodiceFiscaleCliente`),
  CONSTRAINT `CF_Telefono` FOREIGN KEY (`CodiceFiscaleCliente`) REFERENCES `cliente` (`CodiceFiscale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dump dei dati della tabella crm.telefono: ~1 rows (circa)
INSERT INTO `telefono` (`NumeroDiTelefono`, `CodiceFiscaleCliente`) VALUES
	('+39 3474104392', 'PSQKNM33A01B565J');

-- Dump della struttura di tabella crm.utenti
CREATE TABLE IF NOT EXISTS `utenti` (
  `Username` varchar(30) NOT NULL,
  `Password` varchar(32) NOT NULL,
  `Ruolo` enum('operatore','segreteria') NOT NULL,
  `CodiceUtente` char(4) NOT NULL,
  `NomeUtente` varchar(30) NOT NULL,
  `CognomeUtente` varchar(30) NOT NULL,
  PRIMARY KEY (`Username`),
  UNIQUE KEY `CodiceUtente_UNIQUE` (`CodiceUtente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dump dei dati della tabella crm.utenti: ~2 rows (circa)
INSERT INTO `utenti` (`Username`, `Password`, `Ruolo`, `CodiceUtente`, `NomeUtente`, `CognomeUtente`) VALUES
	('operatore1', '0e9159d0ef350e3354bb09429d417b35', 'operatore', 'A001', 'Valerio', 'Baldazzi'),
	('segreteria1', '8dbb2d8af10214fa5409d23d73e1cec9', 'segreteria', 'B001', 'Massimo', 'Baldazzi');

-- Dump della struttura di trigger crm.appuntamento_before_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `appuntamento_before_insert` BEFORE INSERT ON `appuntamento` FOR EACH ROW BEGIN
	DECLARE counter INT DEFAULT 0;
	DECLARE precedente TIME;
   DECLARE successivo TIME;
   SELECT COUNT(*) INTO counter
   FROM appuntamento
 	WHERE appuntamento.CodiceFiscaleCliente= NEW.CodiceFiscaleCliente AND 
                     appuntamento.`Data` = NEW.`Data`;
   IF counter > 0 THEN 
				SELECT MAX(Orario) INTO precedente
				FROM appuntamento
				WHERE appuntamento.Orario< NEW.Orario;
				SELECT MIN(Orario) INTO successivo
				FROM appuntamento
				WHERE appuntamento.Orario> NEW.Orario;
       		IF precedente IS NOT NULL THEN
		  			IF TIMEDIFF(NEW.Orario, precedente) < '02:00:00' THEN
						SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Errore: già  
                                                presente un appuntamento precedente per il cliente a meno di due ore!';
					END IF;
				END IF;
        		IF successivo IS NOT NULL THEN
					IF TIMEDIFF(successivo, NEW.Orario) < '02:00:00' THEN
						SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Errore: già 
                                                presente un appuntamento successivo per il cliente a meno di due ore!';
					END IF;
				END IF;
	END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dump della struttura di trigger crm.nota_before_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `nota_before_insert` BEFORE INSERT ON `nota` FOR EACH ROW BEGIN
	DECLARE scadenza DATE;
   SELECT DataDiScadenza INTO scadenza
	FROM offerta 
	WHERE offerta.CodiceOfferta= NEW.CodiceOfferta;
	IF NEW.DataDiModifica > scadenza THEN 
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Errore: offerta già scaduta!';
	END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Dump della struttura di trigger crm.offertaaccettata_before_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `offertaaccettata_before_insert` BEFORE INSERT ON `offertaaccettata` FOR EACH ROW BEGIN
		DECLARE scadenza DATE;
   	SELECT DataDiScadenza INTO scadenza
    	FROM offerta 
    	WHERE offerta.CodiceOfferta= NEW.CodiceOfferta;
            IF NEW.DataDiAccettazione > scadenza THEN 
                 SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Errore: offerta già scaduta!';
   	 END IF;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

-- Rimozione temporanea di tabella e creazione della struttura finale della vista
DROP TABLE IF EXISTS `clienti_in_ordine_di_contatto`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `clienti_in_ordine_di_contatto` AS SELECT CodiceFiscale, Nome,Cognome, Indirizzo, Città, Provincia, Paese, DataDiNascita, DataDiRegistrazione
FROM (cliente LEFT JOIN (SELECT nota.CodiceFiscaleCliente AS CodiceFiscaleCliente, MAX(nota.DataDiModifica) AS DataRecente FROM nota GROUP BY nota.CodiceFiscaleCliente) AS note ON ((cliente.CodiceFiscale = note.CodiceFiscaleCliente)))
ORDER BY note.DataRecente ;

-- Rimozione temporanea di tabella e creazione della struttura finale della vista
DROP TABLE IF EXISTS `offerte_scadute_non_accettate`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `offerte_scadute_non_accettate` AS SELECT *
FROM offerta
WHERE CodiceOfferta NOT IN (SELECT DISTINCT CodiceOfferta FROM offertaaccettata)
      AND CURRENT_DATE > DataDiScadenza
		AND CodiceOfferta NOT IN(SELECT DISTINCT CodiceOfferta FROM nota) ;

-- Rimozione temporanea di tabella e creazione della struttura finale della vista
DROP TABLE IF EXISTS `offerte_valide`;
CREATE ALGORITHM=UNDEFINED SQL SECURITY DEFINER VIEW `offerte_valide` AS SELECT 
        `offerta`.`CodiceOfferta` AS `CodiceOfferta`,
        `offerta`.`NomeOfferta` AS `NomeOfferta`,
        `offerta`.`Descrizione` AS `Descrizione`,
        `offerta`.`DataDiScadenza` AS `DataDiScadenza`
    FROM
        `offerta`
    WHERE
        (CURRENT_DATE() <= `offerta`.`DataDiScadenza`) ;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
