package com.april2nd.hexagonalwithdomainmodel;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.library.Architectures;

@AnalyzeClasses(packages = "com.april2nd.hexagonalwithdomainmodel", importOptions = ImportOption.DoNotIncludeTests.class)
public class HexagonalArchitectureTest {
    @ArchTest
    void hexagonalArchitecture(JavaClasses classes) {
        Architectures.layeredArchitecture()
                .consideringAllDependencies()
                .layer("domain").definedBy("com.april2nd.hexagonalwithdomainmodel.domain..")
                .layer("application").definedBy("com.april2nd.hexagonalwithdomainmodel.application..")
                .layer("adapter").definedBy("com.april2nd.hexagonalwithdomainmodel.adapter..")
                .whereLayer("domain").mayOnlyBeAccessedByLayers("adapter", "application")
                .whereLayer("application").mayOnlyBeAccessedByLayers("adapter")
                .whereLayer("adapter").mayNotBeAccessedByAnyLayer()
                .check(classes);


    }
}
