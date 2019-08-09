## SpringBoot
## 资料
[Spring 文档](https://spring.io/guides)

[Spring 外部文档](https://spring.io/guides/gs/serving-web-content/)

[社区文档](http://localhost:8887/)(http://community.lgy.life)

[GitHub deploy key文档](https://developer.github.com/v3/guides/managing-deploy-keys/#deploy-keys)

[BootStrap文档](https://v3.bootcss.com/getting-started/)

[GitHub OAuth文档](https://developer.github.com/apps/building-oauth-apps/creating-an-oauth-app/)

[Spring](https://docs.spring.io/spring-boot/docs/current/reference/html/)

[菜鸟教程](https://www.runoob.com/)

##工具
[Git](https://git-scm.com/download)

[Visual Paradigm](https://www.visual-paradigm.com/cn/)

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


