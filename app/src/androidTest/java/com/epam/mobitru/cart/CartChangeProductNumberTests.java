package com.epam.mobitru.cart;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.epam.mobitru.AppBaseTest;
import com.epam.mobitru.MainActivity;
import com.epam.mobitru.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.stream.IntStream;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CartChangeProductNumberTests extends AppBaseTest {

    private static final int MAX_PRODUCT_VALUE = 10;
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void loginToApp() throws InterruptedException {
        enterDefaultCredsAndSingIn();
        performAddToCart(0);
        onView(withId(R.id.cart_title)).perform(click());
    }

    @Test
    public void checkIncreaseAndDecreaseProductNumber() {
        clickWithWait(onView(withId(R.id.plus)));
        onView(withId(R.id.counter)).check(matches(withText("2")));
        onView(withId(R.id.remove)).check(matches(is(not(isDisplayed()))));
        clickWithWait(onView(withId(R.id.minus)));
        onView(withId(R.id.counter)).check(matches(withText("1")));
        onView(withId(R.id.remove)).check(matches(is(isDisplayed())));
    }

    @Test
    public void checkRemoveProduct() {
        clickWithWait(onView(withId(R.id.remove)));
        onView(withId(R.id.empty_cart_text)).check(matches(isDisplayed()));
        onView(withId(R.id.continue_to_checkout)).check(matches(not(isDisplayed())));
        clickWithWait(onView(withId(R.id.continue_to_shopping)));
        onView(withId(R.id.cart_title)).check(matches(withText("Cart (0)")));
    }

    @Test
    public void checkMaxNumberOfProducts() {
        IntStream.range(0, MAX_PRODUCT_VALUE).
                forEach(indx -> clickWithWait(onView(withId(R.id.plus))));
        onView(withId(R.id.plus)).check(matches(is(not(isEnabled()))));
        onView(withId(R.id.counter)).check(matches(withText(String.valueOf(MAX_PRODUCT_VALUE))));
    }

    @Test
    public void checkReturnBackToProductList() {
        onView(isRoot()).perform(ViewActions.pressBack());
        onView(withId(R.id.cart_title)).check(matches(withText("Cart (1)")));
        clickWithWait(onView(withId(R.id.cart_title)));
        onView(withId(R.id.counter)).check(matches(withText("1")));
    }

    @After
    public void logoutAfter() {
        onView(isRoot()).perform(ViewActions.pressBack());
        logout();
    }

}
