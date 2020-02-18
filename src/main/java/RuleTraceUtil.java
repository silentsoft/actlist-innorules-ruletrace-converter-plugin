public class RuleTraceUtil {
	
	public static char[] convert(String content) {
		content = content.replaceAll(PluginConst.REPLACE_TARGET_LTGT, PluginConst.REPLACE_VALUE_LTGT);
		content = content.replaceAll(PluginConst.REPLACE_TARGET_CRLF, PluginConst.REPLACE_VALUE_CRLF);
		content = content.replaceAll(PluginConst.REPLACE_TARGET_AMPERSAND, PluginConst.REPLACE_VALUE_AMPERSAND);
		
		char[] charArray = content.toCharArray();
		for (int i=0, j=charArray.length; i<j; i++) {
			if (charArray[i] == PluginConst.REPLACE_TARGET_STX || charArray[i] == PluginConst.REPLACE_TARGET_ETX) {
				charArray[i] = PluginConst.REPLACE_VALUE_STX_ETX;
			}
		}
		
		return charArray;
	}

}
