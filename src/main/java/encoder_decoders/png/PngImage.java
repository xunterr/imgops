package encoder_decoders.png;

import encoder_decoders.ByteOps;
import filemanager.FileReader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PngImage {

    List<Byte> byteArray = new ArrayList<>();

    protected void encode(Object message, File file) {

    }

    protected void decode(File file) {
        byteArray = FileReader.readToByteArray(file, 0, 8);
        if(isTrueFile(byteArray)){
            System.out.println("ITS A PNG!");
        }else{System.out.println("ITS ISNT A PNG!");}
    }

    protected boolean isTrueFile(List<Byte> byteArray) {
        boolean result = false;
        byte[] trueInfo = new byte[8];
        for(int i = 0; i<8; i++){
            trueInfo[i] = byteArray.get(i);
        }
        String magicNumber = ByteOps.bytesToHex(trueInfo);
        System.out.println(magicNumber);
        if(magicNumber.equalsIgnoreCase("89504e470d0a1a0a"))
            result = true;

        return result;
    }
}
