package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
@AllArgsConstructor
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playerId")
    private Long playerId;

    @Column(name = "name")
    private String name;

    @CreationTimestamp
    @Column(name = "registerDate")
    private LocalDateTime registerDate;

    @Column(name = "games")
    @OneToMany(mappedBy = "player", fetch = FetchType.EAGER, orphanRemoval = true, targetEntity = Game.class, cascade = CascadeType.ALL)
    // if [error HttpMessageNotWritableException: Could not write JSON: Infinite recursion (StackOverflowError)]
    // cuando tienes una relación bidireccional entre entidades JPA y Jackson (la biblioteca que Spring utiliza para la serialización y deserialización JSON).
    // En otras palabras, si las clases Game y Player se refieren mutuamente, Jackson puede entrar en un bucle infinito al intentar serializar estas clases a JSON.
    // Por esto utilizamos JsonIgnore
    @JsonIgnore
    private List<Game> games;


    public Player (String name) {
        this.name = name;
        this.registerDate = LocalDateTime.now();
        this.games = new ArrayList<>();
    }

    public Player() {
        this.registerDate = LocalDateTime.now();
        this.games = new ArrayList<>();
    }

    public void addGameToGames(Game game) {
        this.games.add(game);
    }

    public void deleteAllGamesByPlayer() {
        this.games.clear();
    }

}

