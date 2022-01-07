package fact.it.gameinfo;

        import com.fasterxml.jackson.databind.ObjectMapper;
        import com.fasterxml.jackson.databind.json.JsonMapper;
        import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
        import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
        import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
        import fact.it.gameinfo.model.Game;
        import fact.it.gameinfo.repository.GameRepository;
        import org.junit.jupiter.api.Test;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
        import org.springframework.boot.test.context.SpringBootTest;
        import org.springframework.boot.test.mock.mockito.MockBean;
        import org.springframework.http.MediaType;
        import org.springframework.test.web.servlet.MockMvc;

        import java.util.ArrayList;
        import java.util.List;

        import static org.hamcrest.Matchers.is;
        import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
        import static org.mockito.BDDMockito.given;
        import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
        import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerUnitTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameRepository gameRepository;


    private final ObjectMapper mapper = JsonMapper.builder().addModule(new Jdk8Module()).addModule(new ParameterNamesModule()).addModule(new JavaTimeModule()).build();

    @Test
    public void givenGame_whenGetGamesByName_thenReturnJsonGames() throws Exception {

        Game game1 = new Game("tester1", 2592, "Blizzard", 81135);

        List<Game> gameList = new ArrayList<>();
        gameList.add(game1);

        given(gameRepository.findGamesByNameContaining("test")).willReturn(gameList);

        mockMvc.perform(get("/games/name/{name}", "test"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("tester1")))
                .andExpect(jsonPath("$[0].release_year", is(2592)))
                .andExpect(jsonPath("$[0].developerName", is("Blizzard")))
                .andExpect(jsonPath("$[0].sales", is(81135)));
    }

    @Test
    public void givenGames_whenGetGames_thenReturnJsonGames() throws Exception {
        Game game1 = new Game("tester1", 2592, "Activision", 81135);
        Game game2 = new Game("tester2", 2292, "Activision", 87135);
        Game game3 = new Game("tester3", 2002, "Blizzard", 83535);
        List<Game> gameList = new ArrayList<>();
        gameList.add(game1);
        gameList.add(game2);
        gameList.add(game3);
        given(gameRepository.findAll()).willReturn(gameList);

        mockMvc.perform(get("/games"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("tester1")))
                .andExpect(jsonPath("$[0].release_year", is(2592)))
                .andExpect(jsonPath("$[0].developerName", is("Activision")))
                .andExpect(jsonPath("$[0].sales", is(81135)))
                .andExpect(jsonPath("$[1].name", is("tester2")))
                .andExpect(jsonPath("$[2].name", is("tester3")));
    }

    @Test
    public void givenGames_whenGetGamesByDeveloper_thenReturnJsonGames() throws Exception {

        Game game1 = new Game("tester1", 2592, "Activision", 81135);
        Game game2 = new Game("tester2", 2292, "Activision", 87135);
        List<Game> devList = new ArrayList<>();
        devList.add(game1);
        devList.add(game2);
        given(gameRepository.findGamesByDeveloperName("Activision")).willReturn(devList);

        mockMvc.perform(get("/games/developer/{developer}", "Activision"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("tester1")))
                .andExpect(jsonPath("$[0].release_year", is(2592)))
                .andExpect(jsonPath("$[0].developerName", is("Activision")))
                .andExpect(jsonPath("$[0].sales", is(81135)))
                .andExpect(jsonPath("$[1].name", is("tester2")))
                .andExpect(jsonPath("$[1].release_year", is(2292)))
                .andExpect(jsonPath("$[1].developerName", is("Activision")))
                .andExpect(jsonPath("$[1].sales", is(87135)));
    }

    @Test
    public void givenGame_whenGetGameByName_thenReturnJsonGames() throws Exception {

        Game game1 = new Game("tester1", 2592, "Blizzard", 81135);
        given(gameRepository.findGameByName("tester1")).willReturn(game1);

        mockMvc.perform(get("/games/{name}", "tester1"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("tester1")))
                .andExpect(jsonPath("$.release_year", is(2592)))
                .andExpect(jsonPath("$.developerName", is("Blizzard")))
                .andExpect(jsonPath("$.sales", is(81135)));


    }

    @Test
    public void whenPostGame_thenReturnJsonGame() throws Exception {
        Game game1 = new Game("test6", 1999 , "Blizzard", 813284);

        mockMvc.perform(post("/games")
                .content(mapper.writeValueAsString(game1))
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
        Game game1 = new Game("test15", 1800 , "Activision", 84);

        given(gameRepository.findGameByName("test15")).willReturn(game1);

        Game updatedGame = new Game("test15", 1899 , "Activision", 84);

        mockMvc.perform(put("/games/{game}", "test15")
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
        given(gameRepository.findGameByName("test99")).willReturn(null);

        mockMvc.perform(delete("/games/{name}", "test99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}