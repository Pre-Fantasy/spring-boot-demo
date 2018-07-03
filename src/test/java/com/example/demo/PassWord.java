package com.example.demo;

import com.example.demo.model.SysPerm;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PassWord {

    public static void main(String[] arges) {
        String md5 = "md5";
        String password = "123456";
        int hashIterations = 1024;
        String hash = "awwwwwwww==";
        ByteSource credentialsSalt = ByteSource.Util.bytes(hash);
        String newPassword = new SimpleHash(md5, password, credentialsSalt, hashIterations).toHex();
        System.out.println(newPassword);

    }
}
