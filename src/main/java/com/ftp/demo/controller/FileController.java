package com.ftp.demo.controller;

import com.ftp.demo.dto.FileTree;
import com.ftp.demo.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api
public class FileController {
    @Autowired
    FileService fileService;
    @Value("${dir.path}")
    private String dirPath;

    @ApiOperation(value = "test")
    @GetMapping("/fileTree")
    public FileTree getFileTree() {
        return fileService.getFileTree(dirPath);
    }
}
