package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.controller;

import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.dto.RegisterPlayerDTO;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.service.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

public interface IDiceGameController {

    /**
     * POST -> /players/add
     */
    ResponseEntity<?> addPlayer(@RequestBody RegisterPlayerDTO registerPlayerDTO, WebRequest request);
    /**
     * PUT -> /players/update/{playerId}
     */
    ResponseEntity<?> updatePlayer(@PathVariable Long playerId, @RequestBody PlayerDTO playerDTO, WebRequest request);
    /**
     * POST -> /players/{playerId}/game --> un jugador/a específic realitza una tirada dels daus.
     */
    ResponseEntity<?> playGame(@PathVariable Long playerId, WebRequest request);
    /**
     * DELETE -> /players/{playerId}/games --> elimina les tirades del jugador/a.
     */
    ResponseEntity<Message> deleteAllGames(@PathVariable Long playerId, WebRequest request);
//    ResponseEntity<Message> deleteGames(@PathVariable long player_id, WebRequest request);
    /**
     * GET -> /players --> retorna el llistat de tots  els jugadors/es del sistema amb el seu  percentatge mitjà d’èxits.
     */
    ResponseEntity<?> getAllPlayers(WebRequest request);
    /**
     * GET -> /players/{playerId}/games --> retorna el llistat de jugades per un jugador/a.
     */
    ResponseEntity<?> getAllGamesByPlayer(@PathVariable Long playerId, WebRequest request);
    /**
     * GET -> /players/ranking --> retorna el ranking mig de tots els jugadors/es del sistema. És a dir, el  percentatge mitjà d’èxits.
     */
    ResponseEntity<?> getWinAverageRanking(WebRequest request);
    /**
     * GET -> /players/ranking/last-loser --> retorna el jugador/a  amb pitjor percentatge de perdre. Amb el percentatge de perdre més petit.
     */
    ResponseEntity<?> getLastLoser(WebRequest request);
    /**
     * GET -> /players/ranking/last-winner --> retorna el jugador/a  amb pitjor percentatge d’èxit. Amb el percentatge de guanyar més petit.
     */
    ResponseEntity<?> getLastWinner(WebRequest request);
}
