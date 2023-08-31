package ru.netology.test;


import com.codeborne.selenide.logevents.SelenideLogger;
import dev.failsafe.internal.util.Assert;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.Data;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.SQLHelper;
import ru.netology.pages.MainPage;
import ru.netology.pages.PayPage;
import ru.netology.data.DataHelper;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.netology.data.DataHelper.*;


public class PaymentByCardTest {
    MainPage mainPage;

    @BeforeAll
    public static void setupAllure() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    public static void tearDownAllure() {
        SelenideLogger.removeListener("allure");
        SQLHelper.cleanBD();
    }

    @BeforeEach
    void openSetup() {
        mainPage = open(System.getProperty("sut.url"), MainPage.class);
    }

    @Test
    @DisplayName("Поля заполнены валидными значениями и номером карты APPROVED")
    public void shouldPaymentByCardWithApprovedStatus() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(), getValidMonthFromNow(), getValidYearFromNow(), getValidOwner(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifySuccessNotification();
        val payStatus = SQLHelper.getPaymentStatus();
        assertEquals("APPROVED", payStatus);
    }

    @Test
    @DisplayName("Поля заполнены валидными значениями и номером карты DECLINED")
    public void shouldPaymentByCardWithDeclinedStatus() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getDeclinedCardNumber(), getValidMonthFromNow(), getValidYearFromNow(), getValidOwner(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifySuccessNotification();
        val payStatus = SQLHelper.getPaymentStatus();
        assertEquals("DECLINED", payStatus);
    }

    @Test
    @DisplayName("Отправка пустой формы")
    public void sendEmptyForm() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getCardNumberEmptyField(), getMonthEmptyField(), getYearEmptyField(), getOwnerEmptyField(), getCVCEmptyField());
        payPage.fillCardData(info);
        payPage.verifyEmptyField();
        payPage.verifyIncorrectFormat();
    }

    @Test
    @DisplayName("Ввод номера неизвестной карты")
    public void EnteringUnknownCardNumber() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getCardNumberUnknownCard(), getValidMonthFromNow(), getValidYearFromNow(), getValidOwner(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifyErrorNotification();
    }
    @Test
    @DisplayName("Отправка формы с пустым полем Номер карты")
    public void SendFormWithEmptyFieldCardNumber() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getCardNumberEmptyField(), getValidMonthFromNow(), getValidYearFromNow(), getValidOwner(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifyIncorrectFormat();
    }
    @Test
    @DisplayName("Ввод латинских букв в поле Номер карты")
    public void EnteringLatinLettersInTheCardNumberField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(),getValidMonthFromNow(), getValidYearFromNow(), getOwnerLowerCaser(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifyEmptyFieldInputLetters();
    }
}


