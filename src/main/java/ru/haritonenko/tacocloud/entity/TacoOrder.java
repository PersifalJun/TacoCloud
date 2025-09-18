package ru.haritonenko.tacocloud.entity;


import com.datastax.oss.driver.api.core.uuid.Uuids;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.CreditCardNumber;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import ru.haritonenko.tacocloud.db.cassandra.udt.TacoUDT;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Table("orders")
public class TacoOrder {

    @PrimaryKey
    private UUID id = Uuids.timeBased();

    private static final long serialVersionUID = 1L;

    @Column("placed_at")
    private Date placedAt;


    @NotBlank(message="Delivery name is required")
    @Column("delivery_name")
    private String deliveryName;

    @NotBlank(message="Street is required")
    @Column("delivery_street")
    private String deliveryStreet;

    @NotBlank(message="City is required")
    @Column("delivery_city")
    private String deliveryCity;

    @NotBlank(message="State is required")
    @Column("delivery_state")
    private String deliveryState;

    @NotBlank(message="Zip code is required")
    @Column("delivery_zip")
    private String deliveryZip;

    @CreditCardNumber(message="Not a valid credit card number")

    @Column("cc_number")
    private String ccNumber;

    @Pattern(regexp="^(0[1-9]|1[0-2])([\\/])([2-9][0-9])$",
            message="Must be formatted MM/YY")
    @Column("cc_expiration")
    private String ccExpiration;

    @Digits(integer=3, fraction=0, message="Invalid CVV")
    @Column("cc_cvv")
    private String ccCVV;

    @Column("tacos")
    private List<TacoUDT> tacos = new ArrayList<>();

    public void addTaco(TacoUDT taco) {
        this.tacos.add(taco);
    }

}



