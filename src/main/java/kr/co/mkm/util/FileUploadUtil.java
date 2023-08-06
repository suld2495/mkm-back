package kr.co.mkm.util;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

public class FileUploadUtil {
    public FileUploadUtil() {
    }

    public static String fileUpload(Map<String, Object> paramMap, MultipartFile mFile, HttpServletRequest request) {
        String fileName = DateUtil.getDateTime() + mFile.getOriginalFilename();

        String ext;
        try {
            ext = paramMap.get("path").toString() + "/" + fileName;
            byte[] b = mFile.getBytes();
            FileOutputStream fos = new FileOutputStream(ext);
            fos.write(b);
            fos.close();
        } catch (Exception var7) {
            System.out.println(paramMap.get("path").toString() + fileName);
            var7.printStackTrace();
            System.out.println("파일 업로드 중에 문제가 발생하였습니다.");
        }

        ext = StringUtil.ext(fileName);
        return fileName;
    }

    public static String fileUpload(Map<String, Object> paramMap, MultipartFile mFile) throws IOException {
        String realFileName = mFile.getOriginalFilename();
        String extension = realFileName.substring(realFileName.lastIndexOf("."), realFileName.length());

        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString() + extension;

        String ext;
        try {
            ext = paramMap.get("path").toString() + "/" + fileName;
            byte[] b = mFile.getBytes();
            FileOutputStream fos = new FileOutputStream(ext);
            fos.write(b);
            fos.close();
        } catch (Exception e) {
            System.out.println(paramMap.get("path").toString() + fileName);
            System.out.println("파일 업로드 중에 문제가 발생하였습니다.");
            throw e;
        }

        ext = StringUtil.ext(fileName);
        return fileName;
    }

    public static void movieTest(String path, String fileName) {
        String[] thumnailFilenames = fileName.split("\\.");
        String thumnailFilename = "";
        int length = thumnailFilenames.length;

        for(int i = 0; i < length - 1; ++i) {
            thumnailFilename = thumnailFilename + thumnailFilenames[i];
        }

        thumnailFilename = thumnailFilename + ".png";
        String image = path + fileName;
        String thumbnail = path + "th" + thumnailFilename;
        Runtime run = Runtime.getRuntime();
        String command = "ffmpeg -i " + image + " -pix_fmt rgb24 -vframes 1 -ss 00:00:03 -s 200x200  " + thumbnail;

        try {
            System.out.println(command);
            run.exec(command);
        } catch (Exception var10) {
            System.out.println("동영상 썸네일 추출 하는 동안 문제가 발생 하였습니다.");
        }

    }
}
