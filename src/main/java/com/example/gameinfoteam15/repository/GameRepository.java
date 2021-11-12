package com.example.gameinfoteam15.repository;

import com.example.gameinfoteam15.model.Game;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Repository
public interface GameRepository extends MongoRepository<Game, Integer> {
    Game findGameByName(String name);
    List<Game> findGamesByNameContaining(String name);
}
