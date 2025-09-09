package ru.haritonenko.tacocloud.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.haritonenko.tacocloud.ref.IngredientRef;

@Component
public class IngredientRefByIdConverter implements Converter<String, IngredientRef> {
    @Override public IngredientRef convert(String id) { return new IngredientRef(id); }
}