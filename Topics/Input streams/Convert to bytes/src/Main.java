import java.io.InputStream;

class Main {
    public static void main(String[] args) throws Exception {
        InputStream inputStream = System.in;

        byte[] bytes = new byte[50];
        int charAsNumber = inputStream.read(bytes);
        for (int i = 0; i < charAsNumber; i++) {
            System.out.print((int) bytes[i]);
        }
        inputStream.close();
    }
}