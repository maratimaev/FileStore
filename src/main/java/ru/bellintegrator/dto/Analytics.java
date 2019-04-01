package ru.bellintegrator.dto;

/**
 * Dto статистики
 */
public class Analytics {

    /**
     * dto пользователя
     */
    private UserView userView;

    /**
     * количество файлов пользователя
     */
    private Integer countFiles;

    public Analytics() {
    }

    public Analytics(UserView userView, Integer countFiles) {
        this.userView = userView;
        this.countFiles = countFiles;
    }

    public UserView getUserView() {
        return userView;
    }

    public void setUserView(UserView userView) {
        this.userView = userView;
    }

    public Integer getCountFiles() {
        return countFiles;
    }

    public void setCountFiles(Integer countFiles) {
        this.countFiles = countFiles;
    }
}
