package com.alexanders.recipeapp.controllers;

import java.util.HashSet;
import java.util.Set;

import com.alexanders.recipeapp.domain.Recipe;
import com.alexanders.recipeapp.repositories.CategoryRepository;
import com.alexanders.recipeapp.services.RecipeService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(SpringExtension.class)
class IndexControllerTest {

    MockMvc mockMvc;
    private IndexController indexController;
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private RecipeService recipeService;
    @Mock
    private Model model;

    @BeforeEach
    void setUp() {
        indexController = new IndexController(categoryRepository, recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
    }

    @SneakyThrows
    @Test
    void testGetRecipes() {
        mockMvc.perform(get("/recipes"))
               .andExpect(status().isOk())
               .andExpect(view().name("index"))
               .andExpect(model().attributeExists("recipes"));
    }

    @SneakyThrows
    @Test
    void testShowRecipe() {
        final int id = 1;
        Recipe recipe = new Recipe();
        recipe.setId(id);
        when(recipeService.getRecipeById(id)).thenReturn(recipe);
        mockMvc.perform(get(String.format("/recipe/show/%s", id)))
               .andExpect(status().isOk())
               .andExpect(view().name("recipe/index"))
               .andExpect(model().attributeExists("recipe"));
    }

    @Test
    void getRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        Recipe recipe1 = new Recipe();
        recipe1.setId(1);
        recipeSet.add(recipe1);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2);
        recipeSet.add(recipe2);
        when(recipeService.getRecipes()).thenReturn(recipeSet);

        ArgumentCaptor<Set<Recipe>> setArgumentCaptor = ArgumentCaptor.forClass(Set.class);

        String recipes = indexController.getRecipes(model);
        assertEquals("index", recipes);
        verify(recipeService).getRecipes();
        verify(model).addAttribute(eq("recipes"), setArgumentCaptor.capture());
        assertEquals(2, setArgumentCaptor.getValue().size());
    }

    @Test
    void showRecipe() {
        final int id = 1;
        Recipe recipe = new Recipe();
        recipe.setId(id);

        when(recipeService.getRecipeById(id)).thenReturn(recipe);

        ArgumentCaptor<Recipe> recipeArgumentCaptor = ArgumentCaptor.forClass(Recipe.class);

        String recipes = indexController.showRecipe(model, id);
        assertEquals("recipe/index", recipes);
        verify(recipeService).getRecipeById(id);
        verify(model).addAttribute(eq("recipe"), recipeArgumentCaptor.capture());
        assertEquals(id, recipeArgumentCaptor.getValue().getId());
    }
}