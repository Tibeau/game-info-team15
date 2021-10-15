package fact.it.gameinfoservice.repository;

import fact.it.gameinfoservice.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {
    Game findGameByName(String name);
    List<Game> findGamesByNameContaining(String name);
}
