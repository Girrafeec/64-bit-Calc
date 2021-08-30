package com.girrafeecstud.a64_bitcalcrelease;

import android.util.Log;
import android.widget.Button;

public class IODataChanger {

    private ValueParser parser = new ValueParser();
    private DataSizeModificator dataSizeModificator = new DataSizeModificator();
    private NotationConverter converter = new NotationConverter();
    private CalcEngine engine = new CalcEngine();

    // function to delete last character from input string
    public String deleteLastCharacter(String str){
        String result = "";

        if (str != null && str.length() > 0){
            result = str.substring(0, str.length()-1);
        }

        return result;
    }

    // function to delete zero from input when we try to input digit, randomize number or bracket
    public String removeZeroFromInput(String str){
        if (str.equals("0"))
            str = deleteLastCharacter(str);
        return str;
    }

    // Function to convert value from one notation to another
    public String convert(String dataModeButtonText, String prevMode, String curMode, String input){

        String convertResult = "";
        int actionIndex = 0, numberIndex = 0;

        if (!input.isEmpty()) {

            parser.parsedActions.clear();
            parser.parsedNumbers.clear();
            parser.dataOrder.clear();

            parser.parseString(input);
            input = "";

            for (int i = 0; i < parser.dataOrder.size(); i++){

                if (parser.dataOrder.get(i).equals("Sign")){
                    input += parser.parsedActions.get(actionIndex);
                    actionIndex++;
                }
                else if(parser.dataOrder.get(i).equals("Number")) {

                    if (parser.parsedNumbers.get(numberIndex).charAt(0) == '-')
                        parser.parsedNumbers.set(numberIndex, dataSizeModificator
                                .toUnsignedMode(dataModeButtonText, parser.parsedNumbers.get(numberIndex)));

                    convertResult = converter.convertTo(prevMode, curMode, parser.parsedNumbers.get(numberIndex));

                    if (curMode.equals("Decimal")){
                        Log.i("convert res", convertResult);
                        convertResult = dataSizeModificator.ifAnswerNegative(dataModeButtonText, convertResult);
                    }

                    input += convertResult;
                    numberIndex++;
                }

            }
        }

        return input;
    }

    // function to decrease bits in output string when the data mode has changeed
    public String decreaseOutputBits(String dataModeButtonText, String previousDataModeBinaryOutputStr, String currentNotationMode){

        int extraBits = 0;

        String outputStr = previousDataModeBinaryOutputStr;

        extraBits = dataSizeModificator.numberOfExtraBitsInString(dataModeButtonText, outputStr);
        outputStr = outputStr.substring(extraBits, outputStr.length());

        if (!currentNotationMode.equals("Binary"))
            outputStr = convert(dataModeButtonText, "Binary", currentNotationMode, outputStr);

        // check if data become negative
        if (!outputStr.isEmpty()) {
            // before that we should convert data from other notation to decimal
            if (!currentNotationMode.equals("Decimal"))
                outputStr = convert(dataModeButtonText, currentNotationMode, "Decimal", outputStr);
            outputStr = dataSizeModificator.ifAnswerNegative(dataModeButtonText, outputStr);
            if (!currentNotationMode.equals("Decimal"))
                outputStr = convert(dataModeButtonText, "Decimal", currentNotationMode, outputStr);
        }
        return outputStr;
    }

    // function to decrease bits in input string when the data mode has changeed
    public String decreaseInputBits(String dataModeButtonText, String previousDataModeBinaryInputStr, String currentNotationMode){

        int extraBits = 0;

        String inputStr = previousDataModeBinaryInputStr;

        parser.parsedActions.clear();
        parser.parsedNumbers.clear();
        parser.dataOrder.clear();

        parser.parseString(inputStr);
        inputStr = "";

        for (int i=0; i< parser.parsedNumbers.size(); i++){
            extraBits = dataSizeModificator.numberOfExtraBitsInString(dataModeButtonText, parser.parsedNumbers.get(i));
            parser.parsedNumbers.set(i, parser.parsedNumbers.get(i).substring(extraBits, parser.parsedNumbers.get(i).length()));
        }

        int actionIndex = 0, numberIndex = 0;

        for (int i = 0; i < parser.dataOrder.size(); i++){

            if (parser.dataOrder.get(i).equals("Sign")){
                inputStr += parser.parsedActions.get(actionIndex);
                actionIndex++;
            }
            else if(parser.dataOrder.get(i).equals("Number")) {
                inputStr += parser.parsedNumbers.get(numberIndex);
                numberIndex++;
            }

        }

        if (!currentNotationMode.equals("Binary"))
            inputStr = convert(dataModeButtonText, "Binary", currentNotationMode, inputStr);

        return inputStr;
    }

    //procedure to reverse sign in output string
    public String changeOutputNumberSign(String dataModeButtonText, String currentNotationMode, String outputStr){

        String str = outputStr;

        if (!currentNotationMode.equals("Binary"))
            str = convert(dataModeButtonText, currentNotationMode, "Binary", str);

        str = dataSizeModificator.addZeros(dataModeButtonText, str);

        char[] outputStrArray = new char[str.length()];

        for (int i = 0; i < outputStrArray.length; i++) {
            outputStrArray[i] = str.charAt(i);
        }

        // binary inversion
        for (int i=0; i < outputStrArray.length; i++){
            if (outputStrArray[i] == '0')
                outputStrArray[i] = '1';
            else
                outputStrArray[i] = '0';
        }

        str = String.valueOf(outputStrArray);

        str = convert(dataModeButtonText, "Binary", "Decimal", str);

        str = engine.caculate(str + "+1", dataModeButtonText);

        str = convert(dataModeButtonText, "Decimal", "Binary", str);

        if (!currentNotationMode.equals("Binary"))
            str = convert(dataModeButtonText, "Binary", currentNotationMode, str);

        return str;
    }

    // function to change sign of last input number or expression
    public String changeInputNumberSign(String dataModeButtonText, String currentNotationMode, String inputStr){

        String str = inputStr;

        // if last char is right braket we change sign of last expression
        if (str.charAt(str.length()-1) == ')'){

            String lastInputExpression = parser.getLastExpression(str);
            int lastInputExpressionLength = lastInputExpression.length();

            // if we already have - sign before expression
            if (lastInputExpression.charAt(0) == '-') {
                lastInputExpression = lastInputExpression.substring(1, lastInputExpressionLength);
                int i = 0;
                while(i<lastInputExpressionLength) {
                    str = deleteLastCharacter(str);
                    i++;
                }
                str = str + lastInputExpression;
                return str;
            }

            // else if we don't have - sign before our last expression
            lastInputExpression = "-" + lastInputExpression;
            int i=0;
            while(i<lastInputExpressionLength) {
                str = deleteLastCharacter(str);
                i++;
            }
            str = str + lastInputExpression;
            return str;
        }

        // if we do not have right braket at the end of expression wechange sign of last number
        String lastInputNumber = parser.getLastNumber(str);
        int lastInputNumberLength = lastInputNumber.length();

        // add '-' sign to number with NOT sign
        if (!lastInputNumber.isEmpty() && lastInputNumber.charAt(0) == '!') {
            lastInputNumber = "-" + lastInputNumber;
            int i=0;
            while(i<lastInputNumberLength) {
                str = deleteLastCharacter(str);
                i++;
            }
            str = str + lastInputNumber;
            return str;
        }

        // delete '-' sign from number with NOT sign
        if (!lastInputNumber.isEmpty() && lastInputNumber.charAt(0) == '-' && lastInputNumber.charAt(1) == '!'){
            lastInputNumber = lastInputNumber.substring(1, lastInputNumber.length());
            int i=0;
            while(i<lastInputNumberLength) {
                str = deleteLastCharacter(str);
                i++;
            }
            str = str + lastInputNumber;
            return str;
        }

        // find last number and reverse sign
        if (!currentNotationMode.equals("Binary"))
            lastInputNumber = convert(dataModeButtonText, currentNotationMode, "Binary", lastInputNumber);

        lastInputNumber = dataSizeModificator.addZeros(dataModeButtonText, lastInputNumber);

        char[] inputStrArray = new char[lastInputNumber.length()];

        for (int i = 0; i < inputStrArray.length; i++) {
            inputStrArray[i] = lastInputNumber.charAt(i);
        }

        // binary inversion
        for (int i=0; i < inputStrArray.length; i++){
            if (inputStrArray[i] == '0')
                inputStrArray[i] = '1';
            else
                inputStrArray[i] = '0';
        }

        lastInputNumber = String.valueOf(inputStrArray);

        lastInputNumber = convert(dataModeButtonText, "Binary", "Decimal", lastInputNumber);

        // add one to number
        lastInputNumber = engine.caculate(lastInputNumber + "+1", dataModeButtonText);

        lastInputNumber = convert(dataModeButtonText, "Decimal", "Binary", lastInputNumber);

        if (!currentNotationMode.equals("Binary"))
            lastInputNumber = convert(dataModeButtonText, "Binary", currentNotationMode, lastInputNumber);

        int i=0;
        while(i<lastInputNumberLength) {
            str = deleteLastCharacter(str);
            i++;
        }

        str = str + lastInputNumber;

        return str;
    }

    // function to add logic not operator to expression
    public String addLogicNotToExpression(String inputStr){

        String str = inputStr;
        String lastInputNumber = "";

        if ((str.length() > 1) && !(str.charAt(str.length()-1) == ')')) {
            lastInputNumber = parser.getLastNumber(str);
            int lastInputNumberLength = lastInputNumber.length();

            int i=0;
            while(i<lastInputNumberLength) {
                str = deleteLastCharacter(str);
                i++;
            }

            str += "!(" + lastInputNumber + ")";
        }
        else{
            String lastInputExpression = "";
            int lastInputExpressionLength = 0;

            lastInputExpression = parser.getLastExpression(str);
            lastInputExpressionLength = lastInputExpression.length();

            int i=0;
            while(i<lastInputExpressionLength) {
                str = deleteLastCharacter(str);
                i++;
            }

            str += "!(" + lastInputExpression + ")";
        }

        return str;
    }

    // delete full last number or expresison if we have Not sign before it
    public String delNotNumberOrExpression(String str){

        String lastInputNumber = "";

        if ((str.length() > 1) && !(str.charAt(str.length()-1) == ')')) {
            lastInputNumber = parser.getLastNumber(str);
            int lastInputNumberLength = lastInputNumber.length();

            int i=0;
            while(i<lastInputNumberLength) {
                str = deleteLastCharacter(str);
                i++;
            }

            // if we already have not operator before last number we delete all number
            if (!lastInputNumber.isEmpty() && lastInputNumberLength > 1 && (lastInputNumber.charAt(0) == '!'
                    || ((lastInputNumber.charAt(0) == '-') && (lastInputNumber.charAt(1) == '!')))) {
                return str;
            }
            else
                str += lastInputNumber;
            return str;
        }

            String lastInputExpression = "";
            int lastInputExpressionLength = 0;

            lastInputExpression = parser.getLastExpression(str);
            lastInputExpressionLength = lastInputExpression.length();

            int i=0;
            while(i<lastInputExpressionLength) {
                str = deleteLastCharacter(str);
                i++;
            }

            // if we already have not operator before last expression we delete all this expresison
            if (!lastInputExpression.isEmpty() && lastInputExpressionLength > 1 && (lastInputExpression.charAt(0) == '!'
                    || ((lastInputExpression.charAt(0) == '-') && (lastInputExpression.charAt(1) == '!')))) {
                return str;
            }
            else
                str += lastInputExpression;
            return str;
    }

    // change view of input string for user
    public String designInputForUser(String notationMode, String input) {

        String stringWithSpaces = "";
        int actionIndex = 0, numberIndex = 0;

        if (!input.isEmpty()) {

            parser.parsedActions.clear();
            parser.parsedNumbers.clear();
            parser.dataOrder.clear();

            parser.parseString(input);
            input = "";

            for (int i = 0; i < parser.dataOrder.size(); i++){

                if (parser.dataOrder.get(i).equals("Sign")){
                    if (!parser.parsedActions.get(actionIndex).equals("(") && !parser.parsedActions.get(actionIndex).equals(")")
                            && !parser.parsedActions.get(actionIndex).equals("!"))
                        input += " " + parser.parsedActions.get(actionIndex) + " ";
                    else
                        input += parser.parsedActions.get(actionIndex);
                    actionIndex++;
                }
                else if(parser.dataOrder.get(i).equals("Number")) {

                    stringWithSpaces = addSpacesToNumber(notationMode, parser.parsedNumbers.get(numberIndex));

                    input += stringWithSpaces;
                    numberIndex++;
                }

            }
        }

        input = changeSignsForUser(input);

        return input;
    }

    // change view of input string for engine
    public String designInputForEngine(String str){

        str =  delSpacesFromString(str);
        str = changeSignsForEngine(str);

        return str;
    }

    private String addSpacesToNumber(String notationMode, String str){

        int numberOfDigitGroup = 0;

        switch (notationMode){
            case "Binary":
                numberOfDigitGroup = 4;
                break;
            case "Octal":
                numberOfDigitGroup = 3;
                break;
            case "Decimal":
                numberOfDigitGroup = 3;
                break;
            case "Hexidecimal":
                numberOfDigitGroup = 4;
                break;
            default:
                break;
        }

        int digitCounter = 0;

        for (int i = str.length()-1; i >= 0; i--){

            if (parser.isDigit(str.charAt(i)))
                digitCounter++;

            if (i != 0){
                if ((digitCounter % numberOfDigitGroup == 0) && str.charAt(i-1) != '-') {
                    str = str.substring(0, i) + " " + str.substring(i, str.length());
                }
            }
        }

        return str;
    }

    // function to delete all spaces from string
    private String delSpacesFromString(String str){
        return str.replaceAll("\\s+", "");
    }

    // replace engine signs for user
    private String changeSignsForUser(String str){

        str = str.replaceAll("\\&", "AND");
        str = str.replaceAll("\\|", "OR");
        str = str.replaceAll("\\^", "XOR");
        str = str.replaceAll("\\<", "NOR");
        str = str.replaceAll("\\~", "XNOR");
        str = str.replaceAll("\\!", "NOT");
        str = str.replaceAll("\\_", "NAND");
        str = str.replaceAll("\\>", "IMPL");

        str = str.replaceAll("\\*", "×");
        str = str.replaceAll("\\/", "÷");


        return str;
    }

    // replace user signs for engine
    private String changeSignsForEngine(String str){

        str = str.replaceAll("NAND", "_");
        str = str.replaceAll("AND", "&");
        str = str.replaceAll("NOT", "!");
        str = str.replaceAll("XNOR", "~");
        str = str.replaceAll("XOR", "^");
        str = str.replaceAll("NOR", "<");
        str = str.replaceAll("OR", "|");
        str = str.replaceAll("IMPL", ">");

        str = str.replaceAll("×", "*");
        str = str.replaceAll("÷", "/");

        return str;
    }

}
