/*
Navicat MariaDB Data Transfer

Source Server         : MariaDB
Source Server Version : 100212
Source Host           : localhost:3306
Source Database       : exchange

Target Server Type    : MariaDB
Target Server Version : 100212
File Encoding         : 65001

Date: 2018-01-07 15:11:13
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for exchange_user
-- ----------------------------
DROP TABLE IF EXISTS `exchange_user`;
CREATE TABLE `exchange_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `username` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '用户密码，加salt并md5加密',
  `head_url` varchar(255) NOT NULL DEFAULT '' COMMENT '用户头像',
  `gender` int(4) NOT NULL COMMENT '性别',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `position` varchar(50) DEFAULT NULL COMMENT '所在公司,所在学校',
  `introduce` varchar(100) DEFAULT NULL COMMENT '一句话介绍',
  `profile` text DEFAULT NULL COMMENT '个人简介',
  `question` varchar(100) DEFAULT NULL COMMENT '找回密码问题',
  `answer` varchar(100) DEFAULT NULL COMMENT '找回密码答案',
  `role` int(4) NOT NULL COMMENT '角色0-管理员,1-学生,2-老师',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_unique` (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exchange_user
-- ----------------------------
