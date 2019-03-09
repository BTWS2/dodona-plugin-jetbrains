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
import be.ugent.piedcler.dodona.resources.Series;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.project.Project;

import javax.annotation.Nonnull;
import java.util.Optional;

/**
 * Requests the user to select a course and series.
 */
public class SelectSeriesTask extends SelectResourceTask<Series> {
	/**
	 * SelectSeriesTask constructor.
	 *
	 * @param project the project to display notifications in
	 */
	public SelectSeriesTask(@Nonnull final Project project) {
		super(project, "Configure Series");
	}
	
	@Override
	protected Optional<Series> compute(@Nonnull final ProgressIndicator indicator) throws RuntimeException {
		try {
			final Course course = this.askCourse(indicator, 0.45)
				.orElseThrow(UserAbortedException::new);
			return this.askSeries(course, indicator, 0.90);
		} catch (final UserAbortedException | InterruptedException ex) {
			return Optional.empty();
		} catch (final Exception error) {
			throw new RuntimeException(error);
		}
	}
}
