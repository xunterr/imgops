package encoder_decoders;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public interface IEncoderDecoder {

    public List<String> getName();
    public void encodeMessage(Object message, File file);
    public void decodeMessage(File file);

}
