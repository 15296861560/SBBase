## SpringBoot

##部署
 - Git
 - JDK
 - Maven
 - MySQL
 ##步骤
 - yum update
 - yum install git
 - mkdir App
 - cd App
 - git clone https://github.com/15296861560/SBBase.git
 - yum install maven
 - mvn -v
 - mvn compile package
 - more src/main/resources/application.properties
 - cp src/main/resources/application.properties src/main/resources/application-production.properties
 - vim src/main/resources/application-production.properties
 - mvn package
 - java -jar -Dspring.profile.active=production target/demo-0.0.1-SNAPSHOT.jar
 - ps -aux | grep-java
 - git pull

## 资料
[Spring 文档](https://spring.io/guides)

[Spring 外部文档](https://spring.io/guides/gs/serving-web-content/)

[社区文档](http://localhost:8887/)(http://community.lgy.life)

[GitHub deploy key文档](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)

[BootStrap文档](https://v3.bootcss.com/getting-started/)

[GitHub OAuth文档](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)

[Spring](https://docs.spring.io/spring-boot/docs/current/reference/html/)

[菜鸟教程](https://www.runoob.com/)

[Thymeleaf](https://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html)

[Markdown 插件](https://pandao.github.io/editor.md/)

##工具
[Git](https://git-scm.com/download)

[Visual Paradigm](https://www.visual-paradigm.com/cn/)

[lombok](https://projectlombok.org/)

##数据库脚本

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account_id` varchar(100) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `token` char(36) DEFAULT NULL,
  `gmt_create` bigint(19) DEFAULT NULL,
  `gmt_modified` bigint(19) DEFAULT NULL,
  `bio` varchar(255) DEFAULT NULL,
  `avatar_url` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;



SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 NOT NULL,
  `description` longtext CHARACTER SET utf8mb4 NOT NULL,
  `GMT_CREATE` bigint(19) DEFAULT NULL,
  `GMT_MODIFIED` bigint(19) DEFAULT NULL,
  `TAG` varchar(255) CHARACTER SET utf8mb4 NOT NULL,
  `CREATOR` int(10) NOT NULL,
  `COMMENT_COUNT` int(10) NOT NULL DEFAULT '0',
  `VIEW_COUNT` int(10) NOT NULL DEFAULT '0',
  `LIKE_COUNT` int(10) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` bigint(19) NOT NULL AUTO_INCREMENT,
  `PARENT_ID` int(10) DEFAULT NULL,
  `TYPE` int(10) DEFAULT NULL,
  `COMMENTATOR` int(10) DEFAULT NULL,
  `GMT_CREATE` bigint(19) DEFAULT NULL,
  `GMT_MODIFIED` bigint(19) DEFAULT NULL,
  `LIKE_COUNT` bigint(19) DEFAULT NULL,
  `CONTENT` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for notification
-- ----------------------------
DROP TABLE IF EXISTS `notification`;
CREATE TABLE `notification` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `NOTIFIER` int(10) NOT NULL,
  `RECEVIER` int(10) DEFAULT NULL,
  `OUTER_ID` int(10) DEFAULT NULL,
  `TYPE` int(10) DEFAULT NULL,
  `GMT_CREATE` bigint(19) DEFAULT NULL,
  `STATUS` int(10) DEFAULT '0',
  `NOTIFIER_NAME` varchar(100) CHARACTER SET utf8mb4 DEFAULT NULL,
  `OUTER_TITLE` varchar(255) CHARACTER SET utf8mb4 DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;





