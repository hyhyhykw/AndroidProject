package com.zx.easyshop.model;


import com.google.gson.annotations.SerializedName;

/**
 * Created Time: 2017/2/16 10:07.
 *
 * @author HY
 */

public class GoodsInfo {


    /**
     * price : 66
     * name : 单车
     * description : ......
     * page : /images/D3228118230A43C0B77/5606FF8A48F1FC4907D/F99E38F09A.JPEG
     * type : other
     * uuid : 5606FF8EF60146A48F1FCDC34144907D
     * master : android
     */

    private String price;//价格
    private String name;//商品名称
    private String description;//商品描述
    private String page;//图片
    private String type;//商品类型
    @SerializedName("uuid")
    private String tableId;//商品表主键
    private String master;//发布者

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTableId() {
        return tableId;
    }

    public void setUuid(String tableId) {
        this.tableId = tableId;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }
}
