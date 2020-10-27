DELIMITER $$

USE `easyfix`$$

DROP PROCEDURE IF EXISTS `sp_ef_city_getcitylist`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ef_city_getcitylist`()
BEGIN
    
	SELECT * FROM `tbl_city`;
	
    END$$

DELIMITER ;