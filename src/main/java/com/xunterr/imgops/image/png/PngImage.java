package com.xunterr.imgops.image.png;

import com.google.common.primitives.Ints;
import com.xunterr.imgops.exception.InvalidImageException;
import com.xunterr.imgops.image.Image;
import com.xunterr.imgops.utils.ByteOps;
import lombok.Getter;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.InflaterOutputStream;

@Getter
public class PngImage implements Image {

    private final File file;
    private int width;
    private int height;
    private int compressionMethod;
    private String textData;
    private byte[] data;
    private Set<PngChunk> chunks;

    public PngImage(String filename) {
        this.file = new File(filename);
    }

    @Override
    public List<String> getExtensions() {
        return List.of("png");
    }

    @Override
    public String getName(){
        return file.getName().split("\\.")[0];
    }

    @Override
    public byte[] decode() throws IOException {
        byte[] bytes;

        try {
            bytes = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            throw new FileNotFoundException("File '" + file.getPath() + "' not found");
        }

        if(!isTrueFile(bytes)){throw new InvalidImageException("This is not a png file");}

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        parseChunks(bytes);
        for (PngChunk chunk : chunks) {
            switch (chunk.getType()) {
                case IHDR -> parseIHDRChunk(chunk);
                case TEXT -> parseTEXTChunk(chunk);
                case IDAT -> baos.writeBytes(addIDATChunk(chunk).toByteArray());
                case IEND -> parseIENDChunk(baos);
            }
        }

        return data;
    }

    private void parseChunks(byte[] data) {
        int position = 8;
        this.chunks = new HashSet<>();
        while(position < data.length){
            PngChunk chunk = new PngChunk(Arrays.copyOfRange(data, position, data.length));
            chunks.add(chunk);
            position += chunk.getTotalLength();
        }
    }

    private void parseTEXTChunk(PngChunk chunk) {
        this.textData = new String(chunk.getData(), StandardCharsets.UTF_8);
    }

    private void parseIENDChunk(ByteArrayOutputStream baos) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        OutputStream ios = new InflaterOutputStream(os);
        ios.write(baos.toByteArray());
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
        return pngRecognitionHex.equals("89504E470D0A1A0A");
    }
}