package ru.bellintegrator.dto;

/**
 * Dto информации о файле
 */
public class FileInfoView {
    /**
     * Имя файла
     */
    private String filename;
    /**
     * Имя файла с UUID
     */
    private String tmpFilename;
    /**
     * Путь для скачивания
     */
    private String url;
    /**
     * Размер файла
     */
    private Long fileSize;
    /**
     * Число скачиваний
     */
    private Integer downloadCount;

    /**
     * dto пользователя
     */
    private UserView userView;

    public FileInfoView() {
    }

    public FileInfoView(String filename, String tmpFilename, String url, Long fileSize, UserView userView) {
        this.filename = filename;
        this.tmpFilename = tmpFilename;
        this.url = url;
        this.fileSize = fileSize;
        this.userView = userView;
    }


    public UserView getUserView() {
        return userView;
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }

    public String getFilename() {
        return this.filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getTmpFilename() {
        return tmpFilename;
    }

    public void setTmpFilename(String tmpFilename) {
        this.tmpFilename = tmpFilename;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }
}
