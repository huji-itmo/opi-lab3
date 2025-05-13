import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class Native2ASCII {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java Native2ASCII <input> <output>");
            return;
        }

        Path input = Paths.get(args[0]);
        Path output = Paths.get(args[1]);

        String content = new String(Files.readAllBytes(input), StandardCharsets.UTF_8);
        StringBuilder ascii = new StringBuilder();

        for (char c : content.toCharArray()) {
            if (c > 127) {
                ascii.append(String.format("\\u%04x", (int) c));
            } else {
                ascii.append(c);
            }
        }

        Files.write(output, ascii.toString().getBytes(StandardCharsets.US_ASCII));
        System.out.println("Файл преобразован: " + output);
    }
}