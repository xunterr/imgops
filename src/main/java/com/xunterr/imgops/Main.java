package com.xunterr.imgops;

import com.xunterr.imgops.image.Image;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Input a full path: ");
        String filepath = in.nextLine();

        String[] split = filepath.split("\\.");
        ImageFactory manager = new ImageFactory(filepath);

        Image image = manager.getExtension(split[split.length - 1]);

        try {
            image.decode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
