package com.ftp.demo.service;

import com.ftp.demo.dto.FileTree;

/**
 * @author LiuXPeng
 */
public interface FileService {
    FileTree getFileTree(String dirpath);
}
