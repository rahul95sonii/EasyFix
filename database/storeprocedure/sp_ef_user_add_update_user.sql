DELIMITER $$

USE `easyfix`$$

DROP PROCEDURE IF EXISTS `sp_ef_user_add_update_user`$$

CREATE PROCEDURE `sp_ef_user_add_update_user`(IN in_user_id INT, IN in_user_code VARCHAR(255), 
			IN in_user_name VARCHAR(255),IN in_user_status INT)
BEGIN
    
	IF(in_user_id > 0) THEN 
		UPDATE `tbl_user` SET user_name = in_user_name,
					user_code = in_user_code,
					user_status = in_user_status
				WHERE user_id = in_user_id;
	END IF;
	
	
    END$$

DELIMITER ;