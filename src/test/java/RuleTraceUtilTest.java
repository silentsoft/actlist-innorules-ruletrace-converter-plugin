import org.junit.Assert;
import org.junit.Test;

public class RuleTraceUtilTest {
	
	@Test
	public void convertTest() {
		Assert.assertArrayEquals("".toCharArray(), RuleTraceUtil.convert("<>"));
		Assert.assertArrayEquals(":".toCharArray(), RuleTraceUtil.convert("<CR><LF>"));
		Assert.assertArrayEquals(":".toCharArray(), RuleTraceUtil.convert(Character.toString((char) 02)));
		Assert.assertArrayEquals(":".toCharArray(), RuleTraceUtil.convert(Character.toString((char) 03)));
		
		Assert.assertArrayEquals("&amp;".toCharArray(), RuleTraceUtil.convert("&amp;"));
		Assert.assertArrayEquals("&apos;".toCharArray(), RuleTraceUtil.convert("&apos;"));
		Assert.assertArrayEquals("&lt;".toCharArray(), RuleTraceUtil.convert("&lt;"));
		Assert.assertArrayEquals("&gt;".toCharArray(), RuleTraceUtil.convert("&gt;"));
		Assert.assertArrayEquals("&quot;".toCharArray(), RuleTraceUtil.convert("&quot;"));
		
		Assert.assertArrayEquals("&amp;".toCharArray(), RuleTraceUtil.convert("&"));
		Assert.assertArrayEquals("A&amp;B".toCharArray(), RuleTraceUtil.convert("A&B"));
		Assert.assertArrayEquals("A&amp;B".toCharArray(), RuleTraceUtil.convert("A&amp;B"));
		Assert.assertArrayEquals("A&amp;B A&amp;B".toCharArray(), RuleTraceUtil.convert("A&B A&amp;B"));
		Assert.assertArrayEquals("A&amp;B A&amp;B".toCharArray(), RuleTraceUtil.convert("A&amp;B A&B"));
	}
	
}
