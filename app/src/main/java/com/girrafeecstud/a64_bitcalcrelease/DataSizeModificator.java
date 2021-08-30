package com.girrafeecstud.a64_bitcalcrelease;

import android.util.Log;

import java.math.BigInteger;

public class DataSizeModificator {

    private NotationConverter converter = new NotationConverter();

    // function return the number of extra bits in binary string (when the data mode is changed)
    public int numberOfExtraBitsInString(String dataMode, String inputStr){

        int maxBits = 0;
        int extraBits = 0;

        switch (dataMode){
            case "BYTE":
                maxBits = 8;
                break;
            case "WORD":
                maxBits = 16;
                break;
            case "DWORD":
                maxBits = 32;
                break;
            case "QWORD":
                maxBits = 64;
                break;
            default:
                break;
        }

        extraBits = inputStr.length() - maxBits;

        if (extraBits < 0)
            extraBits = 0; // if the length of inputStr is lower than maxBits, so we have 0 extraBits

        return extraBits;
    }

    // check if potential input number is bigger than allowed
    public boolean lowerThanBorder(String dataMode, String inputStr){

        BigInteger maxPositiveValue = new BigInteger(String.valueOf(0));
        BigInteger currentDataValue = new BigInteger(inputStr);
        BigInteger maxnegativeValue = new BigInteger(String.valueOf(0)); // max value without sign

        switch (dataMode){
            case "BYTE":
                maxPositiveValue = BigInteger.valueOf(127);
                break;
            case "WORD":
                maxPositiveValue = BigInteger.valueOf(32767);
                break;
            case "DWORD":
                maxPositiveValue = BigInteger.valueOf(2147483647);
                break;
            case "QWORD":
                maxPositiveValue = new BigInteger("9223372036854775807");
                break;
            default:
                break;
        }

        switch (dataMode) {
            case "BYTE":
                maxnegativeValue = BigInteger.valueOf(-128);
                break;
            case "WORD":
                maxnegativeValue = BigInteger.valueOf(-32768);
                break;
            case "DWORD":
                maxnegativeValue = new BigInteger("-2147483648");
                break;
            case "QWORD":
                maxnegativeValue = new BigInteger("-9223372036854775808");
                break;
            default:
                break;
        }

        if (inputStr.charAt(0) == '-') {

            if (currentDataValue.compareTo(maxnegativeValue) == -1) // if cur if bigger than max negative value
                return false;
            else
                return true;

        }

        if (currentDataValue.compareTo(maxPositiveValue) == 1) // if cur if bigger than max negative value
            return false;
        else
            return true;
    }

    // convert negative decimal value to unsigned value
    public String toUnsignedMode(String dataMode, String str){

        BigInteger maxValue = new BigInteger(String.valueOf(0)); // max value without sign
        BigInteger currentDataValue = new BigInteger(str);

        switch (dataMode) {
            case "BYTE":
                maxValue = BigInteger.valueOf(256);
                break;
            case "WORD":
                maxValue = BigInteger.valueOf(65536);
                break;
            case "DWORD":
                maxValue = new BigInteger("4294967296");
                break;
            case "QWORD":
                maxValue = new BigInteger("18446744073709551616");
                break;
            default:
                break;
        }

        currentDataValue = currentDataValue.add(maxValue);

        return currentDataValue.toString();
    }

    // convert decimal number from unsigned mode to negative mode
    public String ifAnswerNegative(String dataMode, String inputStr) {

        BigInteger maxValue = new BigInteger(String.valueOf(0)); // max value without sign
        BigInteger maxPositiveValue = new BigInteger(String.valueOf(0)); // max positive value
        BigInteger currentDataValue = new BigInteger(inputStr);

        switch (dataMode) {
            case "BYTE":
                maxValue = BigInteger.valueOf(256);
                break;
            case "WORD":
                maxValue = BigInteger.valueOf(65536);
                break;
            case "DWORD":
                maxValue = new BigInteger("4294967296");
                break;
            case "QWORD":
                maxValue = new BigInteger("18446744073709551616");
                break;
            default:
                break;
        }

        switch (dataMode){
            case "BYTE":
                maxPositiveValue = BigInteger.valueOf(127);
                break;
            case "WORD":
                maxPositiveValue = BigInteger.valueOf(32767);
                break;
            case "DWORD":
                maxPositiveValue = BigInteger.valueOf(2147483647);
                break;
            case "QWORD":
                maxPositiveValue = new BigInteger("9223372036854775807");
                break;
            default:
                break;
        }

        // if cur if bigger than max positive value and not bigger than max value
        if (currentDataValue.compareTo(maxPositiveValue) == 1 && currentDataValue.compareTo(maxValue) != 1)
            currentDataValue = currentDataValue.subtract(maxValue);

        return currentDataValue.toString();
    }

    // function to supplement binary string with zeros
    public String addZeros(String dataMode, String str){

        int maxBits = 0;

        switch (dataMode){
            case "BYTE":
                maxBits = 8;
                break;
            case "WORD":
                maxBits = 16;
                break;
            case "DWORD":
                maxBits = 32;
                break;
            case "QWORD":
                maxBits = 64;
                break;
            default:
                break;
        }

        while (str.length() < maxBits)
            str = "0" + str;

        return str;
    }

    // function to check if the calc result if bigger than active data size mode
    public String checkResSize(String dataMode, String str){

        int extraBits = 0;

        if (str.charAt(0) == '-')
            str = toUnsignedMode(dataMode, str);

        str = converter.convertTo("Decimal", "Binary", str);
        Log.i("bin val", str);
        extraBits = numberOfExtraBitsInString(dataMode, str);
        Log.i("extra", String.valueOf(extraBits));
        str = str.substring(extraBits, str.length());
        str = converter.convertTo("Binary", "Decimal", str);

        return str;
    }

}
