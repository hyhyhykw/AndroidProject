package com.zx.easyshop.network;

import com.google.gson.Gson;
import com.zx.easyshop.model.CachePreferences;
import com.zx.easyshop.model.GoodsUpload;
import com.zx.easyshop.model.User;

import java.io.File;
import java.util.List;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created Time: 2017/2/9 17:13.
 *
 * @author HY
 *         易淘项目客户端
 */
public class EasyShopClient {
    private OkHttpClient mOkHttpClient;
    private static EasyShopClient mEasyShopClient;

    private Gson gson;

    //单例模式
    private EasyShopClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        gson = new Gson();
    }

    public static EasyShopClient newInstance() {
        if (null == mEasyShopClient)
            mEasyShopClient = new EasyShopClient();
        return mEasyShopClient;
    }

    /**
     * 注册 post
     *
     * @param username 用户名
     * @param password 密码
     * @return 呼叫请求
     */
    public Call register(String username, String password) {
        //请求体
        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        //请求
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.REGISTER)
                .post(requestBody)
                .build();

        return mOkHttpClient.newCall(request);
    }

    /**
     * 登陆 post
     *
     * @param username 用户名
     * @param password 密码
     * @return 呼叫请求
     */
    public Call login(String username, String password) {
        //请求体
        RequestBody requestBody = new FormBody.Builder()
                .add("username", username)
                .add("password", password)
                .build();
        //请求
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.LOGIN)
                .post(requestBody)
                .build();

        return mOkHttpClient.newCall(request);
    }

    /**
     * 修改用户昵称
     *
     * @param user 修改后的用户信息实体类
     * @return 新的呼叫请求
     */
    public Call unloadUser(User user) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                //上传用户信息实体类
                .addFormDataPart("user", gson.toJson(user))
                .build();
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.UPDATA)
                .post(requestBody)
                .build();
        return mOkHttpClient.newCall(request);
    }

    /**
     * 上传图像
     *
     * @param file 图像文件
     * @return 呼叫请求
     */
    public Call unloadImage(File file) {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user", gson.toJson(CachePreferences.getUser()))
                .addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("image/png"), file))
                .build();
        Request request = new Request.Builder()
                .post(requestBody)
                .url(EasyShopApi.BASE_URL + EasyShopApi.UPDATA)
                .build();
        return mOkHttpClient.newCall(request);
    }

    /**
     * 获取所有商品
     *
     * @param pageNo 页数
     * @param type   类型
     * @return 呼叫请求
     */
    public Call getGoods(int pageNo, String type) {
        RequestBody requestBody = new FormBody.Builder()
                .add("pageNo", String.valueOf(pageNo))
                .add("type", type)
                .build();
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.GETGOODS)
                .post(requestBody)
                .build();
        return mOkHttpClient.newCall(request);
    }

    /**
     * 获取商品详情
     *
     * @param goodUuid 商品表主键
     * @return 呼叫请求
     */
    public Call getGoodDetail(String goodUuid) {
        RequestBody requestBody = new FormBody.Builder()
                .add("uuid", goodUuid)
                .build();
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.DETAIL)
                .post(requestBody)
                .build();
        return mOkHttpClient.newCall(request);
    }

    /**
     * 获取个人商品
     *
     * @param pageNo 分页
     * @param type   类型
     * @return 呼叫
     */
    public Call getPersonGoods(int pageNo, String type) {
        RequestBody requestBody = new FormBody.Builder()
                .add("pageNo", String.valueOf(pageNo))
                .add("type", type)
                .add("master", CachePreferences.getUser().getName())
                .build();

        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.GETGOODS)
                .post(requestBody)
                .build();

        return mOkHttpClient.newCall(request);
    }

    /**
     * 删除商品
     *
     * @param uuid 商品表主键
     * @return 呼叫请求
     */
    public Call delete(String uuid) {
        RequestBody requestBody = new FormBody.Builder()
                .add("uuid", uuid)
                .build();

        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.DELETE)
                .post(requestBody)
                .build();

        return mOkHttpClient.newCall(request);
    }

    /**
     * 上传商品
     *
     * @param goodsUpload 商品信息
     * @param files       商品图片文件
     * @return 呼叫请求
     */
    public Call upload(GoodsUpload goodsUpload, List<File> files) {
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("good", gson.toJson(goodsUpload));
        //添加文件
        for (File file : files) {
            builder.addFormDataPart("image", file.getName(),
                    RequestBody.create(MediaType.parse("image/png"), file));
        }

        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.UPLOADGOODS)
                .post(builder.build())
                .build();
        return mOkHttpClient.newCall(request);
    }

    /**
     * 查找用户
     *
     * @param nickname 用户昵称
     * @return 呼叫请求
     */
    public Call getSearchUser(String nickname) {
        RequestBody requestBody = new FormBody.Builder()
                .add("nickname", nickname)
                .build();
        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.GET_USER)
                .post(requestBody)
                .build();
        return mOkHttpClient.newCall(request);
    }

    /**
     * 获取好友列表
     *
     * @param ids 用户id列表
     * @return 呼叫请求
     */
    public Call getUsers(List<String> ids) {
        String names = ids.toString();
        //去除空格
        names = names.replace(" ", "");
        RequestBody requestBody = new FormBody.Builder()
                .add("name", names)
                .build();

        Request request = new Request.Builder()
                .url(EasyShopApi.BASE_URL + EasyShopApi.GET_NAMES)
                .post(requestBody)
                .build();
        return mOkHttpClient.newCall(request);
    }
}
