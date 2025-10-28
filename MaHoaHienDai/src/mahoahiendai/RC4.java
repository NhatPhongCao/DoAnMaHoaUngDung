/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mahoahiendai;

import java.io.IOException;



/**
 *
 * @author Admin
 */
public class RC4 {
    private byte[] S = new byte[256];
    private byte[] key;
    //Giai đoạn 1: khởi tạo
    public RC4 (byte[] key){
        //khởi tạo mảng hoán vị S với giá trị từ 0-255
        for (int i = 0; i < 256; i++) {
            S[i] = (byte) i;
        }
        //mảng key được nhập từ bên ngoài
        this.key = key;
        //hoán vị mảng S dựa trên key
        int j = 0 ;
        for (int i = 0 ; i <256;i++){
            //0xFF dùng để chuyển các số có dấu thành số nguyên không dấu
            //j được tính bằng j + giá trị thứ i trong mảng S + giá trị thứ i trong mảng key % 256
            j = (j+(S[i]&0xFF)+(key[i%key.length]&0xFF))%256;
            //hoán vị S[i] và S[j]
            byte tam = S[i];
            S[i] = S[j];
            S[j] = tam;
        }
    }
    
   
    //Giai đoạn 3: sinh số
    public byte[] sinhSo(byte[] data){
        //tạo mảng kết quả chứa key được sinh ra 
        byte[] output = new byte[data.length];
        //dùng mảng S đã bị xáo trộn ở trên để tạo ra một dòng byte khóa
        int i=0,j=0;
        for(int k = 0 ; k<data.length;k++){
            i = (i+1)%256;
            j =(j+(S[i]&0XFF))%256;
            //hoán vị S[i]và S[j]
            byte tam = S[i];
            S[i] = S[j];
            S[j] = tam;
            //sinh key
            int t = ((S[i]&0xFF)+(S[j]&0xFF))%256;
            byte s = S[t];
            //thực hiện phép XOR dữ liệu và key tương ứng
           output[k]=(byte)(data[k]^s);
        }
        return output;
    }
    
    public byte[] maHoa(byte [] plainText){
        
        return sinhSo(plainText);
    }
    public byte[] giaiMa(byte[]cipherText){
        
        return sinhSo(cipherText);
    }
    
}