package fact.it.gameinfoservice.repository;

import fact.it.gameinfoservice.model.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface GameRepository extends MongoRepository<Game, Integer> {
    Game findGameByName(String name);
    List<Game> findGamesByNameContaining(String name);
}
