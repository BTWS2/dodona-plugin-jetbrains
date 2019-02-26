/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.browser;

import javafx.application.Platform;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Main browser panel.
 */
public class BrowserPanel extends JPanel {
	private BrowserView view;
	
	/**
	 * BrowserPanel constructor.
	 *
	 * @param view browser view
	 */
	public BrowserPanel(final BrowserView view) {
		this.view = view;
		this.initWebView();
	}
	
	/**
	 * Initialises the webbrowser.
	 */
	private void initWebView() {
		Platform.setImplicitExit(false);
		SwingUtilities.invokeLater(() -> {
			this.removeAll();
			
			this.view.init();
			this.view.setPanel(this);
			
			this.add(this.view.getNode());
			
			this.validate();
			this.repaint();
		});
	}
	
	/**
	 * Loads the given url.
	 *
	 * @param url the url to load
	 */
	void load(@Nonnull final String url) {
		this.view.load(url);
	}
}
