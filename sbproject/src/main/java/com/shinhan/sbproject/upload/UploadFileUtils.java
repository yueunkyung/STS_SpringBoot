package com.shinhan.sbproject.upload;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import org.springframework.util.FileCopyUtils;

import net.coobird.thumbnailator.Thumbnails;
public class UploadFileUtils {
	// 썸네일 이미지 크기
	static final int THUMB_WIDTH = 300;
	static final int THUMB_HEIGHT = 300;

	// 파일 업로드
	public static String fileUpload(String uploadPath, 
			String fileName, byte[] fileData, String ymdPath)
			throws Exception {
		UUID uid = UUID.randomUUID();
		String newFileName = uid + "_" + fileName;
		String imgPath = uploadPath + ymdPath;
		File target = new File(imgPath, newFileName);
		FileCopyUtils.copy(fileData, target);	//upload 파일
//		String thumbFileName = "s_" + newFileName;
//		File image = new File(imgPath + File.separator + newFileName);
//		File thumbnail = new File(imgPath + File.separator + "s" + File.separator + thumbFileName);
//		if (image.exists()) {
//			thumbnail.getParentFile().mkdirs();
//			Thumbnails.of(image).size(THUMB_WIDTH, THUMB_HEIGHT).toFile(thumbnail);	//썸네일 만들기
//		}
		return newFileName;
	}

	// 폴더이름 및 폴더 생성
	public static String calcPath(String uploadPath) {
		//File.separator => / (슬래시를 의미함)
		Calendar cal = Calendar.getInstance();
		String yearPath = File.separator + cal.get(Calendar.YEAR);
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		
		int pos = uploadPath.lastIndexOf(File.separator);	// 마지막 경로 슬래시 위치를 찾음 cc/bb/cc.png
		String folder = uploadPath.substring(0, pos);
		makeDir(folder, uploadPath.substring(pos));
		System.out.println(folder + ":" + uploadPath.substring(pos)); 
		makeDir(uploadPath, yearPath, monthPath, datePath);
		makeDir(uploadPath, yearPath, monthPath, datePath + "\\s");
		return datePath;
	}

	// 폴더 생성
	private static void makeDir(String uploadPath, String... paths) {
		if (new File(paths[paths.length - 1]).exists())
			return;
		for (String path : paths) {
			File dirPath = new File(uploadPath + path);	//파일 이름
			if (!dirPath.exists())
				dirPath.mkdir();	//디렉토리 만들기
		}
	}
}
