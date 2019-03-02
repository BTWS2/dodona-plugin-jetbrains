/*
 * Copyright (c) 2018. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/ugent-dodona/
 */
package be.ugent.piedcler.dodona.plugin.ui;

import be.ugent.piedcler.dodona.plugin.ui.listeners.SelectedItemListener;
import be.ugent.piedcler.dodona.resources.Exercise;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.components.JBList;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.util.Collection;

/**
 * A dialog that allows the user to select an exercise.
 */
public class ExerciseSelectionDialog extends SelectionDialog<Exercise> {
	private JPanel contentPane;
	
	private JBList<Exercise> exercisesList;
	private final CollectionListModel<Exercise> exercisesModel;
	
	private JCheckBox hideCompletedCheck;
	
	@Nullable
	private Exercise selectedExercise;
	
	/**
	 * SelectExerciseDialog constructor.
	 *
	 * @param exercises the exercises to select from
	 */
	public ExerciseSelectionDialog(final Collection<Exercise> exercises) {
		this.exercisesModel = new CollectionListModel<>(exercises);
		
		this.createComponents();
		
		this.exercisesList.addListSelectionListener(e -> this.selectedExercise = this.exercisesList.getSelectedValue());
		this.exercisesList.setCellRenderer(new ExerciseListRenderer());
		this.exercisesList.setEmptyText("No exercises were found in this series.");
		this.exercisesList.setModel(this.exercisesModel);
		this.exercisesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	}
	
	@Override
	public void addListener(@Nonnull final SelectedItemListener<Exercise> listener) {
		//TODO implement me
	}
	
	/**
	 * Creates the form components.
	 */
	private void createComponents() {
		this.setContentPane(this.contentPane);
		this.setModal(true);
	}
	
	@Nullable
	@Override
	public Exercise getSelectedItem() {
		return this.selectedExercise;
	}
	
	@Override
	public boolean hasItems() {
		return !this.exercisesList.isEmpty();
	}
}