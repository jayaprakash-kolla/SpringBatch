package com.springboot.springbatch.Config;

import com.springboot.springbatch.Entity.Customer;
import org.springframework.batch.item.ItemProcessor;

public class CustomerDataProcessor implements ItemProcessor<Customer,Customer> {

    @Override
    public Customer process(Customer customer) throws Exception {
        return customer;
    }
}
