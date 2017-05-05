package com.hy.filemanager.utils;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import com.hy.filemanager.R;
import com.hy.filemanager.constant.Constant;
import com.hy.filemanager.constant.FileType;
import com.hy.filemanager.entity.FileDetailInfo;

public class FileManager {

    private static FileManager mFileManager;

    private FileManager() {
    }

    public static FileManager getInstance() {
        if (null == mFileManager) {
            synchronized (FileManager.class) {
                if (null == mFileManager) {
                    mFileManager = new FileManager();
                }
            }
        }
        return mFileManager;
    }

    private List<FileDetailInfo> dirDetailInfos = new Vector<>();
    private List<FileDetailInfo> fileDetailInfos = new Vector<>();

    private List<FileDetailInfo> fileAndDirInfos = new Vector<>();

    /**
     * initial data
     */
    private void initData() {
        dirDetailInfos.clear();
        fileDetailInfos.clear();
        fileAndDirInfos.clear();
    }

    /**
     * @param path sd path
     * @return file detail list
     */
    public List<FileDetailInfo> getFileList(File path, boolean isShowHid, boolean isFolderUp, int sortMode) {
        initData();
        File[] fileList = path.listFiles();
        for (File file : fileList) {
            if (!isShowHid && file.getName().startsWith(".")) {
                continue;
            }
            distinguishFile(file);
        }
        fileAndDirInfos.addAll(dirDetailInfos);
        fileAndDirInfos.addAll(fileDetailInfos);

        switch (sortMode) {
            case Constant.SORT_BY_NAME:
                Collections.sort(fileDetailInfos, new SortByNameComparator());
                Collections.sort(dirDetailInfos, new SortByNameComparator());
                Collections.sort(fileAndDirInfos, new SortByNameComparator());
                break;
            case Constant.SORT_BY_DATE:
                Collections.sort(fileDetailInfos, new SortByDateComparator());
                Collections.sort(dirDetailInfos, new SortByDateComparator());
                Collections.sort(fileAndDirInfos, new SortByDateComparator());
                break;
            case Constant.SORT_BY_SIZE:
                Collections.sort(fileDetailInfos, new SortBySizeCopparator());
                fileAndDirInfos.clear();
                fileAndDirInfos.addAll(dirDetailInfos);
                fileAndDirInfos.addAll(fileDetailInfos);
                break;
            case Constant.SORT_BY_TYPE:
                Collections.sort(fileDetailInfos, new SortByTypeComparator());
                Collections.sort(dirDetailInfos, new SortByNameComparator());
                Collections.sort(fileAndDirInfos, new SortByTypeComparator());
                break;
            case Constant.SORT_BY_NAME_INVERTED:
                Collections.sort(fileDetailInfos, new SortByNameInvertComparator());
                Collections.sort(dirDetailInfos, new SortByNameInvertComparator());
                Collections.sort(fileAndDirInfos, new SortByNameInvertComparator());
                break;
            case Constant.SORT_BY_DATE_INVERTED:
                Collections.sort(fileDetailInfos, new SortByDateInvertComparator());
                Collections.sort(dirDetailInfos, new SortByDateInvertComparator());
                Collections.sort(fileAndDirInfos, new SortByDateInvertComparator());
                break;
            case Constant.SORT_BY_SIZE_INVERTED:
                Collections.sort(fileDetailInfos, new SortBySizeInvertCopparator());
                fileAndDirInfos.clear();
                fileAndDirInfos.addAll(dirDetailInfos);
                fileAndDirInfos.addAll(fileDetailInfos);
                break;

        }

        dirDetailInfos.addAll(fileDetailInfos);

        if (isFolderUp) {
            return dirDetailInfos;
        } else {
            return fileAndDirInfos;
        }
    }

    /**
     * create file detail info
     *
     * @param file dir
     */
    private void distinguishFile(File file) {
        if (file.isDirectory()) {
            dirDetailInfos.add(createFileInfo(FileType.FOLDER, file,
                    file.getName(), ""));
        } else {
            String fileName = file.getName();
            String suffix = fileSuffix(fileName);
            if (FileUitls.isTextType(suffix)) {
                fileDetailInfos.add(createFileInfo(FileType.TXT, file,
                        fileName, suffix));
            } else if (FileUitls.isHtmlType(suffix)) {
                fileDetailInfos.add(createFileInfo(FileType.HTML, file,
                        fileName, suffix));
            } else if (FileUitls.isDocType(suffix)) {
                fileDetailInfos.add(createFileInfo(FileType.DOC, file,
                        fileName, suffix));
            } else if (FileUitls.isXlsType(suffix)) {
                fileDetailInfos.add(createFileInfo(FileType.XLS, file,
                        fileName, suffix));
            } else if (FileUitls.isPptType(suffix)) {
                fileDetailInfos.add(createFileInfo(FileType.PPT, file,
                        fileName, suffix));
            } else if (FileUitls.isPdfType(suffix)) {
                fileDetailInfos.add(createFileInfo(FileType.PDF, file,
                        fileName, suffix));
            } else if (FileUitls.isVideoFile(suffix)) {
                fileDetailInfos.add(createFileInfo(FileType.VIDEO, file,
                        fileName, suffix));
            } else if (FileUitls.isAudioFile(suffix)) {
                fileDetailInfos.add(createFileInfo(FileType.AUDIO, file,
                        fileName, suffix));
            } else if (FileUitls.isImageFile(suffix)) {
                fileDetailInfos.add(createFileInfo(FileType.IMAGE, file,
                        fileName, suffix));
            } else if (FileUitls.isZipFile(suffix)) {
                fileDetailInfos.add(createFileInfo(FileType.ARCHIVE, file,
                        fileName, suffix));
            } else if (FileUitls.isProgramFile(suffix)) {
                fileDetailInfos.add(createFileInfo(FileType.APK, file,
                        fileName, suffix));
            } else if (FileUitls.isDBType(suffix)) {
                fileDetailInfos.add(createFileInfo(FileType.DB, file, fileName,
                        suffix));
            } else {
                fileDetailInfos.add(createFileInfo(FileType.OTHER, file,
                        fileName, suffix));
            }
        }
    }

    /**
     * on the basis of file type get FileDetailInfo object
     *
     * @param type     file type
     * @param file     file
     * @param fileName file name
     * @param suffix   file suffix
     * @return file detail info
     */
    private FileDetailInfo createFileInfo(FileType type, File file, String fileName, String suffix) {
        switch (type) {
            case FOLDER:// folder
                return new FileDetailInfo(R.mipmap.folder_blue, file, fileName,
                        suffix);
            case TXT:// txt
                return new FileDetailInfo(R.drawable.icon_file_text, file,
                        fileName, suffix);
            case DOC:// dox
                return new FileDetailInfo(R.drawable.icon_file_word, file,
                        fileName, suffix);
            case PPT:// ppt
                return new FileDetailInfo(R.drawable.icon_file_powerpoint, file,
                        fileName, suffix);
            case XLS:// xls
                return new FileDetailInfo(R.drawable.icon_file_excel, file,
                        fileName, suffix);
            case PDF:// pdf
                return new FileDetailInfo(R.drawable.icon_file_pdf, file, fileName,
                        suffix);
            case HTML:// html
                return new FileDetailInfo(R.drawable.icon_file_html, file,
                        fileName, suffix);
            case VIDEO:// video
                return new FileDetailInfo(R.drawable.icon_file_video, file,
                        fileName, suffix);
            case AUDIO:// audio
                return new FileDetailInfo(R.drawable.icon_file_audio, file,
                        fileName, suffix);
            case IMAGE:// image
                return new FileDetailInfo(R.drawable.icon_file_image, file,
                        fileName, suffix);
            case ARCHIVE:// archive
                return new FileDetailInfo(R.drawable.icon_file_archive, file,
                        fileName, suffix);
            case APK:// app
                return new FileDetailInfo(R.drawable.icon_file_apk, file, fileName,
                        suffix);
            case DB:// database
                return new FileDetailInfo(R.drawable.icon_file_database, file,
                        fileName, suffix);
            case OTHER:// other
            default:
                return new FileDetailInfo(R.drawable.icon_file_unknown, file,
                        fileName, suffix);
        }
    }

    /**
     * get file suffix
     *
     * @param fileName file name
     * @return file suffix
     */
    public String fileSuffix(String fileName) {
        if (fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

}
