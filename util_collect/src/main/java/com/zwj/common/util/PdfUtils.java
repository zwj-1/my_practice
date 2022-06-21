package com.zwj.common.util;

import cn.hutool.core.util.ObjectUtil;
import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.parser.*;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @NAME: PdfUtils
 * @Author: Yao Lingcheng
 * @DATE: 2020/12/8 星期二
 */
public class PdfUtils {

    private static String footer = "第%d页，共%d页";// 基础字体对象
    public static BaseFont bf = null;

    /**
     * 功能描述: 根据页码分割pdf
     *
     * @param: [pdfFile, newFile, from, end]
     * @return: void
     * @auther: pjj
     * @date: 2019/4/15
     */
    public static void partitionPdfFile(String pdfFile, String newFile, int from, int end) {

        Document document = null;
        PdfCopy copy = null;
        PdfReader reader = null;
        FileOutputStream fileOutputStream = null;
        try {
            int n = getPdfFileTotalPage(reader, pdfFile);
            if (end == 0) {
                end = n;
            }
            ArrayList<String> savepaths = new ArrayList<String>();
            String staticpath = pdfFile.substring(0, pdfFile.lastIndexOf("\\") + 1);
            savepaths.add(newFile);
            document = new Document(reader.getPageSize(1));
            fileOutputStream = new FileOutputStream(savepaths.get(0));
            copy = new PdfCopy(document, fileOutputStream);
            document.open();
            for (int j = from; j <= end; j++) {
                document.newPage();
                PdfImportedPage page = copy.getImportedPage(reader, j);
                copy.addPage(page);
            }

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        } finally {
            closeStream(document, copy, reader, fileOutputStream);
        }
    }

    public static void partitionPdfFile1(String pdfFile, String newFile, int from, int end) {

        Document document = null;
        PdfCopy copy = null;
        PdfReader reader = null;
        FileOutputStream fileOutputStream = null;
        try {
            int n = getPdfFileTotalPage(reader, pdfFile);
            if (end == 0) {
                end = n;
            }
            ArrayList<String> savepaths = new ArrayList<String>();
            String staticPath = pdfFile.substring(0, pdfFile.lastIndexOf("\\") + 1);
            savepaths.add(newFile);
            document = new Document(reader.getPageSize(1));
            fileOutputStream = new FileOutputStream(savepaths.get(0));
            copy = new PdfCopy(document, fileOutputStream);
            document.open();
            for (int j = from; j <= end; j++) {
                document.newPage();
                PdfImportedPage page = copy.getImportedPage(reader, j);
                copy.addPage(page);
            }

        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        } finally {
            closeStream(document, copy, reader, fileOutputStream);
        }
    }

    @SneakyThrows
    private static Integer getPdfFileTotalPage(PdfReader reader, String pdfFile) {
        if (ObjectUtil.isEmpty(reader)) {
            reader = new PdfReader(pdfFile);
        }
        return reader.getNumberOfPages();
    }

    /**
     * 合并PDF文件.
     *
     * @param files   需要合并的pdf文件路径.
     * @param newfile 合并之后的pdf文件.
     *                <p>
     *                功能描述: 合并pdf
     * @param: [files, newfile]
     * @return: boolean
     * @auther: pjj
     * @date: 2019/4/15
     */
    public static boolean mergePdfFiles(String[] files, String newfile) {
        boolean retValue = false;
        Document document = null;
        FileOutputStream fileOutputStream = null;
        PdfCopy copy = null;
        PdfReader pdfReader1 = null;
        try {
            pdfReader1 = new PdfReader(files[0]);
            document = new Document(pdfReader1.getPageSize(1));
            fileOutputStream = new FileOutputStream(newfile);
            copy = new PdfCopy(document, fileOutputStream);
            document.open();
//            int pagesNum=0;//总页码
//            int pages=0;//当前页码
//            for (int i = 0; i < files.length ; i++) {
//                PdfReader reader=new PdfReader(files[i]);
//                pagesNum += reader.getNumberOfPages();
//            }
            for (int i = 0; i < files.length; i++) {
                PdfReader reader = new PdfReader(files[i]);
                int n = reader.getNumberOfPages();
//                PdfCopy.PageStamp stamp;//插入页码所需  不要页码可删除
                for (int j = 1; j <= n; j++) {
                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(reader, j);
//                    stamp = copy.createPageStamp(page);//插入页码所需  不要页码可删除
//                    //插入页码所需  不要页码可删除 start
//                    String footerFormat = String.format(footer, pages, pagesNum);
//                    Paragraph phrase = addFont(footerFormat);
//                    float len = bf.getWidthPoint(footerFormat, 14f);
//                    ColumnText.showTextAligned(stamp.getUnderContent(),
//                            Element.ALIGN_CENTER,
//                            phrase,
//                            (document.rightMargin() + document.right() + document.leftMargin() - document.left() - len) / 2.0F + 20F,
//                            document.bottom() - 20,
//                            0);
//                    //插入页码所需  不要页码可删除 end
//                    stamp.alterContents(); //插入页码所需  不要页码可删除
                    copy.addPage(page);
                }
                reader.close();
            }
            retValue = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeStream(document, copy, pdfReader1, fileOutputStream);
        }
        return retValue;
    }

    protected static Paragraph addFont(String content) {
        BaseFont baseFont = null;
        try {
            try {
                if (bf == null) {
                    baseFont = BaseFont.createFont("D:/z/simsun.ttc,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED); //D:/z/simsun.ttc,0是所下字体的路径
                    bf = baseFont;
                }
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font font = null;
        font = new Font(baseFont, 18f, Font.NORMAL); //设置字体
        return addText(content, font);
    }

    private static Paragraph addText(String content, Font font) {
        Paragraph paragraph = new Paragraph(content, font);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        return paragraph;
    }

    /**
     * 关流
     *
     * @param document
     * @param copy
     * @param reader
     * @param fileOutputStream
     */
    private static void closeStream(Document document, PdfCopy copy, PdfReader reader, FileOutputStream fileOutputStream) {
        if (document != null) {
            document.close();
        }
        if (copy != null) {
            copy.close();
        }
        if (reader != null) {
            reader.close();
        }
        if (fileOutputStream != null) {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取模板自定义关键字所在页码
     *
     * @param filePath 文件全路径
     * @param keyword  模板中定义的关键字
     * @return
     */
    @SneakyThrows
    public static int[] getKeyWordPage(String filePath, String keyword) {
        int keyWordPage = 0;

        //1.给定文件
        File pdfFile = new File(filePath);
        //2.定义一个byte数组，长度为文件的长度
        byte[] pdfData = new byte[(int) pdfFile.length()];

        //3.IO流读取文件内容到byte数组
        FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream(pdfFile);
            inputStream.read(pdfData);
        } catch (IOException e) {
            throw e;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }

        PdfReader reader = new PdfReader(pdfData);
        int pages = reader.getNumberOfPages();
        int[] ints = new int[2];
        ints[1] = pages;
        //5.调用方法，给定关键字和文件
        List<float[]> positions = findKeywordPostions(pdfData, keyword);

        //6.返回值类型是  List<float[]> 每个list元素代表一个匹配的位置，分别为 float[0]所在页码  float[1]所在x轴 float[2]所在y轴
//        System.out.println("total:" + positions.size());
        if (positions != null && positions.size() > 0) {
            for (float[] position : positions) {
                keyWordPage = (int) position[0];
                System.out.print("pageNum: " + (int) position[0]);
                System.out.print("\tx: " + position[1]);
                System.out.println("\ty: " + position[2]);
            }
        } else {
            System.out.println("模板不存在关键字");
        }
        ints[0] = keyWordPage;
        return ints;
    }

    /**
     * findKeywordPostions
     *
     * @param pdfData 通过IO流 PDF文件转化的byte数组
     * @param keyword 关键字
     * @return List<float [ ]> : float[0]:pageNum float[1]:x float[2]:y
     * @throws IOException
     */
    public static List<float[]> findKeywordPostions(byte[] pdfData, String keyword) throws IOException {
        List<float[]> result = new ArrayList<>();
        List<PdfPageContentPositions> pdfPageContentPositions = getPdfContentPostionsList(pdfData);
        for (PdfPageContentPositions pdfPageContentPosition : pdfPageContentPositions) {
            List<float[]> charPositions = findPositions(keyword, pdfPageContentPosition);
            if (charPositions == null || charPositions.size() < 1) {
                continue;
            }
            result.addAll(charPositions);
        }
        return result;
    }

    private static List<PdfPageContentPositions> getPdfContentPostionsList(byte[] pdfData) throws IOException {
        PdfReader reader = new PdfReader(pdfData);

        List<PdfPageContentPositions> result = new ArrayList<>();

        int pages = reader.getNumberOfPages();
        for (int pageNum = 1; pageNum <= pages; pageNum++) {
            float width = reader.getPageSize(pageNum).getWidth();
            float height = reader.getPageSize(pageNum).getHeight();

            PdfRenderListener pdfRenderListener = new PdfRenderListener(pageNum, width, height);

            //解析pdf，定位位置
            PdfContentStreamProcessor processor = new PdfContentStreamProcessor(pdfRenderListener);
            PdfDictionary pageDic = reader.getPageN(pageNum);
            PdfDictionary resourcesDic = pageDic.getAsDict(PdfName.RESOURCES);
            try {
                processor.processContent(ContentByteUtils.getContentBytesForPage(reader, pageNum), resourcesDic);
            } catch (IOException e) {
                reader.close();
                throw e;
            }

            String content = pdfRenderListener.getContent();
            List<CharPosition> charPositions = pdfRenderListener.getcharPositions();

            List<float[]> positionsList = new ArrayList<>();
            for (CharPosition charPosition : charPositions) {
                float[] positions = new float[]{charPosition.getPageNum(), charPosition.getX(), charPosition.getY()};
                positionsList.add(positions);
            }

            PdfPageContentPositions pdfPageContentPositions = new PdfPageContentPositions();
            pdfPageContentPositions.setContent(content);
            pdfPageContentPositions.setPostions(positionsList);

            result.add(pdfPageContentPositions);
        }
        reader.close();
        return result;
    }

    private static List<float[]> findPositions(String keyword, PdfPageContentPositions pdfPageContentPositions) {

        List<float[]> result = new ArrayList<>();

        String content = pdfPageContentPositions.getContent();
        List<float[]> charPositions = pdfPageContentPositions.getPositions();

        for (int pos = 0; pos < content.length(); ) {
            int positionIndex = content.indexOf(keyword, pos);
            if (positionIndex == -1) {
                break;
            }
            float[] postions = charPositions.get(positionIndex);
            result.add(postions);
            pos = positionIndex + 1;
        }
        return result;
    }

    private static class PdfPageContentPositions {
        private String content;
        private List<float[]> positions;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public List<float[]> getPositions() {
            return positions;
        }

        public void setPostions(List<float[]> positions) {
            this.positions = positions;
        }
    }

    private static class PdfRenderListener implements RenderListener {
        private int pageNum;
        private float pageWidth;
        private float pageHeight;
        private StringBuilder contentBuilder = new StringBuilder();
        private List<CharPosition> charPositions = new ArrayList<>();

        public PdfRenderListener(int pageNum, float pageWidth, float pageHeight) {
            this.pageNum = pageNum;
            this.pageWidth = pageWidth;
            this.pageHeight = pageHeight;
        }

        @Override
        public void beginTextBlock() {
        }

        @Override
        public void renderText(TextRenderInfo renderInfo) {
            List<TextRenderInfo> characterRenderInfos = renderInfo.getCharacterRenderInfos();
            for (TextRenderInfo textRenderInfo : characterRenderInfos) {
                String word = textRenderInfo.getText();
                if (word.length() > 1) {
                    word = word.substring(word.length() - 1, word.length());
                }
                Rectangle2D.Float rectangle = textRenderInfo.getAscentLine().getBoundingRectange();

                float x = (float) rectangle.getX();
                float y = (float) rectangle.getY();
//                float x = (float)rectangle.getCenterX();
//                float y = (float)rectangle.getCenterY();
//                double x = rectangle.getMinX();
//                double y = rectangle.getMaxY();

                //这两个是关键字在所在页面的XY轴的百分比
                float xPercent = Math.round(x / pageWidth * 10000) / 10000f;
                float yPercent = Math.round((1 - y / pageHeight) * 10000) / 10000f;

//                CharPosition charPosition = new CharPosition(pageNum, xPercent, yPercent);
                CharPosition charPosition = new CharPosition(pageNum, (float) x, (float) y);
                charPositions.add(charPosition);
                contentBuilder.append(word);
            }
        }

        @Override
        public void endTextBlock() {
        }

        @Override
        public void renderImage(ImageRenderInfo renderInfo) {
        }

        public String getContent() {
            return contentBuilder.toString();
        }

        public List<CharPosition> getcharPositions() {
            return charPositions;
        }
    }

    private static class CharPosition {
        private int pageNum = 0;
        private float x = 0;
        private float y = 0;

        public CharPosition(int pageNum, float x, float y) {
            this.pageNum = pageNum;
            this.x = x;
            this.y = y;
        }

        public int getPageNum() {
            return pageNum;
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        @Override
        public String toString() {
            return "[pageNum=" + this.pageNum + ",x=" + this.x + ",y=" + this.y + "]";
        }
    }

    /**
     * pdf分割-每个pdf只有一页
     *
     * @param pdfFile：源pdf文件地址
     * @param splitPdfFile：pdf分割文件地址
     */
    public static void splitPdf(String pdfFile, String splitPdfFile) {
        if (!pdfFile.endsWith(".pdf") || !splitPdfFile.endsWith(".pdf")) {
            //输入pdf或者输出pdf不是pdf文件
        }
        if (StringUtils.isBlank(pdfFile) || StringUtils.isBlank(splitPdfFile)) {
            // 输入pdf或者输出pdf地址为空
        }
        try {
            PdfReader reader = new PdfReader(pdfFile);
            int n = reader.getNumberOfPages();
            int i = 0;
            String initOutPath = splitPdfFile.substring(0, splitPdfFile.length() - 4);
            while (i < n) {
                String outFile = initOutPath + i + ".pdf";
                Document document = new Document(reader.getPageSizeWithRotation(1));
                PdfCopy writer = new PdfCopy(document, new FileOutputStream(outFile));
                document.open();
                PdfImportedPage page = writer.getImportedPage(reader, ++i);
                writer.addPage(page);
                document.close();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * pdf分割-可指定开始页及分割长度
     *
     * @param pdfFile：源pdf文件地址
     * @param splitPdfFile：pdf分割文件地址
     * @param startSplit：分割开始页码
     * @param splitSize：分割长度
     */
    public static List<String> splitPdf(String pdfFile, String splitPdfFile, int startSplit, int splitSize) {
        if (!pdfFile.endsWith(".pdf") || !splitPdfFile.endsWith(".pdf")) {
            //输入pdf或者输出pdf不是pdf文件
        }
        if (StringUtils.isBlank(pdfFile) || StringUtils.isBlank(splitPdfFile)) {
            // 输入pdf或者输出pdf地址为空
        }
        if (ObjectUtil.isEmpty(startSplit)) {
            startSplit = 1;
        }
        if (ObjectUtil.isEmpty(splitSize)) {
            splitSize = 1;
        }
        List<String> filePaths = new ArrayList<>(10);
        try {
            PdfReader reader = new PdfReader(pdfFile);
            int n = reader.getNumberOfPages();
            int i = 0;
            while (i < n) {
                String outFile = splitPdfFile.substring(0, splitPdfFile.length() - 4) + i + ".pdf";
                filePaths.add(outFile);
                Document document = new Document(reader.getPageSizeWithRotation(1));
                PdfCopy writer = new PdfCopy(document, new FileOutputStream(outFile));
                document.open();
                int a = startSplit + i;
                int b = splitSize + i;
                if (b > n) {
                    b = n;
                }
                for (int j = a; j <= b; j++) {
                    document.newPage();
                    PdfImportedPage page = writer.getImportedPage(reader, j);
                    writer.addPage(page);
                    ++i;
                }
                document.close();
                writer.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filePaths;
    }
}
