package com.example.bootdemo.utils.sign;

import com.alibaba.fastjson.JSONObject;
import com.example.bootdemo.utils.constant.SysStringConstant;
import com.example.bootdemo.utils.log.LoggerUtil;
import com.sun.org.apache.xml.internal.security.utils.Base64;
import sun.misc.BASE64Decoder;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import static com.example.bootdemo.utils.constant.SysStringConstant.KEY_ALGORITHM;

public class RSAUtil {

    /**
     * 私钥
     */
    private RSAPrivateKey privateKey;

    /**
     * 公钥
     */
    private RSAPublicKey publicKey;

    /**
     * SHA256WithRSA私钥加密
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String signSHA256(String content, String privateKey) throws Exception {
        String charset = SysStringConstant.CHARSET;
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
        KeyFactory keyf = KeyFactory.getInstance("RSAUtil");
        PrivateKey priKey = keyf.generatePrivate(priPKCS8);

        java.security.Signature signature = java.security.Signature.getInstance(SysStringConstant.SIGN_ALGORITHMS256);
        signature.initSign(priKey);
        signature.update(content.getBytes(charset));
        byte[] signed = signature.sign();
        return Base64.encode(signed);
    }

    /**
     * SHA1WithRSA私钥加密
     * @param content
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static String signSHA1(String content, String privateKey) throws Exception {
        String charset = SysStringConstant.CHARSET;
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
        KeyFactory keyf = KeyFactory.getInstance("RSAUtil");
        PrivateKey priKey = keyf.generatePrivate(priPKCS8);

        java.security.Signature signature = java.security.Signature.getInstance(SysStringConstant.DEFAULT_RSA_ALGORITHM);
        signature.initSign(priKey);
        signature.update(content.getBytes(charset));
        byte[] signed = signature.sign();
        return Base64.encode(signed);
    }

    /**
     * RSA公钥解密
     * @param content      待解密字符串
     * @param signature    加密结果
     * @param publicKey    公钥
     * @param alogrithm    加密类型（SHA256WithRSA或者SHA1WithRSA）
     * @param charset      编码格式（UTF-8）
     * @return
     */
    private static boolean rsaDecrypt(String content, String signature, String publicKey, String alogrithm, String charset) {
        LoggerUtil.info(RSAUtil.class, "content:" + content + "\n--signature:" + signature + "\n--publicKey:" + publicKey + "\n--alogrithm:" + alogrithm + "\n--charset:" + charset);
        try {
            BASE64Decoder base = new BASE64Decoder();
            KeyFactory keyFactory = KeyFactory.getInstance("RSAUtil");
            byte[] encodedKey = base.decodeBuffer(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature sign = java.security.Signature.getInstance(alogrithm);
            sign.initVerify(pubKey);
            sign.update(content.getBytes(charset));
            boolean bverify = sign.verify(base.decodeBuffer(signature));
            return bverify;
        } catch (NoSuchAlgorithmException e) {
            LoggerUtil.error(RSAUtil.class, e.getMessage());
            return false;
        } catch (IOException e) {
            LoggerUtil.error(RSAUtil.class, e.getMessage());
            return false;
        } catch (InvalidKeySpecException e) {
            LoggerUtil.error(RSAUtil.class, e.getMessage());
            return false;
        } catch (InvalidKeyException e) {
            LoggerUtil.error(RSAUtil.class, e.getMessage());
            return false;
        } catch (SignatureException e) {
            LoggerUtil.error(RSAUtil.class, e.getMessage());
            return false;
        }
    }

    /**
     * 生成一对rsa公私钥
     * @return
     */
    public static String getPubAndPriKey() throws Exception {
        //获得对象 KeyPairGenerator 参数 RSA 1024个字节
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);
        //通过对象 KeyPairGenerator 获取对象KeyPair
        KeyPair keyPair = keyPairGen.generateKeyPair();

        //通过对象 KeyPair 获取RSA公私钥对象RSAPublicKey RSAPrivateKey
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        //公私钥对象存入map中
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(SysStringConstant.PUBLIC_KEY, publicKey);
        keyMap.put(SysStringConstant.PRIVATE_KEY, privateKey);
        //获得map中的公钥对象 转为key对象
        Key keyPub = (Key) keyMap.get(SysStringConstant.PUBLIC_KEY);
        //编码返回字符串
        String pubKey =  Base64Util.encryptBASE64(keyPub.getEncoded());
        //获得map中的私钥对象 转为key对象
        Key keyPri = (Key) keyMap.get(SysStringConstant.PRIVATE_KEY);
        //编码返回字符串
        String priKey = Base64Util.encryptBASE64(keyPri.getEncoded());
        JSONObject jo = new JSONObject();
        jo.put("publicKey", pubKey);
        jo.put("privateKey", priKey);
        return jo.toJSONString();
    }










    //*****************************  下方是main方法，用来rsa加解密的  *********************************************************

    /**
     * 从字符串中加载公钥
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public void loadPublicKey(String publicKeyStr) throws Exception{
        try {
            BASE64Decoder base64Decoder= new BASE64Decoder();
            byte[] buffer= base64Decoder.decodeBuffer(publicKeyStr);
            KeyFactory keyFactory= KeyFactory.getInstance("RSA");
            X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);
            this.publicKey= (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (IOException e) {
            throw new Exception("公钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }



    public void loadPrivateKey(String privateKeyStr) throws Exception{
        try {
            BASE64Decoder base64Decoder= new BASE64Decoder();
            byte[] buffer= base64Decoder.decodeBuffer(privateKeyStr);
            PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(buffer);
            KeyFactory keyFactory= KeyFactory.getInstance("RSA");
            this.privateKey= (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (IOException e) {
            throw new Exception("私钥数据内容读取错误");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * 加密过程
     * @param publicKey 公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception{
        if(publicKey== null){
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher= null;
        try {
            cipher= Cipher.getInstance("RSA"/*, new BouncyCastleProvider()*/);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output= cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 解密过程
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception{
        if (privateKey== null){
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher= null;
        try {
            cipher= Cipher.getInstance("RSA"/*, new BouncyCastleProvider()*/);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output= cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }


    /**
     * 字节数据转十六进制字符串
     * @param data 输入数据
     * @return 十六进制内容
     */
    public static String byteArrayToString(byte[] data){
        StringBuilder stringBuilder= new StringBuilder();
        for (int i=0; i<data.length; i++){
            //取出字节的高四位 作为索引得到相应的十六进制标识符 注意无符号右移
            stringBuilder.append(HEX_CHAR[(data[i] & 0xf0)>>> 4]);
            //取出字节的低四位 作为索引得到相应的十六进制标识符
            stringBuilder.append(HEX_CHAR[(data[i] & 0x0f)]);
            if (i<data.length-1){
                stringBuilder.append(' ');
            }
        }
        return stringBuilder.toString();
    }



    public static void main(String[] args){
        RSAUtil rsaEncrypt= new RSAUtil();
        // rsaEncrypt.genKeyPair();


        //加载公钥
        try {
            rsaEncrypt.loadPublicKey(RSAUtil.DEFAULT_PUBLIC_KEY);
            System.out.println("加载公钥成功");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("加载公钥失败");
        }

        //加载私钥
        try {
            rsaEncrypt.loadPrivateKey(RSAUtil.DEFAULT_PRIVATE_KEY);
            System.out.println("加载私钥成功");
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.err.println("加载私钥失败");
        }
        System.out.println("秘钥长度分别为:"+DEFAULT_PRIVATE_KEY.length()+","+DEFAULT_PUBLIC_KEY.length());
        //测试字符串
        String encryptStr= "panpan10810814";
        try {
            //公钥加密
            String cipher = new String(org.apache.commons.codec.binary.Base64.encodeBase64(rsaEncrypt.encrypt(rsaEncrypt.getPublicKey(), encryptStr.getBytes())));
            System.out.println(cipher);
            String str = new String (rsaEncrypt.decrypt(rsaEncrypt.getPrivateKey(), org.apache.commons.codec.binary.Base64.decodeBase64(cipher.getBytes())));
            System.out.println(str);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static final String DEFAULT_PRIVATE_KEY=
            "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMVuBfEyarwY4/5Nzt/ete2pQKWt" +
                    "\n2nr1hykfOTKOd2Lrd/0ua8YtfzvwlCa+8AnruHoWhOV+BQMReqgiCGk/WYWYheTaUZfstMJ+t13a" +
                    "\nrMBeYFSikNRJ4O/8gNHFDLR9vEYC93XIDh4K4YhFPISPAoUWSP3cwtegPoVyHbhoXrZLAgMBAAEC" +
                    "\ngYAkRTpCPYjuVYRNS+4dosS5jMabqXzh6gmSvHG9n/6+5ZN9p/GUzvb5BBGzrJBcwjl59Hkl0CkL" +
                    "\nj0KBcP8qlsDTP4K/iNcTGqjfinfitNg0uBHY7BfBxGID2k/NdOV1l7g2iS2Oiv6ThLdFfcmFQ8Mn" +
                    "\nKbLCKi3FrcsduELJ8Sx7wQJBAPJ/gk6bzwnHN6zGg8Ze/B4Sk1x9AnYXBDFSZESCySh1ABKi32M5" +
                    "\nTuEGYwiV1upYC8cE8cJe71r9TrdcxFe06SUCQQDQbB/14cG0k4NDv+xZePBNF3ja5nLS25s+me+H" +
                    "\nR94gFXiZUo1d6x+4gqWN+tAPplRLe5Z1kKWEAS5+L/tLjR6vAkEA0nGJegKJF+lFDbFxJjPEA60H" +
                    "\nKVprSmQLBWqFDVeajnuKxqGFzywqoenTA95VFiW2gs5tp8qPWZ0+NK5SzfJ36QJBAKniaE64XHa/" +
                    "\nAnC3wIPRgzWjWzw6OP/MRwdI3CgNmW3XcnyvAFG8dBemTiGjffIzpmP8cdCUPYWSnP34SEH13jsC" +
                    "\nQA4wR4ER1a+IgEsbeGFJV72yMuWxmkZ0jbUijsD/JTnoLzjLVRd8vXroH3nc5Z3nwrHkfF+CTEJz" +
                    "\nG0EU2eOQg8M=\n";

    private static final String DEFAULT_PUBLIC_KEY=
            "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFbgXxMmq8GOP+Tc7f3rXtqUClrdp69YcpHzky" +
                    "\njndi63f9LmvGLX878JQmvvAJ67h6FoTlfgUDEXqoIghpP1mFmIXk2lGX7LTCfrdd2qzAXmBUopDU" +
                    "\nSeDv/IDRxQy0fbxGAvd1yA4eCuGIRTyEjwKFFkj93MLXoD6Fch24aF62SwIDAQAB\n";

    private static final char[] HEX_CHAR= {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public RSAPrivateKey getPrivateKey() {
        return privateKey;
    }

    public RSAPublicKey getPublicKey() {
        return publicKey;
    }

}
