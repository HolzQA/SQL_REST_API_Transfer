package ru.netology.data;

import lombok.Value;
import java.util.Random;

import static ru.netology.data.SQLHelper.*;

public class DataGenerator {



    private DataGenerator() {}

    @Value
    public static class AuthData {
        String login;
        String password;
    }

    @Value
    public static class VerifyData {
        String login;
        String code;
    }

    @Value
    public static class CardData {
        String cardId;
        String cardNumber;
    }


    public static AuthData getAuthData() {
        return new AuthData("vasya", "qwerty123");
    }

    public static VerifyData getVerifyData() {
        return new VerifyData("vasya", getVerificationCode());

    }

    public static CardData getFirstCard() {
        return new CardData("92df3f1c-a033-48e6-8390-206f6b1f56c0", "5559 0000 0000 0001");
    }

    public static CardData getSecondCard() {
        return new CardData("0f3f5c2a-249e-4c3d-8287-09f7a039391d", "5559 0000 0000 0002");
    }

    public static int generateValidAmount(int balance) {
        return new Random().nextInt(Math.abs(balance)) + 1;
    }


}
