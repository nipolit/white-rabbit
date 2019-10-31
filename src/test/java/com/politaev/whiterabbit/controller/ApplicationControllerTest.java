package com.politaev.whiterabbit.controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static com.politaev.whiterabbit.controller.ApplicationController.initializeApplicationContext;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class ApplicationControllerTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outContent;
    private PrintStream outPrintStream;

    @Before
    public void setUp() {
        outContent = new ByteArrayOutputStream();
        outPrintStream = new PrintStream(outContent);
        System.setOut(outPrintStream);
    }

    @Test
    public void testMultipleResults() {
        ApplicationController applicationController = initializeApplicationContext()
                .withGivenPhrase("aa b b")
                .withWordLimit(4)
                .withMd5SumsToFind("f7f352b6dda87169409be317b6870696",
                        "ca0ec9a8ae74b46616be8765cb4e51e2")
                .createController();
        applicationController.runApplication();
        String output = outContent.toString();
        assertThat(output.split("\r?\n"))
                .containsOnly("f7f352b6dda87169409be317b6870696 baa' b",
                        "ca0ec9a8ae74b46616be8765cb4e51e2 a b a b");
    }

    @Test
    public void testSingleResult() {
        ApplicationController applicationController = initializeApplicationContext()
                .withGivenPhrase("aa b b")
                .withWordLimit(3)
                .withMd5SumsToFind("f7f352b6dda87169409be317b6870696",
                        "ca0ec9a8ae74b46616be8765cb4e51e2")
                .createController();
        applicationController.runApplication();
        assertEquals("f7f352b6dda87169409be317b6870696 baa' b", outContent.toString().trim());
    }

    @Test
    public void testPhraseNotCaseSensitive() {
        ApplicationController applicationController = initializeApplicationContext()
                .withGivenPhrase("Aa B b")
                .withWordLimit(3)
                .withMd5SumsToFind("f7f352b6dda87169409be317b6870696")
                .createController();
        applicationController.runApplication();
        assertEquals("f7f352b6dda87169409be317b6870696 baa' b", outContent.toString().trim());
    }

    @After
    public void tearDown() {
        System.setOut(originalOut);
        outPrintStream.close();
    }
}
