package com.bluetoothdevicemanager;

import android.support.test.espresso.action.ViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class SecondScreenExpressoTest {

    @Rule
    public ActivityTestRule<SecondScreenActivity> mActivityRule =
            new ActivityTestRule<>(SecondScreenActivity.class);

    @Test
    public void ensureInitViewState() {
        onView(withId(R.id.tvListEmpty)).check(matches(withText(R.string.no_saved_devices)));
        onView(withId(R.id.tvListEmpty)).check(matches((isDisplayed())));
        onView(withId(R.id.list)).check(matches(not(isDisplayed())));
    }

    @Test
    public void ensureIfOnSearchDialogShow(){
        onView(withId(R.id.action_update)).perform(ViewActions.click());
        onView(withText(R.string.download)).check(matches(isDisplayed()));
    }

    @Test
    public void ensureIfOrderInitDisable(){
        onView(withId(R.id.action_order)).check(matches(not(isEnabled())));
    }
}
