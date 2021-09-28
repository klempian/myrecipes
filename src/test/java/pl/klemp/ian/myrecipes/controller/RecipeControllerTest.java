package pl.klemp.ian.myrecipes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
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
import pl.klemp.ian.myrecipes.dto.RecipeThumbnailDto;
import pl.klemp.ian.myrecipes.model.Keyword;
import pl.klemp.ian.myrecipes.model.Recipe;
import pl.klemp.ian.myrecipes.service.KeywordService;
import pl.klemp.ian.myrecipes.service.RecipeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
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
    @MockBean
    private KeywordService keywordService;

    private final UUID uuid = UUID.randomUUID();
    private final RecipeDto mockRecipeDto = new RecipeDto();
    private final Recipe mockRecipe = new Recipe();

    @BeforeEach
    public void setup() {
        mockRecipeDto.setName("New test recipe name");

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

        when(recipeService.findById(uuid)).thenReturn(mockRecipe);
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

        when(recipeService.findById(uuid)).thenReturn(mockRecipe);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/recipes/" + uuid)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Test Recipe name")));
    }

    @Test
    public void deleteRecipeById() {
    }

    @Nested
    @DisplayName("Tests with lists of Recipes")
    class recipeListTests {

        private final List<Recipe> recipeList = new ArrayList<>();

        @BeforeEach
        public void setup() {

            recipeList.add(mockRecipe);
            recipeList.add(
                    Recipe.builder()
                    .name("Second test Recipe name")
                    .build()
            );
        }

        @Test
        public void getByCategory_success() throws Exception {
            when(recipeService.findAllByRecipeCategoryId(uuid)).thenReturn(recipeList);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/category?id=" + uuid)
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[1]", aMapWithSize(RecipeThumbnailDto.class.getDeclaredFields().length)))
                    .andExpect(jsonPath("$[1].name", is("Second test Recipe name"))
            );
        }

        @Test
        public void getByKeyword_success() throws Exception {
            Optional<Keyword> optionalKeyword= Optional.of(new Keyword("test keyword"));

            when(keywordService.findByName(optionalKeyword.get().getName())).thenReturn(optionalKeyword);
            when(recipeService.findAllByKeyword(optionalKeyword.get())).thenReturn(recipeList);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/keyword?key=" + optionalKeyword.get().getName())
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(recipeList.size())))
                    .andExpect(jsonPath("$[1]", aMapWithSize(RecipeThumbnailDto.class.getDeclaredFields().length)))
                    .andExpect(jsonPath("$[1].name", is(recipeList.get(1).getName()))
            );
        }

        @Test
        public void getAllRecipes_success() throws Exception {
            when(recipeService.findAll()).thenReturn(recipeList);

            mockMvc.perform(MockMvcRequestBuilders.get("/api/recipes/all")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(recipeList.size())))
                    .andExpect(jsonPath("$[1]", aMapWithSize(RecipeThumbnailDto.class.getDeclaredFields().length)))
                    .andExpect(jsonPath("$[1].name", is(recipeList.get(1).getName()))
                    );
        }
    }
}
