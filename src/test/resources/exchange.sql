/*
Navicat MariaDB Data Transfer

Source Server         : MariaDB
Source Server Version : 100212
Source Host           : localhost:3306
Source Database       : exchange

Target Server Type    : MariaDB
Target Server Version : 100212
File Encoding         : 65001

Date: 2018-01-16 17:48:14
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for exchange_comment
-- ----------------------------
DROP TABLE IF EXISTS `exchange_comment`;
CREATE TABLE `exchange_comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '评论表id',
  `content` text NOT NULL COMMENT '评论的内容',
  `user_id` int(11) NOT NULL COMMENT '发表评论的用户id',
  `entity_id` int(11) NOT NULL COMMENT '评论的实体的id',
  `entity_type` int(11) NOT NULL COMMENT '评论的实体的类型，(问题:0,评论:1)',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '该评论的状态(常态=0,删除=1)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `entity_index` (`entity_id`,`entity_type`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exchange_comment
-- ----------------------------
INSERT INTO `exchange_comment` VALUES ('1', '我有钱', '53', '37', '0', '0', '2018-01-12 21:21:47', '2018-01-13 18:44:57');
INSERT INTO `exchange_comment` VALUES ('2', '我有钱', '53', '37', '0', '0', '2018-01-12 21:22:37', '2018-01-12 21:22:37');
INSERT INTO `exchange_comment` VALUES ('3', '我有钱', '53', '37', '0', '0', '2018-01-12 21:23:31', '2018-01-12 21:23:31');
INSERT INTO `exchange_comment` VALUES ('4', '我有钱', '53', '37', '0', '0', '2018-01-12 21:24:08', '2018-01-12 21:24:08');

-- ----------------------------
-- Table structure for exchange_login_ticket
-- ----------------------------
DROP TABLE IF EXISTS `exchange_login_ticket`;
CREATE TABLE `exchange_login_ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'login_ticket表id',
  `user_id` int(11) NOT NULL COMMENT 't票所属用户id',
  `ticket` varchar(45) NOT NULL DEFAULT '' COMMENT 't票',
  `expired` datetime NOT NULL COMMENT '过期时间',
  `status` int(11) DEFAULT 0 COMMENT '状态，登录0，登出1',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `ticket_unique` (`ticket`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exchange_login_ticket
-- ----------------------------
INSERT INTO `exchange_login_ticket` VALUES ('3', '49', 'b88f21540a3242b7bcfa17adb2c2c015', '2018-01-19 17:25:47', '1', '2018-01-12 17:25:47', '2018-01-12 17:25:47');
INSERT INTO `exchange_login_ticket` VALUES ('5', '52', '2bbb2c6455844f30bc210d0608d3c4d7', '2018-01-19 18:19:13', '1', '2018-01-12 18:19:13', '2018-01-12 18:19:13');
INSERT INTO `exchange_login_ticket` VALUES ('6', '49', 'fe699219833f447d80b51cdbbbf3e1ac', '2018-01-19 18:44:01', '1', '2018-01-12 18:44:01', '2018-01-12 18:44:01');
INSERT INTO `exchange_login_ticket` VALUES ('7', '49', 'd8518c8ec494426c89f3063c1c7bf778', '2018-01-19 19:15:36', '1', '2018-01-12 19:15:36', '2018-01-12 19:15:36');
INSERT INTO `exchange_login_ticket` VALUES ('8', '49', 'def16edbedb74b0db7c2a1ebd655d2a4', '2018-01-19 20:02:18', '1', '2018-01-12 20:02:18', '2018-01-12 20:02:18');
INSERT INTO `exchange_login_ticket` VALUES ('9', '49', '59f3f27ece264970babb98054177e4f1', '2018-01-19 20:08:35', '1', '2018-01-12 20:08:35', '2018-01-12 20:08:35');
INSERT INTO `exchange_login_ticket` VALUES ('10', '49', '0bdd1998035d4923abf963e6cd8f4d23', '2018-01-19 23:04:06', '1', '2018-01-12 23:04:06', '2018-01-12 23:04:06');
INSERT INTO `exchange_login_ticket` VALUES ('11', '52', '008c7d79ee3e42db9ee2797d061bffbe', '2018-01-20 17:28:00', '1', '2018-01-13 17:28:00', '2018-01-13 17:28:00');
INSERT INTO `exchange_login_ticket` VALUES ('12', '53', '0bb184524ccd448ea98f5f80dd086bce', '2018-01-20 17:31:36', '1', '2018-01-13 17:31:36', '2018-01-13 17:31:36');
INSERT INTO `exchange_login_ticket` VALUES ('13', '53', '8d1c74e2dde54b80818b1495e99c53f1', '2018-01-20 18:33:21', '1', '2018-01-13 18:33:21', '2018-01-13 18:33:21');

-- ----------------------------
-- Table structure for exchange_message
-- ----------------------------
DROP TABLE IF EXISTS `exchange_message`;
CREATE TABLE `exchange_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '消息表id',
  `from_id` int(11) DEFAULT NULL COMMENT '接收方',
  `to_id` int(11) DEFAULT NULL COMMENT '发送方',
  `content` text DEFAULT NULL COMMENT '内容',
  `has_read` int(11) NOT NULL DEFAULT 1 COMMENT '已读-0,未读-1',
  `conversation_id` varchar(45) NOT NULL DEFAULT '' COMMENT '会话id(id小->大)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `conversation_id_index` (`conversation_id`) USING BTREE,
  KEY `create_time_index` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exchange_message
-- ----------------------------
INSERT INTO `exchange_message` VALUES ('3', '49', '52', '你也好', '0', '49_52', '2018-01-13 21:04:39', '2018-01-13 21:04:45');
INSERT INTO `exchange_message` VALUES ('4', '49', '52', '最近怎么样', '0', '49_52', '2018-01-13 21:05:19', '2018-01-13 21:05:22');
INSERT INTO `exchange_message` VALUES ('5', '52', '49', '近来身体怎么样', '1', '49_52', '2018-01-15 21:58:04', '2018-01-15 21:58:04');
INSERT INTO `exchange_message` VALUES ('6', '52', '49', '近来身体怎么样', '1', '49_52', '2018-01-15 21:59:14', '2018-01-15 21:59:14');
INSERT INTO `exchange_message` VALUES ('7', '52', '49', '还有一周放假', '1', '49_52', '2018-01-15 22:01:18', '2018-01-15 22:01:18');
INSERT INTO `exchange_message` VALUES ('8', '52', '49', '放假去哪里玩呀', '1', '49_52', '2018-01-15 23:20:06', '2018-01-15 23:20:06');
INSERT INTO `exchange_message` VALUES ('9', '52', '65', '作业太多，写不完啊老师', '1', '52_65', '2018-01-15 23:22:07', '2018-01-15 23:22:07');
INSERT INTO `exchange_message` VALUES ('10', '52', '65', '你是不是到了更年期了啊', '1', '52_65', '2018-01-15 23:23:01', '2018-01-15 23:23:01');
INSERT INTO `exchange_message` VALUES ('11', '52', '53', '用户:admin赞了你的评论，http://127.0.0.1:8080/questions/null', '1', '52_53', '2018-01-16 10:20:00', '2018-01-16 10:20:00');
INSERT INTO `exchange_message` VALUES ('12', '52', '53', '用户:hikari赞了你的评论，http://127.0.0.1:8080/questions/null', '1', '52_53', '2018-01-16 10:23:54', '2018-01-16 10:23:54');
INSERT INTO `exchange_message` VALUES ('13', '52', '53', '用户:admin赞了你的评论，http://127.0.0.1:8080/questions/null', '1', '52_53', '2018-01-16 10:26:02', '2018-01-16 10:26:02');
INSERT INTO `exchange_message` VALUES ('14', '52', '53', '用户:admin赞了你的评论，http://127.0.0.1:8080/questions/null', '1', '52_53', '2018-01-16 14:39:07', '2018-01-16 14:39:07');

-- ----------------------------
-- Table structure for exchange_project
-- ----------------------------
DROP TABLE IF EXISTS `exchange_project`;
CREATE TABLE `exchange_project` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '课题表id',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '课题的题目',
  `detail` text DEFAULT NULL COMMENT '课题详情',
  `user_id` int(11) NOT NULL COMMENT '发表课题的用户id',
  `origin` int(4) NOT NULL COMMENT '来源，**院',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exchange_project
-- ----------------------------
INSERT INTO `exchange_project` VALUES ('1', 'Layui', '<!DOCTYPE html>\\r\\n<html>\\r\\n<head>\\r\\n  <meta charset=\\\"utf-8\\\">\\r\\n  <meta name=\\\"viewport\\\" content=\\\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\\\">\\r\\n  <title>layui在线调试</title>\\r\\n  <link rel=\\\"stylesheet\\\" href=\\\"//res.layui.com/lay/v1/build/css/layui.css\\\" media=\\\"all\\\">\\r\\n  <style>\\r\\n    body{margin: 10px;}\\r\\n  </style>\\r\\n</head>\\r\\n<body>\\r\\n \\r\\n<blockquote class=\\\"layui-elem-quote\\\">\\r\\n  温馨提示：如果最左侧的导航的高度超出了你的屏幕，你可以将鼠标移入导航区域，然后滑动鼠标滚轮即可\\r\\n</blockquote>\\r\\n \\r\\n<div class=\\\"layui-tab layui-tab-card\\\" lay-filter=\\\"demo\\\" style=\\\"height: 300px;\\\">\\r\\n  <ul class=\\\"layui-tab-title\\\">\\r\\n    <li class=\\\"layui-this\\\">演示说明</li>\\r\\n    <li>标题一</li>\\r\\n    <li>标题二</li>\\r\\n    <li>标题三</li>\\r\\n  </ul>\\r\\n  <div class=\\\"layui-tab-content\\\">\\r\\n    <div class=\\\"layui-tab-item layui-show\\\">\\r\\n       在这里，你将以最直观的形式体验Layui！在编辑器中可以执行layui相关的一切代码。\\r\\n      <br>你也可以点击左侧导航针对性地试验我们提供的示例。\\r\\n    </div>\\r\\n    <div class=\\\"layui-tab-item\\\">内容1</div>\\r\\n    <div class=\\\"layui-tab-item\\\">内容2</div>\\r\\n    <div class=\\\"layui-tab-item\\\">内容3</div>\\r\\n  </div>\\r\\n</div>\\r\\n \\r\\n<div id=\\\"pageDemo\\\"></div>\\r\\n \\r\\n<script src=\\\"//res.layui.com/lay/v1/build/layui.js\\\"></script>\\r\\n<script>\\r\\nlayui.use([\'layer\', \'laypage\', \'element\'], function(){\\r\\n  var layer = layui.layer\\r\\n  ,laypage = layui.laypage\\r\\n  ,element = layui.element();\\r\\n  \\r\\n  //向世界问个好\\r\\n  layer.msg(\'Hello World\');\\r\\n \\r\\n  //监听Tab切换\\r\\n  element.on(\'tab(demo)\', function(data){\\r\\n    layer.msg(\'切换了：\'  this.innerHTML);\\r\\n    console.log(data);\\r\\n  });\\r\\n  \\r\\n  //分页\\r\\n  laypage({\\r\\n    cont: \'pageDemo\' //分页容器的id\\r\\n    ,pages: 100 //总页数\\r\\n    ,skin: \'', '65', '14', '2018-01-14 14:07:11', '2018-01-14 15:18:24');
INSERT INTO `exchange_project` VALUES ('3', 'Layui', '<!DOCTYPE html>\\r\\n<html>\\r\\n<head>\\r\\n  <meta charset=\\\"utf-8\\\">\\r\\n  <meta name=\\\"viewport\\\" content=\\\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\\\">\\r\\n  <title>layui在线调试</title>\\r\\n  <link rel=\\\"stylesheet\\\" href=\\\"//res.layui.com/lay/v1/build/css/layui.css\\\" media=\\\"all\\\">\\r\\n  <style>\\r\\n    body{margin: 10px;}\\r\\n  </style>\\r\\n</head>\\r\\n<body>\\r\\n \\r\\n<blockquote class=\\\"layui-elem-quote\\\">\\r\\n  温馨提示：如果最左侧的导航的高度超出了你的屏幕，你可以将鼠标移入导航区域，然后滑动鼠标滚轮即可\\r\\n</blockquote>\\r\\n \\r\\n<div class=\\\"layui-tab layui-tab-card\\\" lay-filter=\\\"demo\\\" style=\\\"height: 300px;\\\">\\r\\n  <ul class=\\\"layui-tab-title\\\">\\r\\n    <li class=\\\"layui-this\\\">演示说明</li>\\r\\n    <li>标题一</li>\\r\\n    <li>标题二</li>\\r\\n    <li>标题三</li>\\r\\n  </ul>\\r\\n  <div class=\\\"layui-tab-content\\\">\\r\\n    <div class=\\\"layui-tab-item layui-show\\\">\\r\\n       在这里，你将以最直观的形式体验Layui！在编辑器中可以执行layui相关的一切代码。\\r\\n      <br>你也可以点击左侧导航针对性地试验我们提供的示例。\\r\\n    </div>\\r\\n    <div class=\\\"layui-tab-item\\\">内容1</div>\\r\\n    <div class=\\\"layui-tab-item\\\">内容2</div>\\r\\n    <div class=\\\"layui-tab-item\\\">内容3</div>\\r\\n  </div>\\r\\n</div>\\r\\n \\r\\n<div id=\\\"pageDemo\\\"></div>\\r\\n \\r\\n<script src=\\\"//res.layui.com/lay/v1/build/layui.js\\\"></script>\\r\\n<script>\\r\\nlayui.use([\'layer\', \'laypage\', \'element\'], function(){\\r\\n  var layer = layui.layer\\r\\n  ,laypage = layui.laypage\\r\\n  ,element = layui.element();\\r\\n  \\r\\n  //向世界问个好\\r\\n  layer.msg(\'Hello World\');\\r\\n \\r\\n  //监听Tab切换\\r\\n  element.on(\'tab(demo)\', function(data){\\r\\n    layer.msg(\'切换了：\'  this.innerHTML);\\r\\n    console.log(data);\\r\\n  });\\r\\n  \\r\\n  //分页\\r\\n  laypage({\\r\\n    cont: \'pageDemo\' //分页容器的id\\r\\n    ,pages: 100 //总页数\\r\\n    ,skin: \'', '65', '14', '2018-01-14 15:24:58', '2018-01-14 15:24:58');
INSERT INTO `exchange_project` VALUES ('4', 'JMS', '<!DOCTYPE html>\\r\\n<html>\\r\\n<head>\\r\\n  <meta charset=\\\"utf-8\\\">\\r\\n  <meta name=\\\"viewport\\\" content=\\\"width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0\\\">\\r\\n  <title>layui在线调试</title>\\r\\n  <link rel=\\\"stylesheet\\\" href=\\\"//res.layui.com/lay/v1/build/css/layui.css\\\" media=\\\"all\\\">\\r\\n  <style>\\r\\n    body{margin: 10px;}\\r\\n  </style>\\r\\n</head>\\r\\n<body>\\r\\n \\r\\n<blockquote class=\\\"layui-elem-quote\\\">\\r\\n  温馨提示：如果最左侧的导航的高度超出了你的屏幕，你可以将鼠标移入导航区域，然后滑动鼠标滚轮即可\\r\\n</blockquote>\\r\\n \\r\\n<div class=\\\"layui-tab layui-tab-card\\\" lay-filter=\\\"demo\\\" style=\\\"height: 300px;\\\">\\r\\n  <ul class=\\\"layui-tab-title\\\">\\r\\n    <li class=\\\"layui-this\\\">演示说明</li>\\r\\n    <li>标题一</li>\\r\\n    <li>标题二</li>\\r\\n    <li>标题三</li>\\r\\n  </ul>\\r\\n  <div class=\\\"layui-tab-content\\\">\\r\\n    <div class=\\\"layui-tab-item layui-show\\\">\\r\\n       在这里，你将以最直观的形式体验Layui！在编辑器中可以执行layui相关的一切代码。\\r\\n      <br>你也可以点击左侧导航针对性地试验我们提供的示例。\\r\\n    </div>\\r\\n    <div class=\\\"layui-tab-item\\\">内容1</div>\\r\\n    <div class=\\\"layui-tab-item\\\">内容2</div>\\r\\n    <div class=\\\"layui-tab-item\\\">内容3</div>\\r\\n  </div>\\r\\n</div>\\r\\n \\r\\n<div id=\\\"pageDemo\\\"></div>\\r\\n \\r\\n<script src=\\\"//res.layui.com/lay/v1/build/layui.js\\\"></script>\\r\\n<script>\\r\\nlayui.use([\'layer\', \'laypage\', \'element\'], function(){\\r\\n  var layer = layui.layer\\r\\n  ,laypage = layui.laypage\\r\\n  ,element = layui.element();\\r\\n  \\r\\n  //向世界问个好\\r\\n  layer.msg(\'Hello World\');\\r\\n \\r\\n  //监听Tab切换\\r\\n  element.on(\'tab(demo)\', function(data){\\r\\n    layer.msg(\'切换了：\'  this.innerHTML);\\r\\n    console.log(data);\\r\\n  });\\r\\n  \\r\\n  //分页\\r\\n  laypage({\\r\\n    cont: \'pageDemo\' //分页容器的id\\r\\n    ,pages: 100 //总页数\\r\\n    ,skin: \'', '65', '14', '2018-01-14 15:28:48', '2018-01-14 15:28:48');
INSERT INTO `exchange_project` VALUES ('5', 'Hello World', 'print \"Hello World!\"', '65', '14', '2018-01-14 15:29:31', '2018-01-14 15:29:31');

-- ----------------------------
-- Table structure for exchange_question
-- ----------------------------
DROP TABLE IF EXISTS `exchange_question`;
CREATE TABLE `exchange_question` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '问题表id',
  `title` varchar(255) NOT NULL DEFAULT '' COMMENT '问题标题',
  `content` text DEFAULT NULL COMMENT '问题内容',
  `user_id` int(11) NOT NULL COMMENT '发表问题的用户id',
  `comment_count` int(11) NOT NULL COMMENT '评论数量',
  `create_time` datetime NOT NULL COMMENT '问题创建时间',
  `update_time` datetime NOT NULL COMMENT '问题更新时间',
  PRIMARY KEY (`id`),
  KEY `create_date_index` (`create_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exchange_question
-- ----------------------------
INSERT INTO `exchange_question` VALUES ('23', 'TITLE{0}', 'Balaababalalalal Content 0', '49', '0', '2018-01-12 18:34:45', '2018-01-12 18:34:45');
INSERT INTO `exchange_question` VALUES ('24', 'TITLE{1}', 'Balaababalalalal Content 1', '52', '1', '2018-01-12 18:34:45', '2018-01-12 18:34:45');
INSERT INTO `exchange_question` VALUES ('25', 'TITLE{2}', 'Balaababalalalal Content 2', '53', '2', '2018-01-12 18:34:45', '2018-01-12 18:34:45');
INSERT INTO `exchange_question` VALUES ('26', 'TITLE{3}', 'Balaababalalalal Content 3', '54', '3', '2018-01-12 18:34:45', '2018-01-12 18:34:45');
INSERT INTO `exchange_question` VALUES ('27', 'TITLE{4}', 'Balaababalalalal Content 4', '55', '4', '2018-01-12 18:34:45', '2018-01-12 18:34:45');
INSERT INTO `exchange_question` VALUES ('28', 'TITLE{5}', 'Balaababalalalal Content 5', '56', '5', '2018-01-12 18:34:45', '2018-01-12 18:34:45');
INSERT INTO `exchange_question` VALUES ('29', 'TITLE{6}', 'Balaababalalalal Content 6', '57', '6', '2018-01-12 18:34:46', '2018-01-12 18:34:46');
INSERT INTO `exchange_question` VALUES ('30', 'TITLE{7}', 'Balaababalalalal Content 7', '58', '7', '2018-01-12 18:34:46', '2018-01-12 18:34:46');
INSERT INTO `exchange_question` VALUES ('31', 'TITLE{8}', 'Balaababalalalal Content 8', '59', '8', '2018-01-12 18:34:46', '2018-01-12 18:34:46');
INSERT INTO `exchange_question` VALUES ('32', 'TITLE{9}', 'Balaababalalalal Content 9', '60', '9', '2018-01-12 18:34:46', '2018-01-12 18:34:46');
INSERT INTO `exchange_question` VALUES ('34', 'unix在终端中怎么直接拷贝当前路径?', 'unix 在终端中怎么直接拷贝当前路径？我到当前文件路径中例如：cd /usr/local/src然后我怎么拷贝当前路径呢？我目前是用 pwd命令然后再用鼠标复制，现在我能直接就拷贝这个路径吗？更正确一点的说法是：在不同的文字终端中用到这个路径，想方便地把这个路径保存到系统剪贴板中。', '49', '0', '2018-01-12 20:57:24', '2018-01-12 20:57:24');
INSERT INTO `exchange_question` VALUES ('37', '很多？我到当前文件路径中例如：cd /usr/local/src然后我怎么拷贝当前路径呢？我目前是用 pwd命令然后再用鼠标复制，现在我能直接就拷贝这个路径吗？更正确一点的说法是：在不同的文字终端中用到这个路径，想方便地把这个路径保存到系统剪贴板中。', '很多？我到当前文件路径中例如：cd /usr/local/src然后我怎么拷贝当前路径呢？我目前是用 pwd命令然后再用鼠标复制，现在我能直接就拷贝这个路径吗？更正确一点的说法是：在不同的文字终端中用到这个路径，想方便地把这个路径保存到系统剪贴板中。', '49', '6', '2018-01-12 21:07:07', '2018-01-12 21:07:07');

-- ----------------------------
-- Table structure for exchange_user
-- ----------------------------
DROP TABLE IF EXISTS `exchange_user`;
CREATE TABLE `exchange_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户表id',
  `username` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(50) NOT NULL DEFAULT '' COMMENT '用户密码，加salt并md5加密hikari',
  `head_url` varchar(255) DEFAULT '' COMMENT '用户头像',
  `gender` int(4) DEFAULT NULL COMMENT '性别0-男,1-女',
  `role` int(4) NOT NULL COMMENT '角色0-管理员,1-学生,2-老师',
  `email` varchar(50) DEFAULT '' COMMENT '邮箱',
  `phone` varchar(20) DEFAULT NULL COMMENT '电话号码',
  `position` varchar(50) DEFAULT NULL COMMENT '所在公司,所在学校',
  `introduce` varchar(100) DEFAULT NULL COMMENT '一句话介绍',
  `profile` text DEFAULT NULL COMMENT '个人简介',
  `question` varchar(100) DEFAULT NULL COMMENT '找回密码问题',
  `answer` varchar(100) DEFAULT NULL COMMENT '找回密码答案',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` datetime NOT NULL COMMENT '最后一次更新时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name_unique` (`username`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=68 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of exchange_user
-- ----------------------------
INSERT INTO `exchange_user` VALUES ('49', 'hikari', 'E3077D6DB9522E04276CA57909F4C826', 'http://img4.imgtn.bdimg.com/it/u=1121475478,2545730346&fm=27&gp=0.jpg', '0', '0', 'hikari@qq.com', '13100000000', '中北大学', 'bug比代码多', 'coding是副，主业是null。挖坑诚可贵，填坑价更高。若为跳票故，两者皆可抛。', '我叫什么名字', 'hikari', '2018-01-07 17:45:22', '2018-01-07 17:45:22');
INSERT INTO `exchange_user` VALUES ('52', 'admin', 'D0E3070BB05188D005A077B16FE72CC3', 'http://images.nowcoder.com/head/958t.png', '0', '0', 'admin@qq.com', null, null, null, null, null, null, '2018-01-12 18:19:13', '2018-01-12 18:19:13');
INSERT INTO `exchange_user` VALUES ('53', 'USER0', '3C3E9B3697A0B06DB27E524446165A2B', 'http://images.nowcoder.com/head/849t.png', '0', '1', null, null, null, null, null, null, null, '2018-01-12 18:34:45', '2018-01-12 18:34:45');
INSERT INTO `exchange_user` VALUES ('54', 'USER1', '', 'http://images.nowcoder.com/head/604t.png', '1', '1', null, null, null, null, null, null, null, '2018-01-12 18:34:45', '2018-01-12 18:34:45');
INSERT INTO `exchange_user` VALUES ('55', 'USER2', '', 'http://images.nowcoder.com/head/967t.png', '0', '1', null, null, null, null, null, null, null, '2018-01-12 18:34:45', '2018-01-12 18:34:45');
INSERT INTO `exchange_user` VALUES ('56', 'USER3', '', 'http://images.nowcoder.com/head/122t.png', '1', '1', null, null, null, null, null, null, null, '2018-01-12 18:34:45', '2018-01-12 18:34:45');
INSERT INTO `exchange_user` VALUES ('57', 'USER4', '', 'http://images.nowcoder.com/head/585t.png', '0', '1', null, null, null, null, null, null, null, '2018-01-12 18:34:45', '2018-01-12 18:34:45');
INSERT INTO `exchange_user` VALUES ('58', 'USER5', '', 'http://images.nowcoder.com/head/456t.png', '1', '1', null, null, null, null, null, null, null, '2018-01-12 18:34:45', '2018-01-12 18:34:45');
INSERT INTO `exchange_user` VALUES ('59', 'USER6', '', 'http://images.nowcoder.com/head/719t.png', '0', '1', null, null, null, null, null, null, null, '2018-01-12 18:34:45', '2018-01-12 18:34:45');
INSERT INTO `exchange_user` VALUES ('60', 'USER7', '', 'http://images.nowcoder.com/head/698t.png', '1', '1', null, null, null, null, null, null, null, '2018-01-12 18:34:46', '2018-01-12 18:34:46');
INSERT INTO `exchange_user` VALUES ('61', 'USER8', '', 'http://images.nowcoder.com/head/799t.png', '0', '1', null, null, null, null, null, null, null, '2018-01-12 18:34:46', '2018-01-12 18:34:46');
INSERT INTO `exchange_user` VALUES ('62', 'USER9', '', 'http://images.nowcoder.com/head/292t.png', '1', '1', null, null, null, null, null, null, null, '2018-01-12 18:34:46', '2018-01-12 18:34:46');
INSERT INTO `exchange_user` VALUES ('63', 'USER10', '', 'http://images.nowcoder.com/head/297t.png', '0', '1', null, null, null, null, null, null, null, '2018-01-12 18:34:46', '2018-01-12 18:34:46');
INSERT INTO `exchange_user` VALUES ('65', 'teacher', '15FEA567998D657BA52CDE29A992BB59', 'http://pic.qqtn.com/up/2017-10/15082099205103498.jpg', '1', '2', '', null, null, null, null, null, null, '2018-01-14 14:02:31', '2018-01-14 14:02:33');
