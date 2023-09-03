package ru.netology.test;


import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.data.SQLHelper;
import ru.netology.pages.MainPage;


import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

    // Позитивные тесты
    @Test
    @DisplayName("Поля заполнены валидными значениями и номером карты APPROVED")
    public void shouldPaymentByCardWithApprovedStatus() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(), getValidMonthFromNow(), getValidYearFromNow(), getValidOwner(), getValidCVC());
        payPage.byuButtonTitle();
        payPage.fillCardData(info);
        payPage.verifySuccessNotification();
        val payStatus = SQLHelper.getPaymentStatus();
        assertEquals("APPROVED", payStatus);
    }

    @Test
    @DisplayName("Поля заполнены валидными значениями и номером карты DECLINED")
    //Баг<=================================================================
    public void shouldPaymentByCardWithDeclinedStatus() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getDeclinedCardNumber(), getValidMonthFromNow(), getValidYearFromNow(), getValidOwner(), getValidCVC());
        payPage.byuButtonTitle();
        payPage.fillCardData(info);
        payPage.verifyErrorNotification();
        val payStatus = SQLHelper.getPaymentStatus();
        assertEquals("DECLINED", payStatus);
    }

    // Негативные тесты
    @Test
    @DisplayName("Отправка пустой формы")
    public void sendEmptyForm() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getCardNumberEmptyField(), getMonthEmptyField(), getYearEmptyField(), getOwnerEmptyField(), getCVCEmptyField());
        payPage.fillCardData(info);
        payPage.verifyEmptyField();
        payPage.verifyIncorrectFormat();
    }

    // Поле "Номер карты"
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
        val info = new CardData(getCardNumberLetters(), "", "", "", "");
        payPage.fillCardData(info);
        payPage.verifyEmptyFieldInputLetters();
    }

    @Test
    @DisplayName("Ввод букв на кириллице в поле Номер карты")
    public void EnteringCyrillicLettersInTheCardNumberField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getCardNumberCyrillicLetters(), "", "", "", "");
        payPage.fillCardData(info);
        payPage.verifyEmptyFieldInputCyrillicLetters();
    }

    @Test
    @DisplayName("Ввод спецсимволов в поле Номер карты")
    public void EnteringSpecialCharactersInTheCardNumberField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getCardNumberSpecSymbols(), "", "", "", "");
        payPage.fillCardData(info);
        payPage.verifyEmptyFieldInputSpecSymbols();
    }

    @Test
    @DisplayName("Ввод в поле Номер карты номер из 15 чисел")
    public void EnteringNumberOf15NumbersInTheCardNumberField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getCardNumber15Numbers(), getValidMonthFromNow(), getValidYearFromNow(), getValidOwner(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifyIncorrectFormat();
    }

    // Поле "Месяц"
    @Test
    @DisplayName("Отправка формы с пустым полем Месяц")
    public void SubmitFormWithEmptyMonthField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(), getMonthEmptyField(), getValidYearFromNow(), getValidOwner(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifyIncorrectFormat();
    }

    @Test
    @DisplayName("Ввод латинских букв в поле Месяц")
    public void EnteringLatinLettersInTheMonthField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData("", getMonthLetters(), "", "", "");
        payPage.fillCardData(info);
        payPage.verifyEmptyFieldInputLetters();
    }

    @Test
    @DisplayName("Ввод букв на кириллице в поле Месяц")
    public void EnteringCyrillicLettersInTheMonthField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData("", getMonthCyrillicLetters(), "", "", "");
        payPage.fillCardData(info);
        payPage.verifyEmptyFieldInputCyrillicLetters();
    }

    @Test
    @DisplayName("Ввод спецсимволов в поле Месяц")
    public void EnteringSpecialCharactersInTheMonthField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData("", getMonthSpecSymbols(), "", "", "");
        payPage.fillCardData(info);
        payPage.verifyEmptyFieldInputSpecSymbols();
    }

    @Test
    @DisplayName("Отправка формы со значением меньше минимального в поле Месяц") //Баг
    public void SubmitFormWithValueLessThanTheMinValueInTheMonthField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(), getMonthLessMin(), getValidYearFromNow(), getValidOwner(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifyInvalidCardExpirationDate();
    }

    @Test
    @DisplayName("Отправка формы со значением больше максимального в поле Месяц") //Баг
    public void SubmitFormWithValueMoreThanTheMaxValueInTheMonthField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(), getMonthMoreMax(), getValidYearFromNow(), getValidOwner(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifyInvalidCardExpirationDate();
    }

    @Test
    @DisplayName("Отправка формы со значением с неверным форматом в поле Месяц") //Баг
    public void SubmitFormWithInvalidlyFormattedValueInTheMonthField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(), getMonthWrongFormat(), getValidYearFromNow(), getValidOwner(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifyIncorrectFormat();
    }

    @Test
    @DisplayName("Отправка формы с просроченной картой на один месяц") //Баг
    public void SubmitFormWithOverdueCardForOneMonth() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(), getPreviousMonth(), getValidYearFromNow(), getValidOwner(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifyInvalidCardExpirationDate();
    }

    // Поле "Год"
    @Test
    @DisplayName("Отправка формы с пустым полем Год")
    public void SubmitFormWithEmptyYearField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(), getValidMonthFromNow(), getYearEmptyField(), getValidOwner(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifyIncorrectFormat();
    }

    @Test
    @DisplayName("Ввод латинских букв в поле Год")
    public void EnteringLatinLettersInTheYearField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData("", "", getYearLetters(), "", "");
        payPage.fillCardData(info);
        payPage.verifyEmptyFieldInputLetters();
    }

    @Test
    @DisplayName("Ввод букв на кириллице в поле Год")
    public void EnteringCyrillicLettersInTheYearField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData("", "", getYearCyrillicLetters(), "", "");
        payPage.fillCardData(info);
        payPage.verifyEmptyFieldInputCyrillicLetters();
    }

    @Test
    @DisplayName("Ввод спецсимволов в поле Год")
    public void EnteringSpecialCharactersInTheYearField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData("", "", getYearSpecSymbols(), "", "");
        payPage.fillCardData(info);
        payPage.verifyEmptyFieldInputSpecSymbols();
    }

    @Test
    @DisplayName("Отправка формы с просроченной картой на один год")
    //Баг<=================================================================
    public void SubmitFormWithOverdueCardForOneYear() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(), getValidMonthFromNow(), getPreviousYear(), getValidOwner(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifyCardValidity();
    }

    // Поле "Владелец"
    @Test
    @DisplayName("Отправка формы с пустым полем Владелец")
    public void SubmitFormWithEmptyOwnerField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(), getValidMonthFromNow(), getValidYearFromNow(), getOwnerEmptyField(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifyEmptyField();
    }

    @Test
    @DisplayName("Отправка формы с именем и фамилией введенными на кириллице в поле Владелец")
    // Баг<=================================================================
    public void SendFormWithTheFirstAndLastNameEnteredInCyrillicInTheOwnerField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(), getValidMonthFromNow(), getValidYearFromNow(), getOwnerCyrillicLetters(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifyIncorrectFormat();
    }

    @Test
    @DisplayName("Отправка формы с введенными спецсимволами в поле Владелец")
    // Баг<=================================================================
    public void SubmitFormWithSpecsumbolsEnteredInTheOwnerField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(), getValidMonthFromNow(), getValidYearFromNow(), getOwnerSpecSymbols(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifyIncorrectFormat();
    }

    @Test
    @DisplayName("Отправка формы с введенными цифрами в поле Владелец")
    // Баг<=================================================================
    public void SubmitFormWithNumbersEnteredInTheOwnerField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(), getValidMonthFromNow(), getValidYearFromNow(), getOwnerNumber(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifyIncorrectFormat();
    }

    @Test
    @DisplayName("Отправка формы с введенными именем и фамилией нижним регистром в поле Владелец")
    // Баг<=================================================================
    public void SubmitFormWithLowercaseFirstNameAndLastNameEnteredInTheOwnerField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(), getValidMonthFromNow(), getValidYearFromNow(), getOwnerLowerCaser(), getValidCVC());
        payPage.fillCardData(info);
        payPage.verifyIncorrectFormat();
    }

    //Поле "CVC/CVV"
    @Test
    @DisplayName("Отправка формы с пустым полем CVC/CVV")
    public void SubmitFormWithEmptyCVCField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(), getValidMonthFromNow(), getValidYearFromNow(), getValidOwner(), getCVCEmptyField());
        payPage.fillCardData(info);
        payPage.verifyIncorrectFormat();
    }

    @Test
    @DisplayName("Отправка формы с количеством цифр в поле CVC/CVV меньше минимального")
    public void SubmitFormWithLessMinNumberDigitsInTheCVCField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(), getValidMonthFromNow(), getValidYearFromNow(), getValidOwner(), getLessMinCVC());
        payPage.fillCardData(info);
        payPage.verifyIncorrectFormat();
    }

    @Test
    @DisplayName("Отправка формы с количеством цифр в поле CVC/CVV больше максимального")
    public void SubmitFormWithMoreMaxNumberDigitsInTheCVCField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData(getApprovedCardNumber(), getValidMonthFromNow(), getValidYearFromNow(), getValidOwner(), getMoreMaxCVC());
        payPage.fillCardData(info);
        payPage.verifySuccessNotification();
    }

    @Test
    @DisplayName("Ввод латинских букв в поле CVC/CVV")
    public void EnteringLatinLettersInTheCVCField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData("", "", "", "", getCVCLetters());
        payPage.fillCardData(info);
        payPage.verifyEmptyFieldInputLetters();
    }

    @Test
    @DisplayName("Ввод букв на кириллице в поле Месяц")
    public void EnteringCyrillicLettersInTheCVCField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData("", "", "", "", getCVCCyrillicLetters());
        payPage.fillCardData(info);
        payPage.verifyEmptyFieldInputCyrillicLetters();
    }

    @Test
    @DisplayName("Ввод спецсимволов в поле Месяц")
    public void EnteringSpecialCharactersInTheCVCField() {
        val payPage = mainPage.clickByuButton();
        val info = new CardData("", "", "", "", getCVCSpecSymbols());
        payPage.fillCardData(info);
        payPage.verifyEmptyFieldInputSpecSymbols();
    }
}


