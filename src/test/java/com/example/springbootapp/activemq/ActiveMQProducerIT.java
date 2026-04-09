package com.example.springbootapp.activemq;

import com.example.springbootapp.ApplicationIT;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ActiveMQProducerIT extends ApplicationIT {

    /**
     * This approach check the messages that message broker receives from the code.
     */
    @Test
    void sendToTopic1() {

        // given
        testMessageListener.clear();

        // when
        var expected = activeMQProducer.sendToTopic();
        var actual = testMessageListener.getFirstFromTopic1();

        // then
        assertThat(actual).isEqualTo(expected);
    }
}