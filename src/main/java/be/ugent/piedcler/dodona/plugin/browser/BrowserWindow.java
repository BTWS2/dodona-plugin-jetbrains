/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.browser;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Factory for Web browsers.
 */
public class BrowserWindow implements ToolWindowFactory {
	private static final String BROWSER_TOOL_WINDOW_ID = "Dodona";
	
	@Override
	public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
		final ContentFactory cf = ContentFactory.SERVICE.getInstance();
		final Content content = cf.createContent(new BrowserPanel(new JavaFXBrowserView()), "", false);
		toolWindow.getContentManager().addContent(content);
	}
	
	/**
	 * Gets the instance of the browser tool window and loads the given url.
	 *
	 * @param project the current project
	 * @param url     the url to load
	 */
	public static void showAndLoad(@Nonnull final Project project, @Nonnull final String url) {
		SwingUtilities.invokeLater(() -> {
			final ToolWindow tw = ToolWindowManager.getInstance(project).getToolWindow(BROWSER_TOOL_WINDOW_ID);
			
			final BrowserPanel browser = (BrowserPanel) tw.getContentManager().getContent(0);
			if (browser != null) {
				browser.load(url);
				
				tw.show(() -> {
				});
			}
		});
	}
}
