package com.udacity.gradle.builditbigger;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.udacity.gradle.androidlibs.jokeshow.DisplayJokeActivity;
import com.udacity.gradle.backend.jokes.myApi.MyApi;

import java.io.IOException;

/*
  Boilerplate code used from :
  https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints
*/

public class CallGCEJokerAsyncTask extends AsyncTask<Context, Void, String> {

    private static MyApi myApiService = null;
    private Context context;
    ProgressBar progressBar;

    private AsyncTaskTestListener asyncTaskTestListener = null;

    public CallGCEJokerAsyncTask setListener(AsyncTaskTestListener listener) {
        asyncTaskTestListener = listener;
        return this;
    }


    public CallGCEJokerAsyncTask(AppCompatActivity activity) {
        if (activity != null){
            progressBar = (ProgressBar) activity.findViewById(R.id.progressBar);
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onPostExecute(String result) {

        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        if (asyncTaskTestListener == null) {
            Intent intent = new Intent(context, DisplayJokeActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, result);
            context.startActivity(intent);
        } else{
            // if listener exists, execute callback
            asyncTaskTestListener.onTaskFinished(result);
        }

    }

    @Override
    protected String doInBackground(Context... params) {
        if(myApiService == null) {  // Only do this once
            MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            myApiService = builder.build();
        }

        context = params[0];

        try {

            return myApiService.getRandomJokeGCE().execute().getData();

        } catch (IOException e) {
            String error = e.getMessage();
            return error;
        }
    }


}
