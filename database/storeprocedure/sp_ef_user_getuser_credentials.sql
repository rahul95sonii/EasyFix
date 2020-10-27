DELIMITER $$

USE `easyfix`$$

DROP PROCEDURE IF EXISTS `sp_ef_user_getuser_credentials`$$

CREATE PROCEDURE `sp_ef_user_getuser_credentials`(IN in_email VARCHAR(255))
BEGIN
    
	SELECT U.*, C.city_name FROM `tbl_user` U
	LEFT JOIN `tbl_city` C ON C.city_id = U.city_id 
	WHERE U.`official_email` = in_email AND U.user_status = 1;
	
    END$$

DELIMITER ;