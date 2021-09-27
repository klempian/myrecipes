package pl.klemp.ian.myrecipes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.klemp.ian.myrecipes.dto.RecipeDto;
import pl.klemp.ian.myrecipes.model.Recipe;
import pl.klemp.ian.myrecipes.service.RecipeService;

import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private RecipeService recipeService;

    private final UUID uuid = UUID.randomUUID();
    private final RecipeDto mockRecipeDto = new RecipeDto();
    private final Recipe mockRecipe = new Recipe();

    @BeforeEach
    public void setup() {
        mockRecipeDto.setName("New test recipe name");
        mockRecipeDto.setImage(new ArrayList<>());

        mockRecipe.setName("Test Recipe name");
    }

    @Test
    public void create_success() throws Exception {
        String json = objectMapper.writeValueAsString(mockRecipeDto);

        when(recipeService.save(Mockito.any())).thenReturn(uuid);

        mockMvc.perform(post("/api/recipes/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(redirectedUrlPattern("http*://*/api/recipes/" + uuid));
    }

    @Test
    public void update_success() throws Exception {
        String json = objectMapper.writeValueAsString(mockRecipeDto);

        when(recipeService.findById(Mockito.any())).thenReturn(mockRecipe);
        when(recipeService.save(Mockito.any())).thenReturn(uuid);

        mockMvc.perform(put("/api/recipes/"+ uuid)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());

        Assertions.assertEquals(mockRecipe.getName(), mockRecipeDto.getName());
    }

    @Test
    public void getById_success() throws Exception {

        when(recipeService.findById(Mockito.any())).thenReturn(mockRecipe);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/recipes/" + uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Test Recipe name")));
    }
}
