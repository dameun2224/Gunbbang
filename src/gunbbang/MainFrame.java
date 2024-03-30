package gunbbang;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.Image;

// 게임 시작 화면을 담당하는 MainFrame 클래스입니다.
public class MainFrame extends JFrame {
	// 게임 화면 크기
    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 600;
    
    MainFrame mainFrame = this;
    	
    BackgroundPanel backgroundPanel; // 시작 화면 panel
    Menu menu; // 메뉴 class
    HowToPlay howToPlay; // 게임 방법 panel
    
    public MainFrame() {
        setTitle("Main Frame");
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);

       menu = new Menu(mainFrame);
     
       
        backgroundPanel = new BackgroundPanel();
        backgroundPanel.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        backgroundPanel.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        backgroundPanel.setVisible(true);
        add(backgroundPanel);
        
        
        
        
        howToPlay = new HowToPlay(this);
        howToPlay.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        howToPlay.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        howToPlay.setVisible(false);
        add(howToPlay);
    }
}
