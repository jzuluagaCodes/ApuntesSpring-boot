# Parcial Spring Boot - Chuleta Practica

## Ejecutar en Linux
```bash
sudo apt update
sudo apt install openjdk-17-jdk maven -y

cd ~/ruta/parcial-apuntes-spring-boot
mvn clean compile
mvn spring-boot:run
```

## Base de datos en examen
- Por defecto: H2 en memoria (ya configurada)
- URL app: http://localhost:8080
- H2 Console: http://localhost:8080/h2-console
- JDBC: `jdbc:h2:mem:testdb`
- User: `sa`
- Password: vacio

## Estructura minima que siempre te piden
```text
src/main/java/com/parcial/apuntes/
  models/        # Entidades JPA (@Entity)
  repositories/  # Interfaces JpaRepository + @Query
  services/      # Logica + validaciones + @Transactional
  controllers/   # Endpoints REST
```

## Archivos clave de chuleta
- `CHULETA.md`: estructura general + checklist + recetas rapidas
- `src/main/java/com/parcial/apuntes/chuleta/ParcialChuleta.java`: snippets buscables en un solo archivo

## Comandos rapidos
```bash
# compilar
mvn clean compile

# ejecutar
mvn spring-boot:run

# test
mvn test


# matar puerto 8080 en linux
lsof -i :8080
kill -9 <PID>


package edu.co.icesi.introspringboot.controller;

import edu.co.icesi.introspringboot.entity.Club;
import edu.co.icesi.introspringboot.entity.Country;
import edu.co.icesi.introspringboot.entity.Match;
import edu.co.icesi.introspringboot.entity.Player;
import edu.co.icesi.introspringboot.repository.ClubRepository;
import edu.co.icesi.introspringboot.repository.CountryRepository;
import edu.co.icesi.introspringboot.repository.MatchRepository;
import edu.co.icesi.introspringboot.repository.PlayerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/worldcup")
public class WorldCupController {

    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final ClubRepository clubRepository;
    private final CountryRepository countryRepository;


    public WorldCupController(PlayerRepository playerRepository,
                              MatchRepository matchRepository,
                              ClubRepository clubRepository,
                              CountryRepository countryRepository
                              ) {
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
        this.clubRepository = clubRepository;
        this.countryRepository = countryRepository;
    }

    //Jugadores que han jigado en el real
    @GetMapping("/ej1")
    public List<Player> ej1(){
        throw new RuntimeException("not implemented");
    }

    //Jugadores que han jigado en el real
    @GetMapping("/ej2")
    public List<Player> ej2(){
        throw new RuntimeException("not implemented");
    }

    //Partidos en los que participan jugadores que hacen parte actualmente de bayern
    @GetMapping("/ej3")
    public List<Match> ej3(){
        throw new RuntimeException("not implemented");
    }

    //Partidos en los que participan jugadores que hacen parte actualmente de bayern
    @GetMapping("/ej4")
    public List<Club> ej4(){
        throw new RuntimeException("not implemented");
    }

    @GetMapping("/ej5")
    public Iterable<Country> ej5(){
        throw new RuntimeException("not implemented");
    }

}
package edu.co.icesi.introspringboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "club")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String city;
    private LocalDate founded;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @JsonIgnore
    @OneToMany(mappedBy = "playerClub")
    private List<PlayerClub> playerClubs;

    public Club() {
    }

    public Club(String name, String city, LocalDate founded, Country country) {
        this.name = name;
        this.city = city;
        this.founded = founded;
        this.country = country;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public LocalDate getFounded() { return founded; }
    public void setFounded(LocalDate founded) { this.founded = founded; }

    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }

    public List<PlayerClub> getPlayerClubs() { return playerClubs; }
    public void setPlayerClubs(List<PlayerClub> playerClubs) { this.playerClubs = playerClubs; }
}
package edu.co.icesi.introspringboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String code;
    private String confederation;

    @JsonIgnore
    @OneToMany(mappedBy = "homeCountry")
    private List<Match> homeMatches;

    @JsonIgnore
    @ManyToOne
    private Match awayMatches;

    @JsonIgnore
    @OneToMany(mappedBy = "country")
    private List<Player> players;

    @JsonIgnore
    @OneToMany(mappedBy = "country")
    private List<Club> clubs;

    public Country() {
    }

    public Country(String name, String code, String confederation) {
        this.name = name;
        this.code = code;
        this.confederation = confederation;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getConfederation() { return confederation; }
    public void setConfederation(String confederation) { this.confederation = confederation; }

    public List<Match> getHomeMatches() { return homeMatches; }
    public void setHomeMatches(List<Match> homeMatches) { this.homeMatches = homeMatches; }

    public Match getAwayMatches() { return awayMatches; }
    public void setAwayMatches(Match awayMatches) { this.awayMatches = awayMatches; }

    public List<Player> getPlayers() { return players; }
    public void setPlayers(List<Player> players) { this.players = players; }

    public List<Club> getClubs() { return clubs; }
    public void setClubs(List<Club> clubs) { this.clubs = clubs; }
}
package edu.co.icesi.introspringboot.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "match_game")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "match_date")
    private LocalDate matchDate;

    @ManyToOne
    @JoinColumn(name = "home_country_id")
    private Country homeCountry;

    @OneToMany
    private List<Country> awayCountry;

    private String stadium;

    public Match() {
    }

    public Match(LocalDate matchDate, Country homeCountry, List<Country> awayCountry, String stadium) {
        this.matchDate = matchDate;
        this.homeCountry = homeCountry;
        this.awayCountry = awayCountry;
        this.stadium = stadium;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getMatchDate() { return matchDate; }
    public void setMatchDate(LocalDate matchDate) { this.matchDate = matchDate; }

    public Country getHomeCountry() { return homeCountry; }
    public void setHomeCountry(Country homeCountry) { this.homeCountry = homeCountry; }

    public List<Country> getAwayCountry() { return awayCountry; }
    public void setAwayCountry(List<Country> awayCountry) { this.awayCountry = awayCountry; }

    public String getStadium() { return stadium; }
    public void setStadium(String stadium) { this.stadium = stadium; }
}
package edu.co.icesi.introspringboot.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String position;

    @Column(name = "fifa_score")
    private Integer fifaScore;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @JsonIgnore
    @OneToMany(mappedBy = "player")
    private List<PlayerClub> playerClubs;

    public Player(String name, LocalDate birthDate, String position, Integer fifaScore, Country country) {
        this.name = name;
        this.birthDate = birthDate;
        this.position = position;
        this.fifaScore = fifaScore;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
package edu.co.icesi.introspringboot.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "player_club")
public class PlayerClub {

    @EmbeddedId
    private PlayerClubId id;

    @ManyToOne
    @MapsId("playerId")
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @MapsId("clubId")
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public PlayerClub() {
    }

    public PlayerClub(Player player, Club club, LocalDate startDate, LocalDate endDate) {
        this.player = player;
        this.club = club;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = new PlayerClubId(player.getId(), club.getId());
    }

    public PlayerClubId getId() {
        return id;
    }

    public void setId(PlayerClubId id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
package edu.co.icesi.introspringboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Table
public class PlayerClubId implements Serializable {

    @Column(name = "player_id")
    private Integer playerId;

    @Column(name = "club_id")
    private Integer clubId;

    public PlayerClubId() {
    }

    public PlayerClubId(Integer playerId, Integer clubId) {
        this.playerId = playerId;
        this.clubId = clubId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerClubId that = (PlayerClubId) o;
        return Objects.equals(playerId, that.playerId) && Objects.equals(clubId, that.clubId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, clubId);
    }
}
package edu.co.icesi.introspringboot;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntroSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntroSpringBootApplication.class, args);
    }

    @PostConstruct
    public void init() {
        System.out.println(">>>>>Inició la app");
    }
}
package edu.co.icesi.introspringboot;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(IntroSpringBootApplication.class);
    }

}
spring.application.name=IntroSpringBoot
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
# http://localhost:8080/h2
spring.h2.console.path=/h2
spring.jpa.defer-datasource-initialization=true
server.port=8081
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.11</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>edu.co.icesi</groupId>
    <artifactId>IntroSpringBoot</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>war</packaging>
    <name>IntroSpringBoot</name>
    <description>IntroSpringBoot</description>
    <url/>
    <licenses>
        <license/>
    </licenses>
    <developers>
        <developer/>
    </developers>
    <scm>
        <connection/>
        <developerConnection/>
        <tag/>
        <url/>
    </scm>
    <properties>
        <java.version>17</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

```
# compilar
mvn clean compile

# ejecutar
mvn spring-boot:run

# test
mvn test


# matar puerto 8080 en linux
lsof -i :8080
kill -9 <PID>


package edu.co.icesi.introspringboot.controller;

import edu.co.icesi.introspringboot.entity.Club;
import edu.co.icesi.introspringboot.entity.Country;
import edu.co.icesi.introspringboot.entity.Match;
import edu.co.icesi.introspringboot.entity.Player;
import edu.co.icesi.introspringboot.repository.ClubRepository;
import edu.co.icesi.introspringboot.repository.CountryRepository;
import edu.co.icesi.introspringboot.repository.MatchRepository;
import edu.co.icesi.introspringboot.repository.PlayerRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/worldcup")
public class WorldCupController {

    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;
    private final ClubRepository clubRepository;
    private final CountryRepository countryRepository;


    public WorldCupController(PlayerRepository playerRepository,
                              MatchRepository matchRepository,
                              ClubRepository clubRepository,
                              CountryRepository countryRepository
                              ) {
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
        this.clubRepository = clubRepository;
        this.countryRepository = countryRepository;
    }

    //Jugadores que han jigado en el real
    @GetMapping("/ej1")
    public List<Player> ej1(){
        throw new RuntimeException("not implemented");
    }

    //Jugadores que han jigado en el real
    @GetMapping("/ej2")
    public List<Player> ej2(){
        throw new RuntimeException("not implemented");
    }

    //Partidos en los que participan jugadores que hacen parte actualmente de bayern
    @GetMapping("/ej3")
    public List<Match> ej3(){
        throw new RuntimeException("not implemented");
    }

    //Partidos en los que participan jugadores que hacen parte actualmente de bayern
    @GetMapping("/ej4")
    public List<Club> ej4(){
        throw new RuntimeException("not implemented");
    }

    @GetMapping("/ej5")
    public Iterable<Country> ej5(){
        throw new RuntimeException("not implemented");
    }

}
package edu.co.icesi.introspringboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "club")
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String city;
    private LocalDate founded;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @JsonIgnore
    @OneToMany(mappedBy = "playerClub")
    private List<PlayerClub> playerClubs;

    public Club() {
    }

    public Club(String name, String city, LocalDate founded, Country country) {
        this.name = name;
        this.city = city;
        this.founded = founded;
        this.country = country;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public LocalDate getFounded() { return founded; }
    public void setFounded(LocalDate founded) { this.founded = founded; }

    public Country getCountry() { return country; }
    public void setCountry(Country country) { this.country = country; }

    public List<PlayerClub> getPlayerClubs() { return playerClubs; }
    public void setPlayerClubs(List<PlayerClub> playerClubs) { this.playerClubs = playerClubs; }
}
package edu.co.icesi.introspringboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "country")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String code;
    private String confederation;

    @JsonIgnore
    @OneToMany(mappedBy = "homeCountry")
    private List<Match> homeMatches;

    @JsonIgnore
    @ManyToOne
    private Match awayMatches;

    @JsonIgnore
    @OneToMany(mappedBy = "country")
    private List<Player> players;

    @JsonIgnore
    @OneToMany(mappedBy = "country")
    private List<Club> clubs;

    public Country() {
    }

    public Country(String name, String code, String confederation) {
        this.name = name;
        this.code = code;
        this.confederation = confederation;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getConfederation() { return confederation; }
    public void setConfederation(String confederation) { this.confederation = confederation; }

    public List<Match> getHomeMatches() { return homeMatches; }
    public void setHomeMatches(List<Match> homeMatches) { this.homeMatches = homeMatches; }

    public Match getAwayMatches() { return awayMatches; }
    public void setAwayMatches(Match awayMatches) { this.awayMatches = awayMatches; }

    public List<Player> getPlayers() { return players; }
    public void setPlayers(List<Player> players) { this.players = players; }

    public List<Club> getClubs() { return clubs; }
    public void setClubs(List<Club> clubs) { this.clubs = clubs; }
}
package edu.co.icesi.introspringboot.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "match_game")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "match_date")
    private LocalDate matchDate;

    @ManyToOne
    @JoinColumn(name = "home_country_id")
    private Country homeCountry;

    @OneToMany
    private List<Country> awayCountry;

    private String stadium;

    public Match() {
    }

    public Match(LocalDate matchDate, Country homeCountry, List<Country> awayCountry, String stadium) {
        this.matchDate = matchDate;
        this.homeCountry = homeCountry;
        this.awayCountry = awayCountry;
        this.stadium = stadium;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public LocalDate getMatchDate() { return matchDate; }
    public void setMatchDate(LocalDate matchDate) { this.matchDate = matchDate; }

    public Country getHomeCountry() { return homeCountry; }
    public void setHomeCountry(Country homeCountry) { this.homeCountry = homeCountry; }

    public List<Country> getAwayCountry() { return awayCountry; }
    public void setAwayCountry(List<Country> awayCountry) { this.awayCountry = awayCountry; }

    public String getStadium() { return stadium; }
    public void setStadium(String stadium) { this.stadium = stadium; }
}
package edu.co.icesi.introspringboot.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String position;

    @Column(name = "fifa_score")
    private Integer fifaScore;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @JsonIgnore
    @OneToMany(mappedBy = "player")
    private List<PlayerClub> playerClubs;

    public Player(String name, LocalDate birthDate, String position, Integer fifaScore, Country country) {
        this.name = name;
        this.birthDate = birthDate;
        this.position = position;
        this.fifaScore = fifaScore;
        this.country = country;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


}
package edu.co.icesi.introspringboot.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "player_club")
public class PlayerClub {

    @EmbeddedId
    private PlayerClubId id;

    @ManyToOne
    @MapsId("playerId")
    @JoinColumn(name = "player_id")
    private Player player;

    @ManyToOne
    @MapsId("clubId")
    @JoinColumn(name = "club_id")
    private Club club;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    public PlayerClub() {
    }

    public PlayerClub(Player player, Club club, LocalDate startDate, LocalDate endDate) {
        this.player = player;
        this.club = club;
        this.startDate = startDate;
        this.endDate = endDate;
        this.id = new PlayerClubId(player.getId(), club.getId());
    }

    public PlayerClubId getId() {
        return id;
    }

    public void setId(PlayerClubId id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
package edu.co.icesi.introspringboot.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Table
public class PlayerClubId implements Serializable {

    @Column(name = "player_id")
    private Integer playerId;

    @Column(name = "club_id")
    private Integer clubId;

    public PlayerClubId() {
    }

    public PlayerClubId(Integer playerId, Integer clubId) {
        this.playerId = playerId;
        this.clubId = clubId;
    }

    public Integer getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Integer playerId) {
        this.playerId = playerId;
    }

    public Integer getClubId() {
        return clubId;
    }

    public void setClubId(Integer clubId) {
        this.clubId = clubId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerClubId that = (PlayerClubId) o;
        return Objects.equals(playerId, that.playerId) && Objects.equals(clubId, that.clubId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, clubId);
    }
}
package edu.co.icesi.introspringboot;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IntroSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntroSpringBootApplication.class, args);
    }

    @PostConstruct
    public void init() {
        System.out.println(">>>>>Inició la app");
    }
}
package edu.co.icesi.introspringboot;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(IntroSpringBootApplication.class);
    }

}
spring.application.name=IntroSpringBoot
spring.jpa.hibernate.ddl-auto=create-drop
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.h2.console.enabled=true
# http://localhost:8080/h2
spring.h2.console.path=/h2
spring.jpa.defer-datasource-initialization=true
server.port=8081
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.11</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    <groupId>edu.co.icesi</groupId>
    <artifactId>IntroSpringBoot</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <packaging>war</packaging>
    <name>IntroSpringBoot</name>
    <description>IntroSpringBoot</description>
    <url/>
    <licenses>
        <license/>
    </licenses>
    <developers>
        <developer/>
    </developers>
    <scm>
        <connection/>
        <developerConnection/>
        <tag/>
        <url/>
    </scm>
    <properties>
        <java.version>17</java.version>
    </properties>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>
        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
            <scope>provided</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>

</project>

¡Excelente progreso! Ya tienes el **modelo casi perfecto** y el controller listo. Ahora solo faltan:

## 1. Corregir 3 errores críticos para compilar

**A) Player le falta @Entity**  
```java
@Entity  // ← AGREGAR ESTA LÍNEA
@Table(name = "player")
public class Player {
```

**B) PlayerClubId le falta @Embeddable**  
```java
@Embeddable  // ← AGREGAR ESTA LÍNEA
public class PlayerClubId implements Serializable {
```

**C) Club: mappedBy incorrecto**  
```java
@JsonIgnore
@OneToMany(mappedBy = "club")  // ← CAMBIAR "playerClub" por "club"
private List<PlayerClub> playerClubs;
```

Haz esos 3 cambios y ejecuta `mvn clean compile`. Debería compilar sin errores.

## 2. Crear los 4 repositorios

Crea estas interfaces en `repository/`:

```java
// PlayerRepository.java
package edu.co.icesi.introspringboot.repository;
import edu.co.icesi.introspringboot.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface PlayerRepository extends JpaRepository<Player, Integer> {
    List<Player> findByPlayerClubsClubName(String clubName);
}
```

```java
// MatchRepository.java  
package edu.co.icesi.introspringboot.repository;
import edu.co.icesi.introspringboot.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface MatchRepository extends JpaRepository<Match, Integer> {
    List<Match> findDistinctByHomeCountryPlayersPlayerClubsClubNameOrAwayCountryPlayersPlayerClubsClubName(String name1, String name2);
}
```

```java
// ClubRepository.java
package edu.co.icesi.introspringboot.repository;
import edu.co.icesi.introspringboot.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ClubRepository extends JpaRepository<Club, Integer> {
    Club findByName(String name);
}
```

```java
// CountryRepository.java
package edu.co.icesi.introspringboot.repository;
import edu.co.icesi.introspringboot.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CountryRepository extends JpaRepository<Country, Integer> {
}
```

## 3. Implementar ej1 (jugadores que han jugado en Real Madrid)

```java
@GetMapping("/ej1")
public List<Player> ej1(){
    return playerRepository.findByPlayerClubsClubName("Real Madrid");
}
```

## 4. Probar paso a paso

1. `mvn clean compile` → debe pasar  
2. `mvn spring-boot:run` → debe arrancar en puerto 8081  
3. Ir a `http://localhost:8081/worldcup/ej1` → debe devolver lista vacía `[]` (porque no hay datos, pero no explota)

## 5. Si sale error específico

Pega aquí el **error exacto** que te dé `mvn clean compile` después de los cambios. Con eso lo resolvemos en 1 minuto.

¡Estás a 5 minutos de tenerlo corriendo! 🚀

Citas:
[1] selected_image_2195414170131224037.jpg https://ppl-ai-file-upload.s3.amazonaws.com/web/direct-files/attachments/images/63389360/9e97b037-5385-438e-869d-21f82e2ddb92/selected_image_2195414170131224037.jpg

