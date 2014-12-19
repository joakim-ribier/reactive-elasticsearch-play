package utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import com.google.common.io.Files;

public class FileUtils implements IFileUtils {

    @Override
    public String getContent(String filePath) throws IOException {
        File file = new File(filePath);
        return Files.toString(file, Charset.defaultCharset());
    }

}
