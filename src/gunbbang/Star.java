package gunbbang;

import java.util.ArrayList;

import javax.swing.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Star extends JLabel implements Moveable {

	// 의존성 컴포지션
	private MapPlay mContext; // myContext
	private Player player;
	private Player playerEnemy;
	private BackgroundStarService backgroundBulletService;

	// 위치 상태
	private int x;
	private int y;

	// 움직임 상태
	private boolean left;
	private boolean right;

	// 0: 적을 맞출 수 있는 상태, 1: 적을 맞출 수 없는 상태
	private int state;

	// 속력
	private int speed = 2;

	private ImageIcon bullet; 

	public Star(MapPlay mContext, int type) {
		this.mContext = mContext;
		// player1은 player2만, palyer2는 player1만 공격할 수 있다.
		// 자기 자신이 쏜 총알에는 맞지 않는다.
		if (type == 1) {
			this.player = mContext.getPlayer1();
			playerEnemy = mContext.getPlayer2();
		} else {
			this.player = mContext.getPlayer2();
			playerEnemy = mContext.getPlayer1();
		}
		initObject();
		initSetting();
	}

	private void initObject() {
		bullet = new ImageIcon("image/bullet.png");
		backgroundBulletService = new BackgroundStarService(this);
	}

	private void initSetting() {
		left = false;
		right = false;

		x = player.getX() + 20;
		y = player.getY() + 38;

		setIcon(bullet);
		setSize(50, 50);

		state = 0;
	}

	@Override
	public void left() {
		new Thread(() -> {
			left = true;
			for (int i = 0; i < 200; i++) {
				if (state == 1)
					break; // 적을 맞춘 총알이라면 움직이지 않음
				x -= speed;
				setLocation(x, y);
				// 왼쪽 벽에 충돌하면 true 반환됨
				if (backgroundBulletService.leftWall()) {
					left = false;
					break;
				}
				// enemy와 충돌했는지 확인
				for (int j = 0; j < mContext.getEnemySize(); j++) {
					if ((Math.abs(x - mContext.getEnemyList().get(j).x) < 70)
							&& (Math.abs(y - mContext.getEnemyList().get(j).y) > 0
									&& Math.abs(y - mContext.getEnemyList().get(j).y) < 50)) {
						// 적이 살아있고 별사탕의 state == 0이라면 실행됨
						if (mContext.getEnemyList().get(j).state == 0 && state == 0) {
							state = 1;
							attack(mContext.getEnemyList().get(j));
							break;
						}
					}
				}

				// playerEnemy와 충돌했는지 확인
				if (Math.abs(x - playerEnemy.getX()) <= 10 && Math.abs(y - playerEnemy.getY()) <= 50
						&& playerEnemy.hpPlayer > 0) {
					// 적이 살아있고 별사탕의 state == 0이라면 실행됨
					if (playerEnemy.getState() == 0 && state == 0) {
						state = 1;
						attack(); // Star의 attack() 메서드를 실행하여 상대 플레이어의 체력을 감소시킵니다.
						break; // 충돌하였으므로, 더 이상 이동하지 않습니다.
					}
				}

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			clearStar();
		}).start();

	}

	@Override
	public void right() {
		new Thread(() -> {
			right = true;
			for (int i = 0; i < 200; i++) {
				if (state == 1)
					break;
				x += speed;
				setLocation(x, y);
				// 오른쪽 벽에 충돌하면 true 반환됨
				if (backgroundBulletService.rightWall()) {
					right = false;
					break;
				}
				// enemy와 충돌했는지 확인
				for (int j = 0; j < mContext.getEnemySize(); j++) {
					if ((Math.abs(x - mContext.getEnemyList().get(j).x) < 70)
							&& (Math.abs(y - mContext.getEnemyList().get(j).y) > 0
									&& Math.abs(y - mContext.getEnemyList().get(j).y) < 50)) {
						if (mContext.getEnemyList().get(j).state == 0 && state == 0) {
							state = 1;
							attack(mContext.getEnemyList().get(j));
							break;
						}
					}
				}
				
				// playerEnemy와 충돌했는지 확인
				if (Math.abs(x - playerEnemy.getX()) <= 10 && Math.abs(y - playerEnemy.getY()) <= 50
						&& playerEnemy.hpPlayer > 0) {
					// 적이 살아있고 별사탕의 state == 0이라면 실행됨
					if (playerEnemy.getState() == 0 && state == 0) {
						state = 1;
						attack(); // Star의 attack() 메서드를 실행하여 상대 플레이어의 체력을 감소시킵니다.
						break; // 충돌하였으므로, 더 이상 이동하지 않습니다.
					}
				}

				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			clearStar();
		}).start();

	}

	@Override
	public void up() {
		// fill
	}

	@Override // playerEnemy 공격
	public void attack() {
		// 체력이 있다면 실행됨
		if(playerEnemy.hpPlayer > 0) {
			state = 1;
			clearStar(); // 별사탕 삭제
			playerEnemy.attacked();
		}
	}

	@Override // enemy 공격
	public void attack(Enemy enemy) {
		state = 1;
		clearStar();
		enemy.die();
		mContext.repaint();
	}

	// 별사탕(총알)을 삭제해주는 메소드
	private void clearStar() {
		state = 1;
		mContext.remove(this);
		mContext.repaint();
	}
}
