package com.ken.stuscoremanager.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyTool {
	public static String SaveImg(MultipartFile file, String dir, String imgName) {
		File direction = new File(dir);
		if (!direction.exists()) {
			direction.mkdir();
		}
		File myFile = new File(direction, imgName);
		System.out.println("addr:" + myFile.getAbsolutePath());
		try {
			FileInputStream fis = (FileInputStream) file.getInputStream();
			FileOutputStream fos = new FileOutputStream(myFile);
			int len = 0;
			byte[] b = new byte[1024];
			while ((len = fis.read(b)) != -1) {
				fos.write(b);
			}
			fos.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (myFile.getAbsolutePath());
	}

	//定义上传文件的位置
	public static String getImg() {
		String imgPath = "";
		imgPath = "E:/uploads/";
		return imgPath;
	}

}
