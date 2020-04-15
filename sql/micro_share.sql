/*
 Navicat MySQL Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 50725
 Source Host           : localhost:3306
 Source Schema         : micro_share

 Target Server Type    : MySQL
 Target Server Version : 50725
 File Encoding         : 65001

 Date: 15/04/2020 22:13:28
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for at_me
-- ----------------------------
DROP TABLE IF EXISTS `at_me`;
CREATE TABLE `at_me`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '类型对应id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '艾特我的',
  `at_me_type` int(11) NULL DEFAULT NULL COMMENT '艾特类型',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '艾特表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of at_me
-- ----------------------------
INSERT INTO `at_me` VALUES (2, 5, 2, 0, 0, 0, '2019-04-28 12:17:08', '2019-04-28 12:17:08');
INSERT INTO `at_me` VALUES (3, 6, 2, 0, 0, 0, '2019-04-28 12:25:22', '2019-04-28 12:25:22');
INSERT INTO `at_me` VALUES (4, 7, 2, 0, 0, 0, '2019-04-28 12:25:54', '2019-04-28 12:25:54');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '类型对应id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '评论内容',
  `likes_num` int(11) NULL DEFAULT NULL COMMENT '评论点赞数',
  `reply_num` int(11) NULL DEFAULT NULL COMMENT '评论回复数',
  `reply_to` int(11) NULL DEFAULT NULL COMMENT '回复给谁',
  `comment_type` int(11) NULL DEFAULT NULL COMMENT '评论类型',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '评论表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES (1, 1, 2, '第一条评论啦啦啦', 0, 1, 1, 0, 0, 1, '2019-04-23 05:19:51', '2019-04-23 05:21:07');
INSERT INTO `comment` VALUES (2, 1, 2, '第一条评论的回复', 0, 0, 2, 1, 0, 0, '2019-04-23 05:21:07', '2019-04-23 05:21:07');

-- ----------------------------
-- Table structure for likes
-- ----------------------------
DROP TABLE IF EXISTS `likes`;
CREATE TABLE `likes`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `type_id` int(11) NULL DEFAULT NULL COMMENT '类型对应id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `likes` tinyint(1) NULL DEFAULT NULL COMMENT '是否点赞',
  `likes_type` int(11) NULL DEFAULT NULL COMMENT '点赞类型',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '点赞表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of likes
-- ----------------------------

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限描述',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of permission
-- ----------------------------

-- ----------------------------
-- Table structure for post
-- ----------------------------
DROP TABLE IF EXISTS `post`;
CREATE TABLE `post`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `likes_num` int(11) NULL DEFAULT NULL COMMENT '点赞数',
  `comment_num` int(11) NULL DEFAULT NULL COMMENT '评论数',
  `allow_comment` tinyint(1) NULL DEFAULT NULL COMMENT '允许评论',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '贴子表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post
-- ----------------------------
INSERT INTO `post` VALUES (1, 1, '我的第一条ins', 0, 1, 1, 0, 1, '2019-04-04 19:48:58', '2019-04-23 05:19:51');
INSERT INTO `post` VALUES (2, 1, '我的第二条ins', 0, 0, 0, 0, 0, '2019-04-04 21:30:24', '2019-04-04 21:30:24');
INSERT INTO `post` VALUES (3, 3, '新的微博啦啦啦', 0, 0, 0, 0, 0, '2019-04-28 11:53:32', '2019-04-28 11:53:32');
INSERT INTO `post` VALUES (5, 3, '新的微博啦啦啦@不属于你的康仔的 出来啦，这是一个#测试话题 拉拉', 0, 0, 0, 0, 0, '2019-04-28 12:17:08', '2019-04-28 12:17:08');
INSERT INTO `post` VALUES (6, 3, '新的微博啦啦22啦@不属于你的康仔的 出来啦，这是一个#测试话题 拉拉', 0, 0, 0, 0, 0, '2019-04-28 12:25:22', '2019-04-28 12:25:22');
INSERT INTO `post` VALUES (7, 3, '新的微博啦啦22啦@不属于你的康仔的 出来啦，这是一个#测试话题2 拉拉', 0, 0, 0, 0, 0, '2019-04-28 12:25:54', '2019-04-28 12:25:54');

-- ----------------------------
-- Table structure for post_detail
-- ----------------------------
DROP TABLE IF EXISTS `post_detail`;
CREATE TABLE `post_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `post_id` int(11) NULL DEFAULT NULL COMMENT '用户贴子id',
  `sort` int(11) NULL DEFAULT NULL COMMENT '排序',
  `resource_id` int(11) NULL DEFAULT NULL COMMENT '贴子图片id',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '贴子详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of post_detail
-- ----------------------------
INSERT INTO `post_detail` VALUES (1, 1, 1, 1, 0, 0, '2019-04-04 19:48:58', '2019-04-04 19:48:58');
INSERT INTO `post_detail` VALUES (2, 2, 1, 1, 0, 0, '2019-04-04 21:30:24', '2019-04-04 21:30:24');
INSERT INTO `post_detail` VALUES (3, 2, 2, 2, 0, 0, '2019-04-04 21:30:24', '2019-04-04 21:30:24');
INSERT INTO `post_detail` VALUES (4, 3, 1, 12, 0, 0, '2019-04-28 11:53:32', '2019-04-28 11:53:32');
INSERT INTO `post_detail` VALUES (5, 3, 2, 13, 0, 0, '2019-04-28 11:53:32', '2019-04-28 11:53:32');
INSERT INTO `post_detail` VALUES (6, 3, 3, 14, 0, 0, '2019-04-28 11:53:32', '2019-04-28 11:53:32');
INSERT INTO `post_detail` VALUES (10, 5, 1, 12, 0, 0, '2019-04-28 12:17:08', '2019-04-28 12:17:08');
INSERT INTO `post_detail` VALUES (11, 5, 2, 13, 0, 0, '2019-04-28 12:17:08', '2019-04-28 12:17:08');
INSERT INTO `post_detail` VALUES (12, 5, 3, 14, 0, 0, '2019-04-28 12:17:08', '2019-04-28 12:17:08');
INSERT INTO `post_detail` VALUES (13, 6, 1, 12, 0, 0, '2019-04-28 12:25:22', '2019-04-28 12:25:22');
INSERT INTO `post_detail` VALUES (14, 6, 2, 13, 0, 0, '2019-04-28 12:25:22', '2019-04-28 12:25:22');
INSERT INTO `post_detail` VALUES (15, 6, 3, 14, 0, 0, '2019-04-28 12:25:22', '2019-04-28 12:25:22');
INSERT INTO `post_detail` VALUES (16, 7, 1, 12, 0, 0, '2019-04-28 12:25:54', '2019-04-28 12:25:54');
INSERT INTO `post_detail` VALUES (17, 7, 2, 13, 0, 0, '2019-04-28 12:25:54', '2019-04-28 12:25:54');
INSERT INTO `post_detail` VALUES (18, 7, 3, 14, 0, 0, '2019-04-28 12:25:54', '2019-04-28 12:25:54');

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `original_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '原始图片',
  `watermark_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '水印图片',
  `thumbnail_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '缩略图片',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES (1, '94ea9d7d491b4b0f853e2fb51f754834_original.png', '94ea9d7d491b4b0f853e2fb51f754834_watermark.png', '94ea9d7d491b4b0f853e2fb51f754834_thumbnail.png', 0, 0, '2019-04-03 23:09:31', '2019-04-03 23:09:31');
INSERT INTO `resource` VALUES (2, '318da4fd9bb843a485680cd4ceb14502_original.png', '318da4fd9bb843a485680cd4ceb14502_watermark.png', '318da4fd9bb843a485680cd4ceb14502_thumbnail.png', 0, 0, '2019-04-03 23:32:39', '2019-04-03 23:32:39');
INSERT INTO `resource` VALUES (3, '1b70c7140a824fda85288b3a6e8741c1_original.png', '1b70c7140a824fda85288b3a6e8741c1_watermark.png', '1b70c7140a824fda85288b3a6e8741c1_thumbnail.png', 0, 0, '2019-04-15 11:48:42', '2019-04-15 11:48:42');
INSERT INTO `resource` VALUES (4, '629357b98f974300a7a2b6e93d344896_original.png', '629357b98f974300a7a2b6e93d344896_watermark.png', '629357b98f974300a7a2b6e93d344896_thumbnail.png', 0, 0, '2019-04-15 11:50:29', '2019-04-15 11:50:29');
INSERT INTO `resource` VALUES (5, '2bbf76d28b334753ba03564cd9ed901d_original.png', '2bbf76d28b334753ba03564cd9ed901d_watermark.png', '2bbf76d28b334753ba03564cd9ed901d_thumbnail.png', 0, 0, '2019-04-15 11:51:16', '2019-04-15 11:51:16');
INSERT INTO `resource` VALUES (6, '46aa36ca40f342168e362ec63b61ec1e.png', '909f54558ff0426b822f0c1f579b2e78.png', '1650b8b7fff2463c9c96feb59bc2b17d.png', 0, 0, '2019-04-15 13:04:07', '2019-04-15 13:04:07');
INSERT INTO `resource` VALUES (7, '46aa36ca40f342168e362ec63b61ec1e.png', '047ba9ab8e1142418ce483a0838cb2ed.png', '7f7a96cbdb8e4992ae493b62369264c2.png', 0, 0, '2019-04-15 13:04:59', '2019-04-15 13:04:59');
INSERT INTO `resource` VALUES (8, '46aa36ca40f342168e362ec63b61ec1e.png', 'fcd0265c880346d3a849d9d58be1542a.png', '0408580b2c1846a48f9b8098e36a2e55.png', 0, 0, '2019-04-15 13:05:06', '2019-04-15 13:05:06');
INSERT INTO `resource` VALUES (9, 'b98dcbb5facb4ed599a35f69e863bd24.png', 'ebe4fb5b31c5454bbcee4c5b4719b6cf.png', 'd304c545dcb5481685eeb5a377b2cb08.png', 0, 0, '2019-04-15 22:48:13', '2019-04-15 22:48:13');
INSERT INTO `resource` VALUES (10, 'a99cb65336a0466db95224240e534e75.png', '9a7eb64918e14b4f83a37848ce79c330.png', '7e3cce17246f4de3be7dd3de3132b440.png', 0, 0, '2019-04-15 23:00:27', '2019-04-15 23:00:27');
INSERT INTO `resource` VALUES (11, '43e03ec2d000461987c84a4801f2638e.jpg', '568b48d2313b49cf9a4725eab4631973.jpg', 'c1a6a5ce64d14833aa55f177b85c0e2f.jpg', 0, 0, '2019-04-15 23:01:11', '2019-04-15 23:01:11');
INSERT INTO `resource` VALUES (12, '60f0bc6d57b949dc88902a7097a2fb40.jpg', '7463a586ff1345b4acf4fa652adb5a6c.jpg', '73536115405a4386aec9417feaf6d0e8.jpg', 0, 0, '2019-04-15 23:01:50', '2019-04-15 23:01:50');
INSERT INTO `resource` VALUES (13, 'a07524d62dcd4b07886fbd71c451fb8a.jpg', 'c95472f541b74b86a92270c72644291e.jpg', 'b0d3637108e746edbdf11f93ffcbf69d.jpg', 0, 0, '2019-04-15 23:06:13', '2019-04-15 23:06:13');
INSERT INTO `resource` VALUES (14, 'd30664adc8154134834a2677ec915c8f.jpg', 'aac1cd17fc43476d87030d5984025d05.jpg', '501259f98c3047ea9d4d43b7da5b4878.jpg', 0, 0, '2019-04-27 18:58:02', '2019-04-27 18:58:02');

-- ----------------------------
-- Table structure for resource_detail
-- ----------------------------
DROP TABLE IF EXISTS `resource_detail`;
CREATE TABLE `resource_detail`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `md5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'MD5值',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网络路径',
  `path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '物理路径',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '资源名称',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 43 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '资源详情表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of resource_detail
-- ----------------------------
INSERT INTO `resource_detail` VALUES (1, '4de767a826329cb2d490213af46e8fc7', '/instagram/resource_detail/download/', '/root/instagram/resource/', '94ea9d7d491b4b0f853e2fb51f754834_original.png', 0, 0, '2019-04-03 23:09:30', '2019-04-03 23:09:30');
INSERT INTO `resource_detail` VALUES (2, '97911148888f1b4b39aa80718b899b05', '/instagram/resource_detail/download/', '/root/instagram/resource/', '94ea9d7d491b4b0f853e2fb51f754834_watermark.png', 0, 0, '2019-04-03 23:09:31', '2019-04-03 23:09:31');
INSERT INTO `resource_detail` VALUES (3, 'ee5e764cb277ba88c6b38b14e11b14de', '/instagram/resource_detail/download/', '/root/instagram/resource/', '94ea9d7d491b4b0f853e2fb51f754834_thumbnail.png', 0, 0, '2019-04-03 23:09:31', '2019-04-03 23:09:31');
INSERT INTO `resource_detail` VALUES (4, 'd965b8b0be5e49fa7c28b36b6d1aa335', '/instagram/resource_detail/download/', '/root/instagram/resource/', '318da4fd9bb843a485680cd4ceb14502_original.png', 0, 0, '2019-04-03 23:32:39', '2019-04-03 23:32:39');
INSERT INTO `resource_detail` VALUES (5, 'f04080fd2a44cac645e74d1239ac9bfa', '/instagram/resource_detail/download/', '/root/instagram/resource/', '318da4fd9bb843a485680cd4ceb14502_watermark.png', 0, 0, '2019-04-03 23:32:39', '2019-04-03 23:32:39');
INSERT INTO `resource_detail` VALUES (6, 'ad2abb874b41f5ddb093ec0a4772192d', '/instagram/resource_detail/download/', '/root/instagram/resource/', '318da4fd9bb843a485680cd4ceb14502_thumbnail.png', 0, 0, '2019-04-03 23:32:39', '2019-04-03 23:32:39');
INSERT INTO `resource_detail` VALUES (7, '5ffbf090e112b3535ebc8be2552d01c8', '/instagram/resource_detail/download/', '/root/instagram/resource/', '1b70c7140a824fda85288b3a6e8741c1_original.png', 0, 0, '2019-04-15 11:48:42', '2019-04-15 11:48:42');
INSERT INTO `resource_detail` VALUES (8, 'd486a33c8f2be77419076afb25c61dcc', '/instagram/resource_detail/download/', '/root/instagram/resource/', '1b70c7140a824fda85288b3a6e8741c1_watermark.png', 0, 0, '2019-04-15 11:48:42', '2019-04-15 11:48:42');
INSERT INTO `resource_detail` VALUES (9, '4d86712dc199c3de931d46ad6805fd2c', '/instagram/resource_detail/download/', '/root/instagram/resource/', '1b70c7140a824fda85288b3a6e8741c1_thumbnail.png', 0, 0, '2019-04-15 11:48:42', '2019-04-15 11:48:42');
INSERT INTO `resource_detail` VALUES (10, '705c1563820d9e47fd9ae76470de24c7', '/instagram/resource_detail/download/', '/root/instagram/resource/', '629357b98f974300a7a2b6e93d344896_original.png', 0, 0, '2019-04-15 11:50:28', '2019-04-15 11:50:28');
INSERT INTO `resource_detail` VALUES (11, '281444aa4a9ef4fee69d45eca3007252', '/instagram/resource_detail/download/', '/root/instagram/resource/', '629357b98f974300a7a2b6e93d344896_watermark.png', 0, 0, '2019-04-15 11:50:28', '2019-04-15 11:50:28');
INSERT INTO `resource_detail` VALUES (12, '90bf66f48f5719d3dd325fe6dde45731', '/instagram/resource_detail/download/', '/root/instagram/resource/', '629357b98f974300a7a2b6e93d344896_thumbnail.png', 0, 0, '2019-04-15 11:50:29', '2019-04-15 11:50:29');
INSERT INTO `resource_detail` VALUES (13, '86184283ddf224d58cc3de0679338134', '/instagram/resource_detail/download/', '/root/instagram/resource/', '2bbf76d28b334753ba03564cd9ed901d_original.png', 0, 0, '2019-04-15 11:51:16', '2019-04-15 11:51:16');
INSERT INTO `resource_detail` VALUES (14, '083f9b7572d77dc09a8587fdcec78259', '/instagram/resource_detail/download/', '/root/instagram/resource/', '2bbf76d28b334753ba03564cd9ed901d_watermark.png', 0, 0, '2019-04-15 11:51:16', '2019-04-15 11:51:16');
INSERT INTO `resource_detail` VALUES (15, '982d02d320f743fac88811658393ab7c', '/instagram/resource_detail/download/', '/root/instagram/resource/', '2bbf76d28b334753ba03564cd9ed901d_thumbnail.png', 0, 0, '2019-04-15 11:51:16', '2019-04-15 11:51:16');
INSERT INTO `resource_detail` VALUES (16, '28ba9f16f3d6077d9b9637d1b5e6cb2f', '/instagram/resource_detail/download/', '/root/instagram/resource/', '46aa36ca40f342168e362ec63b61ec1e.png', 0, 0, '2019-04-15 13:04:06', '2019-04-15 13:04:06');
INSERT INTO `resource_detail` VALUES (17, 'dccf64e503b3162d625c1520dd3d9f70', '/instagram/resource_detail/download/', '/root/instagram/resource/', '909f54558ff0426b822f0c1f579b2e78.png', 0, 0, '2019-04-15 13:04:07', '2019-04-15 13:04:07');
INSERT INTO `resource_detail` VALUES (18, '44be0a7614a8c7dd36030c24b7ed8a52', '/instagram/resource_detail/download/', '/root/instagram/resource/', '1650b8b7fff2463c9c96feb59bc2b17d.png', 0, 0, '2019-04-15 13:04:07', '2019-04-15 13:04:07');
INSERT INTO `resource_detail` VALUES (19, 'dccf64e503b3162d625c1520dd3d9f70', '/instagram/resource_detail/download/', '/root/instagram/resource/', '047ba9ab8e1142418ce483a0838cb2ed.png', 0, 0, '2019-04-15 13:04:59', '2019-04-15 13:04:59');
INSERT INTO `resource_detail` VALUES (20, '44be0a7614a8c7dd36030c24b7ed8a52', '/instagram/resource_detail/download/', '/root/instagram/resource/', '7f7a96cbdb8e4992ae493b62369264c2.png', 0, 0, '2019-04-15 13:04:59', '2019-04-15 13:04:59');
INSERT INTO `resource_detail` VALUES (21, 'dccf64e503b3162d625c1520dd3d9f70', '/instagram/resource_detail/download/', '/root/instagram/resource/', 'fcd0265c880346d3a849d9d58be1542a.png', 0, 0, '2019-04-15 13:05:06', '2019-04-15 13:05:06');
INSERT INTO `resource_detail` VALUES (22, '44be0a7614a8c7dd36030c24b7ed8a52', '/instagram/resource_detail/download/', '/root/instagram/resource/', '0408580b2c1846a48f9b8098e36a2e55.png', 0, 0, '2019-04-15 13:05:06', '2019-04-15 13:05:06');
INSERT INTO `resource_detail` VALUES (23, '1e6527891894647ff15279918fe094a5', '/instagram/resource_detail/download/', '/root/instagram/resource/', 'b98dcbb5facb4ed599a35f69e863bd24.png', 0, 0, '2019-04-15 22:48:12', '2019-04-15 22:48:12');
INSERT INTO `resource_detail` VALUES (24, 'b3dac0a511b57676828f182cd9993912', '/instagram/resource_detail/download/', '/root/instagram/resource/', 'ebe4fb5b31c5454bbcee4c5b4719b6cf.png', 0, 0, '2019-04-15 22:48:13', '2019-04-15 22:48:13');
INSERT INTO `resource_detail` VALUES (25, 'bc7a78c4748073d65fa229dbbeef739c', '/instagram/resource_detail/download/', '/root/instagram/resource/', 'd304c545dcb5481685eeb5a377b2cb08.png', 0, 0, '2019-04-15 22:48:13', '2019-04-15 22:48:13');
INSERT INTO `resource_detail` VALUES (26, 'eafd61247deb393eea0e569a5079e4b9', '/instagram/resource_detail/download/', '/root/instagram/resource/', 'a99cb65336a0466db95224240e534e75.png', 0, 0, '2019-04-15 23:00:26', '2019-04-15 23:00:26');
INSERT INTO `resource_detail` VALUES (27, '8d4eb44e63dad930e67ba6fada5f260b', '/instagram/resource_detail/download/', '/root/instagram/resource/', '9a7eb64918e14b4f83a37848ce79c330.png', 0, 0, '2019-04-15 23:00:27', '2019-04-15 23:00:27');
INSERT INTO `resource_detail` VALUES (28, '3efc2fd404e0403be09aba3786bb3c4a', '/instagram/resource_detail/download/', '/root/instagram/resource/', '7e3cce17246f4de3be7dd3de3132b440.png', 0, 0, '2019-04-15 23:00:27', '2019-04-15 23:00:27');
INSERT INTO `resource_detail` VALUES (29, '61e0be0aea64db1a1c116951fdb13714', '/instagram/resource_detail/download/', '/root/instagram/resource/', '43e03ec2d000461987c84a4801f2638e.jpg', 0, 0, '2019-04-15 23:01:11', '2019-04-15 23:01:11');
INSERT INTO `resource_detail` VALUES (30, 'b4e431c4be1d35b5d48b6b6386af2b6c', '/instagram/resource_detail/download/', '/root/instagram/resource/', '568b48d2313b49cf9a4725eab4631973.jpg', 0, 0, '2019-04-15 23:01:11', '2019-04-15 23:01:11');
INSERT INTO `resource_detail` VALUES (31, '0ce4630244ee8a27e0eb81221ddd515a', '/instagram/resource_detail/download/', '/root/instagram/resource/', 'c1a6a5ce64d14833aa55f177b85c0e2f.jpg', 0, 0, '2019-04-15 23:01:11', '2019-04-15 23:01:11');
INSERT INTO `resource_detail` VALUES (32, '6b2d199cd717e64981a9cd689009f7cd', '/instagram/resource_detail/download/', '/root/instagram/resource/', '60f0bc6d57b949dc88902a7097a2fb40.jpg', 0, 0, '2019-04-15 23:01:50', '2019-04-15 23:01:50');
INSERT INTO `resource_detail` VALUES (33, '97fc24b1f6a2decd2381dab6660b01b5', '/instagram/resource_detail/download/', '/root/instagram/resource/', '7463a586ff1345b4acf4fa652adb5a6c.jpg', 0, 0, '2019-04-15 23:01:50', '2019-04-15 23:01:50');
INSERT INTO `resource_detail` VALUES (34, '03cbec7037b1c7460763d0151daa01ee', '/instagram/resource_detail/download/', '/root/instagram/resource/', '73536115405a4386aec9417feaf6d0e8.jpg', 0, 0, '2019-04-15 23:01:50', '2019-04-15 23:01:50');
INSERT INTO `resource_detail` VALUES (35, 'd136b7973136f207d62cb83267488127', '/instagram/resource_detail/download/', '/root/instagram/resource/', 'a07524d62dcd4b07886fbd71c451fb8a.jpg', 0, 0, '2019-04-15 23:06:12', '2019-04-15 23:06:12');
INSERT INTO `resource_detail` VALUES (36, 'aa501d7a069fe1133d8f69caa5f35ee8', '/instagram/resource_detail/download/', '/root/instagram/resource/', 'c95472f541b74b86a92270c72644291e.jpg', 0, 0, '2019-04-15 23:06:13', '2019-04-15 23:06:13');
INSERT INTO `resource_detail` VALUES (37, '841c10e49656968b11051032f95e3571', '/instagram/resource_detail/download/', '/root/instagram/resource/', 'b0d3637108e746edbdf11f93ffcbf69d.jpg', 0, 0, '2019-04-15 23:06:13', '2019-04-15 23:06:13');
INSERT INTO `resource_detail` VALUES (38, '86184283ddf224d58cc3de0679338134', '/instagram/download/', '/root/instagram/resource/', 'b6fcac2cab064c2482d56cb542e01c88.png', 0, 0, '2019-04-27 18:28:45', '2019-04-27 18:28:45');
INSERT INTO `resource_detail` VALUES (39, 'c688a9261e2ae4755eedfba9af1933bb', '/instagram/download/', '/root/instagram/resource/', '526bdb5fb7af48d68b476a5788200d8a.png', 0, 0, '2019-04-27 18:30:08', '2019-04-27 18:30:08');
INSERT INTO `resource_detail` VALUES (40, '55d0286a8a485a31363bd041afd71bbe', '/instagram/download/', '/root/instagram/resource/', 'd30664adc8154134834a2677ec915c8f.jpg', 0, 0, '2019-04-27 18:58:01', '2019-04-27 18:58:01');
INSERT INTO `resource_detail` VALUES (41, 'ba81be2da7d42f6b6ff3f781c8f07810', '/instagram/download/', '/root/instagram/resource/', 'aac1cd17fc43476d87030d5984025d05.jpg', 0, 0, '2019-04-27 18:58:02', '2019-04-27 18:58:02');
INSERT INTO `resource_detail` VALUES (42, '98da4bfc7f25306cc07e12ae18f7a154', '/instagram/download/', '/root/instagram/resource/', '501259f98c3047ea9d4d43b7da5b4878.jpg', 0, 0, '2019-04-27 18:58:02', '2019-04-27 18:58:02');

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色名称',
  `description` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role
-- ----------------------------
INSERT INTO `role` VALUES (1, 'vip', 'VIP用户', 0, 0, '2019-03-28 00:09:10', '2019-03-28 00:09:10');
INSERT INTO `role` VALUES (2, 'user', '普通用户', 0, 0, '2019-03-28 00:09:10', '2019-03-28 00:09:10');

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `permission_id` int(11) NULL DEFAULT NULL COMMENT '权限id',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of role_permission
-- ----------------------------

-- ----------------------------
-- Table structure for topic
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '话题名称',
  `follow_num` int(11) NULL DEFAULT NULL COMMENT '关注人数',
  `post_num` int(11) NULL DEFAULT NULL COMMENT '贴子数量',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '话题表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of topic
-- ----------------------------
INSERT INTO `topic` VALUES (1, '测试话题', 0, 2, 0, 1, '2019-04-28 12:17:08', '2019-04-28 12:25:22');
INSERT INTO `topic` VALUES (2, '测试话题2', 0, 1, 0, 1, '2019-04-28 12:25:54', '2019-04-28 12:25:54');

-- ----------------------------
-- Table structure for topic_follow
-- ----------------------------
DROP TABLE IF EXISTS `topic_follow`;
CREATE TABLE `topic_follow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `topic_id` int(11) NULL DEFAULT NULL COMMENT '话题id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `follow` tinyint(1) NULL DEFAULT NULL COMMENT '是否关注',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '话题关注表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of topic_follow
-- ----------------------------

-- ----------------------------
-- Table structure for topic_post
-- ----------------------------
DROP TABLE IF EXISTS `topic_post`;
CREATE TABLE `topic_post`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `topic_id` int(11) NULL DEFAULT NULL COMMENT '话题id',
  `post_id` int(11) NULL DEFAULT NULL COMMENT '贴子id',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '话题贴子表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of topic_post
-- ----------------------------
INSERT INTO `topic_post` VALUES (1, 1, 5, 0, 0, '2019-04-28 12:17:08', '2019-04-28 12:17:08');
INSERT INTO `topic_post` VALUES (2, 1, 6, 0, 0, '2019-04-28 12:25:22', '2019-04-28 12:25:22');
INSERT INTO `topic_post` VALUES (3, 2, 7, 0, 0, '2019-04-28 12:25:54', '2019-04-28 12:25:54');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `disabled` tinyint(1) NULL DEFAULT NULL COMMENT '是否禁用',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, '2098629243@qq.com', '$2a$10$AutNAhDWfTRqC8e9GqHD6e2/bn6TcwFkRgm1aK.h6otu3VOyrHhTe', 0, 0, 0, '2019-03-27 17:49:00', '2019-04-01 19:06:16');
INSERT INTO `user` VALUES (2, '178550136@qq.com', '$2a$10$VzDInub06fQHwBDXDpAvy.raHWDP9rN2N4.vplLl2L8W0mruIlpOG', 0, 0, 0, '2019-04-07 22:49:05', '2019-04-07 22:49:05');
INSERT INTO `user` VALUES (3, 'qwe178550136@163.com', '$2a$10$MclM8pn6TKVaPczPxHspvOhFR4Em3cknxB2D4OHy..Fqhr/WZuz9a', 0, 0, 4, '2019-04-27 17:42:44', '2019-04-29 13:28:39');

-- ----------------------------
-- Table structure for user_collection
-- ----------------------------
DROP TABLE IF EXISTS `user_collection`;
CREATE TABLE `user_collection`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `post_id` int(11) NULL DEFAULT NULL COMMENT '贴子id',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户收藏表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_collection
-- ----------------------------
INSERT INTO `user_collection` VALUES (1, 2, 1, 0, 0, '2019-04-21 08:48:26', '2019-04-21 08:48:28');
INSERT INTO `user_collection` VALUES (2, 2, 2, 0, 0, '2019-04-21 08:52:51', '2019-04-21 08:52:54');

-- ----------------------------
-- Table structure for user_follow
-- ----------------------------
DROP TABLE IF EXISTS `user_follow`;
CREATE TABLE `user_follow`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '关注者id',
  `follow_id` int(11) NULL DEFAULT NULL COMMENT '被关注者id',
  `follow` tinyint(1) NULL DEFAULT NULL COMMENT '是否关注',
  `each_other` tinyint(1) NULL DEFAULT NULL COMMENT '是否互粉',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户关注表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_follow
-- ----------------------------
INSERT INTO `user_follow` VALUES (1, 1, 2, 1, 1, 0, 0, '2019-04-09 21:24:13', '2019-04-09 21:24:13');
INSERT INTO `user_follow` VALUES (2, 2, 1, 1, 1, 0, 0, '2019-04-09 21:24:13', '2019-04-09 21:24:13');
INSERT INTO `user_follow` VALUES (3, 3, 2, 1, 0, 0, 2, '2019-04-27 18:14:26', '2019-04-27 18:23:58');

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `nickname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `gender` tinyint(1) NULL DEFAULT NULL COMMENT '性别',
  `profile_photo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像图片',
  `website` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '网站',
  `bio` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个人简介',
  `following` int(11) NULL DEFAULT NULL COMMENT '我关注的',
  `follower` int(11) NULL DEFAULT NULL COMMENT '关注我的',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES (1, 1, '机器人', 0, '9a7eb64918e14b4f83a37848ce79c330.png', 'https://www.google.com', '大家好，我是机器人。', 1, 1, 0, 0, '2019-04-07 22:49:05', '2019-04-07 22:49:05');
INSERT INTO `user_info` VALUES (2, 2, '不属于你的康仔的', 0, '9a7eb64918e14b4f83a37848ce79c330.png', 'https://www.baidu.com', '大家好，我是康仔。', 1, 2, 0, 3, '2019-03-27 17:49:00', '2019-04-27 18:23:58');
INSERT INTO `user_info` VALUES (3, 3, '三石的爸爸', 1, '526bdb5fb7af48d68b476a5788200d8a.png', 'www.163.com.cn', '大家好我是三石的爸爸', 1, 0, 0, 8, '2019-04-27 17:42:44', '2019-04-28 21:45:27');

-- ----------------------------
-- Table structure for user_role
-- ----------------------------
DROP TABLE IF EXISTS `user_role`;
CREATE TABLE `user_role`  (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `user_id` int(11) NULL DEFAULT NULL COMMENT '用户id',
  `role_id` int(11) NULL DEFAULT NULL COMMENT '角色id',
  `deleted` tinyint(1) NULL DEFAULT NULL COMMENT '是否删除',
  `version` int(11) NULL DEFAULT NULL COMMENT '乐观锁',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modified_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_role
-- ----------------------------
INSERT INTO `user_role` VALUES (1, 1, 1, 0, 0, '2019-03-28 00:08:36', '2019-03-28 00:08:36');
INSERT INTO `user_role` VALUES (2, 2, 2, 0, 0, '2019-03-28 00:08:36', '2019-03-28 00:08:36');
INSERT INTO `user_role` VALUES (3, 3, 2, 0, 0, '2019-04-27 17:42:44', '2019-04-27 17:42:44');

SET FOREIGN_KEY_CHECKS = 1;
