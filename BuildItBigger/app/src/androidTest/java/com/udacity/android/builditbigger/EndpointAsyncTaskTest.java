package com.udacity.android.builditbigger;

import android.support.test.runner.AndroidJUnit4;
import android.support.test.rule.ActivityTestRule;
import android.util.Log;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class EndpointAsyncTaskTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testAsyncTask() {

        try {
            EndpointAsyncTask task = new EndpointAsyncTask();
            String myJoke = task.execute().get();
            assertNotNull(myJoke);
        } catch (Exception e) {
            Log.e("TestAsyncEndpoint", e.getStackTrace().toString());
        }

    }
}
