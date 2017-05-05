package com.hy.filemanager;

import java.io.File;
import java.util.List;
import java.util.Vector;

import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.hy.filemanager.adapter.FileListAdapter;
import com.hy.filemanager.constant.Constant;
import com.hy.filemanager.entity.FileDetailInfo;
import com.hy.filemanager.utils.FileManager;
import com.hy.filemanager.utils.FileUitls;

/**
 * @author HY
 */
public class MainActivity extends BaseActivity {

    /**
     * file list
     */
    protected ListView mLstFileList;
    protected List<FileDetailInfo> fileDetailInfos;
    protected FileListAdapter adapter;
    protected FileManager fileManager;

    protected List<File> parentFiles = new Vector<File>();
    protected TextView mTxtCurrent;
    protected TextView mTxtParent;
    protected FileDetailInfo detailInfo;
    protected TextView mTxtEmptyFolder;
    protected AlertDialog.Builder mBuilder;

    protected TextView mTxtMemUsed;
    protected TextView mTxtMemFree;

    protected boolean isShowHid;
    protected boolean isFolderUp;

    /**
     * share file data
     */
    protected SharedPreferences mSharedPre;
    /**
     * editor object
     */
    protected Editor mEditor;

    protected int sortMode;

    protected String path;

    protected List<File> currentFiles = new Vector<File>();

    protected DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case AlertDialog.BUTTON_POSITIVE:
                    finish();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                adapter.update(fileDetailInfos);
                break;
            case R.id.action_settings:
                toActivity(SettingActivity.class);
                break;
            case R.id.return_main:
                fileDetailInfos.clear();
                parentFiles.clear();
                initSdPath();
                adapter.update(fileDetailInfos);
                break;
            case R.id.dialog_about:

                break;
            case R.id.action_exit:
                System.exit(0);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        initView();
        initData();
        initEvent();
        adapter.update(fileDetailInfos);
        if (!currentFiles.isEmpty()) {
            File currentFile = currentFiles.get(currentFiles.size() - 1);
            mTxtEmptyFolder
                    .setVisibility(FileUitls.isEmpty(currentFile) ? View.VISIBLE
                            : View.GONE);
        }
    }

    /**
     * initialize view
     */
    public void initView() {
        mTxtEmptyFolder = (TextView) this.findViewById(R.id.txt_empty_folder);
        mTxtCurrent = (TextView) this.findViewById(R.id.txt_current_path);
        mTxtParent = (TextView) this.findViewById(R.id.txt_parent_path);
        mLstFileList = (ListView) this.findViewById(R.id.lst_file_lst);
        adapter = new FileListAdapter(this, fileDetailInfos);
        mLstFileList.setAdapter(adapter);
    }

    /**
     * initialize data
     */
    public void initData() {
        mSharedPre = this.getSharedPreferences(Constant.CODE_IS_CHANGED,
                Context.MODE_PRIVATE);
        mEditor = mSharedPre.edit();

        isShowHid = mSharedPre.getBoolean("isShowHid", true);
        isFolderUp = mSharedPre.getBoolean("isFolderUp", true);
        sortMode = mSharedPre.getInt("sortMode", 0);
        mBuilder = new AlertDialog.Builder(this);
        mBuilder.setIcon(R.mipmap.ic_launcher);
        mBuilder.setTitle("请选择");
        mBuilder.setMessage("是否退出?");
        mBuilder.setPositiveButton("是", listener);
        mBuilder.setNegativeButton("否", listener);
        if (currentFiles.isEmpty())
            initSdPath();
        else
            initPath();
    }

    private void initSdPath() {
        path = FileUitls.getOuterSdcard(this);
        File sdCardPath;
        if (null == path)
            sdCardPath = new File(FileUitls.getInnerSdcard(this));
        else
            sdCardPath = new File(path);

        fileManager = FileManager.getInstance();
        fileDetailInfos = fileManager.getFileList(sdCardPath, isShowHid,
                isFolderUp, sortMode);
    }

    private void initPath() {
        File path = currentFiles.get(currentFiles.size() - 1);
        fileDetailInfos = fileManager.getFileList(path, isShowHid, isFolderUp,
                sortMode);
    }

    /**
     * initialize click event
     */
    public void initEvent() {
        mLstFileList.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                FileDetailInfo detailInfo = fileDetailInfos.get(position);
                File file = detailInfo.getFile();
                if (file.isFile()) {
                    String suffix = detailInfo.getSuffix();
                    String type = FileUitls.getMimeType(file);
                    if (type.equals("text/plain")) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("file", file);
                        toActivity(TextReadActivity.class, bundle);
                    } else if (FileUitls.isImageFile(suffix)) {

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("image", file);
                        toActivity(ImgViewActivity.class, bundle);
                    } else if (FileUitls.isDBType(suffix)) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("dbFile", file);
                        toActivity(DatabaseActivity.class, bundle);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.fromFile(file),
                                FileUitls.getMimeType(file));
                        startActivity(intent);
                    }
                } else {
                    if (detailInfo.isEmpty()) {
                        mTxtEmptyFolder.setVisibility(View.VISIBLE);
                    } else {
                        mTxtEmptyFolder.setVisibility(View.GONE);
                    }
                    fileDetailInfos = fileManager.getFileList(file, isShowHid,
                            isFolderUp, sortMode);
                    adapter.update(fileDetailInfos);
                    File parentDir = file.getParentFile();
                    File currentFile = file.getAbsoluteFile();
                    if (currentFile.isDirectory())
                        currentFiles.add(currentFile);
                    parentFiles.add(parentDir);
                }
            }
        });

        mLstFileList.setOnItemLongClickListener(new OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                FileDetailInfo fInfo = fileDetailInfos.get(position);
                File file = fInfo.getFile();
                Bundle bundle = new Bundle();
                bundle.putSerializable("file", file);
                toActivity(OperateActivity.class, bundle);
                return true;
            }
        });
    }

    // 重写返回键
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (!parentFiles.isEmpty()) {
                mTxtEmptyFolder.setVisibility(View.GONE);
                fileDetailInfos = fileManager.getFileList(
                        parentFiles.remove(parentFiles.size() - 1), isShowHid,
                        isFolderUp, sortMode);
                adapter.update(fileDetailInfos);
                return true;
            } else {
                mBuilder.show();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * @param view
     */
    public void bottomClick(View view) {
        // 返回上一级
        switch (view.getId()) {
            case R.id.img_return:
                if (!parentFiles.isEmpty()) {
                    mTxtEmptyFolder.setVisibility(View.GONE);
                    fileDetailInfos = fileManager.getFileList(
                            parentFiles.remove(parentFiles.size() - 1), isShowHid,
                            isFolderUp, sortMode);
                    currentFiles.remove(currentFiles.size() - 1);
                    adapter.update(fileDetailInfos);
                }
                break;
            case R.id.img_option:
                openOptionsMenu();
                break;
            case R.id.img_sort_file:
                toActivity(SortActivity.class, Constant.REQUEST_CODE_SORT);
                break;
            case R.id.img_add_file:
                Bundle bundle = new Bundle();
                String path = getParentPath();

                bundle.putString("parentPath", path + Constant.PATH_SEPARATOR);

                toActivity(CreateActivity.class, bundle);
                break;
        }
    }

    public String getParentPath() {
        if (parentFiles.isEmpty()) {
            return path;
        } else {
            return currentFiles.get(parentFiles.size() - 1).getAbsolutePath()
                    + Constant.PATH_SEPARATOR;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mEditor.putInt("sortMode", resultCode);
        mEditor.commit();
    }

}
