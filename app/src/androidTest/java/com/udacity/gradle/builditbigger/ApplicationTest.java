package com.udacity.gradle.builditbigger;

import android.app.Application;
import android.test.ApplicationTestCase;

import java.io.IOError;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {

    private String returnedJoke;

    public ApplicationTest() {
        super(Application.class);
    }

    public  void testCallGCEJokerAsyncTask() {

        /*
            signal solution learned from:
            http://stackoverflow.com/questions/2321829/android-asynctask-testing-with-android-test-framework
            and well understood from:
            http://howtodoinjava.com/2013/07/18/when-to-use-countdownlatch-java-concurrency-example-tutorial/
         */

        //Start waiting for execution of one thread (AsyncTask)
        final CountDownLatch signal = new CountDownLatch(1);

        CallGCEJokerAsyncTask jokerAsyncTask = new CallGCEJokerAsyncTask(null);
        jokerAsyncTask.execute(getContext());
        jokerAsyncTask.setListener(new AsyncTaskTestListener() {
            @Override
            public void onTaskFinished(String joke) {
                returnedJoke = joke;
                signal.countDown(); //signal thread is over as callback was called from onPostExecute (last line)
            }
        });

        try {
            signal.await(); //Wait until AsyncTask has finished.
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new Error("Error occurred waiting for background task to execute.");
        }

        assertFalse("Empty or null string returned from GCE Joker",
                returnedJoke == null ||
                        returnedJoke.length() == 0);

    }
}