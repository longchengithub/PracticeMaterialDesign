package com.example.chenlong.practicematerialdesign.view.HomePage;

import java.util.List;

/**
 * Created by chenlong on 2016/12/9.
 */

public class BannerEntity {
    private int code;
    private List<BannerData> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<BannerData> getData() {
        return data;
    }

    public void setData(List<BannerData> data) {
        this.data = data;
    }

    public static class BannerData {
        private String title;

        private String value;

        private String image;

        private int type;

        private int weight;

        private String remark;

        private String hash;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }
    }
}
