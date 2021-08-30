package com.girrafeecstud.a64_bitcalcrelease;

public class PreCalcStringModifier {

    private ValueParser parser = new ValueParser();

    // function to delete last character from input string
    private String deleteLastCharacter(String str){
        String result = "";

        if (str != null && str.length() > 0){
            result = str.substring(0, str.length()-1);
        }

        return result;
    }

    // apply all changes to string to make all calculations right
    public String modifyStringBeforeCalculation(String str){

        str = ifExpressionIsZero(str);
        str = ifFirstSignIsMinus(str);
        str = addBrackets(str);
        str = addZeroToEmptyBrackets(str);
        str = signAtTheEnd(str);
        str = addBraketsToNumarator(str);
        str = minusAfterLeftBracket(str);
        str = minusBeforeMultiple(str);
        str = minusBeforeDivSign(str);
        str = minusBeforeModSign(str);
        str = minusAfterPlus(str);
        str = minusAfterMinus(str);
        str = addBrackets(str);

        return str;
    }

    // function to add brakets to numerator before the division
    private String addBraketsToNumarator(String str){

        for (int i = 0; i < str.length(); i++){

            if (str.charAt(i) == '/' || str.charAt(i) == '%'){

                String numerator = str.substring(0, i);
                String partBeforeNumerator = str.substring(0, i);
                int numeratorLength = 0;

                // if we have right braket first we have to find last expression
                if (numerator.charAt(numerator.length()-1) == ')') {
                    numerator = parser.getLastExpression(numerator);
                    numeratorLength = numerator.length();

                    int j = 0;
                    while(j<numeratorLength) {
                        partBeforeNumerator = deleteLastCharacter(partBeforeNumerator);
                        j++;
                    }

                    str = partBeforeNumerator + "(" + numerator + ")" + str.substring(i, str.length());

                    return str;
                }
                // else if we have number in numerator
                numerator = parser.getLastNumber(numerator);
                numeratorLength = numerator.length();

                int j = 0;
                while(j<numeratorLength) {
                    partBeforeNumerator = deleteLastCharacter(partBeforeNumerator);
                    j++;
                }

                str = partBeforeNumerator + "(" + numerator + ")" + str.substring(i, str.length());

                return str;
            }

        }

        return str;
    }

    // function to avoid error when we have minus sign before division
    private String minusBeforeDivSign(String input){

        String temp = "";

        for (int i=0; i<input.length(); i++) {

            int j;

            if (i != 0 && i != input.length()-1 && input.charAt(i) == '-' && input.charAt(i-1) == '/') {

                for (j=i+1; j < input.length(); j++){

                    if (parser.isAction(input.charAt(j)))
                        break;
                    else
                        temp += input.charAt(j);


                }

                input = input.substring(0, i) + "(0-" + temp + ")" + input.substring(j, input.length());
                temp = "";
            }

        }
        return  input;
    }

    // function to avoid error when we have minus sign before mod sign
    private String minusBeforeModSign(String input){

        String temp = "";

        for (int i=0; i<input.length(); i++) {

            int j;

            if (i != 0 && i != input.length()-1 && input.charAt(i) == '-' && input.charAt(i-1) == '%') {

                for (j=i+1; j< input.length(); j++){

                    if (parser.isAction(input.charAt(j)))
                        break;
                    else
                        temp += input.charAt(j);


                }

                input = input.substring(0, i) + "(0-" + temp + ")" + input.substring(j, input.length());
                temp = "";
            }

        }

        return input;
    }

    // function to avoid error when we have minus sign before multiple sign
    private String minusBeforeMultiple(String input){

        String temp = "";

        for (int i=0; i<input.length(); i++) {

            int j;

            if (i != 0 && i != input.length() - 1 && input.charAt(i) == '-' && input.charAt(i - 1) == '*') {

                for (j = i + 1; j < input.length(); j++) {

                    if (parser.isAction(input.charAt(j)))
                        break;
                    else
                        temp += input.charAt(j);


                }

                input = input.substring(0, i) + "(0-" + temp + ")" + input.substring(j, input.length());
                temp = "";
            }
        }

        return input;
    }

    // function to avoid error when we have minus sign after left bracket
    private String minusAfterLeftBracket(String input){

        String temp = "";

        for (int i=0; i<input.length(); i++) {

            int j;

            if (i != 0 && i != input.length() - 1 && input.charAt(i) == '-' && input.charAt(i - 1) == '(') {

                for (j = i + 1; j < input.length(); j++) {

                    if (parser.isAction(input.charAt(j)))
                        break;
                    else
                        temp += input.charAt(j);


                }

                input = input.substring(0, i) + "0-" + temp + input.substring(j, input.length());
                temp = "";

            }
        }
        return input;
    }

    // function to avoid error when we have sign at the ena of the expression (apart from left or right bracket)
    private String signAtTheEnd(String input){

        if (parser.isAction(input.charAt(input.length()-1)) && (input.charAt(input.length()-1) != ')' || input.charAt(input.length()-1) != '('))
            input = input.substring(0, input.length()-1);

        return input;
    }

    // function to avoid error when we have minus sign after plus sign
    private String minusAfterPlus(String input){

        for (int i=0; i<input.length(); i++) {

            if (i != 0 && input.charAt(i) == '-' && input.charAt(i - 1) == '+')
                input = input.substring(0, i - 1) + "-" + input.substring(i + 1);
        }

        return input;
    }

    // function to avoid error when we have minus sign after minus sign
    private String minusAfterMinus(String input){

        for (int i = 0; i < input.length(); i++) {

            if (i != 0 && input.charAt(i) == '-' && input.charAt(i - 1) == '-')
                input = input.substring(0, i - 1) + "+" + input.substring(i + 1);
        }

        return input;
    }

    // function to add right brackets to the end of the expression
    // if the number of them is lower than the number of right brackets
    private String addBrackets(String input){

        int leftBracketsQuality = 0, rightBracketsQuality = 0;

        for (int i = 0; i < input.length(); i++){

            if (input.charAt(i) == '(')
                leftBracketsQuality++;
            else if (input.charAt(i) == ')')
                rightBracketsQuality++;
        }

        while (leftBracketsQuality > rightBracketsQuality) {
            input += ")";
            rightBracketsQuality++;
        }

        return input;
    }

    // function to add zero number to empty brackets
    private String addZeroToEmptyBrackets(String str){

        for (int i = 0; i < str.length(); i++){

            if (i != str.length()-1 && str.charAt(i) == '(' && str.charAt(i+1) == ')')
                str = str.substring(0, i+1) + "0" + str.substring(i+1, str.length());
        }

        return str;
    }

    // add plus and zero if the expression equals to zero
    private String ifExpressionIsZero(String str) {

        if (str.equals("0"))
            str += "+0";

        return str;
    }

    // function to add zero before minus sign if minus is the first sign
    private String ifFirstSignIsMinus(String str){

        if (str.charAt(0) == '-')
            str = "0" + str;

        return str;
    }

    /*
    private String NotSignFirstAndThenNegative(String numStr){

        if (numStr.charAt(0) == '!' && numStr.charAt(1) == '-') {

            boolean firstNumberIsFinished = false;
            String firstNumber = "-";
            int firstNumberLastIndex = 0;

            for (int i = 2; i < numStr.length(); i++)
                if (parser.isAction(numStr.charAt(i))) {
                    firstNumberLastIndex = i;
                    break;
                } else {
                    firstNumber += numStr.charAt(i);
                    if (i == numStr.length() - 1) {
                        firstNumberLastIndex = i + 1;
                        break;
                    }
                }
            if (firstNumberLastIndex >= numStr.length())
                numStr = numStr.substring(0, 1) + "(0" + firstNumber + ")";
            else
                numStr = numStr.substring(0, 1) + "(0" + firstNumber + ")" + numStr.substring(firstNumberLastIndex, numStr.length());
        }

        return numStr;
    }
     */

}
