package www.tq.weather.unit;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class RSAUnit {

    private static String tag = "RsaUnit";
    private static String RSA = "RSA";

    /*从assets中获取公钥*/
    public static PublicKey getPublicKeyFromAssets(Context context,String filename) {
        try {
            InputStream inputStream = context.getResources().getAssets().open(filename);
            String publicKeyStr = readKey(inputStream);
            byte[] buffer = Base64Unit.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            PublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            DbUnit.logd(tag, "-----getPublicKeyFromAssets---------publicKeyStr=" + publicKeyStr);
            inputStream.close();
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
            DbUnit.logd(tag, "-----getPublicKeyFromAssets-----Exception----e=" + e.toString());
        }
        return null;
    }

    /*从 InputStream 中获取公钥*/
    public static PublicKey getPublicKeyFromInputStream(Context context,InputStream inputStream ) {

        try {
            String publicKeyStr = readKey(inputStream);
            byte[] buffer = Base64Unit.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            PublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            DbUnit.logd(tag, "-----getPublicKeyFromInputStream---------publicKeyStr=" + publicKeyStr);
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
            DbUnit.logd(tag, "-----getPublicKeyFromInputStream-----Exception----e=" + e.toString());

        }
        return null;
    }

    /*根据字符串生成公钥*/
    public static PublicKey getPublicKeyFromString(Context context,String publicKeyStr) {

        try {
            byte[] buffer = Base64Unit.decode(publicKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            PublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(keySpec);
            DbUnit.logd(tag, "-----getPublicKeyFromString---------publicKeyStr=" + publicKeyStr);
            return publicKey;
        } catch (Exception e) {
            e.printStackTrace();
            DbUnit.logd(tag, "-----getPublicKeyFromString-----Exception----e=" + e.toString());
        }
        return null;
    }

    /*从assets文件夹中获取私钥*/
    public static PrivateKey getPrivateKeyFromAssets(Context context,String fileName) {

        try {
            InputStream inputStream = context.getResources().getAssets().open(fileName);
            String privateKeyStr = readKey(inputStream);
            byte[] buffer = Base64Unit.decode(privateKeyStr);
            // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            DbUnit.logd(tag, "-----getPrivateKey---------privateKeyStr=" + privateKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey privateKey= (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
            inputStream.close();
            return privateKey;
        } catch (Exception e) {
            DbUnit.logd(tag, "-----getPrivateKey----Exception-----e=" + e.toString());
            e.printStackTrace();
        }
        return null;

    }

    /*从文件流中中获取私钥*/
    public static PrivateKey getPrivateKeyFromInputStream(Context context, InputStream inputStream) {

        try {
            String privateKeyStr = readKey(inputStream);
            byte[] buffer = Base64Unit.decode(privateKeyStr);
            // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            DbUnit.logd(tag, "-----getPrivateKey---------privateKeyStr=" + privateKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey privateKey= (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (Exception e) {
            DbUnit.logd(tag, "-----getPrivateKey----Exception-----e=" + e.toString());
            e.printStackTrace();
        }
        return null;

    }

    /*从string文件夹中获取私钥*/
    public static PrivateKey getPrivateKeyFromString(Context context,String privateKeyStr) {

        try {
            byte[] buffer = Base64Unit.decode(privateKeyStr);
            // X509EncodedKeySpec keySpec = new X509EncodedKeySpec(buffer);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(buffer);
            DbUnit.logd(tag, "-----getPrivateKey---------privateKeyStr=" + privateKeyStr);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            PrivateKey privateKey= (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
            return privateKey;
        } catch (Exception e) {
            DbUnit.logd(tag, "-----getPrivateKey----Exception-----e=" + e.toString());
            e.printStackTrace();
        }
        return null;

    }
    /*先用公钥将数据加密,再将加密后的空字节数组经常base64加密*/
    public static String getEncryData(Context context, String source,PublicKey publicKey) {

        try {
            DbUnit.logd(tag, "------加密源数据-----source=" + source);
            byte[] encryptByte = null;
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            // 编码前设定编码方式及密钥
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            // 传入编码数据并返回编码结果
            encryptByte = cipher.doFinal(source.getBytes());
            // 为了方便观察吧加密后的数据用base64加密转一下，要不然看起来是乱码,所以解密是也是要用Base64先转换
            String afterencrypt = Base64Unit.getencode(encryptByte);
            DbUnit.logd(tag, "------加密后返回-----base64=" + afterencrypt);
            return afterencrypt;
        } catch (Exception e) {
            DbUnit.logd(tag, "-----getEncryData-----Exception----e=" + e.toString());
            e.printStackTrace();
        }
        return null;
    }

    /*RSA解密数据,先将数据进行Base64转换,再用私钥解密*/
    public static String getdecryptData(Context context, String data,PrivateKey privateKey) {
        try {
            DbUnit.logd(tag, "------解密源数据-----data=" + data);
            // 因为RSA加密后的内容经Base64再加密转换了一下，所以先Base64解密回来再给RSA解密
            byte[] base64bytes  = Base64Unit.decode(data);
            DbUnit.logd(tag, "------解密base64后的长度-----base64bytes length=" + base64bytes.length);

            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] decryptByte = cipher.doFinal(base64bytes);
            DbUnit.logd(tag, "解密后返回---decryptByte length=" + decryptByte.length);

            String decryptStr = new String(decryptByte);
            DbUnit.logd(tag, "解密后返回---return=" + decryptStr);
            return decryptStr;
        } catch (Exception e) {
            DbUnit.logd(tag, "-----getdecryptData-----Exception----e=" + e.toString());

            e.printStackTrace();
        }
        return null;
    }

    // public String get

    /**
     * 读取密钥信息
     *
     * @param in
     * @return
     * @throws IOException
     */
    private static String readKey(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String readLine = null;
        StringBuilder sb = new StringBuilder();
        while ((readLine = br.readLine()) != null) {
            if (readLine.charAt(0) == '-') {
                continue;
            } else {
                sb.append(readLine);
                sb.append('\r');
            }
        }

        return sb.toString();
    }
}
