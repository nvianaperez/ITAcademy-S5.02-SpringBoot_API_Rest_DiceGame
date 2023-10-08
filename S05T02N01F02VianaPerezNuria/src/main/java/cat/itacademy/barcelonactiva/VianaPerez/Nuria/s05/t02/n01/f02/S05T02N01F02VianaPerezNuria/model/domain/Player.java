package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;



@Data
@AllArgsConstructor
@Document(collection = "players")
public class Player {

    @MongoId
    private ObjectId playerId;

    @Field(name = "name")
    private String name;

    @CreationTimestamp
    @Field(name = "registerDate")
    private LocalDateTime registerDate;

    @Field(name = "games")
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

