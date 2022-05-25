package com.xunterr.imgops;

import com.xunterr.imgops.image.Image;
import com.xunterr.imgops.image.png.PngImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageFactoryTest {

    ClassLoader classLoader;

    @BeforeEach
    public void setUp() {
        this.classLoader = Main.class.getClassLoader();
    }

    @Test
    void getImage() {
        Image img = new PngImage("test.png");
        assertEquals(
                img.getName(),
                ImageFactory.getImage("test.png")
                .getName());
    }
}