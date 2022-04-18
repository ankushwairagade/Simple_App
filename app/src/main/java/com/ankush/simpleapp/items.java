package com.ankush.simpleapp;

public class items
{
    private String image_id;
    private String text;

    public String getImage_id()
    {
        return image_id;
    }

    public void setImage_id(String image_id)
    {
        this.image_id = image_id;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    items(String img, String text)
    {
        image_id = img;
        this.text = text;
    }
}
