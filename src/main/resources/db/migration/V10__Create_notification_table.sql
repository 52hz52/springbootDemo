create table notification
(
	id bigint auto_increment,
	notifier bigint not null,
	receive bigint not null,
	outerId bigint not null,
	type int not null,
	gmt_create bigint not null,
	status int default 0 not null,
	outer_title  varchar(256),
	notifier_name varchar(100),
	constraint notification_pk
	primary key (id)
);

