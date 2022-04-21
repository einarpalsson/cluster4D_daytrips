# Kynning punktar

- **queryParser()** --> Sér um alla filteringu. Gerir ráð fyrir tveim edge-caseum.
  - Tekur inn hakkatöflu, parsar hana og býr til SQL skipanir út frá gefnum parameterum
  - Tekur inn gerð SQL skipanar. POST og GET
  - tekur inn upprunalegt query sem önnur föll sjá um að ákvarða
  - Tekur inn parametera fyrir hvern klasa til að fá þá í réttri röð.
- **createDayTrip()** --> Býr til daytrip, tekur in hakkatöflu með parameterum.
- **getDayTrips()** --> Sækir daytrips eftir parameterum (eða ekki) og skilar þeim sem arraylista af DayTrip objectum. Frjálst parametera-val.
- **bookDayTrip()** --> Býr til bókun á DayTrip, hér þarf alla parametera og sérstaklega DayTripID. Fallið uppfærir einnig sætapláss í daytrip-töflu
  - Skilar BookindID
- **getBookings()** --> Alveg eins og getDayTrips()
- **getOperators()** --> Alveg eins og getDayTrips()
- **getReviews()** --> Alveg eins og getDayTrips()
- **insertReview()** --> Býr til review, verður að fá inn daytripID og kennitölu
- **main()** --> Ýmsar prófanir á ofangreindum föllum

**emailSender** --> Það er cron Job sem keyrir alltaf kl 00 á tölvuni sem keyrt forritið frá. Og svo er for-loopað í gegnum bookings og tékkað hvaða bookings eru á morgun. Svo er sent reminder tölvupóst

Fyrir skil ef þörf:
Forrita klasa sem sér um að testa og sýnir virkni, ef kennara sér ekki nóg útfrá interface.
