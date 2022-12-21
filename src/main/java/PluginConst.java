public class PluginConst {
	
	public static final char REPLACE_TARGET_STX = (char) 0x02;
	public static final char REPLACE_VALUE_STX = ':';

	public static final char REPLACE_TARGET_EOT = (char) 0x04;
	public static final char REPLACE_VALUE_EOT = ':';

	public static final char REPLACE_TARGET_GS = (char) 0x1D;
	public static final char REPLACE_VALUE_GS = ':';

	public static final char REPLACE_TARGET_RS = (char) 0x1E;
	public static final char REPLACE_VALUE_RS = ':';

	public static final char REPLACE_TARGET_ETX = (char) 0x03;
	public static final char REPLACE_VALUE_ETX = ';';

	public static final String REPLACE_TARGET_CRLF = "<CR><LF>";
	public static final String REPLACE_VALUE_CRLF = ";";

	public static final String REPLACE_TARGET_LTGT = "<>";
	public static final String REPLACE_VALUE_LTGT = "&lt;&gt;";

	public static final String REPLACE_TARGET_AMPERSAND = "(?!&amp;)(?!&apos;)(?!&lt;)(?!&gt;)(?!&quot;)[&]";
	public static final String REPLACE_VALUE_AMPERSAND = "&amp;";
	
	public static final String DEFAULT_AUTOMATIC_CONVERT_PATH = "C:\\logs\\innorules\\trace";
	
	public static final String CONFIG_KEY_AUTOMATIC_CONVERT_ENABLED = "AUTOMATIC_CONVERT_ENABLED";
	public static final String CONFIG_KEY_AUTOMATIC_CONVERT_PATH = "AUTOMATIC_CONVERT_PATH";

}
