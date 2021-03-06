package Client;

import javax.swing.ComboBoxEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import data.Data;

import java.awt.EventQueue;
import java.awt.Color;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DecimalFormat;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Client extends JFrame {

    private static final long serialVersionUID = 1L;
    
	private JPanel contentPane;
	private JTextField txtPortClient;
	private JTextField txtDiaChiIP;
	private JTextField txtTenClient;
	private JButton btnTimFile;
	private JButton btnKetNoi;
	private JButton btnIn;
	private DefaultListModel model_trangThai;
	JTextArea txt;
	ObjectOutputStream out;
	 
	
	Socket socket ;
	ObjectOutputStream output;
	File f;
	
	DataOutputStream outToServer ;
    ObjectOutputStream oos ;
    ObjectInputStream ois ;
    private JTextField txtName;
	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Client frame = new Client();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Client() {
		initComponents();
	}

	public void initComponents() {
		model_trangThai = new DefaultListModel();
		
		setTitle("Client - Chia s??? m??y in trong m???ng LAN");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
	
		
		ImageIcon originalIcon = new ImageIcon(Client.class.getResource("/Image/mayclient.jfif"));
        JLabel originalLabel = new JLabel(originalIcon);
        int width = originalIcon.getIconWidth() /2;
        int height = originalIcon.getIconHeight() / 2;
        Image scaled = scaleImage(originalIcon.getImage(), width, height);
        ImageIcon scaledIcon = new ImageIcon(scaled);
        contentPane.setLayout(null);
        JLabel newLabel = new JLabel(scaledIcon);
        newLabel.setBounds(22, 79, 136, 132);
        newLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        contentPane.add(newLabel);
        
        JLabel lblNameClient = new JLabel("M??y kh??ch");
        lblNameClient.setBounds(310, 5, 128, 33);
        lblNameClient.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblNameClient.setForeground(new Color(0, 0, 128));
        lblNameClient.setHorizontalAlignment(SwingConstants.CENTER);
        contentPane.add(lblNameClient);
        
        JLabel lblNewLabel = new JLabel("C???ng");
        lblNewLabel.setToolTipText("Nh???p c???ng k???t n???i m??y ch???");
        lblNewLabel.setBounds(219, 125, 49, 18);
        lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 13));
        contentPane.add(lblNewLabel);
        
        txtPortClient = new JTextField("9999");
        txtPortClient.setBounds(278, 125, 145, 20);
        txtPortClient.setBackground(new Color(245, 245, 245));
        txtPortClient.setColumns(10);
        contentPane.add(txtPortClient);
        
        JLabel lblaChIp = new JLabel("?????a ch??? IP");
        lblaChIp.setToolTipText("Nh???p ?????a ch??? IP m??y kh??ch");
        lblaChIp.setBounds(201, 96, 63, 18);
        lblaChIp.setHorizontalAlignment(SwingConstants.RIGHT);
        lblaChIp.setFont(new Font("Dialog", Font.BOLD, 13));
        contentPane.add(lblaChIp);
        
        txtDiaChiIP = new JTextField();
        txtDiaChiIP.setText("Localhost");
        txtDiaChiIP.setBounds(278, 96, 145, 20);
        txtDiaChiIP.setBackground(new Color(245, 245, 245));
        txtDiaChiIP.setColumns(10);
        contentPane.add(txtDiaChiIP);
        
        JLabel lblNewLabel_1 = new JLabel("Ch???n t???p c???n in");
        lblNewLabel_1.setBounds(169, 297, 125, 20);
        lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_1.setForeground(new Color(0, 0, 102));
        contentPane.add(lblNewLabel_1);
        
        btnTimFile = new JButton("T??m t???p");
        btnTimFile.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		 
        			final JFileChooser fc = new JFileChooser();
        			int c = fc.showOpenDialog(null);
        	        try {
        	            if (c == JFileChooser.APPROVE_OPTION) {
        	            	txtName.setText(fc.getSelectedFile().getName());
        	            	f  = fc.getSelectedFile();
        	            }
        	        } catch (Exception ex) {
        	            ex.printStackTrace();
        	        }
        		
        	}
        });
        
        btnTimFile.setBounds(587, 320, 86, 25);
        btnTimFile.setForeground(new Color(255, 255, 255));
        btnTimFile.setBackground(new Color(0, 0, 128));
        btnTimFile.setFont(new Font("Tahoma", Font.BOLD, 13));
        contentPane.add(btnTimFile);
        
     
         btnIn = new JButton("In");
         btnIn.setFont(new Font("Tahoma", Font.BOLD, 13));
         btnIn.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		 try {
                         FileInputStream in = new FileInputStream(f);
                         byte b[] = new byte[in.available()];
                         in.read(b);
                         Data data = new Data();
                         data.setNameFile(txtName.getText().trim());
                         data.setFile(b);
                         out.writeObject(data);
                         out.flush();
                         txt.append("???? g???i 1 t???p v???i t??n "+txtName.getText()+"...\n"); 
                         txtName.setText("");
                     
                 } catch (Exception e2) {
                     JOptionPane.showMessageDialog(null, e2, "L???i", JOptionPane.ERROR_MESSAGE);
                 }
         	
         	}
         });
        btnIn.setForeground(new Color(255, 255, 255));
        btnIn.setBackground(new Color(0, 0, 128));
        btnIn.setBounds(558, 368, 115, 30);
        contentPane.add(btnIn);
        
        JLabel lblNewLabel_5 = new JLabel("Tr???ng th??i k???t n???i");
        lblNewLabel_5.setForeground(new Color(0, 0, 102));
        lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblNewLabel_5.setBounds(172, 154, 125, 25);
        contentPane.add(lblNewLabel_5);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(169, 179, 504, 107);
        contentPane.add(scrollPane);
        
         txt = new JTextArea();
         scrollPane.setViewportView(txt);
        
        JLabel lblTnMy = new JLabel("T??n");
        lblTnMy.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTnMy.setFont(new Font("Dialog", Font.BOLD, 13));
        lblTnMy.setBounds(233, 64, 31, 18);
        contentPane.add(lblTnMy);
        
        txtTenClient = new JTextField();
        txtTenClient.setToolTipText("Nh???p t??n m??y kh??ch");
        txtTenClient.setColumns(10);
        txtTenClient.setBackground(new Color(245, 245, 245));
        txtTenClient.setBounds(278, 64, 145, 20);
        contentPane.add(txtTenClient);
        
         btnKetNoi = new JButton("K???t n???i");
         btnKetNoi.setFont(new Font("Tahoma", Font.BOLD, 11));
         btnKetNoi.addActionListener(new ActionListener() {
         	public void actionPerformed(ActionEvent e) {
         		try {
                    socket = new Socket(txtDiaChiIP.getText().trim(), Integer.parseInt(txtPortClient.getText().trim()));
                    txt.append("???? k???t n???i th??nh c??ng ...\n");
                    out = new ObjectOutputStream(socket.getOutputStream());
                    Data data = new Data();
                    data.setStatus("new");
                    data.setNameClient(txtTenClient.getText().toString());
                    out.writeObject(data);
                    out.flush();
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(null, e1, "L???i", JOptionPane.ERROR_MESSAGE);
                }
         	}
         });
   
        btnKetNoi.setBackground(new Color(0, 0, 128));
        btnKetNoi.setForeground(new Color(255, 255, 255));
        btnKetNoi.setBounds(488, 64, 118, 25);
        contentPane.add(btnKetNoi);
        
        
        JButton btnngKtNi = new JButton("????ng k???t n???i");
        btnngKtNi.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnngKtNi.setForeground(Color.WHITE);
        btnngKtNi.setBackground(new Color(0, 0, 128));
        btnngKtNi.setBounds(488, 96, 118, 25);
        contentPane.add(btnngKtNi);
        
        txtName = new JTextField();
        txtName.setBounds(250, 322, 327, 23);
        contentPane.add(txtName);
        txtName.setColumns(10);
        
		setBounds(100, 100, 730, 448);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	private Image scaleImage(Image image, int w, int h) {
	        Image scaled = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
	        return scaled;
	}
}
