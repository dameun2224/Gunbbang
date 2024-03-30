package gunbbang;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

// 메뉴 클래스입니다.
// 메뉴 객체 생성 시 mainFrame에 버튼 2개가 추가됩니다.
public class Menu {
	private MainFrame mainFrame;
	
	private static final int BUTTON_WIDTH = 251;
	private static final int BUTTON_HEIGHT = 152;

	private ImageIcon startButtonOn = new ImageIcon("image/btn_on_start.png");
	private ImageIcon startButtonOff = new ImageIcon("image/btn_off_start.png");

	private ImageIcon htpButtonOn = new ImageIcon("image/btn_on_htp.png");
	private ImageIcon htpButtonOff = new ImageIcon("image/btn_off_htp.png");

	private JButton startButton = new JButton(startButtonOn);
	private JButton htpButton = new JButton(htpButtonOn);

	public Menu(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		startButton.setBounds(187, 400, BUTTON_WIDTH, BUTTON_HEIGHT);
		startButton.setBorderPainted(false);
		startButton.setContentAreaFilled(false);
		startButton.setFocusPainted(false);
		startButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				startButton.setIcon(startButtonOff);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				startButton.setIcon(startButtonOn);
			}

			@Override
			public void mousePressed(MouseEvent e) { // Game Start
				mainFrame.dispose(); // MainFrame 종료
				MapPlay mapPlay = new MapPlay(); // MapPlay 생성
				mapPlay.setVisible(true);
			}
		});
		mainFrame.add(startButton);

		htpButton.setBounds(562, 400, BUTTON_WIDTH, BUTTON_HEIGHT);
		htpButton.setBorderPainted(false);			
		htpButton.setContentAreaFilled(false);
		htpButton.setFocusPainted(false);
		htpButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				htpButton.setIcon(htpButtonOff);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				htpButton.setIcon(htpButtonOn);
			}

			@Override
			public void mousePressed(MouseEvent e) { // how to play
				// backgroundPanel과 menu 버튼을 보이지 않게 합니다.
				mainFrame.backgroundPanel.setVisible(false); 
				visibleFalse();
				// howToPlay(panel)를 보이게 합니다.
				mainFrame.howToPlay.setVisible(true);
			}
		});
		mainFrame.add(htpButton);
	}
	
	// 버튼을 보이지 않게 하는 메소드
	public void visibleFalse() {
		startButton.setVisible(false);
		htpButton.setVisible(false);
	}
	
	// 버튼을 보이게 하는 메소드
	public void visibleTrue() {
		startButton.setVisible(true);
		htpButton.setVisible(true);
	}

}
