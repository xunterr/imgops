package encoder_decoders.jpeg;

import encoder_decoders.IEncoderDecoder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JpegEncoderDecoder implements IEncoderDecoder {

    @Override
    public void encodeMessage(Object message, File file) {

    }

    @Override
    public void decodeMessage(File file) {

    }

    @Override
    public List<String> getName() {
        List<String> arr = new ArrayList<>();
        arr.add("jpeg");
        arr.add("jpg");
        return arr;
    }
}
