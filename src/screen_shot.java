
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.MouseInfo;
import java.awt.Panel;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.RepaintManager;

import com.sun.awt.AWTUtilities;

public class screen_shot{
	
	//窗口名称
	JFrame frame = new JFrame("截图工具");
	Button b1 = new Button("截图");
	Button b2 = new Button("关闭");
	
	TextField tField = new TextField(30);
	
	//记录截图时鼠标的开始位置和结束位置
	int startx,starty;
	int endx,endy;
	
	public screen_shot(){
		
		//获取屏幕宽高
		Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screensize.getWidth();
		int height = (int)screensize.getHeight();
		
		//鼠标监听事件
		frame.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				startx=e.getX();
				starty=e.getY();
			}
			public void mouseReleased(MouseEvent e) {
				endx=e.getX();
				endy=e.getY();
			}
			public void mouseEntered(MouseEvent e) {
				tField.setText("鼠标已经进入窗体");
			}
			public void mouseExited(MouseEvent e) {
				tField.setText("鼠标已经移出窗体");
				
			}
		});
		frame.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				String string = "鼠标由("+startx+","+starty
						+")移动到（" + e.getX() + "，" + e.getY() +"）";
				tField.setText(string);
			}
		}); 
		
		
		//窗体监听
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		
		//设置截图按钮的单击事件
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				action();
			}
		});
		
		//设置截图按钮的快捷键监听(有bug
		b1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.isControlDown() &&  e.isShiftDown()&& e.getKeyCode() == KeyEvent.VK_A ){
					new screen_shot().action();
				}
			}
		});
		
		//设置关闭按钮
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		Label label = new Label("请按下鼠标左键并拖动");
		
		//锁定窗口
		frame.setAlwaysOnTop(true);
		
		//窗体布局
		frame.add(label, "North");
		frame.add(tField, "South");
		Panel p = new Panel();
		p.add(b1);
		p.add(b2);	
		frame.add(p,BorderLayout.EAST);
		frame.setSize(200, 100);
		frame.setLocation(1000, 0);
		
        frame.setUndecorated(true);
        //设置透明度   使用了rt.jar中的AWTUtilities类 (需要提前对类库进行access设置
        AWTUtilities.setWindowOpacity(frame, 0.7f);
        
		frame.setVisible(true);
	}
	//具体截屏步骤
	public void action(){
		Point point = MouseInfo.getPointerInfo().getLocation();
		try {
			//得到矩形截图并存入文件
			BufferedImage scnst = (new Robot()).createScreenCapture(
					new Rectangle(startx+400, starty+250, endx>startx?(endx-startx):(startx-endx),
							endy>starty?(endy-starty):(starty-endy))
					);
			File file = new File("D:/screen.jpg");
			try {
				ImageIO.write(scnst, "jpg", file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("已截屏");
	}
	
	public static void main(String[] args) {
		new screen_shot();
	}
	

}