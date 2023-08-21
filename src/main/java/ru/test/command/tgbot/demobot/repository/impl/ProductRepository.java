package ru.test.command.tgbot.demobot.repository.impl;

import org.springframework.stereotype.Component;
import ru.test.command.tgbot.demobot.model.product.NewProduct;
import ru.test.command.tgbot.demobot.repository.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ProductRepository implements Repository<NewProduct> {

    private final Map<Long, NewProduct> productMap = new HashMap<>();

    {
        productMap.put( 1L, new NewProduct( 1L, "Telephone", "Samsung", 100.0 ) );
        productMap.put( 2L, new NewProduct( 2L, "Photo", "Cannon", 200.0 ) );
        productMap.put( 3L, new NewProduct( 3L, "Telephone", "Apple", 300.0 ) );
        productMap.put( 4L, new NewProduct( 4L, "Telephone", "Xiaomi", 400.0 ) );
    }


    public void save(NewProduct newProduct) {
        productMap.put( newProduct.getId(), newProduct );
    }

    @Override
    public NewProduct get(Long id) {
        return productMap.get( id );
    }

    public List<NewProduct> getAll() {
        return productMap.values().stream().toList();
    }

}
