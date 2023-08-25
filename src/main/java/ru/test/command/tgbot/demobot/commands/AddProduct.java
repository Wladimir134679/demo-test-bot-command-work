package ru.test.command.tgbot.demobot.commands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.test.command.tgbot.demobot.model.product.NewProduct;
import ru.test.command.tgbot.demobot.repository.impl.ProductRepository;
import ru.test.command.tgbot.demobot.service.impl.AdminServiceImpl;
import ru.test.command.tgbot.demobot.service.impl.ProductServiceImpl;
import ru.wdeath.managerbot.lib.bot.TelegramLongPollingEngine;
import ru.wdeath.managerbot.lib.bot.annotations.CommandFirst;
import ru.wdeath.managerbot.lib.bot.annotations.CommandNames;
import ru.wdeath.managerbot.lib.bot.annotations.CommandOther;
import ru.wdeath.managerbot.lib.bot.annotations.ParamName;
import ru.wdeath.managerbot.lib.bot.command.CommandContext;

@Service
@Slf4j
@RequiredArgsConstructor
@CommandNames("/addProduct")
public class AddProduct {

    private final AdminServiceImpl adminService;
    private final ProductRepository repository;
    private final ProductServiceImpl productService;

    @CommandFirst
    public void addProductStart(TelegramLongPollingEngine engine, @ParamName("chatId") Long chatId) {


        String text = "Привет добавь товар  по следующим правилам \n\n" +
                "Product: {Наименование товара} \n\n" +
                "Description: {Описание товара} \n\n" +
                "Price: {Описание товара} \n\n";

        var send = new SendMessage();
        send.setChatId(String.valueOf(chatId));
        send.setText(text);

        engine.executeNotException(send);
        try {
            engine.execute(send);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @CommandOther
    public void addProductNewProduct(TelegramLongPollingEngine engine, CommandContext context, @ParamName("chatId") Long chatId) throws TelegramApiException {
        String message = "Вы не являетесь админом, вам не разрешено добавлять товары";
        // TODO: Исправить проверку админа, не того проверка
        if (adminService.isAdmin(engine.getMe().getId())) {
            message = context.getUpdate().getMessage().getText();
            NewProduct newProduct = productService.getByMessage(message);
            repository.save(newProduct);
            message = "Товар с id " + newProduct.getId() + " успешно добавлен";
        }
        var send = new SendMessage();
        send.setChatId(String.valueOf(chatId));
        send.setText(message);

        try {
            engine.execute(send);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
