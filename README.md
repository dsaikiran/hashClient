### HashMap Client

#### Local setup
##### Build
```
  cd hashClient
  mvn clean install
```
##### Run
```
  mvn spring-boot:run
```

#### PCF setup

##### Build
```
  cd hashClient
  mvn clean install
```
##### Push into PCF
```
  cf push hashclient -p target/hashclient-0.0.1-SNAPSHOT.jar
```

##### Sample curl requests
```
	PUT - https://hashclients-nonconcentric-consolation.cfapps.io/TechoHash/name
		provide request body as {saikiran}
	GET - https://hashclients-nonconcentric-consolation.cfapps.io/TechoHash/name
		OutPut : saikiran
