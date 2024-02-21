set echo on
create table USERS(
	username varchar(30) PRIMARY KEY,
	password varchar(30) NOT NULL,
    	userID varchar(40)
);

commit;
--desc is used to show the table and all of its contents
desc USERS;

create table AUCTION_ITEMS(
	name varchar(40),
	description varchar(30),
	currentBid varchar(30),
   	 itemID varchar(40),
	aucStartTime datetime,
	aucEndTime datetime,
	sellerUser varchar(30),
	bidderUser varchar(30),
   	 image blob,
	FOREIGN KEY(sellerUser) REFERENCES USERS(userID),
	FOREIGN KEY(bidderUser) REFERENCES USERS(userID)
);

commit;
desc AUCTION_ITEMS;

--credit 0 or 1 for boolean
create table PAYMENT_METHODS(
    paymentID varchar(40),
    credit integer,
    cardNumber varchar(30),
    expirationMonth number,
    expirationYear number,
    cvv number,
    userID varchar(40),
    FOREIGN KEY (userID) REFERENCES USERS(userID)
);

commit;
desc PAYMENT_METHODS;

purge recyclebin;
