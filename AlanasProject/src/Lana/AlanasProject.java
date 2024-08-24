package Lana;

import java.util.*;
import java.util.List;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
public class AlanasProject extends JFrame {
	public JLabel someText;
	public JTextField txtField = new JTextField("");
	public DefaultListModel<String> listModel = new DefaultListModel<String>();
	public JList<String> list = new JList<String>(listModel);
	//****GUI SHIT
	public AlanasProject() {
		super();
		Init();
		loadRestaurants();
	}
	private void loadRestaurants() {
	    List<String> restaurants = AlanasProject.ReadFile("Restaurants.txt");
	    if (restaurants != null) {
	        for (String restaurant : restaurants) {
	            listModel.addElement(restaurant);
	        }
	    } else {
	        System.out.println("Failed to read the file.");
	    }
	}
	private void Init() {
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		//txtField = JTextField("");
		 
		 
		 JFrame frame = new JFrame("HomeWork FIVE");
		 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		 
		 
		// Create the main panel with a grid layout
	    JPanel mainPanel = new JPanel(new GridLayout(2, 1));

	    // Create the panel for the first row with a grid layout
	    JPanel row1Panel = new JPanel(new GridLayout(1, 3));
		    
		    
		 //frame.setLayout(new GridLayout(2, 3));
		 someText = new JLabel("Press a button to change this text");
		 
		 //frame.addMouseListener(new MouseClickListener());
		 

		 
		 //to call later and do things to it
		 JLabel HWlabel = new JLabel("Lanas Menu:");
		 JButton AddNameBtn = new JButton("Add Restuarant");
		 JButton RemoveNameBtn = new JButton("Remove Restuarant");
		 JButton FindRestuarantBtn = new JButton("Find Restuarant");
		 
		 
	 
		AddNameBtn.addActionListener(new AddButtonListener(this, listModel));
		RemoveNameBtn.addActionListener(new RemoveListener(this, listModel, list));
		FindRestuarantBtn.addActionListener(new FindRestuarantListener(this, listModel));
		
		row1Panel.add(HWlabel);
		row1Panel.add(txtField);
		row1Panel.add(list);
		mainPanel.add(row1Panel);
		JPanel row2Panel = new JPanel();
		row2Panel.add(AddNameBtn);
		row2Panel.add(RemoveNameBtn);
		row2Panel.add(FindRestuarantBtn);
		
		mainPanel.add(row2Panel);
		
		frame.add(mainPanel);
		 //frame.add(someText);
		 //frame.setSize(200, 200);
		 frame.pack();
		 frame.setVisible(true);



	}
	private static void constructGUI() {
		AlanasProject newWindow = new AlanasProject();
	}
	//****GUI SHIT
	public static void main(String[] args) {
			SwingUtilities.invokeLater(new Runnable() {
    			public void run() {
    				constructGUI();
    			}	
    		});
			
	}
	
	//******FILE SHIT
	public static List<String> ReadFile(String filename) 
	{
		
		List<String> readLines = new ArrayList<>();
		try {
            readLines = Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            System.err.format("Error reading file: %s%n", e);
        }
        return readLines;
    }
	public static void WriteFile(String word, String filename) {
	    try {
	        Files.write(Paths.get(filename), Collections.singletonList(word), Charset.defaultCharset(), StandardOpenOption.APPEND);
	    } catch (IOException x) {
	        System.err.format("Error writing to file: %s%n", x);
	    }
	}
	//******FILE SHIT

}






//Button Listeners
class RemoveListener implements ActionListener {
	AlanasProject fr;
	DefaultListModel<String> listModel;
	JList<String> list;
	public RemoveListener(AlanasProject frame, DefaultListModel<String> listModel, JList<String> list)
	{
		fr = frame;
		this.listModel = listModel;
		this.list = list;
	}
	public void actionPerformed(ActionEvent eventData) {
        int selectedIndex = fr.list.getSelectedIndex();
        if (selectedIndex != -1) { // Ensure an item is selected
            String removedItem = listModel.getElementAt(selectedIndex);
            JOptionPane.showMessageDialog(null, "You have removed " + removedItem + " from the list");

            // Remove the item from the list model
            listModel.remove(selectedIndex);

            // Remove the item from the file
            removeItemFromFile(removedItem, "Restaurants.txt");
        } else {
            JOptionPane.showMessageDialog(null, "Please select an item to remove");
        }
    }
	private void removeItemFromFile(String itemToRemove, String filename) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filename));
            lines.remove(itemToRemove);
            Files.write(Paths.get(filename), lines);
        } catch (IOException e) {
            System.err.format("Error removing item from file: %s%n", e);
        }
    }
}
class AddButtonListener implements ActionListener {
	AlanasProject fr;
	DefaultListModel<String> listModel;
	public AddButtonListener(AlanasProject frame, DefaultListModel<String> listModel)
	{
		fr = frame;
		this.listModel = listModel;
	}
	public void actionPerformed(ActionEvent eventData) {
		String textToAdd = fr.txtField.getText().trim(); // Trim any leading or trailing spaces

	    if (fr.txtField.getText().length() != 0) { // Check if the text is not empty
	        listModel.addElement(textToAdd);
	        AlanasProject.WriteFile(textToAdd, "Restaurants.txt");
	        JOptionPane.showMessageDialog(null, "You have added " + textToAdd + " to the list");
	    } else {
	        JOptionPane.showMessageDialog(null, "Please enter a non-empty value to add to the list");
	    }
		
	}
}
class FindRestuarantListener implements ActionListener {
	AlanasProject fr;
	DefaultListModel<String> listModel;
	public FindRestuarantListener(AlanasProject frame, DefaultListModel<String> listModel)
	{
		fr = frame;
		this.listModel = listModel;
	}
	public void actionPerformed(ActionEvent eventData) {
		int randomIndex = new Random().nextInt(listModel.size());
		String randomElement = listModel.getElementAt(randomIndex);
	    JOptionPane.showMessageDialog(null, randomElement);
		
	}
}

