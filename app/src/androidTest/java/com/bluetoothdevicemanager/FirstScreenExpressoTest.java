package com.bluetoothdevicemanager;

import android.content.ComponentName;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.rule.ActivityTestRule;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getTargetContext;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

@RunWith(AndroidJUnit4.class)
public class FirstScreenExpressoTest {

    @Rule
    public ActivityTestRule<FirstScreenActivity> mActivityRule =
            new ActivityTestRule<>(FirstScreenActivity.class);

    @Rule public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    @Before
    public void setUp() {
        Intents.init();
    }

    @Test
    public void ensureInitViewState() {
        onView(withId(R.id.tvListEmpty)).check(matches(withText(R.string.empty_list)));
        onView(withId(R.id.tvListEmpty)).check(matches((isDisplayed())));
        onView(withId(R.id.list)).check(matches(not(isDisplayed())));
    }

    @Test
    public void ensureIfSecondScreenIsLaunched() {
        onView(withId(R.id.ibSavedDevices)).perform(ViewActions.click());
        intended(hasComponent(new ComponentName(getTargetContext(),SecondScreenActivity.class)));
    }

    @Test
    public void ensureIfOnSearchDialogShow(){ //Activar el bluetooth primero
        onView(withId(R.id.ibRefresh)).perform(ViewActions.click());
        onView(withText(R.string.init_search)).check(matches(isDisplayed()));
    }

    @After
    public void afterTest() throws Exception{
        Intents.release();
    }
}
