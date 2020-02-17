

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.silentsoft.actlist.plugin.ActlistPlugin;

import com.jfoenix.controls.JFXToggleButton;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class Plugin extends ActlistPlugin {
	
	@FXML
	private JFXToggleButton automaticConvertToggleButton;
	
	@FXML
	private Label automaticConvertPathLabel;
	
	@FXML
	private ListView listView;
	
	public static void main(String[] args) {
		debug();
	}
	
	public Plugin() throws Exception {
		super("InnoRules Ruletrace Converter");
		
		setPluginVersion("0.0.1"); // 1.0.0
		setPluginAuthor("silentsoft.org", URI.create("https://github.com/silentsoft/actlist-innorules-ruletrace-converter-plugin"));
		setPluginUpdateCheckURI(URI.create("http://actlist.silentsoft.org/api/plugin/cc78cf69/update/check"));
		setPluginDescription(URI.create("https://github.com/silentsoft/actlist-innorules-ruletrace-converter-plugin/blob/master/README.md"));
		setPluginChangeLog(URI.create("https://github.com/silentsoft/actlist-innorules-ruletrace-converter-plugin/blob/master/CHANGELOG.md"));
		setPluginLicense(URI.create("https://github.com/silentsoft/actlist-innorules-ruletrace-converter-plugin/blob/master/NOTICE.md"));
		
		setMinimumCompatibleVersion(1, 2, 6);
	}

	@Override
	protected void initialize() throws Exception {
		listView.setPlaceholder(new Label("Drag and Drop ruletrace files to here."));
		
		listView.setOnDragOver(dragEvent -> {
			
		});
		listView.setOnDragDropped(dragEvent -> {
			
		});
	}

	@Override
	public void pluginActivated() throws Exception {
		String automaticConvertPath = getConfig(ConfigKey.AUTOMATIC_CONVERT_PATH);
		
		boolean enabledAutomaticConvert = true;
		if (automaticConvertPath == null || "".equals(automaticConvertPath.trim()) || Files.notExists(Paths.get(automaticConvertPath)) || Files.isDirectory(Paths.get(automaticConvertPath)) == false) {
			enabledAutomaticConvert = false;
		}
		
		automaticConvertToggleButton.setSelected(enabledAutomaticConvert);
		automaticConvertPathLabel.setVisible(enabledAutomaticConvert);
		automaticConvertPathLabel.setText(automaticConvertPath);
	}

	@Override
	public void pluginDeactivated() throws Exception {
		
	}
	
}
