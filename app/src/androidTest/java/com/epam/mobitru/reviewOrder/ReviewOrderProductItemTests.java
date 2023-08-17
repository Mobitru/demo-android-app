package com.epam.mobitru.reviewOrder;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.core.Is.is;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.epam.mobitru.AppBaseTest;
import com.epam.mobitru.MainActivity;
import com.epam.mobitru.R;
import com.epam.mobitru.matchers.RViewMatcher;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@LargeTest
public class ReviewOrderProductItemTests extends AppBaseTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void loginToApp() throws InterruptedException {
        enterDefaultCredsAndSingIn();
        performAddToCart(0);
        clickWithWait(onView(withId(R.id.cart_title)));
        clickWithWait(onView(withId(R.id.continue_to_checkout)));
    }


    @Test
    public void checkProductItemElements() throws InterruptedException {
        enterUserInfoAndSave();

        onView(new RViewMatcher(R.id.list).
                elementOnView(0, R.id.price)).check(matches(is(isDisplayed())));
        onView(new RViewMatcher(R.id.list).
                elementOnView(0, R.id.title)).check(matches(is(isDisplayed())));
        onView(new RViewMatcher(R.id.list).
                elementOnView(0, R.id.image)).check(matches(is(isDisplayed())));
        onView(new RViewMatcher(R.id.list).
                elementOnView(0, R.id.counter)).check(matches(is(isDisplayed())));

        onView(withId(R.id.name)).check(matches(withText(containsString(CORRECT_FN))));
        onView(withId(R.id.name)).check(matches(withText(containsString(CORRECT_LN))));
        onView(withId(R.id.email)).check(matches(withText(VALID_LOGIN)));
        onView(withId(R.id.address)).check(matches(withText(CORRECT_ADDRESS)));

        onView(withId(R.id.packaging_fee_title)).check(matches(is(isDisplayed())));
        onView(withId(R.id.packaging_fee_value)).check(matches(is(isDisplayed())));
        onView(withId(R.id.subtotal_title)).check(matches(is(isDisplayed())));
        onView(withId(R.id.subtotal_value)).check(matches(is(isDisplayed())));
        onView(withId(R.id.delivery_fee_title)).check(matches(is(isDisplayed())));
        onView(withId(R.id.delivery_fee_value)).check(matches(is(isDisplayed())));
        onView(withId(R.id.discount_title)).check(matches(is(isDisplayed())));
        onView(withId(R.id.discount_value)).check(matches(is(isDisplayed())));
        onView(withId(R.id.total_title)).check(matches(is(isDisplayed())));
        onView(withId(R.id.total_value)).check(matches(is(isDisplayed())));


        onView(withId(R.id.confirm)).check(matches(is(isDisplayed())));
    }

    @Test
    public void checkEditAccountElement() {
        onView(withId(R.id.first_name)).check(matches(is(isDisplayed())));
        onView(withId(R.id.last_name)).check(matches(is(isDisplayed())));
        onView(withId(R.id.email)).check(matches(is(isNotEnabled())));
        onView(withId(R.id.address)).check(matches(is(isDisplayed())));
        onView(withId(R.id.save)).check(matches(is(isDisplayed())));
    }

}
