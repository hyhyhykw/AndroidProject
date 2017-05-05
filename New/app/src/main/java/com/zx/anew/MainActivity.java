package com.zx.anew;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.edt)
    EditText mEdt;

    private ChatAdapter mAdapter;
    private LinearLayoutManager mManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ButterKnife.bind(this);
        mAdapter = new ChatAdapter();
        mRecycler.setAdapter(mAdapter);
        mManager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(mManager);
    }

    @OnClick({R.id.btn_a, R.id.btn_b})
    public void onClick(View view) {
        if (TextUtils.isEmpty(mEdt.getText())) {
            Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        MessageInfo msgInfo = new MessageInfo();
        msgInfo.setMsg(mEdt.getText().toString());
        switch (view.getId()) {
            case R.id.btn_a:
                msgInfo.setMsgType(MsgType.MSG_A);
                break;
            case R.id.btn_b:
                msgInfo.setMsgType(MsgType.MSG_B);
                break;
        }
        mAdapter.addMseeage(msgInfo);
        mManager.scrollToPosition(mAdapter.getItemCount() - 1);
        mEdt.setText(null);
        mEdt.clearFocus();
        closeKey();
    }

    private void closeKey() {
        InputMethodManager input = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        input.hideSoftInputFromWindow(mEdt.getWindowToken(), 0);
    }
}
