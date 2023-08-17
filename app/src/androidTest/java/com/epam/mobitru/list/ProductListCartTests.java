package com.epam.mobitru.list;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.epam.mobitru.AppBaseTest;
import com.epam.mobitru.MainActivity;
import com.epam.mobitru.R;
import com.epam.mobitru.matchers.RViewMatcher;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.stream.IntStream;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProductListCartTests extends AppBaseTest {


    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);


    @Before
    public void loginToApp() throws InterruptedException {
        enterDefaultCredsAndSingIn();
        performAddToCart(0);
    }

    @Test
    public void checkAddToCart() {
        onView(withId(R.id.cart_title)).check(matches(withText("Cart (1)")));
    }

    @Test
    public void checkAddSeveralProductsToCart() {
        int numberOfItems = 2;
        IntStream.range(1, numberOfItems).forEach(this::performAddToCart);
        onView(withId(R.id.cart_title)).check(matches(withText(String.format("Cart (%d)", numberOfItems))));
        IntStream.range(1, numberOfItems).forEach(this::performRemoveFromCartIfPresent);
    }

    @Test
    public void checkRemoveFromCart() {
        onView(new RViewMatcher(R.id.product_list).
                elementOnView(0, R.id.removeFromCart)).perform(click());
        onView(withId(R.id.cart_title)).check(matches(withText("Cart (0)")));
    }

    @Test
    public void checkOpenCartAfterAddProduct() {
        onView(withId(R.id.cart_title)).perform(click());
        onView(withId(R.id.cart_list)).check(matches(isDisplayed()));
        onView(withId(R.id.continue_to_checkout)).check(matches(isDisplayed()));
        onView(isRoot()).perform(ViewActions.pressBack());
    }

    @Test
    public void checkOpenCartAfterRemoveSingleProduct() {
        checkRemoveFromCart();
        onView(withId(R.id.cart_title)).perform(click());
        onView(withId(R.id.empty_cart_text)).check(matches(isDisplayed()));
        onView(withId(R.id.continue_to_checkout)).check(matches(not(isDisplayed())));
        onView(isRoot()).perform(ViewActions.pressBack());
    }

    @After
    public void logoutAfter() {
        logout();
    }

}
