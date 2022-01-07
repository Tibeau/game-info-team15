package fact.it.gameinfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
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
import com.fasterxml.jackson.databind.json.JsonMapper;

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

    private Game game1 = new Game("tester1",2020,"Activision",23000);
    private Game game2 = new Game("test2",2250,"Blizzard",20052);
    private Game game3 = new Game("test3",2019,"Activision",29500);
    private Game game4 = new Game("test4",2032,"Blizzard",20950);
    private Game gameToBeDeleted5 = new Game("test99",2020,"Bethesda",21250);

    @BeforeEach
    public void beforeAllTests() {
        gameRepository.deleteAll();
        gameRepository.save(game1);
        gameRepository.save(game2);
        gameRepository.save(game3);
        gameRepository.save(game4);
        gameRepository.save(gameToBeDeleted5);

    }
    @AfterEach
    public void afterAllTests() {
        //Watch out with deleteAll() methods when you have other data in the test database!
        gameRepository.deleteAll();
    }


    private final ObjectMapper mapper = JsonMapper.builder().addModule(new Jdk8Module()).addModule(new ParameterNamesModule()).addModule(new JavaTimeModule()).build();

    @Test
    public void givenGame_whenGetGamesByName_thenReturnJsonGames() throws Exception {

        List<Game> gamesList = new ArrayList<>();
        gamesList.add(game1);

        mockMvc.perform(get("/games/name/{name}", "teste"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("tester1")))
                .andExpect(jsonPath("$[0].release_year", is(2020)))
                .andExpect(jsonPath("$[0].developerName", is("Activision")))
                .andExpect(jsonPath("$[0].sales", is(23000)));
    }

    @Test
    public void givenGames_whenGetGames_thenReturnJsonGames() throws Exception {

        List<Game> gamesList = new ArrayList<>();
        gamesList.add(game1);

        mockMvc.perform(get("/games"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].name", is("tester1")))
                .andExpect(jsonPath("$[0].release_year", is(2020)))
                .andExpect(jsonPath("$[0].developerName", is("Activision")))
                .andExpect(jsonPath("$[0].sales", is(23000)))
                .andExpect(jsonPath("$[1].name", is("test2")))
                .andExpect(jsonPath("$[2].name", is("test3")))
                .andExpect(jsonPath("$[3].name", is("test4")));
    }

    @Test
    public void givenGames_whenGetGamesByDeveloper_thenReturnJsonGames() throws Exception {

        List<Game> gamesList = new ArrayList<>();
        gamesList.add(game1);

        mockMvc.perform(get("/games/developer/{developer}", "Activision"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("tester1")))
                .andExpect(jsonPath("$[0].release_year", is(2020)))
                .andExpect(jsonPath("$[0].developerName", is("Activision")))
                .andExpect(jsonPath("$[0].sales", is(23000)));
    }

    @Test
    public void givenGame_whenGetGameByName_thenReturnJsonGames() throws Exception {

        mockMvc.perform(get("/games/{name}", "test2"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("test2")))
                .andExpect(jsonPath("$.release_year", is(2250)))
                .andExpect(jsonPath("$.developerName", is("Blizzard")))
                .andExpect(jsonPath("$.sales", is(20052)));


    }

    @Test
    public void whenPostGame_thenReturnJsonGame() throws Exception {
        Game game6 = new Game("test6", 1999 , "Blizzard", 813284);

        mockMvc.perform(post("/games")
                .content(mapper.writeValueAsString(game6))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("test6")))
                .andExpect(jsonPath("$.release_year", is(1999)))
                .andExpect(jsonPath("$.developerName", is("Blizzard")))
                .andExpect(jsonPath("$.sales", is(813284)));
    }



    @Test
    public void GivenGame_whenPutGame_thenReturnJsonGame() throws Exception {
        Game updatedGame = new Game("test15", 1899 , "Activision", 84);

        mockMvc.perform(put("/games/{name}", "tester1")
                 .content(mapper.writeValueAsString(updatedGame))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("test15")))
                .andExpect(jsonPath("$.release_year", is(1899)))
                .andExpect(jsonPath("$.developerName", is("Activision")))
                .andExpect(jsonPath("$.sales", is(84)));
    }

    @Test
    public void givenGame_whenDeleteGame_thenStatusOk() throws Exception {

        mockMvc.perform(delete("/games/{name}", "test99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
