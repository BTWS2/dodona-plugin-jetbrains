/*
 * Copyright (c) 2019. All rights reserved.
 *
 * @author Pieter De Clercq
 * @author Tobiah Lissens
 *
 * https://github.com/thepieterdc/dodona-plugin-jetbrains
 */
package be.ugent.piedcler.dodona.plugin.code.identification.configurers;

import javax.annotation.Nonnull;

/**
 * Identification configurer for HTML files.
 */
public class HtmlIdentificationConfigurer extends AbstractIdentificationConfigurer {
	private static final String EXTENSION = "html";
	
	@Nonnull
	@Override
	public String getFileExtension() {
		return EXTENSION;
	}
	
	@Nonnull
	@Override
	String getIdentificationLine(@Nonnull String identification) {
		return String.format("// %s\n", identification);
	}
}
