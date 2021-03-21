package se.dandel.gameon;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.library.Architectures.onionArchitecture;

@AnalyzeClasses(packages = "se.dandel.gameon", importOptions = {ImportOption.DoNotIncludeTests.class})
public class ArchitectureTest {

    @ArchTest
    static final ArchRule onion_architecture_is_respected = onionArchitecture()
            .domainModels("se.dandel.gameon.domain.model..")
            .domainServices("se.dandel.gameon.domain.service..", "se.dandel.gameon.domain.repository..", "se.dandel.gameon.domain.port..")
            .applicationServices("se.dandel.gameon.application.service..")
//            .adapter("cli", "se.dandel.gameon.adapter.cli..")
            .adapter("persistence", "se.dandel.gameon.adapter.jpa..")
            .adapter("out.rest", "se.dandel.gameon.adapter.out.rest..")
            .adapter("in.rest", "se.dandel.gameon.adapter.in.rest..");

}
