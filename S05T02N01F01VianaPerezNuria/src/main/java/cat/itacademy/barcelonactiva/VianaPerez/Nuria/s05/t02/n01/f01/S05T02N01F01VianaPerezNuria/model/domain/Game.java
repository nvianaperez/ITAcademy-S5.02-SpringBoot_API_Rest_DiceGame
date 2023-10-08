package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "game")
public class Game {

    private static final int WINNER_RESULT = 7;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "gameId")
    private Long gameId;

    @Column(name = "dice1")
    private byte dice1;
    @Column(name = "dice2")
    private byte dice2;

    @Column(name = "gameScore")
    private byte gameScore;
    @Column(name = "gameResult")
    private Result gameResult;

    public enum Result {
        WIN, LOSE
    }

    @JoinColumn(name = "playerId")
    @ManyToOne(optional = false)
    private Player player;

    @Column(name = "gameDate")
    private LocalDateTime gameDate;

    public Game (Player player) {
        this.player = player;
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
