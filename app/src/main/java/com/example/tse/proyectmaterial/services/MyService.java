package com.example.tse.proyectmaterial.services;

import com.example.tse.proyectmaterial.logging.L;

import me.tatarka.support.job.JobParameters;
import me.tatarka.support.job.JobService;

/**
 * Created by TSE on 16/09/2015.
 */
public class MyService extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        L.t(this,"onStartJob");
        jobFinished(jobParameters,false);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
