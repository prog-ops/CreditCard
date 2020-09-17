package com.example.empatkali;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.empatkali.constants.CardType;
import com.example.empatkali.constants.CreditCardUtils;
import com.example.empatkali.ui.creditcard.CreditCardActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class CreditCardActivityTest {
    @Rule
    public ActivityTestRule<CreditCardActivity> mActivityTestRule =
            new ActivityTestRule<>(CreditCardActivity.class);

    @Test//*PASSED
    public void checkNPerformTvNumber(){

        String[] array = {
                "^4[0-9]{12}(?:[0-9]{3}){0,2}$",
                "^(?:5[1-5]|2(?!2([01]|20)|7(2[1-9]|3))[2-7])\\d{14}$",
                "^3[47][0-9]{13}$",
                "^3(?:0[0-5]\\d|095|6\\d{0,2}|[89]\\d{2})\\d{12,15}$",
                "^6(?:011|[45][0-9]{2})[0-9]{12}$",
                "^(?:2131|1800|35\\d{3})\\d{11}$",
                "^62[0-9]{14,17}$"
        };

        for (String a : array) {
            if (CreditCardUtils.isValid(a)) {
                int getInt = CreditCardUtils.getCardType(a);

                if (getInt == CreditCardUtils.VISA) {
                    onView((withId(R.id.TV_cc_name))).check(matches(withText("VISA")));
                    onView((withId(R.id.TV_number))).check(matches(withText(a)));

                } else if (getInt == CreditCardUtils.MASTERCARD) {
                    onView((withId(R.id.TV_cc_name))).check(matches(withText("MASTERCARD")));
                    onView((withId(R.id.TV_number))).check(matches(withText(a)));

                } else if (getInt == CreditCardUtils.DISCOVER) {
                    onView((withId(R.id.TV_cc_name))).check(matches(withText("DISCOVER")));
                    onView((withId(R.id.TV_number))).check(matches(withText(a)));

                } else if (getInt == CreditCardUtils.AMEX) {
                    onView((withId(R.id.TV_cc_name))).check(matches(withText("AMEX")));
                    onView((withId(R.id.TV_number))).check(matches(withText(a)));

                } else {
                    onView((withId(R.id.TV_cc_name))).check(matches(withText("?")));
                    onView((withId(R.id.TV_number))).check(matches(withText("unknown credit card type")));

                }

            } else if (a.length() <= 1){
                onView((withId(R.id.TV_cc_name))).check(matches(withText("?")));
                onView((withId(R.id.TV_number))).check(matches(withText("")));

            } else {
                onView((withId(R.id.TV_cc_name))).check(matches(withText("")));
                onView((withId(R.id.TV_number))).check(matches(withText("")));
            }
        }
    }

    @Test//*PASSED
    public void checkNPerformEditNumber(){
        onView((withId(R.id.EDIT_number))).check(matches(withHint("0000000000000000")));
//        String regex = "[0-9]+";
        String regex = "^[0-9]{16}$";//?

        CardType cardType = CardType.detect(regex);

        String[] array = {
                "^4[0-9]{12}(?:[0-9]{3}){0,2}$",
                "^(?:5[1-5]|2(?!2([01]|20)|7(2[1-9]|3))[2-7])\\d{14}$",
                "^3[47][0-9]{13}$",
                "^3(?:0[0-5]\\d|095|6\\d{0,2}|[89]\\d{2})\\d{12,15}$",
                "^6(?:011|[45][0-9]{2})[0-9]{12}$",
                "^(?:2131|1800|35\\d{3})\\d{11}$",
                "^62[0-9]{14,17}$"
        };

        for (String a : array) {
            onView(withId(R.id.EDIT_number)).perform(typeText(a));
        }
    }

    @Test//*PASSED
    public void checkEditNPerformCCV(){
        onView((withId(R.id.EDIT_ccv))).check(matches(withHint("000")));
        String regex = "[0-9]{3}$";//ng
        onView(withId(R.id.EDIT_ccv)).perform(typeText(regex));
    }

    @Test//*PASSED
    public void checkEditMonthHint(){
        onView((withId(R.id.EDIT_month))).check(matches(withHint("12")));
        //ng bisa
        String months[] = {"01","02","03","04","05","06","07","08","09","10","11","12"};
        for (String month : months) {
            onView(withId(R.id.EDIT_month)).perform(typeText(month));
        }
    }

    @Test//*PASSED
    public void checkNPerformEditYear(){
        onView((withId(R.id.EDIT_year))).check(matches(withHint("2020")));
        /*
            Years from 1000 to 2999
            ^[12][0-9]{3}$

            For 1900-2099
            ^(19|20)\d{2}$

            1970-2019:
            (19[789]\d|20[01]\d)

            ng
            2020-2100:
            (20[23456789]\d|21[01]\d)
         */
//        String regex = "^([5-9]\\d|[1-9]\\d{2,})$";
//        String regex = "^(20|20)\\d{2}$";//ng
        String regex = "(20[234]\\d|21[01]\\d)";
        onView(withId(R.id.EDIT_year)).perform(typeText(regex));
    }

    //! bisa dijadikan satu dengan cek hint jadi ini ga usah
    /*@Test//*PASSED
    public void performEditNumberText(){
        String regex = "[0-9]+";
        onView(withId(R.id.EDIT_number)).perform(typeText(regex));
    }*/

    /*@Test
    public void checkTvNumberText(){
        String regex = "[0-9]+";
        onView(withId(R.id.TV_number)).check(matches(withText(regex)));
    }*/


    //* TES UNTUK KLIK SAVE JIKA SEMUA DATA KOSONG
    @Test//*PASSED
    public void performButtonSave(){
        onView((withId(R.id.BUTTON_save))).perform(click());
    }


    //* TES UNTUK KLIK SAVE JIKA SEMUA DATA LENGKAP
    @Test//*PASSED
    public void checkNPerformSemua(){
        String regex = "[0-9]+";

        //! INITIAL
        /*//* Here's a solution without using a custom matcher:
        onView(withId(R.id.EDIT_number)).check(matches(allOf(
                hasDescendant(allOf(withId(R.id.edtUserId), withText(mStringToBetyped))),
                hasDescendant(allOf(withId(R.id.edtPass), withText(mStringToBetyped)))
        )));*/

        //! 2ND PERFORM
        onView((withId(R.id.EDIT_number))).perform(typeText("4000056655665556"));
        onView((withId(R.id.EDIT_ccv))).perform(typeText("123"));
        onView((withId(R.id.EDIT_validity_month_year))).perform(typeText("12/20"));
        onView((withId(R.id.BUTTON_save))).perform(click());
//        onView((withId(R.id.BUTTON_cek_validitas))).perform(click());//passed

        //! RESULTS

        //* But since you're only comparing the text in 2 views, you can just use the Assert class
//        Assert.equals(edittext1.getText().toString(), edittext2.getText().toString());

        /*assertEquals(CardType.VISA, CardType.detect("4000056655665556"));
        assertEquals(CardType.VISA, CardType.detect("4242424242424242"));

        assertEquals(CardType.MASTERCARD, CardType.detect("5105105105105100"));
        assertEquals(CardType.MASTERCARD, CardType.detect("5200828282828210"));
        assertEquals(CardType.MASTERCARD, CardType.detect("5555555555554444"));

        assertEquals(CardType.AMERICAN_EXPRESS, CardType.detect("371449635398431"));
        assertEquals(CardType.AMERICAN_EXPRESS, CardType.detect("378282246310005"));

        assertEquals(CardType.DISCOVER, CardType.detect("6011000990139424"));
        assertEquals(CardType.DISCOVER, CardType.detect("6011111111111117"));

        assertEquals(CardType.DINERS_CLUB, CardType.detect("30569309025904"));
        assertEquals(CardType.DINERS_CLUB, CardType.detect("38520000023237"));

        assertEquals(CardType.JCB, CardType.detect("3530111333300000"));
        assertEquals(CardType.JCB, CardType.detect("3566002020360505"));

        assertEquals(CardType.UNKNOWN, CardType.detect("0000000000000000"));*/
    }

    //ga dipake
    @Test
    public void clickIncrementButton_ChangesQuantityAndCost() {
        // Check the initial quantity variable is zero
        onView((withId(R.id.quantity_text_view))).check(matches(withText("0")));

        // Click on increment button
        onView((withId(R.id.increment_button)))
                .perform(click());

        // Verify that the increment button increases the quantity by 1
        onView(withId(R.id.quantity_text_view)).check(matches(withText("1")));

        // Verify that the increment button also increases the total cost to $5.00
//        onView(withId(R.id.cost_text_view)).check(matches(withText("$5.00")));

    }

    @Test
    public void clickDecrementButton_ChangesQuantityAndCost() {

        // Check the initial quantity variable is zero
        onView((withId(R.id.quantity_text_view))).check(matches(withText("0")));

        // Click on decrement button
        onView((withId(R.id.decrement_button)))
                .perform(click());

        // Verify that the decrement button decreases the quantity by 1
        onView(withId(R.id.quantity_text_view)).check(matches(withText("0")));

        // Verify that the increment button also increases the total cost to $5.00
//        onView(withId(R.id.cost_text_view)).check(matches(withText("$0.00")));

    }
}
