# cluster4D_daytrips
 
### How to generate daytrips.db
Make sure to be in daytrips folder and that you have the sqlite-jdbc-3.36.0.3.jar there.

```javac Init.java```

```java -cp .:sqlite-jdbc-3.36.0.3.jar Init.java```

### How to run Init
From the root folder:
```javac -cp .:sqlite-jdbc-3.36.0.3.jar:junit-4.13.2.jar daytrips/*.java```
```java -cp .:sqlite-jdbc-3.36.0.3.jar:junit-4.13.2.jar daytrips/Init   ```
