package com.example.empatkali;

import android.view.View;
import android.widget.EditText;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.empatkali.constants.CardType;
import com.example.empatkali.ui.creditcard.CreditCardActivity;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.regex.Matcher;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.withHint;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static java.util.EnumSet.allOf;
import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class CreditCardActivityTest {
    @Rule
    public ActivityTestRule<CreditCardActivity> mActivityTestRule =
            new ActivityTestRule<>(CreditCardActivity.class);

    @Test//*PASSED
    public void checkEditNumberHint(){
        onView((withId(R.id.EDIT_number))).check(matches(withHint("0000000000000000")));
    }

    @Test//*PASSED
    public void performEditNumberText(){
        String regex = "[0-9]+";
        onView(withId(R.id.EDIT_number)).perform(typeText(regex));
    }

    @Test
    public void checkTvNumberText(){
        String regex = "[0-9]+";
        onView(withId(R.id.TV_number)).check(matches(withText(regex)));
    }

    @Test//*PASSED
    public void performButtonValiditas(){
        onView((withId(R.id.BUTTON_cek_validitas))).perform(click());
    }

    @Test
    public void inputEditNumber(){
        String regex = "[0-9]+";

        //! INITIAL
        /*//* Here's a solution without using a custom matcher:
        onView(withId(R.id.EDIT_number)).check(matches(allOf(
                hasDescendant(allOf(withId(R.id.edtUserId), withText(mStringToBetyped))),
                hasDescendant(allOf(withId(R.id.edtPass), withText(mStringToBetyped)))
        )));*/

        //! 2ND PERFORM
        onView((withId(R.id.EDIT_number))).perform(typeText("4000056655665556"));
        onView((withId(R.id.BUTTON_cek_validitas))).perform(click());

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
