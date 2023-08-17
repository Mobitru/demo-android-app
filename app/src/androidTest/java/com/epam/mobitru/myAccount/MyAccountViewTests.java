package com.epam.mobitru.myAccount;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.epam.mobitru.AppBaseTest;
import com.epam.mobitru.MainActivity;
import com.epam.mobitru.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class MyAccountViewTests extends AppBaseTest {


    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void loginToApp() throws InterruptedException {
        enterDefaultCredsAndSingIn();
        onView(allOf(withText("Account"), withEffectiveVisibility(VISIBLE))).perform(click());
    }


    @Test
    public void checkMyAccountDetailsExist() {
        onView(withId(R.id.edit)).check(matches(isDisplayed()));
        onView(withId(R.id.email)).check(matches(isDisplayed()));
    }

    @Test
    public void checkGeneralViewElements() {
        onView(withText("General")).check(matches(isDisplayed()));
    }

    @Test
    public void checkLogoutElement() {
        onView(withId(R.id.logout)).check(matches(isDisplayed()));
    }
}
