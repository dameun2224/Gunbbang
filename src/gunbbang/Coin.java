package gunbbang;

import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

// Coin 클래스입니다.
public class Coin extends JLabel {
	// 의존성 컴포지션
	private MapPlay mContext;
	private Player player1;
	private Player player2;
	private BackgroundCoinService coinBackground;

	// 위치 상태
	public int x;
	public int y;

	// 움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	// 움직임 속도
	Random random = new Random();
	private int hSpeed; // 횡방향 속도
	private int vSpeed; // 종방향 속도

	// 움직이는 시간
	private int timeMax = 100; // 특정한 속력으로 timeMax만큼 움직입니다
	private int time = timeMax; // 특정한 속력으로 이동할 수 있는 남은 시간입니다.

	// 이미지 파일
	private ImageIcon coinMoveImage, coinImage;

	// 플레이어와 충돌을 했는지에 대한 변수
	private boolean touchByPlayer1 = false;
	private boolean touchByPlayer2 = false;

	// 0: 먹을 수 있는 상태, 1: 먹을 수 없는 상태
	private int state;

	public Coin(MapPlay mContext) {
		if(mContext.getState() == 1) return;
		this.mContext = mContext;
		initObject();
		initSetting();
		player1 = mContext.getPlayer1();
		player2 = mContext.getPlayer2();
		mContext.add(this, -1);
		// this.setVisible(true);
		// mContext.repaint(); // 레이아웃을 다시 그려서 변경 사항을 적용합니다.
	}

	private void initObject() {
		coinMoveImage = new ImageIcon("image/coinMove.png");
		coinImage = new ImageIcon("image/coin.png");
		coinBackground = new BackgroundCoinService(this);
	}

	private void initSetting() {
		left = random.nextBoolean();
		right = !left;

		up = true;
		down = false;

		hSpeed = random.nextInt(5);
		vSpeed = random.nextInt(5);

		state = 1;

		setIcon(coinMoveImage);
		setSize(25, 25);
	}

	public void move() {
		while (hSpeed > 0 || vSpeed > 0) {
			time--;
			setLocation(x, y);

			if (coinBackground.checkUpWall()) { // 위쪽벽에 충돌
				// 방향을 아래쪽으로 설정
				up = false;
				down = true;
			}
			if (up) { // 코인의 방향이 위쪽일 경우
				if (vSpeed > 0)
					y -= vSpeed; // y값을 종방향의 속력만큼 감소
				else { // 만약 종방향의 속력이 0이라면 코인은 떨어질수밖에 없다. (횡방향의 속력만 존재한다면 코인은 수평으로 움직이게 된다)
						// 방향을 아래쪽으로 설정
					up = false;
					down = true;
				}
			}

			if (coinBackground.checkDownWall()) { // 바닥에 충돌
				// 방향을 위쪽으로 설정
				up = true;
				down = false;
			}
			if (down) {// 코인의 방향이 아래쪽일 경우
				if (vSpeed > 0)
					y += vSpeed; // y값을 종방향의 속력만큼 증가
				else
					y++; // 만약 종방향의 속력이 0이라도 코인은 떨어질수밖에 없다. 그래므로 y값을 증가시켜준다. (횡방향의 속력만 존재한다면 코인은 수평으로
							// 움직이게 된다)
			}

			if (coinBackground.checkLeftWall()) { // 왼쪽벽에 충돌
				// 방향을 오른쪽으로 설정
				left = false;
				right = true;
			}
			// 코인의 방향이 왼쪽일 경우
			if (left)
				x -= hSpeed; // x값을 횡방향의 속력만큼 감소

			if (coinBackground.checkRightWall()) {
				// 방향을 왼쪽으로 설정
				left = true;
				right = false;
			}
			// 코인의 방향이 오른쪽일 경우
			if (right)
				x += hSpeed; // x값을 횡방향의 속력만큼 증가

			if (time == 0) { // 이동시간이 다 소진되었다면 양쪽의 속력을 감소시킵니다.
				if (hSpeed > 0)
					hSpeed--; // 감소
				if (vSpeed > 0)
					vSpeed--; // 감소
				time = timeMax; // 감소된 속력으로 다시 timeMax만큼의 시간동안 움직입니다.
			}

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		down();
	}

	private void down() {
		while (true) {
			if (coinBackground.checkDownWall()) {
				break;
			}

			y++;
			setLocation(x, y);

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		fixed();
	}

	private void fixed() {
		setIcon(coinImage); // 이미지 변경
		state = 0;
		touch();
	}

	private void touch() {
		while (true) {
			// 좌표를 이용하여 충돌 여부를 구합니다 -> 조건 만족시
			// player와 충돌 시 touchByPlayer 는 true가 됩니다.
			// player2와 충돌 시 touchByPlayer2 는 true가 됩니다.
			if ((player1.getX() - x > -90 && player1.getX() - x < 35)
					&& (player1.getY() - y > -90 && player1.getY() - y < 45) && player1.hpPlayer > 0)
				touchByPlayer1 = true;
			if ((player2.getX() - x > -90 && player2.getX() - x < 35)
					&& (player2.getY() - y > -90 && player2.getY() - y < 45) && player2.hpPlayer > 0)
				touchByPlayer2 = true;

			// 충돌이 있엇다면 반복문을 종료합니다.
			if (touchByPlayer1 || touchByPlayer2)
				break;

			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// 코인 충돌에 의한 처리
		// 1. 동시 충돌
		if (touchByPlayer1 && touchByPlayer2) { // 동시로 판단되면, 코인을 다시 멀리 날립니다.
			initSetting(); // 코인에 다시 랜덤한 속력을 부여한 다음 (이미지도 회색으로 바뀝니다)
			move(); // 날려보냅니다.
		} else if (touchByPlayer1) { // player하고만 충돌 했을 경우
			player1.coinCnt++; // player의 소지 코인의 개수를 증가시킵니다.
			mContext.coinDisplay1.reset(); // player의 코인 개수를 JLabel을 이용해 보여주는 객체도 다시 설정해 줍니다.
		} else if (touchByPlayer2) { // player2하고만 충돌 했을 경우
			player2.coinCnt++; // player2의 소지 코인의 개수를 증가시킵니다.
			mContext.coinDisplay2.reset(); // player2의 코인 개수를 JLabel을 이용해 보여주는 객체도 다시 설정해 줍니다.
		}
		clearCoin(); // 코인을 메모리 상에서 제거합니다.
	}

	private void clearCoin() {
		mContext.remove(this);
		mContext.repaint();
	}
}
