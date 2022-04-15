CREATE TABLE OPERATOR (
    operatorId VARCHAR(128) NOT NULL,
    name VARCHAR(32) NOT NULL COLLATE NOCASE,
    phoneNo VARCHAR(32),
    location VARCHAR(32),
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

INSERT INTO OPERATOR VALUES('16145a87-a62a-469d-b81b-19c6177b2018','Ævintýraferðir','Vík í Mýrdal', '555-1234');
INSERT INTO OPERATOR VALUES('2ae6c012-b639-4cd8-81c6-88ee4edf5169', 'Arctic Adventures', '555-0000','Akureyri');
INSERT INTO OPERATOR VALUES('e7d0b4c0-93e8-432b-9d19-6ec0f056ee90', 'Iceland Travels', '521-2630','Reykjavík');
INSERT INTO OPERATOR VALUES('08e9604b-97b8-4bc9-b17c-95b4a9a2400e', 'Iceland Excursions', '567-8910','Akureyri');
INSERT INTO OPERATOR VALUES('d5aa025b-fea2-4c9c-ad0b-6a497fc4edc0', 'All around tours', '555-2345','Reykjavík');
INSERT INTO OPERATOR VALUES('55a44c80-b5ee-4725-bd35-860f498ee2ed', 'Country Tours', '500-1234','Vík í Mýrdal');

INSERT INTO DAYTRIP VALUES('23c8e048-ecc6-45a4-acec-2e01ef352f0a', 'Jöklaferð', 5000, 'Jöklaferð uppá Vatnajökul','Vík í Mýrdal',DATE('2022-05-01'), DATETIME('2022-05-01 09:00:00'), DATETIME('2022-05-01 12:00:00'),  10, 3, 10, 'Ævintýraferðir');
INSERT INTO DAYTRIP VALUES('0734277a-d6af-4578-aa89-fc3c1ba9e03c', 'Hestatúr', 7000, '5 klst reiðferð um sveitina','Vík í Mýrdal',DATE('2022-07-15'), DATETIME('2022-07-15 12:00:00'), DATETIME('2022-07-15 17:00:00'),  10, 1, 15, 'Ævintýraferðir');
INSERT INTO DAYTRIP VALUES('662b1aa3-edc2-44ea-b4a2-32ee8f6a0d17', 'Buggy ferð', 6000, '3 klst buggy ferð','Vík í Mýrdal',DATE('2022-10-10'), DATETIME('2022-10-10 13:00:00'), DATETIME('2022-10-10 16:00:00'),  17, 1, 5, 'Ævintýraferðir');
INSERT INTO DAYTRIP VALUES('d9bd1368-df4f-49ce-907a-f2efab6b2fb5', 'Kajak day trip', 10000, '3 hour kajak sailing','Akureyri',DATE('2022-09-20'), DATETIME('2022-09-20 10:00:00'), DATETIME('2022-09-20 13:00:00'),  10, 1, 4, 'Arctic Adventures');
INSERT INTO DAYTRIP VALUES('afb7a39e-8a86-43c8-af92-7e547f27619c', 'Mountain hike', 9000,'4 hour mountain hike','Akureyri',DATE('2022-09-10'), DATETIME('2022-09-10 14:00:00'), DATETIME('2022-09-10 18:00:00'), 15, 3, 15, 'Arctic Adventures');
INSERT INTO DAYTRIP VALUES('a9fa50da-2211-4c58-8616-f4dcc43835e6', 'Snorkling', 9000, '4 hour snorkling adventure','Akureyri',DATE('2022-06-08'), DATETIME('2022-06-08 12:00:00'), DATETIME('2022-06-08 16:00:00'),  15, 1, 5, 'Arctic Adventures');
INSERT INTO DAYTRIP VALUES('2332bd56-9124-479c-89a6-e654a2b0c1ab', 'Golden circle', 11000, '11 hour tour that covers Icelands most visited places','Reykjavík',DATE('2022-11-13'), DATETIME('2022-11-13 09:00:00'), DATETIME('2022-11-13 20:00:00'), 1, 1, 15, 'Iceland Travels');
INSERT INTO DAYTRIP VALUES('334df690-4edf-4bfa-9926-db57ebab4fa8', 'Northern lights tour', 12000, '3-5 hours, stop at different locations and see the northern light during the night','Reykjavík',DATE('2022-05-25'), DATETIME('2022-05-25 11:00:00'), DATETIME('2022-05-25 16:00:00'), 0, 1, 10, 'Iceland Travels');
INSERT INTO DAYTRIP VALUES('05ec1500-ba64-41bf-bf1c-3e21fc229bf3', 'Snæfellsnes round trip', 1000, 'Take a round trip around the beautiful Snæfellsnes','Reykjavík',DATE('2022-08-16'), DATETIME('2022-08-16 12:00:00'), DATETIME('2022-08-16 17:00:00'), 0, 1, 5, 'Iceland Travels');
INSERT INTO DAYTRIP VALUES('76e3aee4-a8e3-4bff-b1d4-d0c89cac6512', 'Waterfall excursion', 8000, '7 hour road trip to see Icelands most visited waterfalls','Akureyri',DATE('2022-07-05'), DATETIME('2022-07-05 09:00:00'), DATETIME('2022-07-05 16:00:00'), 5, 2, 5, 'Iceland Excursions');
INSERT INTO DAYTRIP VALUES('a129539a-bd76-4059-b478-ec69521aaadb', 'Whale watching ', 7000, '3 hour tour boat tour','Akureyri',DATE('2022-11-23'), DATETIME('2022-11-23 14:00:00'), DATETIME('2022-11-23 17:00:00'), 10, 1, 6, 'Iceland Excursions');
INSERT INTO DAYTRIP VALUES('58037642-f2f1-4c3c-bae7-f2f2ec660b4d', 'Hot spring tour', 5000, '5 hour road trip to see hotsprings','Akureyri',DATE('2022-08-14'), DATETIME('2022-08-14 15:00:00'), DATETIME('2022-08-14 12:00:00'), 10, 1, 6, 'Iceland Excursions');
INSERT INTO DAYTRIP VALUES('4368d8ed-6cd3-47fc-9a05-190872bb21ee', 'Volcano hike', 10000, '5 hour walking tour to see Icelands volcanic wonders','Reykjavík',DATE('2022-06-02'), DATETIME('2022-06-02 12:00:00'), DATETIME('2022-06-02 17:00:00'), 10, 4, 10, 'All around tours');
INSERT INTO DAYTRIP VALUES('92eba69c-d797-4ab9-82a8-afaa705c7a76', 'The Lava Tunnel', 12000, '3 hour adventure and visit the lava tunnel in Raufarhólshellir','Reykjavík',DATE('2022-07-17'), DATETIME('2022-07-17 16:00:00'), DATETIME('2022-07-17 19:00:00'), 10, 4, 6, 'All around tours');
INSERT INTO DAYTRIP VALUES('34e86ddb-4dbe-4869-b726-a6d0f953d91a', 'Blue Lagoon', 15000, '5 hour trip to the most relaxing lagoon in Iceland','Reykjavík',DATE('2022-08-28'), DATETIME('2022-08-28 17:00:00'), DATETIME('2022-08-28 22:00:00'), 5, 1, 5, 'All around tours');
INSERT INTO DAYTRIP VALUES('41eb56d5-52f8-407f-8354-bdb4bcbddef2', 'Ice caving', 15000, '3 hour ice cave adventure','Vík í Mýrdal',DATE('2022-11-03'), DATETIME('2022-11-03 16:00:00'), DATETIME('2022-11-03 19:00:00'), 15, 3, 15, 'Country Tours');
INSERT INTO DAYTRIP VALUES('df3e1fde-55a5-445a-9a8b-b2acbc5bb9ce', 'Paragliding', 20000, '2 hours of adrenaline rush','Vík í Mýrdal',DATE('2022-08-26'), DATETIME('2022-08-26 15:00:00'), DATETIME('2022-08-26 17:00:00'), 18, 2, 4, 'Country Tours');
INSERT INTO DAYTRIP VALUES('f2167055-02d8-4707-a355-80c3a69e051f', 'Black beach horse riding', 11000, '4 hours of horese back riding on the famous Black beach','Vík í Mýrdal',DATE('2022-06-06'), DATETIME('2022-06-06 16:00:00'), DATETIME('2022-06-06 20:00:00'), 10, 3, 5, 'Country Tours');
