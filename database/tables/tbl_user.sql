CREATE TABLE `tbl_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_code` varchar(255) DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  `official_email` varchar(255) DEFAULT NULL,
  `user_role` int(11) DEFAULT NULL,
  `city_id` int(11) DEFAULT NULL,
  `user_status` tinyint(2) DEFAULT NULL COMMENT '0:Inactive, 1:Active',
  `insert_date` timestamp NULL DEFAULT NULL,
  `update_date` timestamp NULL DEFAULT NULL,
  `updated_by` int(11) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
)