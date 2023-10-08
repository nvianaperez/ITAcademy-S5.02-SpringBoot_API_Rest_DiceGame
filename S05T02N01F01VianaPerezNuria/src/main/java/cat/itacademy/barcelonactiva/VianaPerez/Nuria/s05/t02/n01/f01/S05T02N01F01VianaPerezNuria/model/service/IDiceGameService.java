package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.service;

import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.dto.RegisterPlayerDTO;

import java.util.List;

public interface IDiceGameService {
    PlayerDTO addPlayer(RegisterPlayerDTO registerPlayerDTO);
    PlayerDTO editPlayerName(Long playerId, PlayerDTO playerDTO);

    GameDTO addGameToPlayer(Long playerId);

    void deleteAllGames(Long playerId);

    List<PlayerDTO> getAllPlayers();

    List<GameDTO> getAllGamesByPlayerId(Long playerId);

    List<PlayerDTO> getWinRank();

    PlayerDTO getLastLoserPlayer();

    PlayerDTO getLastWinnerPlayer();
}
