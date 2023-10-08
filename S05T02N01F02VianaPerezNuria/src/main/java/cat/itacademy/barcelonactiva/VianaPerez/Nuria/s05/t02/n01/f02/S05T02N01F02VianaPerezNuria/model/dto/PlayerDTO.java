package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private ObjectId playerId;
    private String name;
    private LocalDateTime registerDate;
    private double winAverage;
    private double loseAverage;


    public PlayerDTO(String name) {
        this.name = name;
    }
}
