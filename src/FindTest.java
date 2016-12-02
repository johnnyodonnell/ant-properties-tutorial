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
    public void testMissingFile() {
        Find find = new Find();
        try {
            find.execute();
            Assert.fail("No 'no-file'-exception thrown.");
        } catch (Exception e) {
            // exception expected
            String expected = "file not set";
            Assert.assertEquals("Wrong exception message.", expected, e.getMessage());
        }
    }

    @Test
    public void testMissingLocation() {
        Find find = new Find();
        find.setFile("ant.jar");
        try {
            find.execute();
            Assert.fail("No 'no-location'-exception thrown.");
        } catch (Exception e) {
			// exception expected
            String expected = "location not set";
            Assert.assertEquals("Wrong exception message.", expected, e.getMessage());
        }
    }

    @Test
    public void testMissingFileset() {
        Find find = new Find();
        find.setFile("ant.jar");
        find.setLocation("location.ant-jar");
        try {
            find.execute();
            Assert.fail("No 'no-fileset'-exception thrown.");
        } catch (Exception e) {
			// exception expected
            String expected = "path not set";
            Assert.assertEquals("Wrong exception message.", expected, e.getMessage());
        }
    }

    @Test
    public void testFileNotPresent() {
        buildRule.executeTarget("testFileNotPresent");
        String result = buildRule.getProject().getProperty("location.ant-jar");
        Assert.assertNull("Property set to wrong value.", result);
    }

    @Test
    public void testFilePresent() {
        buildRule.executeTarget("testFilePresent");
        String result = buildRule.getProject().getProperty("location.ant-jar");
        Assert.assertNotNull("Property not set.", result);
        Assert.assertTrue("Wrong file found.", result.endsWith("ant.jar"));
    }
}

