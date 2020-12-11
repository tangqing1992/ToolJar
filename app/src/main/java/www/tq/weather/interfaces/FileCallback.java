package www.tq.weather.interfaces;

import java.util.ArrayList;

import www.tq.weather.model.FileUnit;
import www.tq.weather.model.FolderUnit;

public interface FileCallback {
    void  callback(ArrayList<FolderUnit> folderUnits, ArrayList<FileUnit> fileUnits);
    void  callback(ArrayList<FileUnit> fileUnits);
}
