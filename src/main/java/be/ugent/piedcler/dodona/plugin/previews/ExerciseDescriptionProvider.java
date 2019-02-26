/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.previews;

import com.intellij.ide.highlighter.JavaFileType;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorPolicy;
import com.intellij.openapi.fileEditor.FileEditorState;
import com.intellij.openapi.fileEditor.WeighedFileEditorProvider;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.vfs.VirtualFile;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

public class ExerciseDescriptionProvider extends WeighedFileEditorProvider {
	@Override
	public boolean accept(@NotNull Project project, @NotNull VirtualFile file) {
		return file.getFileType() == JavaFileType.INSTANCE;
	}
	
	@NotNull
	@Override
	public FileEditor createEditor(@NotNull Project project, @NotNull VirtualFile file) {
		return new ExerciseDescriptionEditor(file);
	}
	
	@Override
	public void disposeEditor(@NotNull FileEditor editor) {
		Disposer.dispose(editor);
	}
	
	@NotNull
	@Override
	public String getEditorTypeId() {
		return "dodona-exercise-java";
	}
	
	@NotNull
	@Override
	public FileEditorPolicy getPolicy() {
		return FileEditorPolicy.PLACE_AFTER_DEFAULT_EDITOR;
	}
	
	@NotNull
	@Override
	public FileEditorState readState(@NotNull Element sourceElement, @NotNull Project project, @NotNull VirtualFile file) {
		return FileEditorState.INSTANCE;
	}
	
	@Override
	public void writeState(@NotNull FileEditorState state, @NotNull Project project, @NotNull Element targetElement) {
	
	}
}
