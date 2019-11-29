package com.dave.art.controller;

public class ImageAndDescription {
    String _image;
    String _description;

    public ImageAndDescription(String _image, String _description) {
        this._image = _image;
        this._description = _description;
    }

    public String getImage() {
        return _image;
    }

    public void setImage(String image) {
        this._image = image;
    }

    public String getDescription() {
        return _description;
    }

    public void setDescription(String description) {
        this._description = description;
    }
}
