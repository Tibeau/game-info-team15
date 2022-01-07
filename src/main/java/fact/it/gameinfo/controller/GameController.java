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

    @GetMapping("/games")
    public List<Game> getGames(){
        return gameRepository.findAll();
    }


    @PostMapping("/games")
    public Game addGame(@RequestBody Game game){
        gameRepository.save(game);
        return game;
    }

    @GetMapping("/games/developer/{developerName}")
    public List<Game> getGamesByDeveloperName(@PathVariable String developerName){
        return gameRepository.findGamesByDeveloperName(developerName);
    }

    @DeleteMapping("/games/{name}")
    public void deleteGame(@PathVariable String name){
        Game game = gameRepository.findGameByName(name);
        gameRepository.delete(game);
    }

    @PutMapping("/games/{name}")
    public Game changeGame(@RequestBody Game game, @PathVariable String name){
        Game game1 = gameRepository.findGameByName(name);
        game1.setName(game.getName());
        game1.setRelease_year(game.getRelease_year());
        game1.setDeveloperName(game.getDeveloperName());
        game1.setSales(game.getSales());
        return gameRepository.save(game1);
    }
}

