package com.xunterr.imgops.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public interface Image {

    List<String> getName();

    byte[] decode() throws IOException;

    byte[] encode(InputStream os);
}
