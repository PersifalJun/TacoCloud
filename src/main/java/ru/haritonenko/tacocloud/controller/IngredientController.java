package ru.haritonenko.tacocloud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.haritonenko.tacocloud.entity.Ingredient;
import ru.haritonenko.tacocloud.service.IngredientService;

@RestController
@RequestMapping(path="/api/ingredients", produces="application/json")
@CrossOrigin(origins="http://localhost:8080")
public class IngredientController {
    private final IngredientService ingredientService;
    @Autowired
    public IngredientController(IngredientService ingredientService) {
        this.ingredientService = ingredientService;
    }
    @GetMapping
    public Iterable<Ingredient> allIngredients() {
        return ingredientService.findAllIngredients();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("#{hasRole('ADMIN')}")
    public Ingredient saveIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.saveIngredient(ingredient);
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("#{hasRole('ADMIN')}")
    public void deleteIngredient(@PathVariable("id") String ingredientId) {
        ingredientService.deleteIngredientById(ingredientId);
    }
}