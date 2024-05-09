package com.appium.challenges;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.openqa.selenium.NotFoundException;
import org.testng.annotations.Test;

import javax.imageio.ImageIO;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadQRCodeData {

    public static String getQRCodeData(String path) throws WriterException, IOException, NotFoundException, com.google.zxing.NotFoundException {
        String charset = "UTF-8";
        Map<EncodeHintType, ErrorCorrectionLevel> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        return readQRCode(path, charset, hintMap);
    }

    public static String readQRCode(String path, String charset, Map map) throws IOException, NotFoundException, com.google.zxing.NotFoundException {
        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(ImageIO.read(new FileInputStream(path)))));
        Result result = new MultiFormatReader().decode(binaryBitmap);
        return result.getText();
    }

    @Test
    public void readDataFromQRCodeFile() throws com.google.zxing.NotFoundException, IOException, WriterException {
        String data = getQRCodeData(System.getProperty("user.dir")+"/src/main/resources/test1.png");
        System.out.println(data);
    }
}
