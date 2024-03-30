package gunbbang;

import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

// Coin의 개수를 보여주는 클래스입니다.
// Coin의 개수를 textLabel에 저장하고, ImageIcon에 붙여 화면에 띄웁니다.
public class CoinDisplay extends JLabel {
	private MapPlay mContext;
	
	private int type; // player 1 or player 2
	private Player player;
	
	private ImageIcon coinPlayerImage;
	private JLabel textLabel;

	
	CoinDisplay(Player player, int type) {
		this.player = player;
		this.type = type;
		initSetting();
		initObject();
	}

	public void initObject() {
		if (type == 1)
			coinPlayerImage = new ImageIcon("image/04C1.png");
		else
			coinPlayerImage = new ImageIcon("image/04C2.png");
		setIcon(coinPlayerImage);
	}

	public void initSetting() {
		setLayout(null); 
		
		textLabel = new JLabel(Integer.toString(player.coinCnt));
		textLabel.setFont(new Font("Dialog", Font.ITALIC, 60));
		if (type == 1) {
			setBounds(90, 40, 173, 82);
			textLabel.setBounds(75, 0, 80, 80); // 텍스트 위치 설정
		} else {
			setBounds(725, 40, 173, 82);
			textLabel.setBounds(10, 0, 80, 80); // 텍스트 위치 설정
		}
		textLabel.setHorizontalAlignment(JLabel.CENTER);
		textLabel.setVerticalAlignment(JLabel.CENTER);
		textLabel.setPreferredSize(textLabel.getPreferredSize()); // 텍스트에 맞게 크기 자동 조절
		
		setSize(173, 82);
		add(textLabel);
	}

	// textLabel을 다시 reset합니다.
	// Coin 클래스 내에서, player와 접촉 후 coin이 증가되면 호출되는 메소드입니다.
	public void reset() {
		JLabel textLabel = (JLabel) getComponent(0); // 첫 번째 컴포넌트를 가져옴 (텍스트 라벨)
		textLabel.setText(Integer.toString(player.coinCnt));
	}

}
