package src;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;

public class Practice {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        // Filtering all the inputs to check for validation
        while(sc.hasNext()){
            try{
                String input = sc.nextLine();
                // Checking if input is null
                if (input.trim().isEmpty()) {
                    continue;
                }
                File f= new File(input);
                // Checking if the file exists
                if(!f.exists()){
                    System.out.println("File not found");
                    continue;
                }
                BufferedImage img = ImageIO.read(f);
                // Checking if the jpg file is not an image
                if (img == null) {
                    System.out.println("Error: File is not a supported image format.");
                    continue;
                }
                System.out.println("Lines found: " + countLines(img, 50));
            }catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }

    static int countLines(BufferedImage img, int threshold) {
        int[] rows = { img.getHeight() / 4, img.getHeight() / 2, 3 * img.getHeight() / 4 };
        // We'll track the highest line count found across all three rows
        int max = 0;

        for (int y : rows) {
            int count = 0;
            boolean in = false;
            for (int x = 0; x < img.getWidth(); x++) {
                boolean dark = isDark(img.getRGB(x, y), threshold);
                if (dark && !in)  { count++; in = true; }
                else if (!dark)     { in = false; }
            }
            max = Math.max(max, count);
        }

        return max;
    }

    // Extraction of packed RGB Binary
    static boolean isDark(int rgb, int t) {
        return ((rgb >> 16) & 0xFF) < t
                && ((rgb >>  8) & 0xFF) < t
                && ( rgb        & 0xFF) < t;
    }
}