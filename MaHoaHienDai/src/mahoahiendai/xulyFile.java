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
    
}
