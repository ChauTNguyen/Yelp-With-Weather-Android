package com.chautnguyen.yelpresults;

public class YelpBusiness {
    private String name;
    private int reviewCount;
    private String url;
    private String imageUrl;
    private String displayAddress;
    private String ratingImageUrl;

    public YelpBusiness(
            String name,
            Integer reviewCount,
            String url,
            String imageUrl,
            String displayAddress,
            String ratingImageUrl) {
        this.name = name;
        this.reviewCount = reviewCount;
        this.url = url;
        this.imageUrl = imageUrl;
        this.displayAddress = displayAddress;
        this.ratingImageUrl = ratingImageUrl;
    }

    public String getRatingImageUrl() {
        return ratingImageUrl;
    }

    public void setRatingImageUrl(String ratingImageUrl) {
        this.ratingImageUrl = ratingImageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(int reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDisplayAddress() {
        return displayAddress;
    }

    public void setDisplayAddress(String displayAddress) {
        this.displayAddress = displayAddress;
    }
}