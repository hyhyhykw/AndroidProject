package com.zx.eshop.base.widgets;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zx.eshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created Time: 2017/2/28 19:03.
 *
 * @author HY
 *         自定义搜索视图
 * @see android.widget.SearchView
 */
public final class SimpleSearchView extends LinearLayout implements TextWatcher, TextView.OnEditorActionListener {

    @BindView(R.id.button_search)
    protected ImageButton mBtnSearch;//搜索按钮
    @BindView(R.id.edit_query)
    protected EditText mEtQuery;//搜索框
    @BindView(R.id.button_clear)
    protected ImageButton mBtnClear;//清除按钮
    //搜索按钮回调
    private OnSearchLisenter mOnSearchLisenter;

    public SimpleSearchView(Context context) {
        //代码中使用
        this(context, null);
    }

    public SimpleSearchView(Context context, AttributeSet attrs) {
        //布局中使用，不设置style
        this(context, attrs, 0);
    }

    public SimpleSearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        //布局中使用，设置style
        super(context, attrs, defStyleAttr);
        init(context);
    }

    //初始化视图
    private void init(Context context) {
        //渲染和绑定视图
        LayoutInflater.from(context).inflate(R.layout.widget_simple_search_view, this);
        ButterKnife.bind(this);

        //enter键改为搜索
        mEtQuery.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
        //输入类型
        mEtQuery.setInputType(EditorInfo.TYPE_CLASS_TEXT);

        mEtQuery.setOnEditorActionListener(this);
        mEtQuery.addTextChangedListener(this);
    }

    //搜索
    private void search() {
        if (null != mOnSearchLisenter) {
            mOnSearchLisenter.onSearch(mEtQuery.getText().toString());
        }
        closeKeybord();
    }

    //关闭键盘
    private void closeKeybord() {
        //清除焦点
        mEtQuery.clearFocus();
        //关闭键盘
        InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(mEtQuery.getWindowToken(), 0);
    }

    @OnClick({R.id.button_clear, R.id.button_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_clear://清除
                mEtQuery.setText(null);
            case R.id.button_search://搜索
                search();
                break;
        }
    }

    //设置监听的方法
    public void setOnSearchLisenter(OnSearchLisenter onSearchLisenter) {
        mOnSearchLisenter = onSearchLisenter;
    }

    //#######################        搜索框文本改变监听         #########################
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        //根据当前搜索框内是否有文字，设置清除按钮是否可见
        mBtnClear.setVisibility(s.length() > 0 ? VISIBLE : INVISIBLE);
    }

    //###########################      监听键盘按键        #########################
    @Override
    public boolean onEditorAction(TextView view, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            search();
            return true;
        }
        return false;
    }

    //##########################      自定义搜索按钮监听        ###########################

    /* 自定义回调接口，用于监听搜索按钮事件 */
    public interface OnSearchLisenter {
        void onSearch(String text);
    }
}
