package com.course.graphql.util;

import org.bouncycastle.crypto.generators.OpenBSDBCrypt;

import java.nio.charset.StandardCharsets;

public class HashUtil {

    private static final String BCRYPT_SALT = "dontDoThisOnProd";


    public static boolean isBcryptMatch(String original, String hashed) {
        //return true;
        return OpenBSDBCrypt.checkPassword(hashed, original.getBytes(StandardCharsets.UTF_8));
    }

    public static String hashBCrypt(String original) {
        return OpenBSDBCrypt.generate(original.getBytes(StandardCharsets.UTF_8),
                BCRYPT_SALT.getBytes(StandardCharsets.UTF_8), 5);
    }

}
