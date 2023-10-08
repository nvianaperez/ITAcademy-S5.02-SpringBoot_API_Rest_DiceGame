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

@Data
@AllArgsConstructor
public class Game {

    private static final int WINNER_RESULT = 7;

//    @MongoId
//    private ObjectId gameId;

    @Field(name = "dice1")
    private byte dice1;
    @Field(name = "dice2")
    private byte dice2;

    @Field(name = "gameScore")
    private byte gameScore;
    @Field(name = "gameResult")
    private Result gameResult;

    public enum Result {
        WIN, LOSE
    }

    @CreationTimestamp
    @Field(name = "gameDate")
    private LocalDateTime gameDate;

    @Field(name = "player")
    private Player player;
    public Game () {
        this.dice1 = (byte)Dice.getInstance().rollDice();
        this.dice2 = (byte)Dice.getInstance().rollDice();
        this.gameScore = calculateGameScore();
        this.gameResult = setGameResult();
        this.gameDate = LocalDateTime.now();
    }

    public Result setGameResult() {
        if(calculateGameScore() == WINNER_RESULT) return Result.WIN;
        else return Result.LOSE;
    }
    private byte calculateGameScore() {
        return (byte)(dice1+dice2);
    }




}
