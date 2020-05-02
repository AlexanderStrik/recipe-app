package com.alexanders.recipeapp.repositories;

import java.util.Optional;

import com.alexanders.recipeapp.domain.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
