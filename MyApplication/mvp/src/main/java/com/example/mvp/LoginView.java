package com.example.mvp;

/**
 * Created by HY on 2016/12/21.
 */

public interface LoginView {

    /**
     * get text of username
     *
     * @return
     */
    String getUsrName();

    /**
     * get text of password
     *
     * @return
     */
    String getPassword();

    /**
     * used to show a dialog when connection
     */
    void showDialog();

    /**
     * cancel the dialog
     */
    void cancelDialog();

    /**
     * clear text
     */
    void clear();

}
