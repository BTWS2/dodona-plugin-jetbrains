/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.previews;

import com.intellij.codeHighlighting.BackgroundEditorHighlighter;
import com.intellij.ide.structureView.StructureViewBuilder;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.FileEditorStateLevel;
import com.intellij.openapi.util.UserDataHolderBase;
import com.intellij.openapi.vfs.VirtualFile;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.beans.PropertyChangeListener;

public class ExerciseDescriptionEditor extends UserDataHolderBase implements FileEditor {
	private WebView browser;
	
	private final JFXPanel panel;
	
	public ExerciseDescriptionEditor(@Nonnull final VirtualFile file) {
		this.panel = new JFXPanel();
		
		Platform.setImplicitExit(false);
		Platform.runLater(() -> initWebViewInComponent(this.panel));
	}
	
	private void initWebViewInComponent(final JFXPanel panel) {
		final Group group = new Group();
		final Scene scene = new Scene(group);
		panel.setScene(scene);
		
		this.browser = new WebView();
		
		group.getChildren().add(this.browser);
		this.browser.setContextMenuEnabled(false);
		
		this.browser.getEngine().loadContent("<html><b>Woop!</b></html>é");
	}
	
	@NotNull
	@Override
	public JComponent getComponent() {
		return this.panel;
	}
	
	@Nullable
	@Override
	public JComponent getPreferredFocusedComponent() {
		return this.panel;
	}
	
	@NotNull
	@Override
	public String getName() {
		return "Dodona Preview";
	}
	
	@NotNull
	@Override
	public FileEditorState getState(@NotNull FileEditorStateLevel level) {
		return FileEditorState.INSTANCE;
	}
	
	@Override
	public void setState(@NotNull FileEditorState state) {
	
	}
	
	@Override
	public boolean isModified() {
		return false;
	}
	
	@Override
	public boolean isValid() {
		return true;
	}
	
	@Override
	public void selectNotify() {
	
	}
	
	@Override
	public void deselectNotify() {
	
	}
	
	@Override
	public void addPropertyChangeListener(@NotNull PropertyChangeListener listener) {
	
	}
	
	@Override
	public void removePropertyChangeListener(@NotNull PropertyChangeListener listener) {
	
	}
	
	@Nullable
	@Override
	public BackgroundEditorHighlighter getBackgroundHighlighter() {
		return null;
	}
	
	@Nullable
	@Override
	public FileEditorLocation getCurrentLocation() {
		return null;
	}
	
	@Nullable
	@Override
	public StructureViewBuilder getStructureViewBuilder() {
		return null;
	}
	
	@Override
	public void dispose() {
	
	}
}
