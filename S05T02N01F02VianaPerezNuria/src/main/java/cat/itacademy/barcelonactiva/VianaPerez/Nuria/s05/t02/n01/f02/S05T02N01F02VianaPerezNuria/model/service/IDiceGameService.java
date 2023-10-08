package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.service;

import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.dto.RegisterPlayerDTO;
import org.bson.types.ObjectId;

import java.util.List;

public interface IDiceGameService {
    PlayerDTO addPlayer(RegisterPlayerDTO registerPlayerDTO);
    PlayerDTO editPlayerName(ObjectId playerId, PlayerDTO playerDTO);

    GameDTO addGameToPlayer(ObjectId playerId);

    void deleteAllGames(ObjectId playerId);

    List<PlayerDTO> getAllPlayers();

    List<GameDTO> getAllGamesByPlayerId(ObjectId playerId);

    List<PlayerDTO> getWinRank();

    PlayerDTO getLastLoserPlayer();

    PlayerDTO getLastWinnerPlayer();
}
