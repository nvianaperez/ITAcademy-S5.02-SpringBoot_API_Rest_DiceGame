package cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.controller;

import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.dto.GameDTO;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.dto.PlayerDTO;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.dto.RegisterPlayerDTO;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.service.IDiceGameService;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.service.Message;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.service.exception.AnyPlayerInTheApplicationException;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.service.exception.GamesByPlayerNotFoundException;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.service.exception.PlayerDuplicatedException;
import cat.itacademy.barcelonactiva.VianaPerez.Nuria.s05.t02.n01.f02.S05T02N01F02VianaPerezNuria.model.service.exception.PlayerNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;


import java.time.LocalDateTime;

@RestController
@RequestMapping("/diceGame")
public class DiceGameControllerImpl implements IDiceGameController {

    private final IDiceGameService diceGameService;

    @Autowired
    public DiceGameControllerImpl(IDiceGameService diceGameService) {
        this.diceGameService = diceGameService;
    }


    @Override
    @Operation(
            tags = {"Sprint 5.2.1.F2"},
            operationId = "addPlayer",
            summary = "Create a new player and save in database",
            description = "needs a RegisterPlayerDTO in json format, if name given is blank or empty, name will be unknown, " +
                    "then check if player is present by name or is present by unknown and same id, " +
                    "in order to assure that PlayerDuplicatedException could be thrown. Finally, the expected responses are 201 or 400"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Player created correctly", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayerDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Player value already exists", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))})
    })

    @PostMapping("/players/add")
    public ResponseEntity<?> addPlayer(@RequestBody RegisterPlayerDTO registerPlayerDTO, WebRequest request) {
        try {
            return new ResponseEntity<>(diceGameService.addPlayer(registerPlayerDTO), HttpStatus.CREATED);
        } catch (PlayerDuplicatedException e) {
            return new ResponseEntity<>(new Message(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(), e.getMessage(), request.getDescription(false)), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Operation(
            tags = {"Sprint 5.2.1.F2"},
            operationId = "updatePlayer",
            summary = "Update player's name",
            description = "Updates an existing player in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player updated correctly", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = PlayerDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Player not found by id", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))})
    })

    @PutMapping("players/update/{playerId}")
    public ResponseEntity<?> updatePlayer(@PathVariable("playerId") ObjectId playerId, @RequestBody PlayerDTO playerDTO, WebRequest request) {
        try {
            return new ResponseEntity<>(diceGameService.editPlayerName(playerId, playerDTO), HttpStatus.OK);
        } catch (PlayerNotFoundException e) {
            return new ResponseEntity<>(new Message(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), e.getMessage(), request.getDescription(false)), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Operation(
            tags = {"Sprint 5.2.1.F2"},
            operationId = "addGameByPlayer",
            summary = "Create a new game",
            description = "Adds a new game into the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Game created correctly", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = GameDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Player not found by id", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))})
    })

    @PostMapping("/players/{playerId}/game")
    public ResponseEntity<?> playGame(@PathVariable("playerId") ObjectId playerId, WebRequest request) {
        try {
            return new ResponseEntity<>(diceGameService.addGameToPlayer(playerId), HttpStatus.CREATED);
        } catch (PlayerNotFoundException e) {
            return new ResponseEntity<>(new Message(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), e.getMessage(), request.getDescription(false)), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Operation(
            tags = {"Sprint 5.2.1.F2"},
            operationId = "deleteGamesByPlayer",
            summary = "Delete player's games",
            description = "Deletes the player's entire games list from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Games removed successfully", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "404", description = "Player not found by id", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "404", description = "Games not found by player", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))})
    })

    @DeleteMapping("/players/{playerId}/games")
    public ResponseEntity<Message> deleteAllGames(@PathVariable("playerId") ObjectId playerId, WebRequest request) {
        try {
            diceGameService.deleteAllGames(playerId);
            return new ResponseEntity<>(new Message(HttpStatus.OK.value(), LocalDateTime.now(), "Game List removed successfully", request.getDescription(false)), HttpStatus.OK);
        } catch (PlayerNotFoundException | GamesByPlayerNotFoundException e) {
            return new ResponseEntity<>(new Message(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), e.getMessage(), request.getDescription(false)), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Operation(
            tags = {"Sprint 5.2.1.F2"},
            operationId = "getPlayers",
            summary = "Get all players",
            description = "Returns a list with all players stored in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of players retrieved successfully", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PlayerDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "There are no players introduced in the database", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))})
    })

    @GetMapping("/players")
    public ResponseEntity<?> getAllPlayers(WebRequest request) {
        try {
            return new ResponseEntity<>(diceGameService.getAllPlayers(), HttpStatus.OK);
        } catch (AnyPlayerInTheApplicationException e) {
            return new ResponseEntity<>(new Message(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), e.getMessage(), request.getDescription(false)), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Operation(
            tags = {"Sprint 5.2.1.F2"},
            operationId = "getGamesByPlayer",
            summary = "Get all games by player",
            description = "Returns a list with all games played by the player stored in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of games by player retrieved successfully", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = GameDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "Player not found by id", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))}),
            @ApiResponse(responseCode = "404", description = "Games not found by player", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))})
    })

    @GetMapping("/{playerId}/games")
    public ResponseEntity<?> getAllGamesByPlayer(@PathVariable("playerId") ObjectId playerId, WebRequest request) {
        try {
            return new ResponseEntity<>(diceGameService.getAllGamesByPlayerId(playerId), HttpStatus.OK);
        } catch (PlayerNotFoundException | GamesByPlayerNotFoundException e) {
            return new ResponseEntity<>(new Message(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), e.getMessage(), request.getDescription(false)), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Operation(
            tags = {"Sprint 5.2.1.F2"},
            operationId = "getWinnerRanking",
            summary = "Players win average ",
            description = "Returns the players win-average from the database, sorted descendant.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Players average wins retrieved successfully", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = Message.class)))}),
            @ApiResponse(responseCode = "404", description = "There are no players introduced in the database", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))})
    })

    @GetMapping("/players/ranking")
    public ResponseEntity<?> getWinAverageRanking(WebRequest request) {
        try {
            return new ResponseEntity<>(diceGameService.getWinRank(), HttpStatus.OK);
        } catch (AnyPlayerInTheApplicationException e) {
            return new ResponseEntity<>(new Message(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), e.getMessage(), request.getDescription(false)), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Operation(
            tags = {"Sprint 5.2.1.F2"},
            operationId = "getTheBestLosingPlayer",
            summary = "Get the smallest lose average percentage player",
            description = "Returns the better losing player stored in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Better losing player retrieved successfully", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PlayerDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "There are no players introduced in the database", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))})
    })

    @GetMapping("/players/ranking/last-loser")
    public ResponseEntity<?> getLastLoser(WebRequest request) {
        try {
            return new ResponseEntity<>(diceGameService.getLastLoserPlayer(), HttpStatus.OK);
        } catch (AnyPlayerInTheApplicationException e) {
            return new ResponseEntity<>(new Message(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), e.getMessage(), request.getDescription(false)), HttpStatus.NOT_FOUND);
        }
    }

    @Override
    @Operation(
            tags = {"Sprint 5.2.1.F2"},
            operationId = "getTheWorstWinnerPlayer",
            summary = "Get the worst winner player",
            description = "Returns the smallest winner-percentage player stored in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Most winner player retrieved successfully", content = {@Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = PlayerDTO.class)))}),
            @ApiResponse(responseCode = "404", description = "There are no players introduced in the database", content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Message.class))})
    })

    @GetMapping("/players/ranking/last-winner")
    public ResponseEntity<?> getLastWinner(WebRequest request) {
        try {
            return new ResponseEntity<>(diceGameService.getLastWinnerPlayer(), HttpStatus.OK);
        } catch (AnyPlayerInTheApplicationException e) {
            return new ResponseEntity<>(new Message(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), e.getMessage(), request.getDescription(false)), HttpStatus.NOT_FOUND);
        }
    }
}
