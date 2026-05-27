package com.parking.smartparking.service;

import com.parking.smartparking.util.QRUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for QR code generation.
 * Delegates to the QRUtil utility class for Base64-encoded QR image creation.
 */
@Service
@RequiredArgsConstructor
public class QRService {

    /**
     * Generates a Base64-encoded QR code image for a booking.
     * The QR content follows the format: BOOKING-{bookingId}-{vehicleNumber}
     *
     * @param bookingId     the booking ID to encode
     * @param vehicleNumber the vehicle number to encode
     * @return the Base64-encoded QR code image string
     */
    public String generateQRCode(Long bookingId, String vehicleNumber) {
        String qrText = "BOOKING-" + bookingId + "-" + vehicleNumber;
        return QRUtil.generateQRCodeBase64(qrText, 250, 250);
    }
}
