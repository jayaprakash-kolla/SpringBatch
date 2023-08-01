package com.springboot.springbatch.JobLauncher;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

@AllArgsConstructor
@NoArgsConstructor
public class CustomerJobLauncher {

    private JobLauncher jobLauncher;

    private Job job;

    public void customerJobLauncherMethod()
    {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt",System.currentTimeMillis())
                .toJobParameters();

        try{
            jobLauncher.run(job,jobParameters);
        }
        catch(JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException|
                JobParametersInvalidException| JobRestartException e) {
            e.printStackTrace();
        }

    }
}
