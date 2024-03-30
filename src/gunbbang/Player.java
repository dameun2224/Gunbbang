package gunbbang;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Player extends JLabel implements Moveable {

	private MapPlay mContext;

	// player 1 or player 2
	private int type;

	// 위치 상태
	private int x;
	private int y;

	// 플레이어의 방향
	private PlayerWay playerWay;

	// 움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	private int state; // 0: live, 1: depth
	private int isAttacked; // 0: 공격받지 않음, 1: 공격받음

	// 벽에 충돌한 상태
	private boolean leftWallCrash;
	private boolean rightWallCrash;

	private final int SPEED = 4;
	private final int JUMPSPEED = 2; // up, down

	public int hpPlayer;
	public HpBar hpBar;

	// 플레이어 코인
	public int coinCnt; // 코인 개수

	private ImageIcon player1R, player1L;
	private ImageIcon player1RD, player1LD;
	private ImageIcon player2R, player2L;
	private ImageIcon player2RD, player2LD;

	// enemy와 충돌 시 1초간 enemy와 충돌해도 체력이 깎이지 않도록 하는 필드
	public long timegap = 1000;
	public long pretime;

	public Player(MapPlay mContext, int type) {
		this.mContext = mContext;
		initObject();
		initSetting(type);
		initBackgroundPlayerService();
	}

	private void initObject() {
		player1R = new ImageIcon("Image/01R.png");
		player1L = new ImageIcon("Image/01L.png");
		player1RD = new ImageIcon("Image/01RD.png");
		player1LD = new ImageIcon("Image/01LD.png");
		player2R = new ImageIcon("Image/03R.png");
		player2L = new ImageIcon("Image/03L.png");
		player2RD = new ImageIcon("Image/03RD.png");
		player2LD = new ImageIcon("Image/03LD.png");
	}

	private void initSetting(int type) {
		this.type = type;
		if (type == 1) { // player1
			x = 80;
			y = 490;
			playerWay = PlayerWay.RIGHT;
			setIcon(player1R);
		} else if (type == 2) { // player2
			x = 800;
			y = 490;
			playerWay = PlayerWay.LEFT;
			setIcon(player2L);
		}

		left = false;
		right = false;
		up = false;
		down = false;

		state = 0;
		isAttacked = 0;

		leftWallCrash = false;
		rightWallCrash = false;

		setSize(90, 90);
		setLocation(x, y);

		hpPlayer = HpBar.hpMax;
		hpBar = new HpBar(type);
		mContext.add(hpBar);

		coinCnt = 0;

		pretime = 0;
	}

	private void initBackgroundPlayerService() {
		new Thread(new BackgroundPlayerService(this)).start();
	}

	public void left() {
		if (state == 1)
			return;
		playerWay = PlayerWay.LEFT;
		left = true;
		new Thread(() -> {
			while (left) {
				if (type == 1)
					setIcon(player1L);
				else if (type == 2)
					setIcon(player2L);
				x = x - SPEED;
				setLocation(x, y);

				try {
					Thread.sleep(10); // 0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void right() {
		if (state == 1)
			return;
		playerWay = PlayerWay.RIGHT;
		right = true;
		new Thread(() -> {
			while (right) {
				if (type == 1)
					setIcon(player1R);
				else if (type == 2)
					setIcon(player2R);
				x = x + SPEED;
				setLocation(x, y);

				try {
					Thread.sleep(10); // 0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	// left + up, right + up 가능하도록 스레드
	public void up() {
		if (state == 1)
			return;
		up = true;
		new Thread(() -> {
			for (int i = 0; i < 130 / JUMPSPEED; i++) {
				y = y - JUMPSPEED;
				setLocation(x, y);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			up = false;
			down();

		}).start();
	}

	public void down() {
		if (state == 1)
			return;
		// System.out.println("down");
		down = true;
		new Thread(() -> {
			while (down) {
				y = y + JUMPSPEED;
				setLocation(x, y);
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			down = false;
		}).start();

	}

	public void attack() {
		if (state == 1)
			return;
		if (type == 1) {

		} else {

		}
		new Thread(() -> {
			Star star = new Star(mContext, type);
			mContext.add(star);
			if (playerWay == PlayerWay.LEFT) {
				star.left();
			} else {
				star.right();
			}
		}).start();
	}

	// 플레이어 사망 메서드
	public void die() {
		state = 1; // 죽은 상태
		if (type == 1) { // 죽은 이미지로 변환
			if (right == true)
				setIcon(player1RD);
			else
				setIcon(player1LD);
		} else {
			if (right == true)
				setIcon(player2RD);
			else
				setIcon(player2LD);
		}
		right = left = up = down = false; // 움직임 정지
		dropAll();
		try { // 2초동안 스레드 정지
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// MapPlay에서 삭제
		mContext.remove(this);
		revive();
	}

	// 플레이어 부활 메서드
	private void revive() {
		try { // 1초동안 스레드 정지
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (type == 1) { // player1
			x = 80;
			y = 490;
			playerWay = PlayerWay.RIGHT;
			setIcon(player1R);
		} else if (type == 2) { // player2
			x = 800;
			y = 490;
			playerWay = PlayerWay.LEFT;
			setIcon(player2L);
		}
		state = 0; // 살아있는 상태

		mContext.add(this); // MapPlay에 다시 추가

		hpPlayer = hpBar.inMAX; // 체력에 10000을 대입하여 무적 구현 (맞아도 1.2초만에 만대를 맞기는 힘들다)
		hpBar.refill(); // 부활 중(체력바를 다시 초록색으로 채웁니다)
		try { // 1.2초 동안은 체력이 10000이하 입니다.
			Thread.sleep(1200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		hpPlayer = hpBar.hpMax; // 원래 기본 최대 체력으로 (무적 해제)
	}

	// 공격 받으면 실행되는 메서드
	public void attacked() {
		hpPlayer--; // 체력 1 감소
		hpBar.fill(hpPlayer); // 체력바를 변경된 체력으로 수정
		mContext.repaint();
		if (hpPlayer == 0) {
			die();
		}
	}

	// 플레이어 코인을 떨어뜨리는 메서드 : 이 메서드는 star2의 attack()에서, 플레이어의 체력이 0이 되었을 때 실행됩니다.
	// 위의 부활메서드도 같은 조건에 의해서 실행됩니다. 동시간에 작업이 일어나야 하므로, revive(), drop() 둘 다 새로운 스레드를
	// 이용합니다.
	public void drop() {
		new Thread(() -> {
			Coin coin = new Coin(mContext); // 코인 객체 생성
			// 코인의 초기 위치를 현재 플레이어의 위치를 이용하여 정해줍니다. (플레이어 몸에서 코인이 쏟아져 나오도록 위함입니다)
			coin.x = getX() + 25;
			coin.y = getY();
			mContext.add(coin, 0); // JFrame에 코인을 추가합니다. (이제 코인이 화면에 보입니다)
			mContext.repaint();
			coin.move(); // 코인을 움직입니다.
		}).start();

	}

	public void dropAll() {
		int dropCnt = coinCnt / 2 + coinCnt % 2;
		coinCnt -= dropCnt;
		if (type == 1)
			mContext.getCoinDisplay1().reset();
		if (type == 2)
			mContext.getCoinDisplay2().reset();
		while (dropCnt-- != 0)
			drop();
	}
}
