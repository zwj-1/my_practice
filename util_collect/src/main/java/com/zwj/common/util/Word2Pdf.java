package com.zwj.common.util;


import cn.hutool.core.collection.CollUtil;
import com.aspose.words.Document;
import com.aspose.words.ImportFormatMode;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

public class Word2Pdf {
    private static boolean license = false;

    static {
        try {
            // license.xml放在src/main/resources文件夹下
            InputStream is = Word2Pdf.class.getClassLoader().getResourceAsStream("license.xml");
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
     * word文档转pdf
     *
     * @param pdfPath:输出pdf文档地址
     * @param docPath：源word文档地址
     */
    public static void doc2pdf(String pdfPath, String docPath) {
        File pdfFile = new File(docPath);
        try {
            long old = System.currentTimeMillis();
            Document document = new Document(pdfPath);
            FileOutputStream outputStream = new FileOutputStream(pdfFile);
            document.save(outputStream, SaveFormat.PDF);
            outputStream.close();
            long now = System.currentTimeMillis();
            System.out.println("PDF转化WORD共耗时：" + ((now - old) / 1000.0) + "秒");
        } catch (Exception e) {
            System.out.println("转化失败");
            e.printStackTrace();
        }
    }

    /**
     * 多个word文件合并
     *
     * @param filePathList:参与被合并的word文件
     * @param parentFilePath：合并word文件地址
     */
    @SneakyThrows
    public static void mergeWord(List<String> filePathList, String parentFilePath) {
        if (CollUtil.isEmpty(filePathList) || StringUtils.isBlank(parentFilePath)) {
            // 抛出异常 被合并word文件地址和合并word文件地址不能为空
            //throw new
        }

        Document parentWord = new Document();
        parentWord.removeAllChildren();
        for (int i = 0; i < filePathList.size(); i++) {
            Document addToWord = new Document(filePathList.get(i));
            parentWord.appendDocument(addToWord, ImportFormatMode.USE_DESTINATION_STYLES);
            parentWord.save(parentFilePath, SaveFormat.DOC);
        }
    }
}
