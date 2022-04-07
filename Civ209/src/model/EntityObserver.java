//-----------------------------------------------------------
//File:   EntityObserver.java
//Desc:   This program creates an observer that will update an entities image
//-----------------------------------------------------------
package model;

import javafx.scene.image.ImageView;

public interface EntityObserver {
    public void updateImage(ImageView image);
}