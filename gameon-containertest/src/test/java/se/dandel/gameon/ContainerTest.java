package se.dandel.gameon;

import io.smallrye.config.inject.ConfigProducer;
import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.adapter.jpa.ContainerTestEntityManagerProducer;
import se.dandel.gameon.adapter.jpa.EntityManagerProducer;
import se.dandel.gameon.datamodel.test.jpa.PersistenceTestManager;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@EnableAutoWeld
@AddPackages({GameonPackageRoot.class})
@AddBeanClasses({ConfigProducer.class})
@AddBeanClasses({PersistenceTestManager.class, ContainerTestEntityManagerProducer.class, EntityManagerProducer.class})
public @interface ContainerTest {
    Logger LOGGER = LoggerFactory.getLogger(ContainerTest.class);

}
