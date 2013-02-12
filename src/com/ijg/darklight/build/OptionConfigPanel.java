package com.ijg.darklight.build;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class OptionConfigPanel extends JPanel {
	private static final long serialVersionUID = -4343591283579567030L;

	private JButton next, back;
	private JRadioButton useConfig, useDefault;
	private ButtonGroup group;
	
	public OptionConfigPanel(final InstallerFrame parent) {
		super();
		
		next = Utils.genericButton("Next");
		next.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (useDefault.isSelected()) {
					parent.setConfigFileToCopy(null);
					parent.changePanel(EPanels.CHOOSE_SHORTCUT);
				} else if (useConfig.isSelected()) {
					parent.changePanel(EPanels.CHOOSE_CONFIG);
				}
			}
		});
		
		back = Utils.genericButton("Back");
		Utils.addPanelSwitchActionListener(back, EPanels.CHOOSE_JAR, parent);
		
		useConfig = new JRadioButton("Use existing config.json file");
		useDefault = new JRadioButton("Generate default config.json file");
		
		group = new ButtonGroup();
		group.add(useConfig);
		group.add(useDefault);
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		c.insets = new Insets(5, 5, 5, 5);
		c.anchor = GridBagConstraints.WEST;
		add(useConfig, c);
		
		c.gridy = 1;
		add(useDefault, c);
		
		c.gridy = 2;
		c.insets = new Insets(0, 5, 5, 0);
		add(back, c);
		
		c.gridx = 1;
		c.insets = new Insets(0, 0, 5, 5);
		c.anchor = GridBagConstraints.EAST;
		add(next, c);
	}
}
