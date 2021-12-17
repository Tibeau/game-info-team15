package fact.it.gameinfo.controller;


import fact.it.gameinfo.model.Game;
import fact.it.gameinfo.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class GameController {
    @Autowired
    private GameRepository gameRepository;

    @PostConstruct
    public void fillDB() {
        if (gameRepository.count() == 0) {
            gameRepository.save(new Game("Call of Duty: Cold War", 2021, "Activision", 1500000));
            gameRepository.save(new Game("Call of Duty: Modern Warfare", 2020, "Activision", 1600999));
        }

    }

    @GetMapping("/games/name/{name}")
    public List<Game> getGamesbyName(@PathVariable String name){
        return gameRepository.findGamesByNameContaining(name);
    }
    @GetMapping("/games/{name}")
    public Game getGamebyName(@PathVariable String name){
        return gameRepository.findGameByName(name);
    }

    @PostMapping("/games")
    public Game addGame(@RequestBody Game game){
        gameRepository.save(game);
        return game;
    }


}
