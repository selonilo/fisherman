package com.sc.fisherman.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class FileEditor {

    @Value("${upload.path}")
    private static String uploadPath;

    public static String saveFile(MultipartFile file) throws IOException {
        // 📌 Tüm dosyaları JPG olarak kaydedeceğiz
        String fileName = System.currentTimeMillis() + ".jpg";
        Path filePath = Paths.get(uploadPath, fileName);
        Files.createDirectories(filePath.getParent());

        // 1️⃣ MultipartFile'i BufferedImage olarak oku
        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null) {
            throw new RuntimeException("Görsel okunamadı.");
        }

        // 2️⃣ PNG ise arka planı beyaza çevir (çünkü JPG transparan desteklemez)
        if ("png".equalsIgnoreCase(getFileExtension(file.getOriginalFilename()))) {
            BufferedImage newImage = new BufferedImage(
                    image.getWidth(), image.getHeight(),
                    BufferedImage.TYPE_INT_RGB
            );
            Graphics2D g2d = newImage.createGraphics();
            g2d.setColor(Color.WHITE); // Beyaz arka plan
            g2d.fillRect(0, 0, newImage.getWidth(), newImage.getHeight());
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();
            image = newImage;
        }

        int targetWidth = 1080;
        if (image.getWidth() > targetWidth) {
            int targetHeight = (int) (((double) targetWidth / image.getWidth()) * image.getHeight());
            BufferedImage resized = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2dResize = resized.createGraphics();
            g2dResize.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g2dResize.drawImage(image, 0, 0, targetWidth, targetHeight, null);
            g2dResize.dispose();
            image = resized;
        }

        // 4️⃣ JPG olarak daha düşük kaliteyle kaydet (%30 kalite)
        try (OutputStream os = Files.newOutputStream(filePath)) {
            ImageWriter writer = ImageIO.getImageWritersByFormatName("jpg").next();
            ImageOutputStream ios = ImageIO.createImageOutputStream(os);
            writer.setOutput(ios);

            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(0.3f); // %30 kalite
            }

            writer.write(null, new IIOImage(image, null, null), param);
            ios.close();
            writer.dispose();
        }
        return "/fisherman/uploads/" + fileName;
    }

    // 📌 Yardımcı metod
    private static String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) return "";
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
