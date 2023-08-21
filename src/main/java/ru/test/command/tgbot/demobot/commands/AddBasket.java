package ru.test.command.tgbot.demobot.commands;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.test.command.tgbot.demobot.model.product.NewProduct;
import ru.test.command.tgbot.demobot.repository.impl.Basket;
import ru.test.command.tgbot.demobot.repository.impl.ProductRepository;
import ru.wdeath.managerbot.lib.bot.TelegramLongPollingEngine;
import ru.wdeath.managerbot.lib.bot.annotations.CommandFirst;
import ru.wdeath.managerbot.lib.bot.annotations.CommandNames;
import ru.wdeath.managerbot.lib.bot.annotations.ParamName;
import ru.wdeath.managerbot.lib.bot.command.CommandContext;
import ru.wdeath.managerbot.lib.bot.command.TypeCommand;

@Service
@Slf4j
@RequiredArgsConstructor
@CommandNames(value = AddBasket.NAME, type = TypeCommand.CALLBACK)
public class AddBasket {
    public static final String NAME = "add-basket";

    private final Basket basket;
    private final ProductRepository repository;

    @CommandFirst
    public void addProductToBasket(TelegramLongPollingEngine engine, CommandContext context, @ParamName("chatId") Long chatId) {

        Long productId = Long.valueOf( (String) context.getData() );
        NewProduct product = repository.get( productId );
        basket.save( chatId, product );
        var send = new SendMessage();
        send.setChatId( String.valueOf( chatId ) );
        send.setText( "Товар с id "+productId+ " добавлен в корзину" );


        try{
            engine.execute( send );
        } catch(TelegramApiException e){
            throw new RuntimeException( e );
        }
    }
}
