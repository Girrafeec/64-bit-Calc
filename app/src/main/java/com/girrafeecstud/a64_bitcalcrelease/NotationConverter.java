package com.girrafeecstud.a64_bitcalcrelease;

import java.math.BigInteger;

public class NotationConverter {

    private String result = "";

    public String convertTo(String previousNotationMode, String currentNotationMode, String inputStr){

        switch (currentNotationMode){
            case "Binary":
                result = convertToBinary(previousNotationMode, inputStr);
                break;

            case "Octal":
                result = convertToOctal(previousNotationMode, inputStr);
                break;

            case "Decimal":
                result = convertToDecimal(previousNotationMode, inputStr);
                break;

            case "Hexidecimal":
                result = convertToHexidecimal(previousNotationMode, inputStr);
                break;

            default:
                break;
        }

        return result;
    }

    private String convertToHexidecimal(String previousNotationMode, String inputStr){

        String res = "";
        String decimalStr = "";
        String hexidecimal = "";
        BigInteger decimal;
        String digits = "0123456789ABCDEF";

            switch (previousNotationMode) {
                case "Binary":
                    decimalStr = convertToDecimal("Binary", inputStr);
                    break;

                case "Decimal":
                    decimalStr = inputStr;
                    break;

                case "Octal":
                    decimalStr = convertToDecimal("Octal", inputStr);
                    break;

                default:
                    break;
            }


            decimal = new BigInteger(decimalStr);

            while (!decimal.equals(new BigInteger(String.valueOf(0)))) {
                BigInteger temp = decimal.mod(new BigInteger(String.valueOf(16)));
                hexidecimal = String.valueOf(digits.charAt(temp.intValue())) + hexidecimal;
                decimal = decimal.divide(new BigInteger(String.valueOf(16)));
            }

            if (hexidecimal.isEmpty())
                hexidecimal = "0";
            res = hexidecimal;

        return res;
    }

    private String convertToOctal(String previousNotationMode, String inputStr){

        String res = "";
        String decimalStr = "";
        String octal = "";
        BigInteger decimal;

            switch (previousNotationMode) {
                case "Binary":
                    decimalStr = convertToDecimal("Binary", inputStr);
                    break;

                case "Decimal":
                    decimalStr = inputStr;
                    break;

                case "Hexidecimal":
                    decimalStr = convertToDecimal("Hexidecimal", inputStr);
                    break;

                default:
                    break;
            }

            decimal = new BigInteger(decimalStr);

            while (!decimal.equals(new BigInteger(String.valueOf(0)))) {
                octal = String.valueOf(decimal.mod(new BigInteger(String.valueOf(8)))) + octal;
                decimal = decimal.divide(new BigInteger(String.valueOf(8)));
            }

        if (octal.isEmpty())
            octal = "0";
        res = octal;

        return res;
    }

    private String convertToBinary(String previousNotationMode, String inputStr){

        String res = "";
        String binary = "";
        String decimalStr = "";
        BigInteger decimal;

            switch (previousNotationMode) {
                case "Decimal":
                    decimalStr = inputStr;
                    break;

                case "Octal":
                    decimalStr = convertToDecimal("Octal", inputStr);

                    break;

                case "Hexidecimal":
                    decimalStr = convertToDecimal("Hexidecimal", inputStr);
                    break;

                default:
                    break;
            }

            decimal = new BigInteger(decimalStr);

            while (!decimal.equals(new BigInteger(String.valueOf(0)))) {
                BigInteger temp = decimal.mod(new BigInteger(String.valueOf(2)));
                binary = temp.toString() + binary;
                decimal = decimal.divide(new BigInteger(String.valueOf(2)));
            }

            if (binary.isEmpty())
                binary = "0";
            res = binary;

        return res;
    }

    private String convertToDecimal(String previousNotationMode, String inputStr){

        String res = "";
        int n=0; // power
        BigInteger decimal = new BigInteger(String.valueOf(0));

            switch (previousNotationMode) {
                case "Binary":
                    BigInteger binary = new BigInteger(inputStr);

                    while (true) {
                        if (binary.equals(new BigInteger(String.valueOf(0)))) {
                            break;
                        } else {
                            BigInteger temp = binary.mod(new BigInteger(String.valueOf(10)));
                            BigInteger mult = new BigInteger(String.valueOf(2));
                            decimal = decimal.add(temp.multiply(mult.pow(n)));
                            binary = binary.divide(new BigInteger(String.valueOf(10)));
                            n++;
                        }
                    }
                    res = decimal.toString();
                    break;

                case "Octal":
                    BigInteger octal = new BigInteger(inputStr);

                    while (true) {
                        if (octal.equals(new BigInteger(String.valueOf(0)))) {
                            break;
                        } else {
                            BigInteger temp = octal.mod(new BigInteger(String.valueOf(10)));
                            BigInteger mult = new BigInteger(String.valueOf(8));
                            decimal = decimal.add(temp.multiply(mult.pow(n)));
                            octal = octal.divide(new BigInteger(String.valueOf(10)));
                            n++;
                        }
                    }
                    res = decimal.toString();
                    break;

                case "Hexidecimal":
                    String digits = "0123456789ABCDEF";
                    String hexidecimal = inputStr;
                    String fff = "";

                    for (int i = 0; i < hexidecimal.length(); i++) {
                        char c = hexidecimal.charAt(i);
                        BigInteger d = new BigInteger(String.valueOf(digits.indexOf(c)));
                        decimal = decimal.multiply(new BigInteger(String.valueOf(16)));
                        decimal = decimal.add(d);
                    }
                    res = decimal.toString();
                    break;

                default:
                    break;
            }

        return res;
    }


}
