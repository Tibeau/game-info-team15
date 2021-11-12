package fact.it.gameinfo.repository;

import fact.it.gameinfo.model.Game;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Repository
public interface GameRepository extends MongoRepository<Game, String> {
    Game findGameByName(String name);
    List<Game> findGamesByNameContaining(String name);
}