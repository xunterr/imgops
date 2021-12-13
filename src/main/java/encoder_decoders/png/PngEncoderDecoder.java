package encoder_decoders.png;

import encoder_decoders.IEncoderDecoder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PngEncoderDecoder implements IEncoderDecoder {

    PngImage image = new PngImage();

    @Override
    public void encodeMessage(Object message, File file) {

    }

    @Override
    public void decodeMessage(File file) {
        image.decode(file);
    }

    @Override
    public List<String> getName() {
        List<String> arr = new ArrayList<>();
        arr.add("png");
        return arr;
    }


}
