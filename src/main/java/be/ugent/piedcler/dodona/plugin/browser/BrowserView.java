/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.browser;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Interface for webbrowser implementations.
 */
public interface BrowserView {
	/**
	 * Gets the panel.
	 *
	 * @return the panel
	 */
	@Nonnull
	JComponent getNode();
	
	/**
	 * Initialisation logic.
	 */
	default void init() {
	}
	
	/**
	 * Load the given url.
	 *
	 * @param url the url
	 */
	void load(String url);
	
	/**
	 * Sets the browserpanel.
	 *
	 * @param panel the panel
	 */
	void setPanel(@Nonnull final BrowserPanel panel);
}
