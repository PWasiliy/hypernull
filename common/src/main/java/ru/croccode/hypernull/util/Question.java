package ru.croccode.hypernull.util;

import java.util.Scanner;

public class Question {
    public static boolean ask(String question) {
        System.out.printf(question.endsWith("?") ? "%s (Y/N)\n" : "%s? (Y/N)\n", question);
        while (true) {
            String answer = new Scanner(System.in).nextLine();
            if (answer.trim().equalsIgnoreCase("y") || answer.trim().equalsIgnoreCase("yes"))
                return true;
            else if (answer.trim().equalsIgnoreCase("n") || answer.trim().equalsIgnoreCase("no"))
                return false;
            else
                System.out.println("Неизвестный тип ответа. Пожалуйста, попробуйте еще раз.");
        }
    }
}
