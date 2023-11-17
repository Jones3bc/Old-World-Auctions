set echo on
--drop table LOGIN_INFO cascade constraints;
create table LOGIN_INFO(
	userID varchar2(15)NOT NULL,
	password varchar2(20) NOT NULL,
	constraint LOG_pk primary key (userID)
);

commit;
--desc is used to show the table and all of its contents
desc LOGIN_INFO

--drop table USER_INFO CASCADE CONSTRAINTS;
create table USER_INFO(
	Fname varchar2(15),
	Lname varchar2(15),
	email varchar2(30) NOT NULL,
	Sex varchar2(1) CHECK (Sex = 'M' or Sex = 'F' or Sex = 'N'),
	constraint USER_INFO_fk foreign key (email) references LOGIN_INFO(userID)
);

commit; 
desc USER_INFO

--drop table ITEM_INFO cascade constraints;
create table Auction_item(
	item_name varchar2(40),
	item_description varchar2(100),
	item_price number,
	item_color varchar2(15),
	manufactured_yr number (4),
	
	
	constraint ITEM_INFO_pk primary key (item_name)
);

commit;
desc ITEM_INFO

purge recyclebin;