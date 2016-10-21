
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
	
	//��������
	JFrame frame = new JFrame("��ͼ����");
	Button b1 = new Button("��ͼ");
	Button b2 = new Button("�ر�");
	
	TextField tField = new TextField(30);
	
	//��¼��ͼʱ���Ŀ�ʼλ�úͽ���λ��
	int startx,starty;
	int endx,endy;
	
	public screen_shot(){
		
		//��ȡ��Ļ���
		Dimension   screensize   =   Toolkit.getDefaultToolkit().getScreenSize();
		int width = (int)screensize.getWidth();
		int height = (int)screensize.getHeight();
		
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
			public void mouseEntered(MouseEvent e) {
				tField.setText("����Ѿ����봰��");
			}
			public void mouseExited(MouseEvent e) {
				tField.setText("����Ѿ��Ƴ�����");
				
			}
		});
		frame.addMouseMotionListener(new MouseAdapter() {
			public void mouseDragged(MouseEvent e) {
				String string = "�����("+startx+","+starty
						+")�ƶ�����" + e.getX() + "��" + e.getY() +"��";
				tField.setText(string);
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
		
		//���ý�ͼ��ť�Ŀ�ݼ�����(��bug
		b1.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if(e.isControlDown() &&  e.isShiftDown()&& e.getKeyCode() == KeyEvent.VK_A ){
					new screen_shot().action();
				}
			}
		});
		
		//���ùرհ�ť
		b2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		Label label = new Label("�밴�����������϶�");
		
		//��������
		frame.setAlwaysOnTop(true);
		
		//���岼��
		frame.add(label, "North");
		frame.add(tField, "South");
		Panel p = new Panel();
		p.add(b1);
		p.add(b2);	
		frame.add(p,BorderLayout.EAST);
		frame.setSize(200, 100);
		frame.setLocation(1000, 0);
		
        frame.setUndecorated(true);
        //����͸����   ʹ����rt.jar�е�AWTUtilities�� (��Ҫ��ǰ��������access����
        AWTUtilities.setWindowOpacity(frame, 0.7f);
        
		frame.setVisible(true);
	}
	//�����������
	public void action(){
		Point point = MouseInfo.getPointerInfo().getLocation();
		try {
			//�õ����ν�ͼ�������ļ�
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
		System.out.println("�ѽ���");
	}
	
	public static void main(String[] args) {
		new screen_shot();
	}
	

}