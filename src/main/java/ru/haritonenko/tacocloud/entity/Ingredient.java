package ru.haritonenko.tacocloud.entity;


import jakarta.persistence.*;
import lombok.Data;
import ru.haritonenko.tacocloud.enums.Type;


@Data
@Entity
@Table(name = "Ingredient")
public class Ingredient {

    @Id
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Type type;


}