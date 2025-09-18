package ru.haritonenko.tacocloud.controller;



import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.haritonenko.tacocloud.db.cassandra.udt.TacoUDT;
import ru.haritonenko.tacocloud.entity.Ingredient;
import ru.haritonenko.tacocloud.entity.Taco;
import ru.haritonenko.tacocloud.entity.TacoOrder;
import ru.haritonenko.tacocloud.enums.Type;
import ru.haritonenko.tacocloud.repository.IngredientRepository;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("tacoOrder")
public class DesignTacoController {

    private final IngredientRepository ingredientRepo;

    @Autowired
    public DesignTacoController(
            IngredientRepository ingredientRepo) {
        this.ingredientRepo = ingredientRepo;
    }

    @ModelAttribute
    public void addIngredientsToModel(Model model) {
        List<Ingredient> ingredients = StreamSupport
                .stream(ingredientRepo.findAll().spliterator(), false)
                .collect(Collectors.toList()); //Это из-за возврата Iterable от CrudRepository

        Type[] types = Type.values();
        for (Type type : types) {
            model.addAttribute(type.toString().toLowerCase(),
                    filterByType(ingredients, type));
        }
    }

    private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
        return ingredients.stream()
                .filter(ing -> ing.getType() == type)
                .collect(Collectors.toList());
    }

    @ModelAttribute("tacoOrder")
    private TacoOrder order(){
        return new TacoOrder();
    }

    @ModelAttribute("taco")
    private Taco taco(){
        return new Taco();
    }

    @GetMapping
    public String showDesignForm() {
        return "design";
    }

    @PostMapping
    public String processTaco(@Valid TacoUDT taco, Errors errors, @ModelAttribute TacoOrder tacoOrder){
        if (errors.hasErrors()) {
            return "design";
        }
        tacoOrder.addTaco(taco);
        log.info("Processing taco: {}",taco);

        return "redirect:/orders/current";
    }




}
