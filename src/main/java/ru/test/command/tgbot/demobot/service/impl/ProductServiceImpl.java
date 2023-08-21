package ru.test.command.tgbot.demobot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.test.command.tgbot.demobot.model.product.NewProduct;
import ru.test.command.tgbot.demobot.service.MessageService;
import ru.test.command.tgbot.demobot.service.ProductService;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    MessageService messageService;

    public NewProduct getByMessage(String message) {
        String productName = messageService.getProductName( message );
        String productDescription = messageService.getDescription( message );
        String productPrice = messageService.getPrice( message );
        return new NewProduct();
    }
}
