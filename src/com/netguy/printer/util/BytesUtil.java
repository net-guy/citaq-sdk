package com.netguy.printer.util;

import android.annotation.SuppressLint;
import android.util.Base64;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BytesUtil {

    /**
     * Converts a file to a byte array
     * 
     * @param f
     * @return byte[] if successful, null if the file was null or an error occurred
     */
    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a file from a byte array
     * 
     * @param b
     * @param outputFile
     * @return File if successful, null if an error occurred
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    /**
     * Converts a byte array to an object
     * 
     * @param objBytes
     * @return Object if successful, null if byte array was null or empty
     * @throws Exception
     */
    public static Object getObjectFromBytes(byte[] objBytes) throws Exception {
        if (objBytes == null || objBytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        return oi.readObject();
    }

    /**
     * Converts an object to a byte array
     * 
     * @param obj
     * @return byte[] if successful, null if the object was null
     * @throws Exception
     */
    public static byte[] getBytesFromObject(Serializable obj) throws Exception {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo = new ObjectOutputStream(bo);
        oo.writeObject(obj);
        return bo.toByteArray();
    }

    /**
     * Converts a byte array into a Base64 encoded String.
     * Base64 encoding schemes are commonly used when there is a need
     * to encode binary data, especially when that data needs to be
     * stored and transferred over media that are designed to deal with text.
     *
     * @param data Input byte array to be converted into a Base64 string.
     * @return Base64 encoded string.
     *         Returns null if the byte array is null or empty.
     */
    public static String getBase64StringFromBytes(byte[] data) {
        if (data == null || data.length <= 0) {
            return null;
        }
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    /**
     * Converts a Base64 encoded string to a byte array.
     *
     * @param base64string A Base64 encoded string to convert.
     * @return Byte array corresponding to the input Base64 string.
     *         Returns null if the input string is null or empty.
     */
    public static byte[] getBytesFromBase64String(String base64string) {
        if (base64string == null || base64string.equals("")) {
            return null;
        }
        return Base64.decode(base64string, Base64.DEFAULT);
    }

    /**
     * Converts a byte array to a hexadecimal string.
     *
     * @param data byte array to convert.
     * @return The resulting hexadecimal string, or null if the input is a null or
     *         empty byte array.
     */
    public static String getHexStringFromBytes(byte[] data) {

        // Return null if the input data is null or of zero length
        if (data == null || data.length <= 0) {
            return null;
        }

        String hexString = "0123456789ABCDEF";
        int size = data.length * 2;
        // StringBuilder is used for efficient string concatenation
        StringBuilder sb = new StringBuilder(size);

        for (int i = 0; i < size; i++) {
            // The left shift operator ( << ) is used to append the hex equivalent of high
            // nibble
            sb.append(hexString.charAt((data[i] & 0xF0) >> 4));
            // The left shift operator ( << ) is used to append the hex equivalent of low
            // nibble
            sb.append(hexString.charAt((data[i] & 0x0F) >> 0));
        }

        // Return hexadecimal representation from byte array as a String
        return sb.toString();
    }

    /**
     * Converts a hexadecimal string to a byte array by iterating over each hex pair
     * in the input string.
     * For each pair, it converts the pair to a decimal byte value and stores it in
     * the output byte array.
     * 
     * @param hexstring Hexadecimal value represented as a string, with 2 characters
     *                  representing each byte.
     * @return byte[] Byte array representing the input hexadecimal string.
     *         Returns null if the input string is null or empty.
     */
    @SuppressLint("DefaultLocale")
    public static byte[] getBytesFromHexString(String hexstring) {
        // User input check: return null if hexstring is empty or null
        if (hexstring == null || hexstring.equals("")) {
            return null;
        }

        // Convert all characters in hexstring to upper case
        hexstring = hexstring.toUpperCase();

        // Initialize a byte array of the size needed
        int size = hexstring.length() / 2;
        char[] hexarray = hexstring.toCharArray();
        byte[] rv = new byte[size];

        // Convert each hex pair in string to a decimal byte value and store in byte
        // array
        for (int i = 0; i < size; i++) {
            int pos = i * 2;
            rv[i] = (byte) (charToByte(hexarray[pos]) << 4 | charToByte(hexarray[pos + 1]));
        }

        // Return the byte array
        return rv;
    }

    /**
     * This method attempts to convert a string into a byte array using GBK
     * encoding.
     * 
     * @param paramString The string to be converted into GBK-encoded byte array.
     * @return returns a GBK-encoded byte array representation of the input string.
     *         If any error occurs during the conversion, the error stack trace will
     *         be printed and null will be returned.
     */
    public static byte[] getGbk(String paramString) {
        byte[] arrayOfByte = null;
        try {
            arrayOfByte = paramString.getBytes("GBK");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return arrayOfByte;
    }

    /**
     * This method transforms a given string into a printable text byte array.
     * The string "s" is first split into an array by spaces.
     * Then, for each element (if it's not empty), its bytes are obtained via the
     * transCommandBytes method
     * and copied into the printText array. This continues until the whole input
     * string is processed.
     * 
     * Finally, a new byte array of the exact filled length in printText is created
     * and filled with the corresponding data from printText before being returned.
     * 
     * @param s The string input to be transformed into a printable text byte array.
     * @return A byte array version of the input string that is suitable for
     *         printing.
     */
    public static byte[] transToPrintText(String s) {

        byte[] printText = new byte[4096];
        int iNum = 0;
        byte[] cmdData;
        String[] tmp = s.split(" ");

        for (int i = 0; i < tmp.length; i++) {
            if (tmp[i].length() > 0) {
                cmdData = transCommandBytes(tmp[i]);
                System.arraycopy(cmdData, 0, printText, iNum, cmdData.length);
                iNum += cmdData.length;
            }
        }

        cmdData = new byte[iNum];
        System.arraycopy(printText, 0, cmdData, 0, iNum);

        return cmdData;
    }

    /**
     * This method transforms input string commands into corresponding byte arrays.
     * First, it checks if the command string is equal to any byte in the 'cmdBytes'
     * array.
     * If a match is found, it returns the byte index as a new byte array.
     * 
     * If no match is found, it then checks the command string against three
     * patterns:
     * 1. A digit (up to three digits long) ending in 'D' or 'd'.
     * 2. A hex-like string (up to two characters long) ending in 'H' or 'h'.
     * 3. A string starting with "0x" followed by a hex-like string (up to two
     * characters long).
     *
     * If the command string matches the first pattern, it is parsed as an integer.
     * If the integer is above 255, the function attempts to convert the
     * command string into a GBK-encoded byte array; otherwise, it returns the byte
     * value of the integer.
     *
     * If the command string matches the second or third pattern, it converts the
     * (hex-like)
     * part of the string into a byte array.
     *
     * If the command string does not match any patterns, the function attempts to
     * convert the command string into a GBK-encoded byte array.
     *
     * @param s The string to be converted into a byte array.
     * @return The resulting byte array corresponding to the input command string.
     */
    public static byte[] transCommandBytes(String s) {
        for (byte i = 0; i < cmdBytes.length; i++) {
            if (cmdBytes[i].equals(s)) {
                return new byte[] { i };
            }
        }

        Pattern p1 = Pattern.compile("(\\d{1,3})([Dd]$)");
        Pattern p2 = Pattern.compile("([0-9a-fA-F]{1,2})([Hh]$)");
        Pattern p3 = Pattern.compile("^0x([0-9a-fA-F]{1,2})");
        Matcher m;

        m = p1.matcher(s);
        if (m.matches()) {
            int i = Integer.parseInt(m.group(1));
            if (i > 255) {
                return getGbk(s);
            } else {
                return new byte[] { (byte) i };
            }
        }

        m = p2.matcher(s);
        if (m.matches()) {
            return getBytesFromHexString(m.group(1));
        }

        m = p3.matcher(s);
        if (m.matches()) {
            return getBytesFromHexString(m.group(1));
        }

        return getGbk(s);
    }

    /**
     * This method converts a character (representing a hexadecimal digit) into its
     * decimal byte value.
     * It does this by finding its index in the string "0123456789ABCDEF". For
     * example, '0' will return 0, '1' will return 1, ... , 'F' or 'f' will return
     * 15.
     * 
     * @param c the character to be converted. This should be a character from '0'
     *          to 'F' or 'f'
     * @return The decimal byte value representation of the hexadecimal digit.
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }

    private static String[] cmdBytes = new String[] {
            "NUL", "SOH", "STX", "ETX", "EOT", "ENQ", "ACK", "BEL",
            "BS", "HT", "LF", "VT", "CLR", "CR", "SO", "SI",
            "DLE", "DC1", "DC2", "DC3", "DC4", "NAK", "SYN", "ETB",
            "CAN", "EM", "SUB", "ESC", "FS", "GS", "RS", "US",
            "SP"
    };

}
