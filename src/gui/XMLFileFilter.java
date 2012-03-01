package gui;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class XMLFileFilter extends FileFilter{

    @Override
    public boolean accept(File f) {
        if (f.isFile()){
            String filename=f.getName();
            int x=filename.lastIndexOf('.');
            return filename.substring(x,filename.length()).equals(".xml");
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "XML file only";
    }

}
