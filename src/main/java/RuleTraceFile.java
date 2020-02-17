import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.io.IOUtils;
import org.silentsoft.core.util.InetAddressUtil;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.SVGPath;

public class RuleTraceFile extends HBox {
	
	private File file;
	
	private HBox statusBox;
	
	public RuleTraceFile(File file) {
		this.file = file;
		
		composite();
	}
	
	private void composite() {
		statusBox = new HBox();
		markStatusAsInProgress();
		
		setSpacing(5.0);
		getChildren().addAll(statusBox, new Label(String.format("%s (%s)", file.getName(), file.getParentFile().getPath())));
	}
	
	protected void convert() {
		new Thread(() -> {
			FileInputStream fileInputStream = null;
			InputStreamReader inputStreamReader = null;
			BufferedReader bufferedReader = null;
			FileOutputStream fileOutputStream = null;
			
			AtomicBoolean hasBeenSucceed = new AtomicBoolean(false);
			try {
				StringBuilder ruleXml = new StringBuilder();
				
				String encoding = determineEncoding();
				
				try {
					fileInputStream = new FileInputStream(file);
					inputStreamReader = new InputStreamReader(fileInputStream, encoding);
					bufferedReader = new BufferedReader(inputStreamReader);
					
					boolean isFirstLine = true;
					String current;
					while ((current = bufferedReader.readLine()) != null) {
						if (isFirstLine) {
							isFirstLine = false;
							
							if (current.startsWith("<?xml version=") == false) {
								current = String.format("<?xml version=\"1.0\" encoding=\"%s\"?>\r\n%s", encoding, current);
							}
						}
						
						current = current.replaceAll("<>", "");
						current = current.replaceAll("<CR><LF>", ":");
						
						char[] charArray = current.toCharArray();
						for (int i=0, j=charArray.length; i<j; i++) {
							if (charArray[i] == PluginConst.STX || charArray[i] == PluginConst.ETX) {
								charArray[i] = ':';
							}
						}

						ruleXml.append(charArray);
						ruleXml.append("\r\n");
					}
				} catch (Exception e) {
					throw e;
				} finally {
					IOUtils.closeQuietly(fileInputStream);
					IOUtils.closeQuietly(inputStreamReader);
					IOUtils.closeQuietly(bufferedReader);
				}
				
				fileOutputStream = new FileOutputStream(file);
				IOUtils.write(ruleXml, fileOutputStream, encoding);
				
				hasBeenSucceed.set(true);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				IOUtils.closeQuietly(fileOutputStream);
				
				Platform.runLater(() -> {
					if (hasBeenSucceed.get()) {
						markStatusAsSucceed();
					} else {
						markStatusAsFailed();
					}
				});
			}
		}).start();
	}
	
	private String determineEncoding() {
		if (file.getName().contains(InetAddressUtil.getAddress())) {
			return "utf-8";
		}
		
		return "euc-kr";
	}
	
	
	private void markStatusAsInProgress() {
		ProgressIndicator status = new ProgressIndicator(ProgressIndicator.INDETERMINATE_PROGRESS);
		status.setPrefSize(16, 16);
		
		statusBox.getChildren().setAll(status);
	}
	
	private void markStatusAsSucceed() {
		SVGPath status = new SVGPath();
		status.setFill(Paint.valueOf("#59bf53"));
		status.setContent("M9 16.2L4.8 12l-1.4 1.4L9 19 21 7l-1.4-1.4L9 16.2z");
		
		statusBox.getChildren().setAll(status);
	}
	
	private void markStatusAsFailed() {
		SVGPath status = new SVGPath();
		status.setFill(Paint.valueOf("#da4242"));
		status.setContent("M19 6.41L17.59 5 12 10.59 6.41 5 5 6.41 10.59 12 5 17.59 6.41 19 12 13.41 17.59 19 19 17.59 13.41 12z");
		
		statusBox.getChildren().setAll(status);
	}
	
}