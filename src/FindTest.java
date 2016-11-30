import org.junit.Rule;
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;
import org.apache.tools.ant.BuildFileRule;


public class FindTest {

    @Rule
    public final BuildFileRule buildRule = new BuildFileRule();


    @Before
    public void setUp() {
        buildRule.configureProject("build.xml");
    }

    @Test
    public void testSimple() {
        buildRule.executeTarget("use.simple");
        Assert.assertEquals("test-value", buildRule.getLog());
    }
}

