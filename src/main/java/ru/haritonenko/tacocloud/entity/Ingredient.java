package ru.haritonenko.tacocloud.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.haritonenko.tacocloud.enums.Type;



@Data
@Entity
@Table(name = "Ingredient")
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    @Id
    private String id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Type type;

}