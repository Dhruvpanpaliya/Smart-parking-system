package com.parking.smartparking.util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

/**
 * Utility class for generating QR codes as Base64-encoded PNG strings
 * using the ZXing library.
 */
public final class QRUtil {

    private static final Logger logger = LoggerFactory.getLogger(QRUtil.class);

    private QRUtil() {
        // Prevent instantiation
    }

    /**
     * Generates a QR code from the given text and returns it as a Base64-encoded PNG string.
     *
     * @param text   the text to encode in the QR code
     * @param width  the width of the QR code image in pixels
     * @param height the height of the QR code image in pixels
     * @return Base64-encoded PNG string of the QR code, or null on error
     */
    public static String generateQRCodeBase64(String text, int width, int height) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);

            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "PNG", outputStream);

            byte[] imageBytes = outputStream.toByteArray();
            return Base64.getEncoder().encodeToString(imageBytes);

        } catch (WriterException | IOException e) {
            logger.error("Error generating QR code for text: {}", text, e);
            return null;
        }
    }
}
