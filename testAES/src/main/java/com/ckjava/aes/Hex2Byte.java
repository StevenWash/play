package com.ckjava.aes;

public class Hex2Byte {


	public static void main(String[] args) {
		String str = "33906F4FE05C5019D48CF9AFE866CF60";
/*		
		byte[] result = new byte[str.length() / 2];
        for (int i = 0; i < result.length; i++)
        {
        	
            result[i] = Convert.ToByte(str.substring(i * 2, 2), 16);
        }*/
	}
	
	public static byte[] hexStringToByte(String hex) {  
	    byte[] b = new byte[hex.length() / 2];  
	    int j = 0;  
	    for (int i = 0; i < b.length; i++) {  
	        char c0 = hex.charAt(j++);  
	        char c1 = hex.charAt(j++);  
	        b[i] = (byte) ((parse(c0) << 4) | parse(c1));  
	    }  
	    return b;  
	}  
	
	private static int parse(char c) {  
	    if (c >= 'a')  
	        return (c - 'a' + 10) & 0x0f;  
	    if (c >= 'A')  
	        return (c - 'A' + 10) & 0x0f;  
	    return (c - '0') & 0x0f;  
	} 
}
