package com.epam.mobitru.cart;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.core.Is.is;

import androidx.test.espresso.action.ViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.epam.mobitru.AppBaseTest;
import com.epam.mobitru.MainActivity;
import com.epam.mobitru.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class CartProductItemTests extends AppBaseTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void loginToApp() throws InterruptedException {
        enterDefaultCredsAndSingIn();
        performAddToCart(1);
        clickWithWait(onView(withId(R.id.cart_title)));
    }

    @Test
    public void checkProductItemElements() {
        onView(withId(R.id.apply_promo_code)).check(matches(is(isDisplayed())));
        onView(withId(R.id.priceOriginal)).check(matches(is(isDisplayed())));
        onView(withId(R.id.priceDiscount)).check(matches(is(isDisplayed())));
        onView(withId(R.id.discountValue)).check(matches(is(isDisplayed())));
        onView(withId(R.id.title)).check(matches(is(isDisplayed())));
        onView(withId(R.id.image)).check(matches(is(isDisplayed())));
    }

    @After
    public void logoutAfter() {
        onView(isRoot()).perform(ViewActions.pressBack());
        logout();
    }

}
