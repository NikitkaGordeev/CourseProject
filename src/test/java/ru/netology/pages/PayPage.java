package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PayPage {
    private final SelenideElement cardNumberField = $("input[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthField = $("input[placeholder='08']");
    private final SelenideElement yearField = $("input[placeholder='22']");
    private final SelenideElement ownerField = $$(".input").find(exactText("Владелец")).$(".input__control");
    private final SelenideElement cvcField = $("[placeholder='999']");
    private final SelenideElement button = $$(".button").find(exactText("Продолжить"));

    private final SelenideElement emptyField = $(byText("Поле обязательно для заполнения"));
    private final SelenideElement cardValidity = $(byText("Истёк срок действия карты"));
    private final SelenideElement invalidСardExpirationDate = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement incorrectFormat = $(byText("Неверный формат"));
    private final SelenideElement successNotification = $(byText("Операция одобрена Банком."));
    private final SelenideElement errorNotification = $(byText("Ошибка! Банк отказал в проведении операции."));
}
