CREATE TABLE OPERATOR (
    operatorId VARCHAR(128) NOT NULL,
    name VARCHAR(32) NOT NULL COLLATE NOCASE,
    phoneNo VARCHAR(32),
    location VARCHAR(32),
    localCode INT,
    PRIMARY KEY(operatorId)
);

CREATE TABLE DAYTRIP (
    dayTripId VARCHAR(128) NOT NULL,
    name VARCHAR(32) NOT NULL COLLATE NOCASE, 
    price REAL(32), 
    description VARCHAR(255), 
    location VARCHAR(32), 
    date DATE NOT NULL, 
    timeStart TIMESTAMP, 
    timeEnd TIMESTAMP, 
    ageLimit INT, 
    difficulty INT, 
    capacity INT, 
    operatorId VARCHAR(32), 
    PRIMARY KEY(dayTripId), 
    FOREIGN KEY(operatorId) REFERENCES OPERATOR(operatorId)
);

CREATE TABLE BOOKING (
    bookingId VARCHAR(128) NOT NULL COLLATE NOCASE, 
    clientSSN VARCHAR(10), 
    clientEmail VARCHAR(32), 
    clientPhoneNumber VARCHAR(32), 
    clientCount INT, 
    date DATE NOT NULL, 
    isPaid BOOLEAN, 
    dayTripId VARCHAR(128), 
    PRIMARY KEY(bookingId), 
    FOREIGN KEY(dayTripId) REFERENCES DAYTRIP(dayTripId)
);

CREATE TABLE REVIEW (
    rating INT NOT NULL CHECK(rating BETWEEN 1 AND 5), 
    review VARCHAR(255), 
    date DATE NOT NULL, 
    clientSSN VARCHAR(10), 
    dayTripId VARCHAR(128),
    PRIMARY KEY(clientSSN, dayTripId),
    FOREIGN KEY(clientSSN, dayTripId) REFERENCES BOOKING(clientSSN, dayTripId)
);

INSERT INTO OPERATOR VALUES('16145a87-a62a-469d-b81b-19c6177b2018', 'Ævintýraferðir', '555-7895', 'Ísafjörður', 1);
INSERT INTO OPERATOR VALUES('374962ca-e40e-41ae-9e8c-9569070fce02', 'Hvalaskoðunin Ýsa', '565-6363', 'Ísafjörður', 1);
INSERT INTO OPERATOR VALUES('d87ee834-bf0b-11ec-9d64-0242ac120002', 'Bjarnaferðir', '555-1234', 'Bolungarvík', 1);
INSERT INTO OPERATOR VALUES('e4c8ce30-b587-4364-aba1-0db5de194aea', 'Icelandic Smoketrips', '589-8989', 'Reykjarfjörður', 1);
INSERT INTO OPERATOR VALUES('09033b53-2ba7-4132-91d1-ad84b8fea245', 'Country Tours', '500-1234', 'Stykkishólmur', 2);
INSERT INTO OPERATOR VALUES('5dbf1cd9-1324-43f7-8786-312296c98343', 'Ólafsferðir', '545-8521', 'Ólafsvík', 2);
INSERT INTO OPERATOR VALUES('8c0fd3fb-b458-4557-a9d1-4bbf2bbac0a5', 'Jöklagarpar ehf.', '565-3434', 'Arnarstapi', 2);
INSERT INTO OPERATOR VALUES('f4b33e38-8c24-48d3-8662-f6569f222b09', 'Iceland Travels', '521-2630', 'Reykjavík', 3);
INSERT INTO OPERATOR VALUES('e76ee3f1-8268-45d9-839b-a616bd7ec772', 'All around tours', '555-2345', 'Reykjavík', 3);
INSERT INTO OPERATOR VALUES('eca85667-415f-443b-ba5f-9f3af534cd89', 'Reykjavik travelling', '577-2775', 'Reykjavík', 3);
INSERT INTO OPERATOR VALUES('2d6ac083-8bc6-475f-98a5-0eff554bca8b', 'Reykjavik experience', '555-2225', 'Reykjavík', 3);
INSERT INTO OPERATOR VALUES('388dd631-d881-4e12-be9c-132bb2653d9f', 'Army tours', '555-2345', 'Keflavík', 3);
INSERT INTO OPERATOR VALUES('f5e8529a-1f45-4d9a-8800-48ebe13cb4ef', 'Northern lights wow', '588-2888', 'Selfoss', 3);
INSERT INTO OPERATOR VALUES('fed7c855-8225-4c95-bb48-b0957eec3e23', 'Vestmanna-Boat tours', '589-6148', 'Vestmannaeyjar', 4);
INSERT INTO OPERATOR VALUES('d5e17676-caea-41f0-b6a3-ba5014ddf10d', 'Swing along', '569-6969', 'Vestmannaeyjar', 4);
INSERT INTO OPERATOR VALUES('6485ea70-8a0d-498f-93c5-e4c86416d155', 'Ferðir Egils', '578-9876', 'Egilsstaðir', 5);
INSERT INTO OPERATOR VALUES('98db6bcf-4a51-43a2-9b2c-36c102f1b27c', 'Subbuferðir', '545-5454', 'Seyðisfjörður', 5);
INSERT INTO OPERATOR VALUES('d4ca6968-7334-4695-b44f-d25c867de54a', 'Nestrips', '577-6471', 'Neskaupsstaður', 5);
INSERT INTO OPERATOR VALUES('365d4028-a8e0-4c8e-86e5-e62720acb793', 'Arctic Adventures', '555-0000', 'Akureyri', 6);
INSERT INTO OPERATOR VALUES('3f16248c-da08-424b-a12c-e8a216935f5c', 'Iceland Excursions', '567-8910', 'Akureyri', 6);
INSERT INTO OPERATOR VALUES('19f15af0-5d0c-4bd5-8e76-0e08c4906d6a', 'House tours', '566-6610', 'Húsavík', 6);
INSERT INTO OPERATOR VALUES('035cfd9f-7f5c-42e6-863e-393f822344d5', 'Smokehill tours', '551-9050', 'Reykjahlíð', 6);