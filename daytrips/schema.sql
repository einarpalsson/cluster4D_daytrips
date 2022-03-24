CREATE TABLE OPERATOR (
    Name VARCHAR(32) NOT NULL COLLATE NOCASE
    , Location VARCHAR(32)
    , PhoneNo VARCHAR(32)
    , PRIMARY KEY(Name)
);

CREATE TABLE DAYTRIP (
    Name VARCHAR(32) NOT NULL COLLATE NOCASE
    , Date DATE NOT NULL
    , TimeStart VARCHAR(32)
    , TimeEnd VARCHAR(32)
    , Difficulty INT
    , Description VARCHAR(32)
    , AgeLimit INT
    , Price INT(32)
    , Operator VARCHAR(32)
    , Location VARCHAR(32)
    , Capacity INT
    , PRIMARY KEY(Name)
    , FOREIGN KEY(operator) REFERENCES OPERATOR(Name)
);

CREATE TABLE BOOKING (
    ID VARCHAR(32) NOT NULL COLLATE NOCASE
    , Client VARCHAR(32)
    , DayTrip VARCHAR(32) 
    , Date DATE NOT NULL
    , IsPaid INT
    , ClientEmail VARCHAR(32)
    , ClientPhoneNumber VARCHAR(32)
    , ClientCount INT
    , PRIMARY KEY(ID)
    , FOREIGN KEY(DayTrip) REFERENCES DAYTRIP(Name)
);

CREATE TABLE REVIEW (
    Rating INT(32) NOT NULL
    , REVIEW VARCHAR (32)
    , TimeStamp DATETIME
    , Client VARCHAR (32)
    , TripName VARCHAR (32)
    , PRIMARY KEY(TripName) 
    ,FOREIGN KEY (TripName) REFERENCES DAYTRIP(Name) 
);

INSERT INTO OPERATOR VALUES('Ævintýraferðir','Vík í Mýrdal', '555-1234');
INSERT INTO OPERATOR VALUES('Arctic Adventures','Akureyri', '555-0000');
INSERT INTO OPERATOR VALUES('Iceland Travels','Reykjavík', '521- 2630');
INSERT INTO OPERATOR VALUES('Iceland Excursions','Akureyri', '567-8910');
INSERT INTO OPERATOR VALUES('All around tours','Reykjavík', '555-2345');
INSERT INTO OPERATOR VALUES('Country Tours','Vík í Mýrdal', '500-12345');

INSERT INTO DAYTRIP VALUES('Jöklaferð', '2022-05-01', '2022-05-01-09-00-00', '2022-05-01-12-00-00' , 3, 'Jöklaferð uppá Vatnajökul', 10, 5000,'Ævintýraferðir','Vík í Mýrdal', 10);
INSERT INTO DAYTRIP VALUES('Hestatúr', '2022-07-15', '2022- 07-15-12-00-00', '2022- 07-15-17-00-00', 1, '5 klst reiðferð um sveitina', 10, 7000,'Ævintýraferðir','Vík í Mýrdal', 15);
INSERT INTO DAYTRIP VALUES('Buggy ferð', '2022-10-10', '2022-10-10-13-00-00', '2022-10-10-16-00-00', 1, '3 klst buggy ferð', 17, 6000,'Ævintýraferðir','Vík í Mýrdal', 5);

INSERT INTO DAYTRIP VALUES('Kajak day trip', '2022-09-20', '2022-09-20-10-00-00', '2022-09-20-13-00-00', 1, '3 hour kajak sailing', 10, 10000,'Arctic Adventures','Akureyri', 4);
INSERT INTO DAYTRIP VALUES('Mountain hike', '2022-09-10', '2022-09-10-14-00-00', '2022-09-10-18-00-00', 3, '4 hour mountain hike', 15, 9000 ,'Arctic Adventures','Akureyri', 15);
INSERT INTO DAYTRIP VALUES('Snorkling', '2022-06-08', '2022-06-08-12-00-00', '2022-06-08-16-00-00', 1, '4 hour snorkling adventure', 15, 9000,'Arctic Adventures','Akureyri', 5);

INSERT INTO DAYTRIP VALUES('Golden circle', '2022-11-13', '2022-11-13-09-00-00', '2022-11-13-20-00-00', 1,'11 hour tour that covers Icelands most visited places', 1, 11000,'Iceland Travels','Reykjavík', 15);
INSERT INTO DAYTRIP VALUES('Northern lights tour', '2022-05-25', '2022-05-25-11-00-00', '2022-05-25-16-00-00', 1,'3-5 hours, stop at different locations and see the northern light during the night', 0, 12000,'Iceland Travels','Reykjavík', 10);
INSERT INTO DAYTRIP VALUES('Snæfellsnes round trip', '2022-08-16', '2022-08-16-12-00-00', '2022-08-16-17-00-00' , 1,'Take a round trip around the beautiful Snæfellsnes',0, 1000,'Iceland Travels','Reykjavík', 5);

INSERT INTO DAYTRIP VALUES('Waterfall excursion', '2022-07-05', '2022-07-05-09-00-00', '2022-07-05-16-00-00', 2,'7 hour road trip to see Icelands most visited waterfalls', 5, 8000,'Iceland Excursions','Akureyri', 5);
INSERT INTO DAYTRIP VALUES('Whale watching ', '2022-11-23', '2022-11-23-14-00-00', '2022-11-23-17-00-00', 1,'3 hour tour boat tour', 10, 7000,'Iceland Excursions','Akureyri', 6);
INSERT INTO DAYTRIP VALUES('Hot spring tour', '2022-08-14', '2022-08-14-15-00-00', '2022-08-14-12-00-00', 1,'5 hour road trip to see hotsprings',10, 5000,'Iceland Excursions','Akureyri', 6);

INSERT INTO DAYTRIP VALUES('Volcano hike', '2022-06-02', '2022-06-02-12-00-00', '2022-06-02-17-00-00', 4,'5 hour walking tour to see Icelands volcanic wonders', 10, 10000,'All around tours','Reykjavík', 10);
INSERT INTO DAYTRIP VALUES('The Lava Tunnel', '2022-07-17', '2022-07-17-16-00-00', '2022-07-17-19-00-00', 4,'3 hour adventure and visit the lava tunnel in Raufarhólshellir', 10, 12000,'All around tours','Reykjavík', 6);
INSERT INTO DAYTRIP VALUES('Blue Lagoon', '2022-08-28', '2022-08-28-17-00-00', '2022-08-28-22-00-00', 1,'5 hour trip to the most relaxing lagoon in Iceland', 5, 15000,'All around tours','Reykjavík', 5);

INSERT INTO DAYTRIP VALUES('Ice caving', '2022-11-03', '2022-11-03-16-00-00', '2022-11-03-19-00-00', 3,'3 hour ice cave adventure', 15, 15000,'Country Tours','Vík í Mýrdal', 15);
INSERT INTO DAYTRIP VALUES('Paragliding', '2022-08-26,', '2022-08-26-15-00-00', '2022-08-26-17-00-00', 2,'2 hours of adrenaline rush', 18, 20000,'Country Tours','Vík í Mýrdal', 4);
INSERT INTO DAYTRIP VALUES('Black beach hore riding', '2022-06-06', '2022-06-06-16-00-00', '2022-06-06-20-00-00', 3,'4 hours of horese back riding on the famous Black beach', 10, 11000,'Country Tours','Vík í Mýrdal', 5);
