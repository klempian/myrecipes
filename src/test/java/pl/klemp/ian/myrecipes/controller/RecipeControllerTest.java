package pl.klemp.ian.myrecipes.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.klemp.ian.myrecipes.dto.RecipeDto;
import pl.klemp.ian.myrecipes.dto.RecipeThumbnailDto;
import pl.klemp.ian.myrecipes.model.Category;
import pl.klemp.ian.myrecipes.model.Keyword;
import pl.klemp.ian.myrecipes.model.Recipe;
import pl.klemp.ian.myrecipes.service.CategoryService;
import pl.klemp.ian.myrecipes.service.KeywordService;
import pl.klemp.ian.myrecipes.service.RecipeService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    @MockBean
    private CategoryService categoryService;

    private final UUID uuid = UUID.randomUUID();
    private static final RecipeDto mockRecipeDto = new RecipeDto();
    private final Recipe mockRecipe = new Recipe();

    @BeforeAll
    static void setupAll() {
        mockRecipeDto.setName("New test recipe name");
    }

    @Nested
    @DisplayName("Single Recipe tests")
    class SingleRecipeTests {

        @BeforeEach
        public void setup() {
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
                    .andExpect(redirectedUrlPattern("http*://*/api/recipes/" + uuid)
                    );
        }

        @Test
        public void getById_success() throws Exception {

            when(recipeService.findByUuid(uuid)).thenReturn(mockRecipe);

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/recipes/" + uuid)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", notNullValue()))
                    .andExpect(jsonPath("$.name", is("Test Recipe name"))
                    );
        }

        @Test
        public void update_success() throws Exception {
            String json = objectMapper.writeValueAsString(mockRecipeDto);

            when(recipeService.findByUuid(uuid)).thenReturn(mockRecipe);
            when(recipeService.save(Mockito.any())).thenReturn(uuid);

            mockMvc.perform(put("/api/recipes/" + uuid)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(json)
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isAccepted()
                    );

            Assertions.assertEquals(mockRecipe.getName(), mockRecipeDto.getName());
        }

        @Test
        public void deleteRecipeById() throws Exception {

            mockMvc.perform(delete("/api/recipes/" + uuid))
                    .andExpect(status().isOk()
                    );
        }
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
            Category mockCategory = mock(Category.class);

            when(categoryService.findByName(Mockito.any())).thenReturn(mockCategory);
            when(recipeService.findAllByRecipeCategory(mockCategory)).thenReturn(recipeList);

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/recipes/category?name=" + Mockito.any())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(2)))
                    .andExpect(jsonPath("$[1]", aMapWithSize(RecipeThumbnailDto.class.getDeclaredFields().length)))
                    .andExpect(jsonPath("$[1].name", is("Second test Recipe name"))
            );
        }

        @Test
        public void getByKeyword_success() throws Exception {
            Keyword mockKeyword = mock(Keyword.class);

            when(keywordService.findByName(Mockito.any())).thenReturn(Optional.of(mockKeyword));
            when(recipeService.findAllByKeyword(mockKeyword)).thenReturn(recipeList);

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/recipes/keyword?name=" + Mockito.any())
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

            mockMvc.perform(MockMvcRequestBuilders
                            .get("/api/recipes/all")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$", hasSize(recipeList.size())))
                    .andExpect(jsonPath("$[1]", aMapWithSize(RecipeThumbnailDto.class.getDeclaredFields().length)))
                    .andExpect(jsonPath("$[1].name", is(recipeList.get(1).getName()))
            );
        }
    }
}
