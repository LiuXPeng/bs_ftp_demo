package com.ftp.demo.service.impl;

import com.ftp.demo.dto.FileTree;
import com.ftp.demo.service.FileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * @author LiuXPeng
 */
@Service
public class FileServiceImpl implements FileService {

    @Override
    public FileTree getFileTree(String dirPath) {
        FileTree fileTree = new FileTree();
        File file = new File(dirPath);
        fileTree.setName(file.getName());
        fileTree.setPath(dirPath);
        if (file.isDirectory()) {
            fileTree.setType(0);
            File[] list = file.listFiles();
            for (int i = 0;i< list.length;i++) {
                fileTree.getChildren().add(getFileTree(list[i].toString()));
            }
        } else {
            fileTree.setType(1);
            fileTree.setChildren(null);
        }
        return fileTree;
    }
}
