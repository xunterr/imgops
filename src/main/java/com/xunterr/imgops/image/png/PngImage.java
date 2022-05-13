package com.xunterr.imgops.image.png;

import com.google.common.primitives.Ints;
import com.xunterr.imgops.image.Image;
import com.xunterr.imgops.utils.ByteOps;
import lombok.Getter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.zip.InflaterOutputStream;

@Getter
public class PngImage implements Image {

    private final File file;
    private int width;
    private int height;
    private int compressionMethod;
    private byte[] data;

    public PngImage(String filename) {
        this.file = new File(filename);
    }

    @Override
    public List<String> getName() {
        return List.of("png");
    }

    @Override
    public byte[] decode() {
        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException("File '" + file.getPath() + "' not found");
        }

        if(!isTrueFile(bytes)){
            throw new RuntimeException("This is not a png file");
        }

        int IDATNumber = 0;
        int position = 8;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while(position < bytes.length){
            PngChunk chunk = new PngChunk(Arrays.copyOfRange(bytes, position, bytes.length));
            System.out.println("Current chunk: " + chunk.getType());
            switch (chunk.getType()) {
                case "IHDR" -> parseIHDRChunk(chunk);
                case "tEXt" -> parseTEXTChunk(chunk);
                case "IDAT" -> {
                    baos.writeBytes(addIDATChunk(chunk).toByteArray());
                    IDATNumber++;
                }
                case "IEND" -> {
                    parseIENDChunk(baos);
                    System.out.println(data.length);
                }
            }
            position += chunk.getTotalLength();
        }
        System.out.println("\tNumber of IDAT chunks: " + IDATNumber);
        return new byte[10];
    }

    private void parseTEXTChunk(PngChunk chunk) {
        System.out.println("\tText: " + new String(chunk.getData(), StandardCharsets.UTF_8));
    }

    private void parseIENDChunk(ByteArrayOutputStream baos) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try (OutputStream ios = new InflaterOutputStream(os)) {
            ios.write(baos.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.data = os.toByteArray();
    }

    private ByteArrayOutputStream addIDATChunk(PngChunk chunk) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.writeBytes(chunk.getData());
        return baos;
    }

    private void parseIHDRChunk(PngChunk chunk) {
        this.width = Ints.fromByteArray(Arrays.copyOfRange(chunk.getData(), 0, 4));
        this.height = Ints.fromByteArray(Arrays.copyOfRange(chunk.getData(), 4, 8));
        this.compressionMethod = Arrays.copyOfRange(chunk.getData(), 10, 11)[0];
    }

    @Override
    public byte[] encode(InputStream is) {
        return null;
    }

    protected boolean isTrueFile(byte[] bytes) {
        String pngRecognitionHex = ByteOps.bytesToHex(Arrays.copyOfRange(bytes, 0,8));
        if(pngRecognitionHex.equals("89504E470D0A1A0A")){
            System.out.println("PNG hex found");
            return true;
        }
        return false;
    }
}