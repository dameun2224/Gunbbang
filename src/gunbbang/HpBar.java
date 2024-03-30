package gunbbang;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLabel;

// 체력바를 구현한 HpBar 클래스입니다.
public class HpBar extends JLabel {
	private final int frame = 40;

	static final int hpMax = 3; // 최대 체력
	static final int inMAX = 10000; // 무적? 체력

	private int barSize = hpMax * frame;
	private int maxBarSize = hpMax * frame;

	public HpBar(int type) {
		// 플레이어 1, 2에 따른 위치
		if (type == 1)
			setLocation(115, 135);
		else
			setLocation(755, 135);
		setBackground(Color.RED); // 배경색
		setSize(120, 20); // 크기
		setVisible(true); // 보이게
		setOpaque(true); // 뒷배경도 보이게
	}

	// hp 감소 시 체력바 감소
	public void fill(int hp) {
		barSize = hp * frame;
		repaint();
	}

	// 체력 회복
	public void refill() {
		new Thread(() -> {
			// 천천히 색깔이 채워지도록 구현하기 위해 for문 사용
			for (int i = 1; i <= 120; i++) {
				barSize = i;
				repaint();
				try {
					// 무적 시간이 1.2s이므로, 총 1.2초의 딜레이를 줍니다.
					// 10 * 120 = 1200ms = 1.2s
					Thread.sleep(10);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.GREEN);
		int width = (int) (this.getWidth() / maxBarSize * barSize);
		g.fillRect(120 - width, 0, 120, this.getHeight());
	}
}