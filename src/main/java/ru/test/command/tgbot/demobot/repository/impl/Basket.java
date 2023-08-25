package ru.test.command.tgbot.demobot.repository.impl;

import lombok.Data;
import org.springframework.stereotype.Component;
import ru.test.command.tgbot.demobot.model.product.NewProduct;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Component
public class Basket {
    private final ConcurrentHashMap<Long, List<NewProduct>> basket = new ConcurrentHashMap<>();

    public List<NewProduct> getByUserId(Long userId) {
        List<NewProduct> newProductList;
        newProductList = basket.get( userId );
        return Objects.requireNonNullElseGet( newProductList, ArrayList::new );

    }

    public void save(Long userId, NewProduct newProduct) {
        List<NewProduct> newProductList = basket.get( userId );
        newProductList = Objects.requireNonNullElseGet( newProductList, ArrayList::new );
        newProductList.add( newProduct );
        basket.put( userId,newProductList );
    }
}
