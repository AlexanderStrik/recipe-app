package com.alexanders.recipeapp.controllers;

import java.util.HashSet;
import java.util.Set;

import com.alexanders.recipeapp.RecipeService;
import com.alexanders.recipeapp.domain.Recipe;
import com.alexanders.recipeapp.repositories.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class IndexControllerTest {

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
    }

    @Test
    void getRecipes() {
        Set<Recipe> recipeSet = new HashSet<>();
        Recipe recipe1 = new Recipe();
        recipe1.setId(1L);
        recipeSet.add(recipe1);
        Recipe recipe2 = new Recipe();
        recipe2.setId(2L);
        recipeSet.add(recipe2);
        when(recipeService.getRecipes()).thenReturn(recipeSet);

        ArgumentCaptor<Set<Recipe>> setArgumentCaptor = ArgumentCaptor.forClass(Set.class);

        String recipes = indexController.getRecipes(model);
        assertEquals("index", recipes);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), setArgumentCaptor.capture());
        assertEquals(2, setArgumentCaptor.getValue().size());
    }
}