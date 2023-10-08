package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.service;

import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.domain.Game;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.domain.Player;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.dto.RegisterPlayerDTO;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.repository.IGameRepository;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.repository.IPlayerRepository;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.service.exception.AnyPlayerInTheApplicationException;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.service.exception.GamesByPlayerNotFoundException;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.service.exception.PlayerDuplicatedException;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f01.S05T02N01F01VianaPerezNuria.model.service.exception.PlayerNotFoundException;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class DiceGameServiceImpl implements IDiceGameService{

    @Autowired
    private ModelMapper modelMapper;

    private final IPlayerRepository playerRepository;
    private final IGameRepository gameRepository;

    @Autowired
    public DiceGameServiceImpl(IPlayerRepository playerRepository, IGameRepository gameRepository) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    @Transactional
    public PlayerDTO addPlayer(RegisterPlayerDTO registerPlayerDTO) {
        RegisterPlayerDTO playerValid = checkPlayerName(registerPlayerDTO);
        Player playerAdded = playerRepository.save(convertRegisterPlayerDTOToPlayer(playerValid));
        return convertPlayerToPlayerDTO(playerAdded);
    }

    @Override
    @Transactional
    public PlayerDTO editPlayerName(Long playerId, PlayerDTO playerDTO) {
        PlayerDTO playerToEdit = getPlayerDTOById(playerId);
        playerToEdit.setName(playerDTO.getName());
        playerRepository.save(convertPlayerDTOToPlayer(playerToEdit));
        return playerToEdit;
    }

    @Override
    @Transactional
    public GameDTO addGameToPlayer(Long playerId) {
        Player playerToAddGame = getPlayerById(playerId);
        Game newGame = new Game(playerToAddGame);
        gameRepository.save(newGame);
        playerToAddGame.addGameToGames(newGame);
        return convertGameToGameDTO(newGame);
    }

    @Override
    public void deleteAllGames(Long playerId) {
        Player playerToDeleteGames = getPlayerById(playerId);
        if(playerToDeleteGames.getGames().isEmpty()) throw new GamesByPlayerNotFoundException("There is no games saved for this player.");
        playerToDeleteGames.deleteAllGamesByPlayer();
        playerRepository.save(playerToDeleteGames);
    }

    @Override
    public List<PlayerDTO> getAllPlayers() {
        List<Player> playerList = getAllPlayersInRepo();
        List<PlayerDTO> playerDTOList = new ArrayList<>(playerList.size());

        playerList.forEach(player -> {
            PlayerDTO playerDTO = convertPlayerToPlayerDTO(player);
            playerDTO.setWinAverage(winAverage(player));
            playerDTO.setLoseAverage(loseAverage(player));
            playerDTOList.add(playerDTO);
        });

        return playerDTOList;
    }

    @Override
    public List<GameDTO> getAllGamesByPlayerId(Long playerId) {
        Player playerFound = getPlayerById(playerId);
        if(playerFound.getGames().isEmpty()) throw new GamesByPlayerNotFoundException("There is no games saved for this player.");
        List<GameDTO> gamesDTOByPlayerId = playerFound.getGames().stream()
                .map(this::convertGameToGameDTO)
                .collect(Collectors.toList());
        return gamesDTOByPlayerId;
    }

    @Override
    public List<PlayerDTO> getWinRank() {
        List<PlayerDTO> orderedPlayerDTOList = getAllPlayers().stream()
                .sorted(Comparator.comparingDouble(PlayerDTO::getWinAverage).reversed())
                .collect(Collectors.toList());
        return orderedPlayerDTOList;
    }

    @Override
    public PlayerDTO getLastLoserPlayer() {
        List<PlayerDTO> playerListDTOWithGames = getPlayerDTOSWithGames();
        return playerListDTOWithGames.stream()
                .min(Comparator.comparingDouble(PlayerDTO::getLoseAverage)).get();
    }

    @Override
    public PlayerDTO getLastWinnerPlayer() {
        List<PlayerDTO> playerListDTOWithGames = getPlayerDTOSWithGames();
        return playerListDTOWithGames.stream()
                .min(Comparator.comparingDouble(PlayerDTO::getWinAverage)).get();
    }

    @NotNull
    private List<PlayerDTO> getPlayerDTOSWithGames() {
        List<Player> playerListWithGames = getAllPlayersInRepo().stream()
                .filter(player -> !(player.getGames().isEmpty()))
                .collect(Collectors.toList());

        List<PlayerDTO> playerListDTOWithGames = new ArrayList<>();

        playerListWithGames.forEach(player -> {
            PlayerDTO playerDTO = convertPlayerToPlayerDTO(player);
            playerDTO.setWinAverage(winAverage(player));
            playerDTO.setLoseAverage(loseAverage(player));
            playerListDTOWithGames.add(playerDTO);
        });
        return playerListDTOWithGames;
    }

    private double winAverage(Player player) {
        Player playerFound = getPlayerById(player.getPlayerId());

        if(playerFound.getGames().isEmpty()) {
            return 0.00;
        } else {
            double countWin = playerFound.getGames().stream()
                    .filter(game -> game.getGameResult() == Game.Result.WIN)
                    .count();
            return Math.round(countWin / playerFound.getGames().size()*100.0d);
        }
    }

    private double loseAverage(Player player) {
        Player playerFound = getPlayerById(player.getPlayerId());

        if(playerFound.getGames().isEmpty()) {
            return 0.00;
        } else {
            double countLose = playerFound.getGames().stream()
                    .filter(game -> game.getGameResult() == Game.Result.LOSE)
                    .count();
            return Math.round(countLose / playerFound.getGames().size()*100.0d);
        }
    }

    private List<Player> getAllPlayersInRepo() {
        if(playerRepository.findAll().isEmpty()) throw new AnyPlayerInTheApplicationException("There is any player in the application to start playing games.");
        else return playerRepository.findAll();
    }

    public PlayerDTO getPlayerDTOById(Long playerId) {
        Optional<Player> playerFoundById = playerRepository.findById(playerId);
        if(playerFoundById.isPresent()) {
            return convertPlayerToPlayerDTO(playerFoundById.get());
        } else throw new PlayerNotFoundException("The id player is not in database.");
    }

    public Player getPlayerById(Long playerId) {
        Optional<Player> playerFoundById = playerRepository.findById(playerId);
        if(playerFoundById.isPresent()) {
            return playerFoundById.get();
        } else throw new PlayerNotFoundException("The id player is not in database.");
    }

    private RegisterPlayerDTO checkPlayerName(RegisterPlayerDTO registerPlayerDTO) {
        if ((registerPlayerDTO.getName() == null) || (registerPlayerDTO.getName().isEmpty()) || (registerPlayerDTO.getName().isBlank())) {
            registerPlayerDTO.setName("unknown");
        } else if (playerRepository.existsByName((registerPlayerDTO.getName())) && (!registerPlayerDTO.getName().equals("unknown"))) {
            throw new PlayerDuplicatedException("Player's name must be unique.");
        } else if (registerPlayerDTO.getName().equals("unknown") && playerRepository.existsById(registerPlayerDTO.getPlayerId())) {
            throw new PlayerDuplicatedException("Player's name must be unique.");
        }
        return registerPlayerDTO;
    }

    // INIT convert DTO <--> ENTITIES
    private Player convertRegisterPlayerDTOToPlayer(RegisterPlayerDTO registerPlayerDTO) {
        return modelMapper.map(registerPlayerDTO, Player.class);
    }

    private Player convertPlayerDTOToPlayer(PlayerDTO playerDTO) {
        return modelMapper.map(playerDTO,Player.class);
    }
    private PlayerDTO convertPlayerToPlayerDTO(Player player) {
        return modelMapper.map(player, PlayerDTO.class);
    }

    private GameDTO convertGameToGameDTO(Game game) {
        return modelMapper.map(game, GameDTO.class);
    }

    // ENDING convert DTO <--> ENTITIES
}
