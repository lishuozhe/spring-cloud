/*
 Navicat Premium Data Transfer

 Source Server         : 39.106.132.103
 Source Server Type    : MySQL
 Source Server Version : 50616
 Source Host           : 39.106.132.103:3306
 Source Schema         : spring-cloud-base

 Target Server Type    : MySQL
 Target Server Version : 50616
 File Encoding         : 65001

 Date: 01/04/2020 16:17:03
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict`;
CREATE TABLE `sys_dict` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除状态: 0-否, 1-是',
  `platform_id` bigint(20) DEFAULT NULL,
  `remarks` varchar(200) DEFAULT NULL,
  `update_by` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `code` varchar(20) DEFAULT NULL,
  `code_name` varchar(20) DEFAULT NULL,
  `label` varchar(20) NOT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `value` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_dict
-- ----------------------------
BEGIN;
INSERT INTO `sys_dict` VALUES (1, 'admin', '2020-04-01 15:29:49', '0', NULL, '备注', NULL, '2020-04-01 15:29:49', 'goods_type', '货物类型', '煤', NULL, 0, 'coal');
COMMIT;

-- ----------------------------
-- Table structure for sys_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除状态: 0-否, 1-是',
  `platform_id` bigint(20) DEFAULT NULL,
  `remarks` varchar(200) DEFAULT NULL,
  `update_by` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `code` varchar(30) NOT NULL,
  `name` varchar(20) NOT NULL,
  `pid` bigint(20) DEFAULT NULL,
  `sort` int(11) DEFAULT NULL,
  `type` char(1) NOT NULL DEFAULT '0' COMMENT '权限类型: 0-系统, 1-模块, 2-接口',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_permission` VALUES (1, 'admin', '2020-04-01 15:01:42', '0', NULL, NULL, NULL, '2020-04-01 15:01:42', 'admin:dict:get', '管理字典', 0, NULL, '0');
INSERT INTO `sys_permission` VALUES (2, 'admin', '2020-04-01 15:02:36', '0', NULL, NULL, NULL, '2020-04-01 15:02:36', 'admin:dict:add', '添加字典', 1, NULL, '0');
INSERT INTO `sys_permission` VALUES (3, 'admin', '2020-04-01 15:02:48', '0', NULL, NULL, NULL, '2020-04-01 15:02:48', 'admin:dict:del', '删除字典', 1, NULL, '0');
INSERT INTO `sys_permission` VALUES (4, 'admin', '2020-04-01 15:02:58', '0', NULL, NULL, NULL, '2020-04-01 15:02:58', 'admin:dict:edit', '修改字典', 1, NULL, '0');
INSERT INTO `sys_permission` VALUES (5, 'admin', '2020-04-01 15:59:51', '0', NULL, NULL, NULL, '2020-04-01 15:59:51', 'admin:permission:get', '管理权限', 0, NULL, '0');
INSERT INTO `sys_permission` VALUES (6, 'admin', '2020-04-01 16:02:06', '0', NULL, NULL, NULL, '2020-04-01 16:02:06', 'admin:permission:add', '添加权限', 5, NULL, '0');
INSERT INTO `sys_permission` VALUES (7, 'admin', '2020-04-01 16:02:16', '0', NULL, NULL, NULL, '2020-04-01 16:02:16', 'admin:permission:del', '删除权限', 5, NULL, '0');
INSERT INTO `sys_permission` VALUES (8, 'admin', '2020-04-01 16:05:41', '0', NULL, NULL, NULL, '2020-04-01 16:05:41', 'admin:permission:edit', '修改权限', 5, NULL, '0');
INSERT INTO `sys_permission` VALUES (9, 'admin', '2020-04-01 16:07:00', '0', NULL, NULL, NULL, '2020-04-01 16:07:00', 'admin:role:get', '管理角色', 0, NULL, '0');
INSERT INTO `sys_permission` VALUES (10, 'admin', '2020-04-01 16:07:31', '0', NULL, NULL, NULL, '2020-04-01 16:07:31', 'admin:role:add', '添加角色', 9, NULL, '0');
INSERT INTO `sys_permission` VALUES (11, 'admin', '2020-04-01 16:07:40', '0', NULL, NULL, NULL, '2020-04-01 16:07:40', 'admin:role:del', '删除角色', 9, NULL, '0');
INSERT INTO `sys_permission` VALUES (12, 'admin', '2020-04-01 16:07:52', '0', NULL, NULL, NULL, '2020-04-01 16:07:52', 'admin:role:edit', '修改角色', 9, NULL, '0');
INSERT INTO `sys_permission` VALUES (13, 'admin', '2020-04-01 16:08:19', '0', NULL, NULL, NULL, '2020-04-01 16:08:19', 'admin:user:get', '管理用户', 0, NULL, '0');
INSERT INTO `sys_permission` VALUES (14, 'admin', '2020-04-01 16:08:58', '0', NULL, NULL, NULL, '2020-04-01 16:08:58', 'admin:user:add', '添加用户', 13, NULL, '0');
INSERT INTO `sys_permission` VALUES (15, 'admin', '2020-04-01 16:09:06', '0', NULL, NULL, NULL, '2020-04-01 16:09:06', 'admin:user:del', '删除用户', 13, NULL, '0');
INSERT INTO `sys_permission` VALUES (16, 'admin', '2020-04-01 16:09:17', '0', NULL, NULL, NULL, '2020-04-01 16:09:17', 'admin:user:edit', '修改用户', 13, NULL, '0');
COMMIT;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除状态: 0-否, 1-是',
  `platform_id` bigint(20) DEFAULT NULL,
  `remarks` varchar(200) DEFAULT NULL,
  `update_by` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `code` varchar(10) NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_role` VALUES (1, 'admin', '2020-04-01 15:03:49', '0', NULL, NULL, 'admin', '2020-04-01 16:11:10', 'ADMIN', '管理用户');
INSERT INTO `sys_role` VALUES (2, 'admin', '2020-04-01 15:04:05', '0', NULL, NULL, 'admin', '2020-04-01 16:12:19', 'TEST', '测试用户');
COMMIT;

-- ----------------------------
-- Table structure for sys_role_permission
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `role_id` bigint(20) NOT NULL,
  `permission_id` bigint(20) NOT NULL,
  KEY `FKomxrs8a388bknvhjokh440waq` (`permission_id`),
  KEY `FK9q28ewrhntqeipl1t04kh1be7` (`role_id`),
  CONSTRAINT `FK9q28ewrhntqeipl1t04kh1be7` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`),
  CONSTRAINT `FKomxrs8a388bknvhjokh440waq` FOREIGN KEY (`permission_id`) REFERENCES `sys_permission` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
BEGIN;
INSERT INTO `sys_role_permission` VALUES (1, 1);
INSERT INTO `sys_role_permission` VALUES (1, 2);
INSERT INTO `sys_role_permission` VALUES (1, 3);
INSERT INTO `sys_role_permission` VALUES (1, 4);
INSERT INTO `sys_role_permission` VALUES (1, 5);
INSERT INTO `sys_role_permission` VALUES (1, 6);
INSERT INTO `sys_role_permission` VALUES (1, 7);
INSERT INTO `sys_role_permission` VALUES (1, 8);
INSERT INTO `sys_role_permission` VALUES (1, 9);
INSERT INTO `sys_role_permission` VALUES (1, 10);
INSERT INTO `sys_role_permission` VALUES (1, 11);
INSERT INTO `sys_role_permission` VALUES (1, 12);
INSERT INTO `sys_role_permission` VALUES (1, 13);
INSERT INTO `sys_role_permission` VALUES (1, 14);
INSERT INTO `sys_role_permission` VALUES (1, 15);
INSERT INTO `sys_role_permission` VALUES (1, 16);
INSERT INTO `sys_role_permission` VALUES (2, 1);
INSERT INTO `sys_role_permission` VALUES (2, 5);
INSERT INTO `sys_role_permission` VALUES (2, 9);
INSERT INTO `sys_role_permission` VALUES (2, 13);
COMMIT;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `create_by` varchar(20) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除状态: 0-否, 1-是',
  `platform_id` bigint(20) DEFAULT NULL,
  `remarks` varchar(200) DEFAULT NULL,
  `update_by` varchar(20) DEFAULT NULL,
  `update_time` datetime DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `mobile` varchar(20) DEFAULT NULL,
  `password` varchar(64) DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user
-- ----------------------------
BEGIN;
INSERT INTO `sys_user` VALUES (1, NULL, NULL, '0', NULL, NULL, 'admin', '2020-04-01 15:04:29', NULL, NULL, '$2a$10$b4QxQh3Bh1lE6J4Ktdvz6OLHbxkY5Xwal4m1q9JYTsrs8Rqd2GYxa', 'admin');
INSERT INTO `sys_user` VALUES (2, 'admin', '2020-04-01 11:40:52', '0', NULL, NULL, 'admin', '2020-04-01 15:04:38', NULL, NULL, '$2a$10$dxKpkyNfNhWcRjw06pmykuV.0gLZba4t6kpW9cN9Bx/sPknEFvKZm', 'test');
COMMIT;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` bigint(20) NOT NULL,
  KEY `FKhh52n8vd4ny9ff4x9fb8v65qx` (`role_id`),
  KEY `FKb40xxfch70f5qnyfw8yme1n1s` (`user_id`),
  CONSTRAINT `FKb40xxfch70f5qnyfw8yme1n1s` FOREIGN KEY (`user_id`) REFERENCES `sys_user` (`id`),
  CONSTRAINT `FKhh52n8vd4ny9ff4x9fb8v65qx` FOREIGN KEY (`role_id`) REFERENCES `sys_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
BEGIN;
INSERT INTO `sys_user_role` VALUES (1, 1);
INSERT INTO `sys_user_role` VALUES (2, 2);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
