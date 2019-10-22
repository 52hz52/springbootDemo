## springboot study

## 资料
[Spring 文档](https://spring.io/guides)      
[参考网站](https://elasticsearch.cn/)   
[Github OAUTE](https://developer.github.com/apps/building-oauth-apps/)   
[MAVEN仓库](https://mvnrepository.com)    
[H2数据库](http://www.h2database.com/)     
[MAVEN3+ 使用 flyway 需要修复](https://flywaydb.org/documentation/maven/repair.html)   
[SpringMVC](https://docs.spring.io/spring/docs/5.0.3.RELEASE/spring-framework-reference/web.html#mvc-handlermapping-interceptor)   

## 工具
[LomBok](https://projectlombok.org/setup/maven)   
[flyway](https://flywaydb.org/getstarted/firststeps/maven)   

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

CREATE  TABLE QUESTION(
    ID INT auto_increment primary  key ,
    TITLE VARCHAR(50),
    desciption TEXT,
    GMT_CREATE BIGINT,
    GMT_MODIFIED BIGINT,
    CREATOR INT,
    COMMENT_COUNT INT DEFAULT 0,
    VIEW_COUNT INT DEFAULT 0,
    LIKE_COUNT INT DEFAULT 0,
    TAG VARCHAR(256)
)
```
## 
```bash
mvn flyway:repair 
1mvn flyway:migrate
mvn -Dmybatis.generator.overwrite=true mybatis-generator:generate
```