package com.alexanders.recipeapp.repositories;

import java.util.Optional;

import com.alexanders.recipeapp.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class UnitOfMeasureRepositoryIT {

    @Autowired
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @Test
    void findByDescription() {
        String teaspoon = "Teaspoon";
        Optional<UnitOfMeasure> optionalTeaspoon = unitOfMeasureRepository.findByDescription(teaspoon);
        assertTrue(optionalTeaspoon.isPresent());
        assertEquals(teaspoon, optionalTeaspoon.get().getDescription());
    }
}