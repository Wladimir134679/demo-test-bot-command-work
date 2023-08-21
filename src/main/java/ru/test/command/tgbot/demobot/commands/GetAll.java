package ru.test.command.tgbot.demobot.commands;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.test.command.tgbot.demobot.model.product.NewProduct;
import ru.test.command.tgbot.demobot.repository.impl.Basket;
import ru.wdeath.managerbot.lib.bot.TelegramLongPollingEngine;
import ru.wdeath.managerbot.lib.bot.annotations.CommandFirst;
import ru.wdeath.managerbot.lib.bot.annotations.CommandNames;
import ru.wdeath.managerbot.lib.bot.annotations.ParamName;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@CommandNames("/getAllByBasket")
public class GetAll {
    private final Basket basket;


    @CommandFirst
    public void addProductToBasket(TelegramLongPollingEngine engine, @ParamName("chatId") Long chatId) {

        List<NewProduct> productList = basket.getByUserId( chatId );
        StringBuilder stringBuilder = new StringBuilder();

        for (NewProduct product :productList) {
            stringBuilder.append( "Id:"+product.getId()+"\n\n" )
                    .append( "Product: " )
                    .append( product.getName() )
                    .append( "\n\n" ).append( "Description: " )
                    .append( product.getDescription() )
                    .append( "\n\n" ).append( "Price: " )
                    .append( product.getPrice().floatValue() )
                    .append( "\n\n" )
                    .append( "--------------------------" )
                    .append( "\n\n" );
        }

        var send = new SendMessage();
        send.setChatId( String.valueOf( chatId ) );
        send.setText( stringBuilder.toString()  );
        try{
            engine.execute( send );
        } catch(TelegramApiException e){
            throw new RuntimeException( e );
        }
    }
}

