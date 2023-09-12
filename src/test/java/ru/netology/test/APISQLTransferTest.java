package ru.netology.test;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static ru.netology.data.APIHelper.*;
import static ru.netology.data.DataGenerator.*;
import static ru.netology.data.SQLHelper.*;

public class APISQLTransferTest {

    @AfterEach
    void cleanCode() {
        cleanAuth_code();
    }

    @AfterAll
    static void cleanAll() {
        cleanDatabase();
    }

    @Test
    public void validTransferFromFirstToSecond() {
        var user = getAuthData();
        sendQueryToValidLogin(user, 200);
        var verificationData = new VerifyData(user.getLogin(), getVerificationCode());
        var token = sendQueryToValidVerifyAndGetToken(verificationData, 200);
        var cardsBalances = sendQueryToGetCardBalances(token.getToken(), 200);
        var firstCardBalance = cardsBalances.get(getFirstCard().getCardId());
        var secondCardBalance = cardsBalances.get(getSecondCard().getCardId());
        var amount = generateValidAmount(firstCardBalance);
        var transferInfo = new APITransferInfo(getFirstCard().getCardNumber(), getSecondCard().getCardNumber(), amount);
        sendQueryToTransfer(token.getToken(), transferInfo, 200);
        cardsBalances = sendQueryToGetCardBalances(token.getToken(), 200);
        var actualFirstCardBalance = cardsBalances.get(getFirstCard().getCardId());
        var actualSecondCardBalance = cardsBalances.get(getSecondCard().getCardId());
        Assertions.assertEquals(firstCardBalance - amount, actualFirstCardBalance);
        Assertions.assertEquals(secondCardBalance + amount, actualSecondCardBalance);
    }
}
