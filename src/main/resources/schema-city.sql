DROP TABLE city IF EXISTS;

CREATE TABLE city (
	id integer not null auto_increment, 
	name varchar(255), 
	province varchar(25), 
	country varchar(25), 
	latitude varchar(25), 
	longitude varchar(25), 
	primary key (id)
	);
	
	
