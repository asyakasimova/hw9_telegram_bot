package tests;


import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static utils.RandomUtils.*;

public class FormTestsParametrised extends TestBase{

    @Test
    void checkFormFillTest() {
        String firstName = getRandomString(7),
                lastName = getRandomString(10),
                email = getRandomEmail(),
                gender = getRandomGender(),
                phone = getRandomPhone(),
                month = getRandomMonth(),
                year = getRandomYear(),
                date = getRandomDate(),
                subject = "Mat",
                hobby = getRandomHobby(),
                picture = "1.jpg",
                address = getRandomString(15) + " " + getRandomString(10) + " " +
                        getRandomInt(1, 100) + ", " + getRandomString(20),
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
    }
