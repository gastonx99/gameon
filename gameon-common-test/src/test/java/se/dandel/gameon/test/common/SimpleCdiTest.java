package se.dandel.gameon.test.common;

import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

@EnableAutoWeld
@AddBeanClasses(SimpleTestService.class)
public class SimpleCdiTest {
    final Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Inject
    private SimpleTestService service;

    @Test
    void simple() {
        // Given
        LOGGER.debug("Injected {}", service);

        // When

        // Then
    }
}
