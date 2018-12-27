package pl.nauka.jarek.weather.common;

public class LettersConverter {

    public static String makeSmallLetters(String name) {
        char[] array = name.toCharArray();
        String smallLettersWord = "";
        char ch = 0;

        for (int i = 0; i < array.length; i++) {

            if (array[i] >= 'A' && array[i] <= 'Z'){
                ch = (char) (array[i] + 32);
                smallLettersWord = "" + smallLettersWord + ch;
            }else {
                smallLettersWord = "" + smallLettersWord + array[i];
            }
        }
        return smallLettersWord;
    }
}
