package ru.test.command.tgbot.demobot.commands;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.test.command.tgbot.demobot.model.product.NewProduct;
import ru.test.command.tgbot.demobot.repository.impl.Basket;
import ru.test.command.tgbot.demobot.repository.impl.ProductRepository;
import ru.wdeath.managerbot.lib.bot.annotations.CommandFirst;
import ru.wdeath.managerbot.lib.bot.annotations.CommandNames;
import ru.wdeath.managerbot.lib.bot.annotations.CommandOther;
import ru.wdeath.managerbot.lib.bot.annotations.ParamName;
import ru.wdeath.managerbot.lib.bot.callback.CallbackData;
import ru.wdeath.managerbot.lib.bot.callback.CallbackDataSender;
import ru.wdeath.managerbot.lib.bot.command.CommandContext;
import ru.wdeath.managerbot.lib.util.KeyboardUtil;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@CommandNames(value = Start.NAME)
public class Start {

    public static final String NAME = "/start";

    private final ProductRepository repository;

    @CommandFirst
    public void startMenu(CommandContext context, @ParamName("chatId") Long chatId, @ParamName("messageId") Long messageId) {

        List<NewProduct> productList = repository.getAll();

        final CallbackDataSender[][] buttons = new CallbackDataSender[productList.size()][1];
        for (int i = 0; i < productList.size(); i++) {
            NewProduct product = productList.get( i );
            String productText = "Product: " + product.getName() + "\n\n" +
                    "Description: " + product.getDescription() + "\n\n" +
                    "Price: " + product.getPrice().floatValue() + "\n\n";
            CallbackDataSender callbackDataSender = new CallbackDataSender( productText, new CallbackData( "add-basket", "" + product.getId() ) );
            buttons[i][0] = callbackDataSender;
        }

        final SendMessage edit = SendMessage
                .builder()
                .chatId( chatId )
                .text( "             Выберите товар  " )
                .replyMarkup( KeyboardUtil.inline( buttons ) )
                .parseMode( ParseMode.MARKDOWN )
                .build();

        context.getEngine().executeNotException( edit );
    }
}
