package ru.haritonenko.tacocloud.utils;



import org.springframework.stereotype.Component;
import ru.haritonenko.tacocloud.db.cassandra.udt.IngredientUDT;
import ru.haritonenko.tacocloud.entity.Ingredient;


@Component
public class TacoUDRUtils {


    public static IngredientUDT toIngredientUDT(Ingredient ingredient) {

        return new IngredientUDT(ingredient.getName(),ingredient.getType());

    }
}
