package gunbbang;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.Timer;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

// Ending 시 이미지를 띄워주는 클래스입니다.
// 투명도를 각각 100, 90, 80, ..., 0으로 설정한 이미지를 깜빡거리도록 화면에 띄웁니다.
public class Ending extends JLabel {
	private int type; // p1 win : 1, p2 win : 2, draw : 0
	
	private MapPlay mContext;
	
	private ImageIcon ending1;
	private ImageIcon ending2;
	private ImageIcon ending3;
	private ImageIcon ending4;
	private ImageIcon ending5;
	private ImageIcon ending6;
	private ImageIcon ending7;
	private ImageIcon ending8;
	private ImageIcon ending9;
	private ImageIcon ending10;
	private ImageIcon endingNull;

	// gameTimer가 끝나면 ending 객체 생성
	public Ending(MapPlay mContext, int type) {
		this.mContext = mContext;
		this.type = type;
		initObject();
		initSetting();
		run();
	}

	private void initObject() {
		// type, 0 : draw, 1 : 1p win, 2 : 2p win
		if (type == 0) {
			ending1 = new ImageIcon("image/draw1.png");
			ending2 = new ImageIcon("image/draw2.png");
			ending3 = new ImageIcon("image/draw3.png");
			ending4 = new ImageIcon("image/draw4.png");
			ending5 = new ImageIcon("image/draw5.png");
			ending6 = new ImageIcon("image/draw6.png");
			ending7 = new ImageIcon("image/draw7.png");
			ending8 = new ImageIcon("image/draw8.png");
			ending9 = new ImageIcon("image/draw9.png");
			ending10 = new ImageIcon("image/draw10.png");
		} else if (type == 1) {
			ending1 = new ImageIcon("image/win1p_1.png");
			ending2 = new ImageIcon("image/win1p_2.png");
			ending3 = new ImageIcon("image/win1p_3.png");
			ending4 = new ImageIcon("image/win1p_4.png");
			ending5 = new ImageIcon("image/win1p_5.png");
			ending6 = new ImageIcon("image/win1p_6.png");
			ending7 = new ImageIcon("image/win1p_7.png");
			ending8 = new ImageIcon("image/win1p_8.png");
			ending9 = new ImageIcon("image/win1p_9.png");
			ending10 = new ImageIcon("image/win1p_10.png");
		} else if (type == 2) {
			ending1 = new ImageIcon("image/win2p_1.png");
			ending2 = new ImageIcon("image/win2p_2.png");
			ending3 = new ImageIcon("image/win2p_3.png");
			ending4 = new ImageIcon("image/win2p_4.png");
			ending5 = new ImageIcon("image/win2p_5.png");
			ending6 = new ImageIcon("image/win2p_6.png");
			ending7 = new ImageIcon("image/win2p_7.png");
			ending8 = new ImageIcon("image/win2p_8.png");
			ending9 = new ImageIcon("image/win2p_9.png");
			ending10 = new ImageIcon("image/win2p_10.png");
		}
		endingNull = new ImageIcon("image/endingNull.png");
	}

	private void initSetting() {
		setSize(1000, 600); // 크기
		setLayout(null); // NullLayout으로 설정
		setBounds(0, 0, 1000, 600);
		setIcon(ending1);
	}

	private void run() {
		// 스레드를 통해 깜빡거리도록 화면에 띄워줍니다.
		new Thread(() -> {
			while (true) {
				setIcon(ending1);
				tryCatch();
				setIcon(ending2);
				tryCatch();
				setIcon(ending3);
				tryCatch();
				setIcon(ending4);
				tryCatch();
				setIcon(ending5);
				tryCatch();
				setIcon(ending6);
				tryCatch();
				setIcon(ending7);
				tryCatch();
				setIcon(ending8);
				tryCatch();
				setIcon(ending9);
				tryCatch();
				setIcon(ending10);
				tryCatch();
				setIcon(endingNull);
				tryCatch();
				setIcon(ending10);
				tryCatch();
				setIcon(ending9);
				tryCatch();
				setIcon(ending8);
				tryCatch();
				setIcon(ending7);
				tryCatch();
				setIcon(ending6);
				tryCatch();
				setIcon(ending5);
				tryCatch();
				setIcon(ending4);
				tryCatch();
				setIcon(ending3);
				tryCatch();
				setIcon(ending2);
				tryCatch();
			}
		}).start();
	}

	// 엔딩 화면 이미지 전환의 속도를 조금 지연시켜줍니다.
	private void tryCatch() {
		try {
			Thread.sleep(75);
		} catch (InterruptedException e) {
			return;
		}
	}
}
