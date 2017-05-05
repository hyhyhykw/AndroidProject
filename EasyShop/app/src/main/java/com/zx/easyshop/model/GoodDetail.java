package com.zx.easyshop.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created Time: 2017/2/16 15:22.
 *
 * @author HY
 */

public class GoodDetail implements Serializable{

    /**
     * uuid : 0139444443D640C08C000EE05684F2CC
     * name : 航母111
     * type : dress
     * price : 23434
     * description : 张健云的商品1111
     * master : qwerty1
     * pages : []
     */

    //商品表主键
    private String uuid;
    //商品名称
    private String name;
    //商品类型
    private String type;
    //商品价格
    private String price;
    //商品描述
    private String description;
    //发布者名称
    private String master;
    //商品图片Uri
    private List<ImageUri> pages;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

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

    public List<ImageUri> getPages() {
        return pages;
    }

    public void setPages(List<ImageUri> pages) {
        this.pages = pages;
    }

    public class ImageUri {
        private String uri;

        public String getUri() {
            return uri;
        }
    }
}
