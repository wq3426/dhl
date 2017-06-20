package com.souvi.common;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public final class StringUtil {
	
	/**
	 * String 工具类
	 * @param targetObject
	 * @return
	 */
	public static boolean isNull(String targetObject){
		if(targetObject == null 
				|| targetObject.trim().equals("") || targetObject.trim().equals("null")){
			return true;
		}
		return false;
	}
	/**
     * <p>Checks if a String is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(String str)
    {
        int strLen;
        if (str == null || (strLen = str.length()) == 0)
        {
            return true;
        }
        for (int i = 0; i < strLen; i++)
        {
            if ((Character.isWhitespace(str.charAt(i)) == false))
            {
                return false;
            }
        }
        return true;
    }
    /**
	 * 移动文件
	 * 被移动的文件夹  : srcfile
	 * 目标文件夹  :destfile
	 */
	public static void remove(File srcFile,File destfile){
		if(!destfile.exists()){
			destfile.mkdirs();
		}
		//将文件移动到另一个文件目录下  
		String datestr = DateUtil.getSystemDateCountTwo();
		srcFile.renameTo(new File(destfile,datestr+"_"+srcFile.getName()));  
	}
	/**
	 * 删除文件夹
	 * @param file
	 */
	public static void delete(File file) {
		if(file.isDirectory()) {
			File[] fs = file.listFiles();
			for (int i = 0; i < fs.length; i++) {
				delete(fs[i]);
			}
			file.delete();
		}else {
			file.delete();
		}
	}
	/**
	  * 字符串的压缩
	  * @param str 待压缩的字符串
	  * @return 返回压缩后的字符串
	  * @throws IOException
	  */
	 public static String compress(String str) throws IOException {
	     if (null == str || str.length() <= 0) {
	         return str;
	     }
	     // 创建一个新的输出流
	     ByteArrayOutputStream out = new ByteArrayOutputStream();
	     // 使用默认缓冲区大小创建新的输出流
	     GZIPOutputStream gzip = new GZIPOutputStream(out);
	     // 将字节写入此输出流
	     gzip.write(str.getBytes("utf-8"));  //因为后台默认字符集有可能是GBK字符集，所以此处需指定一个字符集
	     gzip.close();
	     // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
	     return out.toString("ISO-8859-1");
	 }
	 /**
	  * 字符串的解压
	  * @param str 对字符串解压
	  * @return 返回解压缩后的字符串
	  * @throws IOException
	  */
	public static String unCompress(String str) throws IOException {
	     if (null == str || str.length() <= 0) {
	         return str;
	     }
	     // 创建一个新的输出流
	     ByteArrayOutputStream out = new ByteArrayOutputStream();
	     // 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
	     ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
	     // 使用默认缓冲区大小创建新的输入流
	     GZIPInputStream gzip = new GZIPInputStream(in);
	     byte[] buffer = new byte[256];
	     int n = 0;

	     // 将未压缩数据读入字节数组
	     while ((n = gzip.read(buffer)) >= 0){
	           out.write(buffer, 0, n);
	     }
	     // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串 
	     return out.toString("utf-8"); 
	 }
}
