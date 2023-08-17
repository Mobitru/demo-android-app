package com.epam.mobitru.list;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

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

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ProductListSortingTests extends AppBaseTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void loginToApp() throws InterruptedException {
        enterDefaultCredsAndSingIn();
        onView(withId(R.id.sortBy)).perform(click());
    }

    @Test
    public void checkSortPriceDescending() {
        onView(withText("Price descending")).perform(click());
        clickWithWait(onView(withId(R.id.apply)));
        onView(withId(R.id.product_list)).check(matches(isDisplayed()));
    }

    @Test
    public void checkSortPriceAscending() {
        checkSortPriceDescending();
        onView(withId(R.id.sortBy)).perform(click());
        onView(withText("Price ascending")).perform(click());
        clickWithWait(onView(withId(R.id.apply)));
        onView(withId(R.id.product_list)).check(matches(isDisplayed()));
    }

    @Test
    public void checkSortNameAscending() {
        onView(withText("Product name A-Z")).perform(click());
        clickWithWait(onView(withId(R.id.apply)));
        onView(withId(R.id.product_list)).check(matches(isDisplayed()));
    }

    @Test
    public void checkSortNameDescending() {
        onView(withText("Product name Z-A")).perform(click());
        clickWithWait(onView(withId(R.id.apply)));
        onView(withId(R.id.product_list)).check(matches(isDisplayed()));
    }

    @After
    public void logoutAfter() {
        logout();
    }
}
