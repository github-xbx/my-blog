package com.xingbingxuan.blog.utils;

import com.sun.istack.internal.Nullable;
import lombok.SneakyThrows;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * 序列化工具类
 * @author : xbx
 * @date : 2022/7/17 11:07
 */
public class SerializeUtil {

    /**
     * 功能描述:
     * <p>序列化key</p>
     *
     * @param string
     * @return : byte[]
     * @author : xbx
     * @date : 2022/7/16 23:49
     */
    public static byte[] serializeKey(@Nullable String string) {
        return string == null ? null : string.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 功能描述:
     * <p>反序列化key</p>
     *
     * @param bytes
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/7/17 11:07
     */
    public static String deserializeKey(@Nullable byte[] bytes) {
        return bytes == null ? null : new String(bytes, StandardCharsets.UTF_8);
    }
    /**
     * 功能描述:
     * <p>序列化对象</p>
     *
     * @param object
     * @return : byte[]
     * @author : xbx
     * @date : 2022/7/16 17:50
     */
    public static byte[] serializeObject(Object object) throws Exception {
        if (object == null) {
            return new byte[0];
        }
        if (!(object instanceof Serializable)) {
            throw new IllegalArgumentException("类型不能序列化，请检查代码！！！");
        }

        try {
            return serialize(object);
        } catch (Exception e) {
            throw new Exception("Failed to serialize object", e);
        }

    }
    /**
     * 功能描述:
     * <p>反序列化字符串</p>
     *
     * @param bytes
     * @return : java.lang.String
     * @author : xbx
     * @date : 2022/7/16 17:52
     */
    @SneakyThrows
    public static  Object deserializeObject(byte[] bytes) {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return  deserialize(bytes);
        } catch (Exception e) {
            throw new Exception("Failed to deserialize payload",e);

        }
    }

    /**
     * 功能描述:
     * <p>序列化</p>
     *
     * @param state
     * @return : byte[]
     * @author : xbx
     * @date : 2022/7/16 18:06
     */
    private static byte[] serialize(Object state) {
        ObjectOutputStream oos = null;
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream(512);
            oos = new ObjectOutputStream(bos);
            oos.writeObject(state);
            oos.flush();
            return bos.toByteArray();
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    // eat it
                }
            }
        }
    }

    /**
     * 功能描述:
     * <p>反序列化</p>
     *
     * @param byteArray
     * @return : T
     * @author : xbx
     * @date : 2022/7/16 18:06
     */
    private static Object deserialize(byte[] byteArray) {
        ObjectInputStream oip = null;
        try {
            ByteArrayInputStream bos = new ByteArrayInputStream(byteArray);
            oip = new ObjectInputStream(bos);
            Object result =  oip.readObject();
            return result;
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        } finally {
            if (oip != null) {
                try {
                    oip.close();
                } catch (IOException e) {
                    // eat it
                }
            }
        }
    }
}
