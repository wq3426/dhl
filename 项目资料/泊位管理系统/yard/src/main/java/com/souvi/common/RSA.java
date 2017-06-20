package com.souvi.common;

public class RSA {
	public int privateKey = 20643;
	public int publicKey = 32823;
	public int modulus = 29893;
	
	public int crypt(int pLngMessage, int pLngKey){
		int lLngMod;
		int lLngResult;
		int lLngIndex;
		if(pLngKey % 2 == 0){
			lLngResult = 1;
			for(lLngIndex = 0;  lLngIndex < pLngKey / 2; lLngIndex++){
				lLngMod = (pLngMessage * pLngMessage) % modulus;
				//Mod may error on key generation
				lLngResult = (lLngMod * lLngResult) % modulus; 
			}
		}else{
			lLngResult = pLngMessage;
			for(lLngIndex = 0;  lLngIndex < pLngKey / 2; lLngIndex++){
				lLngMod = (pLngMessage * pLngMessage) % modulus;
				//Mod may error on key generation
				lLngResult = (lLngMod * lLngResult) % modulus;
			}
		}
		return lLngResult;
	}
	
	public String encode(String message){
		int lLngIndex;
		int lLngMaxIndex;
		int lBytAscii;
		int lLngEncrypted;
		String encodeStr = "";
		lLngMaxIndex = message.length();
		if(lLngMaxIndex == 0){
			return "";
		}
		for(lLngIndex = 0; lLngIndex < lLngMaxIndex ; lLngIndex++){
			char c=message.charAt(lLngIndex);
			lBytAscii = (byte)c;
			lLngEncrypted = crypt(lBytAscii, publicKey);
			encodeStr += numberToHex(lLngEncrypted, 4);
		}
		return encodeStr.toUpperCase();
	}
	
	public String decode(String message){
		int lLngIndex;
		int lLngMaxIndex;
		int lBytAscii;
		int lLngEncrypted;
		String encodeStr = "";
		lLngMaxIndex = message.length();
		if(lLngMaxIndex == 0){
			return "";
		}
		for(lLngIndex = 0; lLngIndex < lLngMaxIndex ; lLngIndex+=4){
			lLngEncrypted = hexToNumber(message.substring(lLngIndex,lLngIndex+4));
			lBytAscii = crypt(lLngEncrypted, privateKey);
			encodeStr += (char)lBytAscii;
		}
		return encodeStr;
	}
	
	private String numberToHex(int lLngEncrypted, int pLngLength){
		String hexForInt = Integer.toHexString(lLngEncrypted);
		for(int i = hexForInt.length() ; i < pLngLength; i++){
			hexForInt = "0" + hexForInt;
		}
		return hexForInt;
	}
	
	private int hexToNumber(String pStrHex){
		int hexForInt = Integer.parseInt(pStrHex,16);
		return hexForInt;
	}
	
	public static void main(String[]args){		
		System.out.println(new RSA().encode("Welcome2"));
		System.out.println(new RSA().decode("21232F297A57A5A743894A0E4A801FC3"));
		System.out.println(new RSA().decode("68AD6B91143F1C7A615C1FC84F885C1F1089"));
		String aa="13840105205/15041238016/024-62336757/62336756/15840064789/024 31693591-16";
		System.out.println(aa.length());
	}
}
