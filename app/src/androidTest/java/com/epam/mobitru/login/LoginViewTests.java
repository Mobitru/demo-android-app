package com.epam.mobitru.login;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.startsWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.epam.mobitru.AppBaseTest;
import com.epam.mobitru.MainActivity;
import com.epam.mobitru.R;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class LoginViewTests extends AppBaseTest {


    private static final String INVALID_LOGIN = "testuser@mobitru.com1";
    private static final String INVALID_PASSWORD = "password2";
    private static final String DEFAULT_LOGIN_ERROR = "Incorrect email or password";


    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule
            = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void loginValidCredential() throws InterruptedException {
        enterCredsAndSingIn(VALID_LOGIN, VALID_PASSWORD);
        onView(withId(R.id.category)).check(matches(withText(startsWith("Mobile phones"))));
    }

    @Test
    public void loginInValidCredential() throws InterruptedException {
        enterCredsAndSingIn(INVALID_LOGIN, INVALID_PASSWORD);
        onView(withText(DEFAULT_LOGIN_ERROR)).check(matches(isDisplayed()));
    }

    @Test
    public void loginEmptyUsername() throws InterruptedException {
        enterCredsAndSingIn(EMPTY, VALID_PASSWORD);
        onView(withText(DEFAULT_LOGIN_ERROR)).check(matches(isDisplayed()));
    }

    @Test
    public void loginEmptyPassword() throws InterruptedException {
        enterCredsAndSingIn(VALID_LOGIN, EMPTY);
        onView(withText(DEFAULT_LOGIN_ERROR)).check(matches(isDisplayed()));
    }

    @Test
    public void loginEmptyPasswordAndUserName() throws InterruptedException {
        enterCredsAndSingIn(EMPTY, EMPTY);
        onView(withText(DEFAULT_LOGIN_ERROR)).check(matches(isDisplayed()));
    }

}
