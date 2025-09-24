package ru.haritonenko.tacocloud.entity;

import jakarta.persistence.*;


import lombok.Data;


import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "Taco")
public class Taco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "taco_order", nullable = false,
            foreignKey = @ForeignKey(name = "fk_taco_order"))
    private TacoOrder tacoOrder;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @ManyToMany
    @JoinTable(
            name = "Ingredient_Ref",
            joinColumns = @JoinColumn(name = "taco"),
            inverseJoinColumns = @JoinColumn(name = "ingredient")
    )
    private List<Ingredient> ingredients;


    @PrePersist
    void setCreatedAt() {
        this.createdAt = new Date();
    }
    @Override
    public String toString() {
        String ing = (ingredients == null) ? "[]" :
                ingredients.stream().map(Ingredient::getId).toList().toString();
        return "Taco{id=" + id + ", name=" + name + ", ingredients=" + ing + "}";
    }
}