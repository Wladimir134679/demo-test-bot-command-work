package ru.test.command.tgbot.demobot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.test.command.tgbot.demobot.model.users.CalculateDataUser;
import ru.test.command.tgbot.demobot.service.CalculatorService;

import java.util.ArrayList;
import java.util.HashMap;

@Slf4j
@Service
public class CalculatorServiceImpl implements CalculatorService {

    private final HashMap<Long, ArrayList<CalculateDataUser>> memory = new HashMap<>();

    @Override
    public void listIn(long key, CalculateDataUser user) {

        if (memory.containsKey(key)) memory.get(key).add(user);

        else memory.computeIfAbsent(key, k -> new ArrayList<>()).add(user);
    }

    @Override
    public ArrayList<CalculateDataUser> listOut(long key) {

        return memory.getOrDefault(key, null);
    }

    @Override
    public HashMap<Long, ArrayList<CalculateDataUser>> listAll() {
        return memory;
    }
}
