/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mahoahiendai;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 *
 * @author Admin
 */
public class xulyFile {
    // Đọc toàn bộ nội dung của một file vào một mảng byte.
    public  static byte[] docFile (String tenFile )throws IOException{
        // Chuyển đổi đường dẫn (String) thành một đối tượng Path
        Path path = Paths.get(tenFile);
        // Đọc tất cả byte từ file
        return Files.readAllBytes(path);
    }
    public static void ghiFile(String tenFile, byte[] data) throws IOException {
        // Chuyển đổi đường dẫn
        Path path = Paths.get(tenFile);
        
        // Ghi chuỗi byte vào file
        Files.write(path, data);
    }
    public static void doiTenFile(String duongDanCu, String duongDanMoi) throws IOException {
        Path pathCu = Paths.get(duongDanCu);
        Path pathMoi = Paths.get(duongDanMoi);

        Files.move(pathCu, pathMoi, StandardCopyOption.REPLACE_EXISTING);
    }
    public static String layTenFile(String path) {
        return Paths.get(path).getFileName().toString();
    }

    // Lấy đuôi file (bao gồm dấu chấm)
    public static String layDuoiFile(String tenFile) {
        int p = tenFile.lastIndexOf(".");
        if (p == -1) return "";   // không có đuôi
        return tenFile.substring(p);  // ví dụ .png
    }

    // Xóa đuôi file
    public static String xoaDuoiFile(String tenFile) {
        int p = tenFile.lastIndexOf(".");
        if (p == -1) return tenFile;  // không có đuôi
        return tenFile.substring(0, p);
    }

    // Đổi đuôi file
    public static String doiDuoiFile(String tenFile, String duoiMoi) {
        return xoaDuoiFile(tenFile) + duoiMoi; 
    }
}
