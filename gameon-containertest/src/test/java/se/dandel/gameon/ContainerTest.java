package se.dandel.gameon;

import org.jboss.weld.junit5.auto.AddBeanClasses;
import org.jboss.weld.junit5.auto.AddPackages;
import org.jboss.weld.junit5.auto.EnableAutoWeld;
import se.dandel.gameon.adapter.jpa.PersistenceTestManager;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

;

@Target(TYPE)
@Retention(RUNTIME)
@EnableAutoWeld
@AddPackages({se.dandel.gameon.adapter.EnvironmentConfig.class})
@AddPackages({se.dandel.gameon.application.service.FetchDataFromApi1Service.class})
@AddPackages({se.dandel.gameon.domain.port.Api1Port.class})
@AddPackages({se.dandel.gameon.domain.repository.TournamentRepository.class})
@AddPackages({se.dandel.gameon.ContainerTest.class})
@AddBeanClasses({PersistenceTestManager.class})
public @interface ContainerTest {
}
