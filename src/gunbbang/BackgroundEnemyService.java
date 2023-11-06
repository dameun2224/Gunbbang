package gunbbang;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

// enemy 객체가 맵을 인식하도록 하는 클래스입니다.
// backgroundMapService 이미지를 바탕으로 맵의 색깔을 확인합니다.
public class BackgroundEnemyService implements Runnable {

	private BufferedImage image;
	private Enemy enemy;
	
	public BackgroundEnemyService(Enemy enemy) {
		this.enemy = enemy;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (Exception e) {
			// TODO: handle exception 
		}
	}

	@Override
	public void run() {
		// while문을 통해 충돌을 확인합니다.
		while(true) {
			// 색상
			Color leftColor = new Color(image.getRGB(enemy.getX(), enemy.getY()+ 35));
			Color rightColor = new Color(image.getRGB(enemy.getX() + 70, enemy.getY() + 35));
			int bottomColor = image.getRGB(enemy.getX() + 25, enemy.getY() + 70)
					+ image.getRGB(enemy.getX() + 70 - 25, enemy.getY() + 70);
			
			// 바닥 충돌 확인
			if(bottomColor != -2) { // bottomColor가 하얀색이 아니라면
				enemy.setDown(false);
			}else { // bottomColor == -2, 하얀색이라면 내려감
				if(!enemy.isUp() && !enemy.isDown()) {
					enemy.down();
				}
			}
			
			// 외벽 충돌 확인
			if(leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
				enemy.setLeft(false);
				if(!enemy.isRight()) {
					enemy.right();
				}
			} else if(rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
				enemy.setRight(false);
				if(!enemy.isLeft()) {
					enemy.left();
				}
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
