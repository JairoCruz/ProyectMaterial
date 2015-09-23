package com.example.tse.proyectmaterial.services;

import android.os.AsyncTask;

import com.example.tse.proyectmaterial.logging.L;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**
 * Created by TSE on 16/09/2015.
 */
public class MyService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        L.t(this, "onStartJob");
        // jobFinished(jobParameters,false);
        new MyTask(this);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    private class MyTask extends AsyncTask<JobParameters, Void, JobParameters>{

        MyService myService;
        MyTask(MyService myService){
            this.myService = myService;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JobParameters doInBackground(JobParameters... params) {
            return params[0];
        }

        @Override
        protected void onPostExecute(JobParameters jobParameters) {
            myService.jobFinished(jobParameters,false);
        }
    }
}
