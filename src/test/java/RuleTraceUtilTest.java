import org.junit.Assert;
import org.junit.Test;

public class RuleTraceUtilTest {
	
	@Test
	public void convertTest() {
		Assert.assertArrayEquals(String.valueOf(PluginConst.REPLACE_VALUE_STX).toCharArray(), RuleTraceUtil.convert(Character.toString(PluginConst.REPLACE_TARGET_STX)));
		Assert.assertArrayEquals(String.valueOf(PluginConst.REPLACE_VALUE_EOT).toCharArray(), RuleTraceUtil.convert(Character.toString(PluginConst.REPLACE_TARGET_EOT)));
		Assert.assertArrayEquals(String.valueOf(PluginConst.REPLACE_VALUE_GS).toCharArray(), RuleTraceUtil.convert(Character.toString(PluginConst.REPLACE_TARGET_GS)));
		Assert.assertArrayEquals(String.valueOf(PluginConst.REPLACE_VALUE_RS).toCharArray(), RuleTraceUtil.convert(Character.toString(PluginConst.REPLACE_TARGET_RS)));

		Assert.assertArrayEquals(String.valueOf(PluginConst.REPLACE_VALUE_ETX).toCharArray(), RuleTraceUtil.convert(Character.toString(PluginConst.REPLACE_TARGET_ETX)));
		Assert.assertArrayEquals(PluginConst.REPLACE_VALUE_CRLF.toCharArray(), RuleTraceUtil.convert(PluginConst.REPLACE_TARGET_CRLF));

		Assert.assertArrayEquals(PluginConst.REPLACE_VALUE_LTGT.toCharArray(), RuleTraceUtil.convert(PluginConst.REPLACE_TARGET_LTGT));
		Assert.assertArrayEquals(PluginConst.REPLACE_VALUE_AMPERSAND.toCharArray(), RuleTraceUtil.convert("&"));

		Assert.assertArrayEquals("&amp;".toCharArray(), RuleTraceUtil.convert("&amp;"));
		Assert.assertArrayEquals("&apos;".toCharArray(), RuleTraceUtil.convert("&apos;"));
		Assert.assertArrayEquals("&lt;".toCharArray(), RuleTraceUtil.convert("&lt;"));
		Assert.assertArrayEquals("&gt;".toCharArray(), RuleTraceUtil.convert("&gt;"));
		Assert.assertArrayEquals("&quot;".toCharArray(), RuleTraceUtil.convert("&quot;"));

		Assert.assertArrayEquals("A&lt;&gt;B".toCharArray(), RuleTraceUtil.convert("A<>B"));
		Assert.assertArrayEquals("A&lt;&gt;B".toCharArray(), RuleTraceUtil.convert("A&lt;&gt;B"));
		Assert.assertArrayEquals("A&lt;&gt;B A&lt;&gt;B".toCharArray(), RuleTraceUtil.convert("A<>B A&lt;&gt;B"));
		Assert.assertArrayEquals("A&lt;&gt;B A&lt;&gt;B".toCharArray(), RuleTraceUtil.convert("A&lt;&gt;B A<>B"));

		Assert.assertArrayEquals("A&amp;B".toCharArray(), RuleTraceUtil.convert("A&B"));
		Assert.assertArrayEquals("A&amp;B".toCharArray(), RuleTraceUtil.convert("A&amp;B"));
		Assert.assertArrayEquals("A&amp;B A&amp;B".toCharArray(), RuleTraceUtil.convert("A&B A&amp;B"));
		Assert.assertArrayEquals("A&amp;B A&amp;B".toCharArray(), RuleTraceUtil.convert("A&amp;B A&B"));
	}
	
}
