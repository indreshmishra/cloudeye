package com.rwork.cloudeye.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class BytesUtil {

	public static byte[] toByteArray(Object obj) throws IOException{
		byte[] bytes=null;
		ByteArrayOutputStream bos=null;
		ObjectOutputStream oos=null;
		
		
		try {
			bos=new ByteArrayOutputStream();
			oos =new ObjectOutputStream(bos);
			oos.writeObject(obj);
			oos.flush();
			bytes= bos.toByteArray();
			System.out.println("size of bytes "+bytes.length);
		} 
		finally{
			if(oos!=null){
				oos.close();
			}
			if(bos!= null){
				bos.close();
			}
			
		}
		return bytes;
	}
	
	public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException{
		Object obj=null;
		ByteArrayInputStream bis=null;
		ObjectInputStream ois=null;
		try{
		bis=new ByteArrayInputStream(bytes);
		ois=new ObjectInputStream(bis);
		obj= ois.readObject();
		}
		finally{
			if(bis!=null){
				bis.close();
			}
			if(ois!= null){
				ois.close();
			}
		}
		
		return obj;
	}
}
