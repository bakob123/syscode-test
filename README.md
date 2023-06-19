# Syscode test
A feladat két egymással kommunikálni képes service megvalósítása volt (ProfileService, AddressService).
A servicek külön indíthatók, azonban az AddressServicet kizárólag a ProfileServicen keresztülj tudjuk elérni, autentikáció után.

A repository tartalmazza a serviceket, illetve 1-1 `Dockerfile`-t és egy `docker-compose.yml`-t. Jelen pillanatban a servicek
nem érik el egymást a docker containereken keresztül, önálló API-ként azonban mindkét service képes requesteket fogadni, a 
`docker-compose run` parancs lefuttatása után.
IDE-ben az elvárt módon kommunikál a ProfileService és AddressService, az előbbi a `8080`-as, az utóbbi pedig a `8100`-as porton indul.

A program nem igényel konfigurálást, az adatbázis `Liquibase` és `H2` segítségével futtatáskor felépül,
illetve néhány default adattal bővül.
A publikus metódusok unit tesztelve, az end pointok pedig egy saját `H2` adatbázis segítségével integration tesztelve vannak.

Az AddressServiceben nem implementáltam a Spring Securityt -habár jövőtállóbb megoldás lenne-, túl nagynak gondoltam a jelenlegi specifikációhoz. 
Az autentikáció JWT tokenen keresztül történik.

A dokumentáció a `...Service/documentation` mappában található, illetve `Swagger` formátumban az alkalmazás indítása után
a `localhost:{megfelelőServicePort}/swagger-ui.html` oldalon érhető el.

## Felhasznált technológiák
 - Java 11
 - Spring Boot
 - Maven
 - Liquibase
 - H2
 - Spring data JPA
 - Hibernate validator
 - JJWT library
 - JUnit 5
 - Mockito
 - OpenAPI 3.0
 - Git
 - Docker

## Jelenleg ismert problémák
 - A servicek docker segítségével jelenleg nem képesek kommunikálni.
 - A ProfileService egyik integration tesztje `@Disabled` maradt megoldás hiányában.