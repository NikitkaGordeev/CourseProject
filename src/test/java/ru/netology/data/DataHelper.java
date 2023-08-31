package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    @RequiredArgsConstructor
    public static class CardData {
        String number;
        String month;
        String year;
        String owner;
        String cvc;
    }

    // Поле "Номер карты"
    public static String getApprovedCardNumber() {
        return ("1111 2222 3333 4444");
    }

    public static String getDeclinedCardNumber() {
        return ("5555 6666 7777 8888");
    }

    public static String getCardNumberUnknownCard() {
        return ("1111 1111 1111 1111");
    }

    public static String getCardNumberEmptyField() {
        return ("");
    }

    public static String getCardNumberLetters() {
        return ("qwerty");
    }

    public static String getCardNumberCyrillicLetters() {
        return ("йцукен");
    }

    public static String getCardNumberSpecSymbols() {
        return ("!@#$%^&*");
    }

    public static String getCardNumber15Numbers() {
        return ("1111 2222 3333 444");
    }

    //Поле "Месяц"
    public static String getValidMonthFromNow() {
        LocalDate dateM = LocalDate.now().plusMonths(1);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM");
        return dateM.format(format);
    }

    public static String getMonthEmptyField() {
        return ("");
    }

    public static String getMonthLetters() {
        return ("qwerty");
    }

    public static String getMonthCyrillicLetters() {
        return ("йцукен");
    }

    public static String getMonthSpecSymbols() {
        return ("!@#$%^&*");
    }

    public static String getMonthLessMin() {
        return ("00");
    }

    public static String getMonthMoreMax() {
        return ("13");
    }

    public static String getMonthWrongFormat() {
        return ("9");
    }

    public static String getPreviousMonth() {
        LocalDate dateM = LocalDate.now().minusMonths(1);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MM");
        return dateM.format(format);
    }

    //Поле "Год"
    public static String getValidYearFromNow() {
        LocalDate dateY = LocalDate.now().plusYears(1);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("YY");
        return dateY.format(format);
    }

    public static String getYearEmptyField() {
        return ("");
    }

    public static String getYearLetters() {
        return ("qwerty");
    }

    public static String getYearCyrillicLetters() {
        return ("йцукен");
    }

    public static String getYearSpecSymbols() {
        return ("!@#$%^&*");
    }

    public static String getPreviousYear() {
        LocalDate dateY = LocalDate.now().minusYears(1);
        DateTimeFormatter format = DateTimeFormatter.ofPattern("YY");
        return dateY.format(format);
    }

    // Поле "Владелец"
    public static String getValidOwner() {
        Faker faker = new Faker(new Locale("en"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getOwnerEmptyField() {
        return ("");
    }

    public static String getOwnerCyrillicLetters() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getOwnerSpecSymbols() {
        return ("!@#$%^&*");
    }

    public static String getOwnerNumber() {
        return ("123456");
    }

    public static String getOwnerLowerCaser() {
        return ("ivan ivanov");
    }

    //Поле CVC/CVV
    public static String getValidCVC() {
        Faker faker = new Faker();
        return faker.numerify("###");
    }

    public static String getCVCEmptyField() {
        return ("");
    }

    public static String getLessMinCVC() {
        Faker faker = new Faker();
        return faker.numerify("##");
    }

    public static String getMoreMaxCVC() {
        Faker faker = new Faker();
        return faker.numerify("####");
    }

    public static String getCVCLetters() {
        return ("qwerty");
    }

    public static String getCVCCyrillicLetters() {
        return ("йцукен");
    }

    public static String getCVCSpecSymbols() {
        return ("!@#$%^&*");
    }
}
