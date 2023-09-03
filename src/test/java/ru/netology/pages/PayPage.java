package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PayPage {
    private final SelenideElement cardNumberField = $("input[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("input[placeholder='08']");
    private final SelenideElement yearField = $("input[placeholder='22']");
    private final SelenideElement ownerField = $$(".input").find(exactText("Владелец")).$(".input__control");
    private final SelenideElement cvcField = $("[placeholder='999']");
    private final SelenideElement button = $$(".button").find(exactText("Продолжить"));
    private final SelenideElement payment = $$("h3.heading").find(Condition.exactText("Оплата по карте"));
    private final SelenideElement credit = $$("h3.heading").find(Condition.exactText("Кредит по данным карты"));

    private final SelenideElement emptyField = $(byText("Поле обязательно для заполнения"));
    private final SelenideElement cardValidity = $(byText("Истёк срок действия карты"));
    private final SelenideElement invalidСardExpirationDate = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement incorrectFormat = $(byText("Неверный формат"));
    private final SelenideElement successNotification = $(byText("Операция одобрена Банком."));
    private final SelenideElement errorNotification = $(byText("Ошибка! Банк отказал в проведении операции."));
    private final SelenideElement lettersInField = $(byValue("qwerty")); // для проверки полей по отдельности без заполнения формы полностью
    private final SelenideElement cyrillicLettersInField = $(byText("йцукен")); // для проверки полей по отдельности без заполнения формы полностью
    private final SelenideElement specSymbolsInField = $(byText("!@#$%^&*")); // для проверки полей по отдельности без заполнения формы полностью

    public void fillCardData(DataHelper.CardData cardData) {
        cardNumberField.setValue(cardData.getNumber());
        monthField.setValue(cardData.getMonth());
        yearField.setValue(cardData.getYear());
        ownerField.setValue(cardData.getOwner());
        cvcField.setValue(cardData.getCvc());
        button.click();
    }

    public void verifyEmptyFieldInputLetters() {
        lettersInField.shouldBe(disappear);
    } // для проверки полей по отдельности без заполнения формы полностью

    public void verifyEmptyFieldInputCyrillicLetters() {
        cyrillicLettersInField.shouldBe(disappear);
    } // для проверки полей по отдельности без заполнения формы полностью

    public void verifyEmptyFieldInputSpecSymbols() {
        specSymbolsInField.shouldBe(disappear);
    } // для проверки полей по отдельности без заполнения формы полностью


    public void verifyEmptyField() {
        emptyField.shouldBe(Condition.visible);
    }

    public void verifyCardValidity() {
        cardValidity.shouldBe(Condition.visible);
    }

    public void verifyInvalidCardExpirationDate() {
        invalidСardExpirationDate.shouldBe(Condition.visible);
    }

    public void verifyIncorrectFormat() {
        incorrectFormat.shouldBe(Condition.visible);
    }

    public void verifySuccessNotification() {
        successNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void verifyErrorNotification() {
        errorNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }
    public PayPage byuButtonTitle() {
        payment.shouldBe(Condition.visible);
        return new PayPage();
    }
    public PayPage CreditButtonTitle() {
        credit.shouldBe(Condition.visible);
        return new PayPage();
    }
}
