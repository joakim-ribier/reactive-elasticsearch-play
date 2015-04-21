package utils;

import java.io.File;
import java.io.IOException;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class FileUtils implements IFileUtils {

    @Override
    public String getContent(String filePath) throws IOException {
        File file = new File(filePath);
        return Files.toString(file, Charsets.UTF_8);
    }
    
    @Override
    public void write(String filePath, String content) throws IOException {
        File file = new File(filePath);
        Files.write(content, file, Charsets.UTF_8);
    }
    
}
