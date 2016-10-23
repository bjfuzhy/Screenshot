/*
 * 仍存在的问题...
 * 1,图片命名问题
 * */
import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.RepaintManager;

import com.sun.awt.AWTUtilities;
import com.melloware.jintellitype.HotkeyListener;
import com.melloware.jintellitype.JIntellitype;  

import java.util.Date;
import java.text.SimpleDateFormat;


public class screen_shot{
	//设置热键键值
	static final int KEY_1 = 88;
	static final int KEY_2 = 89;  
	static final int KEY_3 = 90;
	static final int KEY_4 = 91;
	//窗口名称
	JFrame frame = new JFrame("截图工具");
	Button b1 = new Button("截图");
	Button b2 = new Button("关闭");
	
	
	//记录截图时鼠标的开始位置和结束位置
	int startx,starty;
	int endx,endy;

	//获取屏幕宽高
	Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int)screensize.getWidth();
	int height = (int)screensize.getHeight();
	
	public screen_shot(){
		
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
		});
		frame.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				String string = "鼠标由("+startx+","+starty
						+")移动到（" + e.getX() + "，" + e.getY() +"）";
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
		
		//用了jintellitype类库设置全局热键
		JIntellitype.getInstance().registerHotKey(KEY_1,JIntellitype.MOD_WIN,(int)'A');
		JIntellitype.getInstance().registerHotKey(KEY_2,0,27);
		JIntellitype.getInstance().registerHotKey(KEY_3,JIntellitype.MOD_WIN+JIntellitype.MOD_CONTROL,(int)'Z');
		JIntellitype.getInstance().registerHotKey(KEY_4,JIntellitype.MOD_WIN+JIntellitype.MOD_CONTROL,(int)'X');
		
		
		
		JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
			public void onHotKey(int markCode) {
				switch (markCode) {  
					case KEY_1:  {
						if( (startx <= 0 || endx <= 0) || (startx == endx && starty == endy)){
							JOptionPane.showMessageDialog(frame, "未选中截图区域");
							break;
						}
						action();
						break;  
					}
					case KEY_2:  
						System.exit(0);
						break;     
					case KEY_3:{
						frame.setState(Frame.ICONIFIED);
						break;
					}
					case KEY_4:{
						frame.setState(0);
						break;
					}
		     	}    
		    }
		}); 
		
		//设置截图按钮的快捷键监听
		/*KeyAdapter ka=new KeyAdapter(){
			public void keyPressed(KeyEvent e) {
				if(e.isControlDown() &&  e.isShiftDown()&& e.getKeyCode() == KeyEvent.VK_A ){
					action();
				}
				if(e.getKeyCode() == KeyEvent.VK_ESCAPE ){
					System.exit(0);
				}
			}
		};
		b1.addKeyListener(ka);
		b2.addKeyListener(ka);
		*/
		
		
		//设置关闭按钮
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		Label label = new Label("请按下鼠标左键并拖动");
		
		//锁定窗口
		frame.setAlwaysOnTop(true);
		
		Toolkit kit = Toolkit.getDefaultToolkit();  
		Image img=kit.getImage("lib/icon.png");  
		Cursor cu=kit.createCustomCursor(img,new Point(10,5),"stick");  
		frame.setCursor(cu);
		
		//窗体布局
		frame.add(label, "North");
		Panel p = new Panel();
		p.add(b1);
		p.add(b2);	
		frame.add(p,BorderLayout.EAST);
		///frame.setSize(200, 100);
		//frame.setLocation(1000, 0);
		frame.setSize(width,height);
		frame.setLocation(0,0);
		
        frame.setUndecorated(true);
        
        //设置透明度   使用了rt.jar中的AWTUtilities类 (需要提前对类库进行access设置
        AWTUtilities.setWindowOpacity(frame, 0.01f);
        
		frame.setVisible(true);
	}
	
	//确保截图的矩形面积非负
	void ensure(){
		int tmp;
		if(startx<endx&&starty<endy){
			return;
		}
		if(starty>endy){
			tmp=starty;
			starty=endy;
			endy=tmp;
			if(startx>endx){
				tmp=startx;
				startx=endx;
				endx=tmp;
			}
		}
		if(startx>endx){
			tmp=startx;
			startx=endx;
			endx=tmp;
		}
	}
	
	
	//具体截屏步骤
	public void action(){
		Point point = MouseInfo.getPointerInfo().getLocation();
		try {
			//得到矩形截图并存入文件
			ensure();
			BufferedImage scnst = (new Robot()).createScreenCapture(
					new Rectangle(startx, starty, endx-startx,
							endy-starty)
					);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
			String dt=df.format(new Date());// new Date()为获取当前系统时间
			//将文件命名为时间.jpg (未实现
			
			String dtname="C:\\Users\\Administrator\\Pictures\\";
			File file = new File(dtname+df+".jpg");
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