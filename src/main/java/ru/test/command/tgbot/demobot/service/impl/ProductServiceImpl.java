package ru.test.command.tgbot.demobot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.test.command.tgbot.demobot.model.product.NewProduct;
import ru.test.command.tgbot.demobot.service.MessageService;
import ru.test.command.tgbot.demobot.service.ProductService;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
    private final MessageService messageService;
    private final SequenceImpl sequence;

    public ProductServiceImpl(MessageService messageService, SequenceImpl sequence) {
        this.messageService = messageService;
        this.sequence = sequence;
    }

    public NewProduct getByMessage(String message) {

        String productName = messageService.getProductName( message );
        String productDescription = messageService.getDescription( message );
        String productPrice = messageService.getPrice( message );

        NewProduct newProduct = new NewProduct();
        newProduct.setId( (long) sequence.get() );
        newProduct.setName( productName );
        newProduct.setDescription( productDescription );
        newProduct.setPrice( Double.valueOf( productPrice ) );

        return newProduct;
    }
}
