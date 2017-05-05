package com.zx.easyshop.model;

/**
 * Created Time: 2017/2/17 14:26.
 *
 * @author HY
 *         表示上传的商品的实体类
 */

public class GoodsUpload {

    /**
     * "description": "诚信商家，非诚勿扰",     //商品描述
     * "master": "android",                    //商品发布者
     * "name": "礼物，鱼丸，鱼翅，火箭，飞机",   //商品名称
     * "price": "88",                          //商品价格
     * "type": "gift"                          //商品类型
     */

    private String description;
    private String master;
    private String name;
    private String price;
    private String type;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
