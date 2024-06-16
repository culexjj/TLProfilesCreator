 package checkNews.gui;


import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
//import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import checkNews.data.ChatTelegram;
import checkNews.data.ChatTelegramHM;
import checkNews.data.DataSetMessage;
import checkNews.data.DataSetMessageHM;
import checkNews.data.DataSetUser;
import checkNews.data.DataSetUserHM;
import checkNews.data.MessageTelegram;
import checkNews.data.MessageTelegramHM;
import checkNews.data.News;
import checkNews.data.NewsHM;
import checkNews.data.StatisticData;
import checkNews.data.UserTelegram;
import checkNews.support.IOManager;
import checkNews.support.gui.BarChart;
import checkNews.support.gui.HeaderTable;
import checkNews.support.gui.LineChart;
//import checkNews.support.OpenBrowser;
import checkNews.support.gui.PieChart;
import checkNews.support.gui.SetOutputStream;

/**
 * Main View
 * @author Jose Javier Culebras
 * @version 1.0
 */
public class View {
	

	/*--------------------------*/
	/*  VARIABLES DECLARATION	*/
	/*--------------------------*/
	
		final JFrame frame;
		final JPanel newsJPanel, messagesJPanel, chatJPanel, dataSetJPanel;
		final JScrollPane scrollNews, scrollMessageTelegram,scrollChatTelegram,scrollDataSetA,scrollDataSetB;
		final JTabbedPane tabPanel;
		final DefaultTableModel modelNews,modelMessageTelegram,modelChatTelegram,modelDataSetA,modelDataSetB; 
		final JMenuBar menuBar;				
		final JMenu file, internet, telegram, dataset, statistical, help; 
		final JMenuItem i1, i2, i3, i4, i5, i6, i7, i8, i9, i10, i10bis,i11, i12, i13, i14, i15, i16, i17, i18, i19, i20, i21, i22, i23, i24, i25, i26, i27, i28, i29, i30, i31, i32, i33; 
	    
	    final JTable jTableNews;
		final JTable jTableMessageTelegram;
		final JTable jTableChatTelegram;
		final JTable jTableDataSetA;
		final JTable jTableDataSetB;
		
		final JMenuItem applyFilterNews, openBrowser, searchMessages, applyFilterMessages, createDataset, openTelegramWeb, applyFilterChats, searchPublicChats, joinToChat; 
		
		final JPopupMenu  tableNewsPopup;
		final JPopupMenu  tableMessageTelegramPopup;
		final JPopupMenu  tableChatTelegramPopup;

		private String filterNews = "";
		private String filterNewsNew = "";
		private String filterMessages = "";
		private String filterMessagesNew = "";
		private String filterChats = "";
		private String filterChatsNew = "";
		private String rightButtonValue = "";
		private String columnName = "";
		
		private ArrayList<String> NewsMultipleRowSeleccion = new ArrayList<String>(); //Array for keep values of multiple row selecction on Jtable News
		private boolean rightButtonclick = false; //bool for checking if mouse right button has been clicked

		
	/*--------------------------*/
	/*          BUILDERS        */
	/*--------------------------*/
	
	@SuppressWarnings("serial")
	public View() {
		
		frame = new JFrame("Telegram-ProfilesCreator v3.0");
		
		/* Jtable objects: global definitions (structure & headers) */
		String tableData[][]={};
		String columnNews[]={"NAME","URL","DESCRIPTION"};
		String columnMessagesTelegram[]={"DATE","SENDERID","CHATID (NAME)","MESSAGEID","URL / KEYWORD", "CONTENT"};
		String columnChatsTelegram[]={"STATUS","CHATID","CHAT NAME"};
		String columnDataSetA[]={"TYPE","SENDERID","NAME","ATTRIBUTES","DATE","CHATID","CONTENT"};
		String columnDataSetB[]={"TYPE","MESSAGE","SENDERID","NAME","ATTRIBUTES"};
		
		String[] newsColumnTips = { "Click a header to sort data", "Use the right button for searching messages","Just News from RSS sources have a description"};
		String[] messagesColumnTips = { "Click a header to sort data", "Use the right button for create a USER dataset",null,"Use the right button for opening the MESSAGE","Use the right button for create a MESSAGE dataset",null};
		String[] chatsColumnTips = { "Click a header to sort data", "Use the right button for joining",null};
		String[] dataSetAColumnTips = { null, null,null,"Account attributes: Fake, Scam, Verified, Premium",null,null,null};
		String[] dataSetBColumnTips = { null, null,null,null,"Account attributes: Fake, Scam, Verified, Premium"};
		
		
		/* NEWS Table definion. */	
		
		modelNews = new DefaultTableModel(tableData,columnNews) { 
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
				
		jTableNews = new JTable(modelNews) { 
			
		public String getToolTipText(MouseEvent e) { //Filter
				        
			if (!(filterNews.equals(""))) {
					
				return "Applied filter: " + filterNews;
			} else {
					
				return "No filter";
			}
		}
			
		protected JTableHeader createDefaultTableHeader() { return new JTableHeader(columnModel) { //Table header popup
		
			public String getToolTipText(MouseEvent e) {
				
                java.awt.Point position = e.getPoint();
                int index = columnModel.getColumnIndexAtX(position.x);
                int columnIndex = columnModel.getColumn(index).getModelIndex();
                return newsColumnTips[columnIndex];
            }};
        }};
        
        /* Allowing multiple row selection */
	    ListSelectionModel listSelectionModel;
	    listSelectionModel = jTableNews.getSelectionModel();
	    listSelectionModel.addListSelectionListener(new SharedListSelectionHandler());
	    jTableNews.setSelectionModel(listSelectionModel);
		listSelectionModel.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        		
        jTableNews.addMouseListener(new MouseListener());    
        
        /* Right button options NEWS table */
		tableNewsPopup = new JPopupMenu();
		
		applyFilterNews = new JMenuItem("Filter by");
		tableNewsPopup.add(applyFilterNews);
		applyFilterNews.setActionCommand("SHOWNEWSFILTER");
		
		openBrowser = new JMenuItem("Open browser");
		tableNewsPopup.add(openBrowser);
		openBrowser.setActionCommand("OPENBROWSER");
		
		searchMessages = new JMenuItem("Search Messages");
		tableNewsPopup.add(searchMessages);
		searchMessages.setActionCommand("SEARCHMESSAGESALLCHATRB");

		jTableNews.setComponentPopupMenu(tableNewsPopup); 
			
        jTableNews.setRowSorter(new TableRowSorter<DefaultTableModel>(modelNews)); //Sorter
        jTableNews.getColumnModel().getColumn(0).setHeaderRenderer(new HeaderTable(SwingConstants.LEFT)); //Header label position LEFT
        scrollNews = new JScrollPane(jTableNews);
        
       	
        /* MESSAGE TELEGRAM Table definion */
        
		modelMessageTelegram = new DefaultTableModel(tableData,columnMessagesTelegram) { 
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};

		jTableMessageTelegram = new JTable(modelMessageTelegram) {
			
			public String getToolTipText(MouseEvent e) { //Filter
			
			if (!(filterMessages.equals(""))) {
				
				return "Applied filter: " + filterMessages;
			} else {
				
				return "No filter";
			}
		} 
		
		protected JTableHeader createDefaultTableHeader() { return new JTableHeader(columnModel) { //Table header popup
			public String getToolTipText(MouseEvent e) {
				
                java.awt.Point position = e.getPoint();
                int index = columnModel.getColumnIndexAtX(position.x);
                int columnIndex = columnModel.getColumn(index).getModelIndex();
                return messagesColumnTips[columnIndex];
            }};
        }};
        
        jTableMessageTelegram.addMouseListener(new MouseListener());
     
        /* Right button options MESSAGE TELEGRAM table */
        tableMessageTelegramPopup = new JPopupMenu();
        
        applyFilterMessages = new JMenuItem("Filter by");
        tableMessageTelegramPopup.add(applyFilterMessages);
		applyFilterMessages.setActionCommand("SHOWMESSAGESFILTER");
       
        createDataset = new JMenuItem("Create Dataset");
        tableMessageTelegramPopup.add(createDataset);
        createDataset.setActionCommand("CREATEDATASETRB");     
       
        openTelegramWeb = new JMenuItem("Open Telegram");
        tableMessageTelegramPopup.add(openTelegramWeb);
        openTelegramWeb.setActionCommand("OPENTELEGRAMWEB");
                
        jTableMessageTelegram.setComponentPopupMenu(tableMessageTelegramPopup);
                
        jTableMessageTelegram.setRowSorter(new TableRowSorter<DefaultTableModel>(modelMessageTelegram)); //Sorter
		jTableMessageTelegram.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTableMessageTelegram.getColumnModel().getColumn(0).setPreferredWidth(130);
		jTableMessageTelegram.getColumnModel().getColumn(1).setPreferredWidth(120);
		jTableMessageTelegram.getColumnModel().getColumn(2).setPreferredWidth(240);
		jTableMessageTelegram.getColumnModel().getColumn(3).setPreferredWidth(120);
		jTableMessageTelegram.getColumnModel().getColumn(4).setMinWidth(180);
		jTableMessageTelegram.getColumnModel().getColumn(5).setMinWidth(1200);
		jTableMessageTelegram.getColumnModel().getColumn(0).setHeaderRenderer(new HeaderTable(SwingConstants.LEFT)); //Header label position LEFT
		scrollMessageTelegram = new JScrollPane(jTableMessageTelegram);
		
		
		/* CHAT TELEGRAM Table definion */
		
		modelChatTelegram = new DefaultTableModel(tableData,columnChatsTelegram) { 
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		
		jTableChatTelegram = new JTable(modelChatTelegram) {
			
			public String getToolTipText(MouseEvent e) { //Filter
	        
			if (!(filterChats.equals(""))) {
					
				return "Applied filter: " + filterChats;
			} else {
					
				return "No filter";
			}
		}
			
		protected JTableHeader createDefaultTableHeader() { return new JTableHeader(columnModel) { //Table header popup
			public String getToolTipText(MouseEvent e) {
			
				Point position = e.getPoint();
	            int index = columnModel.getColumnIndexAtX(position.x);
	            int columnIndex = columnModel.getColumn(index).getModelIndex();
	            return chatsColumnTips[columnIndex];
		    }};
		}};
	
		jTableChatTelegram.addMouseListener(new MouseListener());
		
		/* Right button options CHAT TELEGRAM table */
		tableChatTelegramPopup = new JPopupMenu();
		
		applyFilterChats = new JMenuItem("Filter by");
		tableChatTelegramPopup.add(applyFilterChats);
		applyFilterChats.setActionCommand("SHOWCHATSFILTER");
		
		searchPublicChats = new JMenuItem("Search chats");
		tableChatTelegramPopup.add(searchPublicChats);
		searchPublicChats.setActionCommand("SEARCHPUBLICCHATS");
		
		joinToChat = new JMenuItem("Join to chat");
		tableChatTelegramPopup.add(joinToChat);
		joinToChat.setActionCommand("JOINTOCHATRB");
		
		jTableChatTelegram.setComponentPopupMenu(tableChatTelegramPopup); 
		
		jTableChatTelegram.setRowSorter(new TableRowSorter<DefaultTableModel>(modelChatTelegram)); //Sorter
		jTableChatTelegram.getColumnModel().getColumn(0).setHeaderRenderer(new HeaderTable(SwingConstants.LEFT)); //Header label position LEFT
		scrollChatTelegram = new JScrollPane(jTableChatTelegram);
		
		
		/* DATASET A (USER) Table definion */
		
		modelDataSetA = new DefaultTableModel(tableData,columnDataSetA) { 
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		
		jTableDataSetA = new JTable(modelDataSetA) {
			
			protected JTableHeader createDefaultTableHeader() { return new JTableHeader(columnModel) { //Table header popup
				
				public String getToolTipText(MouseEvent e) { //Table header popup
				
	                java.awt.Point position = e.getPoint();
	                int index = columnModel.getColumnIndexAtX(position.x);
	                int columnIndex = columnModel.getColumn(index).getModelIndex();
	                return dataSetAColumnTips[columnIndex];
		    }};
		}};
		
		jTableDataSetA.setColumnSelectionAllowed(true);
					
		jTableDataSetA.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTableDataSetA.getColumnModel().getColumn(0).setPreferredWidth(85);
		jTableDataSetA.getColumnModel().getColumn(1).setPreferredWidth(100);
		jTableDataSetA.getColumnModel().getColumn(2).setPreferredWidth(200);
		jTableDataSetA.getColumnModel().getColumn(3).setPreferredWidth(85);
		jTableDataSetA.getColumnModel().getColumn(4).setMinWidth(130);
		jTableDataSetA.getColumnModel().getColumn(5).setPreferredWidth(120);
		jTableDataSetA.getColumnModel().getColumn(6).setMinWidth(1300);
		jTableDataSetA.getColumnModel().getColumn(0).setHeaderRenderer(new HeaderTable(SwingConstants.LEFT)); //Header label position LEFT
		scrollDataSetA = new JScrollPane(jTableDataSetA);
		
		
		/* DATASET B (MESSAGE) Table definion */
		
		modelDataSetB = new DefaultTableModel(tableData,columnDataSetB){ 
			@Override
			public boolean isCellEditable(int row, int column) { return false; }
		};
		
		jTableDataSetB = new JTable(modelDataSetB) {
			
			protected JTableHeader createDefaultTableHeader() { return new JTableHeader(columnModel) { //Table header popup
				
				public String getToolTipText(MouseEvent e) {
				
	                java.awt.Point position = e.getPoint();
	                int index = columnModel.getColumnIndexAtX(position.x);
	                int columnIndex = columnModel.getColumn(index).getModelIndex();
	                return dataSetBColumnTips[columnIndex];
		    }};
		}};
		
		jTableDataSetB.setColumnSelectionAllowed(true);
		
		jTableDataSetB.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jTableDataSetB.getColumnModel().getColumn(0).setPreferredWidth(85);
		jTableDataSetB.getColumnModel().getColumn(1).setPreferredWidth(355);
		jTableDataSetB.getColumnModel().getColumn(2).setPreferredWidth(130);
		jTableDataSetB.getColumnModel().getColumn(3).setPreferredWidth(300);
		jTableDataSetB.getColumnModel().getColumn(4).setMinWidth(1300);
		jTableDataSetB.getColumnModel().getColumn(0).setHeaderRenderer(new HeaderTable(SwingConstants.LEFT)); //Header label position LEFT
		scrollDataSetB = new JScrollPane(jTableDataSetB);
	
		/* GUI definition Tabs */
		newsJPanel = new JPanel(new BorderLayout()); 
		newsJPanel.add(scrollNews, BorderLayout.CENTER);
        messagesJPanel = new JPanel(new BorderLayout()); 
        messagesJPanel.add(scrollMessageTelegram, BorderLayout.CENTER);
        chatJPanel = new JPanel(new BorderLayout()); 
        chatJPanel.add(scrollChatTelegram, BorderLayout.CENTER);	       
        dataSetJPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(dataSetJPanel,BoxLayout.Y_AXIS);
		dataSetJPanel.setLayout(boxLayout);
		dataSetJPanel.add(scrollDataSetA);
		dataSetJPanel.add(scrollDataSetB);
		
		tabPanel = new JTabbedPane();
        tabPanel.addTab("NEWS", newsJPanel); 
        tabPanel.addTab("MESSAGES", messagesJPanel);
        tabPanel.addTab("CHATS", chatJPanel);
        tabPanel.addTab("DATASET", dataSetJPanel);
        
        /* GUI definition Menus */
        menuBar = new JMenuBar();
        file = new JMenu("File");
        internet = new JMenu("Internet");
        telegram = new JMenu("Telegram");
        dataset = new JMenu("DataSet");
        statistical = new JMenu("Information");
        help = new JMenu("Help");
        
        /* GUI definition File menu */
        i23=new JMenuItem("Load News (.json)");
        i23.setActionCommand("LOADNEWSFILE");
        i24=new JMenuItem("Load Messages (.json)");
        i24.setActionCommand("LOADMESSAGESFILE");      
        i6=new JMenuItem("Save News (.json)");
        i6.setActionCommand("SAVENEWSTOFILE");
        i15=new JMenuItem("Save Messages (.json)");
        i15.setActionCommand("SAVEMESSAGESTOFILE");        
        
        file.add(i23);file.add(i24);file.add(i6);file.add(i15);
        
        /* GUI definition NEWS menu */
        i1=new JMenuItem("Show News");
        i1.setActionCommand("SHOWNEWS");        
        i2=new JMenuItem("Show News (filter)");
        i2.setActionCommand("SHOWNEWSFILTER");
        i3=new JMenuItem("Search News");
        i3.setActionCommand("SEARCHNEWS");
        i4=new JMenuItem("Search News RSS");
        i4.setActionCommand("SEARCHNEWSRSS");
        i5=new JMenuItem("Search News RSS (filter)");
        i5.setActionCommand("SEARCHNEWSRSSFILTER");
        i7=new JMenuItem("Delete News");
        i7.setActionCommand("DELETENEWS");
               
        internet.add(i1); internet.add(i2); internet.add(i3); internet.add(i4);internet.add(i5);internet.add(i7); 
        
        /* GUI definition TELEGRAM menu */
        i8=new JMenuItem("Show Messages"); 
        i8.setActionCommand("SHOWMESSAGES");
        i9=new JMenuItem("Show Messages (filter)");
        i9.setActionCommand("SHOWMESSAGESFILTER");     
        i10=new JMenuItem("Show Chats");
        i10.setActionCommand("SHOWCHATS");
        i10bis=new JMenuItem("Show Chats (filter)");
        i10bis.setActionCommand("SHOWCHATSFILTER"); 
        i11=new JMenuItem("Search Messages (chat)");
        i11.setActionCommand("SEARCHMESSAGES");
        i12=new JMenuItem("Search Messages");
        i12.setActionCommand("SEARCHMESSAGESALLCHAT"); 
        i13=new JMenuItem("Search Public chats"); 
        i13.setActionCommand("SEARCHPUBLICCHATS");
        i14=new JMenuItem("Join to chat");
        i14.setActionCommand("JOINTOCHAT");
        i16=new JMenuItem("Delete Messages");
        i16.setActionCommand("DELETEMESSAGES");
        i17=new JMenuItem("Clear Chats view");
        i17.setActionCommand("DELETECHATS");
        
        telegram.add(i8); telegram.add(i9); telegram.add(i10); telegram.add(i10bis); telegram.add(i11); telegram.add(i12); telegram.add(i13);
        telegram.add(i14);telegram.add(i16);telegram.add(i17);
        
        /* GUI definition DATASET menu */
        i18=new JMenuItem("Show Datasets");
        i18.setActionCommand("SHOWDATASETS");
        i19=new JMenuItem("User Datasets (filter)");
        i19.setActionCommand("SHOWDATASETSFILTERA");
        i20=new JMenuItem("Message Datasets (filter)");
        i20.setActionCommand("SHOWDATASETSFILTERB");
        i21=new JMenuItem("Create Dataset");
        i21.setActionCommand("CREATEDATASET");
        i22=new JMenuItem("Clear DataSet view");
        i22.setActionCommand("DELETEDATASETS");
        
        dataset.add(i18); dataset.add(i19);dataset.add(i20);dataset.add(i21);dataset.add(i22);
        
        /* GUI definition STATISTIC menu */
        i25=new JMenuItem("Output console");
        i25.setActionCommand("SHOWMESSAGESCONSOLE");
        i26 = new JMenuItem("Total figures");
        i26.setActionCommand("STATISTICALDATA");
        i30 = new JMenuItem("Messages by type");
        i30.setActionCommand("MESSAGESBYTYPE");
        i31 = new JMenuItem("Messages by hour");
        i31.setActionCommand("MESSAGESBYHOUR");
        i32 = new JMenuItem("News by type");
        i32.setActionCommand("NEWSBYTYPE");
        i33 = new JMenuItem("Messages by day");
        i33.setActionCommand("MESSAGESBYDAY");
        
        statistical.add(i25);statistical.add(i26); statistical.add(i32); statistical.add(i30);statistical.add(i31);statistical.add(i33);
        
        /* GUI definition HELP menu */
        i27 = new JMenuItem("RSS list");
        i27.setActionCommand("RSSLIST");
        i28 = new JMenuItem("Chats List");
        i28.setActionCommand("CHATSLIST");
        i29 = new JMenuItem("About");
        i29.setActionCommand("ABOUT");
        
        help.add(i27); help.add(i28); help.add(i29);
        
        menuBar.add(file);
        menuBar.add(internet); 
        menuBar.add(telegram);
        menuBar.add(dataset);
        menuBar.add(statistical);
        menuBar.add(help);
	}

	
	/*--------------------------*/
	/*    PUBLIC   METHODS      */
	/*--------------------------*/
	
	/**
	 * Method for adding actions to controller 
	 * @param controller
	 */
	public void setControl (Controller controller) {
		i1.addActionListener(controller);
		i2.addActionListener(controller);
		i3.addActionListener(controller);
		i4.addActionListener(controller);
		i5.addActionListener(controller);
		i6.addActionListener(controller);
		i7.addActionListener(controller);
		i8.addActionListener(controller);
		i9.addActionListener(controller);
		i10.addActionListener(controller);
		i10bis.addActionListener(controller);
		i11.addActionListener(controller);
		i12.addActionListener(controller);
		i13.addActionListener(controller);
		i14.addActionListener(controller);
		i15.addActionListener(controller);
		i16.addActionListener(controller);
		i17.addActionListener(controller);
		i18.addActionListener(controller);
		i19.addActionListener(controller);
		i20.addActionListener(controller);
		i21.addActionListener(controller);
		i22.addActionListener(controller);
		i23.addActionListener(controller);
		i24.addActionListener(controller);
		i25.addActionListener(controller);
		i26.addActionListener(controller);
		i27.addActionListener(controller);
		i28.addActionListener(controller);
		i29.addActionListener(controller);
		i30.addActionListener(controller);
		i31.addActionListener(controller);
		i32.addActionListener(controller);
		i33.addActionListener(controller);
		applyFilterNews.addActionListener(controller);
		openBrowser.addActionListener(controller);
		searchMessages.addActionListener(controller);
		applyFilterMessages.addActionListener(controller);
		createDataset.addActionListener(controller);
		openTelegramWeb.addActionListener(controller);
		applyFilterChats.addActionListener(controller);
		searchPublicChats.addActionListener(controller);
		joinToChat.addActionListener(controller);	
	}
	
	
	/**
	 * Method for starting main view
	 */
	public void start() {
		
		frame.setSize(1366,768);	      
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(tabPanel);
        frame.setJMenuBar(menuBar);		
        frame.addWindowListener(new WindowAdapter());
        
        try {
        	
        	UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
       	} catch (ClassNotFoundException e) {
		      
       		System.out.println(e.getMessage());
		} catch (InstantiationException e) {
		      
			System.out.println(e.getMessage());
		} catch (IllegalAccessException e) {
		      
			System.out.println(e.getMessage());
		} catch (UnsupportedLookAndFeelException e) {
			
			System.out.println(e.getMessage());
		}

		SwingUtilities.updateComponentTreeUI(frame);		
		startConsoleOutput();		
		frame.setVisible(true);
	}
	
		
	/*--------------------------*/
	/*   PROTECTED   METHODS    */
	/*--------------------------*/
	
	/**
	 * Method for showing the output messages console 
	 */
	void startConsoleOutput() {
		
		JFrame frameOutputConsole = new JFrame("Output console");
		JTextArea textArea = new JTextArea(50, 10);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        
        PrintStream printStream = new PrintStream(new SetOutputStream(textArea)); //Create a new print stream and assign it to the text area
        System.setOut(printStream); //Set standard output to the new print stream
        
        frameOutputConsole.add(new JScrollPane(textArea));
        frameOutputConsole.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameOutputConsole.setSize(480, 320);
        frameOutputConsole.setLocation(0, Toolkit.getDefaultToolkit().getScreenSize().height - 400);
        frameOutputConsole.setAlwaysOnTop(true);
        frameOutputConsole.setVisible(true);
	}
	
	
	/**
	 * Method for showing Statistics Information window
	 */
	void startTotalFigures() {
	
		StatisticData statisticData = new StatisticData();
		int[] figures = statisticData.getTotalFigures();
		String[] columnsName = {"News", "Messages", "Chats", "User dataset", "Message dataset"}; 
		
		if ((figures[0] == 0) && (figures[1] == 0) && (figures[2] == 0) && (figures[3] == 0) && (figures[4] == 0)) {
			
			System.out.println("INFO: no data available" );
			return;
		}

		new BarChart("Total figures", "TL-ProfilesCreator (Total figures)", columnsName, figures);
    }
	
	
	/**
	 * Method for showing Statistics Information window
	 */
	void startMessagesByType() {
		
		StatisticData statisticData = new StatisticData();
		int[] figures = statisticData.getTotalMessageType();
		String[] names = {"Text", "Picture", "Video", "Others"}; 
		
		if ((figures[0] == 0) && (figures[1] == 0) && (figures[2] == 0) && (figures[3] == 0)) {
	
			System.out.println("INFO: no data available" );
			return;
		} 

		new PieChart("Messages by type", "TL-ProfilesCreator (Messages by type)", names, figures);
	}
	
	
	/**
	 * Method for showing Statistics Information window
	 */
	void startNewsByType() {
		
		StatisticData statisticData = new StatisticData();
		int[] figures = statisticData.getTotalNewsType();
		String[] names = {"Internet", "RSS"}; 
		
		if ((figures[0] == 0) && (figures[1] == 0)) {
			
			System.out.println("INFO: no data available" );
			return;
		} 

		new PieChart("News by type", "TL-ProfilesCreator (News by type)", names, figures);
	}
	
	
	/**
	 * Method for showing Statistics Information window
	 */
	void startMessagesByDay() {
	
		StatisticData statisticData = new StatisticData();
		int[] figures = statisticData.getDays();
		String[] columnsName = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"}; 
		
		if ((figures[0] == 0) && (figures[1] == 0) && (figures[2] == 0) && (figures[3] == 0) && (figures[4] == 0) && (figures[5] == 0)&& (figures[6] == 0)) {
			
			System.out.println("INFO: no data available" );
			return;
		}

		new BarChart("Messages by Day", "TL-ProfilesCreator (Messages by Day)", columnsName, figures);
    }
	
	
	/**
	 * Method for showing Statistics Information window
	 */
	void startMessagesByHour() {
		
		StatisticData statisticData = new StatisticData();
		int[] figures = statisticData.getHours();
		
		boolean control = true;
		
		for (int i = 0; i < figures.length; i++) {
			
			if (figures[i] == 0) {
				
			} else {
				control = false;
				break;
			}
		}
		
		if (control) {
			
			System.out.println("INFO: no data available" );
			return;
		} 

		new LineChart("Messages by Hour", "TL-ProfilesCreator (Messages by hour)", figures, "Hour", "Messages", "Messages by hour", 0, 23);
	}
	
	
	/**
	 * Method for showing the help window
	 */
	void startHelp() {
		
		JFrame frameHelp = new JFrame("About");
		JTextArea textArea = new JTextArea(50, 10);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setLineWrap(true); 
        textArea.setText("Telegram-ProfilesCreator	Release: 3.0  Date:28/03/2024\r\n");
        textArea.append("\r\nThe \"Telegram-ProfilesCreator\" program is used for creating 'News broadcaster profiles' from Social Networks users (Telegram).\r\n");
        textArea.append("Contact: jculebras3@alumno.uned.es\r\n");
        textArea.append("\r\nThe java classes derived from TDLib https://github.com/tdlib/  are under Boost Software License - Version 1.0 - August 17th, 2003");
        textArea.append("\r\nhttps://www.boost.org/LICENSE_1_0.txt");
        
        frameHelp.add(new JScrollPane(textArea));        
        frameHelp.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameHelp.setSize(750, 240);
        frameHelp.setLocationByPlatform(true);
        frameHelp.setVisible(true);
	}
	
	
	/**
	 * Method for showing a list of RSS sites
	 */
	void startRSS() {
		
		JFrame frameRSS = new JFrame("RSS Examples");
		JTextArea textArea = new JTextArea(50, 10);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setText("BBC world news: https://feeds.bbci.co.uk/news/world/rss.xml\r\n"
        		+ "CBN world news: https://www1.cbn.com/rss-cbn-news-world.xml\r\n"
        		+ "BBC technology news: https://feeds.bbci.co.uk/news/technology/rss.xml\r\n"
        		+ "BBC business news: https://feeds.bbci.co.uk/news/business/rss.xml\r\n"
        		+ "El Pais España: https://feeds.elpais.com/mrss-s/pages/ep/site/elpais.com/portada\r\n"
        		+ "El Pais Ciencia: https://feeds.elpais.com/mrss-s/pages/ep/site/elpais.com/section/ciencia/portada\r\n"
        		+ "ABC España: https://www.abc.es/rss/2.0/espana/\r\n"
        		+ "ABC Cultura: https://www.abc.es/rss/2.0/cultura/\r\n"
        		+ "ONU: https://news.un.org/feed/subscribe/es/news/all/rss.xml\r\n"
        		+ "BCE: https://www.ecb.europa.eu/rss/press.html");
        
        frameRSS.add(new JScrollPane(textArea));
        frameRSS.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameRSS.setSize(750, 240);
        frameRSS.setLocationByPlatform(true);
        frameRSS.setVisible(true);
	}

	
	/**
	 * Method for showing a list of public chats
	 */
	void startChatList() {
		
		JFrame frameChats = new JFrame("Chats Examples");
		JTextArea textArea = new JTextArea(50, 10);
		textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setText("IMPORTANT: due to Telegram restrictions a search of the public chat of interest should be done  before being able to join a chat\r\n"
        		+ "\r\nThe New York Times: -1001606432449\r\n"
        		+ "The New York Times en Español: -1001689616031\r\n"
        		+ "Maldito bulo: -1001113598678\r\n"
        		+ "Project veritas: -1001200497997\r\n"
        		+ "The flat earther: -1001883998430\r\n"
        		+ "El Español: -1001545510754\r\n"
        		+ "Afectados Vacunas: -1001550720134\r\n"
        		+ "Sin mentiras: -1001609501158\r\n"
        		+ "Financial Times: -1001385419036\r\n"
        		+ "El Mundo: -1001385419036");
        
        frameChats.add(new JScrollPane(textArea));
        frameChats.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameChats.setSize(750, 240);
        frameChats.setLocationByPlatform(true);
        frameChats.setVisible(true);
	}
	
	
	/**
	 * Method for getting one input value
	 * @param text 'the text to show on the input dialog'
	 * @return the value of the user reply
	 */
	String inputOneParameter (String text) {
		
		String param1 = JOptionPane.showInputDialog(null,text);
		return param1;
	}
	
	
	/**
	 * Method for getting two input values
	 * @param firstText 'the text to show on the first input dialog'
	 * @param secondText 'the text to show on the second input dialog'
	 * @return The two values of the user reply
	 */
	String[] inputTwoParameter (String firstText, String secondText) {
		
		String[] values = {"",""};
		
		JTextField param1 = new JTextField(35); //First parameter of the dialog panel
	    JTextField param2 = new JTextField(10); //Second parameter of the dialog panel

	    JPanel panel = new JPanel();
	    panel.add(new JLabel(firstText));
	    panel.add(param1);
	    panel.add(new JLabel(secondText));
	    panel.add(param2);

	    int result = JOptionPane.showConfirmDialog(null, panel, "Input", JOptionPane.OK_CANCEL_OPTION);
	    if (result == JOptionPane.OK_OPTION) {
	    	
	    	String[] tokens = param2.getText().split(" "); //just filter by the first word 
	    	values[0] = param1.getText();
	    	values[1] = tokens[0];
	    	
	    	return values;
	    } 
	    else {
	    	
	    	values[0] = "cancelByUser";
	    	return values; //if user cancel or close the dialog box, return
	    }
	}
	

	/**
	 * Method for showing a information message
	 * @param text
	 */
	void showInformation (String text) {
		
		JOptionPane.showMessageDialog(null, text);
	}
	
	
	/**
	 * Method for asking for confirmation 
	 * @param questionText
	 * @param windowsName
	 * @return the answer
	 */
	int askConfirmation(String questionText, String windowsName) {
		
		int input = JOptionPane.showConfirmDialog(null, questionText,windowsName, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
		return input;
	}
	

	/**
	 * Method for showing data information on Jtable
	 */
	void showNews() {
	
    	TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(modelNews);
		sorter.setRowFilter (RowFilter.regexFilter(".*.*")); //no filter
		jTableNews.setRowSorter (sorter);
		filterNews = "";
	}
	
	
	/**
	 * Method for filtering the information show  on Jtable
	 * Two String variables are used for managing the data filter filterNews & filterNewsNew
	 * filterNews is only updated when we are sure the user has set a new filter value
	 */
	void showNewsFilter() {
		
		try {
			
		filterNewsNew = JOptionPane.showInputDialog(null,"Filter");
		
		if (filterNewsNew == null) {
			
			return; //If user cancel or close the dialog box, return
		} else {
			
			filterNews =  filterNewsNew;
		}
		
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(modelNews);
		sorter.setRowFilter (RowFilter.regexFilter(filterNews)); //filter, 
		jTableNews.setRowSorter (sorter);
		} catch (Exception e) {
		
		}
	}
	
	
	/**
	 * Method for creating  a dataSet using the mouse right button
	 * A file name is proposal (the senderId for Type A USER or a normalized name for Type B MESSAGE) 
	 * @return fileName, filePath, type of dataset
	 */
	String[] createDataSetRB() {
		
		String[] values = {".",".","."};
		
		if (rightButtonValue.equals(".")) { //Control for getting the right source of the data
			values[0] = "."; values[1] = "."; values[2] = ".";
			rightButtonValue = ""; //Set value to "" for next action
			JOptionPane.showMessageDialog(null, "The source should be a SENDERID or URL/KEYWORD value!");
			return values;
		}
				
		  	
        if (columnName.equals("CHATID (NAME)") || columnName.equals("MESSAGEID") ) {
        			
        	values[0] = "."; values[1] = "."; values[2] = ".";
	        rightButtonValue = ""; //Set value to "" for next action
	    	JOptionPane.showMessageDialog(null, "The source should be a SENDERID or URL/KEYWORD value!");
	    	return values;        		
        }
        		
        String fileName = "";
        	
	    if (columnName.equals("SENDERID")) {
	        		
	        fileName = rightButtonValue;
	    } else {
        		
	        try {
	        	
	        	MessageTelegram message = MessageTelegramHM.getMessageTelegram(Long.valueOf(rightButtonValue).longValue());
	        	String url = message.getSource(); 
	        	fileName = url;
	        } catch (NullPointerException e) {
	        	values[0] = "."; values[1] = "."; values[2] = ".";
	        	return values;
	        }
	    }
       
	    values[0] = fileName;
	   
	    if (isLong(values[0])) {
    		    	
	    	values[2] = "A";
        	String rootPath = (".\\datafiles\\datasets");		
    		String filePath = fileSaveDialog(rootPath, fileName);
    		values[1] = filePath;
    				
    		if (filePath.equals("")) {
    			values[0] = "."; values[1] = "."; values[2] = ".";
    			return values;
    		}
    				
    		rightButtonValue = ""; //Set value to "" for next action
    		return values;

       } else {
    	   
    	  values[2] = "B";
    	  values[0] = fileName;
    	  fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_"); //In the case of dataset type B (Message) the file name has to be normalized
    		    	
       	  System.out.println("INFO: this process may take some time, please be patient" );
       	  String rootPath = (".\\datafiles\\datasets");		
   		  String filePath = fileSaveDialog(rootPath, fileName);
   		  values[1] = filePath;
    				
   		  if (filePath.equals("")) { //If user cancel or close the dialog box, return
    					
   			  values[0] = "."; values[1] = "."; values[2] = ".";
   			  return values;
   		  } 				
    				
   		  rightButtonValue = ""; //Set value to "" for next action
   		  return values;
       } 
	}

	
	/**
	 * Method for creating  a dataSet
	 * A file name is proposal (the senderId for Type A USER or a normalized name for Type B MESSAGE) 
	 * @return fileName, filePath, type of dataset
	 */
	String[] createDataSet() {
		
		String[] values = {".",".","."};
		
		if (rightButtonValue.equals(".")) { //Control for getting the right source of the data
			values[0] = "."; values[1] = "."; values[2] = ".";
			rightButtonValue = ""; //Set value to "" for next action
			JOptionPane.showMessageDialog(null, "The source should be a SENDERID or URL/KEYWORD value!");
			return values;
		}
				
		JPanel panel = new JPanel();
		JTextField value = new JTextField(20);
	    panel.add(new JLabel("Query"));
	    panel.add(value);
	   
	    JRadioButton typeA = new JRadioButton("USER");
        JRadioButton typeB = new JRadioButton("MESSAGE");
        typeA.setSelected(true);
        
        ButtonGroup group=new ButtonGroup();   
        group.add(typeA);
        group.add(typeB);
        
        panel.add(typeA);
        panel.add(typeB);
        
        int result = JOptionPane.showConfirmDialog(null, panel, "Input", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
	  
        	String fileName = value.getText(); //Value used for proposing a file name
        	values[0] = fileName;
	    	
        	if (typeA.isSelected()) {
		    	
	        	values[2] = "A";
	        	String rootPath = (".\\datafiles\\datasets");		
	        	String filePath = fileSaveDialog(rootPath, fileName);
	        	values[1] = filePath;
	        	if (filePath.equals("")) {
	        		values[0] = "."; values[1] = "."; values[2] = ".";
	        		return values;
	        	}
		    	
	        	return values;
	        } else {
			    	
	        	values[2] = "B";
	        	values[0] = fileName;
	        	fileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_"); //In the case of dataset type B (Message) the file name has to be normalized
	        	System.out.println("INFO: this process may take some time, please be patient" );
	        	String rootPath = (".\\datafiles\\datasets");		
	        	String filePath = fileSaveDialog(rootPath, fileName);
	        	values[1] = filePath;
	        			
	        	if (filePath.equals("")) { //If user cancel or close the dialog box, return
	        				
	        		values[0] = "."; values[1] = "."; values[2] = ".";
	        		return values;
	        	} 				
				
	        	return values;
	        } 
        } else {
        		
       		return values;
       }
	}

	
	/**
	 * Method for cleaning the News Jtable 
	 */
	void deleteAllNews() {
		
		modelNews.setNumRows(0);
	}
	

	/**
	 * Method for showing data information on Jtable
	 */
	void showMessages() {
    	
    	TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(modelMessageTelegram);
		sorter.setRowFilter (RowFilter.regexFilter(".*.*")); //No filter
		jTableMessageTelegram.setRowSorter (sorter);
		filterMessages = "";
	}
	
	
	/**
	 * Method for filtering the information show  on Jtable
	 * Two String variables are used for managing the data filter filterMessages & filterMessagesNew
	 * filterMessages is only updated when we are sure the user has set a new filter value
	 */
	void showMessagesFilter() {
		
		try {
			
		filterMessagesNew = JOptionPane.showInputDialog(null,"Filter");
		
		if (filterMessagesNew == null) {
			
			return; //If user cancel or close the dialog box, return
		} else {
			
			filterMessages =  filterMessagesNew;
		}
		
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(modelMessageTelegram);
		sorter.setRowFilter (RowFilter.regexFilter(filterMessages));
		jTableMessageTelegram.setRowSorter (sorter);
		
		} catch (Exception e) {
			
		}
	}
	
	
	/**
	 * Method for getting the list of chats 
	 * https://core.telegram.org/tdlib/docs/classtd_1_1td__api_1_1load_chats.html -> "The maximum number of chats to be loaded.
	 * For optimal performance, the number of loaded chats is chosen by TDLib and can be smaller than the specified limit,
	 * even if the end of the list is not reached."
	 * 
	 */
	void getChatList() {
		
    	updateModelChatTelegram();
    	
    	TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(modelChatTelegram);
		sorter.setRowFilter (RowFilter.regexFilter(".*.*")); //No filter
		jTableChatTelegram.setRowSorter (sorter);
		filterMessages = "";
	}
	
	
	/**
	 * Method for filtering the information show  on Jtable
	 * Two String variables are used for managing the data filter filterChats & filterChatsNew
	 * filterChats is only updated when we are sure the user has set a new filter value
	 */
	void showChatsFilter() {
				
		filterChatsNew = JOptionPane.showInputDialog(null,"Filter");
		
		if (filterChatsNew == null) {
			
			return; //If user cancel or close the dialog box, return
		} else {
			
			filterChats =  filterChatsNew;
		}
		
		TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<DefaultTableModel>(modelChatTelegram);
		sorter.setRowFilter (RowFilter.regexFilter(filterChats));
		jTableChatTelegram.setRowSorter (sorter);
	}
	
	
	/**
	 * Method for getting the value of rightButton
	 * @return the value selected
	 */
	String getRightButtonValue() {
		
		return rightButtonValue;
	}
	
	
	/**
	 * Method for set right button to ""
	 */
	void setRightButtonValue() {
		
		rightButtonValue = "";	
	}
	
	
	/**
	 * Method for getting the size of the News multiple selection array
	 * @return the size of the selecction
	 */
	int GetNewsMultipleRowSeleccionSize() {
		
		return NewsMultipleRowSeleccion.size();
	}
	
	
	/**
	 * Method for setting the News multiple selection array empty
	 */
	void setNewsMultipleRowSeleccion() {
		
		NewsMultipleRowSeleccion = new ArrayList<String>(); //clear array for next action		
	}
	
	
	/**
	 * Method for getting the News multiple selection array
	 * @return NewsMultipleRowSeleccion
	 */
	ArrayList<String> GetNewsMultipleRowSeleccion() {
		
		return NewsMultipleRowSeleccion;
	}
	
		
	/**
	 * Method for cleaning the MessageTelegram Jtable 
	 */
	void deleteAllMessageTelegram() {
		
		modelMessageTelegram.setNumRows(0);
	}
	
	
	/**
	 * Method for clean the chats  Jtable 
	 */
	void deleteAllChats() {
		
		modelChatTelegram.setNumRows(0);
	}
	
	
	/**
	 * Method for showing data information on Jtable
	 */
	void showDataSets() {
		
		updateModelDataSet("");
	}
	
	
	/**
	 * Method for filtering the information show  on Jtable
	 */
	void showDataSetsFilterA() {
			
		String value = ""; 
		value = JOptionPane.showInputDialog(null,"Sender Id");
		
		if (value == null) {
			
			return; //If user cancel or close the dialog box, return
		}

		updateModelDataSet(value);
	}
	
	
	/**
	 * Method for filtering the information show  on Jtable
	 */
	void showDataSetsFilterB() {
		
		String value = ""; 
		value = JOptionPane.showInputDialog(null,"Message");
		
		if (value == null) {
			
			return; //If user cancel or close the dialog box, return
		}

		updateModelDataSet(value);
	}
	
	
	/**
	 * Method for clean the DataSets Jtable 
	 */
	void deleteAllDataSet() {
		
		modelDataSetA.setNumRows(0);
		modelDataSetB.setNumRows(0);
	}

	
	/**
	 * Method for reading a file of news
	 */
	void loadNewsFile() {
		
		updateModelNews(); 
	}

	/**
	 * Method for reading a file of Messages
	 */
	void loadMessagesFile() {
		
		updateModelMessage();
	}
	
	
	/**
	 * Method for opening a browser  a file of Messages. The value of variable rightButtonValue is used for setting the url
	 */
	/*void openBrowserOld() {
			
		if (NewsMultipleRowSeleccion.size() <=1) {
			
			try {
				
				OpenBrowser.openBrowser(rightButtonValue);
			} catch (IOException e) {
				
				//e.printStackTrace();
			}
			
			rightButtonValue = ""; //Set value to "" for next action
		} else {
			
			for (String query : NewsMultipleRowSeleccion) {
		        
				try {
					
					OpenBrowser.openBrowser(query);
				} catch (IOException e) {
					
					//e.printStackTrace();
				}
			}	
			
			NewsMultipleRowSeleccion = new ArrayList<String>(); //clear array for next action
			rightButtonValue = ""; //Set value to "" for next action			
		}
	}*/
	
	
	ArrayList<String> openBrowser() {
		
		ArrayList<String> newsRowSeleccion = new ArrayList<String>();
		
		if (NewsMultipleRowSeleccion.size() <=1) {
		
			newsRowSeleccion.add(rightButtonValue);
			rightButtonValue = ""; //Set value to "" for next action
			return newsRowSeleccion; 
		} else {
			
			for (String query : NewsMultipleRowSeleccion) {
		        
				newsRowSeleccion.add(query);
			}	
			
			NewsMultipleRowSeleccion = new ArrayList<String>(); //clear array for next action
			rightButtonValue = ""; //Set value to "" for next action
			return newsRowSeleccion; 
		}
	}
	
	
	/**
	 * Method for opening  TelegramWeb
	 */
	String[] openTelegramWeb() {
				
		String[] itemLines = {"",""};
		
		if (!(columnName.equals("MESSAGEID"))) {
    		
    		rightButtonValue = ""; //Set value to "" for next action
			JOptionPane.showMessageDialog(null, "The source should be a MESSAGEID value!");
			return itemLines;        		
    	}
		
		itemLines = rightButtonValue.split(" ");		
		rightButtonValue = ""; //Set value to "" for next action
		return itemLines;
	}
	
	
	/**
	 * Method for updating the information on Jtable. It doesn't modify data on  HashMap
	 */
	void updateModelNews()  {
		
		ArrayList<News> listOfNews = new ArrayList<News>();
		News newsRec = new News();
		modelNews.setNumRows(0);
		
		try {
		
			listOfNews = NewsHM.getNewsList();
		} catch (NullPointerException e) {
			
			return; //If null we return. no error but not answer on input dialog
		}
		
		Iterator<News> arrayIterator = listOfNews.iterator();
		
		while (arrayIterator.hasNext()) { 
			
			newsRec = arrayIterator.next();			 
			String line[] ={newsRec.getTitle(),newsRec.getUrl(), newsRec.getDescription()};
		    modelNews.addRow(line);	    		        
		 }
	}
		
	
	/**
	 * Method for updating the information on Jtable. It doesn't modify data on HashMap
	 */
	void updateModelMessage() {
		
		ArrayList<MessageTelegram> listOfMessages = new ArrayList<MessageTelegram>();
		MessageTelegram newsMessageTelegram = new MessageTelegram();
		ArrayList<ChatTelegram> listOfChats = new ArrayList<ChatTelegram>();
		listOfChats = ChatTelegramHM.getChatTelegramList();
		modelMessageTelegram.setNumRows(0);
			
		try {
				
			listOfMessages = MessageTelegramHM.getMessageTelegramList();
		} catch (NullPointerException e){
		
			return; //If null we return. no error but not answer on input dialog
		}
			
		Iterator<MessageTelegram> arrayMessageIterator = listOfMessages.iterator();
			
		Long chatId = null;
		String chatName = "";
		ChatTelegram chatTelegram = new ChatTelegram();
	
		while (arrayMessageIterator.hasNext()) { 
		 
			newsMessageTelegram = arrayMessageIterator.next();		 
			chatId = newsMessageTelegram.getChatId();
			Iterator<ChatTelegram> arrayChatIterator = listOfChats.iterator();
		 		
			while (arrayChatIterator.hasNext()) {
		 			
				chatTelegram = arrayChatIterator.next();
		 			
				if (chatId.equals(chatTelegram.getChatId())) {
					chatName = chatTelegram.getChatName();
					break;	
				} 
			}
		 		
		String line[] ={newsMessageTelegram.getDate(),newsMessageTelegram.getSenderId().toString(), chatId + " (" + chatName +")", newsMessageTelegram.getMessageId().toString(), newsMessageTelegram.getSource(), newsMessageTelegram.getContent()};
	    modelMessageTelegram.addRow(line);	 
		}
	}
	
	
	/**
	 * Method for update the chat model
	 */
	void updateModelChatTelegram()  {
		
		ArrayList<ChatTelegram> listOfChats = new ArrayList<ChatTelegram>();
		ChatTelegram chatRec = new ChatTelegram();
		deleteAllChats();
		
		listOfChats = ChatTelegramHM.getChatTelegramList();
		
		if (listOfChats.size() == 0) { //Control if list of news is empty
			
        	JOptionPane.showMessageDialog(null, "No chats"); 
    	}
		
		Iterator<ChatTelegram> arrayIterator = listOfChats.iterator();
		
		 while (arrayIterator.hasNext()) { 
			 
			 chatRec = arrayIterator.next();			 
			 String line[] ={chatRec.getStatus().toString(),chatRec.getChatId().toString(), chatRec.getChatName()};
		     modelChatTelegram.addRow(line);	    		        
		 }
	}
	
	
	/**
	 * Method for return the path + file name used for saving it
	 *  If the caller method is createDataset a proposal or file name is used 
	 *  if the caller method is saveNewsToAFile or saveMessagesToAFile there is no proposal of file name
	 *  The dummy word "noProposal" is used for selection
	 * @param rootPath
	 * @param name
	 * @return the filePath
	 */
	String fileSaveDialog(String rootPath, String name) {
		
		String filePath = "";//By default
		JFileChooser fileChooser = new JFileChooser(rootPath);
		
		if (!(name.equals("noProposal"))) { //Control for setting a proposal for the name of the file
						
			fileChooser.setSelectedFile(new File(name)); 
		}
		
		int option = fileChooser.showSaveDialog(null);	
			
		if (option == JFileChooser.APPROVE_OPTION) {
			
			File selectedFile = fileChooser.getSelectedFile();
							
			if (!(selectedFile.toString().endsWith(".json"))) { //Check right file extension
				
				selectedFile = new File(selectedFile.toString() + ".json");	//Add file extension (if need)
			}
			
			if(selectedFile.exists()) { //If the file already exists
				
				int input = JOptionPane.showConfirmDialog(null, "A file with this name already exist, Do you want to overwrite it?","Warning", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
			    
				if (input == 0) { //If Yes, overwrite
					
					filePath = selectedFile.getAbsolutePath();
					return filePath;
				}
			} else {
			
				filePath = selectedFile.getAbsolutePath();
				return filePath;
			}
		}
		
		return filePath;
	}
	
	
	/**
	 * Method for reading information from a file
	 * @param rootPath
	 * @return filePath
	 */
	String fileOpenDialog(String rootPath) {
		
		String filePath = "";//By default
		JFileChooser fileChooser = new JFileChooser(rootPath);		
		int option = fileChooser.showOpenDialog(null);

		if (option == JFileChooser.APPROVE_OPTION) {
			
			File selectedFile = fileChooser.getSelectedFile();
					
			filePath = selectedFile.getAbsolutePath();
			return filePath;
		} 
		else {
		
			return "";
		}		
	}
	
	
	/*--------------------------*/
	/*    PRIVATE METHODS       */
	/*--------------------------*/
	
	/**
	 * Method for checking is a String is a Long number
	 * @param string
	 * @return
	 */
	private boolean isLong(String string) {
	    
		if (string == null) {
	        return false;
	    }
	   
		try {
	       
			@SuppressWarnings("unused")
			Long number  = Long.parseLong(string);
	    } catch (NumberFormatException e) {
	        
	    	return false;
	    }
		
	    return true;
	}
	
	
	/**
	 * Method for updating the information on Jtable. It doesn't modify data on  HashMap
	 * @param query
	 */
	private void updateModelDataSet(String query)  {
	
		ArrayList<DataSetUser> listOfDataSetUser = new ArrayList<DataSetUser>();
		DataSetUser dataSetA = new DataSetUser();
		ArrayList<DataSetMessage> listOfDataSetMessage = new ArrayList<DataSetMessage>();
		DataSetMessage dataSetB = new DataSetMessage();
		deleteAllDataSet();
		
		try {
			
			if (query.equals("")) { //If not query. Show all Dataset
				
				listOfDataSetUser = DataSetUserHM.getDataSetUserList();
				listOfDataSetMessage = DataSetMessageHM.getDataSetMessageList();
			} else { //If query, we choose between type A (user) or type B (message)
				
				if (query.matches("-?\\d+")) { //If query is a number, show dataset type A (user)
					
					listOfDataSetUser = DataSetUserHM.getDataSetUserFilterList(Long.valueOf(query).longValue()); 
				} else { //show dataset type B (message)
					
					listOfDataSetMessage = DataSetMessageHM.getDataSetMessageFilterList(query);
				}
			}
			
		} catch (NullPointerException e) {
			
			return; //if null we return. no error but not answer on input dialog
		}
		
		if (listOfDataSetUser.size() == 0 && listOfDataSetMessage.size() == 0) { //Control if list of dataset is empty
			
        	return;
    	}
		
		Iterator<DataSetUser> arrayUserIterator = listOfDataSetUser.iterator();
		
		try {
			
			while (arrayUserIterator.hasNext()) { 
				 
				dataSetA = arrayUserIterator.next();				
				String lineHeader[] ={"USER", dataSetA.getUserTelegram().getSenderId().toString(), (dataSetA.getUserTelegram().getFirstName() + " " + dataSetA.getUserTelegram().getLastName() + " ("+ dataSetA.getUserTelegram().getUserName() + ") "),
						dataSetA.getUserTelegram().getIsFakeToString() + dataSetA.getUserTelegram().getIsScamToString() + dataSetA.getUserTelegram().getIsVerifiedToString() + dataSetA.getUserTelegram().getIsPremiumToString(), "", "", "" };
				modelDataSetA.addRow(lineHeader);
				
				ArrayList<MessageTelegram> listOfMessages = dataSetA.getListOfMessages();
				 
				for (int i = 0; i < listOfMessages.size(); i++) {
					String lineBody[] ={"MESSAGE", "", "", "", listOfMessages.get(i).getDate(), listOfMessages.get(i).getChatId().toString(), listOfMessages.get(i).getContent() };
				    modelDataSetA.addRow(lineBody);
				}
			}
			
		} catch (NullPointerException e) {
			
			return; //if null we return. no error but not answer on input dialog			
		}	
		 
		 Iterator<DataSetMessage> arrayMessageIterator = listOfDataSetMessage.iterator();
		 
		 try {
			
			 while (arrayMessageIterator.hasNext()) { 
				 
					dataSetB = arrayMessageIterator.next();				
					String lineHeader[] ={"MESSAGE",dataSetB.getMessage(), "", "", "" };
					modelDataSetB.addRow(lineHeader);
					
					ArrayList<UserTelegram> listOfMessages = dataSetB.getListOfUsers();
					 
					for (int i = 0; i < listOfMessages.size(); i++) {
						String lineBody[] ={"USER", "", listOfMessages.get(i).getSenderId().toString(), (listOfMessages.get(i).getFirstName() + " " + listOfMessages.get(i).getLastName() + " ("+ listOfMessages.get(i).getUserName() + ") "),
								listOfMessages.get(i).getIsFakeToString() + listOfMessages.get(i).getIsScamToString() + listOfMessages.get(i).getIsVerifiedToString() + listOfMessages.get(i).getIsPremiumToString()};
					    modelDataSetB.addRow(lineBody);
					}    		        
				}
			 
			} catch (NullPointerException e) {
				
				return; //if null we return. no error but not answer on input dialog				
		}
	}
	
	
	/*--------------------------*/
	/*      INNER  CLASSES      */
	/*--------------------------*/
	
	/**
	 * MouseListener class
	 */
	public class MouseListener extends MouseAdapter { public void mousePressed(MouseEvent e) {
		
		JTable table = null;
		
		try {
			table = (JTable)(e.getSource());
			
		}  catch (Exception ex) { 
			
		}
		
		Point point = e.getPoint();
		int row = table.rowAtPoint(point);
		int column = table.columnAtPoint(point);
		
		if ((row == -1) || (column == -1)) { //Mouse autoscroll may produce  events out the Table range.
		
			return;
		}
																				
		TableModel model = table.getModel();	
		columnName = table.getColumnName(column);
		TableColumnModel columnModel = table.getColumnModel();
		columnModel.setColumnSelectionAllowed(true);
		
		if (SwingUtilities.isRightMouseButton(e)) { //Check if right button is used
			
			rightButtonclick = true;
			 
			 if (NewsMultipleRowSeleccion.size() <= 1 ) {
		        	
				table.setRowSelectionInterval(row, row);
			}

			if (columnName.equals("NAME") || columnName.equals("URL") || columnName.equals("DESCRIPTION") ) {
				
				rightButtonValue = (String) model.getValueAt(table.convertRowIndexToModel(row), 1); //set to 1 on News Table. We need the URL
			} else if (columnName.equals("SENDERID")) {
				
				rightButtonValue = (String) model.getValueAt(table.convertRowIndexToModel(row), 1); //set to 1 on MessagesTelegram Table. We need the senderId
			} else if (columnName.equals("URL / KEYWORD")) {
				
				rightButtonValue = (String) model.getValueAt(table.convertRowIndexToModel(row), 3); //set to 3 on MessagesTelegram Table. We need the messageId
							
			} else if (columnName.equals("MESSAGEID")) {
				
				rightButtonValue = (String) model.getValueAt(table.convertRowIndexToModel(row), 2); //set to 2. We need the chatId				
				String[] itemLines = rightButtonValue.split(" ");				
				rightButtonValue =  itemLines[0] + " " + (String) model.getValueAt(table.convertRowIndexToModel(row), 3); //set to 3. We need the messageId
			
			} else if (columnName.equals("STATUS") || columnName.equals("CHATID") || columnName.equals("CHAT NAME")) {
				
				rightButtonValue = (String) model.getValueAt(table.convertRowIndexToModel(row), 1); //set to 1. We need the chatId
			} else {
				
				rightButtonValue = "."; //This value is used in the case the right button popup menu is done in the wrong column
			}
		}
		
		rightButtonclick = false;
	}};
		
	
	/**
	 * WindowAdapter class
	 * This class is set for asking to do a backup when you close the app
	 * It set a proposal name for the file/s
	 */
	public class WindowAdapter implements WindowListener {
		
		public void windowClosing(WindowEvent e) {
			
			int messagesNumber = MessageTelegramHM.getNumberOfElements();
			int newsNumber = NewsHM.getNumberOfElements();
			
			if (messagesNumber == 0 && newsNumber == 0) {
				
				System.exit(0);
			}
			
			IOManager ioManager = new IOManager();
			Date date = new Date();
			DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy_HHmmss");
			String fileDate = "_" + dateFormat.format(date);
			String rootPath = (".\\datafiles");
			String filePath = "";
			final JLabel filesBackup = new JLabel("Do you want to save News/Messages data (.json)?");
			int value = JOptionPane.showConfirmDialog(frame, filesBackup, "WARNING", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
			
			if ( value == 0 ) { //Do data backup
				
				if (newsNumber >= 1) {
					
					filePath = fileSaveDialog(rootPath, "News" + fileDate); 					
					ioManager.writeNewsJsonFile(filePath); 
				}
					
				if (messagesNumber >= 1) {
					
					filePath = fileSaveDialog(rootPath, "Messages" + fileDate); 					
					ioManager.writeMessageTelegramJsonFile(filePath); 
				}
				
				System.exit(0);
			}
				
			if ( value == 1)  { //No backup
				
				System.exit(0);
			}
		}
		
		public void windowClosed(WindowEvent e) {}
		public void windowDeactivated(WindowEvent e) {}
		public void windowOpened(WindowEvent e) {}		
		public void windowIconified(WindowEvent e) {}		
		public void windowDeiconified(WindowEvent e) {}		
		public void windowActivated(WindowEvent e) {}
		
	};

	
	/**
	 * SharedListSelectionHandler class
	 * Class for allowing multiple selection row on a Jtable
	 */
	class SharedListSelectionHandler implements ListSelectionListener {
		
		public void valueChanged(ListSelectionEvent e) {
	        
			
			if (!(rightButtonclick == true)) { //if mouse right button has been clicked no rows selection is allowed
				NewsMultipleRowSeleccion = new ArrayList<String>();
			
			ListSelectionModel lsm = (ListSelectionModel) e.getSource();	
			int firstIndex = e.getFirstIndex();
			int lastIndex = e.getLastIndex();
	        
			if ((firstIndex == -1) || (lastIndex == -1)) { //Mouse autoscroll may produce  events out the Table range.
				
				return;
			}
		    
		    TableModel model = jTableNews.getModel();	
		    String value = "";
		    
		    int minIndex = lsm.getMinSelectionIndex();
            int maxIndex = lsm.getMaxSelectionIndex();
		    
		    try {
		    	
		    	for (int i=minIndex; i <= maxIndex; i++) {
		    		if (lsm.isSelectedIndex(i)) {
		    			value =  (String) model.getValueAt(jTableNews.convertRowIndexToModel(i), 1); 
		    			NewsMultipleRowSeleccion.add(value);
		    		}
			    }
		    	
		    	} catch (Exception ex) {
		    	
		    	}
			}	
	    }
	}

}
