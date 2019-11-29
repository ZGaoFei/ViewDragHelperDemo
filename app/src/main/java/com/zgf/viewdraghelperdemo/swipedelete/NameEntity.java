package com.zgf.viewdraghelperdemo.swipedelete;

public class NameEntity {

    private String name;
    private int isOpen;

    public NameEntity() {
    }

    public NameEntity(String name, int isOpen) {
        this.name = name;
        this.isOpen = isOpen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(int isOpen) {
        this.isOpen = isOpen;
    }
}
