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
	private final VirtualFile file;
	
	private final JFXPanel panel;
	
	public ExerciseDescriptionEditor(@Nonnull final VirtualFile file) {
		this.file = file;
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
		
		this.browser.getEngine().loadContent("\n" +
			"<!DOCTYPE html>\n" +
			"<html prefix=\"og: http://ogp.me/ns#\" lang=\"nl\">\n" +
			"<head>\n" +
			" <base href=\"https://dodona.ugent.be\">\n" + 
			"  <meta charset=\"utf-8\">\n" +
			"  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
			"  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
			"  <meta name=\"description\" content=\"Online leren programmeren voor secundair en hoger onderwijs.\">\n" +
			"  <meta name=\"author\" content=\"Universiteit Gent\">\n" +
			"  <meta name=\"application-name\" content=\"Dodona\">\n" +
			"  <meta name=\"theme-color\" content=\"#2196F3\">\n" +
			"  <meta name=\"twitter:card\" content=\"summary\">\n" +
			"  <meta name=\"twitter:site\" content=\"@DodonaEdu\">\n" +
			"  <meta property=\"og:url\" content=\"https://dodona.ugent.be/nl/courses/77/series/856/exercises/1820205566/\">\n" +
			"  <meta property=\"og:title\" content=\"De priemiejagers\">\n" +
			"          <meta property=\"og:image\" content=\"https://dodona.ugent.be/exercises/1820205566/media/bruno_wyndaele.png\">\n" +
			"\n" +
			"  <meta property=\"og:image\" content=\"https://dodona.ugent.be/icon.png\">\n" +
			"  <meta name=\"msapplication-TileColor\" content=\"#2196F3\"/>\n" +
			"  <meta name=\"msapplication-TileImage\" content=\"/icon.png\"/>\n" +
			"  <link rel=\"apple-touch-icon-precomposed\" href=\"/icon.png\"/>\n" +
			"  <link rel=\"shortcut icon\" sizes=\"192x192\" href=\"/icon.png\">\n" +
			"  <link rel=\"shortcut icon\" href=\"/favicon.ico\"/>\n" +
			"  <link rel=\"manifest\" href=\"/manifest.json\">\n" +
			"  <title>Dodona - De priemiejagers</title>\n" +
			"  <link href='https://fonts.googleapis.com/css?family=Roboto:400,400italic,300,500,700' rel='stylesheet' type='text/css'>\n" +
			"  <link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\"\n" +
			"        rel=\"stylesheet\">\n" +
			"  <link href='https://cdn.materialdesignicons.com/3.6.95/css/materialdesignicons.min.css' rel='stylesheet' type='text/css'>\n" +
			"  <link rel=\"stylesheet\" media=\"all\" href=\"/assets/application-e53cb59c5b5244a47e9670fc24fe6b3e656db79c979b764d6f73433a8e6c7792.css\" />\n" +
			"  <script src=\"/packs/application-81ab35a23a045618e92b.js\"></script>\n" +
			"  <script src=\"/assets/application-613bb2fed15c4d70ea236bb4f1d04d034c55999e9e4f327d54bcdbfe7b8e8bba.js\"></script>\n" +
			"    <script src='https://cdnjs.cloudflare.com/ajax/libs/mathjax/2.7.5/MathJax.js?config=TeX-AMS-MML_HTMLorMML'></script>\n" +
			"  <script src=\"/packs/exercise-baafa25ff2ab86ea2dc7.js\"></script>\n" +
			"  <script src=\"/packs/submission-51d87a67df862a1a9dc6.js\"></script>\n" +
			"  \n" +
			"\n" +
			"  <script>\n" +
			"      (function (i, s, o, g, r, a, m) {\n" +
			"          i[\"GoogleAnalyticsObject\"] = r;\n" +
			"          i[r] = i[r] || function () {\n" +
			"              (i[r].q = i[r].q || []).push(arguments);\n" +
			"          }, i[r].l = 1 * new Date();\n" +
			"          a = s.createElement(o),\n" +
			"              m = s.getElementsByTagName(o)[0];\n" +
			"          a.async = 1;\n" +
			"          a.src = g;\n" +
			"          m.parentNode.insertBefore(a, m);\n" +
			"      })(window, document, \"script\", \"https://www.google-analytics.com/analytics.js\", \"ga\");\n" +
			"\n" +
			"      ga(\"create\", \"UA-76309350-1\", \"auto\");\n" +
			"      ga(\"send\", \"pageview\");\n" +
			"  </script>\n" +
			"  <meta name=\"csrf-param\" content=\"authenticity_token\" />\n" +
			"<meta name=\"csrf-token\" content=\"2+3pTg/qMgZcdg856HFaIeKkd7kBRBSh5udVd4jgmnOsfKb8tFunsFvrdXs8ncd5d9wTUrRlPT5W4PqTcrJ/MQ==\" />\n" +
			"  \n" +
			"</head>\n" +
			"<body>\n" +
			"<div id=\"page-wrapper\">\n" +
			"  \n" +
			"  <nav class=\"dodona-navbar hidden-print\">\n" +
			"  <div class=\"left flex\">\n" +
			"    <div class=\"content\">\n" +
			"      <a class=\"brand\" href=\"/?locale=nl\">\n" +
			"        <span class=\"hidden-xs\">Dodona</span>\n" +
			"        <i class=\"visible-xs material-icons\">home</i>\n" +
			"</a>    </div>\n" +
			"  </div>\n" +
			"  <div class=\"center flex\">\n" +
			"    <div class=\"content\">\n" +
			"      \n" +
			"<div class=\"crumbs\">\n" +
			"    <div class=\"crumb\"><a href=\"/nl/exercises/\">Oefeningen</a></div>\n" +
			"</div>\n" +
			"\n" +
			"      <ul class=\"actions\">\n" +
			"        \n" +
			"\n" +
			"  <li class=\"action\">\n" +
			"  <a class=\" active\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Oefening bekijken\" href=\"/nl/courses/77/series/856/exercises/1820205566/\">\n" +
			"      <i class='material-icons md-24 material-icons-faaf'>assignment</i>\n" +
			"    <span class=\"dropdown-title\">Oefening bekijken</span>\n" +
			"</a></li>\n" +
			"\n" +
			"  <li class=\"action\">\n" +
			"  <a data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Oplossingen voor deze oefening\" href=\"/nl/courses/77/series/856/exercises/1820205566/submissions/\">\n" +
			"      <i class=\"custom-material-icons submissions \"><svg viewBox=\"0 0 24 24\">\n" +
			"  <path fill=\"currentColor\" d=\"M 7.6503906 1.53125 C 6.7428939 1.53125 6.0078125 2.274144 6.0078125 3.1816406 L 6 16.382812 C 6 17.290309 6.7350814 18.03125 7.6425781 18.03125 L 17.550781 18.03125 C 18.458277 18.03125 19.199219 17.290309 19.199219 16.382812 L 19.199219 6.4824219 L 14.25 1.53125 L 7.6503906 1.53125 z M 13.425781 2.7695312 L 17.962891 7.3066406 L 13.425781 7.3066406 L 13.425781 2.7695312 z M 2 6 L 2 20 C 2 21.1 2.9 22 4 22 L 18 22 L 18 20 L 4 20 L 4 6 L 2 6 z M 9.3007812 9.78125 L 15.900391 9.78125 L 15.900391 11.431641 L 9.3007812 11.431641 L 9.3007812 9.78125 z M 9.3007812 13.082031 L 15.900391 13.082031 L 15.900391 14.732422 L 9.3007812 14.732422 L 9.3007812 13.082031 z \"/>\n" +
			"</svg>\n" +
			"</i>\n" +
			"    <span class=\"dropdown-title\">Oplossingen voor deze oefening</span>\n" +
			"</a></li>\n" +
			"\n" +
			"  <li class=\"action\">\n" +
			"  <a data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Oefening bewerken\" href=\"/nl/courses/77/series/856/exercises/1820205566/edit/\">\n" +
			"      <i class='material-icons md-24 material-icons-faaf'>edit</i>\n" +
			"    <span class=\"dropdown-title\">Oefening bewerken</span>\n" +
			"</a></li>\n" +
			"\n" +
			"\n" +
			"      </ul>\n" +
			"    </div>\n" +
			"  </div>\n" +
			"  <div class=\"right flex\">\n" +
			"    <div class=\"content\">\n" +
			"      <ul id=\"navbar\" class=\"dropdown-nav collapse\">\n" +
			"        \n" +
			"\n" +
			"  <li class=\"action\">\n" +
			"  <a class=\" active\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Oefening bekijken\" href=\"/nl/courses/77/series/856/exercises/1820205566/\">\n" +
			"      <i class='material-icons md-24 material-icons-faaf'>assignment</i>\n" +
			"    <span class=\"dropdown-title\">Oefening bekijken</span>\n" +
			"</a></li>\n" +
			"\n" +
			"  <li class=\"action\">\n" +
			"  <a data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Oplossingen voor deze oefening\" href=\"/nl/courses/77/series/856/exercises/1820205566/submissions/\">\n" +
			"      <i class=\"custom-material-icons submissions \"><svg viewBox=\"0 0 24 24\">\n" +
			"  <path fill=\"currentColor\" d=\"M 7.6503906 1.53125 C 6.7428939 1.53125 6.0078125 2.274144 6.0078125 3.1816406 L 6 16.382812 C 6 17.290309 6.7350814 18.03125 7.6425781 18.03125 L 17.550781 18.03125 C 18.458277 18.03125 19.199219 17.290309 19.199219 16.382812 L 19.199219 6.4824219 L 14.25 1.53125 L 7.6503906 1.53125 z M 13.425781 2.7695312 L 17.962891 7.3066406 L 13.425781 7.3066406 L 13.425781 2.7695312 z M 2 6 L 2 20 C 2 21.1 2.9 22 4 22 L 18 22 L 18 20 L 4 20 L 4 6 L 2 6 z M 9.3007812 9.78125 L 15.900391 9.78125 L 15.900391 11.431641 L 9.3007812 11.431641 L 9.3007812 9.78125 z M 9.3007812 13.082031 L 15.900391 13.082031 L 15.900391 14.732422 L 9.3007812 14.732422 L 9.3007812 13.082031 z \"/>\n" +
			"</svg>\n" +
			"</i>\n" +
			"    <span class=\"dropdown-title\">Oplossingen voor deze oefening</span>\n" +
			"</a></li>\n" +
			"\n" +
			"  <li class=\"action\">\n" +
			"  <a data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Oefening bewerken\" href=\"/nl/courses/77/series/856/exercises/1820205566/edit/\">\n" +
			"      <i class='material-icons md-24 material-icons-faaf'>edit</i>\n" +
			"    <span class=\"dropdown-title\">Oefening bewerken</span>\n" +
			"</a></li>\n" +
			"\n" +
			"\n" +
			"          <li class=\"sign-in\"><a href=\"/nl/sign_in/\">Aanmelden</a></li>\n" +
			"        <li class=\"dropdown\">\n" +
			"          <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-haspopup=\"true\" aria-expanded=\"false\">\n" +
			"            <i class='material-icons md-24 material-icons-faaf'>language</i>\n" +
			"            <span class=\"dropdown-title\">nl</span>\n" +
			"            <span class=\"caret\"></span></a>\n" +
			"          <ul class=\"dropdown-menu dropdown-menu-right\">\n" +
			"            <li><a href=\"/en/courses/77/series/856/exercises/1820205566/\">English</a></li>\n" +
			"            <li><a href=\"/nl/courses/77/series/856/exercises/1820205566/\">Nederlands</a></li>\n" +
			"          </ul>\n" +
			"        </li>\n" +
			"      </ul>\n" +
			"      <button type=\"button\" class=\"dodona-navbar-toggle\" data-toggle=\"collapse\" data-target=\"#navbar\" aria-expanded=\"false\" aria-controls=\"navbar\">\n" +
			"        <span class=\"sr-only\">Toggle navigation</span>\n" +
			"        <i class=\"material-icons\">\n" +
			"          more_vert\n" +
			"        </i>\n" +
			"      </button>\n" +
			"    </div>\n" +
			"  </div>\n" +
			"</nav>\n" +
			"\n" +
			"  <div id=\"main-container\" class=\"container main\">\n" +
			"    <div class=\"page-messages hidden-print\">\n" +
			"          \n" +
			"          \n" +
			"          <div class=\"alert alert-warning\"><strong>Opgepast!</strong> Je bekijkt deze oefening binnen een cursus waar je geen lid van bent. Je zult niet verschijnen op de puntenlijst van de lesgever tot je bent geregistreerd.</div>\n" +
			"          \n" +
			"    </div>\n" +
			"    \n" +
			"\n" +
			"<div class=\"row\">\n" +
			"  <a class=\"btn btn-lg btn-down col-sm-1 hidden-xs hidden-print\" title=\"Indienen\" href=\"#submission-card\">\n" +
			"    <span class='hidden-sm'>Indienen</span><br>\n" +
			"    <i class=\"material-icons\">expand_more</i>\n" +
			"</a>  <div class=\"col-sm-10 col-xs-12\">\n" +
			"    <div class=\"card exercise-description\">\n" +
			"      <div class=\"card-title card-title-colored\">\n" +
			"        <h2 class=\"card-title-text\">\n" +
			"          De priemiejagers\n" +
			"        </h2>\n" +
			"      </div>\n" +
			"      <div class=\"card-supporting-text\">\n" +
			"    <p><img src=\"/exercises/1820205566/media/bruno_wyndaele.png\" alt=\"Bruno Wyndaele, presentator van De premiejagers\" style=\"max-width: 70%\"></p>\n" +
			"\n" +
			"<p>In deze opgave zul je een programma schrijven dat, gegeven een getal, controleert of dit getal een priemgetal is. We gebruiken hierbij volgende definitie van een priemgetal:</p>\n" +
			"\n" +
			"<blockquote>\n" +
			"Een getal $$p$$ is een priemgetal, als en slechts als $$p$$ een natuurlijk getal is, groter dan 0 en exact 2 verschillende delers heeft, namelijk 1 en zichzelf. Het getal 1 is bijgevolg geen priemgetal.\n" +
			"</blockquote>\n" +
			"\n" +
			"<p>Implementeer deze opgave in een Java-klasse die je <strong>PrimeTime</strong> noemt.</p>\n" +
			"\n" +
			"<h2 id=\"validatie-van-de-invoer\">Validatie van de invoer</h2>\n" +
			"<p>Aangezien priemgetallen strikt positief moeten zijn, dient je programma een foutboodschap te tonen wanneer de gebruiker een negatief getal invoert. Een voorbeeld van zo’n foute uitvoering is de volgende. De tekst in het <span style=\"color: red\">rood</span> stelt invoer van de gebruiker voor:</p>\n" +
			"<pre>Welk getal wil je controleren?\n" +
			"<span style=\"color: red\">-5000</span>\n" +
			"Getallen lager dan 1 zijn geen priemgetallen.\n" +
			"</pre>\n" +
			"\n" +
			"<h2 id=\"voorbeeld\">Voorbeeld</h2>\n" +
			"<p>De tekst in het <span style=\"color: red\">rood</span> stelt invoer van de gebruiker voor (deze wordt niet uitgeprint door je programma).</p>\n" +
			"\n" +
			"<h4 id=\"het-getal-is-geen-priemgetal\">Het getal is geen priemgetal</h4>\n" +
			"<pre>Welk getal wil je controleren?\n" +
			"<span style=\"color: red\">16</span>\n" +
			"Het getal 16 is geen priemgetal.\n" +
			"</pre>\n" +
			"\n" +
			"<h4 id=\"het-getal-is-wel-een-priemgetal\">Het getal is wel een priemgetal</h4>\n" +
			"<pre>Welk getal wil je controleren?\n" +
			"<span style=\"color: red\">17</span>\n" +
			"Het getal 17 is priem!\n" +
			"</pre>\n" +
			"\n" +
			"<hr>\n" +
			"<p>Hierbij nog wat uitleg over de Dodona-testen voor deze oefening:</p>\n" +
			"<ul>\n" +
			"  <li>\n" +
			"<strong>test00objectConcept</strong>: test of de ingediende klasse de verwachte de klasse definitie heeft (interface/abstracte klasse/klasse/enum/…).</li>\n" +
			"  <li>\n" +
			"<strong>test01methodsPresent</strong>: test of de verwachte methodes aanwezig zijn in de ingediende klasse en of de methode definities overeenkomen met de verwachte definities (= test geen effect!).</li>\n" +
			"  <li>\n" +
			"<strong>test02illegalInput</strong>: Test of je code correct een foutboodschap geeft wanneer er ongeldige gegevens worden ingevoerd, zoals een negatief getal.</li>\n" +
			"  <li>\n" +
			"<strong>test03isPrime</strong>: Test of je code correct priemgetallen kan identificeren.</li>\n" +
			"</ul>\n" +
			"\n" +
			"  <footer class=\"footnote-links visible-print\">\n" +
			"  </footer>\n" +
			"</div>\n" +
			"\n" +
			"    </div>\n" +
			"  </div>\n" +
			"</div>\n" +
			"<div class=\"row hidden-print\">\n" +
			"  <a class=\"anchor\" id=\"submission-card\"></a>\n" +
			"  <div class=\"col-sm-10 col-sm-offset-1 col-xs-12\">\n" +
			"    <div class=\"card card-nav\">\n" +
			"      <div class=\"card-title card-title-colored\">\n" +
			"        <ul class=\"nav nav-tabs\">\n" +
			"            <li class=\"active\"><a href=\"#handin\" id=\"exercise-handin-link\" data-toggle=\"tab\">Indienen</a></li>\n" +
			"          <li class=\"\">\n" +
			"            <a href=\"#submissions\" data-toggle=\"tab\" id='exercise-submission-link'>Oplossingen</a></li>\n" +
			"          <li><a href=\"#feedback\" data-toggle=\"tab\" id='exercise-feedback-link' class='hidden'>Feedback</a>\n" +
			"          </li>\n" +
			"        </ul>\n" +
			"      </div>\n" +
			"      <div class=\"card-supporting-text\">\n" +
			"        <div class=\"tab-content\">\n" +
			"          <div class=\"tab-pane fade in active\" id=\"handin\">\n" +
			"              <div class=\"alert alert-info\"><a href=\"/nl/sign_in/\">Log in</a> om je oplossingen te testen.</div>\n" +
			"                <div id=\"deadline-info\" class=\"alert alert-info\" hidden>\n" +
			"                  De deadline voor deze oefening is om 00:00, wat over minder dan 5 minuten is.\n" +
			"                </div>\n" +
			"                <div id=\"deadline-warning\" class=\"alert alert-warning\" hidden>\n" +
			"                  De deadline voor deze oefening was om 2018-10-24 00:00. Je kan nog steeds indienen, maar er wordt waarschijnlijk geen rekening meer mee gehouden.\n" +
			"                </div>\n" +
			"              <div id=\"editor-window\" class='tex2jax_ignore'>\n" +
			"                <div id=\"editor-text\">public class PrimeTime {\n" +
			"\tpublic static void main(final String[] args) {\n" +
			"        // Jouw oplossing komt hier.\n" +
			"    }\n" +
			"}</div>\n" +
			"              </div>\n" +
			"              <span class=\"header-info-text\"><i class=\"material-icons md-18 material-icons-info\">info</i> Je kunt zo vaak indienen als je wenst. Er wordt enkel rekening gehouden met je laatst ingediende oplossing.</span>\n" +
			"          </div>\n" +
			"          <div class=\"tab-pane fade \" id=\"submissions\">\n" +
			"            <div id=\"submissions-table-wrapper\">\n" +
			"                <div class=\"alert alert-info\"><a href=\"/nl/sign_in/\">Log in</a> om je oplossingen te testen.</div>\n" +
			"            </div>\n" +
			"          </div>\n" +
			"          <div class=\"tab-pane fade hidden\" id=\"feedback\">\n" +
			"            <div id=\"submission-wrapper\"></div>\n" +
			"          </div>\n" +
			"        </div>\n" +
			"      </div>\n" +
			"    </div>\n" +
			"  </div>\n" +
			"</div>\n" +
			"<script type=\"text/javascript\">\n" +
			"    $(function () {\n" +
			"        dodona.initExerciseShow(\n" +
			"            1820205566,\n" +
			"            \"java\",\n" +
			"            false,\n" +
			"            true,\n" +
			"            77,\n" +
			"            \"Tue, 23 Oct 2018 22:00:00 GMT\"\n" +
			"        );\n" +
			"    });\n" +
			"</script>\n" +
			"\n" +
			"  </div>\n" +
			"  <div class=\"notifications\"></div>\n" +
			"  <footer class=\"footer\">\n" +
			"  <div class=\"footer-block\">\n" +
			"    &copy; 2019 Universiteit Gent\n" +
			"  </div>\n" +
			"  <div class='footer-spacer'></div>\n" +
			"  <div class=\"footer-block\">\n" +
			"    <a href=\"https://twitter.com/DodonaEdu\" target=\"_blank\">\n" +
			"      <i class=\"mdi mdi-18 mdi-twitter\"></i>\n" +
			"    </a>\n" +
			"    <a href=\"/nl/contact/\">Contact</a>\n" +
			"    <a href=\"/nl/privacy/\">Privacyverklaring</a>\n" +
			"    <a href=\"/nl/data/\">Jouw data</a>\n" +
			"    <a href=\"/nl/posts/\">Dodona 2.10.8</a>\n" +
			"  </div>\n" +
			"</footer>\n" +
			"\n" +
			"</div>\n" +
			"<script>\n" +
			"    I18n = I18n || {};\n" +
			"    I18n.locale = \"nl\";\n" +
			"    dodona.checkTimeZone(-120);\n" +
			"</script>\n" +
			"</body>\n" +
			"</html>\n");
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
