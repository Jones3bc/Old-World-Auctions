set echo on
create table USER_INFO(
	userID varchar2(15)NOT NULL,
	password varchar2(20) NOT NULL,
	constraint LOG_pk primary key (userID)
);

commit;
--desc is used to show the table and all of its contents
desc USER_INFO

--Commented out section for the account info
--create table ACC_INFO(
--	Fname varchar2(15),
--	Lname varchar2(15),
--	constraint ACC_INFO_fk foreign key (Fname) references USER_INFO(userID)
--);

--commit; 
--desc ACC_INFO

create table Auction_item(
	item_name varchar2(40),
	item_condition varchar2(30),
	item_price number,
	item_color varchar2(15),
	manufactured_yr number (4),
	aucStartTime date;
	aucEndTime date;
	constraint ITEM_INFO_pk primary key (item_name)
);

commit;
desc ITEM_INFO

purge recyclebin;
