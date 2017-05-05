package com.zx.eshop.network.core;

import com.google.gson.annotations.SerializedName;
import com.zx.eshop.network.entity.Status;

/**
 * 请求响应的实体基类，为了防止直接被实现，做成抽象类
 * Created Time: 2017/3/1 15:00.
 *
 * @author HY
 */
public abstract class ResponseEntity {
    @SerializedName("status")
    private Status mStatus;

    public Status getStatus() {
        return mStatus;
    }
}
