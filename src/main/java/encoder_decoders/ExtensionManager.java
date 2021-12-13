package encoder_decoders;

import encoder_decoders.jpeg.JpegEncoderDecoder;
import encoder_decoders.png.PngEncoderDecoder;

import java.util.ArrayList;
import java.util.List;

public class ExtensionManager {
    List<IEncoderDecoder> extensions = new ArrayList<>();

    public ExtensionManager(){
        addExtension(new JpegEncoderDecoder());
        addExtension(new PngEncoderDecoder());
    }

    private void addExtension(IEncoderDecoder extension){
        boolean nameFound = extensions.stream().anyMatch((it) -> it.getName().equals(extension.getName()));
        if(!nameFound){
            extensions.add(extension);
        }
    }

    public IEncoderDecoder getExtension(String search){
        String searchLower = search.toLowerCase();
        for (IEncoderDecoder extension : extensions) {
            if(extension.getName().contains(searchLower)){
                return extension;
            }
        }
        return null;
    }



}
