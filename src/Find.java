import org.apache.tools.ant.Task;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.DirectoryScanner;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import java.io.File;

public class Find extends Task {

    /* Using filesets */
    private String file;
    private String location;
    private List<Path> paths = new ArrayList<>();

    /* Using filesets */
    public void setFile(String file) {
    	this.file = file;
    }

    public void setLocation(String location) {
    	this.location = location;
    }

    public void addPath(Path path) {
	this.paths.add(path);
    }

    protected void validate() {
        if (file==null) throw new BuildException("file not set");
        if (location==null) throw new BuildException("location not set");
        if (paths.size()<1) throw new BuildException("path not set");
    }

    public void execute() {
	validate();                                                             // 1
        String foundLocation = null;
        for(Iterator itPaths = paths.iterator(); itPaths.hasNext(); ) {         // 2
            Path path = (Path) itPaths.next();
            String[] includedFiles = path.list();
            for(int i=0; i<includedFiles.length; i++) {
                String filename = includedFiles[i].replace('\\','/');           // 4
                filename = filename.substring(filename.lastIndexOf("/")+1);
                if (foundLocation==null && file.equals(filename)) {
                    foundLocation = includedFiles[i];
                }
            }
        }
        if (foundLocation!=null)                                                // 6
            getProject().setNewProperty(location, foundLocation);
    }
}

