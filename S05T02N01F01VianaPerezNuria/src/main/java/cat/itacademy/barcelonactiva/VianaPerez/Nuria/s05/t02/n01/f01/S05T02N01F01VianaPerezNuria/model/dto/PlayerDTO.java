package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDTO {
    private Long playerId;
    private String name;
    private LocalDateTime registerDate;
    private double winAverage;
    private double loseAverage;


    public PlayerDTO(String name) {
        this.name = name;
    }
}
