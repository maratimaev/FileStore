package ru.bellintegrator.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Entity загруженного файла
 */
@Entity
@Table(name = "fileinfo")
public class FileInfo {
    /**
     * id файла
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Служебное поле JPA
     */
    @Version
    private int version;

    /**
     * Имя файла
     */
    @Column(length = 259)
    private String filename;

    /**
     * Имя файла с UUID
     */
    @Column(length = 259)
    private String tmpFilename;

    /**
     * Размер файла
     */
    private Long fileSize;

    /**
     * Владелец файла
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Число скачиваний файла
     */
    private Integer downloadCount;

    public FileInfo() {
    }

    public FileInfo(String filename, String tmpFilename, Long fileSize, User user, Integer downloadCount) {
        this.filename = filename;
        this.tmpFilename = tmpFilename;
        this.fileSize = fileSize;
        this.user = user;
        this.downloadCount = downloadCount;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getTmpFilename() {
        return tmpFilename;
    }

    public void setTmpFilename(String tmpFilename) {
        this.tmpFilename = tmpFilename;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        return "FileInfo{" +
                "id=" + id +
                ", version=" + version +
                ", filename='" + filename + '\'' +
                ", tmpFilename='" + tmpFilename + '\'' +
                ", fileSize=" + fileSize +
                ", user=" + user +
                ", downloadCount=" + downloadCount +
                '}';
    }
}
