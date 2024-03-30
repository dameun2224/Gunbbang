package gunbbang;

// enemy ����� �� remove �� add�� index�� .. �����Ϸ�
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Enemy extends JLabel implements Moveable {
	private MapPlay mContext;

	// 위치 상태
	int x;
	int y;

	private Random random = new Random();

	// enemy의 방향
	private EnemyWay enemyWay;

	// 움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	int state; // 0 live, 1 depth 

	// 속도
	private final int SPEED = 3;
	private final int JUMPSPEED = 1; // up, down

	private ImageIcon enemyR, enemyL, enemyDie;

	public Enemy(MapPlay mContext) {
		this.mContext = mContext;
		initObject();
		initSetting();
		initBackgroundEnemyService();
		move(); // 처음 생성 시 랜덤으로 왼쪽 혹은 오른쪽으로 움직입니다.
	}

	private void initObject() {
		enemyR = new ImageIcon("Image/L.png");
		enemyL = new ImageIcon("Image/L.png");
		enemyDie = new ImageIcon("Image/D.png");
	}

	private void initSetting() {
		x = 480;
		y = 160;

		left = false;
		right = false;
		up = false;
		down = false;
		
		state = 0;
		
		enemyWay = EnemyWay.RIGHT;
		
		setIcon(enemyR);
		setSize(70, 70);
		setLocation(x, y);
	}

	private void initBackgroundEnemyService() {
		new Thread(new BackgroundEnemyService(this)).start();
	}
	
	// 리스폰 시 오른쪽 왼쪽을 결정
	private void move() {
		int a = random.nextInt() % 2;
		if(a==1) right();
		else left();
	}

	public void left() {
		enemyWay = EnemyWay.LEFT;
		left = true;
		new Thread(() -> {
			while (left) {
				setIcon(enemyL);
				x = x - SPEED;
				setLocation(x, y);
				
				// player와 충돌했는지 확인
				for(int i=0; i<2; i++) {
					Player player;
					if(i==0) player = mContext.getPlayer1();
					else player = mContext.getPlayer2();
					if (Math.abs(x - player.getX()) < 50
							&& (Math.abs(y - player.getY()) > 0
									&& Math.abs(y - player.getY()) < 80)) {
						if ((System.currentTimeMillis() - player.pretime) >= player.timegap) {
							// player와 enemy 모두 살아있다면
							if (state == 0 && player.getState() == 0) {
								new Thread(()-> {
									player.attacked();
									player.pretime = System.currentTimeMillis();
								}).start();
								break;
							}
						}
					}
				}
				
				try {
					Thread.sleep(10); // 0.01��
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void right() {
		enemyWay = EnemyWay.RIGHT;
		right = true;
		new Thread(() -> {
			while (right) {
				setIcon(enemyR);
				x = x + SPEED;
				setLocation(x, y);
				
				// player와 충돌했는지 확인
				for(int i=0; i<2; i++) {
					Player player;
					if(i==0) player = mContext.getPlayer1();
					else player = mContext.getPlayer2();
					if (Math.abs(x - player.getX()) < 50
							&& (Math.abs(y - player.getY()) > 0
									&& Math.abs(y - player.getY()) < 60)) {
						if ((System.currentTimeMillis() - player.pretime) >= player.timegap) {
							// player와 enemy 모두 살아있다면
							if (state == 0 && player.getState() == 0) {
								new Thread(()-> {
									player.attacked();
									player.pretime = System.currentTimeMillis();
								}).start();
								break;
							}
						}
					}
				}
				
				
				int a = random.nextInt() % 100;
				if(a == 0 && up == false) up();
				try {
					Thread.sleep(10); // 0.01��
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	public void up() {
		up = true;
		new Thread(() -> {
			for (int i = 0; i < 130 / JUMPSPEED; i++) {
				if(state == 1) break;
				y = y - JUMPSPEED;
				setLocation(x, y);
				int a = random.nextInt() % 100;
				if(a == 0 && up == false) up();
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
		down = true;
		new Thread(() -> {
			while (down) {
				if(state == 1) break;
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
	
	public void die() {
		if(state == 1) return;
		right = left = up = down = false;
		state = 1;
		setIcon(enemyDie);
		drop();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		mContext.remove(this);
		remakeEnemy();
	}
	
	public void remakeEnemy() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		x = 480;
		y = 160;

		left = false;
		right = false;
		up = false;
		down = false;
		
		state = 0;
		
		setIcon(enemyR);
		setLocation(x, y);
		
		mContext.add(this);
		move();
	}
	
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
	
	public void attack(Enemy enemy) {} 
	
	public void attack() {}

	static void main(String[] args) {

	}
}
