package com.xunterr.imgops;

import com.xunterr.imgops.image.Image;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        System.out.print("Input a full path: ");
        String filepath = in.nextLine();

        Image image = ImageFactory.getImage(filepath);
        byte[] data;
        try {
            data = image.decode();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Decoded data length: " + data.length);

    }
}
