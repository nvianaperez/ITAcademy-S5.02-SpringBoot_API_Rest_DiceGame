package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class Message {

    @Schema(description = "Http Status", example = "201")
    private int statusCode;

    @Schema(description = "Date and time", example = "2023-04-12T21:04:31.274+00:00")
    private LocalDateTime timestamp;

    @Schema(description = "Description message", example = "Player created and added successfully into the database")
    private String textMessage;

    @Schema(description = "Uri path", example = "uri=/players/add")
    private String uri;

    public Message(String textMessage) {
        this.textMessage = textMessage;
    }
}
