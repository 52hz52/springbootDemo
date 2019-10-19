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
