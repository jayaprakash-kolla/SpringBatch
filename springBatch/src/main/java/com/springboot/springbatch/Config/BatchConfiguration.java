package com.springboot.springbatch.Config;

import com.springboot.springbatch.Entity.Customer;
import com.springboot.springbatch.Repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableBatchProcessing
@AllArgsConstructor
public class BatchConfiguration {

    private CustomerRepository customerRepository;

    //private JobBuilder jobBuilder;

    private PlatformTransactionManager transactionManager;

    private JobRepository jobRepository;

    @Bean
    public FlatFileItemReader<Customer> customerDataReader(){

        FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("src/main/resources/customers.csv"));
        itemReader.setName("customerDataReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());

        return itemReader;
    }

    private LineMapper<Customer> lineMapper() { // This method has the logic for Reading the data.

        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

        // LineTokenizer will extract the line form the csv file.
        DelimitedLineTokenizer LineTokenizer = new DelimitedLineTokenizer();
        LineTokenizer.setDelimiter(",");
        LineTokenizer.setStrict(false);
        LineTokenizer.setNames("id","firstName","lastName","email","gender","contactNo","country","dob");

        // fieldSetMapper will map the line to the target value.
        BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Customer.class);

        // now provide both lineTokenizer and fieldSetMapper to the lineMapper object.
        lineMapper.setLineTokenizer(LineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;

    }


    @Bean
    public CustomerDataProcessor customerDataProcessor(){
        return new CustomerDataProcessor();
    }


    @Bean
    public RepositoryItemWriter<Customer> customerDataWriter(){

        // This method has the logic for Writing the data in the database.
        RepositoryItemWriter<Customer> itemWriter = new RepositoryItemWriter<>();
        itemWriter.setRepository(customerRepository);
        itemWriter.setMethodName("save");

        return itemWriter;
    }


    @Bean
    public Step step() throws IllegalStateException{         // This is the logic for creating a step.
        // StepBuilderFactory is depreciated and cannot be used.
        return new StepBuilder("consumer-csv-step",jobRepository)
                .<Customer,Customer>chunk(10,transactionManager)
                .reader(customerDataReader())
                .processor(customerDataProcessor())
                .writer(customerDataWriter()).build();

        /*StepBuilder stepBuilder = new StepBuilder("customer-csv-step",jobRepository);


        return stepBuilder.<Customer,Customer>chunk(10,transactionManager)
                .reader(customerDataReader())
                .processor(customerDataProcessor())
                .writer(customerDataWriter())
                .build();*/


    }


    @Bean
    public Job job(){           // This is the logic for creating a Job.
        return new JobBuilder("consumer-csv-job",jobRepository).start(step()).build();
    }



}
