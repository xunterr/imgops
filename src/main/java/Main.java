
import encoder_decoders.ExtensionManager;
import encoder_decoders.IEncoderDecoder;


import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Input a full path: ");
        String filename = in.nextLine();

        ExtensionManager manager = new ExtensionManager();
        String[] split = filename.split("\\.");
        IEncoderDecoder extension = manager.getExtension(split[split.length - 1]);

        extension.decodeMessage(new File(filename));

    }
}
