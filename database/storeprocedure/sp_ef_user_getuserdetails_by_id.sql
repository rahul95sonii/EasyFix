DELIMITER $$

USE `easyfix`$$

DROP PROCEDURE IF EXISTS `sp_ef_user_getuserdetails_by_id`$$

CREATE PROCEDURE `sp_ef_user_getuserdetails_by_id`(IN in_userId INT)
BEGIN
    
	SELECT U.*, C.city_name FROM `tbl_user` U
	LEFT JOIN `tbl_city` C ON C.city_id = U.city_id 
	WHERE `user_id` = in_userId;
	
    END$$

DELIMITER ;