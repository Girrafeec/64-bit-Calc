
package com.girrafeecstud.a64_bitcalcrelease;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import java.math.BigInteger;
import java.util.Random;

public class MainActivity extends Activity implements View.OnClickListener {

    // Добавить скролл текста, чтобы он уходил влево //DONE
    // Добавить перевод в системах счисления // DONE
    // Действия в 2 сс // DONE
    // Действия в 10 сс // DONE
    // Действия в 8 сс // DONE
    // Действия в 16 сс // DONE
    // Изменение знака // DONE
    // решить вопрос округления  // DONE
    // дизайн кнопок // DONE
    // почему не сохраняется состояние radiogroup и как менять состояние кнопки dataMode в MainActivity // DONE
    // закрасить на huawei topBAR //DONE
    // парсить входную строку на значения // DONE
    // разобраться с номером API //DONE
    // решить, что делать с status bar'ом //DONE
    // уменьшение числа при переходе от большего размера к меньшему // DONE
    // переделать парсер так, чтобы считывал скобки (знаки могут идти подряд) //DONE
    // решить вопрос со скобками (actions больше чем numbers и они могут идти подряд) //DONE
    // Менять входную строку при расчете результата (подряд идущие -- или +-. делать что-то с тем, что знак минус может идти после / или *) //DONE
    // сделать неактивными кнопки за счет серых цифр, а не полного затемнения //DONE
    // Сделать границы кнопок посветлее //DONE
    // не делит на отрицательные числа //DONE
    //добавить изменение выражения при расчете в случае, когда минус идет после левой скобки //DONE
    // добавить проверку на пустые скобки () (добавить как ошибку) //DONE
    // почему не считает ответ при числах от 1 до 9 // DONE
    // добавить проверку на количестве введенных левых скобок для того, чтобы не давать вводить правую //DONE
    // при переводе из одного режима данных в другой не всегда преобразоывается правильно (-127 в byte переводится в -127 в word, а должно быть 129) //DONE
    // изменение знака числа после перевода из одного режима данных в другой //DONE
    // позволить вводить первую скобку первым знаком //DONE
    // добавить проверку на то, что после левой скобки из знаков может идти только - //DONE
    // решить баг при смене режима (получается отрицательное число и тогда проваливается проверка на ввод) //DONE
    // модификация выражения во время вычисления, чтобы работало вычисление после знака в самом конце //DONE
    // модификация выражения во время вычисления, чтобы добавлялись скобки по максимуму в конце, если в выражении есть незакрытые //DONE
    // после скобки может идти только знак //DONE
    // решить проблему индекса после удаления символов //DONE
    // сделать что-то со сдвигом первых кнопок //DONE
    // ошибка при переводе выражения со скобками в другой размер данных //DONE
    // операция and и or и xor //DONE
    // операция nand, nor //DONE
    // операция not //DONE
    // переписать функции для того, чтобы до числа или выражения мог идти знак ! (перед скобкой или отрицательным числом) //DONE
    // удаление знака not в случае, если мы пытаемся его добавить к числу или выражению, где он уже есть //DONE
    // сделать что-то в случае удаления части числа или выражения со знаком not (вариант - удалить // DONE
    // был баг при удалении символов и когда рядом был оператор not //DONE
    // сохранять состояние после переворота (выбранный режим сс, размера данных и input output) //DONE
    // Смена знака последнего числа если перед ним стоит оператор not (дополнить функцию lastnumber) //DONE
    // рандомайзер //DONE
    // менять знак последнего выражения -(65+9) (65+9) (с учетом того, чтоп еред скобками может быть NOT) //DONE
    // отрицательные числа //DONE
    // размер word, byte, dword, qword //DONE
    // поставить setlistanimator null для всех кнопок во всех экранах //DONE
    // неправильно считает выражение, например -!-!63/98 - не считает все что в числителе одним числом (добавлять скобки? к NOT) //DONE
    // автоматически считать после каждого ввода //DONE
    // перевернутый режим //DONE
    // Делать кнопки активными или неактивными (знаки и скобки) //DONE
    // изменить кнопки (умножение, деление, удаление символа) //DONE
    // экран под планшеты //DONE
    // после нажатия equalButton NOT sign также работает как reverse //DONE
    // ошибка при поиске последнего выражения (когда сначала ищем скобки все правые) //DONE
    // разделение числа на фрагменты (3 в 8 и 10сс; 4 в 2 и 16 сс) //DONE
    // заменить знаки деления, умножения и логические на экране пользователя и кнопках (внутри одиночные знаки, а на отображении слова and И тд) //DONE
    // кнопка clear CE и C //DONE
    // размер input и output на экранах с size 5.0? //DONE


    // Косметика

    // Функционал

    private RadioGroup notationMode, dataModeRadioGroup;

    private Button dataMode;

    private Button aHex, bHex, cHex, dHex, eHex, fHex;
    private Button oneNum, twoNum, threeNum, fourNum, fiveNum, sixNum, sevenNum, eightNum, nineNum, zeroNum, point;
    private Button clearAll, changeSign;
    private Button plusButton, minusButton, multButton, divideButton, modButton, equalButton;
    private Button leftBracket, rightBracket;
    private Button logicalAnd, logicalOr, logicalNand, logicalXor, logicalNor, logicalNot, logicalXnor, logicalImply;
    private Button randomNumber;
    private ImageButton deleteLastSymbol;

    private EditText output, input;

    private HorizontalScrollView inputScroll, outputScroll;

    private Dialog dataModeDialog;

    private String inputUserStr = "", inputEngineStr = "", outputStr = "";
    private String previousNotationMode = "", currentNotationMode = "", previousNotationModeForChangingState = "";
    private String previousDataModeBinaryInputStr = "", previousDataModeBinaryOutputStr = "";

    private static int dataModeRadioButtonFlag; // flag for remembering what data mode is chosen

    private static int inputCharacterIndex = 0; // iterator for parsing input string and control data size

    private static boolean equalHasClicked = false; // boolean flag to chek if the equals button has clicked

    private ValueParser parser = new ValueParser();
    private CalcEngine engine = new CalcEngine();
    private DataSizeModificator dataModificator = new DataSizeModificator();
    private IODataChanger designIO = new IODataChanger();

    @Override
    public void onClick(View view) {

        inputEngineStr = input.getText().toString();
        inputEngineStr = designIO.designInputForEngine(inputEngineStr);

        char c = ' ', charAtIndexBeforeLast = ' ';
        if (!inputEngineStr.isEmpty())
            c = inputEngineStr.charAt(inputEngineStr.length()-1); // char for future check if last character is an action

        if (inputEngineStr.isEmpty())
            inputCharacterIndex = 0;

        Log.i("index before button =", String.valueOf(inputCharacterIndex));

        switch (view.getId()){
            case R.id.oneBtn:
                addDigitToTheEndOfInput("1");
                break;
            case R.id.twoBtn:
                addDigitToTheEndOfInput("2");
                break;
            case R.id.threeBtn:
                addDigitToTheEndOfInput("3");
                break;
            case R.id.fourBtn:
                addDigitToTheEndOfInput("4");
                break;
            case R.id.fiveBtn:
                addDigitToTheEndOfInput("5");
                break;
            case R.id.sixBtn:
                addDigitToTheEndOfInput("6");
                break;
            case R.id.sevenBtn:
                addDigitToTheEndOfInput("7");
                break;
            case R.id.eightBtn:
                addDigitToTheEndOfInput("8");
                break;
            case R.id.nineBtn:
                addDigitToTheEndOfInput("9");
                break;
            case R.id.zeroBtn:
                addDigitToTheEndOfInput("0");
                break;
            case R.id.aBtn:
                addDigitToTheEndOfInput("A");
                break;
            case R.id.bBtn:
                addDigitToTheEndOfInput("B");
                break;
            case R.id.cBtn:
                addDigitToTheEndOfInput("C");
                break;
            case R.id.dBtn:
                addDigitToTheEndOfInput("D");
                break;
            case R.id.eBtn:
                addDigitToTheEndOfInput("E");
                break;
            case R.id.fBtn:
                addDigitToTheEndOfInput("F");
                break;
            case R.id.leftBracketBtn:
                if (equalHasClicked){
                    inputEngineStr = "0";
                    output.setText("");
                    equalHasClicked = false;
                }

                inputEngineStr = designIO.removeZeroFromInput(inputEngineStr);
                input.setText(inputEngineStr);
                if (possibleToPasteLeftBracket())
                    inputEngineStr += "(";
                input.setText(inputEngineStr);
                clearAll.setText("ce");
                break;
            case R.id.rightBracketBtn:
                if(possibleToPasteRightBracket())
                    inputEngineStr += ")";
                input.setText(inputEngineStr);
                clearAll.setText("ce");
                break;
            case R.id.plusBtn:
                addArithmemeticalOrLogicSign("+");
                break;
            case R.id.miplusBtn:
                addArithmemeticalOrLogicSign("-");
                break;
            case R.id.multipleBtn:
                addArithmemeticalOrLogicSign("*");
                break;
            case R.id.divisionBtn:
            addArithmemeticalOrLogicSign("/");
                break;
            case R.id.modBtn:
            addArithmemeticalOrLogicSign("%");
                break;
            case R.id.equalBtn:
                equalsOnClick();
                equalHasClicked = true;
                break;
            case R.id.delSymbolBtn:
                delSymbolBtnActions();
                break;
            case R.id.clearBtn:
                inputCharacterIndex = 0;
                clearIO();
                break;
            case R.id.reverseSignBtn:
                changeNumberSign();
                break;
            case R.id.dataSizeModeBtn:
                changeDataMode();
                break;
            case R.id.logicalAndBtn:
                addArithmemeticalOrLogicSign("&");
                break;
            case R.id.logicalOrBtn:
                addArithmemeticalOrLogicSign("|");
                break;
            case R.id.logicalNotBtn:
                // clear input and output if we have an error
              /*  if (errorIsOccured()){
                    inputEngineStr = "0";
                    input.setText(inputEngineStr);
                    output.setText("");
                    inputCharacterIndex = 0;
                }*/

                if (equalHasClicked){
                    if (errorIsOccured())
                        output.setText("0");
                    inputEngineStr = output.getText().toString();
                    output.setText("");
                    equalHasClicked = false;
                }
                inputEngineStr = designIO.designInputForEngine(inputEngineStr);
                inputEngineStr = designIO.addLogicNotToExpression(inputEngineStr);
                input.setText(inputEngineStr);
                clearAll.setText("ce");
                break;
            case R.id.logicalNandBtn:
                addArithmemeticalOrLogicSign("_");
                break;
            case R.id.logicalXorBtn:
                addArithmemeticalOrLogicSign("^");
                break;
            case R.id.logicalNorBtn:
                addArithmemeticalOrLogicSign("<");
                break;
            case R.id.logicalImplyBtn:
                addArithmemeticalOrLogicSign(">");
                break;
            case R.id.logicalXnorBtn:
                addArithmemeticalOrLogicSign("~");
                break;
            case R.id.randomizeBtn:
                // clear input and output if we have an error before switching to another notation mode
                if (errorIsOccured()){
                    inputEngineStr = "0";
                    input.setText(inputEngineStr);
                    output.setText("");
                    inputCharacterIndex = 0;
                }

                if (equalHasClicked){
                    inputEngineStr = "0";
                    output.setText("");
                    equalHasClicked = false;
                }
                inputEngineStr = designIO.removeZeroFromInput(inputEngineStr);
                inputEngineStr += addRandomNumber();
                input.setText(inputEngineStr);
                clearAll.setText("ce");
                break;
            default:
                break;
        }

        c = inputEngineStr.charAt(inputEngineStr.length()-1);
        if (parser.isAction(c)) {
            inputCharacterIndex = inputEngineStr.length();
        }

        if (inputEngineStr.length() > 1)
            charAtIndexBeforeLast = inputEngineStr.charAt(inputEngineStr.length()-2);

        if ( c == '-' && (((charAtIndexBeforeLast != ')' && parser.isAction(charAtIndexBeforeLast))
                || inputEngineStr.lastIndexOf(c) == 0))) {
            inputCharacterIndex--;
        }

        Log.i("index after button =", String.valueOf(inputCharacterIndex));

        inputUserStr = designIO.designInputForUser(currentNotationMode, inputEngineStr);

        input.setText(inputUserStr);

        equalsOnClick();

        changeButtonsStatus();

        ioScroll();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //hide status bar when landscape
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataModeRadioButtonFlag = 0; // start with qword always

        initText();
        initButtons();
        initOtherElements();
        changeButtonsStatus();
        textForDifScreenSizes();

        oneNum.setOnClickListener(this);
        twoNum.setOnClickListener(this);
        threeNum.setOnClickListener(this);
        fourNum.setOnClickListener(this);
        fiveNum.setOnClickListener(this);
        sixNum.setOnClickListener(this);
        sevenNum.setOnClickListener(this);
        eightNum.setOnClickListener(this);
        nineNum.setOnClickListener(this);
        zeroNum.setOnClickListener(this);

        aHex.setOnClickListener(this);
        bHex.setOnClickListener(this);
        cHex.setOnClickListener(this);
        dHex.setOnClickListener(this);
        eHex.setOnClickListener(this);
        fHex.setOnClickListener(this);

        deleteLastSymbol.setOnClickListener(this);
        clearAll.setOnClickListener(this);
        changeSign.setOnClickListener(this);
        dataMode.setOnClickListener(this);

        leftBracket.setOnClickListener(this);
        rightBracket.setOnClickListener(this);
        plusButton.setOnClickListener(this);
        minusButton.setOnClickListener(this);
        multButton.setOnClickListener(this);
        divideButton.setOnClickListener(this);
        modButton.setOnClickListener(this);
        equalButton.setOnClickListener(this);

        // use these metods only when landscape
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            logicalNand.setOnClickListener(this);
            logicalOr.setOnClickListener(this);
            logicalXor.setOnClickListener(this);
            logicalNor.setOnClickListener(this);
            logicalNot.setOnClickListener(this);
            logicalImply.setOnClickListener(this);
            logicalXnor.setOnClickListener(this);
            logicalAnd.setOnClickListener(this);

            randomNumber.setOnClickListener(this);
        }

        int radioButtonCheckedId = notationMode.getCheckedRadioButtonId();
        switch(radioButtonCheckedId){
            case R.id.binaryRadioBtn:
                currentNotationMode = "Binary";
                numberButtonsStatus();
                break;
            case R.id.octalRadioBtn:
                currentNotationMode = "Octal";
                numberButtonsStatus();
                break;
            case R.id.decimalRadioBtn:
                currentNotationMode = "Decimal";
                numberButtonsStatus();
                break;
            case R.id.hexidecimalRadioBtn:
                currentNotationMode = "Hexidecimal";
                numberButtonsStatus();
                break;
            default:
                break;
        }

        notationMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                // clear input and output if we have an error before switching to another notation mode
                if (errorIsOccured()){
                    input.setText("0");
                    output.setText("");
                    inputCharacterIndex = 0;
                }

                previousNotationMode = currentNotationMode;

                switch (i){
                    case R.id.binaryRadioBtn:
                        currentNotationMode = "Binary";
                        break;

                    case R.id.octalRadioBtn:
                        currentNotationMode = "Octal";
                        break;

                    case R.id.decimalRadioBtn:
                        currentNotationMode = "Decimal";
                        break;

                    case R.id.hexidecimalRadioBtn:
                        currentNotationMode = "Hexidecimal";
                        break;

                    default:
                        break;
                }

                numberButtonsStatus(); // make some number buttons enabled and disabled

                if (previousNotationMode.equals(currentNotationMode))
                    previousNotationMode = previousNotationModeForChangingState;
                else
                    changeIO();

                inputCharacterIndex = parser.findLastArithmeticalOperator(inputEngineStr) + 1;
                changeButtonsStatus();
            }
        });

        if (savedInstanceState != null) {
            dataModeRadioButtonFlag = savedInstanceState.getInt("dataModeFlag");
            inputCharacterIndex = savedInstanceState.getInt("inputDynamicIndex");
            currentNotationMode = savedInstanceState.getString("curNotMode");
            previousNotationMode = savedInstanceState.getString("prevNotMode");
            previousNotationModeForChangingState  = savedInstanceState.getString("prevNotMode");
            dataMode.setText(savedInstanceState.getString("dataModeButtonText"));
            clearAll.setText(savedInstanceState.getString("clearButtonText"));
            inputEngineStr = savedInstanceState.getString("inputStr");
            input.setText(inputEngineStr);
            outputStr = savedInstanceState.getString("outputStr");
            output.setText(outputStr);
            changeButtonsStatus();
            textForDifScreenSizes();
        }
    }

    // Initialize Buttons
    private void initButtons(){
        aHex = findViewById(R.id.aBtn);
        bHex = findViewById(R.id.bBtn);
        cHex = findViewById(R.id.cBtn);
        dHex = findViewById(R.id.dBtn);
        eHex = findViewById(R.id.eBtn);
        fHex = findViewById(R.id.fBtn);

        oneNum = findViewById(R.id.oneBtn);
        twoNum = findViewById(R.id.twoBtn);
        threeNum = findViewById(R.id.threeBtn);
        fourNum = findViewById(R.id.fourBtn);
        fiveNum = findViewById(R.id.fiveBtn);
        sixNum = findViewById(R.id.sixBtn);
        sevenNum = findViewById(R.id.sevenBtn);
        eightNum = findViewById(R.id.eightBtn);
        nineNum = findViewById(R.id.nineBtn);
        zeroNum = findViewById(R.id.zeroBtn);

        deleteLastSymbol = findViewById(R.id.delSymbolBtn);
        clearAll = findViewById(R.id.clearBtn);
        changeSign = findViewById(R.id.reverseSignBtn);

        plusButton = findViewById(R.id.plusBtn);
        minusButton=findViewById(R.id.miplusBtn);
        multButton = findViewById(R.id.multipleBtn);
        divideButton = findViewById(R.id.divisionBtn);
        modButton = findViewById(R.id.modBtn);
        equalButton = findViewById(R.id.equalBtn);
        leftBracket = findViewById(R.id.leftBracketBtn);
        rightBracket = findViewById(R.id.rightBracketBtn);

        logicalAnd = findViewById(R.id.logicalAndBtn);
        logicalOr = findViewById(R.id.logicalOrBtn);
        logicalNand = findViewById(R.id.logicalNandBtn);
        logicalXor = findViewById(R.id.logicalXorBtn);
        logicalNor = findViewById(R.id.logicalNorBtn);
        logicalNot = findViewById(R.id.logicalNotBtn);
        logicalXnor = findViewById(R.id.logicalXnorBtn);
        logicalImply = findViewById(R.id.logicalImplyBtn);

        randomNumber = findViewById(R.id.randomizeBtn);

        dataMode = findViewById(R.id.dataSizeModeBtn);
    }

    // Initialize TextView
    private void initText(){
        output = findViewById(R.id.outputTxt);
        input = findViewById(R.id.inputTxt);


        input.setRawInputType(InputType.TYPE_NULL);
        input.setFocusable(true);

        output.setRawInputType(InputType.TYPE_NULL);
        output.setFocusable(true);
    }

    // Initialize other elements apart from buttons and text views
    private void initOtherElements(){
        notationMode = findViewById(R.id.notationModeRadioGroup);
        inputScroll = findViewById(R.id.inputScrollView);
        outputScroll = findViewById(R.id.outputScrollView);
        dataModeDialog = new Dialog(this);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("dataModeFlag", dataModeRadioButtonFlag);
        outState.putInt("inputDynamicIndex", inputCharacterIndex);
        outState.putString("curNotMode", currentNotationMode);
        outState.putString("prevNotMode", previousNotationMode);
        outState.putString("dataModeButtonText", dataMode.getText().toString());
        outState.putString("clearButtonText", clearAll.getText().toString());

        inputEngineStr = input.getText().toString();
        outState.putString("inputStr", inputEngineStr);
        outputStr = output.getText().toString();
        outState.putString("outputStr", outputStr);
    }

    // procedure to add number to the end of the input string
    private void addDigitToTheEndOfInput(String value){

        String testInput = "";

        if (equalHasClicked) {
            inputEngineStr = "0";
            outputStr = "";
            output.setText(outputStr);
            equalHasClicked = false;
            inputCharacterIndex = 0;
        }

        inputEngineStr = designIO.removeZeroFromInput(inputEngineStr);
        testInput = inputEngineStr.substring(inputCharacterIndex, inputEngineStr.length());

        // we do not add digit if the last character is zero (avoid numbers like 00000 or 00000005)
        if (testInput.equals("0"))
            value = "";

        testInput += value;

        if (currentNotationMode != "Decimal")
            testInput = designIO.convert(dataMode.getText().toString(), currentNotationMode, "Decimal", testInput);

        Log.i("dd", testInput);

        if (possibleToPasteNumber())
            if (dataModificator.lowerThanBorder(dataMode.getText().toString(), testInput))
                inputEngineStr += value;
            else
                testInput = designIO.deleteLastCharacter(testInput);
        input.setText(inputEngineStr);

        clearAll.setText("ce");
    }

    // procedure to add sign to the end of the input string
    private void addArithmemeticalOrLogicSign(String sign){

        if (equalHasClicked) {

            if (errorIsOccured())
                output.setText("0");

            outputStr = output.getText().toString();
            outputStr = designIO.designInputForEngine(outputStr);

            inputEngineStr = outputStr;
            outputStr = "";
            output.setText(outputStr);

            equalHasClicked = false;
            inputCharacterIndex = 0;
        }

        char c = inputEngineStr.charAt(inputEngineStr.length()-1);

        if (parser.isAction(c) && c != ')')
            inputEngineStr = designIO.deleteLastCharacter(inputEngineStr);
        if (c != '(')
            inputEngineStr += sign;
        input.setText(inputEngineStr);

        clearAll.setText("ce");
    }

    // procedure to delete last symbol from input
    private void delSymbolBtnActions(){
        char c = inputEngineStr.charAt(inputEngineStr.length()-1);
        String ifLastNumberOrExpressionHadNotSign = designIO.delNotNumberOrExpression(inputEngineStr);

        if (inputEngineStr.equals(ifLastNumberOrExpressionHadNotSign))
            inputEngineStr = designIO.deleteLastCharacter(inputEngineStr);
        else
            inputEngineStr = ifLastNumberOrExpressionHadNotSign;

        if (inputEngineStr.equals(""))
            inputEngineStr = "0";
        input.setText(inputEngineStr);

        if (parser.isAction(c))
            inputCharacterIndex = parser.findLastArithmeticalOperator(inputEngineStr) + 1;
    }

    // Procedure to clear input and output TextView
    private void clearIO(){

        if (equalHasClicked){
            inputEngineStr = "0";
            outputStr = "";
            input.setText(inputEngineStr);
            output.setText(outputStr);
            equalHasClicked = false;
            clearAll.setText("c");
            inputCharacterIndex = 0;
            return;
        }

        if (clearAll.getText().toString().equals("c")){
            inputEngineStr = "0";
            outputStr = "";
            input.setText(inputEngineStr);
            output.setText(outputStr);
            inputCharacterIndex = 0;
            return;
        }

        if (clearAll.getText().toString().equals("ce")){

            String partForClear = "";
            int strForClearLength = 0;

            if (parser.isAction(inputEngineStr.charAt(inputEngineStr.length()-1)) && inputEngineStr.charAt(inputEngineStr.length()-1) != ')')
                inputEngineStr = designIO.deleteLastCharacter(inputEngineStr);

            if (inputEngineStr.isEmpty()) {
                inputEngineStr = "0";
                input.setText(inputEngineStr);
                clearAll.setText("c");
                return;
            }

            if (inputEngineStr.charAt(inputEngineStr.length()-1) == ')')
                partForClear = parser.getLastExpression(inputEngineStr);
            else
                partForClear = parser.getLastNumber(inputEngineStr);

            strForClearLength = partForClear.length();

            for (int i=0; i < strForClearLength; i++)
                inputEngineStr = designIO.deleteLastCharacter(inputEngineStr);

            if (inputEngineStr.isEmpty())
                inputEngineStr = "0";

            input.setText(inputEngineStr);

            inputCharacterIndex = parser.findLastArithmeticalOperator(inputEngineStr) + 1;

            clearAll.setText("c");
            return;
        }
    }

    // Calculate the result of input string
    private void equalsOnClick(){

        String decimalValue = input.getText().toString(); // count in decimal notation
        decimalValue = designIO.designInputForEngine(decimalValue);

        if (!currentNotationMode.equals("Decimal"))
            decimalValue = designIO.convert(dataMode.getText().toString(), currentNotationMode, "Decimal", decimalValue);
        //else
            //decimalValue = input.getText().toString();

        outputStr = String.valueOf(engine.caculate(decimalValue, dataMode.getText().toString()));

        if (outputStr.equals("null"))
            outputStr = "Error";

        output.setText(outputStr);

        // if the calculation result is not null
        if (!errorIsOccured())
            outputStr = dataModificator.ifAnswerNegative(dataMode.getText().toString(), outputStr);

        if (!currentNotationMode.equals("Decimal") && !errorIsOccured())
                outputStr = designIO.convert(dataMode.getText().toString(),"Decimal", currentNotationMode, outputStr);

        if (!errorIsOccured())
            outputStr = designIO.designInputForUser(currentNotationMode, outputStr);

        output.setText(outputStr);

        ioScroll();
    }

    // Procedure for dynamiclly scrolling input and output TextView
    // after converting or adding new symbols
    private void ioScroll(){

        inputScroll.postDelayed(new Runnable() {
            @Override
            public void run() {
                inputScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 0L);

        outputScroll.postDelayed(new Runnable() {
            @Override
            public void run() {
                outputScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 0L);
    }

    // Procedure to convert value in input and output Edit text to another notation
    private void changeIO(){

        inputEngineStr = input.getText().toString();
        outputStr = output.getText().toString();

        inputEngineStr = designIO.designInputForEngine(inputEngineStr);
        outputStr = designIO.designInputForEngine(outputStr);

        inputEngineStr = designIO.convert(dataMode.getText().toString(), previousNotationMode, currentNotationMode, inputEngineStr);
        inputUserStr = designIO.designInputForUser(currentNotationMode, inputEngineStr);
        input.setText(inputUserStr);
        outputStr = designIO.convert(dataMode.getText().toString(), previousNotationMode, currentNotationMode, outputStr);
        outputStr = designIO.designInputForUser(currentNotationMode, outputStr);
        output.setText(outputStr);

        ioScroll();
    }

    // procedure runs dialog ahere we can change data size mode
    private void changeDataMode(){

        dataModeDialog.setContentView(R.layout.data_mode_activity);

        dataModeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dataModeRadioGroup = dataModeDialog.findViewById(R.id.dataModeRadioGroup);

        switch (dataModeRadioButtonFlag){
            case 1:
                dataModeRadioGroup.check(R.id.byteModeRadioBtn);
                break;
            case 2:
                dataModeRadioGroup.check(R.id.wordModeRadioBtn);
                break;
            case 3:
                dataModeRadioGroup.check(R.id.dwordModeRadioBtn);
                break;
            case 4:
                dataModeRadioGroup.check(R.id.qwordModeRadioBtn);
                break;
            default:
                break;
        }

        dataModeDialog.show();

        dataModeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                // if error we clear input and output
                if (errorIsOccured()){
                    inputEngineStr = "0";
                    input.setText(inputEngineStr);
                    output.setText("");
                }

                previousDataModeBinaryInputStr = input.getText().toString();
                previousDataModeBinaryOutputStr = output.getText().toString();

                previousDataModeBinaryInputStr = designIO.designInputForEngine(previousDataModeBinaryInputStr);
                previousDataModeBinaryOutputStr = designIO.designInputForEngine(previousDataModeBinaryOutputStr);

                if (!currentNotationMode.equals("Binary")) {
                    previousDataModeBinaryInputStr = designIO.convert(dataMode.getText().toString(),
                            currentNotationMode, "Binary", previousDataModeBinaryInputStr);
                    previousDataModeBinaryOutputStr = designIO.convert(dataMode.getText().toString(),
                            currentNotationMode, "Binary", previousDataModeBinaryOutputStr);
                }

                switch (i) {
                    case R.id.byteModeRadioBtn:
                        dataMode.setText("BYTE");
                        dataModeRadioButtonFlag = 1;
                        break;
                    case R.id.wordModeRadioBtn:
                        dataMode.setText("WORD");
                        dataModeRadioButtonFlag = 2;
                        break;
                    case R.id.dwordModeRadioBtn:
                        dataMode.setText("DWORD");
                        dataModeRadioButtonFlag = 3;
                        break;
                    case R.id.qwordModeRadioBtn:
                        dataMode.setText("QWORD");
                        dataModeRadioButtonFlag = 4;
                        break;
                    default:
                        break;
                }

                inputEngineStr = designIO.decreaseInputBits(dataMode.getText().toString(), previousDataModeBinaryInputStr, currentNotationMode);
                outputStr = designIO.decreaseOutputBits(dataMode.getText().toString(), previousDataModeBinaryOutputStr, currentNotationMode);

                inputUserStr = designIO.designInputForUser(currentNotationMode, inputEngineStr);
                outputStr = designIO.designInputForUser(currentNotationMode, outputStr);

                input.setText(inputUserStr);
                output.setText(outputStr);

                if (!input.getText().toString().equals("0"))
                    equalsOnClick();

                inputCharacterIndex = parser.findLastArithmeticalOperator(inputEngineStr) + 1;
                changeButtonsStatus();
            }
        });

    }

    //procedure to reverse sign
    private void changeNumberSign(){

        if (!output.getText().toString().isEmpty() || !input.getText().toString().isEmpty()){

            // if equal hes clicked we clear input and add out output result to input and then negate it
            if (equalHasClicked){
                if (errorIsOccured())
                    output.setText("0");

                outputStr = output.getText().toString();
                outputStr = designIO.designInputForEngine(outputStr);
                outputStr = designIO.changeOutputNumberSign(dataMode.getText().toString(), currentNotationMode, outputStr);
                inputEngineStr = outputStr;
                input.setText(inputEngineStr);
                inputCharacterIndex = 0;
                output.setText("");
                equalHasClicked = false;
                return;
            }
            inputEngineStr = input.getText().toString();
            inputEngineStr = designIO.designInputForEngine(inputEngineStr);
            inputEngineStr = designIO.changeInputNumberSign(dataMode.getText().toString(), currentNotationMode, inputEngineStr);
            input.setText(inputEngineStr);
        }

    }

    // function to check if there is an error in expression
    private boolean errorIsOccured(){

        String potentialError = output.getText().toString();

        if (potentialError.equals("Cannot divide by zero") || potentialError.equals("Error"))
            return true;
        else
            return false;
    }

    // function to check if it is possible to input right bracket
    private boolean possibleToPasteRightBracket(){

        String str = input.getText().toString();

        str = designIO.designInputForEngine(str);

        int leftBracketsQuality = 0, rightBracketsQuality = 0;

        for (int i = 0; i < str.length(); i++){

            if (str.charAt(i) == '(')
                leftBracketsQuality++;
            else if (str.charAt(i) == ')')
                rightBracketsQuality++;

        }

        if ((leftBracketsQuality > rightBracketsQuality) && (str.charAt(str.length()-1) == ')'
                || (!parser.isAction(str.charAt(str.length()-1)))))
            return true;
        else
            return false;
    }

    // function to check if it is possible to input left bracket
    private boolean possibleToPasteLeftBracket(){

        String str = input.getText().toString();

        str = designIO.designInputForEngine(str);

        char c = ' ';

        if (!str.isEmpty())
            c = str.charAt(str.length()-1);

        if (c == '+' || c == '-' || c == '*' || c == '/' || c == '%' || c == '(' || c == '_' || c == '<'
                || c == '>' || c == '~' || c == '&' || c == '|' || c == '!' || c == '^'
                || str.isEmpty() || str.equals("0") || equalHasClicked == true)
            return true;
        else
            return false;

    }

    private boolean possibleToPasteNumber(){

        String str = input.getText().toString();

        str = designIO.designInputForEngine(str);

        if (str.charAt(str.length()-1) == ')')
            return false;
        else
            return true;

    }

    // function to add random number to input
    private String addRandomNumber(){

        int maxBits = 0; // max bit in number
        BigInteger randNumber = new BigInteger(String.valueOf(0));

        switch (dataMode.getText().toString()) {
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

        Random random = new Random();
        randNumber = new BigInteger(maxBits, random);

        randNumber = new BigInteger(String.valueOf(dataModificator.ifAnswerNegative(dataMode.getText().toString(), randNumber.toString())));

        String result = randNumber.toString();

        if (!currentNotationMode.equals("Decimal"))
            result = designIO.convert(dataMode.getText().toString(), "Decimal", currentNotationMode, randNumber.toString());

        return result;
    }

    private void changeButtonsStatus(){

        // right bracket status
        if (!possibleToPasteRightBracket())
            rightBracket.setEnabled(false);
        else
            rightBracket.setEnabled(true);

        // left bracket status
        if (!possibleToPasteLeftBracket())
            leftBracket.setEnabled(false);
        else
            leftBracket.setEnabled(true);

        numberButtonsStatus();
        regularActionButtonsStatus();
        reverseSignButtonStatus();

        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            logicActionButtonsStatus();
            logicNotButtonStatus();
            randButtonStatus();
        }

    }

    // procedure to change lofic buttons status
    private void logicActionButtonsStatus() {

        String str = input.getText().toString();
        str = designIO.designInputForEngine(str);

        if (str.charAt(str.length()-1) == '('){
            logicalOr.setEnabled(false);
            logicalXor.setEnabled(false);
            logicalNor.setEnabled(false);
            logicalNand.setEnabled(false);
            logicalXnor.setEnabled(false);
            logicalImply.setEnabled(false);
            logicalAnd.setEnabled(false);
            return;
        }
        logicalOr.setEnabled(true);
        logicalXor.setEnabled(true);
        logicalNor.setEnabled(true);
        logicalNand.setEnabled(true);
        logicalXnor.setEnabled(true);
        logicalImply.setEnabled(true);
        logicalAnd.setEnabled(true);
    }

    // procedure to change number buttons status
    private void numberButtonsStatus(){

        if (!possibleToPasteNumber()){
            zeroNum.setEnabled(false);
            oneNum.setEnabled(false);
            twoNum.setEnabled(false);
            threeNum.setEnabled(false);
            fourNum.setEnabled(false);
            fiveNum.setEnabled(false);
            sixNum.setEnabled(false);
            sevenNum.setEnabled(false);
            eightNum.setEnabled(false);
            nineNum.setEnabled(false);
            aHex.setEnabled(false);
            bHex.setEnabled(false);
            cHex.setEnabled(false);
            dHex.setEnabled(false);
            eHex.setEnabled(false);
            fHex.setEnabled(false);
        }
        else{
            if (currentNotationMode == "Binary"){
                zeroNum.setEnabled(true);
                oneNum.setEnabled(true);
                twoNum.setEnabled(false);
                threeNum.setEnabled(false);
                fourNum.setEnabled(false);
                fiveNum.setEnabled(false);
                sixNum.setEnabled(false);
                sevenNum.setEnabled(false);
                eightNum.setEnabled(false);
                nineNum.setEnabled(false);
                aHex.setEnabled(false);
                bHex.setEnabled(false);
                cHex.setEnabled(false);
                dHex.setEnabled(false);
                eHex.setEnabled(false);
                fHex.setEnabled(false);
                return;
            }

            if (currentNotationMode == "Octal"){
                zeroNum.setEnabled(true);
                oneNum.setEnabled(true);
                twoNum.setEnabled(true);
                threeNum.setEnabled(true);
                fourNum.setEnabled(true);
                fiveNum.setEnabled(true);
                sixNum.setEnabled(true);
                sevenNum.setEnabled(true);
                eightNum.setEnabled(false);
                nineNum.setEnabled(false);
                aHex.setEnabled(false);
                bHex.setEnabled(false);
                cHex.setEnabled(false);
                dHex.setEnabled(false);
                eHex.setEnabled(false);
                fHex.setEnabled(false);
                return;
            }

            if (currentNotationMode == "Decimal"){
                zeroNum.setEnabled(true);
                oneNum.setEnabled(true);
                twoNum.setEnabled(true);
                threeNum.setEnabled(true);
                fourNum.setEnabled(true);
                fiveNum.setEnabled(true);
                sixNum.setEnabled(true);
                sevenNum.setEnabled(true);
                eightNum.setEnabled(true);
                nineNum.setEnabled(true);
                aHex.setEnabled(false);
                bHex.setEnabled(false);
                cHex.setEnabled(false);
                dHex.setEnabled(false);
                eHex.setEnabled(false);
                fHex.setEnabled(false);
                return;
            }

            if (currentNotationMode == "Hexidecimal"){
                zeroNum.setEnabled(true);
                oneNum.setEnabled(true);
                twoNum.setEnabled(true);
                threeNum.setEnabled(true);
                fourNum.setEnabled(true);
                fiveNum.setEnabled(true);
                sixNum.setEnabled(true);
                sevenNum.setEnabled(true);
                eightNum.setEnabled(true);
                nineNum.setEnabled(true);
                aHex.setEnabled(true);
                bHex.setEnabled(true);
                cHex.setEnabled(true);
                dHex.setEnabled(true);
                eHex.setEnabled(true);
                fHex.setEnabled(true);
            }
        }
    }

    //procedure to change regular actions button status
    private void regularActionButtonsStatus(){

        String str = input.getText().toString();
        str = designIO.designInputForEngine(str);

        if (str.charAt(str.length()-1) == '('){
            plusButton.setEnabled(false);
            divideButton.setEnabled(false);
            modButton.setEnabled(false);
            multButton.setEnabled(false);
            minusButton.setEnabled(false);
            return;
        }
            plusButton.setEnabled(true);
            divideButton.setEnabled(true);
            modButton.setEnabled(true);
            multButton.setEnabled(true);
            minusButton.setEnabled(true);
    }

    // procedure to change rand button status
    private void randButtonStatus(){

        String temp = input.getText().toString();
        temp = designIO.designInputForEngine(temp);

        if (temp.isEmpty() || temp.equals("0") || equalHasClicked == true
                || errorIsOccured() || (parser.isAction(temp.charAt(temp.length()-1)) && temp.charAt(temp.length()-1) != ')')) {
            randomNumber.setEnabled(true);
            return;
        }
        randomNumber.setEnabled(false);
    }

    // procedure to change reverse sign button status
    private void reverseSignButtonStatus(){

        String temp = input.getText().toString();
        temp = designIO.designInputForEngine(temp);

        if ((parser.isAction(temp.charAt(temp.length()-1)) && temp.charAt(temp.length()-1) != ')') && equalHasClicked == false) {
            changeSign.setEnabled(false);
            return;
        }
        else if (equalHasClicked == true) {
            changeSign.setEnabled(true);
            return;
        }
        changeSign.setEnabled(true);
    }

    // procedure to change logic NOT button status
    private void logicNotButtonStatus(){
        String temp = input.getText().toString();

        temp = designIO.designInputForEngine(temp);

        if (parser.isAction(temp.charAt(temp.length()-1)) && temp.charAt(temp.length()-1) != ')' && equalHasClicked == false) {
            logicalNot.setEnabled(false);
            return;
        }
        else if (equalHasClicked == true) {
            logicalNot.setEnabled(true);
            return;
        }
        logicalNot.setEnabled(true);
    }

    // procedure to change input and output text size in different screen sizes
    private void textForDifScreenSizes(){

        int orientation = this.getResources().getConfiguration().orientation;

        //  consider a tablet to have at least a 6.5 inch screen
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);

        if (diagonalInches>=6.5){
            input.setTextSize(60);
            output.setTextSize(70);
        }else if (diagonalInches <= 5.85 && diagonalInches > 5.0){
            input.setTextSize(30);
            output.setTextSize(40);
        }else if (diagonalInches <= 5.0){
            // get screen resolution
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = size.x;
            int height = size.y;

            if (width < 1080 && height < 1920){

                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    input.setTextSize(20);
                    output.setTextSize(25);
                    return;
                }

                input.setTextSize(30);
                output.setTextSize(40);

                return;
            }

            input.setTextSize(30);
            output.setTextSize(40);
        }
    }
}