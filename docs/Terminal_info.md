Last login: Fri Jul 10 09:19:04 on ttys002
/Users/jenskreutzer/Antigravity_Projects/finance-app/run_build_and_deploy.command ; exit;
(base) jenskreutzer@MacBook-Air-von-Jens ~ % /Users/jenskreutzer/Antigravity_Projects/finance-app/run_build_and_deploy.command ; exit;
🛠️  Auslösen des Build & Deploy Prozesses...
🏗️  Starte Produktions-Build
------------------------------------------

☕ Nutze lokales JDK aus der Entwicklungsumgebung...
📦 Baue und optimiere das Vaadin Frontend & Spring Boot Backend...
[INFO] Scanning for projects...
[INFO]
[INFO] ----------------------< com.example:finance-app >-----------------------
[INFO] Building finance-app 1.0.0-SNAPSHOT
[INFO]   from pom.xml
[INFO] --------------------------------[ jar ]---------------------------------
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/vaadin-maven-plugin/25.1.8/vaadin-maven-plugin-25.1.8.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/vaadin-maven-plugin/25.1.8/vaadin-maven-plugin-25.1.8.pom> (6.3 kB at 29 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/vaadin-maven-plugin/25.1.8/vaadin-maven-plugin-25.1.8.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/vaadin-maven-plugin/25.1.8/vaadin-maven-plugin-25.1.8.jar> (34 kB at 118 kB/s)
[WARNING] Parameter 'fork' is unknown for plugin 'spring-boot-maven-plugin:4.0.7:repackage (repackage)'
[INFO]
[INFO] --- clean:3.5.0:clean (default-clean) @ finance-app ---
[INFO] Deleting /Users/jenskreutzer/Antigravity_Projects/finance-app/target
[INFO]
[INFO] --- resources:3.3.1:resources (default-resources) @ finance-app ---
[INFO] Copying 2 resources from src/main/resources to target/classes
[INFO] Copying 1 resource from src/main/resources to target/classes
[INFO]
[INFO] --- compiler:3.14.1:compile (default-compile) @ finance-app ---
[INFO] Recompiling the module because of changed source code.
[INFO] Compiling 5 source files with javac [debug parameters release 21] to target/classes
[INFO] /Users/jenskreutzer/Antigravity_Projects/finance-app/src/main/java/com/example/financeapp/Application.java: /Users/jenskreutzer/Antigravity_Projects/finance-app/src/main/java/com/example/financeapp/Application.java verwendet oder überschreibt eine veraltete API.
[INFO] /Users/jenskreutzer/Antigravity_Projects/finance-app/src/main/java/com/example/financeapp/Application.java: Wiederholen Sie die Kompilierung mit -Xlint:deprecation, um Details zu erhalten.
[INFO]
[INFO] --- vaadin:25.1.8:build-frontend (default) @ finance-app ---
Downloading from central: <https://repo.maven.apache.org/maven2/org/slf4j/slf4j-api/2.0.17/slf4j-api-2.0.17.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/slf4j/slf4j-api/2.0.17/slf4j-api-2.0.17.pom> (2.8 kB at 101 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/slf4j/slf4j-parent/2.0.17/slf4j-parent-2.0.17.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/slf4j/slf4j-parent/2.0.17/slf4j-parent-2.0.17.pom> (13 kB at 446 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-maven-plugin/25.1.7/hilla-maven-plugin-25.1.7.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-maven-plugin/25.1.7/hilla-maven-plugin-25.1.7.pom> (3.0 kB at 108 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-project/25.1.7/hilla-project-25.1.7.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-project/25.1.7/hilla-project-25.1.7.pom> (13 kB at 426 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/flow-maven-plugin/25.1.11/flow-maven-plugin-25.1.11.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/flow-maven-plugin/25.1.11/flow-maven-plugin-25.1.11.pom> (4.2 kB at 150 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/flow-plugins/25.1.11/flow-plugins-25.1.11.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/flow-plugins/25.1.11/flow-plugins-25.1.11.pom> (984 B at 34 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/flow-plugin-base/25.1.11/flow-plugin-base-25.1.11.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/flow-plugin-base/25.1.11/flow-plugin-base-25.1.11.pom> (2.5 kB at 88 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/license-checker/3.0.1/license-checker-3.0.1.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/license-checker/3.0.1/license-checker-3.0.1.pom> (2.2 kB at 78 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/license-checker-parent/3.0.1/license-checker-parent-3.0.1.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/license-checker-parent/3.0.1/license-checker-parent-3.0.1.pom> (4.5 kB at 162 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/flow-polymer2lit/25.1.11/flow-polymer2lit-25.1.11.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/flow-polymer2lit/25.1.11/flow-polymer2lit-25.1.11.pom> (3.7 kB at 143 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/commons-io/commons-io/2.21.0/commons-io-2.21.0.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/commons-io/commons-io/2.21.0/commons-io-2.21.0.pom> (21 kB at 672 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/apache/commons/commons-parent/91/commons-parent-91.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/apache/commons/commons-parent/91/commons-parent-91.pom> (79 kB at 2.2 MB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/apache/commons/commons-text/1.15.0/commons-text-1.15.0.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/apache/commons/commons-text/1.15.0/commons-text-1.15.0.pom> (24 kB at 677 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/apache/commons/commons-parent/93/commons-parent-93.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/apache/commons/commons-parent/93/commons-parent-93.pom> (79 kB at 736 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/apache/commons/commons-lang3/3.20.0/commons-lang3-3.20.0.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/apache/commons/commons-lang3/3.20.0/commons-lang3-3.20.0.pom> (33 kB at 1.0 MB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/apache/commons/commons-parent/92/commons-parent-92.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/apache/commons/commons-parent/92/commons-parent-92.pom> (80 kB at 2.2 MB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/jboss/forge/roaster/roaster-api/2.31.0.Final/roaster-api-2.31.0.Final.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/jboss/forge/roaster/roaster-api/2.31.0.Final/roaster-api-2.31.0.Final.pom> (535 B at 19 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/jboss/forge/roaster/roaster-parent/2.31.0.Final/roaster-parent-2.31.0.Final.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/jboss/forge/roaster/roaster-parent/2.31.0.Final/roaster-parent-2.31.0.Final.pom> (6.7 kB at 239 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/jboss/forge/roaster/roaster-jdt/2.31.0.Final/roaster-jdt-2.31.0.Final.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/jboss/forge/roaster/roaster-jdt/2.31.0.Final/roaster-jdt-2.31.0.Final.pom> (4.4 kB at 143 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/apache/maven/shared/maven-invoker/3.3.0/maven-invoker-3.3.0.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/apache/maven/shared/maven-invoker/3.3.0/maven-invoker-3.3.0.pom> (4.2 kB at 146 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-engine-core/25.1.7/hilla-engine-core-25.1.7.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-engine-core/25.1.7/hilla-engine-core-25.1.7.pom> (2.1 kB at 77 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-typescript-generator/25.1.7/hilla-typescript-generator-25.1.7.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-typescript-generator/25.1.7/hilla-typescript-generator-25.1.7.pom> (7.4 kB at 255 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/io/swagger/core/v3/swagger-core-jakarta/2.2.38/swagger-core-jakarta-2.2.38.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/io/swagger/core/v3/swagger-core-jakarta/2.2.38/swagger-core-jakarta-2.2.38.pom> (5.6 kB at 192 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/io/swagger/core/v3/swagger-project-jakarta/2.2.38/swagger-project-jakarta-2.2.38.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/io/swagger/core/v3/swagger-project-jakarta/2.2.38/swagger-project-jakarta-2.2.38.pom> (22 kB at 710 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/io/swagger/core/v3/swagger-annotations-jakarta/2.2.38/swagger-annotations-jakarta-2.2.38.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/io/swagger/core/v3/swagger-annotations-jakarta/2.2.38/swagger-annotations-jakarta-2.2.38.pom> (3.3 kB at 117 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/io/swagger/core/v3/swagger-models-jakarta/2.2.38/swagger-models-jakarta-2.2.38.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/io/swagger/core/v3/swagger-models-jakarta/2.2.38/swagger-models-jakarta-2.2.38.pom> (3.4 kB at 126 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.19.2/jackson-annotations-2.19.2.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/core/jackson-annotations/2.19.2/jackson-annotations-2.19.2.pom> (6.9 kB at 230 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/jackson-parent/2.19.3/jackson-parent-2.19.3.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/jackson-parent/2.19.3/jackson-parent-2.19.3.pom> (7.2 kB at 58 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/fasterxml/oss-parent/69/oss-parent-69.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/fasterxml/oss-parent/69/oss-parent-69.pom> (24 kB at 695 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/jakarta/xml/bind/jakarta.xml.bind-api/3.0.1/jakarta.xml.bind-api-3.0.1.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/jakarta/xml/bind/jakarta.xml.bind-api/3.0.1/jakarta.xml.bind-api-3.0.1.pom> (13 kB at 414 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/jakarta/xml/bind/jakarta.xml.bind-api-parent/3.0.1/jakarta.xml.bind-api-parent-3.0.1.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/jakarta/xml/bind/jakarta.xml.bind-api-parent/3.0.1/jakarta.xml.bind-api-parent-3.0.1.pom> (9.5 kB at 340 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/sun/activation/jakarta.activation/2.0.1/jakarta.activation-2.0.1.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/sun/activation/jakarta.activation/2.0.1/jakarta.activation-2.0.1.pom> (2.0 kB at 71 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/sun/activation/all/2.0.1/all-2.0.1.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/sun/activation/all/2.0.1/all-2.0.1.pom> (23 kB at 761 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.19.2/jackson-databind-2.19.2.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.19.2/jackson-databind-2.19.2.pom> (23 kB at 780 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/jackson-base/2.19.2/jackson-base-2.19.2.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/jackson-base/2.19.2/jackson-base-2.19.2.pom> (12 kB at 373 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/jackson-bom/2.19.2/jackson-bom-2.19.2.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/jackson-bom/2.19.2/jackson-bom-2.19.2.pom> (20 kB at 698 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/core/jackson-core/2.19.2/jackson-core-2.19.2.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/core/jackson-core/2.19.2/jackson-core-2.19.2.pom> (9.4 kB at 349 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/dataformat/jackson-dataformat-yaml/2.19.2/jackson-dataformat-yaml-2.19.2.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/dataformat/jackson-dataformat-yaml/2.19.2/jackson-dataformat-yaml-2.19.2.pom> (3.0 kB at 99 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/dataformat/jackson-dataformats-text/2.19.2/jackson-dataformats-text-2.19.2.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/dataformat/jackson-dataformats-text/2.19.2/jackson-dataformats-text-2.19.2.pom> (3.5 kB at 124 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/dataformat/jackson-dataformat-yaml/2.21.4/jackson-dataformat-yaml-2.21.4.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/dataformat/jackson-dataformat-yaml/2.21.4/jackson-dataformat-yaml-2.21.4.pom> (3.0 kB at 110 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/dataformat/jackson-dataformats-text/2.21.4/jackson-dataformats-text-2.21.4.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/dataformat/jackson-dataformats-text/2.21.4/jackson-dataformats-text-2.21.4.pom> (3.5 kB at 134 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.21.4/jackson-datatype-jsr310-2.21.4.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.21.4/jackson-datatype-jsr310-2.21.4.pom> (5.3 kB at 47 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/module/jackson-modules-java8/2.21.4/jackson-modules-java8-2.21.4.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/module/jackson-modules-java8/2.21.4/jackson-modules-java8-2.21.4.pom> (3.1 kB at 110 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/tools/jackson/dataformat/jackson-dataformat-yaml/3.1.0/jackson-dataformat-yaml-3.1.0.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/tools/jackson/dataformat/jackson-dataformat-yaml/3.1.0/jackson-dataformat-yaml-3.1.0.pom> (2.9 kB at 93 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/tools/jackson/dataformat/jackson-dataformats-text/3.1.0/jackson-dataformats-text-3.1.0.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/tools/jackson/dataformat/jackson-dataformats-text/3.1.0/jackson-dataformats-text-3.1.0.pom> (3.5 kB at 115 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/snakeyaml/snakeyaml-engine/3.0.1/snakeyaml-engine-3.0.1.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/snakeyaml/snakeyaml-engine/3.0.1/snakeyaml-engine-3.0.1.pom> (20 kB at 272 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/jetbrains/kotlin/kotlin-reflect/2.1.21/kotlin-reflect-2.1.21.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/jetbrains/kotlin/kotlin-reflect/2.1.21/kotlin-reflect-2.1.21.pom> (1.4 kB at 53 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/2.1.21/kotlin-stdlib-2.1.21.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/2.1.21/kotlin-stdlib-2.1.21.pom> (2.3 kB at 89 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-runtime-plugin-transfertypes/25.1.7/hilla-runtime-plugin-transfertypes-25.1.7.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-runtime-plugin-transfertypes/25.1.7/hilla-runtime-plugin-transfertypes-25.1.7.pom> (1.7 kB at 64 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/springframework/data/spring-data-commons/4.0.6/spring-data-commons-4.0.6.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/springframework/data/spring-data-commons/4.0.6/spring-data-commons-4.0.6.pom> (10 kB at 363 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/springframework/data/build/spring-data-parent/4.0.6/spring-data-parent-4.0.6.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/springframework/data/build/spring-data-parent/4.0.6/spring-data-parent-4.0.6.pom> (49 kB at 1.6 MB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/springframework/data/build/spring-data-build/4.0.6/spring-data-build-4.0.6.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/springframework/data/build/spring-data-build/4.0.6/spring-data-build-4.0.6.pom> (6.0 kB at 215 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/io/micrometer/micrometer-tracing-bom/1.6.6/micrometer-tracing-bom-1.6.6.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/io/micrometer/micrometer-tracing-bom/1.6.6/micrometer-tracing-bom-1.6.6.pom> (4.4 kB at 156 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/jmolecules/jmolecules-bom/2025.0.2/jmolecules-bom-2025.0.2.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/jmolecules/jmolecules-bom/2025.0.2/jmolecules-bom-2025.0.2.pom> (4.9 kB at 177 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/vaadin-prod-bundle/25.1.8/vaadin-prod-bundle-25.1.8.pom>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/vaadin-prod-bundle/25.1.8/vaadin-prod-bundle-25.1.8.pom> (4.7 kB at 44 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/slf4j/slf4j-api/2.0.17/slf4j-api-2.0.17.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/slf4j/slf4j-api/2.0.17/slf4j-api-2.0.17.jar> (70 kB at 1.9 MB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/tools/jackson/core/jackson-databind/3.1.2/jackson-databind-3.1.2.jar>
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-maven-plugin/25.1.7/hilla-maven-plugin-25.1.7.jar>
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/flow-maven-plugin/25.1.11/flow-maven-plugin-25.1.11.jar>
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/flow-plugin-base/25.1.11/flow-plugin-base-25.1.11.jar>
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/license-checker/3.0.1/license-checker-3.0.1.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/flow-plugin-base/25.1.11/flow-plugin-base-25.1.11.jar> (44 kB at 327 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/apache/maven/shared/maven-invoker/3.3.0/maven-invoker-3.3.0.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-maven-plugin/25.1.7/hilla-maven-plugin-25.1.7.jar> (34 kB at 230 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/flow-polymer2lit/25.1.11/flow-polymer2lit-25.1.11.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/flow-maven-plugin/25.1.11/flow-maven-plugin-25.1.11.jar> (67 kB at 434 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/commons-io/commons-io/2.21.0/commons-io-2.21.0.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/apache/maven/shared/maven-invoker/3.3.0/maven-invoker-3.3.0.jar> (34 kB at 150 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/apache/commons/commons-text/1.15.0/commons-text-1.15.0.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/tools/jackson/core/jackson-databind/3.1.2/jackson-databind-3.1.2.jar> (1.9 MB at 3.7 MB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/apache/commons/commons-lang3/3.20.0/commons-lang3-3.20.0.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/apache/commons/commons-text/1.15.0/commons-text-1.15.0.jar> (265 kB at 470 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/jboss/forge/roaster/roaster-api/2.31.0.Final/roaster-api-2.31.0.Final.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/apache/commons/commons-lang3/3.20.0/commons-lang3-3.20.0.jar> (714 kB at 847 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/jboss/forge/roaster/roaster-jdt/2.31.0.Final/roaster-jdt-2.31.0.Final.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/jboss/forge/roaster/roaster-api/2.31.0.Final/roaster-api-2.31.0.Final.jar> (87 kB at 88 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-engine-core/25.1.7/hilla-engine-core-25.1.7.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/commons-io/commons-io/2.21.0/commons-io-2.21.0.jar> (585 kB at 590 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-typescript-generator/25.1.7/hilla-typescript-generator-25.1.7.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-engine-core/25.1.7/hilla-engine-core-25.1.7.jar> (55 kB at 51 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/io/swagger/core/v3/swagger-core-jakarta/2.2.38/swagger-core-jakarta-2.2.38.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/flow-polymer2lit/25.1.11/flow-polymer2lit-25.1.11.jar> (1.6 MB at 1.2 MB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/io/swagger/core/v3/swagger-annotations-jakarta/2.2.38/swagger-annotations-jakarta-2.2.38.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/io/swagger/core/v3/swagger-annotations-jakarta/2.2.38/swagger-annotations-jakarta-2.2.38.jar> (51 kB at 34 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/jakarta/xml/bind/jakarta.xml.bind-api/3.0.1/jakarta.xml.bind-api-3.0.1.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-typescript-generator/25.1.7/hilla-typescript-generator-25.1.7.jar> (240 kB at 159 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/sun/activation/jakarta.activation/2.0.1/jakarta.activation-2.0.1.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/io/swagger/core/v3/swagger-core-jakarta/2.2.38/swagger-core-jakarta-2.2.38.jar> (252 kB at 167 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/io/swagger/core/v3/swagger-models-jakarta/2.2.38/swagger-models-jakarta-2.2.38.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/sun/activation/jakarta.activation/2.0.1/jakarta.activation-2.0.1.jar> (62 kB at 39 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/dataformat/jackson-dataformat-yaml/2.21.4/jackson-dataformat-yaml-2.21.4.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/dataformat/jackson-dataformat-yaml/2.21.4/jackson-dataformat-yaml-2.21.4.jar> (61 kB at 33 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.21.4/jackson-datatype-jsr310-2.21.4.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/jakarta/xml/bind/jakarta.xml.bind-api/3.0.1/jakarta.xml.bind-api-3.0.1.jar> (129 kB at 68 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/tools/jackson/dataformat/jackson-dataformat-yaml/3.1.0/jackson-dataformat-yaml-3.1.0.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/fasterxml/jackson/datatype/jackson-datatype-jsr310/2.21.4/jackson-datatype-jsr310-2.21.4.jar> (137 kB at 67 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/snakeyaml/snakeyaml-engine/3.0.1/snakeyaml-engine-3.0.1.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/tools/jackson/dataformat/jackson-dataformat-yaml/3.1.0/jackson-dataformat-yaml-3.1.0.jar> (62 kB at 31 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/jetbrains/kotlin/kotlin-reflect/2.1.21/kotlin-reflect-2.1.21.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/io/swagger/core/v3/swagger-models-jakarta/2.2.38/swagger-models-jakarta-2.2.38.jar> (140 kB at 67 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/2.1.21/kotlin-stdlib-2.1.21.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/license-checker/3.0.1/license-checker-3.0.1.jar> (4.2 MB at 1.8 MB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-runtime-plugin-transfertypes/25.1.7/hilla-runtime-plugin-transfertypes-25.1.7.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/snakeyaml/snakeyaml-engine/3.0.1/snakeyaml-engine-3.0.1.jar> (299 kB at 126 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/org/springframework/data/spring-data-commons/4.0.6/spring-data-commons-4.0.6.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/hilla-runtime-plugin-transfertypes/25.1.7/hilla-runtime-plugin-transfertypes-25.1.7.jar> (11 kB at 4.2 kB/s)
Downloading from central: <https://repo.maven.apache.org/maven2/com/vaadin/vaadin-prod-bundle/25.1.8/vaadin-prod-bundle-25.1.8.jar>
Downloaded from central: <https://repo.maven.apache.org/maven2/org/jetbrains/kotlin/kotlin-stdlib/2.1.21/kotlin-stdlib-2.1.21.jar> (1.7 MB at 454 kB/s)
Downloaded from central: <https://repo.maven.apache.org/maven2/org/springframework/data/spring-data-commons/4.0.6/spring-data-commons-4.0.6.jar> (1.7 MB at 385 kB/s)
Downloaded from central: <https://repo.maven.apache.org/maven2/org/jboss/forge/roaster/roaster-jdt/2.31.0.Final/roaster-jdt-2.31.0.Final.jar> (12 MB at 2.2 MB/s)
Downloaded from central: <https://repo.maven.apache.org/maven2/com/vaadin/vaadin-prod-bundle/25.1.8/vaadin-prod-bundle-25.1.8.jar> (9.4 MB at 1.5 MB/s)
Downloaded from central: <https://repo.maven.apache.org/maven2/org/jetbrains/kotlin/kotlin-reflect/2.1.21/kotlin-reflect-2.1.21.jar> (3.1 MB at 483 kB/s)
[INFO] ClassFinder initialized: 133 urls, 10318 classes scanned, 60 annotation types cached, 2746 subtype relationships cached, took 355ms
[INFO] Scanning classes to find frontend configurations and dependencies...
 [INFO] Visited 1289 classes. Took 550 ms.
[INFO] Checking if a production mode bundle build is needed
[INFO] Dependency @vaadin/router is missing from the bundle
[INFO] A production mode bundle build is needed
[INFO] Skipping `npm install` because the frontend packages are already installed in the folder '/Users/jenskreutzer/Antigravity_Projects/finance-app/node_modules' and the hash in the file '/Users/jenskreutzer/Antigravity_Projects/finance-app/node_modules/.vaadin/vaadin.json' is the same as in 'package.json'
[INFO] Copying frontend resources from jar files ...
[INFO] Visited 143 resources. Took 88 ms.
[INFO] Using globally installed Node.js version 24.14.1
[INFO] Running Vite ...
[INFO] Build frontend completed in 7548 ms.
[INFO]
[INFO] --- resources:3.3.1:testResources (default-testResources) @ finance-app ---
[INFO] Copying 1 resource from src/test/resources to target/test-classes
[INFO]
[INFO] --- compiler:3.14.1:testCompile (default-testCompile) @ finance-app ---
[INFO] Recompiling the module because of changed dependency.
[INFO] Compiling 10 source files with javac [debug parameters release 21] to target/test-classes
[INFO] /Users/jenskreutzer/Antigravity_Projects/finance-app/src/test/java/com/example/financeapp/UC001EnterTransactionTest.java: Einige Eingabedateien verwenden nicht geprüfte oder unsichere Vorgänge.
[INFO] /Users/jenskreutzer/Antigravity_Projects/finance-app/src/test/java/com/example/financeapp/UC001EnterTransactionTest.java: Wiederholen Sie die Kompilierung mit -Xlint:unchecked, um Details zu erhalten.
[INFO]
[INFO] --- surefire:3.5.6:test (default-test) @ finance-app ---
[INFO] Using auto detected provider org.apache.maven.surefire.junitplatform.JUnitPlatformProvider
[INFO]
[INFO] -------------------------------------------------------
[INFO]  T E S T S
[INFO] -------------------------------------------------------
[INFO] Running com.example.financeapp.UC001EnterTransactionTest
09:35:34.574 [main] INFO org.springframework.test.context.support.AnnotationConfigContextLoaderUtils -- Could not detect default configuration classes for test class [com.example.financeapp.UC001EnterTransactionTest]: UC001EnterTransactionTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
09:35:34.634 [main] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper -- Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC001EnterTransactionTest
09:35:34.675 [main] INFO org.springframework.test.context.support.AnnotationConfigContextLoaderUtils -- Could not detect default configuration classes for test class [com.example.financeapp.UC001EnterTransactionTest]: UC001EnterTransactionTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
09:35:34.676 [main] INFO org.springframework.test.context.support.AnnotationConfigContextLoaderUtils -- Could not detect default configuration classes for test class [com.vaadin.browserless.SpringBrowserlessTest]: SpringBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
09:35:34.676 [main] INFO org.springframework.test.context.support.AnnotationConfigContextLoaderUtils -- Could not detect default configuration classes for test class [com.vaadin.browserless.BrowserlessTest]: BrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
09:35:34.676 [main] INFO org.springframework.test.context.support.AnnotationConfigContextLoaderUtils -- Could not detect default configuration classes for test class [com.vaadin.browserless.BaseBrowserlessTest]: BaseBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
09:35:34.678 [main] INFO org.springframework.boot.test.context.SpringBootTestContextBootstrapper -- Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC001EnterTransactionTest
09:35:34.943 [main] WARN com.vaadin.browserless -- Tester 'com.vaadin.flow.component.charts.ChartTester' cannot be loaded because of missing class on classpath: com/vaadin/flow/component/charts/events/SeriesClickEvent
09:35:34.980 [main] INFO org.springframework.boot.devtools.restart.RestartApplicationListener -- Restart disabled due to context in which it is running

  .   ____          ____ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/

 :: Spring Boot ::                (v4.0.7)

2026-07-10T09:35:35.105+02:00  INFO 52569 --- [           main] c.e.f.UC001EnterTransactionTest          : Starting UC001EnterTransactionTest using Java 21.0.2 with PID 52569 (started by jenskreutzer in /Users/jenskreutzer/Antigravity_Projects/finance-app)
2026-07-10T09:35:35.105+02:00  INFO 52569 --- [           main] c.e.f.UC001EnterTransactionTest          : No active profile set, falling back to 1 default profile: "default"
2026-07-10T09:35:35.953+02:00  INFO 52569 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Starting...
2026-07-10T09:35:36.037+02:00  INFO 52569 --- [           main] com.zaxxer.hikari.pool.HikariPool        : HikariPool-1 - Added connection conn0: url=jdbc:h2:mem:financedb_test user=SA
2026-07-10T09:35:36.038+02:00  INFO 52569 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-1 - Start completed.
2026-07-10T09:35:36.058+02:00  INFO 52569 --- [           main] org.flywaydb.core.FlywayExecutor         : Database: jdbc:h2:mem:financedb_test (H2 2.4)
2026-07-10T09:35:36.062+02:00  WARN 52569 --- [           main] o.f.c.internal.database.base.Database    : Using H2 2.4.240 which is newer than the version Flyway has been verified with. The latest verified version of H2 is 2.3.232.
2026-07-10T09:35:36.073+02:00  INFO 52569 --- [           main] o.f.c.i.s.JdbcTableSchemaHistory         : Schema history table "PUBLIC"."flyway_schema_history" does not exist yet
2026-07-10T09:35:36.074+02:00  INFO 52569 --- [           main] o.f.core.internal.command.DbValidate     : Successfully validated 1 migration (execution time 00:00.005s)
2026-07-10T09:35:36.077+02:00  INFO 52569 --- [           main] o.f.c.i.s.JdbcTableSchemaHistory         : Creating Schema History table "PUBLIC"."flyway_schema_history" ...
2026-07-10T09:35:36.094+02:00  INFO 52569 --- [           main] o.f.core.internal.command.DbMigrate      : Current version of schema "PUBLIC": << Empty Schema >>
2026-07-10T09:35:36.100+02:00  INFO 52569 --- [           main] o.f.core.internal.command.DbMigrate      : Migrating schema "PUBLIC" to version "001 - create schema"
2026-07-10T09:35:36.115+02:00  INFO 52569 --- [           main] o.f.core.internal.command.DbMigrate      : Successfully applied 1 migration to schema "PUBLIC", now at version v001 (execution time 00:00.009s)
2026-07-10T09:35:36.485+02:00  INFO 52569 --- [           main] c.e.f.UC001EnterTransactionTest          : Started UC001EnterTransactionTest in 1.51 seconds (process running for 2.565)
2026-07-10T09:35:36.632+02:00  INFO 52569 --- [           main] org.jooq.Constants                       :

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@@@@  @@        @@@@@@@@@@
@@@@@@@@@@@@@@@@@@@@        @@@@@@@@@@
@@@@@@@@@@@@@@@@  @@  @@    @@@@@@@@@@
@@@@@@@@@@  @@@@  @@  @@    @@@@@@@@@@
@@@@@@@@@@        @@        @@@@@@@@@@
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@@@@@@@@@@        @@        @@@@@@@@@@
@@@@@@@@@@    @@  @@  @@@@  @@@@@@@@@@
@@@@@@@@@@    @@  @@  @@@@  @@@@@@@@@@
@@@@@@@@@@        @@  @  @  @@@@@@@@@@
@@@@@@@@@@        @@        @@@@@@@@@@
@@@@@@@@@@@@@@@@@@@@@@@  @@@@@@@@@@@@@
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@  Thank you for using jOOQ 3.19.35 (Build date: 2026-06-04T07:55Z)

2026-07-10T09:35:36.633+02:00  INFO 52569 --- [           main] org.jooq.Constants                       :

jOOQ tip of the day: Multi tenancy is best implemented in jOOQ using runtime schema and table mapping: <https://www.jooq.org/doc/latest/manual/sql-building/dsl-context/custom-settings/settings-render-mapping/>

2026-07-10T09:35:36.670+02:00  INFO 52569 --- [           main] o.j.i.D.logVersionSupport                : Version                  : Database version is supported by dialect SQLDialect.H2: 2.4.240 (2025-09-22)
🔄 Starte automatische Datenmigration aus der alten data.json...
✅ 15 Transaktionseinträge erfolgreich migriert!
Mockito is currently self-attaching to enable the inline-mock-maker. This will no longer work in future releases of the JDK. Please add Mockito as an agent to your build as described in Mockito's documentation: <https://javadoc.io/doc/org.mockito/mockito-core/latest/org.mockito/org/mockito/Mockito.html#0.3>
WARNING: A Java agent has been loaded dynamically (/Users/jenskreutzer/.m2/repository/net/bytebuddy/byte-buddy-agent/1.17.8/byte-buddy-agent-1.17.8.jar)
WARNING: If a serviceability tool is in use, please run with -XX:+EnableDynamicAgentLoading to hide this warning
WARNING: If a serviceability tool is not in use, please run with -Djdk.instrument.traceUsage for more information
WARNING: Dynamic loading of agents will be disallowed by default in a future release
Auto-discovered views: Routes(routes=MainView, errorRoutes=MockInternalSeverError, RouteAccessDeniedError, RouteNotFoundError, layouts=)
2026-07-10T09:35:38.496+02:00  INFO 52569 --- [           main] c.v.f.s.DefaultDeploymentConfiguration   : Vaadin is running in production mode.
DEBUG - MainView constructor called!
DEBUG - MainView constructor called!
DEBUG - MainView constructor called!
DEBUG - MainView constructor called!
DEBUG - MainView constructor called!
[ERROR] Tests run: 5, Failures: 0, Errors: 5, Skipped: 0, Time elapsed: 5.127 s <<< FAILURE! -- in com.example.financeapp.UC001EnterTransactionTest
[ERROR] com.example.financeapp.UC001EnterTransactionTest.cancel_deletion_does_not_delete -- Time elapsed: 2.585 s <<< ERROR!
java.util.NoSuchElementException:
/: No visible H2 in MockedUI[@theme='dark'] matching H2 and text='Jens's Finanzverwaltung': []. Component tree:
└── MockedUI[@theme='dark']
    └── MainView[@style='width:100%;height:100%;align-items:center', @theme='padding spacing']
        ├── H1[text='Couples Finance Tracker', @class='page-title']
        ├── Tabs[selected='1', @style='width:100%']
        │   ├── Tab[selected='false', Tab{Dashboard}]
        │   ├── Tab[selected='true', Tab{Jens}]
        │   ├── Tab[selected='false', Tab{Annika}]
        │   ├── Tab[selected='false', Tab{Prognose}]
        │   └── Tab[selected='false', Tab{Einstellungen}]
        └── Div[@style='width:100%;height:100%;overflow:auto']
            └── HorizontalLayout[@style='width:100%;height:100%', @theme='spacing']
                ├── VerticalLayout[@style='width:40%', @theme='padding spacing']
                │   ├── H3[text='Historische Einträge']
                │   └── Grid[allRowsVisible='true', size='2', pageSize='50', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@6fa84885', @multi-sort-priority='prepend', @style='width:100%']
                │       ├── Column[path='col0']
                │       ├── Column[path='col1']
                │       └── Column[]
                └── VerticalLayout[@class='glass-panel', @style='width:60%', @theme='padding spacing']
                    ├── H3[text='Werte eintragen / bearbeiten']
                    ├── DatePicker[label='Datum', value='2026-07-10', manualValidation='true']
                    ├── VerticalLayout[@style='width:100%', @theme='spacing']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Sparkasse', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='500.00', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Sparkasse', @style='width:150px']
                    │   │   └── ComboBox[value='Girokonto', size='8', selectedItem='{"key":"1","label":"Girokonto","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@39506f12', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Binance', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Zuletzt: 637,06 € (Krypto)', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Binance', @style='width:150px']
                    │   │   └── ComboBox[value='Krypto', size='8', selectedItem='{"key":"1","label":"Krypto","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@74baaba6', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Bitpanda', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Zuletzt: 48.000,16 € (Krypto)', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Bitpanda', @style='width:150px']
                    │   │   └── ComboBox[value='Krypto', size='8', selectedItem='{"key":"1","label":"Krypto","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@56e4f071', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Coinbase', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Zuletzt: 106,53 € (Krypto)', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Coinbase', @style='width:150px']
                    │   │   └── ComboBox[value='Krypto', size='8', selectedItem='{"key":"1","label":"Krypto","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@3fc0f3c2', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='ING-Diba', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='ING-Diba', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@5d233d7', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Scalable Capital', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Zuletzt: 7.863,45 € (Tagesgeld)', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Scalable Capital', @style='width:150px']
                    │   │   └── ComboBox[value='Tagesgeld', size='8', selectedItem='{"key":"1","label":"Tagesgeld","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@24945f50', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Stihl', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Stihl', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@550244cb', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Trade Republic', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Zuletzt: 144.974,10 € (ETF)', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trade Republic', @style='width:150px']
                    │   │   └── ComboBox[value='ETF', size='8', selectedItem='{"key":"1","label":"ETF","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@6de4e34a', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Tresor', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Zuletzt: 1.190,00 € (Silber)', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Tresor', @style='width:150px']
                    │   │   └── ComboBox[value='Silber', size='8', selectedItem='{"key":"1","label":"Silber","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@79bc29aa', @style='width:150px']
                    │   └── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │       ├── Span[text='Trust', @style='width:180px;font-weight:bold']
                    │       ├── Span[text='Zuletzt: 612,04 € (Krypto)', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │       ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trust', @style='width:150px']
                    │       └── ComboBox[value='Krypto', size='8', selectedItem='{"key":"1","label":"Krypto","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@7f549712', @style='width:150px']
                    └── Button[caption='Speichern', @theme='primary']
                        └── Text[text='Speichern']

 at com.vaadin.browserless.ComponentQuery.find(ComponentQuery.java:619)
 at com.vaadin.browserless.ComponentQuery.single(ComponentQuery.java:475)
 at com.example.financeapp.UC001EnterTransactionTest.cancel_deletion_does_not_delete(UC001EnterTransactionTest.java:168)

[ERROR] com.example.financeapp.UC001EnterTransactionTest.registration_fails_when_fields_are_empty -- Time elapsed: 0.053 s <<< ERROR!
java.util.NoSuchElementException:
/: No visible H2 in MockedUI[@theme='dark'] matching H2 and text='Annika's Finanzverwaltung': []. Component tree:
└── MockedUI[@theme='dark']
    └── MainView[@style='width:100%;height:100%;align-items:center', @theme='padding spacing']
        ├── H1[text='Couples Finance Tracker', @class='page-title']
        ├── Tabs[selected='2', @style='width:100%']
        │   ├── Tab[selected='false', Tab{Dashboard}]
        │   ├── Tab[selected='false', Tab{Jens}]
        │   ├── Tab[selected='true', Tab{Annika}]
        │   ├── Tab[selected='false', Tab{Prognose}]
        │   └── Tab[selected='false', Tab{Einstellungen}]
        └── Div[@style='width:100%;height:100%;overflow:auto']
            └── HorizontalLayout[@style='width:100%;height:100%', @theme='spacing']
                ├── VerticalLayout[@style='width:40%', @theme='padding spacing']
                │   ├── H3[text='Historische Einträge']
                │   └── Grid[allRowsVisible='true', size='0', pageSize='50', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@4c48ccc4', @multi-sort-priority='prepend', @style='width:100%']
                │       ├── Column[path='col0']
                │       ├── Column[path='col1']
                │       └── Column[]
                └── VerticalLayout[@class='glass-panel', @style='width:60%', @theme='padding spacing']
                    ├── H3[text='Werte eintragen / bearbeiten']
                    ├── DatePicker[label='Datum', value='2026-07-10', manualValidation='true']
                    ├── VerticalLayout[@style='width:100%', @theme='spacing']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Binance', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Binance', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@4c32428a', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Bitpanda', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Bitpanda', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@4812c244', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Coinbase', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Coinbase', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@231e5af', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='ING-Diba', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='ING-Diba', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@89537c1', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Scalable Capital', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Scalable Capital', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@56ed024b', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Sparkasse', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Sparkasse', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@8b3ea30', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Stihl', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Stihl', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@5a26a14', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Trade Republic', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trade Republic', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@24287e5e', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Tresor', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Tresor', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@1ae9cfca', @style='width:150px']
                    │   └── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │       ├── Span[text='Trust', @style='width:180px;font-weight:bold']
                    │       ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │       ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trust', @style='width:150px']
                    │       └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@1b786da0', @style='width:150px']
                    └── Button[caption='Speichern', @theme='primary']
                        └── Text[text='Speichern']

 at com.vaadin.browserless.ComponentQuery.find(ComponentQuery.java:619)
 at com.vaadin.browserless.ComponentQuery.single(ComponentQuery.java:475)
 at com.example.financeapp.UC001EnterTransactionTest.registration_fails_when_fields_are_empty(UC001EnterTransactionTest.java:108)

[ERROR] com.example.financeapp.UC001EnterTransactionTest.main_success_scenario_enter_transaction -- Time elapsed: 0.054 s <<< ERROR!
java.util.NoSuchElementException:
/: No visible H2 in MockedUI[@theme='dark'] matching H2 and text='Jens's Finanzverwaltung': []. Component tree:
└── MockedUI[@theme='dark']
    └── MainView[@style='width:100%;height:100%;align-items:center', @theme='padding spacing']
        ├── H1[text='Couples Finance Tracker', @class='page-title']
        ├── Tabs[selected='1', @style='width:100%']
        │   ├── Tab[selected='false', Tab{Dashboard}]
        │   ├── Tab[selected='true', Tab{Jens}]
        │   ├── Tab[selected='false', Tab{Annika}]
        │   ├── Tab[selected='false', Tab{Prognose}]
        │   └── Tab[selected='false', Tab{Einstellungen}]
        └── Div[@style='width:100%;height:100%;overflow:auto']
            └── HorizontalLayout[@style='width:100%;height:100%', @theme='spacing']
                ├── VerticalLayout[@style='width:40%', @theme='padding spacing']
                │   ├── H3[text='Historische Einträge']
                │   └── Grid[allRowsVisible='true', size='0', pageSize='50', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@611d6114', @multi-sort-priority='prepend', @style='width:100%']
                │       ├── Column[path='col0']
                │       ├── Column[path='col1']
                │       └── Column[]
                └── VerticalLayout[@class='glass-panel', @style='width:60%', @theme='padding spacing']
                    ├── H3[text='Werte eintragen / bearbeiten']
                    ├── DatePicker[label='Datum', value='2026-07-10', manualValidation='true']
                    ├── VerticalLayout[@style='width:100%', @theme='spacing']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Binance', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Binance', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@52318fa0', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Bitpanda', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Bitpanda', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@5d354d8', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Coinbase', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Coinbase', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@1db9572', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='ING-Diba', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='ING-Diba', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@bc4419c', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Scalable Capital', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Scalable Capital', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@55d683cb', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Sparkasse', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Sparkasse', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@4a3c683d', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Stihl', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Stihl', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@1791040d', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Trade Republic', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trade Republic', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@3e706fc2', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Tresor', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Tresor', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@5f597b1c', @style='width:150px']
                    │   └── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │       ├── Span[text='Trust', @style='width:180px;font-weight:bold']
                    │       ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │       ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trust', @style='width:150px']
                    │       └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@760d9d4a', @style='width:150px']
                    └── Button[caption='Speichern', @theme='primary']
                        └── Text[text='Speichern']

 at com.vaadin.browserless.ComponentQuery.find(ComponentQuery.java:619)
 at com.vaadin.browserless.ComponentQuery.single(ComponentQuery.java:475)
 at com.example.financeapp.UC001EnterTransactionTest.main_success_scenario_enter_transaction(UC001EnterTransactionTest.java:65)

[ERROR] com.example.financeapp.UC001EnterTransactionTest.delete_transaction_confirms_and_deletes -- Time elapsed: 0.073 s <<< ERROR!
java.util.NoSuchElementException:
/: No visible H2 in MockedUI[@theme='dark'] matching H2 and text='Jens's Finanzverwaltung': []. Component tree:
└── MockedUI[@theme='dark']
    └── MainView[@style='width:100%;height:100%;align-items:center', @theme='padding spacing']
        ├── H1[text='Couples Finance Tracker', @class='page-title']
        ├── Tabs[selected='1', @style='width:100%']
        │   ├── Tab[selected='false', Tab{Dashboard}]
        │   ├── Tab[selected='true', Tab{Jens}]
        │   ├── Tab[selected='false', Tab{Annika}]
        │   ├── Tab[selected='false', Tab{Prognose}]
        │   └── Tab[selected='false', Tab{Einstellungen}]
        └── Div[@style='width:100%;height:100%;overflow:auto']
            └── HorizontalLayout[@style='width:100%;height:100%', @theme='spacing']
                ├── VerticalLayout[@style='width:40%', @theme='padding spacing']
                │   ├── H3[text='Historische Einträge']
                │   └── Grid[allRowsVisible='true', size='1', pageSize='50', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@7612a64b', @multi-sort-priority='prepend', @style='width:100%']
                │       ├── Column[path='col0']
                │       ├── Column[path='col1']
                │       └── Column[]
                └── VerticalLayout[@class='glass-panel', @style='width:60%', @theme='padding spacing']
                    ├── H3[text='Werte eintragen / bearbeiten']
                    ├── DatePicker[label='Datum', value='2026-07-10', manualValidation='true']
                    ├── VerticalLayout[@style='width:100%', @theme='spacing']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Sparkasse', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='500.00', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Sparkasse', @style='width:150px']
                    │   │   └── ComboBox[value='Girokonto', size='8', selectedItem='{"key":"1","label":"Girokonto","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@2db6d66d', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Binance', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Binance', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@26fd2a82', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Bitpanda', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Bitpanda', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@23f31375', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Coinbase', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Coinbase', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@40b0c9be', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='ING-Diba', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='ING-Diba', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@5bae7a7b', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Scalable Capital', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Scalable Capital', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@1448e2b6', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Stihl', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Stihl', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@2f9b33f1', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Trade Republic', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trade Republic', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@69a0e285', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Tresor', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Tresor', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@41f53068', @style='width:150px']
                    │   └── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │       ├── Span[text='Trust', @style='width:180px;font-weight:bold']
                    │       ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │       ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trust', @style='width:150px']
                    │       └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@12fb37b2', @style='width:150px']
                    └── Button[caption='Speichern', @theme='primary']
                        └── Text[text='Speichern']

 at com.vaadin.browserless.ComponentQuery.find(ComponentQuery.java:619)
 at com.vaadin.browserless.ComponentQuery.single(ComponentQuery.java:475)
 at com.example.financeapp.UC001EnterTransactionTest.delete_transaction_confirms_and_deletes(UC001EnterTransactionTest.java:133)

[ERROR] com.example.financeapp.UC001EnterTransactionTest.currency_formatting_follows_german_locale -- Time elapsed: 0.057 s <<< ERROR!
java.util.NoSuchElementException:
/: No visible H2 in MockedUI[@theme='dark'] matching H2 and text='Jens's Finanzverwaltung': []. Component tree:
└── MockedUI[@theme='dark']
    └── MainView[@style='width:100%;height:100%;align-items:center', @theme='padding spacing']
        ├── H1[text='Couples Finance Tracker', @class='page-title']
        ├── Tabs[selected='1', @style='width:100%']
        │   ├── Tab[selected='false', Tab{Dashboard}]
        │   ├── Tab[selected='true', Tab{Jens}]
        │   ├── Tab[selected='false', Tab{Annika}]
        │   ├── Tab[selected='false', Tab{Prognose}]
        │   └── Tab[selected='false', Tab{Einstellungen}]
        └── Div[@style='width:100%;height:100%;overflow:auto']
            └── HorizontalLayout[@style='width:100%;height:100%', @theme='spacing']
                ├── VerticalLayout[@style='width:40%', @theme='padding spacing']
                │   ├── H3[text='Historische Einträge']
                │   └── Grid[allRowsVisible='true', size='1', pageSize='50', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@b420d9e', @multi-sort-priority='prepend', @style='width:100%']
                │       ├── Column[path='col0']
                │       ├── Column[path='col1']
                │       └── Column[]
                └── VerticalLayout[@class='glass-panel', @style='width:60%', @theme='padding spacing']
                    ├── H3[text='Werte eintragen / bearbeiten']
                    ├── DatePicker[label='Datum', value='2026-07-10', manualValidation='true']
                    ├── VerticalLayout[@style='width:100%', @theme='spacing']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Binance', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='1234567.89', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Binance', @style='width:150px']
                    │   │   └── ComboBox[value='Krypto', size='8', selectedItem='{"key":"1","label":"Krypto","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@32e2d117', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Bitpanda', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Bitpanda', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@4bdb5933', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Coinbase', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Coinbase', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@75931d62', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='ING-Diba', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='ING-Diba', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@6e26d0b3', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Scalable Capital', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Scalable Capital', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@4ae8de09', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Sparkasse', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Sparkasse', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@5b6b14a6', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Stihl', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Stihl', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@367e6aa4', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Trade Republic', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trade Republic', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@6e858790', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Tresor', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Tresor', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@621104df', @style='width:150px']
                    │   └── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │       ├── Span[text='Trust', @style='width:180px;font-weight:bold']
                    │       ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │       ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trust', @style='width:150px']
                    │       └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@61dcf21f', @style='width:150px']
                    └── Button[caption='Speichern', @theme='primary']
                        └── Text[text='Speichern']

 at com.vaadin.browserless.ComponentQuery.find(ComponentQuery.java:619)
 at com.vaadin.browserless.ComponentQuery.single(ComponentQuery.java:475)
 at com.example.financeapp.UC001EnterTransactionTest.currency_formatting_follows_german_locale(UC001EnterTransactionTest.java:199)

[INFO] Running com.example.financeapp.UC003ManageCategoriesTest
2026-07-10T09:35:39.609+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC003ManageCategoriesTest]: UC003ManageCategoriesTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.610+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC003ManageCategoriesTest
2026-07-10T09:35:39.611+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC003ManageCategoriesTest]: UC003ManageCategoriesTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.611+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.SpringBrowserlessTest]: SpringBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.612+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BrowserlessTest]: BrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.612+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BaseBrowserlessTest]: BaseBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.613+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC003ManageCategoriesTest
[INFO] Running Main Success Scenario
2026-07-10T09:35:39.614+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC003ManageCategoriesTest$MainSuccess]: MainSuccess does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.615+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC003ManageCategoriesTest$MainSuccess
2026-07-10T09:35:39.616+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC003ManageCategoriesTest$MainSuccess]: MainSuccess does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.616+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC003ManageCategoriesTest]: UC003ManageCategoriesTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.616+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.SpringBrowserlessTest]: SpringBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.616+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BrowserlessTest]: BrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.616+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BaseBrowserlessTest]: BaseBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.616+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC003ManageCategoriesTest$MainSuccess
DEBUG - MainView constructor called!
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.133 s -- in Main Success Scenario
[INFO] Running Alternative Flows
2026-07-10T09:35:39.749+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC003ManageCategoriesTest$AlternativeFlows]: AlternativeFlows does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.750+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC003ManageCategoriesTest$AlternativeFlows
2026-07-10T09:35:39.751+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC003ManageCategoriesTest$AlternativeFlows]: AlternativeFlows does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.751+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC003ManageCategoriesTest]: UC003ManageCategoriesTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.751+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.SpringBrowserlessTest]: SpringBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.751+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BrowserlessTest]: BrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.752+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BaseBrowserlessTest]: BaseBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.752+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC003ManageCategoriesTest$AlternativeFlows
DEBUG - MainView constructor called!
DEBUG - MainView constructor called!
DEBUG - MainView constructor called!
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.143 s -- in Alternative Flows
[INFO] Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.284 s -- in com.example.financeapp.UC003ManageCategoriesTest
[INFO] Running com.example.financeapp.UC002ManageInstitutesTest
2026-07-10T09:35:39.895+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC002ManageInstitutesTest]: UC002ManageInstitutesTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.896+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC002ManageInstitutesTest
2026-07-10T09:35:39.896+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC002ManageInstitutesTest]: UC002ManageInstitutesTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.896+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.SpringBrowserlessTest]: SpringBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.896+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BrowserlessTest]: BrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.896+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BaseBrowserlessTest]: BaseBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.897+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC002ManageInstitutesTest
[INFO] Running Alternative Flows
2026-07-10T09:35:39.899+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC002ManageInstitutesTest$AlternativeFlows]: AlternativeFlows does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.899+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC002ManageInstitutesTest$AlternativeFlows
2026-07-10T09:35:39.899+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC002ManageInstitutesTest$AlternativeFlows]: AlternativeFlows does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.900+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC002ManageInstitutesTest]: UC002ManageInstitutesTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.900+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.SpringBrowserlessTest]: SpringBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.900+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BrowserlessTest]: BrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.900+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BaseBrowserlessTest]: BaseBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.900+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC002ManageInstitutesTest$AlternativeFlows
DEBUG - MainView constructor called!
DEBUG - MainView constructor called!
DEBUG - MainView constructor called!
[INFO] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.092 s -- in Alternative Flows
[INFO] Running Main Success Scenario
2026-07-10T09:35:39.992+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC002ManageInstitutesTest$MainSuccess]: MainSuccess does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.994+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC002ManageInstitutesTest$MainSuccess
2026-07-10T09:35:39.994+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC002ManageInstitutesTest$MainSuccess]: MainSuccess does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.995+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC002ManageInstitutesTest]: UC002ManageInstitutesTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.995+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.SpringBrowserlessTest]: SpringBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.995+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BrowserlessTest]: BrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.995+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BaseBrowserlessTest]: BaseBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:39.995+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC002ManageInstitutesTest$MainSuccess
DEBUG - MainView constructor called!
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.034 s -- in Main Success Scenario
[INFO] Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.131 s -- in com.example.financeapp.UC002ManageInstitutesTest
[INFO] Running com.example.financeapp.UC005ForecastWealthTest
2026-07-10T09:35:40.028+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC005ForecastWealthTest]: UC005ForecastWealthTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.028+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC005ForecastWealthTest
2026-07-10T09:35:40.029+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC005ForecastWealthTest]: UC005ForecastWealthTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.029+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.SpringBrowserlessTest]: SpringBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.029+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BrowserlessTest]: BrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.029+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BaseBrowserlessTest]: BaseBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.030+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC005ForecastWealthTest
[INFO] Running Alternative Flows
2026-07-10T09:35:40.031+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC005ForecastWealthTest$AlternativeFlows]: AlternativeFlows does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.032+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC005ForecastWealthTest$AlternativeFlows
2026-07-10T09:35:40.032+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC005ForecastWealthTest$AlternativeFlows]: AlternativeFlows does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.032+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC005ForecastWealthTest]: UC005ForecastWealthTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.032+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.SpringBrowserlessTest]: SpringBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.032+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BrowserlessTest]: BrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.032+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BaseBrowserlessTest]: BaseBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.033+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC005ForecastWealthTest$AlternativeFlows
DEBUG - MainView constructor called!
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.031 s -- in Alternative Flows
[INFO] Running Main Success Scenario
2026-07-10T09:35:40.063+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC005ForecastWealthTest$MainSuccess]: MainSuccess does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.064+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC005ForecastWealthTest$MainSuccess
2026-07-10T09:35:40.065+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC005ForecastWealthTest$MainSuccess]: MainSuccess does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.065+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC005ForecastWealthTest]: UC005ForecastWealthTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.065+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.SpringBrowserlessTest]: SpringBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.066+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BrowserlessTest]: BrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.066+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BaseBrowserlessTest]: BaseBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.066+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC005ForecastWealthTest$MainSuccess
DEBUG - MainView constructor called!
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.035 s -- in Main Success Scenario
[INFO] Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.072 s -- in com.example.financeapp.UC005ForecastWealthTest
[INFO] Running com.example.financeapp.UC004ViewJointDashboardTest
2026-07-10T09:35:40.099+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC004ViewJointDashboardTest]: UC004ViewJointDashboardTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.100+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC004ViewJointDashboardTest
2026-07-10T09:35:40.100+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC004ViewJointDashboardTest]: UC004ViewJointDashboardTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.101+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.SpringBrowserlessTest]: SpringBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.101+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BrowserlessTest]: BrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.101+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BaseBrowserlessTest]: BaseBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.101+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC004ViewJointDashboardTest
[INFO] Running Main Success Scenario
2026-07-10T09:35:40.102+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC004ViewJointDashboardTest$MainSuccess]: MainSuccess does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.103+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC004ViewJointDashboardTest$MainSuccess
2026-07-10T09:35:40.103+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC004ViewJointDashboardTest$MainSuccess]: MainSuccess does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.103+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC004ViewJointDashboardTest]: UC004ViewJointDashboardTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.103+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.SpringBrowserlessTest]: SpringBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.103+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BrowserlessTest]: BrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.103+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BaseBrowserlessTest]: BaseBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.104+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC004ViewJointDashboardTest$MainSuccess
DEBUG - MainView constructor called!
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.032 s -- in Main Success Scenario
[INFO] Running Alternative Flows
2026-07-10T09:35:40.135+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC004ViewJointDashboardTest$AlternativeFlows]: AlternativeFlows does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.136+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC004ViewJointDashboardTest$AlternativeFlows
2026-07-10T09:35:40.137+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC004ViewJointDashboardTest$AlternativeFlows]: AlternativeFlows does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.137+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.example.financeapp.UC004ViewJointDashboardTest]: UC004ViewJointDashboardTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.137+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.SpringBrowserlessTest]: SpringBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.137+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BrowserlessTest]: BrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.137+02:00  INFO 52569 --- [           main] t.c.s.AnnotationConfigContextLoaderUtils : Could not detect default configuration classes for test class [com.vaadin.browserless.BaseBrowserlessTest]: BaseBrowserlessTest does not declare any static, non-private, non-final, nested classes annotated with @Configuration.
2026-07-10T09:35:40.137+02:00  INFO 52569 --- [           main] .b.t.c.SpringBootTestContextBootstrapper : Found @SpringBootConfiguration com.example.financeapp.Application for test class com.example.financeapp.UC004ViewJointDashboardTest$AlternativeFlows
DEBUG - MainView constructor called!
[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.023 s -- in Alternative Flows
[INFO] Tests run: 0, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 0.061 s -- in com.example.financeapp.UC004ViewJointDashboardTest
[INFO]
[INFO] Results:
[INFO]
[ERROR] Errors:
[ERROR]   UC001EnterTransactionTest.cancel_deletion_does_not_delete:168 » NoSuchElement /: No visible H2 in MockedUI[@theme='dark'] matching H2 and text='Jens's Finanzverwaltung': []. Component tree:
└── MockedUI[@theme='dark']
    └── MainView[@style='width:100%;height:100%;align-items:center', @theme='padding spacing']
        ├── H1[text='Couples Finance Tracker', @class='page-title']
        ├── Tabs[selected='1', @style='width:100%']
        │   ├── Tab[selected='false', Tab{Dashboard}]
        │   ├── Tab[selected='true', Tab{Jens}]
        │   ├── Tab[selected='false', Tab{Annika}]
        │   ├── Tab[selected='false', Tab{Prognose}]
        │   └── Tab[selected='false', Tab{Einstellungen}]
        └── Div[@style='width:100%;height:100%;overflow:auto']
            └── HorizontalLayout[@style='width:100%;height:100%', @theme='spacing']
                ├── VerticalLayout[@style='width:40%', @theme='padding spacing']
                │   ├── H3[text='Historische Einträge']
                │   └── Grid[allRowsVisible='true', size='2', pageSize='50', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@6fa84885', @multi-sort-priority='prepend', @style='width:100%']
                │       ├── Column[path='col0']
                │       ├── Column[path='col1']
                │       └── Column[]
                └── VerticalLayout[@class='glass-panel', @style='width:60%', @theme='padding spacing']
                    ├── H3[text='Werte eintragen / bearbeiten']
                    ├── DatePicker[label='Datum', value='2026-07-10', manualValidation='true']
                    ├── VerticalLayout[@style='width:100%', @theme='spacing']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Sparkasse', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='500.00', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Sparkasse', @style='width:150px']
                    │   │   └── ComboBox[value='Girokonto', size='8', selectedItem='{"key":"1","label":"Girokonto","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@39506f12', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Binance', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Zuletzt: 637,06 € (Krypto)', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Binance', @style='width:150px']
                    │   │   └── ComboBox[value='Krypto', size='8', selectedItem='{"key":"1","label":"Krypto","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@74baaba6', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Bitpanda', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Zuletzt: 48.000,16 € (Krypto)', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Bitpanda', @style='width:150px']
                    │   │   └── ComboBox[value='Krypto', size='8', selectedItem='{"key":"1","label":"Krypto","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@56e4f071', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Coinbase', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Zuletzt: 106,53 € (Krypto)', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Coinbase', @style='width:150px']
                    │   │   └── ComboBox[value='Krypto', size='8', selectedItem='{"key":"1","label":"Krypto","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@3fc0f3c2', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='ING-Diba', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='ING-Diba', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@5d233d7', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Scalable Capital', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Zuletzt: 7.863,45 € (Tagesgeld)', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Scalable Capital', @style='width:150px']
                    │   │   └── ComboBox[value='Tagesgeld', size='8', selectedItem='{"key":"1","label":"Tagesgeld","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@24945f50', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Stihl', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Stihl', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@550244cb', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Trade Republic', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Zuletzt: 144.974,10 € (ETF)', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trade Republic', @style='width:150px']
                    │   │   └── ComboBox[value='ETF', size='8', selectedItem='{"key":"1","label":"ETF","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@6de4e34a', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Tresor', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Zuletzt: 1.190,00 € (Silber)', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Tresor', @style='width:150px']
                    │   │   └── ComboBox[value='Silber', size='8', selectedItem='{"key":"1","label":"Silber","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@79bc29aa', @style='width:150px']
                    │   └── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │       ├── Span[text='Trust', @style='width:180px;font-weight:bold']
                    │       ├── Span[text='Zuletzt: 612,04 € (Krypto)', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │       ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trust', @style='width:150px']
                    │       └── ComboBox[value='Krypto', size='8', selectedItem='{"key":"1","label":"Krypto","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@7f549712', @style='width:150px']
                    └── Button[caption='Speichern', @theme='primary']
                        └── Text[text='Speichern']

[ERROR]   UC001EnterTransactionTest.currency_formatting_follows_german_locale:199 » NoSuchElement /: No visible H2 in MockedUI[@theme='dark'] matching H2 and text='Jens's Finanzverwaltung': []. Component tree:
└── MockedUI[@theme='dark']
    └── MainView[@style='width:100%;height:100%;align-items:center', @theme='padding spacing']
        ├── H1[text='Couples Finance Tracker', @class='page-title']
        ├── Tabs[selected='1', @style='width:100%']
        │   ├── Tab[selected='false', Tab{Dashboard}]
        │   ├── Tab[selected='true', Tab{Jens}]
        │   ├── Tab[selected='false', Tab{Annika}]
        │   ├── Tab[selected='false', Tab{Prognose}]
        │   └── Tab[selected='false', Tab{Einstellungen}]
        └── Div[@style='width:100%;height:100%;overflow:auto']
            └── HorizontalLayout[@style='width:100%;height:100%', @theme='spacing']
                ├── VerticalLayout[@style='width:40%', @theme='padding spacing']
                │   ├── H3[text='Historische Einträge']
                │   └── Grid[allRowsVisible='true', size='1', pageSize='50', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@b420d9e', @multi-sort-priority='prepend', @style='width:100%']
                │       ├── Column[path='col0']
                │       ├── Column[path='col1']
                │       └── Column[]
                └── VerticalLayout[@class='glass-panel', @style='width:60%', @theme='padding spacing']
                    ├── H3[text='Werte eintragen / bearbeiten']
                    ├── DatePicker[label='Datum', value='2026-07-10', manualValidation='true']
                    ├── VerticalLayout[@style='width:100%', @theme='spacing']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Binance', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='1234567.89', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Binance', @style='width:150px']
                    │   │   └── ComboBox[value='Krypto', size='8', selectedItem='{"key":"1","label":"Krypto","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@32e2d117', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Bitpanda', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Bitpanda', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@4bdb5933', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Coinbase', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Coinbase', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@75931d62', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='ING-Diba', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='ING-Diba', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@6e26d0b3', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Scalable Capital', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Scalable Capital', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@4ae8de09', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Sparkasse', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Sparkasse', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@5b6b14a6', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Stihl', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Stihl', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@367e6aa4', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Trade Republic', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trade Republic', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@6e858790', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Tresor', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Tresor', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@621104df', @style='width:150px']
                    │   └── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │       ├── Span[text='Trust', @style='width:180px;font-weight:bold']
                    │       ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │       ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trust', @style='width:150px']
                    │       └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@61dcf21f', @style='width:150px']
                    └── Button[caption='Speichern', @theme='primary']
                        └── Text[text='Speichern']

[ERROR]   UC001EnterTransactionTest.delete_transaction_confirms_and_deletes:133 » NoSuchElement /: No visible H2 in MockedUI[@theme='dark'] matching H2 and text='Jens's Finanzverwaltung': []. Component tree:
└── MockedUI[@theme='dark']
    └── MainView[@style='width:100%;height:100%;align-items:center', @theme='padding spacing']
        ├── H1[text='Couples Finance Tracker', @class='page-title']
        ├── Tabs[selected='1', @style='width:100%']
        │   ├── Tab[selected='false', Tab{Dashboard}]
        │   ├── Tab[selected='true', Tab{Jens}]
        │   ├── Tab[selected='false', Tab{Annika}]
        │   ├── Tab[selected='false', Tab{Prognose}]
        │   └── Tab[selected='false', Tab{Einstellungen}]
        └── Div[@style='width:100%;height:100%;overflow:auto']
            └── HorizontalLayout[@style='width:100%;height:100%', @theme='spacing']
                ├── VerticalLayout[@style='width:40%', @theme='padding spacing']
                │   ├── H3[text='Historische Einträge']
                │   └── Grid[allRowsVisible='true', size='1', pageSize='50', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@7612a64b', @multi-sort-priority='prepend', @style='width:100%']
                │       ├── Column[path='col0']
                │       ├── Column[path='col1']
                │       └── Column[]
                └── VerticalLayout[@class='glass-panel', @style='width:60%', @theme='padding spacing']
                    ├── H3[text='Werte eintragen / bearbeiten']
                    ├── DatePicker[label='Datum', value='2026-07-10', manualValidation='true']
                    ├── VerticalLayout[@style='width:100%', @theme='spacing']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Sparkasse', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='500.00', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Sparkasse', @style='width:150px']
                    │   │   └── ComboBox[value='Girokonto', size='8', selectedItem='{"key":"1","label":"Girokonto","className":""}', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@2db6d66d', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Binance', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Binance', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@26fd2a82', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Bitpanda', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Bitpanda', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@23f31375', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Coinbase', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Coinbase', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@40b0c9be', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='ING-Diba', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='ING-Diba', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@5bae7a7b', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Scalable Capital', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Scalable Capital', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@1448e2b6', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Stihl', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Stihl', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@2f9b33f1', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Trade Republic', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trade Republic', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@69a0e285', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Tresor', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Tresor', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@41f53068', @style='width:150px']
                    │   └── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │       ├── Span[text='Trust', @style='width:180px;font-weight:bold']
                    │       ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │       ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trust', @style='width:150px']
                    │       └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@12fb37b2', @style='width:150px']
                    └── Button[caption='Speichern', @theme='primary']
                        └── Text[text='Speichern']

[ERROR]   UC001EnterTransactionTest.main_success_scenario_enter_transaction:65 » NoSuchElement /: No visible H2 in MockedUI[@theme='dark'] matching H2 and text='Jens's Finanzverwaltung': []. Component tree:
└── MockedUI[@theme='dark']
    └── MainView[@style='width:100%;height:100%;align-items:center', @theme='padding spacing']
        ├── H1[text='Couples Finance Tracker', @class='page-title']
        ├── Tabs[selected='1', @style='width:100%']
        │   ├── Tab[selected='false', Tab{Dashboard}]
        │   ├── Tab[selected='true', Tab{Jens}]
        │   ├── Tab[selected='false', Tab{Annika}]
        │   ├── Tab[selected='false', Tab{Prognose}]
        │   └── Tab[selected='false', Tab{Einstellungen}]
        └── Div[@style='width:100%;height:100%;overflow:auto']
            └── HorizontalLayout[@style='width:100%;height:100%', @theme='spacing']
                ├── VerticalLayout[@style='width:40%', @theme='padding spacing']
                │   ├── H3[text='Historische Einträge']
                │   └── Grid[allRowsVisible='true', size='0', pageSize='50', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@611d6114', @multi-sort-priority='prepend', @style='width:100%']
                │       ├── Column[path='col0']
                │       ├── Column[path='col1']
                │       └── Column[]
                └── VerticalLayout[@class='glass-panel', @style='width:60%', @theme='padding spacing']
                    ├── H3[text='Werte eintragen / bearbeiten']
                    ├── DatePicker[label='Datum', value='2026-07-10', manualValidation='true']
                    ├── VerticalLayout[@style='width:100%', @theme='spacing']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Binance', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Binance', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@52318fa0', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Bitpanda', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Bitpanda', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@5d354d8', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Coinbase', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Coinbase', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@1db9572', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='ING-Diba', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='ING-Diba', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@bc4419c', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Scalable Capital', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Scalable Capital', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@55d683cb', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Sparkasse', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Sparkasse', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@4a3c683d', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Stihl', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Stihl', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@1791040d', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Trade Republic', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trade Republic', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@3e706fc2', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Tresor', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Tresor', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@5f597b1c', @style='width:150px']
                    │   └── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │       ├── Span[text='Trust', @style='width:180px;font-weight:bold']
                    │       ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │       ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trust', @style='width:150px']
                    │       └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@760d9d4a', @style='width:150px']
                    └── Button[caption='Speichern', @theme='primary']
                        └── Text[text='Speichern']

[ERROR]   UC001EnterTransactionTest.registration_fails_when_fields_are_empty:108 » NoSuchElement /: No visible H2 in MockedUI[@theme='dark'] matching H2 and text='Annika's Finanzverwaltung': []. Component tree:
└── MockedUI[@theme='dark']
    └── MainView[@style='width:100%;height:100%;align-items:center', @theme='padding spacing']
        ├── H1[text='Couples Finance Tracker', @class='page-title']
        ├── Tabs[selected='2', @style='width:100%']
        │   ├── Tab[selected='false', Tab{Dashboard}]
        │   ├── Tab[selected='false', Tab{Jens}]
        │   ├── Tab[selected='true', Tab{Annika}]
        │   ├── Tab[selected='false', Tab{Prognose}]
        │   └── Tab[selected='false', Tab{Einstellungen}]
        └── Div[@style='width:100%;height:100%;overflow:auto']
            └── HorizontalLayout[@style='width:100%;height:100%', @theme='spacing']
                ├── VerticalLayout[@style='width:40%', @theme='padding spacing']
                │   ├── H3[text='Historische Einträge']
                │   └── Grid[allRowsVisible='true', size='0', pageSize='50', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@4c48ccc4', @multi-sort-priority='prepend', @style='width:100%']
                │       ├── Column[path='col0']
                │       ├── Column[path='col1']
                │       └── Column[]
                └── VerticalLayout[@class='glass-panel', @style='width:60%', @theme='padding spacing']
                    ├── H3[text='Werte eintragen / bearbeiten']
                    ├── DatePicker[label='Datum', value='2026-07-10', manualValidation='true']
                    ├── VerticalLayout[@style='width:100%', @theme='spacing']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Binance', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Binance', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@4c32428a', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Bitpanda', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Bitpanda', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@4812c244', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Coinbase', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Coinbase', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@231e5af', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='ING-Diba', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='ING-Diba', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@89537c1', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Scalable Capital', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Scalable Capital', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@56ed024b', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Sparkasse', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Sparkasse', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@8b3ea30', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Stihl', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Stihl', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@5a26a14', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Trade Republic', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trade Republic', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@24287e5e', @style='width:150px']
                    │   ├── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │   │   ├── Span[text='Tresor', @style='width:180px;font-weight:bold']
                    │   │   ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │   │   ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Tresor', @style='width:150px']
                    │   │   └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@1ae9cfca', @style='width:150px']
                    │   └── HorizontalLayout[@style='width:100%;align-items:center', @theme='spacing']
                    │       ├── Span[text='Trust', @style='width:180px;font-weight:bold']
                    │       ├── Span[text='Kein vorheriger Wert', @style='color:var(--lumo-secondary-text-color);width:220px']
                    │       ├── BigDecimalField[value='null', placeholder='Neuer Wert (€)', manualValidation='true', @data-inst='Trust', @style='width:150px']
                    │       └── ComboBox[value='null', size='8', pageSize='50', itemValuePath='key', itemIdPath='key', placeholder='Kategorie', manualValidation='true', dataprovider='com.vaadin.flow.data.provider.ListDataProvider@1b786da0', @style='width:150px']
                    └── Button[caption='Speichern', @theme='primary']
                        └── Text[text='Speichern']

[INFO]
[ERROR] Tests run: 17, Failures: 0, Errors: 5, Skipped: 0
[INFO]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD FAILURE
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  26.010 s
[INFO] Finished at: 2026-07-10T09:35:40+02:00
[INFO] ------------------------------------------------------------------------
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-surefire-plugin:3.5.6:test (default-test) on project finance-app:
[ERROR]
[ERROR] See /Users/jenskreutzer/Antigravity_Projects/finance-app/target/surefire-reports for the individual test results.
[ERROR] See dump files (if any exist) [date].dump, [date]-jvmRun[N].dump and [date].dumpstream.
[ERROR] -> [Help 1]
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] <http://cwiki.apache.org/confluence/display/MAVEN/MojoFailureException>

✅ Vorgang abgeschlossen. Drücken Sie eine beliebige Taste zum Beenden...

Saving session...
...copying shared history...
...saving history...truncating history files...
...completed.
