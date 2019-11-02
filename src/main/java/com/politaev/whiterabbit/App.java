package com.politaev.whiterabbit;

import com.politaev.whiterabbit.controller.ApplicationController;
import picocli.CommandLine;

import static com.politaev.whiterabbit.controller.ApplicationController.initializeApplicationContext;

@CommandLine.Command(
        name = "white-rabbit",
        description = "Searches anagrams of the given phrase having specified md5 sums.",
        version = {"white-rabbit 1.0", "@|green Nikita Politaev (c) 2019|@"},
        sortOptions = false,
        mixinStandardHelpOptions = true,
        headerHeading = "@|bold,underline Usage|@:%n%n",
        descriptionHeading = "%n@|bold,underline Description|@:%n%n",
        parameterListHeading = "%n@|bold,underline Parameters|@:%n",
        optionListHeading = "%n@|bold,underline Options|@:%n",
        footerHeading = "%n",
        footer = {"More information at https://github.com/nipolit/white-rabbit", "@|green Nikita Politaev (c) 2019|@"},
        customSynopsis = "white-rabbit [-hV] -p=PHRASE -l=SIZE MD5...")
public class App implements Runnable {

    @CommandLine.Option(
            names = {"-p", "--phrase"},
            required = true,
            description = "Search for anagrams of this phrase.",
            paramLabel = "PHRASE")
    private String givenPhrase;

    @CommandLine.Option(
            names = {"-l", "--word-limit"},
            required = true,
            description = "Consider anagrams with at most this number of words.",
            paramLabel = "SIZE")
    private int wordLimit;

    @CommandLine.Parameters(
            arity = "1..*",
            description = "Md5 sums to search for.",
            paramLabel = "MD5")
    private String[] md5SumsToFind;

    public static void main(String[] args) {
        new CommandLine(new App()).execute(args);
    }

    @Override
    public void run() {
        try {
            ApplicationController applicationController = initializeApplicationContext()
                    .withGivenPhrase(givenPhrase)
                    .withWordLimit(wordLimit)
                    .withMd5SumsToFind(md5SumsToFind)
                    .createController();
            applicationController.runApplication();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
