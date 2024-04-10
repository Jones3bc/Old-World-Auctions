set echo on
create table USERS(
    userID varchar(40) PRIMARY KEY,
	username varchar(30) UNIQUE,
	password varchar(30) NOT NULL
);

commit;
--desc is used to show the table and all of its contents
desc USERS;

create table AUCTION_ITEMS(
	itemID varchar(40) UNIQUE ,
	name varchar(40) NOT NULL,
	category varchar(20),
	description varchar(30),
	currentBid varchar(30),
	aucStartTime datetime,
	aucEndTime datetime,
	sellerID varchar(30) NOT NULL,
	bidderID varchar(30) NOT NULL,
   	image blob,
	FOREIGN KEY(sellerID) REFERENCES USERS(userID),
	FOREIGN KEY(bidderID) REFERENCES USERS(userID)
);

commit;
desc AUCTION_ITEMS;

--credit 0 or 1 for boolean
create table PAYMENT_METHODS(
    paymentID varchar(40),
    credit integer,
    cardNumber varchar(30),
    expirationMonth number NOT NULL,
    expirationYear number NOT NULL,
    cvv number NOT NULL,
    userID varchar(40),
    FOREIGN KEY (paymentID) REFERENCES USERS(userID)
);

commit;
desc PAYMENT_METHODS;

create table WISHLIST(
      itemID varchar(40),
      userID varchar(30),
      currentBid number,
      FOREIGN KEY (itemID) REFERENCES AUCTION_ITEMS(itemID)
);

commit;
desc WISHLIST;

purge recyclebin;
