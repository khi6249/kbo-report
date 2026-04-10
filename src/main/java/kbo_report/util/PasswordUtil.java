package kbo_report.util;

import java.security.MessageDigest;

public class PasswordUtil {
    public static String encrypt(String password) {
        try {
            // SHA-256 알고리즘 사용
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString(); // 64자리의 암호화된 문자열 반환
        } catch (Exception e) {
            throw new RuntimeException("암호화 중 에러 발생", e);
        }
    }
}