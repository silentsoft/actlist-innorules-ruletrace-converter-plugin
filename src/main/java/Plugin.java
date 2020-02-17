

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.silentsoft.actlist.plugin.ActlistPlugin;

import com.jfoenix.controls.JFXToggleButton;
import com.sun.javafx.stage.StageHelper;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class Plugin extends ActlistPlugin {
	
	@FXML
	private JFXToggleButton automaticConvertToggleButton;
	
	@FXML
	private Label automaticConvertPathLabel;
	
	@FXML
	private ListView<RuleTraceFile> ruleTraceFileListView;
	
	private Thread directoryObserverThread = null;
	
	private List<String> convertedTraces = new ArrayList<>();
	
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
		initConfig();
		
		{
			String automaticConvertPath = getConfig(PluginConst.CONFIG_KEY_AUTOMATIC_CONVERT_PATH);
			
			boolean enabledAutomaticConvert = true;
			if (automaticConvertPath == null || "".equals(automaticConvertPath.trim()) || Files.notExists(Paths.get(automaticConvertPath)) || Files.isDirectory(Paths.get(automaticConvertPath)) == false) {
				enabledAutomaticConvert = false;
			}
			
			automaticConvertPathLabel.setVisible(enabledAutomaticConvert);
			automaticConvertPathLabel.setText(automaticConvertPath);
			automaticConvertToggleButton.setSelected(enabledAutomaticConvert);
		}
		
		automaticConvertToggleButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
			try {
				if (newValue) {
					boolean shouldStartDirectoryObserverThread = true;
					
					String automaticConvertPath = getConfig(PluginConst.CONFIG_KEY_AUTOMATIC_CONVERT_PATH);
					if (automaticConvertPath == null || "".equals(automaticConvertPath.trim()) || Files.notExists(Paths.get(automaticConvertPath)) || Files.isDirectory(Paths.get(automaticConvertPath)) == false) {
						File directory = changeAutomaticConvertPath();
						if (directory == null) {
							shouldStartDirectoryObserverThread = false;
							
							automaticConvertToggleButton.setSelected(false);
						}
					}
					
					if (shouldStartDirectoryObserverThread) {
						automaticConvertPathLabel.setVisible(true);
						automaticConvertPathLabel.setText(getConfig(PluginConst.CONFIG_KEY_AUTOMATIC_CONVERT_PATH));
						
						startDirectoryObserverThread();
					}
				} else {
					stopDirectoryObserverThread();
					
					automaticConvertPathLabel.setVisible(false);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		
		automaticConvertPathLabel.setOnMouseReleased(mouseEvent -> changeAutomaticConvertPath());
		
		ruleTraceFileListView.setPlaceholder(new Label("Drag and Drop ruletrace files to here."));
		
		ruleTraceFileListView.setOnDragOver(dragEvent -> {
			Dragboard dragboard = dragEvent.getDragboard();
			if (dragboard.hasFiles()) {
				boolean containsXmlFilesOnly = true;
				
				List<File> files = dragboard.getFiles();
				for (File file : files) {
					if (file.getName().toLowerCase().endsWith(".xml") == false) {
						containsXmlFilesOnly = false;
						break;
					}
				}
				
				if (containsXmlFilesOnly) {
					dragEvent.acceptTransferModes(TransferMode.LINK);
				}
			}
			
			dragEvent.consume();
		});
		ruleTraceFileListView.setOnDragDropped(dragEvent -> {
			convertRuleTrace(dragEvent.getDragboard().getFiles());
		});
	}
	
	private void initConfig() throws Exception {
		String automaticConvertPath = getConfig(PluginConst.CONFIG_KEY_AUTOMATIC_CONVERT_PATH);
		if (automaticConvertPath == null || "".equals(automaticConvertPath.trim())) {
			Path innorulesTracePath = Paths.get(PluginConst.DEFAULT_AUTOMATIC_CONVERT_PATH);
			if (Files.exists(innorulesTracePath)) {
				putConfig(PluginConst.CONFIG_KEY_AUTOMATIC_CONVERT_PATH, PluginConst.DEFAULT_AUTOMATIC_CONVERT_PATH);
			}
		}
	}

	@Override
	public void pluginActivated() throws Exception {
		if (automaticConvertToggleButton.isSelected()) {
			startDirectoryObserverThread();
		}
	}

	@Override
	public void pluginDeactivated() throws Exception {
		stopDirectoryObserverThread();
	}
	
	private void startDirectoryObserverThread() {
		if (directoryObserverThread != null) {
			directoryObserverThread.interrupt();
		}
		
		directoryObserverThread = null;
		directoryObserverThread = new Thread(() -> {
			while (true) {
				try {
					Path automaticConvertPath = Paths.get((String) getConfig(PluginConst.CONFIG_KEY_AUTOMATIC_CONVERT_PATH));
					if (Files.exists(automaticConvertPath)) {
						List<Path> notConvertedRuleTraces = Files.walk(automaticConvertPath, 1)
								.filter(path -> path.toString().toLowerCase().endsWith(".xml") && convertedTraces.contains(path.toString()) == false)
								.collect(Collectors.toList());
						notConvertedRuleTraces.forEach(path -> {
							convertRuleTrace(path.toFile());
							
							convertedTraces.add(path.toString());
						});
					}
				} catch (Throwable e) {
					e.printStackTrace();
				} finally {
					try {
						Thread.sleep(TimeUnit.SECONDS.toMillis(5));
					} catch (InterruptedException e) {
						
					}
				}
			}
		});
		directoryObserverThread.start();
	}
	
	private void stopDirectoryObserverThread() {
		if (directoryObserverThread != null) {
			directoryObserverThread.interrupt();
			directoryObserverThread = null;
		}
	}
	
	private File changeAutomaticConvertPath() {
		File directory = null;
		
		try {
			DirectoryChooser directoryChooser = new DirectoryChooser();
			Path innorulesPath = Paths.get(PluginConst.DEFAULT_AUTOMATIC_CONVERT_PATH);
			if (Files.exists(innorulesPath)) {
				directoryChooser.setInitialDirectory(innorulesPath.toFile());
			}
			directoryChooser.setTitle("Select trace directory to automatic convert");
			
			Window window = null;
			{
				ObservableList<Stage> stages = StageHelper.getStages();
				if (stages.isEmpty() == false) {
					window = stages.get(0);
				}
			}
			directory = directoryChooser.showDialog(window);
			if (directory != null) {
				putConfig(PluginConst.CONFIG_KEY_AUTOMATIC_CONVERT_PATH, directory.getAbsolutePath());
				
				automaticConvertPathLabel.setText(directory.getAbsolutePath());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return directory;
	}
	
	private void convertRuleTrace(List<File> files) {
		if (files == null) {
			return;
		}
		
		for (File file : files) {
			convertRuleTrace(file);
		}
	}
	
	private void convertRuleTrace(File file) {
		if (file == null || Files.notExists(file.toPath())) {
			return;
		}
		
		RuleTraceFile ruleTraceFile = new RuleTraceFile(file);
		Platform.runLater(() -> {
			ruleTraceFileListView.getItems().add(0, ruleTraceFile);
		});
		ruleTraceFile.convert();
	}
	
}
