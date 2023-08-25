package ru.test.command.tgbot.demobot.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor

public class NewProduct extends Product{
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    public NewProduct(Long id,String name, String description, Double price) {
        super( name, description, price );
        this.id = id;
    }

}
