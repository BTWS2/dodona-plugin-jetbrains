/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.toolwindow.submissions;

import be.ugent.piedcler.dodona.resources.submissions.PartialSubmission;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.intellij.ui.table.JBTable;

import javax.annotation.Nonnull;
import javax.swing.*;
import javax.swing.table.TableModel;
import java.util.List;

/**
 * Panel for the tab showing exercise submissions.
 */
class SubmissionsTabPanel extends SimpleToolWindowPanel {
	
	/**
	 * DodonaToolWindowView constructor.
	 */
	public SubmissionsTabPanel() {
		super(false, true);
	}
	
	/**
	 * Sets a list of submissions.
	 *
	 * @param submissions the submissions
	 */
	void setSubmissions(@Nonnull final List<PartialSubmission> submissions) {
		final TableModel submissionsTableModel = new SubmissionsTableModel(submissions);
		
		final JBTable submissionsTable = new JBTable(submissionsTableModel);
		
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(submissionsTable);
		
		this.setContent(mainPanel);
	}
	
	/**
	 * No active exercise.
	 */
	void setNoExercise() {
		final JLabel noExerciseLabel = new JLabel("No active exercise is loaded.");
		
		final JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(noExerciseLabel);
		
		this.setContent(mainPanel);
	}
}
