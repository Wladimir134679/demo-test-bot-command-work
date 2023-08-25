package ru.test.command.tgbot.demobot.repository;

import org.springframework.stereotype.Component;
import ru.test.command.tgbot.demobot.model.product.NewProduct;

import java.util.Collection;


@Component
public interface Repository<T> {
    T get(Long id);
    public Collection<T> getAll();
}
