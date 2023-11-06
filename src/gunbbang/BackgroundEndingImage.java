package gunbbang;

import javax.swing.*;

// 엔딩 화면 시 어두운 배경을 띄우는 클래스입니다.
public class BackgroundEndingImage extends JLabel {
	private MapPlay mContext;
	
	private ImageIcon backgroundEnding;
	
	public BackgroundEndingImage(MapPlay mContext) {
		this.mContext = mContext;
		setSize(mContext.SCREEN_WIDTH, mContext.SCREEN_HEIGHT); // 크기
		setLayout(null); // NullLayout으로 설정
		initObject();
		initSetting();
	}
	
	private void initObject() {
		backgroundEnding = new ImageIcon("image/015.png");
	}
	
	private void initSetting() {
		setBounds(0, 0, 1000, 600);
		setIcon(backgroundEnding);
	}
}
