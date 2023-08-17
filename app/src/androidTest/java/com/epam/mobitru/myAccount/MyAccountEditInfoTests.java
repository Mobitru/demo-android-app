package com.epam.mobitru.myAccount;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.isEnabled;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.epam.mobitru.AppBaseTest;
import com.epam.mobitru.MainActivity;
import com.epam.mobitru.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class MyAccountEditInfoTests extends AppBaseTest {



    private static final String EMPTY_ERROR_MESSAGE = "Field can’t be empty";
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void loginToApp() throws InterruptedException {
        enterDefaultCredsAndSingIn();
        onView(allOf(withText("Account"), withEffectiveVisibility(VISIBLE))).perform(click());
        onView(withId(R.id.edit)).perform(click());
    }

    @Test
    public void editMyAccountInfoUsingCorrectValues() throws InterruptedException {
        enterUserInfoAndSave(CORRECT_FN, CORRECT_LN, CORRECT_ADDRESS);
        onView(withId(R.id.name)).check(matches(withText(containsString(CORRECT_FN))));
        onView(withId(R.id.name)).check(matches(withText(containsString(CORRECT_LN))));
        onView(withId(R.id.address)).check(matches(withText(containsString(CORRECT_ADDRESS))));
    }

    @Test
    public void editMyAccountInfoEmptyFirstName() throws InterruptedException {
        enterUserInfoAndSave(EMPTY, CORRECT_LN, CORRECT_ADDRESS);
        onView(withId(R.id.first_name)).check(matches(withErrorInInputLayout(EMPTY_ERROR_MESSAGE)));
    }

    @Test
    public void editMyAccountInfoEmptyLastName() throws InterruptedException {
        enterUserInfoAndSave(CORRECT_FN, EMPTY, CORRECT_ADDRESS);
        onView(withId(R.id.last_name)).check(matches(withErrorInInputLayout(EMPTY_ERROR_MESSAGE)));
    }

    @Test
    public void editMyAccountInfoEmptyAddressName() throws InterruptedException {
        enterUserInfoAndSave(CORRECT_FN, CORRECT_LN, EMPTY);
        onView(withId(R.id.address)).check(matches(withErrorInInputLayout(EMPTY_ERROR_MESSAGE)));
    }

    @Test
    public void editMyAccountInfoEmptyAll() throws InterruptedException {
        enterUserInfoAndSave(EMPTY, EMPTY, EMPTY);
        Arrays.asList(R.id.first_name, R.id.last_name, R.id.address).forEach(id ->
                onView(withId(id)).check(matches(withErrorInInputLayout(EMPTY_ERROR_MESSAGE)))
        );
    }

    @Test
    public void cannotEditEmail() {
        onView(withId(R.id.email)).check(matches(not(isEnabled())));
    }



}
