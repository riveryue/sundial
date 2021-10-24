CREATE database if NOT EXISTS `sundial` default character set utf8mb4 collate utf8mb4_unicode_ci;
use `sundial`;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for task_conf
-- ----------------------------
DROP TABLE IF EXISTS `task_conf`;
CREATE TABLE `task_conf` (
  `id` int NOT NULL AUTO_INCREMENT,
  `task_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` tinyint DEFAULT NULL COMMENT '1 available, 2 unavailable',
  `cron` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uni_task_name` (`task_name`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ----------------------------
-- Records of task_conf
-- ----------------------------
INSERT INTO `task_conf` VALUES ('1', 'test1', '1', '*/4 * * * * ?');
INSERT INTO `task_conf` VALUES ('2', 'test2', '1', '*/9 * * * * ?');