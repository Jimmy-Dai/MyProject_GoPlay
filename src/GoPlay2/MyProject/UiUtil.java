package GoPlay2.MyProject;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JFrame;

public class UiUtil {
	private UiUtil() {
	}
	static Toolkit tk = Toolkit.getDefaultToolkit();
	public static void setFrameImage(JFrame f,String imageName){
		Image i = tk.getImage("src\\cn\\resource\\"+imageName);
		f.setIconImage(i);
	}
	public static void setFrameCenter(JFrame f){
		Dimension d = tk.getScreenSize();
		double screenWidth = d.getWidth();
		double screenHeight = d.getHeight();
		int frameWidth = f.getWidth();
		int frameHeight = f.getHeight();
		
		int pointWidth = (int)(screenWidth-frameWidth)/2;
		int pointHeight = (int)(screenHeight-frameHeight)/2;
		
		f.setLocation(pointWidth, pointHeight);
	}
}
