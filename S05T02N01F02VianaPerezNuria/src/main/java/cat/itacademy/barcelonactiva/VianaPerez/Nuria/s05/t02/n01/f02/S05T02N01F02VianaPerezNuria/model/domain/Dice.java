package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Random;


@Data
@AllArgsConstructor
public class Dice {

    private final byte NUM_OF_FACES = 6;
    private static Dice instance;
    private int dice;

    private Dice () {
        this.dice = rollDice();
    }

    public static Dice getInstance() {
        if (instance == null) {
            instance = new Dice();
        }
        return instance;
    }

    public int rollDice() {
        return new Random().nextInt(NUM_OF_FACES)+1;
    }

}
