DELIMITER $$

USE `easyfix`$$

DROP PROCEDURE IF EXISTS `sp_ef_user_getuserlist`$$

CREATE PROCEDURE `sp_ef_user_getuserlist`()
BEGIN
    
	SELECT U.*, C.city_name FROM `tbl_user` U
	LEFT JOIN `tbl_city` C ON C.city_id = U.city_id;
	
    END$$

DELIMITER ;