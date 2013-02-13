package com.ijg.darklight.build.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.ijg.darklight.build.InstallerFrame;
import com.ijg.darklight.build.Utils;

public class ChooseJarPanel extends JPanel {
	private static final long serialVersionUID = -6761028575354309461L;

	private JLabel description;
	private JTextField selectedFileText;
	private JButton browse, next, back;
	private JFileChooser fileChooser;
	
	public ChooseJarPanel(final InstallerFrame parent) {
		super();
		
		description = new JLabel("Click \"Browse\" to select the Darklight Nova Core jar file.");
		
		next = Utils.genericButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				parent.setJarToCopy(selectedFileText.getText());
				parent.changePanel(EPanels.OPTION_CONFIG);
			}
		});
		
		back = Utils.genericButton("Back");
		Utils.addPanelSwitchActionListener(back, EPanels.OPTION_BUILD, parent);
		
		browse = Utils.genericButton("Browse");
		browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int status = fileChooser.showOpenDialog(null);
				if (status == JFileChooser.APPROVE_OPTION)
					selectedFileText.setText(fileChooser.getSelectedFile().getAbsolutePath());
			}
		});
		
		selectedFileText = new JTextField(25);
		
		fileChooser = new JFileChooser(new File("."));
		fileChooser.setSelectedFile(new File(".", "Darklight.jar"));
		fileChooser.setFileFilter(Utils.generateFileFilter("jar"));
		fileChooser.setMultiSelectionEnabled(false);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(5, 5, 5, 5);
		add(description, c);
		
		c.gridy = 1;
		add(browse, c);
		
		c.gridy = 2;
		c.insets = new Insets(0, 5, 5, 5);
		add(selectedFileText, c);
		
		c.gridy = 3;
		c.insets = new Insets(0, 5, 5, 0);
		c.anchor = GridBagConstraints.WEST;
		add(back, c);
		
		c.gridx = 1;
		c.insets = new Insets(0, 0, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		add(next, c);
	}
}
