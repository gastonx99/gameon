package se.dandel.gameon.datamodel.test.jpa;

import org.apache.openwebbeans.junit5.Cdi;
import org.apache.openwebbeans.junit5.internal.CdiExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@ExtendWith(CdiExtension.class)
@Cdi(classes = {PersistenceTestManager.class}, reusable = true)
public @interface JpaTest {
    Logger LOGGER = LoggerFactory.getLogger(JpaTest.class);

}
