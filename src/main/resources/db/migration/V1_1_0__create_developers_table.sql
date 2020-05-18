create schema employees;
create table users
(
	id int auto_increment,
    version int NOT NULL ,
	first_name varchar(25) not null,
	sur_name varchar(25) not null,
	position varchar(25) not null,
	github_profile_url varchar(200) not null ,
	constraint DEVELOPERS_PK primary key (id)
);
