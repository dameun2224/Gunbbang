package gunbbang;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

// 타이머를 보여주는 JLabel입니다.
// coinDispaly와 마찬가지로 imageIcon에 textLabel를 붙입니다.
public class GameTimer extends JLabel {
	MapPlay mContext;
	
	private int seconds;
	private int minutes;
	
	private ImageIcon backgroundTimer;
	private JLabel textLabel;
	
	// 게임 시간을 결정하는 변수입니다.
	static final int setGameTime = 70;

	public GameTimer(MapPlay mContext) {
		this.mContext = mContext;
		setLayout(null); // NullLayout으로 설정
		initSetting();
		initObject();
	}
	
	public void initObject() {
		backgroundTimer = new ImageIcon("image/04T.png");
		setIcon(backgroundTimer);
	}

	public void initSetting() {
		seconds = setGameTime % 60;
		minutes = setGameTime / 60;
		
		setSize(264, 82); // 크기
		setBounds(365, 40, 264, 82);
		
		Timer timer = new Timer(1000, new TimerListener());
		timer.start();
		
		textLabel = new JLabel(String.format("%02d:%02d", minutes, seconds));
		textLabel.setFont(new Font("Dialog", Font.ITALIC, 60));
		textLabel.setBounds(30, 0, 200, 80);
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		textLabel.setVerticalAlignment(JLabel.CENTER);

		add(textLabel);
	}
	
	// 시간을 업데이트 후 보여줍니다.
	private void updateTime() {
		String time = String.format("%02d:%02d", minutes, seconds);
		textLabel.setText(time);
		mContext.repaint();
	}

	private class TimerListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			seconds--;
			if (seconds < 0) {
				seconds = 59;
				minutes--;
			}
			updateTime();
			// 게임 종료
			if(seconds == 0 && minutes == 0) {
				mContext.TimeOver();
			}
		}
	}
}
