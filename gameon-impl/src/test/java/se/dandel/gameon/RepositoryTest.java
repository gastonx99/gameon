package se.dandel.gameon;

import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.AddExtensions;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.dandel.gameon.datamodel.test.jpa.PersistenceTestManager;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@EnableAutoWeld
@AddExtensions({DatabaseManagerExtension.class})
@AddPackages({se.dandel.gameon.adapter.jpa.EntityManagerProducer.class})
@AddPackages(se.dandel.gameon.domain.repository.TournamentRepository.class)
@AddBeanClasses({PersistenceTestManager.class})
@AddPackages({se.dandel.gameon.RepositoryTest.class})
public @interface RepositoryTest {
    Logger LOGGER = LoggerFactory.getLogger(RepositoryTest.class);

}
