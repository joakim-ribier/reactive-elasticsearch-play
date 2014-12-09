package utils;

import models.exceptions.EncodeUtilsException;

public interface IEncodeUtils {

    String encode(String salt, String value) throws EncodeUtilsException;

}
