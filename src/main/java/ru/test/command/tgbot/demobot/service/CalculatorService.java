package ru.test.command.tgbot.demobot.service;

import ru.test.command.tgbot.demobot.model.users.CalculateDataUser;

import java.util.ArrayList;
import java.util.HashMap;

public interface CalculatorService {

    void listIn(long key, CalculateDataUser user);

    ArrayList<CalculateDataUser> listOut(long key);

    HashMap<Long, ArrayList<CalculateDataUser>> listAll();
}
