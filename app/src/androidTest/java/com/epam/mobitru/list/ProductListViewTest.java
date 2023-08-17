package com.epam.mobitru.list;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.core.Is.is;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.epam.mobitru.AppBaseTest;
import com.epam.mobitru.MainActivity;
import com.epam.mobitru.R;
import com.epam.mobitru.matchers.RViewMatcher;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class ProductListViewTest extends AppBaseTest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void loginToApp() throws InterruptedException {
        enterDefaultCredsAndSingIn();
    }


    @Test
    public void checkProductItemElements() {
        onView(new RViewMatcher(R.id.product_list).
                elementOnView(0, R.id.price)).check(matches(is(isDisplayed())));
        onView(new RViewMatcher(R.id.product_list).
                elementOnView(1, R.id.discountValue)).check(matches(is(isDisplayed())));
        onView(new RViewMatcher(R.id.product_list).
                elementOnView(1, R.id.title)).check(matches(is(isDisplayed())));
        onView(new RViewMatcher(R.id.product_list).
                elementOnView(1, R.id.image)).check(matches(is(isDisplayed())));

    }


}
