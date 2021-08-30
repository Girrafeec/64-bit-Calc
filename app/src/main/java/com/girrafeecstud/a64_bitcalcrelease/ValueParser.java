package com.girrafeecstud.a64_bitcalcrelease;

import java.util.ArrayList;

public class ValueParser {

    public ArrayList<String> parsedNumbers = new ArrayList<String>();
    public ArrayList<String> parsedActions = new ArrayList<String>();
    public ArrayList<String> dataOrder = new ArrayList<String>();

    public boolean isDigit(char input){

        if(input == '1' ||
                input == '2' ||
                input == '3' ||
                input == '4' ||
                input == '5' ||
                input == '6' ||
                input == '7' ||
                input == '8' ||
                input == '9' ||
                input == '0' ||
                input == 'A' ||
                input == 'B' ||
                input == 'C' ||
                input == 'D' ||
                input == 'E' ||
                input == 'F')
            return true;

        return false;

    }

    public boolean isAction(char input){

        if(input == '+' ||
                input == '-' ||
                input == '*' ||
                input == '/' ||
                input == '%' ||
                input == '(' ||
                input == ')' ||
                input == '&' ||
                input == '|' ||
                input == '!' ||
                input == '^' ||
                input == '~' ||
                input == '>' ||
                input == '<' ||
                input == '_')
            return true;

        return false;
    }

    // function returns index of last arithmetical operator
    public int findLastArithmeticalOperator(String str){

        int index;

        for (index = str.length()-1; index >= 0; index--){
            char c = str.charAt(index);
            // find last operator before negative number if the last is negative
            if (c == '-' && ((index != 0 && isAction(str.charAt(index-1)) && str.charAt(index-1) != ')') || str.lastIndexOf(c) == 0)){
                index = index - 1;
                break;
            }

            if (isAction(c))
                break;
        }

        if (index == 0 && str.charAt(index) != '(')
            index -= 1;
        else if (index == 0)
            index = 0;

        return index;
    }

    // function to return the last number from input string
    public String getLastNumber(String input){

        String str = "";

        for (int i=input.length()-1; i >= 0; i--){
            // number may be negative or have NOT sign (it' a part of number)
            if (isDigit(input.charAt(i)) || input.charAt(i) == '!' || (input.charAt(i) == '-' && i == 0)
                    || (i != 0 && input.charAt(i) == '-' && isAction(input.charAt(i-1)) && input.charAt(i-1) != ')')){
                str = input.charAt(i) + str;
            }
            else
                break;
        }

        return str;
    }

    // function to return the last expression from input string
    public String getLastExpression(String input){

        int numberOfRightBrackets = 0;
        int numberOfLeftBrackets = 0;
        String lastExpression = "";

        for (int i = input.length()-1; i >= 0; i--){
            lastExpression = input.charAt(i) + lastExpression;
            if (input.charAt(i) == ')')
                numberOfRightBrackets++;
            else if (input.charAt(i) == '(')
                numberOfLeftBrackets++;

            if (numberOfLeftBrackets == numberOfRightBrackets)
                break;
        }

        // find out if we have another signs for our expression like NOT sign or -
        int i = 0;
        while(i < lastExpression.length()){
            if (input != null && input.length() > 0){
                input = input.substring(0, input.length()-1);
            }
            i++;
        }

        for (int j = input.length()-1; j >= 0; j--){
            if (input.charAt(j) == '!' || (j == 0 && input.charAt(j) == '-')
                    || (j != 0 && input.charAt(j) == '-' && isAction(input.charAt(j-1)) && input.charAt(j-1) != ')'))
                lastExpression = input.charAt(j) + lastExpression;
            else
                break;
        }

        return lastExpression;
    }

    public void parseString(String inputStr) {

        String temp = "";

        for (int i = 0; i < inputStr.length(); i++) {

            // save minus if it is negative decimal number
            if (inputStr.length() > 1 && i == 0 && inputStr.charAt(0) == '-' && isDigit(inputStr.charAt(1))) {
                temp += String.valueOf(inputStr.charAt(0));
                i += 1;
            }

            // save minus if it is negative decimal number and we have other sign before
            if (i != 0 && (inputStr.charAt(i) == '-') && isAction(inputStr.charAt(i-1)) && inputStr.charAt(i-1) != ')' && isDigit(inputStr.charAt(i+1))){
                temp += String.valueOf(inputStr.charAt(i));
                i += 1;
            }

            if(isDigit(inputStr.charAt(i))) {

                    temp += String.valueOf(inputStr.charAt(i));

                    if (i+1 == inputStr.length()){
                            parsedNumbers.add(temp);
                            dataOrder.add("Number");
                            temp = "";
                    }
            }
            else{
                if (!temp.isEmpty()) {
                    parsedNumbers.add(temp);
                    dataOrder.add("Number");
                    temp = "";
                }
                parsedActions.add(String.valueOf(inputStr.charAt(i)));
                dataOrder.add("Sign");
            }

        }

    }

}
