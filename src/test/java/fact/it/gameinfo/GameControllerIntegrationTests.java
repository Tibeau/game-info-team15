package fact.it.gameinfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import fact.it.gameinfo.model.Game;
import fact.it.gameinfo.repository.GameRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private GameRepository gameRepository;

    private Game game1 = new Game("test",2020,"Activision",20000);

    @BeforeEach
    public void beforeAllTests() {
        gameRepository.deleteAll();
        gameRepository.save(game1);

    }
    @AfterEach
    public void afterAllTests() {
        //Watch out with deleteAll() methods when you have other data in the test database!
        gameRepository.deleteAll();
    }


    @Test
    public void givenGame_whenGetGamesByName_thenReturnJsonGames() throws Exception {

        List<Game> gamesList = new ArrayList<>();
        gamesList.add(game1);

        mockMvc.perform(get("/games/name/{name}", "tes"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("test")))
                .andExpect(jsonPath("$[0].release_year", is(2020)))
                .andExpect(jsonPath("$[0].developer", is("Activision")))
                .andExpect(jsonPath("$[0].sales", is(20000)));

    }
    @Test
    public void givenGame_whenGetGameByName_thenReturnJsonGames() throws Exception {

        mockMvc.perform(get("/games/{name}", "test"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("test")))
                .andExpect(jsonPath("$.release_year", is(2020)))
                .andExpect(jsonPath("$.developer", is("Activision")))
                .andExpect(jsonPath("$.sales", is(20000)));


    }

}
