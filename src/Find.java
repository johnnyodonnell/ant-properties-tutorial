import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.DirectoryScanner;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import java.io.File;

public class Find extends Task {

    /* Using paths */
    private String file;
    private String location;
    private List<Path> paths = new ArrayList<>();
    private String delimiter; 

    /* Using paths */
    public void setFile(String file) {
    	this.file = file;
    }

    public void setLocation(String location) {
    	this.location = location;
    }

    public void addPath(Path path) {
	this.paths.add(path);
    }

    public void setDelimiter(String delimiter) {
    	this.delimiter = delimiter;
    }

    protected void validate() {
        if (file==null) throw new BuildException("file not set");
        if (location==null) throw new BuildException("location not set");
        if (paths.size()<1) throw new BuildException("path not set");
    }

    public void execute() {
	List<String> foundFiles = new ArrayList<>();

	validate();                                                             // 1
        for(Iterator itPaths = paths.iterator(); itPaths.hasNext(); ) {         // 2
            Path path = (Path) itPaths.next();
            String[] includedFiles = path.list();
            for(int i=0; i<includedFiles.length; i++) {
                String filename = includedFiles[i].replace('\\','/');           // 4
                filename = filename.substring(filename.lastIndexOf("/")+1);
                if (file.equals(filename) && !foundFiles.contains(includedFiles[i])) {
                    foundFiles.add(includedFiles[i]);
                }
            }
        }

	// create the return value (list/single)
        String rv = null;
        if (foundFiles.size() > 0) {                                        // 2
            if (delimiter==null) {
                // only the first
                rv = (String)foundFiles.get(0);
            } else {
                // create list
                StringBuffer list = new StringBuffer();
                for(Iterator it=foundFiles.iterator(); it.hasNext(); ) {    // 3
                    list.append(it.next());
                    if (it.hasNext()) list.append(delimiter);               // 4
                }
                rv = list.toString();
            }
        }

        if (rv!=null)                                                // 6
            getProject().setNewProperty(location, rv);
    }
}

