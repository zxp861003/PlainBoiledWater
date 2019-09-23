package test;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public class Test {
	
	// 测试主函数
	public static void main(String args[]) throws UnsupportedEncodingException {

//		String ss = "{\"respCode\":200,\"games\"[" 
//				+"{\"gameId\":2,\"status\":1,\"version\":1466478677401,\"logServer\":\"\",\"logPort\":0,\"servers\":["
//				+ "{\"gameId\":2,\"areaId\":1,\"name\":\"暗黑测试1区\",\"address\":\"192.168.4.177\",\"port\":28100,\"gmPort\":8877,\"status\":1,\"message\":\"\"},"
//				+ "{\"gameId\":2,\"areaId\":2,\"name\":\"暗黑测试2区\",\"address\":\"192.168.4.176\",\"port\":28100,\"gmPort\":8877,\"status\":1,\"message\":\"\"},"
//				+ "{\"gameId\":2,\"areaId\":5111,\"name\":\"216测试服\",\"address\":\"192.168.14.216\",\"port\":28100,\"gmPort\":8877,\"status\":1,\"message\":\"\"}],"
//				+ "\"myservers\":["
//				+ "{\"gameId\":2,\"areaId\":1,\"name\":\"暗黑测试1区\",\"address\":\"192.168.4.177\",\"port\":28100,\"gmPort\":8877,\"status\":1,\"message\":\"\"},"
//				+ "{\"gameId\":2,\"areaId\":5111,\"name\":\"216测试服\",\"address\":\"192.168.14.216\",\"port\":28100,\"gmPort\":8877,\"status\":1,\"message\":\"\"}]}"
//				+ "]}";
//
//		
//		String doUrl = "http://localhost:8080/ucenter/servers.action?m=deploy";
//		
////		System.out.println(deployJson);
//		byte[] deployData = ss.getBytes("utf-8");
//		UrlCatcher.urlPost(doUrl, deployData, "text/html", "utf-8");
		
		
		
//		String doUrl = "http://localhost:8080/channel/checkLogin.action";
//		String tmpParams =  "sid=" + "12312"  + "&gameId=" + "2" ;
//
//		tmpParams = MD5Utils.convertMD5(tmpParams);
//		byte[] paramsData = StrUtils.stringToBytes(tmpParams, "utf-8");
//
//		String urlResponseText = UrlCatcher.urlPost(doUrl, paramsData, "text/html", "utf-8");
//		System.out.println(urlResponseText);
		
		
//		String ss = UUID.randomUUID().toString().replaceAll("-", "");
		
		System.out.println(str2HexStr("7d5b82f904d447b1aa40cd7790f1c0d5"));
	}
	
	public static String str2HexStr(String str)    
	{      
	  
	    char[] chars = "0123456789ABCDEF".toCharArray();      
	    StringBuilder sb = new StringBuilder("");    
	    byte[] bs = str.getBytes();      
	    int bit;      
	        
	    for (int i = 0; i < bs.length; i++)    
	    {      
	        bit = (bs[i] & 0x0f0) >> 4;      
	        sb.append(chars[bit]);      
	        bit = bs[i] & 0x0f;      
	        sb.append(chars[bit]);    
	        sb.append(' ');    
	    }      
	    return sb.toString().trim();      
	}   
}
