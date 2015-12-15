SET DEFINE OFF;

CREATE TABLE BUSINESS(
	BUSINESS_ID VARCHAR(50) NOT NULL,
	FULL_ADDRESS VARCHAR(100),
	HOURS VARCHAR(500),
	OPEN VARCHAR(10),
	CATEGORIES VARCHAR(200),
	CITY VARCHAR(50),
	STATE VARCHAR(10),
	REVIEW_COUNT NUMBER(5),
	NAME VARCHAR(100),
	NEIGHBORHOODS VARCHAR(100),
	STARS NUMBER(2),
	ATTRIBUTES VARCHAR(1500),
	TYPE VARCHAR(10),
	PRIMARY KEY (BUSINESS_ID));


CREATE TABLE USERS(
	USER_ID VARCHAR(30) NOT NULL,
	NAME VARCHAR(100),
	PRIMARY KEY (USER_ID));


CREATE TABLE REVIEW(
	USER_ID VARCHAR (30),
	REVIEW_ID VARCHAR(30) NOT NULL,
	STARS NUMBER(1),
	DATES VARCHAR(10),
	CONTENT CLOB,
	BUSINESS_ID VARCHAR(30),
	PRIMARY KEY (REVIEW_ID),
	FOREIGN KEY (BUSINESS_ID) REFERENCES BUSINESS(BUSINESS_ID)
			ON DELETE CASCADE,
	FOREIGN KEY (USER_ID) REFERENCES USERS (USER_ID)
			ON DELETE CASCADE
);

CREATE TABLE CATEGORIES(
	BUSINESS_ID VARCHAR(30),
	CATEGORIE VARCHAR(50),
	FOREIGN KEY (BUSINESS_ID) REFERENCES BUSINESS (BUSINESS_ID)
			ON DELETE CASCADE
);

CREATE TABLE ATTRIBUTES(
	BUSINESS_ID VARCHAR(30) NOT NULL,
	ATTRIBUTES VARCHAR(50),
	FOREIGN KEY (BUSINESS_ID) REFERENCES BUSINESS (BUSINESS_ID)
			ON DELETE CASCADE
);

CREATE TABLE HOURS(
	BUSINESS_ID VARCHAR(30),
	DAY VARCHAR(10),
	CLOSE TIMESTAMP,
	OPEN TIMESTAMP
);