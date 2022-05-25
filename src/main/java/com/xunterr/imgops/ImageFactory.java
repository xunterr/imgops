package com.xunterr.imgops;

import com.xunterr.imgops.image.Image;
import com.xunterr.imgops.image.png.PngImage;

import java.util.ArrayList;
import java.util.List;

public class ImageFactory {
    List<Image> images = new ArrayList<>();

    public static Image getImage(String filepath){
        String[] search = filepath.split("\\.");
        String searchLower = search[search.length - 1].toLowerCase();
        return switch (searchLower) {
            case "png" -> new PngImage(filepath);
            default -> null;
        };
    }



}
