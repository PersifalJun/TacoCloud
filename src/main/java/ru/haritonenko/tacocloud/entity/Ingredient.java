package ru.haritonenko.tacocloud.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import ru.haritonenko.tacocloud.enums.Type;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor(access=AccessLevel.PUBLIC, force=true)
public class Ingredient {
    @Id
    private String id;
    private String name;
    private Type type;


}
