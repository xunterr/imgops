package com.xunterr.imgops.image.png;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HexFormat;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PngImageTest {

    PngImage pngImage;
    ClassLoader classLoader;

    @BeforeEach
    void setUp() {
        this.classLoader = PngImage.class.getClassLoader();
        this.pngImage = new PngImage(classLoader.getResource("test.png").getFile());
    }

    @Test
    void getName() {
        String name = pngImage.getName();
        assertEquals("test", name);
    }

    @Test
    void getExtension(){
        List<String> extension = pngImage.getExtensions();
        assertEquals("png", extension.get(0));

    }

    @Test
    void decode_NOT_NULL() throws IOException {
        assertNotNull(pngImage.decode());
    }

    @Test
    void decode_THROWS_NOTFOUND(){
        PngImage pngInvalidImage = new PngImage("abcd.png");
        assertThrows(IOException.class, pngInvalidImage::decode);
    }

    @Test
    void encode() {
    }

    @Test
    void isTrueFile() {
        boolean isTrueFile = pngImage.isTrueFile(HexFormat.of().parseHex("89504E470D0A1A0A"));
        assertTrue(isTrueFile);
    }

    @Test
    void getFile() {
        assertNotNull(pngImage.getFile());
    }

    @Test
    void getWidth() {
    }

    @Test
    void getHeight() {
    }

    @Test
    void getCompressionMethod() {
    }

    @Test
    void getData() {
    }
}