/*
 * Copyright (c) 2018-2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains/
 */

package io.github.thepieterdc.dodona.plugin.ui.panels;

import io.github.thepieterdc.dodona.plugin.DodonaBundle;
import io.github.thepieterdc.dodona.plugin.ui.Icons;
import io.github.thepieterdc.dodona.plugin.ui.TextColors;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Panel to display when the user has entered incorrect authentication
 * credentials.
 */
public final class UnauthenticatedPanel extends IconTextPanel {
	private static final JComponent ICON = Icons.toComponent(
		Icons.USER_INVALID.color(TextColors.SECONDARY)
	);
	
	/**
	 * UnauthenticatedPanel constructor.
	 */
	private UnauthenticatedPanel() {
		super(ICON, DodonaBundle.message("panel.unauthenticated.message"));
	}
	
	/**
	 * Creates a new instance of the UnauthenticatedPanel.
	 *
	 * @return the instance
	 */
	@Nonnull
	public static UnauthenticatedPanel create() {
		return new UnauthenticatedPanel();
	}
}
