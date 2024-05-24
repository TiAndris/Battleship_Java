import java.io.BufferedReader;
import java.io.InputStreamReader;

class Main {
    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        // start coding here
        String input = reader.readLine();
        System.out.println(reverseString(input));
        reader.close();
    }

    private static String reverseString(String str) {
        StringBuilder reversedStr = new StringBuilder(str);
        return reversedStr.reverse().toString();
    }
}

//class Main {
//    public static void main(String[] args) throws Exception {
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
//            String line = reader.readLine();
//            for (int i = line.length() - 1; i > -1; i--) {
//                System.out.print(line.charAt(i));
//            }
//        }
//    }
//}