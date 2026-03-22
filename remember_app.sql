/*
 Navicat Premium Dump SQL

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 80045 (8.0.45)
 Source Host           : localhost:3306
 Source Schema         : remember_app

 Target Server Type    : MySQL
 Target Server Version : 80045 (8.0.45)
 File Encoding         : 65001

 Date: 13/03/2026 19:03:35
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for question_bank_table
-- ----------------------------
DROP TABLE IF EXISTS `question_bank_table`;
CREATE TABLE `question_bank_table`  (
  `qb_id` int NOT NULL AUTO_INCREMENT COMMENT '题库 id',
  `user_id` int NOT NULL COMMENT '定义 user_id 外键',
  `qb_description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题库描述',
  `question_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '定义 question_name 外键',
  `question_number` int NULL DEFAULT NULL COMMENT '题目数量',
  `qb_created_at` datetime NULL DEFAULT NULL COMMENT '题库创建时间',
  `qb_updated_at` datetime NULL DEFAULT NULL COMMENT '题库修改时间',
  PRIMARY KEY (`qb_id`) USING BTREE,
  INDEX `fk_qb_user_id`(`user_id` ASC) USING BTREE,
  INDEX `fk_qb_question_name`(`question_name` ASC) USING BTREE,
  CONSTRAINT `fk_qb_question_name` FOREIGN KEY (`question_name`) REFERENCES `question_table` (`question_name`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_qb_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_table` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for question_table
-- ----------------------------
DROP TABLE IF EXISTS `question_table`;
CREATE TABLE `question_table`  (
  `question_id` int NOT NULL AUTO_INCREMENT COMMENT '题目 id',
  `qb_id` int NOT NULL COMMENT '定义 qb_id 外键',
  `user_id` int NOT NULL COMMENT '定义 user_Id 外键',
  `question_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题目问题',
  `question_answer` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '题目答案',
  `question_created_at` datetime NULL DEFAULT NULL COMMENT '题目创建时间',
  `question_updated_at` datetime NULL DEFAULT NULL COMMENT '题目修改时间',
  PRIMARY KEY (`question_id`) USING BTREE,
  INDEX `question_name`(`question_name` ASC) USING BTREE,
  INDEX `fk_question_qb_id`(`qb_id` ASC) USING BTREE,
  INDEX `fk_question_user_id`(`user_id` ASC) USING BTREE,
  CONSTRAINT `fk_question_qb_id` FOREIGN KEY (`qb_id`) REFERENCES `question_bank_table` (`qb_id`) ON DELETE RESTRICT ON UPDATE CASCADE,
  CONSTRAINT `fk_question_user_id` FOREIGN KEY (`user_id`) REFERENCES `user_table` (`user_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Table structure for user_table
-- ----------------------------
DROP TABLE IF EXISTS `user_table`;
CREATE TABLE `user_table`  (
  `user_id` int NOT NULL AUTO_INCREMENT COMMENT '用户 id',
  `user_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名/邮箱账号',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户密码',
  `user_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户邮箱',
  `user_phone_num` int NULL DEFAULT NULL COMMENT '用户手机号',
  `user_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户头像',
  `user_created_at` datetime NULL DEFAULT NULL COMMENT '用户创建时间',
  `user_updated_at` datetime NULL DEFAULT NULL COMMENT '用户修改时间',
  `user_last_login_at` datetime NULL DEFAULT NULL COMMENT '用户最后登录时间',
  `user_status` int NOT NULL DEFAULT 1 COMMENT '状态 0-禁用 1-正常',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
