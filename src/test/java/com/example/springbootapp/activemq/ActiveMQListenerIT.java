package com.example.springbootapp.activemq;

import com.example.springbootapp.ApplicationIT;
import com.example.springbootapp.config.TestMessageProducer;
import com.example.springbootapp.dao.company.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ActiveMQListenerIT extends ApplicationIT {

    @Autowired
    private TestMessageProducer testMessageProducer;
    @Autowired
    private CompanyRepository companyRepository;

    /**
     * This approach checks the database result of the code that receives messages from the message broker.
     */
    @Test
    void onMessage3() {

        // given
        var companyName = UUID.randomUUID().toString();

        // when
        testMessageProducer.sendFromCompanyTopic1(companyName, true);

        // then
        var e = companyRepository.findByName(companyName);
        assertThat(e).isNotNull();
        assertThat(e.getName()).isEqualTo(companyName);
    }
}