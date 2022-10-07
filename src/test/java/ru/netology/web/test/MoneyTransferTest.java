package ru.netology.web.test;

import com.codeborne.selenide.Configuration;
import lombok.val;

import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.MoneyTransferPage;

import static com.codeborne.selenide.Selenide.open;

class MoneyTransferTest {
    @BeforeEach
    void shouldLogin() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @AfterEach
    void showBalance() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    void transferFromCard1ToCard2() {
        int amount = 200;
        String cardNumber = String.valueOf(DataHelper.getFirstCardNumber());
        val dashboardPage = new DashboardPage();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        dashboardPage.transferButtonFirstToSecond();
        val transferPage = new MoneyTransferPage();
        transferPage.importTransferData(amount, cardNumber);
        var currentFirstCar1Balance = dashboardPage.getFirstCardBalance();
        var currentSecondCardBalance = dashboardPage.getSecondCardBalance();

        Assertions.assertEquals(firstCardBalance - amount, currentFirstCar1Balance);
        Assertions.assertEquals(secondCardBalance + amount, currentSecondCardBalance);
    }

    @Test
    void transferFromCard2ToCard1() {
        int amount = 200;
        String cardNumber = String.valueOf(DataHelper.getSecondCardNumber());
        val dashboardPage = new DashboardPage();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        dashboardPage.transferButtonSecondToFirst();
        val transferPage = new MoneyTransferPage();
        transferPage.importTransferData(amount, cardNumber);
        var currentFirstCardBalance = dashboardPage.getFirstCardBalance();
        var currentSecondCardBalance = dashboardPage.getSecondCardBalance();

        Assertions.assertEquals(secondCardBalance - amount, currentSecondCardBalance);
        Assertions.assertEquals(firstCardBalance + amount, currentFirstCardBalance);
    }

    @Test
    void transferMoneyMoreThanCardBalanceFromCard1ToCard2() {
        int amount = 11_000;
        String cardNumber = String.valueOf(DataHelper.getFirstCardNumber());
        val dashboardPage = new DashboardPage();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        dashboardPage.transferButtonFirstToSecond();
        val transferPage = new MoneyTransferPage();
        transferPage.importTransferData(amount, cardNumber);
        var currentFirstCar1Balance = dashboardPage.getFirstCardBalance();
        var currentSecondCardBalance = dashboardPage.getSecondCardBalance();
        if (currentFirstCar1Balance < amount) {
            transferPage.getNotification();
        }

        Assertions.assertEquals(firstCardBalance - amount, currentFirstCar1Balance);
        Assertions.assertEquals(secondCardBalance + amount, currentSecondCardBalance);

    }

    @Test
    void transferMoneyMoreThanCardBalanceFromCard2ToCard1() {
        int amount = 22_000;
        String cardNumber = String.valueOf(DataHelper.getSecondCardNumber());
        val dashboardPage = new DashboardPage();
        var firstCardBalance = dashboardPage.getFirstCardBalance();
        var secondCardBalance = dashboardPage.getSecondCardBalance();
        dashboardPage.transferButtonSecondToFirst();
        val transferPage = new MoneyTransferPage();
        transferPage.importTransferData(amount, cardNumber);
        var currentFirstCardBalance = dashboardPage.getFirstCardBalance();
        var currentSecondCardBalance = dashboardPage.getSecondCardBalance();
        if (currentSecondCardBalance < amount) {
            transferPage.getNotification();
        }

        Assertions.assertEquals(secondCardBalance - amount, currentSecondCardBalance);
        Assertions.assertEquals(firstCardBalance + amount, currentFirstCardBalance);
    }
}