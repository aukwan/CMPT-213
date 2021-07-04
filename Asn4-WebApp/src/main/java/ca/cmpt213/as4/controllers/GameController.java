package ca.cmpt213.as4.controllers;

import ca.cmpt213.as4.model.CellLocation;
import ca.cmpt213.as4.model.Maze;
import ca.cmpt213.as4.model.MazeGame;
import ca.cmpt213.as4.model.MoveDirection;
import ca.cmpt213.as4.wrappers.ApiBoardWrapper;
import ca.cmpt213.as4.wrappers.ApiGameWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class GameController {
    private List<MazeGame> games = new ArrayList<>();
    private AtomicInteger nextGameID = new AtomicInteger();

    // General
    @GetMapping("/api/about")
    public String getAboutMessage() {
        return "Austin Kwan";
    }

    // Games
    @GetMapping("/api/games")
    public List<ApiGameWrapper> getAllGames() {
        List<ApiGameWrapper> gameWrapperList = new ArrayList<>();
        for (int i = 0; i < games.size(); i++) {
            MazeGame game = games.get(i);
            gameWrapperList.add(ApiGameWrapper.makeFromGame(game, i + 1));
        }
        return gameWrapperList;
    }

    @PostMapping("/api/games")
    @ResponseStatus(HttpStatus.CREATED)
    public ApiGameWrapper createNewGame() {
        MazeGame game = new MazeGame();
        games.add(game);
        ApiGameWrapper gameWrapper = ApiGameWrapper.makeFromGame(game, nextGameID.incrementAndGet());
        return gameWrapper;
    }

    @GetMapping("/api/games/{id}")
    public ApiGameWrapper getGameByID(@PathVariable("id") int gameNumber) throws FileNotFoundException {
        if (gameNumber > games.size()) {
            throw new FileNotFoundException();
        } else {
            ApiGameWrapper gameWrapper = ApiGameWrapper.makeFromGame(games.get(gameNumber - 1), gameNumber);
            return gameWrapper;
        }
    }

    // Board
    @GetMapping("/api/games/{id}/board")
    public ApiBoardWrapper getMazeByGameID(@PathVariable("id") int gameNumber) throws FileNotFoundException {
        if (gameNumber > games.size()) {
            throw new FileNotFoundException();
        } else {
            ApiBoardWrapper boardWrapper = ApiBoardWrapper.makeFromGame(games.get(gameNumber - 1));
            return boardWrapper;
        }
    }

    // Moves
    @PostMapping("/api/games/{id}/moves")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void makeMove(@PathVariable("id") int gameNumber,
                         @RequestBody String move) throws FileNotFoundException, IllegalArgumentException {
        if (gameNumber > games.size()) {
            throw new FileNotFoundException();
        } else {
            MazeGame game = games.get(gameNumber - 1);
            switch (move) {
                case "MOVE_UP":
                    if (!game.isValidPlayerMove(MoveDirection.MOVE_UP)) {
                        throw new IllegalArgumentException();
                    } else {
                        game.recordPlayerMove(MoveDirection.MOVE_UP);
                    }
                    break;
                case "MOVE_DOWN":
                    if (!game.isValidPlayerMove(MoveDirection.MOVE_DOWN)) {
                        throw new IllegalArgumentException();
                    } else {
                        game.recordPlayerMove(MoveDirection.MOVE_DOWN);
                    }
                    break;
                case "MOVE_LEFT":
                    if (!game.isValidPlayerMove(MoveDirection.MOVE_LEFT)) {
                        throw new IllegalArgumentException();
                    } else {
                        game.recordPlayerMove(MoveDirection.MOVE_LEFT);
                    }
                    break;
                case "MOVE_RIGHT":
                    if (!game.isValidPlayerMove(MoveDirection.MOVE_RIGHT)) {
                        throw new IllegalArgumentException();
                    } else {
                        game.recordPlayerMove(MoveDirection.MOVE_RIGHT);
                    }
                    break;
                case "MOVE_CATS":
                    game.doCatMoves();
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
    }

    // Cheats
    @PostMapping("/api/games/{id}/cheatstate")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void activateCheat(@PathVariable("id") int gameNumber,
                              @RequestBody String cheat) throws FileNotFoundException, IllegalArgumentException {
        if (gameNumber > games.size()) {
            throw new FileNotFoundException();
        } else {
            if (cheat.equals("1_CHEESE")) {
                games.get(gameNumber - 1).setNumberCheeseToCollect(1);
                return;
            } else if (cheat.equals("SHOW_ALL")) {
                Maze maze = games.get(gameNumber - 1).getMaze();
                for (int i = 0; i < maze.getHeight(); i++) {
                    for (int j = 0; j < maze.getWidth(); j++) {
                        maze.recordCellVisible(new CellLocation(j, i));
                    }
                }
                return;
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(FileNotFoundException.class)
    public void gameNotFoundExceptionHandler() {}

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public void cheatNotFoundExceptionHandler() {}

}
