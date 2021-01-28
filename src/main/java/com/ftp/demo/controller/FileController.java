package com.ftp.demo.controller;

import com.ftp.demo.dto.FileTree;
import com.ftp.demo.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xwpf.converter.pdf.PdfConverter;
import org.apache.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.jodconverter.DocumentConverter;
import org.jodconverter.office.OfficeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.regex.Pattern;

@RestController
@Api
@CrossOrigin
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

    @GetMapping("/download")
    public void download(@RequestParam("path") String path, HttpServletResponse response) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            response.setContentType("application/octet-stream");
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(file.getName(), "utf8"));
            byte[] buffer = new byte[1024];
            //输出流
            OutputStream os = null;
            try (FileInputStream fis = new FileInputStream(file);
                 BufferedInputStream bis = new BufferedInputStream(fis);) {
                os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        response.sendError(419, "文件路径错误");
    }

    @GetMapping("/preview")
    public void preview(@RequestParam("path") String path, HttpServletResponse response) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            response.setContentType("application/octet-stream");
            response.setHeader("content-type", "application/octet-stream");
            byte[] buffer = new byte[1024];
            //输出流
            OutputStream os = null;
            if (Pattern.matches(".*.pdf", path)) {
                response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(file.getName(), "utf8"));
                try (FileInputStream fis = new FileInputStream(file);
                     BufferedInputStream bis = new BufferedInputStream(fis);) {
                    os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer);
                        i = bis.read(buffer);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            if (Pattern.matches(".*.doc", path) || Pattern.matches(".*.docx", path)) {
                try {
                    response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(file.getName().replace(".docx", ".pdf").replace(".doc", ".pdf"), "utf8"));
                    os = response.getOutputStream();
                    XWPFDocument document;
                    InputStream doc = new FileInputStream(path);
                    document = new XWPFDocument(doc);
                    PdfOptions options = PdfOptions.create();
//                    OutputStream out = new FileOutputStream(pdfPath);
                    PdfConverter.getInstance().convert(document, os, options);

                    doc.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
            response.sendError(418, "文件不是word或者pdf");
            return;
        }
        response.sendError(419, "文件路径错误");
    }
}
