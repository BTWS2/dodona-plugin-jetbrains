/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.tasks;

import be.ugent.piedcler.dodona.plugin.exceptions.UserAbortedException;
import be.ugent.piedcler.dodona.resources.Course;
import be.ugent.piedcler.dodona.resources.Exercise;
import be.ugent.piedcler.dodona.resources.Series;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Requests the user to select a course, series and exercise.
 */
public class SelectExerciseTask extends SelectResourceTask<Exercise> {
	/**
	 * SelectExerciseTask constructor.
	 *
	 * @param project the project to display notifications in
	 */
	public SelectExerciseTask(@Nonnull final Project project) {
		super(project, "Configure Exercise");
	}
	
	@Override
	protected Optional<Exercise> compute(@NotNull ProgressIndicator indicator) throws RuntimeException {
		try {
			final Course course = this.askCourse(indicator, 0.30)
				.orElseThrow(UserAbortedException::new);
			final Series series = this.askSeries(course, indicator, 0.60)
				.orElseThrow(UserAbortedException::new);
			return this.askExercise(series, indicator, 0.90);
		} catch (final UserAbortedException | InterruptedException ex) {
			return Optional.empty();
		} catch (final Exception error) {
			throw new RuntimeException(error);
		}
	}
}
