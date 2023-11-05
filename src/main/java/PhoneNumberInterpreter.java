import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class PhoneNumberInterpreter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter a sequence of numbers separated by spaces: ");
        String input = scanner.nextLine();

        boolean status = checkInputFormat(input);
        if (!status) {
            return;
        }

        input = input.replaceAll(" ", "");
        Set<String> interpretations = new HashSet<>();
        generateInterpretations(input, "", interpretations);

        for (String interpretation : interpretations) {
            String interpretationWithoutSpaces = interpretation.replaceAll(" ", "");
            System.out.println("Interpretation: " + interpretationWithoutSpaces);
            validatePhoneNumber(interpretationWithoutSpaces);
        }
    }
    
    //Method to identify the natural number ambiguities
    public static void generateInterpretations(String input, String current, Set<String> interpretations) {
        if (input.isEmpty()) {
            interpretations.add(current);
            return;
        }

        if (input.startsWith("003069")) {
            generateInterpretations(input.substring(6), current + "003069", interpretations);
        } else if (input.startsWith("00302")) {
            generateInterpretations(input.substring(5), current + "00302", interpretations);
        } else if (input.startsWith("2")) {
            generateInterpretations(input.substring(1), current + "2", interpretations);
        } else if (input.startsWith("69")) {
            generateInterpretations(input.substring(2), current + "69", interpretations);
        } else {
            for (int i = 1; i <= 3 && i <= input.length(); i++) {
                String part = input.substring(0, i);
                String remaining = input.substring(i);

                generateInterpretations(remaining, current + part, interpretations);

                
                if (part.length() == 2) {
                    char firstDigit = part.charAt(0);
                    char secondDigit = part.charAt(1);
                    if (firstDigit >= '2' && firstDigit <= '9' && secondDigit != '0') {
                        generateInterpretations(remaining, current + firstDigit + "0" + secondDigit, interpretations);
                    }
                }
            }
        }
    }
    
    //Method to check if the given input is a valid phone number
    public static void validatePhoneNumber(String phoneNumber) {
        if (phoneNumber.length() == 10) {
            if (phoneNumber.startsWith("2") || phoneNumber.startsWith("69")) {
                System.out.println("[phone number: VALID]");
            } else {
                System.out.println("[phone number: INVALID]");
            }
        } else if (phoneNumber.length() == 14) {
            if (phoneNumber.startsWith("00302") || phoneNumber.startsWith("003069")) {
                System.out.println("[phone number: VALID]");
            } else {
                System.out.println("[phone number: INVALID]");
            }
        } else {
            System.out.println("[phone number: INVALID]");
        }
    }
    
    //Method to check if each number in the input sequence is up to a three digit number
    public static boolean checkInputFormat(String input) {
        String[] numbers = input.split("\\s+"); // Split input by spaces
        if (numbers.length <= 1) {
            return true;
        }
        for (String number : numbers) {
            if (!number.matches("\\d{1,3}")) {
                System.out.println("The input's format is not proper!");
                return false;
            }
        }
        return true;
    }
}
