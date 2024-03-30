package gunbbang;

import java.awt.Component;
// 2Player, enemyList ���� �Ϸ�
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

// 게임 Frame 입니다.
// MainFrame에서 Start 버튼을 누르면 MainFrame이 종료되고, MapPaly가 실행됩니다.
public class MapPlay extends JFrame {

	public static final int SCREEN_WIDTH = 1000;
	public static final int SCREEN_HEIGHT = 640;

	private MapPlay mContext = this;
	private JLabel backgroundMap;

	private Player player1;
	private Player player2;
	
	// 공격키를 꾹 누를 때 연발이 불가능하도록 설정 해주는 변수
	private boolean isSpace;
	private boolean isShift;

	// 코인 display
	public CoinDisplay coinDisplay1;
	public CoinDisplay coinDisplay2;

	// 게임 타이머
	private GameTimer gametimer;

	// enemy는 List로 다룹니다.
	private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	private int enemySize = 4;

	// 0 : 게임중, 1 : 게임 끝남
	private int state = 0;

	// 엔딩 관련
	private Ending ending;
	private BackgroundEndingImage backgroundEndingImage;

	public MapPlay() {
		initSetting();
		initObject();
		initListener();
		setVisible(true);
	}

	private void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/001.jpg"));
		setContentPane(backgroundMap);

		player1 = new Player(mContext, 1);
		player2 = new Player(mContext, 2);
		add(player1);
		add(player2);

		// 코인 보여주는 라벨 설정
		coinDisplay1 = new CoinDisplay(player1, 1);
		coinDisplay2 = new CoinDisplay(player2, 2);
		add(coinDisplay1);
		add(coinDisplay2);

		gametimer = new GameTimer(this);
		add(gametimer);

		for (int i = 0; i < enemySize; i++) {
			Enemy enemy = new Enemy(mContext);
			enemyList.add(enemy);
			add(enemy);
		}
	}

	private void initSetting() {
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		setLayout(null); 
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	private void initListener() {
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_A:
					if (!player1.isLeft() && !player1.isLeftWallCrash())
						player1.left();
					break;
				case KeyEvent.VK_D:
					if (!player1.isRight() && !player1.isRightWallCrash())
						player1.right();
					break;
				case KeyEvent.VK_W:
					if (!player1.isUp() && !player1.isDown())
						player1.up();
					break;
				case KeyEvent.VK_SHIFT:
					if (!isShift) {
						isShift = true;
						player1.attack();
					}
					break;
				case KeyEvent.VK_LEFT:
					if (!player2.isLeft() && !player2.isLeftWallCrash()) {
						player2.left();
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (!player2.isRight() && !player2.isRightWallCrash()) {
						player2.right();
					}
					break;
				case KeyEvent.VK_UP:
					if (!player2.isUp() && !player2.isDown()) {
						player2.up();
					}
					break;
				case KeyEvent.VK_SPACE:
					if (!isSpace) {
						isSpace = true;
						player2.attack();
					}
					break;
				}
				if (state == 1) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_ENTER:
						mContext.dispose();
						MainFrame frame = new MainFrame();
						frame.setVisible(true);
						break;
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					player2.setLeft(false);
					break;
				case KeyEvent.VK_RIGHT:
					player2.setRight(false);
					break;
				case KeyEvent.VK_SPACE:
					isSpace = false;
					break;
				case KeyEvent.VK_A:
					player1.setLeft(false);
					break;
				case KeyEvent.VK_D:
					player1.setRight(false);
					break;
				case KeyEvent.VK_W:
					break;
				case KeyEvent.VK_SHIFT:
					isShift = false;
				}

			}

		});
	}

	public void TimeOver() {
		state = 1;

		player1.hpBar.setVisible(false);
		player2.hpBar.setVisible(false);
		coinDisplay1.setVisible(false);
		coinDisplay2.setVisible(false);
		gametimer.setVisible(false);

		backgroundEndingImage = new BackgroundEndingImage(this);
		if (player1.coinCnt > player2.coinCnt)
			ending = new Ending(this, 1);
		else if (player1.coinCnt < player2.coinCnt)
			ending = new Ending(this, 2);
		else 
			ending = new Ending(this, 0);
		add(backgroundEndingImage, 0);
		add(ending, 0);
		revalidate();
		repaint();
	}
}
