package com.alexanders.recipeapp.controllers;

import java.util.Optional;

import com.alexanders.recipeapp.RecipeService;
import com.alexanders.recipeapp.domain.Category;
import com.alexanders.recipeapp.domain.Recipe;
import com.alexanders.recipeapp.repositories.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final RecipeService recipeService;

    public IndexController(CategoryRepository categoryRepository, RecipeService recipeService) {
        this.categoryRepository = categoryRepository;
        this.recipeService = recipeService;
    }

    @RequestMapping(path = {"/", "", "/index"})
    public String getIndexPage() {
        System.out.println("1");
        return "index";
    }

    @RequestMapping(path = "/category/get/{name}")
    public String getCategory(@PathVariable String name, Model model) {
        Optional<Category> optionalCategory = categoryRepository.findByName(name);
        String categoryName = "undefined";
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            System.out.println(category);
            categoryName = category.getName();
        }
        model.addAttribute("category", categoryName);
        return "index";
    }

    @RequestMapping(path = "/recipes")
    public String getRecipes(Model model) {
        Iterable<Recipe> recipes = recipeService.getRecipes();
        model.addAttribute("recipes", recipes);
        return "index";
    }
}
