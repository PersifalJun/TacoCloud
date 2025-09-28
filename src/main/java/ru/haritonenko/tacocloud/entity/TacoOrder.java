
package ru.haritonenko.tacocloud.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import ru.haritonenko.tacocloud.entity.user.User;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@Entity
@Table(name = "Taco_Order")
public class TacoOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private static final long serialVersionUID = 1L;
    @Column(name = "placed_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date placedAt;


    @NotBlank(message="Delivery name is required")
    @Column(name = "delivery_name")
    private String deliveryName;

    @NotBlank(message="Street is required")
    @Column(name = "delivery_street")
    private String deliveryStreet;

    @NotBlank(message="City is required")
    @Column(name = "delivery_city")
    private String deliveryCity;

    @NotBlank(message="State is required")
    @Column(name = "delivery_state")
    private String deliveryState;

    @NotBlank(message="Zip code is required")
    @Column(name = "delivery_zip")
    private String deliveryZip;

    @CreditCardNumber(message="Not a valid credit card number")
    @Column(name = "cc_number")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
            message="Must be formatted MM/YY")
    @Column(name = "cc_expiration")
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="Invalid CVV")
    @Column(name = "cc_cvv")
    private String ccCVV;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "fk_order_user"),nullable = false)
    @JsonIgnore
    private User user;

    @OneToMany(
            mappedBy = "tacoOrder",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderColumn(name = "taco_order_key")
    @JsonIgnore
    private List<Taco> tacos = new ArrayList<>();

    public void addTaco(Taco taco) {
        this.tacos.add(taco);
        taco.setTacoOrder(this);
    }

    @PrePersist
    void setPlacedAt() {
        this.placedAt = new Date();
    }
}