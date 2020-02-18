public class PluginConst {
	
	public static final char REPLACE_TARGET_STX = new Character((char) 02);
	public static final char REPLACE_TARGET_ETX = new Character((char) 03);
	public static final char REPLACE_VALUE_STX_ETX = ':';
	
	public static final String REPLACE_TARGET_LTGT = "<>";
	public static final String REPLACE_VALUE_LTGT = "";
	
	public static final String REPLACE_TARGET_CRLF = "<CR><LF>";
	public static final String REPLACE_VALUE_CRLF = ":";
	
	public static final String REPLACE_TARGET_AMPERSAND = "(?!&amp;)(?!&apos;)(?!&lt;)(?!&gt;)(?!&quot;)[&]";
	public static final String REPLACE_VALUE_AMPERSAND = "&amp;";
	
	public static final String DEFAULT_AUTOMATIC_CONVERT_PATH = "C:\\logs\\innorules\\trace";
	
	public static final String CONFIG_KEY_AUTOMATIC_CONVERT_ENABLED = "AUTOMATIC_CONVERT_ENABLED";
	public static final String CONFIG_KEY_AUTOMATIC_CONVERT_PATH = "AUTOMATIC_CONVERT_PATH";

}
