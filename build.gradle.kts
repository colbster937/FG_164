import groovy.util.Node

buildscript {
  repositories {
    maven("https://plugins.gradle.org/m2/")
  }
  dependencies {
    classpath("org.eclipse.jgit:org.eclipse.jgit:5.10.0.202012080955-r")
  }
}

plugins {
  id("java")
  id("idea")
  id("eclipse")
  kotlin("jvm") version "1.9.23"
  `maven-publish`
  `java-gradle-plugin`
}

val jdk = JavaVersion.current()
if (jdk != JavaVersion.VERSION_1_8) {
  throw GradleException("Expected JDK 8, but found JDK ${jdk.majorVersion}.")
}

group = "net.minecraftforge.gradle"
version = "1.0-SNAPSHOT"

//println("Version $version")

base {
  archivesBaseName = "ForgeGradle"
}

java {
  toolchain {
    languageVersion.set(JavaLanguageVersion.of(8))
  }
}

repositories {
  maven("https://maven.minecraftforge.net/")
  mavenCentral()
  mavenLocal()
}

dependencies {
  implementation(gradleApi())
  implementation(gradleKotlinDsl())

  implementation("org.ow2.asm:asm:7.3.1")
  implementation("org.ow2.asm:asm-commons:7.3.1")
  implementation("org.ow2.asm:asm-tree:7.3.1")
  implementation("net.sourceforge.argo:argo:3.4")
  implementation("net.sf.opencsv:opencsv:2.3")
  implementation("com.cloudbees:diff4j:1.1")
  implementation("com.github.abrarsyed.jastyle:jAstyle:1.2")

  implementation("com.github.jponge:lzma-java:1.3")
  implementation("com.nothome:javaxdelta:2.0.1")
  implementation("com.google.code.gson:gson:2.2.4")
  implementation("com.github.tony19:named-regexp:0.2.3")

  implementation("net.md-5:SpecialSource:1.11.2")

  implementation("de.oceanlabs.mcp:RetroGuard:3.6.6")
  implementation("de.oceanlabs.mcp:mcinjector:3.1")

  implementation("org.javassist:javassist:3.29.2-GA")

  implementation(kotlin("stdlib"))
}

gradlePlugin {
  plugins {
    create("forge") {
      id = "net.minecraftforge.forgegradle"
      implementationClass = "net.minecraftforge.gradle.user.ForgeUserPlugin"
    }
  }
}

publishing {
  repositories {
    maven {
      name = "GithubPackages"
      url = uri("https://maven.pkg.github.com/colbster937/FG_164")
      credentials {
        username = System.getenv("GITHUB_ACTOR")
        password = System.getenv("GITHUB_TOKEN")
      }
    }
  }

  afterEvaluate {
    publications.named<MavenPublication>("pluginMaven") {
      groupId = "net.minecraftforge"
      artifactId = "forgegradle"
    }

    publishing.publications.named<MavenPublication>("forgePluginMarkerMaven") {
      pom.withXml {
        val deps = asNode().children().filterIsInstance<Node>().find { it.name().toString().contains("dependencies") }
        deps?.children()?.filterIsInstance<Node>()?.forEach { dep ->
          dep.children().filterIsInstance<Node>().forEach { n ->
            when {
              n.name().toString().contains("groupId") -> n.setValue("net.minecraftforge")
              n.name().toString().contains("artifactId") -> n.setValue("forgegradle")
            }
          }
        }
      }
    }
  }
}

tasks.jar {
  manifest {
    attributes(
      mapOf(
        "version" to project.version,
        "javaCompliance" to project.java.targetCompatibility,
        "group" to project.group
      )
    )
  }
}

tasks.named("validatePlugins") {
  enabled = false
}
