package com.epam.mobitru;

import static android.content.ContentValues.TAG;
import static androidx.core.util.Preconditions.checkNotNull;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.Visibility.VISIBLE;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.supportsInputMethods;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.google.android.apps.common.testing.accessibility.framework.AccessibilityCheckPreset.PRERELEASE;
import static com.google.android.apps.common.testing.accessibility.framework.AccessibilityCheckResultUtils.matchesCheckNames;
import static com.google.android.apps.common.testing.accessibility.framework.AccessibilityCheckResultUtils.matchesViews;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;

import android.util.Log;
import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.accessibility.AccessibilityChecks;
import androidx.test.espresso.matcher.BoundedMatcher;

import com.epam.mobitru.matchers.RViewMatcher;
import com.google.android.material.textfield.TextInputLayout;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.BeforeClass;

import java.time.Duration;
import java.util.Optional;

public class AppBaseTest {

    protected static final String VALID_LOGIN = "testuser@mobitru.com";
    protected static final String VALID_PASSWORD = "password1";

    protected static final String CORRECT_FN = "Edvard";
    protected static final String CORRECT_LN = "Grieg";
    protected static final String CORRECT_ADDRESS = "Oslo, Norway";

    protected static final String EMPTY = "";

    @BeforeClass
    public static void enableAccessibilityChecks() {
        AccessibilityChecks.enable()
                .setRunChecksFromRootView(true) // always check entire screen instead of specific view
                .setCheckPreset(PRERELEASE) // use preset with maximum checks
                .setSuppressingResultMatcher(anyOf(
                        allOf(
                                //example of suppressing the Speakable check for any Input
                                matchesCheckNames(is("SpeakableTextPresentCheck")),
                                matchesViews(supportsInputMethods())
                        ), allOf(
                                //example of suppressing the TargetSize check for specific elements
                                matchesCheckNames(is("TouchTargetSizeCheck")),
                                matchesViews(anyOf(withId(R.id.cart_title), withId(R.id.sortBy), withId(R.id.edit)))
                        )
                ));

    }

//UNCOMMENT below and comment previous one to perform all checks
//    @BeforeClass
//    public static void enableAccessibilityChecks() {
//        AccessibilityChecks.enable()
//                .setRunChecksFromRootView(true); // always check entire screen instead of specific view
//    }

    protected void logout() {
        onView(allOf(withText("Account"), withEffectiveVisibility(VISIBLE))).perform(click());
        clickWithWait(onView(withId(R.id.logout)));
        onView(withId(R.id.login_signin)).check(matches(is(isDisplayed())));
    }

    protected void enterDefaultCredsAndSingIn() throws InterruptedException {
        enterCredsAndSingIn(VALID_LOGIN, VALID_PASSWORD);
    }

    protected void enterCredsAndSingIn(String username, String password) throws InterruptedException {
        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.login_email))))
                .perform(typeText(username), closeSoftKeyboard());
        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.login_password))))
                .perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.login_signin)).perform(click());
        Thread.sleep(Duration.ofSeconds(2).toMillis());
    }

    protected void enterUserInfoAndSave() throws InterruptedException {
        enterUserInfoAndSave(CORRECT_FN, CORRECT_LN, CORRECT_ADDRESS);
    }

    protected void enterUserInfoAndSave(String fn, String ln, String address) throws InterruptedException {
        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.first_name))))
                .perform(typeText(fn), closeSoftKeyboard());
        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.last_name))))
                .perform(typeText(ln), closeSoftKeyboard());
        onView(allOf(supportsInputMethods(), isDescendantOfA(withId(R.id.address))))
                .perform(typeText(address), closeSoftKeyboard());
        onView(withId(R.id.save)).perform(click());
        Thread.sleep(Duration.ofSeconds(1).toMillis());
    }

    protected void performAddToCart(int indx) {
        try {
            performRemoveFromCartIfPresent(indx);
            clickWithWait(onView(new RViewMatcher(R.id.product_list).
                    elementOnView(indx, R.id.addToCart)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void clickWithWait(ViewInteraction view) {
        try {
            view.perform(click());
            Thread.sleep(Duration.ofSeconds(1).toMillis());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void performRemoveFromCartIfPresent(int indx) {
        try {
            clickWithWait(onView(new RViewMatcher(R.id.product_list).
                    elementOnView(indx, R.id.removeFromCart)));
        } catch (Exception e) {
            Log.w(TAG, "remove from cart item is not present");
        }
    }

    public static Matcher<View> withErrorInInputLayout(final Matcher<String> stringMatcher) {
        checkNotNull(stringMatcher);

        return new BoundedMatcher<View, TextInputLayout>(TextInputLayout.class) {
            String actualError = "";

            @Override
            public void describeTo(Description description) {
            }

            @Override
            public boolean matchesSafely(TextInputLayout textInputLayout) {
                CharSequence error = textInputLayout.getError();
                return Optional.ofNullable(error).map(item -> {
                    actualError = error.toString();
                    return stringMatcher.matches(actualError);
                }).orElse(false);
            }
        };
    }

    public static Matcher<View> withErrorInInputLayout(final String string) {
        return withErrorInInputLayout(equalTo(string));
    }

}
