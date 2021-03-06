package com.xunterr.imgops.image.png;

import lombok.Getter;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Getter
public class PngChunk {
    private final int totalLength;
    private int dataLength;
    private PngChunkType type;
    private byte[] data;
    private byte[] crc;

    public PngChunk(byte[] bytes) {
        setDataLength(bytes);
        setType(bytes);
        setData(bytes);
        setCrc(bytes);
        this.totalLength = this.dataLength + 12;
    }


    private void setDataLength(byte[] bytes) {
        byte[] dataLengthBytes = Arrays.copyOfRange(bytes, 0, 4);
        this.dataLength = ByteBuffer.wrap(dataLengthBytes).getInt();
    }

    private void setType(byte[] bytes) {
        byte[] typeBytes = Arrays.copyOfRange(bytes, 4, 8);
        this.type = PngChunkType.valueOf(new String(typeBytes, StandardCharsets.UTF_8).toUpperCase());
    }

    private void setData(byte[] bytes) {
        this.data = Arrays.copyOfRange(bytes, 8, 8 + this.dataLength);
    }

    private void setCrc(byte[] bytes) {
        this.crc = Arrays.copyOfRange(bytes, 8 + this.dataLength, 8 + this.dataLength + 4);
    }
}
