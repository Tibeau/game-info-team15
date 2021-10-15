package fact.it.gameinfoservice.controller;

import fact.it.gameinfoservice.model.Game;
import fact.it.gameinfoservice.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
        System.out.println(gameRepository.findGameByName("Call of Duty: Cold War").getSales());
    }

    @GetMapping("/games/name/{name}")
    public List<Game> getGamesbyName(@PathVariable String name){
        return gameRepository.findGamesByNameContaining(name);
    }


}