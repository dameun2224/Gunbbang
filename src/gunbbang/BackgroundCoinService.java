package gunbbang;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

// Coin 객체가 맵을 인식하도록 하는 클래스입니다.
// backgroundMapService 이미지를 바탕으로 맵의 색깔을 확인합니다.
public class BackgroundCoinService {
	
	private BufferedImage image;
	private Coin coin;
	
	public BackgroundCoinService(Coin coin) {
		this.coin = coin;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public boolean checkUpWall() {
		int topColor = image.getRGB(coin.getX()+12, coin.getY()-6);
		if(topColor != -1) return true;
		return false;
	}
	public boolean checkDownWall() {
		int downColor = image.getRGB(coin.getX()+12, coin.getY()+31);
		if(downColor != -1) return true;
		return false;
	}
	public boolean checkLeftWall() {
		int leftColor = image.getRGB(coin.getX()-10, coin.getY()+12);
		if(leftColor != -1) return true;
		return false;
	}
	public boolean checkRightWall() {
		int rightColor = image.getRGB(coin.getX()+45, coin.getY()+12);
		if(rightColor != -1) return true;
		return false;
	}
	
}

