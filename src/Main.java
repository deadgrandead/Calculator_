import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static String calc(String input) throws IOException{

        String[] parts = input.split(" ");

        String number1 = parts[0].trim();
        String number2 = parts[2].trim();
        String operation = parts[1];

        boolean isArabic = isArabicNumber(number1) && isArabicNumber(number2);
        boolean isRoman = isRomanNumber(number1) && isRomanNumber(number2);

        if (!isArabic && !isRoman) {
            throw new IOException("Неправильный ввод");
        }
        if (isArabic && isRoman) {
            throw new IOException("Используются одновременно разные системы счисления");
        }

        int num1 = isArabic ? Integer.parseInt(number1) : Roman.valueOf(number1).GetValue();
        int num2 = isArabic ? Integer.parseInt(number2) : Roman.valueOf(number2).GetValue();

        if (num1 > 10 || num1 < 0 || num2 > 10 || num2 < 0) {
            throw new IllegalArgumentException("Числа должны быть от 1 до 10 включительно");
        }
        int result = switch (operation) {
            case "+" -> num1 + num2;
            case "-" -> num1 - num2;
            case "*" -> num1 * num2;
            case "/" -> Math.round(num1 / num2);
            default -> throw new IOException("Неверный оператор");
        };
        return isArabic ? String.valueOf(result) : Roman.fromInt(result).toString();
    }

    private static boolean isArabicNumber(String number) {
        for (char ch : number.toCharArray()) {
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isRomanNumber(String number) {
        String romanNumerals = "IVX";
        for (char ch : number.toCharArray()) {
            if (romanNumerals.indexOf(ch) == -1) {
                return false;
            }
        }
        return true;
    }

    private static boolean isValidExpression(String input) {
        String[] operators = {"+", "-", "*", "/"};
        int count = 0;
        StringBuilder operands = new StringBuilder(input);
        for (String operator : operators) {
            int index = input.indexOf(operator);
            while (index != -1) {
                count++;
                operands.setCharAt(index, ' ');
                index = input.indexOf(operator, index + 1);
            }
        }
        if (count != 1) {
            return false;
        }

        String[] operandArray = operands.toString().trim().split("\\s+");
        return operandArray.length == 2;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        if (input == null)
            throw new IOException("Пустая строка");
        if (isValidExpression(input)) {
            System.out.println(calc(input));
        }else
            throw new IOException("формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)");
    }
}