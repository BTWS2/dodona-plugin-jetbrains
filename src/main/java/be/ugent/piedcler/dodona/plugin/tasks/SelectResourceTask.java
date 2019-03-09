/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.tasks;

import be.ugent.piedcler.dodona.plugin.Api;
import be.ugent.piedcler.dodona.plugin.ui.CourseSelectionDialog;
import be.ugent.piedcler.dodona.plugin.ui.ExerciseSelectionDialog;
import be.ugent.piedcler.dodona.plugin.ui.SelectionDialog;
import be.ugent.piedcler.dodona.plugin.ui.SeriesSelectionDialog;
import be.ugent.piedcler.dodona.resources.Course;
import be.ugent.piedcler.dodona.resources.Exercise;
import be.ugent.piedcler.dodona.resources.Resource;
import be.ugent.piedcler.dodona.resources.Series;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogBuilder;
import com.intellij.openapi.ui.DialogWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;
import java.util.Optional;

/**
 * Requests the user to select things.
 */
abstract class SelectResourceTask<R extends Resource> extends Task.WithResult<Optional<R>, RuntimeException> {
	private Course selectedCourse;
	private Exercise selectedExercise;
	private Series selectedSeries;
	
	/**
	 * SelectResourceTask constructor.
	 *
	 * @param project the project to display notifications in
	 * @param title   the title
	 */
	public SelectResourceTask(@Nonnull final Project project,
	                          @Nonnull final String title) {
		super(project, title, false);
	}
	
	/**
	 * Asks the user to select a course.
	 *
	 * @param indicator  the progress indicator
	 * @param percentage percentage to set after retrieving the courses, before the selection
	 * @return the selected course, if any
	 */
	@Nonnull
	Optional<Course> askCourse(@Nonnull final ProgressIndicator indicator,
	                           final double percentage) throws Exception {
		final List<Course> myCourses = Api.callModal(this.myProject, "Retrieving courses",
			dodona -> dodona.me().getSubscribedCourses()
		);
		
		indicator.setFraction(percentage);
		indicator.setText("Waiting for course selection...");
		
		EventQueue.invokeAndWait(() -> this.selectedCourse = choose("Select Course", new CourseSelectionDialog(myCourses)));
		
		return Optional.ofNullable(this.selectedCourse);
	}
	
	/**
	 * Asks the user to select an exercise.
	 *
	 * @param series     the series to select an exercise in
	 * @param indicator  the progress indicator
	 * @param percentage percentage to set after retrieving the exercises, before the selection
	 * @return the selected exercise, if any
	 */
	@Nonnull
	Optional<Exercise> askExercise(@Nonnull final Series series,
	                             @Nonnull final ProgressIndicator indicator,
	                             final double percentage) throws Exception {
		final List<Exercise> exercises = Api.callModal(this.myProject, "Retrieving exercises",
			dodona -> dodona.exercises().getAll(series)
		);
		
		indicator.setFraction(percentage);
		indicator.setText("Waiting for exercise selection...");
		
		EventQueue.invokeAndWait(() -> this.selectedExercise = choose("Select Exercise", new ExerciseSelectionDialog(exercises)));
		
		return Optional.ofNullable(this.selectedExercise);
	}
	
	/**
	 * Asks the user to select a series within a course.
	 *
	 * @param course     the course to select a series in
	 * @param indicator  the progress indicator
	 * @param percentage percentage to set after retrieving the series, before the selection
	 * @return the selected series, if any
	 */
	@Nonnull
	Optional<Series> askSeries(@Nonnull final Course course,
	                               @Nonnull final ProgressIndicator indicator,
	                               final double percentage) throws Exception {
		final List<Series> courseSeries = Api.callModal(this.myProject, "Retrieving series",
			dodona -> dodona.series().getAll(course)
		);
		
		indicator.setFraction(percentage);
		indicator.setText("Waiting for series selection...");
		
		EventQueue.invokeAndWait(() -> this.selectedSeries = choose("Select Series", new SeriesSelectionDialog(courseSeries)));
		
		return Optional.ofNullable(this.selectedSeries);
	}
	
	/**
	 * Shows a selection dialog.
	 *
	 * @param dialog the dialog to show
	 * @param title  title of the dialog
	 * @param <T>    type class of the options
	 * @return the chosen element, or null if canceled
	 */
	@Nullable
	private <T> T choose(final String title, final SelectionDialog<T> dialog) {
		final DialogBuilder builder = new DialogBuilder(this.myProject);
		builder.setCenterPanel(dialog.getRootPane());
		builder.setTitle(title);
		builder.removeAllActions();
		
		if (dialog.hasItems()) {
			builder.addOkAction();
			builder.addCancelAction();
			builder.setOkActionEnabled(false);
			dialog.addListener(i -> builder.setOkActionEnabled(i != null));
		} else {
			builder.addCloseButton();
		}
		
		if (builder.show() == DialogWrapper.OK_EXIT_CODE) {
			return dialog.getSelectedItem();
		}
		return null;
	}
}
