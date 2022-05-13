package com.xunterr.imgops;

import com.xunterr.imgops.image.Image;
import com.xunterr.imgops.image.png.PngImage;

import java.util.ArrayList;
import java.util.List;

public class ImageFactory {
    List<Image> images = new ArrayList<>();

    public ImageFactory(String filepath){
        addExtension(new PngImage(filepath));
    }

    private void addExtension(Image img){
        boolean nameFound = images.stream().anyMatch((it) -> it.getName().equals(img.getName()));
        if(!nameFound){
            images.add(img);
        }
    }

    public Image getExtension(String search){
        String searchLower = search.toLowerCase();
        for (Image image : images) {
            if(image.getName().contains(searchLower)){
                return image;
            }
        }
        return null;
    }



}
