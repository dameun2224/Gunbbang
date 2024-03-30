package gunbbang;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

// star 객체가 맵을 인식하도록 하는 클래스입니다.
// backgroundMapService 이미지를 바탕으로 맵의 색깔을 확인합니다.
public class BackgroundStarService {

	private BufferedImage image;
	private Star bullet;
	
	public BackgroundStarService(Star bullet) {
		this.bullet = bullet;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	// 왼쪽 벽에 충돌했다면 true를 리턴합니다.
	// Star 클래스 내에서 호출됩니다.
	public boolean leftWall() {
		Color leftColor = new Color(image.getRGB(bullet.getX(), bullet.getY()+ 25));
		
		if(leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
			return true;
		}
		return false;
	}
	
	// 오른쪽 벽에 충돌했다면 true를 리턴합니다.
	// Star 클래스 내에서 호출됩니다.
	public boolean rightWall() {
		Color rightColor = new Color(image.getRGB(bullet.getX() + 50 + 15, bullet.getY() + 25));
		
		if(rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
			return true;
		}
		return false;
	}
}
