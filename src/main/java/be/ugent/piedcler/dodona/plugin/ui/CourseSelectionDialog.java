/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.ui;

import be.ugent.piedcler.dodona.resources.Course;
import com.intellij.ui.components.JBList;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Collection;

/**
 * A dialog that allows the user to select a course.
 */
public class CourseSelectionDialog extends SelectionDialog<Course> {
	private JPanel contentPane;
	
	private final boolean hasItems;
	
	@Nullable
	private Course selectedCourse;
	
	/**
	 * SelectCourseDialog constructor.
	 *
	 * @param courses the courses to select from
	 */
	public CourseSelectionDialog(final Collection<Course> courses) {
		this.hasItems = courses.isEmpty();
		
		this.setContentPane(this.contentPane);
		this.setModal(true);
		
		if(!courses.isEmpty()) {
			this.createEmptyDialog();
		}
		
//		this.yearsTabs.addTab("2018-2019", new JPanel());
//		this.yearsTabs.addTab("2017-2018", new JPanel());
//
//		this.coursesList.addListSelectionListener(e -> this.selectedCourse = this.coursesList.getSelectedValue());
//		this.coursesList.setCellRenderer(new CourseListRenderer());
//		this.coursesList.setEmptyText("No courses were found for your account.");
//		this.coursesList.setModel(new CollectionListModel<>(courses));
//		this.coursesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	/**
	 * Creates an empty dialog that contains no courses.
	 */
	private void createEmptyDialog() {
		final JBList<Course> emptyList = new JBList<>();
		emptyList.setEmptyText("No courses were found for your account.");
		this.contentPane.add(emptyList);
	}
	
	@Nullable
	@Override
	public Course getSelectedItem() {
		return this.selectedCourse;
	}
	
	@Override
	public boolean hasItems() {
		return this.hasItems;
	}
}
