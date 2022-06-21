package com.zwj.common.util;

import cn.hutool.core.collection.CollUtil;
import com.aspose.pdf.DocSaveOptions;
import com.aspose.pdf.Document;
import com.aspose.pdf.License;
import com.aspose.pdf.SaveFormat;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Pdf2Word {
    private static boolean license = false;

    static {
        try {
            // license.xml放在src/main/resources文件夹下
            InputStream is = Pdf2Word.class.getClassLoader().getResourceAsStream("license.xml");
            License aposeLic = new License();
            aposeLic.setLicense(is);
            license = true;
        } catch (Exception e) {
            license = false;
            System.out.println("License验证失败...");
            e.printStackTrace();
        }
    }

    /**
     * pdf转为word文档-使用的不是正版，只能转4页
     *
     * @param pdfPath：pdf文件地址
     * @param docPath：生产的doc文件地址
     */
    @SneakyThrows
    public static void pdfToDoc(String pdfPath, String docPath) {
        if (!license) {
            System.out.println("License验证失败...");
        }

        File pdfFile = new File(docPath);
        try {
            long old = System.currentTimeMillis();
            Document document = new Document(pdfPath);
            FileOutputStream outputStream = new FileOutputStream(pdfFile);
            DocSaveOptions saveOptions = new DocSaveOptions();
            saveOptions.setFormat(SaveFormat.DocX);
            document.save(outputStream, saveOptions);
            document.close();
            outputStream.close();
            long now = System.currentTimeMillis();
            System.out.println("PDF转化WORD共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            System.out.println("转化失败");
            e.printStackTrace();
        }
    }

    /**
     * pdf转为word文档-使用的不是正版，先将pdf拆分，再分别对拆分的pdf转为word,最后合并word文档生产最终word文档
     *
     * @param pdfPath:pdf文件地址
     * @param docPath：生成word文件地址
     * @param templateFilePath：pdf拆分文件临时存放地址
     * @param startSplit：pdf拆分开始页
     * @param splitSize：pdf每次拆分多少页
     */
    public static List<String> pdfToDoc(String pdfPath, String docPath, String templateFilePath, int startSplit, int splitSize) {
        List<String> filePaths = PdfUtils.splitPdf(pdfPath, templateFilePath, startSplit, splitSize);
        List<String> docFilePaths = new ArrayList<>(10);
        for (String filePath : filePaths) {
            String docFilePath = filePath.substring(0, filePath.length() - 4) + ".doc";
            docFilePaths.add(docFilePath);
            pdfToDoc(filePath, docFilePath);
        }
        Word2Pdf.mergeWord(docFilePaths, docPath);
        // 删除中间临时文件
        filePaths.addAll(docFilePaths);
        return filePaths;
    }

    public static void delTempFile(List<String> filePaths) {
        if (CollUtil.isEmpty(filePaths)) {
            return;
        }
        for (String filePath : filePaths) {
            File mouldFile = new File(filePath);
            mouldFile.deleteOnExit();
        }
    }

    public static void main(String[] args) {
        String pdfFile = "C:/Users/邹文君/Desktop/2022年重庆市自然科学基金面上项目申报书-正文.pdf";
        String docPath = "C:/Users/邹文君/Desktop/总合并.doc";
        String templateFilePath = "C:/Users/邹文君/Desktop/拆分的pdf文档.pdf";
        List<String> filePaths = pdfToDoc(pdfFile, docPath, templateFilePath, 1, 4);
        delTempFile(filePaths);
    }
}
