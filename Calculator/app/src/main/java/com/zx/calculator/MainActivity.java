package com.zx.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * <p>
 * 1.基本点击事件，点击按钮后改变TextView显示（这里使用一个自定义的控件显示正在输入的数字）
 * 2.判断计算结果是整数还是浮点数，确定显示文本
 * 3.判断是否出错，如果出错将显示Error文本，字体改为红色
 * 4.出错状态按任意按钮变为正常状态，字体改为黑色
 * 5.判断计算状态，是否已计算完成，两种状态按钮点击效果不同
 * 6.小数点输入状态，已有小数点时，不可再输入小数点
 * 7.判断数字正负，确定负号点击效果
 * 8.百分号的输入
 * </p>
 */
public class MainActivity extends AppCompatActivity {

    private TextView mTxtOld;//第一个文本，用于显示已存储的第一个数字
    private ShineTextView mTxtNew;//第二个文本，用于显示正在输入的数字
    private TextView mTxtOperator;//显示运算符的文本

    private boolean isError;//判断是否出错，默认为false，出错后将颜色改为红色
    private boolean isFinished = false;//判断是否计算完成，这里主要是为了在点击运算按钮时，确定是否继续计算

    private double firstNum;//输入的第一个数,当点击运算符后，如果继续点击运算符将结果作为第一个数
    private double secondNum;//输入的第二个数，如果不输入默认为0，除法和乘法的时候默认为1

    private Operation mOperation = Operation.NULL;

//    private double firstNumDouble;//输入的第一个数，有小数点时显示为浮点数
//    private double secondNumDouble;//输入的第二个数，有小数点时显示为浮点数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    //初始化视图
    private void init() {
        mTxtOld = (TextView) findViewById(R.id.txt_old);
        mTxtNew = (ShineTextView) findViewById(R.id.txt_new);
        mTxtOperator = (TextView) findViewById(R.id.txt_operator);
    }

    /**
     * 数字按钮的点击事件 ：
     * 1,2,3,4,5,6,7,8,9,0：根据运算状态，确定输入后的显示
     * 小数点：判断是否已经含有小数点，如果已经有了，就不能再输入
     *
     * @param view 按钮
     */
    public void onNumClick(View view) {
        if (isError) isError = false;
        Button btn = (Button) view;
        String text = mTxtNew.getText();
        if (view.getId() == R.id.btn_point) {
            if (null == text || text.equals("")) {
                text = "0";
            }
            if (!text.contains(".")) {
                text = text + ".";
            }
        } else {
            if (text.length() == 1) {
                String firstChar = String.valueOf(text.charAt(0));
                if (firstChar.equals("0")) {
                    text = String.valueOf(btn.getText());
                } else {
                    text = text + String.valueOf(btn.getText());
                }
            } else {
                text = text + String.valueOf(btn.getText());
            }
        }
        mTxtNew.setText(text);
    }

    /**
     * 运算符点击事件：
     * 加号：加法运算
     * 减号：减法运算
     * 乘号：乘法运算
     * 除号：除数为0，运算状态改为异常
     * 继续按下时，继续保留第二个数，并将前一次的结果作为第一个数继续运算
     *
     * @param view 按钮
     */
    public void onOperatorClick(View view) {
        if (isError) return;

        Button btn = (Button) view;
        String numStr = Util.format(mTxtNew.getText());
        if (firstNum==0&&mOperation == Operation.NULL) {
            firstNum = Double.parseDouble(numStr);
        }
        switch (view.getId()) {
            case R.id.btn_add://加
                mOperation = Operation.ADD;
                break;
            case R.id.btn_multiplication://乘
                mOperation = Operation.MULTIPLICATION;
                break;
            case R.id.btn_subtraction://减
                mOperation = Operation.SUBTRACTION;
                break;
            case R.id.btn_division://除
                mOperation = Operation.DIVISION;
                break;

        }
        if (Util.isInt(String.valueOf(firstNum))){
            mTxtOld.setText(String.valueOf((int)firstNum));
        }else{
            mTxtOld.setText(String.valueOf(firstNum));
        }
        mTxtOperator.setText(btn.getText());
        mTxtNew.setText(null);
    }

    /**
     * 等号：按下运算状态完成，并且显示结果，此时其他运算符继续按下时，不再进行运算
     *
     * @param view 按钮
     */
    public void onEqualsClick(View view) {
        if (isError) return;
        if (firstNum == 0) return;
        double tempNum;
        String text = Util.format(mTxtNew.getText());
        //判断第二个数字是否输入过
        if ("".equals(text)) {
            if (mOperation == Operation.ADD || mOperation == Operation.SUBTRACTION) {
                secondNum = 0;
            } else {
                secondNum = 1;
            }
        } else {
            secondNum = Double.parseDouble(text);
        }
        //计算部分
        switch (mOperation) {
            case ADD://加
                tempNum = firstNum + secondNum;
                break;
            case SUBTRACTION://減
                tempNum = firstNum - secondNum;
                break;
            case MULTIPLICATION://乘
                tempNum = firstNum * secondNum;
                break;
            case DIVISION://除
                if (secondNum == 0) {
                    isError = true;
                    tempNum=0;
                } else
                    tempNum = firstNum / secondNum;
                break;
            default:
                tempNum = 0;
                break;
        }
        String numStr = String.valueOf(tempNum);
        if (Util.isInt(numStr)) {
            numStr = String.valueOf((int) tempNum);
        }
        if (isError){
            mTxtNew.showError();
        }else {
            mTxtOld.setText(numStr);
        }

        mTxtOperator.setText(((Button) view).getText());
        firstNum = tempNum;
        mTxtNew.setText(null);
        mOperation=Operation.NULL;
        isFinished=true;
    }


    /**
     * 其他按钮：
     * % ，百分号，按下相当于乘以0.01
     * ±，负号，如果显示为正数，按下变为负数，如果显示为负数，按下变为正数
     * ← ：删除一个字符，字符长度为0时没有作用，
     * AC(清除) ：清除所有文本显示，以及重置所有运算状态
     *
     * @param view 按钮
     */
    public void onClick(View view) {
        String text = Util.format(mTxtNew.getText());
        switch (view.getId()) {
            case R.id.btn_delete://删除
                if (text.length() > 0) {
                    text = text.substring(0, text.length() - 1);
                }
                break;
            case R.id.btn_clear://清除
                isError = false;
                isFinished = false;
                mOperation = Operation.NULL;
                text = "";
                firstNum=0;
                mTxtOperator.setText("");
                mTxtOld.setText("");
                break;
            case R.id.btn_minus:// 负号
                if (TextUtils.isEmpty(text)) {
                    text = "-";
                } else if (text.equals("-")) {
                    text = "";
                } else {
                    double tempNum = Double.parseDouble(text);
                    if (Util.isInt(text)) {
                        text = String.valueOf((int) (tempNum * -1));
                    } else {
                        text = String.valueOf(tempNum * -1);
                    }
                }
                break;
            case R.id.btn_percent:
                if (!TextUtils.isEmpty(text) && !text.equals("-")) {
                    double tempNum = Double.parseDouble(text);
                    text = String.valueOf(tempNum * 0.01);
                }
                break;
        }
        mTxtNew.setText(text);
    }


    //运算
    private enum Operation {
        ADD,//加
        SUBTRACTION,//减
        MULTIPLICATION,//乘
        DIVISION,//除
        NULL//无运算状态
    }
}
