package gunbbang;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.*;
import javax.swing.*;

// How To Play 버튼 클릭 시 나타나는 화면입니다.
public class HowToPlay extends JPanel {
	private MainFrame mainFrame;
	private Image htp;

	ImageIcon eixtButtonOn = new ImageIcon("image/btn_on_exit.png");
	ImageIcon eixtButtonOff = new ImageIcon("image/btn_off_exit.png");

	JButton exitButton = new JButton(eixtButtonOn);

	public HowToPlay(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
		htp = new ImageIcon("image/how_to_play.png").getImage();

		exitButton.setBounds(840, 80, 70, 70);
		exitButton.setBorderPainted(false);
		exitButton.setContentAreaFilled(false);
		exitButton.setFocusPainted(false);
		exitButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				exitButton.setIcon(eixtButtonOff);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				exitButton.setIcon(eixtButtonOn);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// howToPlay(panel)을 보이지 않게 합니다.
				mainFrame.howToPlay.setVisible(false);
				// backgroundPanel과 menu 버튼을 보이게 합니다.
				mainFrame.backgroundPanel.setVisible(true);
				mainFrame.menu.visibleTrue();
				
			}
		});
		mainFrame.add(exitButton);
	}

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(htp, 0, 0, getWidth(), getHeight(), null);
	}
}
