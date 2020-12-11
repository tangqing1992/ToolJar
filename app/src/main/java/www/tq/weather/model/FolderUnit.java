package www.tq.weather.model;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FolderUnit {
    private String name;
    private ArrayList<FileUnit> fileUnits;

    public FolderUnit(String name) {
        this.name = name;
    }

    public FolderUnit(String name, ArrayList<FileUnit> fileUnits) {
        this.name = name;
        this.fileUnits = fileUnits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FileUnit> getFileUnits() {
        return fileUnits;
    }

    public void setFileUnits(ArrayList<FileUnit> images) {
        this.fileUnits = fileUnits;
    }

    public void addFileUnit(FileUnit fileUnit) {
        if (fileUnit != null && !fileUnit.getPath().isEmpty()) {
            if (fileUnits == null) {
                fileUnits = new ArrayList<>();
            }
            fileUnits.add(fileUnit);
        }
    }


    /**
     * 把图片按文件夹拆分，第一个文件夹保存所有的图片
     */
    public static ArrayList<FolderUnit> splitFolder(Context context, String firstfoldername,ArrayList<FileUnit> fileUnits) {
        ArrayList<FolderUnit> folders = new ArrayList<>();
        folders.add(new FolderUnit(firstfoldername, fileUnits));
        if (fileUnits != null && !fileUnits.isEmpty()) {
            int size = fileUnits.size();
            for (int i = 0; i < size; i++) {
                String path = fileUnits.get(i).getPath();
                String name = getFolderName(path);
                if (!name.isEmpty()) {
                    FolderUnit folder = getFolder(name, folders);
                    folder.addFileUnit(fileUnits.get(i));
                }
            }
        }
        return folders;
    }


    /**
     * 根据图片路径，获取图片文件夹名称
     */
    private static String getFolderName(String path) {
        if (!path.isEmpty()) {
            String[] strings = path.split(File.separator);
            if (strings.length >= 2) {
                return strings[strings.length - 2];
            }
        }
        return "";

    }

    private static FolderUnit getFolder(String name, List<FolderUnit> folders) {
        if (folders != null && !folders.isEmpty()) {
            int size = folders.size();
            for (int i = 0; i < size; i++) {
                FolderUnit folder = folders.get(i);
                if (name.equals(folder.getName())) {
                    return folder;
                }
            }
        }
        FolderUnit newFolder = new FolderUnit(name);
        folders.add(newFolder);
        return newFolder;
    }
}
