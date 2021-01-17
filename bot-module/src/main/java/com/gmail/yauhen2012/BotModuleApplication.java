package com.gmail.yauhen2012;

import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.gmail.yauhen2012.impl.BotCityInformationServiceImpl;
import com.gmail.yauhen2012.model.BotCityInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

public class BotModuleApplication extends TelegramLongPollingBot {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {

        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new BotModuleApplication());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        BotCityInformationService botCityInformationService = new BotCityInformationServiceImpl();
        BotCityInfo botCityInfo = new BotCityInfo();
        Message message = update.getMessage();

        if (message != null && message.hasText()) {
            switch (message.getText()) {
                case "/start":
                    sendMsg(message,
                            "Hello! I'm a travelers bot. Press \"/help\" and I will tell you how to make friends with me",
                            true);
                    break;
                case "/help":
                    sendMsg(message, "I have five saved cities. You see four of them on the menu and the fifth is Tokyo. Use api if you want more cities", false);
                    break;
                default:
                    try {
                        sendMsg(message,
                                botCityInformationService.getCityInfo(message.getText(), botCityInfo, getBotProperty("bot.id")),
                                false);

                    } catch (IOException e) {
                        sendMsg(message,
                                "Sorry, I don't know such a city. Perhaps you need to use the api and add it to the database",
                                true);
                        logger.error("Command '" + message.getText() + "' not found");
                    }
            }
        }
    }

    public void setCityButtons(SendMessage sendMessage) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("Minsk"));
        keyboardFirstRow.add(new KeyboardButton("NewYork"));

        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("Paris"));
        keyboardSecondRow.add(new KeyboardButton("Lida"));

        keyboardRowList.add(keyboardFirstRow);
        keyboardRowList.add(keyboardSecondRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

    public void setHelpButton(SendMessage sendMessage) {

        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton("/help"));
        keyboardRowList.add(keyboardFirstRow);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);

    }

    private void sendMsg(Message message, String text, Boolean needHelp) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        sendMessage.enableHtml(true);
        try {
            if (needHelp) {
                setHelpButton(sendMessage);
            } else {
                setCityButtons(sendMessage);
            }
            execute(sendMessage.setText(text));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return getBotProperty("bot.name");
    }

    @Override
    public String getBotToken() {
        return getBotProperty("bot.token");
    }

    private String getBotProperty(String property) {
        try (InputStream inputStream = getClass().getResourceAsStream("/bot.properties")) {
            Properties prop = new Properties();
            if (inputStream == null) {
                logger.error("Sorry, unable to find bot.properties");
            }
            prop.load(inputStream);
            return prop.getProperty(property);
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.error("Cannot find bot property - " + property);
        return null;
    }
}
