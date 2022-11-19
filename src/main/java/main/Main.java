package main;

import answer.QuestionAnswerer;
import consants.ConstantFilePaths;

public class Main {
    public static void main(String[] args) {
        Integer groupingAmountLimit = 10;
        String resourcePathToFile = ConstantFilePaths.ORIGINAL_WORLD_BANK_JSON;
        QuestionAnswerer.answerAllQuestions(resourcePathToFile, groupingAmountLimit);
    }
}
