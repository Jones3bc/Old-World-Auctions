set echo on
create table USERS(
	username varchar(30) PRIMARY KEY,
	password varchar(30) NOT NULL,
    userID number
);

commit;
--desc is used to show the table and all of its contents
desc USERS;

--Commented out section for the account info
--create table ACC_INFO(
--	Fname varchar2(15),
--	Lname varchar2(15),
--	constraint ACC_INFO_fk foreign key (Fname) references USER_INFO(userID)
--);

--commit; 
--desc ACC_INFO

create table AUCTION_ITEMS(
	name varchar(40),
	description varchar(30),
	currentBid varchar(30),
    	itemID number,
	aucStartTime datetime,
	aucEndTime datetime,
	sellerUser varchar(30),
	bidderUser varchar(30),
    	image blob,
	FOREIGN KEY(sellerUser) REFERENCES USERS(username),
	FOREIGN KEY(bidderUser) REFERENCES USERS(username)
);

commit;
desc AUCTION_ITEMS;

--credit 0 or 1 for boolean
create table PAYMENT_METHODS(
    id varchar(10),
    credit integer,
    cardNumber varchar(30),
    expirationMonth number,
    expirationYear number,
    cvv number,
    userUsername varchar(30),
    FOREIGN KEY (userUsername) REFERENCES USERS(username)
);

purge recyclebin;
