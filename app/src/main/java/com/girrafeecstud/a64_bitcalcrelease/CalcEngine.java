package com.girrafeecstud.a64_bitcalcrelease;

import android.util.Log;

import java.math.BigInteger;
import java.util.Stack;


public class CalcEngine {

    /**The general idea is to use the stack's popping into the stack to push the stack to process the ordering of an expression and perform corresponding operations.*/

    //Number stack: used to store each number in the expression
    private Stack<BigInteger> numberStack = null;
    //Symbol stack: used to store operators and parentheses
    private Stack<Character> symbolStack = null;

    private int scale;//Circular decimals appear during division to retain precision
    public CalcEngine(int scale) {
        super();
        this.scale = scale;
    }
    public CalcEngine() {
        this(32);
    }

    private static String dataMode = "";

    private DataSizeModificator dataSizeChanger = new DataSizeModificator();
    private PreCalcStringModifier numStrModifier = new PreCalcStringModifier();

    /**Pass the string in from outside! ! ! */
    public String caculate(String numStr, String currentDataMode) {

        dataMode = currentDataMode;

        numStr = numStrModifier.modifyStringBeforeCalculation(numStr);


        //Judging whether the arithmetic means is over, that is, judging if there is a = at the end! Not added!
        //equals method: comparison of values
        //charAt method: retrieval method
        if (numStr.length() >= 1 && !"=".equals(numStr.charAt(numStr.length() - 1) + "")) {
            numStr += "=";
        }

        //Check if the expression is correct! Utilize Standard method (custom)
        if (!isStandard(numStr)) {
            String isstandardtext;
            isstandardtext = "Error";
            return null;
        }
        // Initialize the stack
        if (numberStack == null) {
            numberStack = new Stack<BigInteger>();
        }
        numberStack.clear();
        if (symbolStack == null) {
            symbolStack = new Stack<Character>();
        }
        symbolStack.clear();

        /**! ! ! ! ! ! ! ! ! ! Core! ! ! ! ! ! ! ! ! ! ! ! */
        //Create a StringBuffer to hold multiple numbers
        StringBuffer temp = new StringBuffer();
        // Start processing from the first character of the expression
        for (int i = 0; i < numStr.length(); i++) {
            // Get a character
            char ch = numStr.charAt(i);
            // If the current character is a number
            if (isNumber(ch)) {
                // Add to the digital cache
                temp.append(ch);
            } else {  // non-number case
                // Convert the number cache to a string
                String tempStr = temp.toString();
                if (!tempStr.isEmpty()) {
                    BigInteger num = new BigInteger(tempStr);
                    // Push the number onto the stack
                    numberStack.push(num);
                    // Reset the digital cache
                    temp = new StringBuffer();
                }

                // Determine the priority of the operator, if the current priority is lower than the priority of the top of the stack, first calculate it before the calculation
                while (!comparePri(ch) && !symbolStack.empty()) {
                    // Pop the stack, take out the number, last in first out
                    BigInteger b = numberStack.pop();
                    BigInteger a = new BigInteger(String.valueOf(0));

                    boolean aEmptyFlag = false; // flag to chek if a is null or not when using logic not operation

                    if (!numberStack.isEmpty())
                        a = numberStack.pop();
                    else
                        aEmptyFlag = true;

                    BigInteger result;

                    // Take out the operator to perform the corresponding operation, and push the result to the stack for the next operation
                    switch ((char) symbolStack.pop()) {
                        case '+':
                            result = a.add(b);
                            result = new BigInteger(dataSizeChanger.checkResSize(dataMode, result.toString()));
                            numberStack.push(result);
                            break;

                        case '-':
                            result = a.subtract(b);
                            result = new BigInteger(dataSizeChanger.checkResSize(dataMode, result.toString()));
                            numberStack.push(result);
                            break;

                        case '*':
                            result = a.multiply(b);
                            result = new BigInteger(dataSizeChanger.checkResSize(dataMode, result.toString()));
                            numberStack.push(result);
                            break;

                        case '/':
                            try {
                                String newB, newA;

                                b = new BigInteger(new DataSizeModificator().ifAnswerNegative(dataMode, b.toString()));

                                if (b.toString().charAt(0) == '-') {
                                    newB = b.toString().substring(1, b.toString().length());
                                    b = new BigInteger(String.valueOf(newB));
                                    newA = "-" + a.toString();
                                    a = new BigInteger(String.valueOf(newA));
                                }

                                result = a.divide(b);
                                result = new BigInteger(dataSizeChanger.checkResSize(dataMode, result.toString()));
                                numberStack.push(result);
                            } catch (java.lang.ArithmeticException e) {
                                // When there is an infinite loop in the division, an exception will be thrown, and the precision is set here to recalculate
                                //numberStack.push(null);
                                return "Cannot divide by zero";
                            }
                            break;

                        case '%':
                            try {
                                String newB, newA;

                                b = new BigInteger(new DataSizeModificator().ifAnswerNegative(dataMode, b.toString()));

                                if (b.toString().charAt(0) == '-') {
                                    newB = b.toString().substring(1, b.toString().length());
                                    b = new BigInteger(String.valueOf(newB));
                                    newA = "-" + a.toString();
                                    a = new BigInteger(String.valueOf(newA));
                                }

                                result = a.mod(b);
                                result = new BigInteger(dataSizeChanger.checkResSize(dataMode, result.toString()));
                                numberStack.push(result);
                            } catch (java.lang.ArithmeticException e) {
                                // When there is an infinite loop in the division, an exception will be thrown, and the precision is set here to recalculate
                                //numberStack.push(null);
                                return "Cannot divide by zero";
                            }
                            break;

                        case '&': // and operation

                            if (a.toString().charAt(0) == '-')
                                a = new BigInteger(new DataSizeModificator().toUnsignedMode(dataMode, a.toString()));

                            if (b.toString().charAt(0) == '-')
                                b = new BigInteger(new DataSizeModificator().toUnsignedMode(dataMode, b.toString()));

                            result = a.and(b);
                            result = new BigInteger(dataSizeChanger.checkResSize(dataMode, result.toString()));
                            numberStack.push(result);
                            break;

                        case '!': // not operation

                            if (!aEmptyFlag)
                                numberStack.push(a);

                            result = b.not();
                            result = new BigInteger(dataSizeChanger.checkResSize(dataMode, result.toString()));
                            numberStack.push(result);
                            break;

                        case '|': // or operation
                            if (a.toString().charAt(0) == '-')
                                a = new BigInteger(new DataSizeModificator().toUnsignedMode(dataMode, a.toString()));

                            if (b.toString().charAt(0) == '-')
                                b = new BigInteger(new DataSizeModificator().toUnsignedMode(dataMode, b.toString()));

                            result = a.or(b);
                            result = new BigInteger(dataSizeChanger.checkResSize(dataMode, result.toString()));
                            numberStack.push(result);
                            break;

                        case '^': // xor operation
                            if (a.toString().charAt(0) == '-')
                                a = new BigInteger(new DataSizeModificator().toUnsignedMode(dataMode, a.toString()));

                            if (b.toString().charAt(0) == '-')
                                b = new BigInteger(new DataSizeModificator().toUnsignedMode(dataMode, b.toString()));

                            result = a.xor(b);
                            result = new BigInteger(dataSizeChanger.checkResSize(dataMode, result.toString()));
                            numberStack.push(result);
                            break;

                        case '<': // nor operation (стрелка пирса)
                            if (a.toString().charAt(0) == '-')
                                a = new BigInteger(new DataSizeModificator().toUnsignedMode(dataMode, a.toString()));

                            if (b.toString().charAt(0) == '-')
                                b = new BigInteger(new DataSizeModificator().toUnsignedMode(dataMode, b.toString()));

                            result = a.or(b);
                            result = result.not();
                            result = new BigInteger(dataSizeChanger.checkResSize(dataMode, result.toString()));
                            numberStack.push(result);
                            break;

                        case '_': // nand operation (sheffer)
                            if (a.toString().charAt(0) == '-')
                                a = new BigInteger(new DataSizeModificator().toUnsignedMode(dataMode, a.toString()));

                            if (b.toString().charAt(0) == '-')
                                b = new BigInteger(new DataSizeModificator().toUnsignedMode(dataMode, b.toString()));

                            result = a.and(b);
                            result = result.not();
                            result = new BigInteger(dataSizeChanger.checkResSize(dataMode, result.toString()));
                            numberStack.push(result);
                            break;

                        case '~': // xnor operation (equality)
                            if (a.toString().charAt(0) == '-')
                                a = new BigInteger(new DataSizeModificator().toUnsignedMode(dataMode, a.toString()));

                            if (b.toString().charAt(0) == '-')
                                b = new BigInteger(new DataSizeModificator().toUnsignedMode(dataMode, b.toString()));

                            result = a.not().and(b.not()).or(a.and(b));
                            result = new BigInteger(dataSizeChanger.checkResSize(dataMode, result.toString()));
                            numberStack.push(result);
                            break;

                        case '>': // imply operation
                            if (a.toString().charAt(0) == '-')
                                a = new BigInteger(new DataSizeModificator().toUnsignedMode(dataMode, a.toString()));

                            if (b.toString().charAt(0) == '-')
                                b = new BigInteger(new DataSizeModificator().toUnsignedMode(dataMode, b.toString()));

                            a = a.not();
                            result = a.or(b);
                            result = new BigInteger(dataSizeChanger.checkResSize(dataMode, result.toString()));
                            numberStack.push(result);
                            break;

                        default:
                            break;
                    }
                } // while loop ends

                if (ch != '=') {
                    // The symbol is pushed onto the stack
                    symbolStack.push(new Character(ch));
                    // Remove parentheses
                    if (ch == ')') {
                        symbolStack.pop();
                        symbolStack.pop();
                    }
                }
            }
        }  // for loop ends

        // return calculation result
        return numberStack.pop().toString();
    }


    /**================================ Check whether the arithmetic expression is qualified======== ==============================*/
    private boolean isStandard(String numStr) {
        // expression cannot be empty
        if (numStr == null || numStr.isEmpty())
            return false;
        // Used to save parentheses, check whether the left and right parentheses match
        Stack<Character> stack = new Stack<Character>();
        // used to mark'='Whether there are multiple symbols
        boolean b = false;
        for (int i = 0; i < numStr.length(); i++) {
            char n = numStr.charAt(i);
            // Determine whether the character is legal
            if (!(isNumber(n) || "(".equals(n + "") || ")".equals(n + "")
                    || "+".equals(n + "") || "-".equals(n + "")
                    || "*".equals(n + "") || "/".equals(n + "") || "%".equals(n + "")  || "=".equals(n + "")
                    || "&".equals(n + "") || "!".equals(n + "") || "|".equals(n + "") || ">".equals(n + "")
                    || "~".equals(n + "") || "^".equals(n + "") || "_".equals(n + "") || "<".equals(n + ""))) {
                return false;
            }
            // Push the left parenthesis on the stack to match the right parenthesis afterwards
            if ("(".equals(n + "")) {
                stack.push(n);
            }
            if (")".equals(n + "")) {  // match brackets
                if (stack.isEmpty() || !"(".equals((char) stack.pop() + ""))  // Whether the brackets match
                    return false;
            }
            // Check if there are multiple'=' signs
            if ("=".equals(n + "")) {
                if (b)
                    return false;
                b = true;
            }
        }
        // There may be missing closing brackets
        if (!stack.isEmpty())
            return false;
        // an examination'='Is the number not at the end
        if (!("=".equals(numStr.charAt(numStr.length() - 1) + "")))
            return false;
        return true;
    }

    /**================================ Determine whether it is a number from 0-9====== ==================================*/
    private boolean isNumber(char num) {
        if ((num >= '0' && num <= '9'))
            return true;
        return false;
    }

    /**==============Comparison priority, if the current operator has a higher priority than the top element operator in the stack, it returns true, otherwise it returns false========= =*/
    private boolean comparePri(char symbol) {
        // empty stack returns true
        if (symbolStack.empty()) {
            return true;
        }

        /*
                   I designed this calculator to add, subtract, multiply and divide, lg, ln, brackets, and trigonometric functions. The normal priority is to have brackets to calculate the brackets first, then multiply and divide, add and subtract
                   First level: (
                   The second level: × ÷ sin cos tan lg ln
                   Level 3: +-
                   The fourth level:)
         */
        // & - and   | - or    ^ - xor    > - imply   < - nand (pierce)   ! - not
        //  ~ - xnor - equality _ - nand (sheffer)

        // View the object at the top of the stack
        char top = (char) symbolStack.peek();
        if (top == '(') {
            return true;
        }
        // Compare priority
        switch (symbol) {
            case '(':  // highest priority
                return true;
            // Priority is higher than all signs apart from '('
            case '!': {
                if (top == '!' || top == '*' || top == '/' || top == '%' || top == '+' || top == '-')
                    return true;
                else
                    return false;
            }
            // Priority is higher than'+' and'-'
            case '*': {
                if (top == '+' || top == '-')
                    return true;
                else
                    return false;
            }
            // Priority ratio'+'with'-'High
            case '/':
                if (top == '+' || top == '-')
                    return true;
                else
                    return false;
            case '%':
                if (top == '+' || top == '-')
                    return true;
                else
                    return false;

            case '+':
                return false;
            case '-':
                return false;
            // logical operators
            // and
            case '&':
                return false;
            // or
            case '|':
                return false;
            // xor
            case '^':
                return false;
            // nand
            case '_':
                return false;
            // nor
            case '<':
                return false;
            // xnor
            case '~':
                return false;
            // imply
            case '>':
                return false;
            // lowest priority
            case ')':
                return false;
            // terminator
            case '=':
                return false;
            default:
                break;
        }
        return true;
    }

}