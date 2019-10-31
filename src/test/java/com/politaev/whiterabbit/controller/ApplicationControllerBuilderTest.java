package com.politaev.whiterabbit.controller;

import org.junit.Test;

import static com.politaev.whiterabbit.controller.ApplicationController.initializeApplicationContext;

public class ApplicationControllerBuilderTest {

    @Test(expected = NullPointerException.class)
    public void testNoGivenPhrase() {
        initializeApplicationContext()
                .withWordLimit(5)
                .withMd5SumsToFind("13d466a23dbcea55e7deb9934e4e5256",
                        "3e6c5745201e7f5e9561cd3d3b160f9d")
                .createController();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNoWordLimit() {
        initializeApplicationContext()
                .withGivenPhrase("Klaatu barada nikto")
                .withMd5SumsToFind("13d466a23dbcea55e7deb9934e4e5256",
                        "3e6c5745201e7f5e9561cd3d3b160f9d")
                .createController();
    }

    @Test(expected = NullPointerException.class)
    public void testNoMd5SumsToFind() {
        initializeApplicationContext()
                .withGivenPhrase("klaatu verata necktie")
                .withWordLimit(3)
                .createController();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonPositiveWordLimit() {
        initializeApplicationContext()
                .withGivenPhrase("klaatu verata nectar")
                .withWordLimit(0)
                .withMd5SumsToFind("13d466a23dbcea55e7deb9934e4e5256",
                        "3e6c5745201e7f5e9561cd3d3b160f9d")
                .createController();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyGivenPhrase() {
        initializeApplicationContext()
                .withGivenPhrase("")
                .withWordLimit(5)
                .withMd5SumsToFind("13d466a23dbcea55e7deb9934e4e5256")
                .createController();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalCharactersInGivenPhrase() {
        initializeApplicationContext()
                .withGivenPhrase("Klâätû bÅrádå ñíçkél")
                .withWordLimit(5)
                .withMd5SumsToFind("3e6c5745201e7f5e9561cd3d3b160f9d")
                .createController();
    }
}
