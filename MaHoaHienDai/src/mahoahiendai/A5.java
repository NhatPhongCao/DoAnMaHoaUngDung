/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mahoahiendai;

import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Base64;

/**
 *
 * @author Admin
 */
public class A5 {
    private int[] R1 = new int[19];
    private int[] R2 = new int[22];
    private int[] R3 = new int[23];

    // Vị trí bit đồng bộ hóa
    private final int R1_CLOCK_BIT = 8;
    private final int R2_CLOCK_BIT = 10;
    private final int R3_CLOCK_BIT = 10;

    public A5(int[] key) {
        // Khởi tạo các thanh ghi với khóa đầu vào (giả lập)
        for (int i = 0; i < 19; i++) R1[i] = key[i % key.length];
        for (int i = 0; i < 22; i++) R2[i] = key[i % key.length];
        for (int i = 0; i < 23; i++) R3[i] = key[i % key.length];
    }

    private int timBit(int x, int y, int z) {
        // Bit có số lần xuất hiện nhiều nhất
        return (x + y + z) > 1 ? 1 : 0;
    }

    private void dichThanhGhi(int[] reg, int[] taps) {
        int feedback = 0;
        // Tính toán bit phản hồi
        for (int tap : taps) {
            feedback ^= reg[tap];
        }
        // Dịch toàn bộ thanh ghi sang phải
        System.arraycopy(reg, 0, reg, 1, reg.length - 1);
        // Gán bit phản hồi vào đầu thanh ghi
        reg[0] = feedback;
    }

    private void clock() {
        int m = timBit(R1[R1_CLOCK_BIT], R2[R2_CLOCK_BIT], R3[R3_CLOCK_BIT]);
        // Đồng bộ hóa và dịch thanh ghi
        if (R1[R1_CLOCK_BIT] == m) dichThanhGhi(R1, new int[]{13, 16, 17, 18});
        if (R2[R2_CLOCK_BIT] == m) dichThanhGhi(R2, new int[]{20, 21});
        if (R3[R3_CLOCK_BIT] == m) dichThanhGhi(R3, new int[]{7, 20, 21, 22});
    }

    public int bitMaHoa() {
        // Tạo một bit mã hóa
        clock();
        return R1[18] ^ R2[21] ^ R3[22];
    }

    public int[] maHoa(int length) {
        // Mã hóa / giải mã dữ liệu
        int[] stream = new int[length];
        for (int i = 0; i < length; i++) {
            stream[i] = bitMaHoa();
        }
        return stream;
    }
    public int[] giaiMa(int[] ciphertext) {
        int[] keystream = maHoa(ciphertext.length);
        int[] plaintext = new int[ciphertext.length];
        for (int i = 0; i < ciphertext.length; i++) {
            plaintext[i] = ciphertext[i] ^ keystream[i];
        }
        return plaintext;
    }
    public static int[] doiInt(byte[] bytes) {
    int[] bits = new int[bytes.length * 8];
    for (int i = 0; i < bytes.length; i++) {
        for (int bit = 0; bit < 8; bit++) {
            // Lấy từng bit từ trái sang phải
            bits[i * 8 + bit] = (bytes[i] >> (7 - bit)) & 1;
        }
    }
    return bits;
    }
    public static byte[] doiByte(int[] bits) {
    int byteLength = bits.length / 8;
    byte[] bytes = new byte[byteLength];
    for (int i = 0; i < byteLength; i++) {
        byte value = 0;
        for (int bit = 0; bit < 8; bit++) {
            value <<= 1;                   // Dịch trái 1 bit
            value |= (bits[i * 8 + bit] & 1); // Thêm bit mới
        }
        bytes[i] = value;
    }
    return bytes;
    }  
    public static String maHoaTen(String ten, A5 a5) {
        byte[] bytes = ten.getBytes(StandardCharsets.UTF_8);
        int[] bits = doiInt(bytes);
        int[] enc = a5.maHoa(bits.length);
        for (int i = 0; i < bits.length; i++) bits[i] ^= enc[i];
        byte[] out = doiByte(bits);
        return Base64.getUrlEncoder().encodeToString(out);
    }
    public static String giaiMaTen(String tenMaHoa, A5 a5) {
        byte[] bytes = tenMaHoa.getBytes();
        int[] bits = A5.doiInt(bytes);
        int[] enc = a5.maHoa(bits.length);
        for (int i = 0; i < bits.length; i++) {
            bits[i] ^= enc[i];
        }
        byte[] out = A5.doiByte(bits);
        return Base64.getUrlEncoder().encodeToString(out);
    }
}
