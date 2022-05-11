package com.xunterr.imgops.image.png;

import com.google.common.primitives.Ints;
import com.xunterr.imgops.image.Image;
import com.xunterr.imgops.utils.ByteOps;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

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
    public byte[] decode() throws IOException {
        byte[] bytes = Files.readAllBytes(file.toPath());

        if(!isTrueFile(bytes)){
            throw new RuntimeException("This is not a png file");
        }

        int IDATNumber = 0;
        int position = 8;
        while(position < bytes.length){
            PngChunk chunk = new PngChunk(Arrays.copyOfRange(bytes, position, bytes.length));
            System.out.println("Current chunk: " + chunk.getType());
            switch (chunk.getType()){
                case "IHDR":
                    parseIHDRChunk(chunk);
                    break;
                case "tEXt":
                    parseTEXTChunk(chunk);
                case "IDAT":
                    //addIDATChunk(chunk);
                    IDATNumber++;
                case "IEND":
                    parseIENDChunk(chunk);
            }
            position += chunk.getTotalLength();
        }
        System.out.println("\tNumber of IDAT chunks: " + IDATNumber);
        return new byte[10];
    }

    private void parseTEXTChunk(PngChunk chunk) {
        System.out.println("\tText: " + new String(chunk.getData(), StandardCharsets.UTF_8));
    }

    private void parseIENDChunk(PngChunk chunk) {
//        ByteArrayOutputStream os = new ByteArrayOutputStream();
//        try (OutputStream ios = new InflaterOutputStream(os)) {
//            ios.write(chunk.getData());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        this.data = os.toByteArray();
    }

    private void addIDATChunk(PngChunk chunk) {
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