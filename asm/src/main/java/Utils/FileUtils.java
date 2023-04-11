package Utils;

import java.io.File;
import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

public class FileUtils {
	public static String writeFile(HttpServletRequest req, String nameFile, String path) {
		File dir = new File(req.getServletContext().getRealPath(path));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		Part photo;
		try {
			photo = req.getPart(nameFile);
			File photoFile = new File(dir, photo.getSubmittedFileName());
			photo.write(photoFile.getAbsolutePath());
			return photo.getSubmittedFileName();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
