package encoder_decoders;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface Image {

    List<String> getName();

    OutputStream decode() throws IOException;

    OutputStream encode(InputStream os);
}
