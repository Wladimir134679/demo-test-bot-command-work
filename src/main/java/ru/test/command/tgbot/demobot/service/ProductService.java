package ru.test.command.tgbot.demobot.service;

import ru.test.command.tgbot.demobot.model.product.NewProduct;

public interface ProductService {
    NewProduct getByMessage(String message);
}
