package com.thoughtworks.i0.plugins.gradle

import org.gradle.api.Project
import org.gradle.testfixtures.ProjectBuilder
import org.hamcrest.CoreMatchers
import org.junit.BeforeClass
import org.junit.Test

import static org.junit.Assert.assertThat
import org.gradle.api.artifacts.Configuration

import static org.junit.Assert.fail
import org.gradle.api.artifacts.DependencySet

class I0PluginTest {
    private static Project project;

    @BeforeClass
    public static void before() {
        project = ProjectBuilder.builder().build()
        project.apply plugin: 'i0'
    }

    @Test
    public void should_add_repositories() {
        assertThat(project.repositories.size(), CoreMatchers.is(2))
    }

    @Test
    public void should_add_compile_dependencies() {
        Configuration compile = project.configurations.getByName("compile")

        checkDependencies(compile.allDependencies,
                ['javax.inject:javax.inject:1',
                 'javax.servlet:servlet-api:3.0',
                 'javax.annotation:jsr-305:2.0.1'])
    }

    private void checkDependencies(DependencySet dependencies, ArrayList<String> expected) {
        for (dependency in expected) {
            if (dependencies.findAll {"$it.group:$it.name:$it.version" == dependency}.empty)
                fail("'$dependency' should be add to dependencies")
        }
    }
}
