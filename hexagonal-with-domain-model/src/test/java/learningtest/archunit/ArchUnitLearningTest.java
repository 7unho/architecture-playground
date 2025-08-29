package learningtest.archunit;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "learningtest.archunit")
public class ArchUnitLearningTest {
    @Test
    @DisplayName("Application 클래스를 의존하는 클래스는 application, adapter에만 존재해야 한다.")
    void application() {
        ArchRule archRule = classes()
                .that().resideInAPackage("..application..")
                .should().onlyHaveDependentClassesThat().resideInAnyPackage("..application..", "..adapter..");

        archRule.check(new ClassFileImporter().importPackages("learningtest.archunit"));
    }

    @ArchTest
    @DisplayName("Application 클래스는 adapter의 클래스를 의존하면 안 된다.")
    void adapter(JavaClasses classes) {
        noClasses().that().resideInAPackage("..application..")
                .should().dependOnClassesThat().resideInAPackage("..adapter..")
                .check(classes);
    }

    @ArchTest
    @DisplayName("Doamin 클래스는 domain, java에만 의존성을 가져야 한다.")
    void domain(JavaClasses classes) {
        classes().that().resideInAPackage("..domain..")
                .should().onlyHaveDependentClassesThat().resideInAnyPackage("..domain..", "..java..")
                .check(classes);
    }
}