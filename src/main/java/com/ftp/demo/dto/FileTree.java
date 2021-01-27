package com.ftp.demo.dto;

import java.util.ArrayList;
import java.util.List;

public class FileTree {
    private String name;
    private String path;
    private int type;
    private List<FileTree> children = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<FileTree> getChildren() {
        return children;
    }

    public void setChildren(List<FileTree> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "FileTree{" +
                "name='" + name + '\'' +
                ", path='" + path + '\'' +
                ", type=" + type +
                ", children=" + children +
                '}';
    }
}
