package ru.test.command.tgbot.demobot.model.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public  abstract class Product {
    String name;
    String description;
    Double price;


}
