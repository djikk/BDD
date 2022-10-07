package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {

    private SelenideElement transferSumField = $("[data-test-id=amount] input");
    private SelenideElement transferCardField = $("[data-test-id=from] input");
    private SelenideElement applyButton = $("[data-test-id=action-transfer]");
    private SelenideElement notification = $("[data-test-id=error-notification]");


    public void importTransferData(int value, String cardNumber) {
        transferSumField.setValue(Integer.toString(value));
        transferCardField.setValue(String.valueOf(cardNumber));
        applyButton.click();
    }

    public void getNotification() {
        notification.shouldHave(exactText("Вы ввели сумму, превышающую остаток средств на вашей карте. Введите другую сумму.")).shouldBe(visible);
    }
}