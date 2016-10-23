/*
 * �Դ��ڵ�����...
 * 1,ͼƬ��������
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
	//�����ȼ���ֵ
	static final int KEY_1 = 88;
	static final int KEY_2 = 89;  
	static final int KEY_3 = 90;
	static final int KEY_4 = 91;
	//��������
	JFrame frame = new JFrame("��ͼ����");
	Button b1 = new Button("��ͼ");
	Button b2 = new Button("�ر�");
	
	
	//��¼��ͼʱ���Ŀ�ʼλ�úͽ���λ��
	int startx,starty;
	int endx,endy;

	//��ȡ��Ļ���
	Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
	int width = (int)screensize.getWidth();
	int height = (int)screensize.getHeight();
	
	public screen_shot(){
		
		//�������¼�
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
				String string = "�����("+startx+","+starty
						+")�ƶ�����" + e.getX() + "��" + e.getY() +"��";
			}
		}); 
		
		
		//�������
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		
		
		//���ý�ͼ��ť�ĵ����¼�
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				action();
			}
		});
		
		//����jintellitype�������ȫ���ȼ�
		JIntellitype.getInstance().registerHotKey(KEY_1,JIntellitype.MOD_WIN,(int)'A');
		JIntellitype.getInstance().registerHotKey(KEY_2,0,27);
		JIntellitype.getInstance().registerHotKey(KEY_3,JIntellitype.MOD_WIN+JIntellitype.MOD_CONTROL,(int)'Z');
		JIntellitype.getInstance().registerHotKey(KEY_4,JIntellitype.MOD_WIN+JIntellitype.MOD_CONTROL,(int)'X');
		
		
		
		JIntellitype.getInstance().addHotKeyListener(new HotkeyListener() {
			public void onHotKey(int markCode) {
				switch (markCode) {  
					case KEY_1:  {
						if( (startx <= 0 || endx <= 0) || (startx == endx && starty == endy)){
							JOptionPane.showMessageDialog(frame, "δѡ�н�ͼ����");
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
		
		//���ý�ͼ��ť�Ŀ�ݼ�����
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
		
		
		//���ùرհ�ť
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		Label label = new Label("�밴�����������϶�");
		
		//��������
		frame.setAlwaysOnTop(true);
		
		Toolkit kit = Toolkit.getDefaultToolkit();  
		Image img=kit.getImage("lib/icon.png");  
		Cursor cu=kit.createCustomCursor(img,new Point(10,5),"stick");  
		frame.setCursor(cu);
		
		//���岼��
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
        
        //����͸����   ʹ����rt.jar�е�AWTUtilities�� (��Ҫ��ǰ��������access����
        AWTUtilities.setWindowOpacity(frame, 0.01f);
        
		frame.setVisible(true);
	}
	
	//ȷ����ͼ�ľ�������Ǹ�
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
	
	
	//�����������
	public void action(){
		Point point = MouseInfo.getPointerInfo().getLocation();
		try {
			//�õ����ν�ͼ�������ļ�
			ensure();
			BufferedImage scnst = (new Robot()).createScreenCapture(
					new Rectangle(startx, starty, endx-startx,
							endy-starty)
					);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
			String dt=df.format(new Date());// new Date()Ϊ��ȡ��ǰϵͳʱ��
			//���ļ�����Ϊʱ��.jpg (δʵ��
			
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
		System.out.println("�ѽ���");
	}
	
	public static void main(String[] args) {
		new screen_shot();
	}
}