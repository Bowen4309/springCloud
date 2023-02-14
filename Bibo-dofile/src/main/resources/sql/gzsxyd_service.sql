/*
 Navicat Premium Data Transfer

 Source Server         : 127.0.0.1
 Source Server Type    : MySQL
 Source Server Version : 50720
 Source Host           : 127.0.0.1:3306
 Source Schema         : gzsxyd_service

 Target Server Type    : MySQL
 Target Server Version : 50720
 File Encoding         : 65001

 Date: 14/04/2020 15:57:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for T_SERVICE_FILE
-- ----------------------------
DROP TABLE IF EXISTS `T_SERVICE_FILE`;
CREATE TABLE `T_SERVICE_FILE`  (
  `FILE_ID` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '文件ID',
  `FILE_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '原文件名',
  `FILE_URL` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件地址',
  `UPLOAD_USER` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '上传用户',
  `UPLOAD_TIME` datetime(0) DEFAULT NULL COMMENT '上传时间',
  `FILE_SIZE` bigint(20) DEFAULT NULL COMMENT '文件大小',
  `FILE_TYPE` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件类型',
  `FILE_NEW_NAME` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '文件名',
  `STATES` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`FILE_ID`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of T_SERVICE_FILE
-- ----------------------------
INSERT INTO `T_SERVICE_FILE` VALUES ('ccda655772834481806f9150cfe5f39e', '接口文档.doc', 'D:\\sxyd\\2020\\04\\10\\c6cd21ad58204aefa0986721af0b64f1.doc', NULL, '2020-04-10 08:38:38', 31744, '.doc', 'c6cd21ad58204aefa0986721af0b64f1.doc', '2');

SET FOREIGN_KEY_CHECKS = 1;
