package be.ugent.piedcler.dodona.code.identifiers.setter.impl;

import be.ugent.piedcler.dodona.code.identifiers.setter.ExerciseIdentifierSetter;
import com.intellij.lang.Language;
import com.intellij.openapi.editor.Document;

import java.util.HashMap;
import java.util.Map;

public class CombinedExerciseIdentifierSetter implements ExerciseIdentifierSetter {


	private final HashMap<Language, ExerciseIdentifierSetter> setterMap;

	public CombinedExerciseIdentifierSetter() {
		setterMap = new HashMap<>();
	}

	public CombinedExerciseIdentifierSetter(Map<Language, ExerciseIdentifierSetter> preprocessorMap) {
		this.setterMap = new HashMap<>(preprocessorMap);
	}

	public CombinedExerciseIdentifierSetter registerEntry(ExerciseIdentifierSetter setter) {
		if (setter != null)
			this.setterMap.put(setter.getLanguage(), setter);
		return this;
	}

	public CombinedExerciseIdentifierSetter unregisterEntry(Language lang) {
		setterMap.remove(lang);
		return this;
	}

	public CombinedExerciseIdentifierSetter unregisterEntry(ExerciseIdentifierSetter setter) {
		if (setter != null)
			setterMap.remove(setter.getLanguage());
		return this;
	}

	@Override
	public void setIdentifier(Language language, Document file, String id) {
		if (setterMap.containsKey(language))
			setterMap.get(language).setIdentifier(language, file, id);
	}

	@Override
	public Language getLanguage() {
		return null;
	}
}
