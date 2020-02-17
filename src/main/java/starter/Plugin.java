package starter;

import java.net.URI;

import org.silentsoft.actlist.plugin.ActlistPlugin;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class Plugin extends ActlistPlugin {
	
	@FXML
	private Button button;
	
	public static void main(String[] args) {
		debug();
	}
	
	public Plugin() throws Exception {
		super("your plugin name here");
		
		setPluginVersion("1.0.0");
		
		/**
		 * change this value to your own GitHub account and repository.
		 * 
		 * see also https://actlist.silentsoft.org/docs/update-check/
		 */
		//setPluginUpdateCheckURI(URI.create("https://github.com/your-github-account/actlist-plugin-starter-kit/raw/master/update-check.js"));
		
		setPluginAuthor("John Doe");
		/**
		 * or you could use hyper-link via
		 * setPluginAuthor("John Doe", URI.create("https://github.com/your-github-account/"));
		 */
		
		setPluginDescription("You can set the description of your plugin");
		/**
		 * or you could use file via
		 * setPluginDescription(getClass().getResource("/Plugin.description").toURI());
		 *
		 * ! you can set the plugin's ChangeLog and License with same way
		 */
		setPluginChangeLog(getClass().getResource("/Plugin.changelog").toURI());
		//setPluginLicense(getClass().getResource("/Plugin.license").toURI());
	}

	@Override
	protected void initialize() throws Exception {
		System.out.println("#initialize");
		
		button.setOnMouseClicked(mouseEvent -> {
			System.out.println("Hello World !");
		});
	}

	@Override
	public void pluginActivated() throws Exception {
		System.out.println("#pluginActivated");
	}

	@Override
	public void pluginDeactivated() throws Exception {
		System.out.println("#pluginDeactivated");
	}
	
}
