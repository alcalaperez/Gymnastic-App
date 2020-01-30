package com.uo226321.joel.gymnastics.view;


public class CardItem {

    private int mImage;
    private int mTitleResource;

    public CardItem(int title, int image) {
        mTitleResource = title;
        mImage = image;
    }

    public int getImage() {
        return mImage;
    }

    public int getTitle() {
        return mTitleResource;
    }

}
