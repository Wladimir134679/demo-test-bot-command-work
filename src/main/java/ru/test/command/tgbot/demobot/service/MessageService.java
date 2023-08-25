package ru.test.command.tgbot.demobot.service;

public interface MessageService {
     String getProductName(String message);

     String getDescription(String message);

     String getPrice(String message);
}
