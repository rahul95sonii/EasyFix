DELIMITER $$

USE `easyfix`$$

DROP PROCEDURE IF EXISTS `sp_ef_city_getcity_details`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ef_city_getcity_details`(IN in_city_id INT)
BEGIN
    
	SELECT * FROM `tbl_city` WHERE `city_id` = in_city_id;
	
    END$$

DELIMITER ;