package ru.test.command.tgbot.demobot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.test.command.tgbot.demobot.service.MessageService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    public String getProductName(String message) {
        Pattern patternProduct = Pattern.compile( "Product:.+" );
        String productName = "None";
        Matcher matcher = patternProduct.matcher( message );
        while (matcher.find()) {
            productName = message.substring( matcher.start() + 8, matcher.end() );
        }
        return productName;
    }

    public String getDescription(String message) {
        Pattern patternDescription = Pattern.compile( "Description:.+" );
        String productName = "None";
        Matcher matcher = patternDescription.matcher( message );
        while (matcher.find()) {
            productName = message.substring( matcher.start() + 12, matcher.end() );
        }
        return productName;
    }

    public String getPrice(String message) {
        Pattern patternPrice = Pattern.compile( "Price:.+" );
        String productName = "None";
        Matcher matcher = patternPrice.matcher( message );
        while (matcher.find()) {
            productName = message.substring( matcher.start() + 6, matcher.end() );
        }
        return productName;
    }
}
