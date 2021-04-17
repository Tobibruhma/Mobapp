package com.example.text;


import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static androidx.test.InstrumentationRegistry.getInstrumentation;
import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.*;
import static androidx.test.espresso.assertion.ViewAssertions.*;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import com.example.text.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction button = onView(
allOf(withId(R.id.button_sign), withText("ВОЙТИ"),
withParent(allOf(withId(R.id.relativeLayout2),
withParent(withId(android.R.id.content)))),
isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
allOf(withId(R.id.button_reg), withText("РЕГИСТРАЦИЯ"),
withParent(allOf(withId(R.id.relativeLayout2),
withParent(withId(android.R.id.content)))),
isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction button3 = onView(
allOf(withId(R.id.button_sign), withText("ВОЙТИ"),
withParent(allOf(withId(R.id.relativeLayout2),
withParent(withId(android.R.id.content)))),
isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction materialButton = onView(
allOf(withId(R.id.button_sign), withText("ВОЙТИ"),
childAtPosition(
allOf(withId(R.id.relativeLayout2),
childAtPosition(
withId(android.R.id.content),
0)),
1),
isDisplayed()));
        materialButton.perform(click());

         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(7000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }

        pressBack();

         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(7000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }

        ViewInteraction materialButton2 = onView(
allOf(withId(R.id.button_reg), withText("регистрация"),
childAtPosition(
allOf(withId(R.id.relativeLayout2),
childAtPosition(
withId(android.R.id.content),
0)),
2),
isDisplayed()));
        materialButton2.perform(click());

         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(7000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }

        ViewInteraction editText = onView(
allOf(withId(R.id.login_input), withText("Логин"),
withParent(withParent(withId(android.R.id.content))),
isDisplayed()));
        editText.check(matches(withText("Логин")));

        ViewInteraction editText2 = onView(
allOf(withId(R.id.name_input), withText("Имя"),
withParent(withParent(withId(android.R.id.content))),
isDisplayed()));
        editText2.check(matches(withText("Admn")));

        ViewInteraction button4 = onView(
allOf(withId(R.id.button_reg), withText("РЕГИСТРАЦИЯ"),
withParent(withParent(withId(android.R.id.content))),
isDisplayed()));
        button4.check(matches(isDisplayed()));

        pressBack();

         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(7000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }

        ViewInteraction materialButton3 = onView(
allOf(withId(R.id.button_reg), withText("регистрация"),
childAtPosition(
allOf(withId(R.id.relativeLayout2),
childAtPosition(
withId(android.R.id.content),
0)),
2),
isDisplayed()));
        materialButton3.perform(click());

         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(7000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }

        pressBack();

         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(7000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }

        ViewInteraction materialButton4 = onView(
allOf(withId(R.id.button_reg), withText("регистрация"),
childAtPosition(
allOf(withId(R.id.relativeLayout2),
childAtPosition(
withId(android.R.id.content),
0)),
2),
isDisplayed()));
        materialButton4.perform(click());

         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(7000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }

        pressBack();

         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(7000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }

        ViewInteraction materialButton5 = onView(
allOf(withId(R.id.button_sign), withText("ВОЙТИ"),
childAtPosition(
allOf(withId(R.id.relativeLayout2),
childAtPosition(
withId(android.R.id.content),
0)),
1),
isDisplayed()));
        materialButton5.perform(click());

         // Added a sleep statement to match the app's execution delay.
 // The recommended way to handle such scenarios is to use Espresso idling resources:
  // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
try {
 Thread.sleep(7000);
 } catch (InterruptedException e) {
 e.printStackTrace();
 }

        ViewInteraction textView = onView(
allOf(withId(R.id.admin_panel_link), withText("For admin..."),
withParent(allOf(withId(R.id.relativeLayout),
withParent(withId(android.R.id.content)))),
isDisplayed()));
        textView.check(doesNotExist());

        ViewInteraction textView2 = onView(
allOf(withId(R.id.admin_panel_link), withText("For admin..."),
withParent(allOf(withId(R.id.relativeLayout),
withParent(withId(android.R.id.content)))),
isDisplayed()));
        textView2.check(doesNotExist());

        ViewInteraction materialTextView = onView(
allOf(withId(R.id.admin_panel_link), withText("For admin..."),
childAtPosition(
allOf(withId(R.id.relativeLayout),
childAtPosition(
withId(android.R.id.content),
0)),
6),
isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction textView3 = onView(
allOf(withId(R.id.not_admin_panel_link), withText("For user..."),
withParent(allOf(withId(R.id.relativeLayout),
withParent(withId(android.R.id.content)))),
isDisplayed()));
        textView3.check(matches(isDisplayed()));
        }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
