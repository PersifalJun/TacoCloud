package ru.haritonenko.tacocloud.repository;

import org.springframework.data.repository.CrudRepository;

import ru.haritonenko.tacocloud.entity.Ingredient;



public interface IngredientRepository extends CrudRepository<Ingredient, String> {


}
