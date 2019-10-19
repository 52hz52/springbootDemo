## springboot study

## 资料
[Spring 文档](https://spring.io/guides)      
[参考网站](https://elasticsearch.cn/)   
[Github OAUTE](https://developer.github.com/apps/building-oauth-apps/)   
[MAVEN仓库](https://mvnrepository.com)   
[H2数据库](http://www.h2database.com/)     
[MAVEN3+ 使用 flyway 需要修复 ](https://flywaydb.org/documentation/maven/repair.html)   

## 工具


## 脚本
```sql
create table user
(
	id int auto_increment,
	name varchar(50),
	account_id varchar(255),
	token varchar(36),
	gmt_create bigint,
	gmt_modified bigint,
	constraint user_pk
		primary key (id)
);
```
