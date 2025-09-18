package ru.haritonenko.tacocloud.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.haritonenko.tacocloud.db.cassandra.udt.IngredientUDT;
import ru.haritonenko.tacocloud.repository.IngredientRepository;

@Component
public class IngredientUDTByIdConverter implements Converter<String, IngredientUDT> {
    private final IngredientRepository ingredientRepo;

    @Autowired
    public IngredientUDTByIdConverter(IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @Override
    public IngredientUDT convert(String id) {
        var ing = ingredientRepo.findById(id).orElse(null);
        if (ing == null) return null;
        return new IngredientUDT(ing.getName(), ing.getType());
    }
}