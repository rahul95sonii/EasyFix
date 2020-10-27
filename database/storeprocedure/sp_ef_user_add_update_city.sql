DELIMITER $$

USE `easyfix`$$

DROP PROCEDURE IF EXISTS `sp_ef_user_add_update_city`$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `sp_ef_user_add_update_city`(IN in_city_id INT, IN in_city_name VARCHAR(255), 
			IN in_city_status INT)
BEGIN
    
	IF(in_city_id = 0) THEN
		INSERT INTO `tbl_city` (`city_name`,`city_status`) VALUES (in_city_name,in_city_status);
	ELSE 
		UPDATE `tbl_city` SET city_name = in_city_name,
					city_status = in_city_status
				WHERE `city_id` = in_city_id;
	
	END IF;
	
	
    END$$

DELIMITER ;