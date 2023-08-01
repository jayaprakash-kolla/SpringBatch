package com.springboot.springbatch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBatchApplication {

    public SpringBatchApplication(JobLauncher jobLauncher, Job job){
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    private final JobLauncher jobLauncher;
    private final Job job;

    public static void main(String[] args) {
        SpringBatchApplication sba = SpringApplication.run(SpringBatchApplication.class, args).getBean(SpringBatchApplication.class);
        //sba.saveCustomerDetails();

        sba.customerJobLauncherMethod();
    }

    /* public void customerJobInitializer()
    {

        CustomerJobLauncher customerJobLauncher = new CustomerJobLauncher();
        customerJobLauncher.customerJobLauncherMethod();

    }
    */

        public void customerJobLauncherMethod()
        {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("startAt",System.currentTimeMillis())
                    .toJobParameters();

            try{
                jobLauncher.run(job,jobParameters);
            }
            catch(JobInstanceAlreadyCompleteException | JobExecutionAlreadyRunningException |
                  JobParametersInvalidException | JobRestartException e) {
                e.printStackTrace();
            }

        }

    /*
    @Bean
    CommandLineRunner commandLineRunner(customerRepository CustomerRepository){
        return args -> {
            CustomerRepository.save(new customer(1,"jayaprakash",623693,"gmail"));
        };
    }*/

}
