/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.browser;

import be.ugent.piedcler.dodona.plugin.Api;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * JavaFX implementation of a webbrowser.
 */
public class JavaFXBrowserView implements BrowserView {
	private WebView browser;
	private WebEngine engine;
	
	private JFXPanel jfxPanel;
	
	private BrowserPanel panel;
	
	@Nonnull
	@Override
	public JComponent getNode() {
		Platform.runLater(() -> {
			final BorderPane borderPane = new BorderPane(this.browser);
			final Scene scene = new Scene(borderPane);
			this.jfxPanel.setScene(scene);
		});
		
		return this.jfxPanel;
	}
	
	@Override
	public void init() {
		this.jfxPanel = new JFXPanel();
		Platform.runLater(() -> {
			this.browser = new WebView();
			
			this.engine = new WebEngine();
			this.engine.setUserAgent(Api.getUserAgent());
		});
	}
	
	@Override
	public void load(@Nonnull final String url) {
		Platform.runLater(() -> this.engine.load(url));
	}
	
	@Override
	public void setPanel(@Nonnull final BrowserPanel panel) {
		this.panel = panel;
	}
}
