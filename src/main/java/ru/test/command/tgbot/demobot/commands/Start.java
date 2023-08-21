package ru.test.command.tgbot.demobot.commands;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.test.command.tgbot.demobot.model.product.NewProduct;
import ru.test.command.tgbot.demobot.repository.impl.ProductRepository;
import ru.test.command.tgbot.demobot.service.impl.AdminServiceImpl;
import ru.wdeath.managerbot.lib.bot.TelegramLongPollingEngine;
import ru.wdeath.managerbot.lib.bot.annotations.CommandFirst;
import ru.wdeath.managerbot.lib.bot.annotations.CommandNames;
import ru.wdeath.managerbot.lib.bot.annotations.CommandOther;
import ru.wdeath.managerbot.lib.bot.annotations.ParamName;
import ru.wdeath.managerbot.lib.bot.command.CommandContext;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@CommandNames("/start")
public class Start {

    private final AdminServiceImpl adminService;

    private final ProductRepository repository;

    private ApplicationContext applicationContext;

    public Start(AdminServiceImpl adminService, ProductRepository repository, ApplicationContext applicationContext) {
        this.adminService = adminService;
        this.repository = repository;
        this.applicationContext = applicationContext;
    }

    @CommandFirst
    public void perviiRazDa(TelegramLongPollingEngine engine, @ParamName("chatId") Long chatId) throws TelegramApiException {

        var send = hermitageInlineKeyboardAb( engine, chatId );
        send.setChatId( String.valueOf( chatId ) );
        send.setText( " Пользователь с id :" + chatId + " -  выбери товар " );
        try{
            engine.execute( send );
        } catch(TelegramApiException e){
            throw new RuntimeException( e );
        }

    }

    @CommandOther
    public void other(CommandContext context, @ParamName("chatId") Long chatId, @ParamName("messageId") Long mId) {
        var send = new SendMessage();
        send.setChatId( String.valueOf( chatId ) );
        send.setText( "Я уже с тобой поздоровался! Твой сообщение id: " + mId + ", а данные внутри: " + context.getData() );

        context.getEngine().executeNotException( send );
    }

    public SendMessage hermitageInlineKeyboardAb(TelegramLongPollingEngine engine, long chat_id) throws TelegramApiException {

        SendMessage message = new SendMessage();
        message.setChatId( chat_id );
        message.setText( "Выберете товар" );

        Long userId = engine.getMe().getId();


        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        List<NewProduct> productList = repository.getAll();

        for (NewProduct product : productList) {
            InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
            String productText = "Product: " + product.getName() + "\n\n" +
                    "Description: " + product.getDescription() + "\n\n" +
                    "Price: " + product.getPrice().floatValue() + "\n\n";
            inlineKeyboardButton.setText( productText );
            inlineKeyboardButton.setCallbackData( String.valueOf( product.getId() ) );
            List<InlineKeyboardButton> rowInline = new ArrayList<>();
            rowInline.add( inlineKeyboardButton );
            rowsInline.add( rowInline );
        }

        markupInline.setKeyboard( rowsInline );
        message.setReplyMarkup( markupInline );
//
//
//        List<InlineKeyboardButton> rowInline1 = new ArrayList<>();
//
//        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
//        inlineKeyboardButton1.setText( "Аврезе" );
//        inlineKeyboardButton1.setCallbackData( "АВРЕЗЕ" );
//        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
//        inlineKeyboardButton2.setText( "Аликс Ив" );
//        inlineKeyboardButton2.setCallbackData( "АЛИКС" );
//
//        rowInline1.add( inlineKeyboardButton1 );
//        rowInline1.add( inlineKeyboardButton2 );
//
//        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
//
//        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
//        inlineKeyboardButton3.setText( "Амелин" );
//        inlineKeyboardButton3.setCallbackData( "АМЕЛИН" );
//        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
//        inlineKeyboardButton4.setText( "Арстер" );
//        inlineKeyboardButton4.setCallbackData( "АРСТЕР" );
//        rowInline2.add( inlineKeyboardButton3 );
//        rowInline2.add( inlineKeyboardButton4 );
//
//
//        List<InlineKeyboardButton> rowInline11 = new ArrayList<>();
//        InlineKeyboardButton inlineKeyboardButton21 = new InlineKeyboardButton();
//        inlineKeyboardButton21.setText( "Переход на внешний сайт" );
//        inlineKeyboardButton21.setUrl( "https://collections.hermitagemuseum.org" );
//        inlineKeyboardButton21.setCallbackData( "ПЕРЕХОД НА ВНЕШНИЙ САЙТ" );
//        rowInline11.add( inlineKeyboardButton21 );
//
//        rowsInline.add( rowInline1 );
//        rowsInline.add( rowInline2 );
//        rowsInline.add( rowInline11 );
//
//        markupInline.setKeyboard( rowsInline );
//        message.setReplyMarkup( markupInline );

        return message;
    }
}
