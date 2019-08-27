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
 - java -jar -Dspring.profile.active=praduction target/demo-0.0.1-SNAPSHOT.jar
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
CREATE TABLE user (
  id int  AUTO_INCREMENT   PRIMARY KEY NOT NULL,
  account_id varchar(100) ,
  name varchar(50) ,
  token char(36) ,
  gmt_create bigint ,
  gmt_modified bigint ,
);
create table question
(
    id int auto_increment primary key,
	title varchar(50),
	description text,
	gmt_create bigint,
	gmt_modified bigint,
	creator int,
	comment_count int,
	view_count int,
	like_count int,
	tag varchar(256)
);


