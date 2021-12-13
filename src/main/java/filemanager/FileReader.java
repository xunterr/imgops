package filemanager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileReader {

    public static List<Byte> readToByteArray(File file){
        try(FileInputStream fis = new FileInputStream(file)){

            List<Byte> byteArray = new ArrayList<>();
            for (byte b : fis.readAllBytes()){
                byteArray.add(b);
            }
            fis.close();
            return byteArray;

        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public static List<Byte> readToByteArray(File file, int offset, int length){
        try(FileInputStream fis = new FileInputStream(file)){
            byte[] byteArray = new byte[fis.available()];
            fis.read(byteArray, offset, length);
            fis.close();
            List<Byte> bytes = new ArrayList<>();
            for(byte b : byteArray){                               //MAKE IT BETTER PLS
                bytes.add(b);
            }
            return(bytes);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }

    }
}
