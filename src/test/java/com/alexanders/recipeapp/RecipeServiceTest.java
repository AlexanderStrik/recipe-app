package com.alexanders.recipeapp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.alexanders.recipeapp.domain.Recipe;
import com.alexanders.recipeapp.repositories.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class RecipeServiceTest {

    private RecipeService recipeService;
    @Mock
    private RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        recipeService = new RecipeService(recipeRepository);
    }

    @Test
    void getRecipes() {
        Recipe recipe = new Recipe();
        Set<Recipe> recipes = new HashSet<>();
        recipes.add(recipe);
        when(recipeRepository.findAll()).thenReturn(recipes);

        Set<Recipe> actualRecipes = recipeService.getRecipes();
        assertEquals(actualRecipes.size(), 1);
        verify(recipeRepository, times(1)).findAll();
    }
}