package com.epam.mobitru.reviewOrder;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.core.Is.is;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

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
@SmallTest
public class CompleteOrderTests extends AppBaseTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void loginToApp() throws InterruptedException {
        enterDefaultCredsAndSingIn();
        performAddToCart(0);
        clickWithWait(onView(withId(R.id.cart_title)));
        clickWithWait(onView(withId(R.id.continue_to_checkout)));
        enterUserInfoAndSave();
    }

    @Test
    public void completeOrder() {
        clickWithWait(onView(withId(R.id.confirm)));
        onView(withId(R.id.ic_done)).check(matches(is(isDisplayed())));
        onView(withId(R.id.completed_text)).check(matches(is(isDisplayed())));
        clickWithWait(onView(withId(R.id.go_back)));
        onView(new RViewMatcher(R.id.product_list).
                elementOnView(0, R.id.price)).check(matches(is(isDisplayed())));
        onView(withId(R.id.cart_title)).check(matches(withText("Cart (0)")));

    }

    @Test
    public void completedOrderCheck() {
        clickWithWait(onView(withId(R.id.confirm)));
        clickWithWait(onView(withId(R.id.go_back)));
        clickWithWait(onView(allOf(withText("Orders"), withEffectiveVisibility(VISIBLE))));
        onView(allOf(withText("In Progress (1)"), withEffectiveVisibility(VISIBLE))).check(matches(is(isDisplayed())));
    }



}
