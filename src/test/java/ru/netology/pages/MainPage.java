package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$;

public class MainPage {
    private final SelenideElement buyButton = $$("button").find(Condition.exactText("Купить"));
    private final SelenideElement buyCreditButton = $$("button").find((Condition.exactText("Купить в кредит")));
    private final SelenideElement payment = $$("h3.heading").find(Condition.exactText("Оплата по карте"));
    private final SelenideElement credit = $$("h3.heading").find(Condition.exactText("Кредит по данным карты"));

    public PayPage clickByuButton() {
        buyButton.click();
        payment.shouldBe(Condition.visible);
        return new PayPage();
    }

    public PayPage clickCreditButton() {
        buyCreditButton.click();
        credit.shouldBe(Condition.visible);
        return new PayPage();
    }
}
