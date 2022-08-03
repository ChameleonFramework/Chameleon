plugins {
    id("chameleon.publishing")
    id("java-platform")
}

indra {
    configurePublications {
        from(components["javaPlatform"])
    }
}

dependencies {
    constraints {
        for (subproject in rootProject.subprojects) {
            if (subproject != project && !subproject.name.contains("example")) {
                api(project(subproject.path))
            }
        }
    }
}