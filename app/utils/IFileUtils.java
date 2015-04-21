package utils;

import java.io.IOException;

public interface IFileUtils {

    String getContent(String filePath) throws IOException;

    void write(String filePath, String content) throws IOException;

}
