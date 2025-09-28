package ru.haritonenko.tacocloud.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.haritonenko.tacocloud.entity.Ingredient;
import ru.haritonenko.tacocloud.repository.IngredientRepository;

@Service
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Iterable<Ingredient> findAllIngredients() {
        return ingredientRepository.findAll();

    }

    public Ingredient saveIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredientById(String ingredientId) {
        ingredientRepository.deleteById(ingredientId);
    }
}
