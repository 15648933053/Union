/*
 Navicat Premium Data Transfer

 Source Server         : phpMysql
 Source Server Type    : MySQL
 Source Server Version : 80012
 Source Host           : localhost:3306
 Source Schema         : union

 Target Server Type    : MySQL
 Target Server Version : 80012
 File Encoding         : 65001

 Date: 28/07/2020 00:03:04
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for active
-- ----------------------------
DROP TABLE IF EXISTS `active`;
CREATE TABLE `active`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '活动名称',
  `content` text CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '活动内容',
  `date` datetime(0) NOT NULL COMMENT '活动日期',
  `score` int(10) NOT NULL COMMENT '活动分数',
  `uni_create` datetime(0) NOT NULL COMMENT '创建时间',
  `uni_modified` datetime(0) NOT NULL COMMENT '修改时间',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for touch
-- ----------------------------
DROP TABLE IF EXISTS `touch`;
CREATE TABLE `touch`  (
  `openid` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'openid ',
  `actid` varchar(19) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '活动id'
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` char(32) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '主键id',
  `openid` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT 'openID',
  `name` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '名字',
  `birth` datetime(0) NOT NULL COMMENT '生日',
  `score` int(10) NOT NULL DEFAULT 0 COMMENT '活动积分',
  `is_deleted` tinyint(1) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
  `uni_create` datetime(0) NOT NULL COMMENT '创建时间',
  `uni_modified` datetime(0) NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
