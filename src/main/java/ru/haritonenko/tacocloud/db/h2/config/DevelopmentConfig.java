package ru.haritonenko.tacocloud.db.h2.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.haritonenko.tacocloud.entity.Ingredient;
import ru.haritonenko.tacocloud.entity.Taco;
import ru.haritonenko.tacocloud.entity.TacoOrder;
import ru.haritonenko.tacocloud.entity.user.User;
import ru.haritonenko.tacocloud.enums.Type;
import ru.haritonenko.tacocloud.repository.IngredientRepository;
import ru.haritonenko.tacocloud.repository.OrderRepository;

import ru.haritonenko.tacocloud.repository.UserRepository;

import java.util.Arrays;

//Класс нужен для заполнения тестовой бд данным для разработки.

@Profile({"!prod", "!qa"})
@Configuration
public class DevelopmentConfig {

    @Bean
    public CommandLineRunner dataLoader(IngredientRepository ingRepo,
                                        UserRepository userRepo,
                                        PasswordEncoder encoder,
                                        OrderRepository orderRepo) {
        return args -> {
            // 1) ингредиенты -> СНАЧАЛА СОХРАНИТЬ
            Ingredient flourTortilla = new Ingredient("FLTO","Flour Tortilla", Type.WRAP);
            Ingredient cornTortilla  = new Ingredient("COTO","Corn Tortilla",  Type.WRAP);
            Ingredient groundBeef    = new Ingredient("GRBF","Ground Beef",    Type.PROTEIN);
            Ingredient carnitas      = new Ingredient("CARN","Carnitas",       Type.PROTEIN);
            Ingredient tomatoes      = new Ingredient("TMTO","Diced Tomatoes", Type.VEGGIES);
            Ingredient lettuce       = new Ingredient("LETC","Lettuce",        Type.VEGGIES);
            Ingredient cheddar       = new Ingredient("CHED","Cheddar",        Type.CHEESE);
            Ingredient jack          = new Ingredient("JACK","Monterrey Jack", Type.CHEESE);
            Ingredient salsa         = new Ingredient("SLSA","Salsa",          Type.SAUCE);
            Ingredient sourCream     = new Ingredient("SRCR","Sour Cream",     Type.SAUCE);

            ingRepo.saveAll(Arrays.asList(
                    flourTortilla, cornTortilla, groundBeef, carnitas,
                    tomatoes, lettuce, cheddar, jack, salsa, sourCream
            ));

            // 2) пользователь (обязателен для FK user_id NOT NULL)
            var user = userRepo.findByUsername("user");
            if (user == null) {
                user = new User(
                        "user", encoder.encode("password"),
                        "Test User", "123 Street", "City", "ST", "00000", "0000000000"
                );
                userRepo.save(user);
            }

            // 3) заказ
            TacoOrder order = new TacoOrder();
            order.setUser(user);
            order.setDeliveryName("Test User");
            order.setDeliveryStreet("123 Street");
            order.setDeliveryCity("City");
            order.setDeliveryState("ST");
            order.setDeliveryZip("00000");
            order.setCcNumber("4111111111111111");
            order.setCcExpiration("12/30");
            order.setCcCVV("123");

            // 4) тако — ТОЛЬКО через addTaco (он проставит обратную ссылку)
            Taco taco1 = new Taco();
            taco1.setName("Carnivore");
            taco1.setIngredients(Arrays.asList(flourTortilla, groundBeef, carnitas, sourCream, salsa, cheddar));
            order.addTaco(taco1);

            Taco taco2 = new Taco();
            taco2.setName("Bovine Bounty");
            taco2.setIngredients(Arrays.asList(cornTortilla, groundBeef, cheddar, jack, sourCream));
            order.addTaco(taco2);

            Taco taco3 = new Taco();
            taco3.setName("Veg-Out");
            taco3.setIngredients(Arrays.asList(flourTortilla, cornTortilla, tomatoes, lettuce, salsa));
            order.addTaco(taco3);

            // 5) сохранить ТОЛЬКО заказ — каскад сохранит Taco и связи Ingredient_Ref
            orderRepo.save(order);
        };
    }
}