package ru.test.command.tgbot.demobot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.test.command.tgbot.demobot.service.Sequence;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class SequenceImpl implements Sequence {
    private  AtomicInteger id = new AtomicInteger(100);


    @Override
    public int get() {
        return id.incrementAndGet();
    }
}
