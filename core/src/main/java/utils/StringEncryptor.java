package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringEncryptor
{
    private MessageDigest Encryptor;

    public StringEncryptor(String algorithm)
    {
        try
        {
            Encryptor= MessageDigest.getInstance(algorithm);
        }
        catch (NoSuchAlgorithmException e) {e.printStackTrace();}
    }

    public String encrypt(String s)
    {
        Encryptor.reset();

        byte[] bs = Encryptor.digest(s.getBytes());

        StringBuilder stringBuilder = new StringBuilder();

        //hex encode the digest
        for (byte b : bs)
        {
            String hexVal = Integer.toHexString(0xFF & b);
            if (hexVal.length() == 1) stringBuilder.append("0");
            stringBuilder.append(hexVal);
        }

        return stringBuilder.toString();
    }
}
