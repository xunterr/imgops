package encoder_decoders.png;

import encoder_decoders.ByteOps;
import encoder_decoders.Image;
import filemanager.FileReader;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.InflaterInputStream;

public class PngImage implements Image {

    private final File file;

    public PngImage(String filename) {
        this.file = new File(filename);
    }

    @Override
    public List<String> getName() {
        return List.of("png");
    }

    @Override
    public OutputStream decode() throws IOException {

        FileInputStream fis = new FileInputStream(file);

        if(!isTrueFile(fis.readAllBytes())){
            throw new RuntimeException("This is not a png file");
        }

        InflaterInputStream iis = new InflaterInputStream(fis);
        OutputStream os = new ByteArrayOutputStream();

        ByteOps.doCopy(iis, os);
        return os;
    }

    @Override
    public OutputStream encode(InputStream is) {
        return null;
    }

    protected boolean isTrueFile(byte[] bytes) {
        String pngRecognitionHex = ByteOps.bytesToHex(Arrays.copyOfRange(bytes, 0,8));
        if(pngRecognitionHex.equals("89504E470D0A1A0A")){
            System.out.println("PNG!");
            return true;
        }
        return false;
    }
}
