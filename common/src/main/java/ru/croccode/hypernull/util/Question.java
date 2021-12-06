package ru.croccode.hypernull.util;

import java.util.Scanner;

public class Question {
    public static boolean ask(String question) {
        System.out.printf(question.endsWith("?") ? "%s (Y/N)" : "%s? (Y/N)", question);
        while (true) {
            String answer = new Scanner(System.in).nextLine();
            if (answer.equalsIgnoreCase("y") || answer.equalsIgnoreCase("yes"))
                return true;
            else if (answer.equalsIgnoreCase("n") || answer.equalsIgnoreCase("no"))
                return false;
            else
                System.out.println("Неизвестный тип ответа. Пожалуйста, попробуйте еще раз.");
        }
    }
}
