package tests;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static utils.RandomUtils.*;

public class FormTestsWithFaker extends TestBase{

    @Test
    void checkFormFillTest() {
        Faker faker = new Faker();
        FakeValuesService fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());

        String firstName = faker.name().firstName(),
                lastName = faker.name().lastName(),
                email = fakeValuesService.bothify("????##@gmail.com"),
                gender = faker.demographic().sex().toString(),
                phone = faker.phoneNumber().subscriberNumber(10),
                month = getRandomMonth(),
                year = getRandomYear(),
                date = getRandomDate(),
                subject = "Mat",
                hobby = getRandomHobby(),
                picture = "1.jpg",
                address = faker.address().fullAddress(),
                state = "Rajasthan",
                city = "Jaipur";

        step("Open student registration form", () -> {
                    open("https://demoqa.com/automation-practice-form");
                    $(".main-header").shouldHave(text("Practice Form"));
                });

        step("Fill student registration form", () -> {
                    $("#firstName").setValue(firstName);
                    $("#lastName").setValue(lastName);
                    $("#userEmail").setValue(email);
                    $("#genterWrapper").find(byText(gender)).click();
                    $("#userNumber").setValue(phone);
                    //Calendar
                    $("#dateOfBirthInput").click();
                    $(".react-datepicker__month-select").click();
                    $(byText(month)).click();
                    $(".react-datepicker__year-select").click();
                    $(byText(year)).click();
                    $(".react-datepicker__month").find(byText(date)).click();

                    $("#subjectsInput").setValue(subject).pressEnter();
                    $("#hobbiesWrapper").find(byText(hobby)).click();
                    $("#uploadPicture").uploadFromClasspath(picture);
                    $("#currentAddress").setValue(address);

                    $("#state").click();
                    $("#state").find(byText(state)).click();
                    $("#city").click();
                    $("#city").find(byText(city)).click();
                    $("#submit").click();
                });

        //asserts
        step("checking the data is correct", () -> {
            $x("//td[text()='Student Name']").parent().shouldHave(text(firstName + " " + lastName));
            $x("//td[text()='Student Email']").parent().shouldHave(text(email));
            $x("//td[text()='Gender']").parent().shouldHave(text(gender));
            $x("//td[text()='Mobile']").parent().shouldHave(text(phone));
            $x("//td[text()='Date of Birth']").parent().shouldHave(text(date + " " + month + "," + year));
            $x("//td[text()='Subjects']").parent().shouldHave(text(subject));
            $x("//td[text()='Hobbies']").parent().shouldHave(text(hobby));
            $x("//td[text()='Picture']").parent().shouldHave(text(picture));
            $x("//td[text()='Address']").parent().shouldHave(text(address));
            $x("//td[text()='State and City']").parent().shouldHave(text(state + " " + city));
        });
    }

    @Test
    @Tag("negative")
    void negativeFillFormTest() {
        open("https://demoqa.com/automation-practice-form");
        $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));

        $("#submit").click();
        $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));
    }
}