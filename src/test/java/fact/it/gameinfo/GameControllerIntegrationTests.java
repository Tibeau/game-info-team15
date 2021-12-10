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

    private Game gameDev1Name1 = new Game("Call of Duty: Cold War",2020,"Activision",20000);

    @BeforeEach
    public void beforeAllTests() {
        gameRepository.deleteAll();
        gameRepository.save(gameDev1Name1);

    }
    @AfterEach
    public void afterAllTests() {
        //Watch out with deleteAll() methods when you have other data in the test database!
        gameRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenGame_whenGetGameByDeveloperName_thenReturnJsonReview() throws Exception {

        mockMvc.perform(get("/games/name/{name}",  "name"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Call of Duty: Cold War")))
                .andExpect(jsonPath("$.release_year", is(2020)))
                .andExpect(jsonPath("$.developer", is("Activision")))
                .andExpect(jsonPath("$.sales", is(20000)));
    }
}
