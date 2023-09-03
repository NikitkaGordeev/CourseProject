package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
    private final SelenideElement buyButton = $$("button").find(Condition.exactText("Купить"));
    private final SelenideElement buyCreditButton = $$("button").find((Condition.exactText("Купить в кредит")));


    public PayPage clickByuButton() {
        buyButton.click();
        return new PayPage();
    }

    public PayPage clickCreditButton() {
        buyCreditButton.click();
        return new PayPage();
    }
}
