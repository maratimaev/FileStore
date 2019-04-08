package ru.bellintegrator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ru.bellintegrator.controller.DownloadFileController;
import ru.bellintegrator.dto.FileInfoView;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.entity.FileInfo;
import ru.bellintegrator.entity.User;
import ru.bellintegrator.entity.mapper.MapperFacade;
import ru.bellintegrator.repository.FileInfoRepository;
import ru.bellintegrator.service.FileService;
import ru.bellintegrator.service.UserService;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * {@inheritDoc}
 */
@Service
public class FileServiceImpl implements FileService {

    /**
     * Директория хранения файлов
     */
    private final Path rootLocation = Paths.get("service");

    /**
     * {@inheritDoc}
     */
    @Autowired
    private FileInfoRepository fileInfoRepository;

    /**
     * {@inheritDoc}
     */
    @Autowired
    private UserService userService;

    /**
     * {@inheritDoc}
     */
    @Autowired
    MapperFacade mapperFacade;

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        if (!Files.exists(rootLocation)) {
            try {
                Files.createDirectory(rootLocation);
            } catch (IOException e) {
                throw new RuntimeException("Could not initialize storage!");
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<FileInfoView> getUserFiles(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("(Custom) Error -> username can't be null");
        }
        User user = userService.getUser(username);
        List<FileInfo> fileInfoList = fileInfoRepository.findByUser(user);
        return mapperFacade.mapAsList(fileInfoList, FileInfoView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<FileInfoView> getDownloadableSharedFiles(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("(Custom) Error -> username can't be null");
        }
        User user = userService.getUser(username);
        List<FileInfo> accessibleFileList = new ArrayList<>();
        for(User u : userService.getUserListMinus(username)){
            if(u.getDownloadGroup().getMembers().contains(user)) {
                accessibleFileList.addAll(fileInfoRepository.findByUser(u));
            }
        }
        return mapperFacade.mapToFileInfoViewList(accessibleFileList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<FileInfoView> getListableSharedFiles(String username) {
        if (StringUtils.isEmpty(username)) {
            throw new RuntimeException("(Custom) Error -> username can't be null");
        }
        User user = userService.getUser(username);

        List<FileInfo> accessibleFileList = new ArrayList<>();
        for(User u : userService.getUserListMinus(username)){
            if(u.getListGroup().getMembers().contains(user) && !u.getDownloadGroup().getMembers().contains(user)) {
                accessibleFileList.addAll(fileInfoRepository.findByUser(u));
            }
        }
        return mapperFacade.mapToFileInfoViewList(accessibleFileList);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<FileInfoView> getAllFiles() {
        List<FileInfo> allFileInfo = fileInfoRepository.findAll();
        return mapperFacade.mapToFileInfoViewList(allFileInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FileInfoView> addDownloadlink(List<FileInfoView> files) {
        if (files == null) {
            throw new RuntimeException("(Custom) Error -> list of FileInfo can't be null");
        }
        List<FileInfoView> filesWithDownloadLink = new ArrayList<>();

        for (FileInfoView f: files) {
            filesWithDownloadLink.add(new FileInfoView(
                    f.getFilename(),
                    f.getTmpFilename(),
                    MvcUriComponentsBuilder.fromMethodName(DownloadFileController.class, "downloadFile", f.getTmpFilename()).build().toString(),
                    f.getFileSize(),
                    f.getUserView()
            ));
        }
        return filesWithDownloadLink;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public FileInfoView findFile(String tmpFilename) {
        if (StringUtils.isEmpty(tmpFilename)) {
            throw new RuntimeException("(Custom) Error -> tmpFilename can't be null");
        }
        FileInfo fileInfo = fileInfoRepository.findByTmpFilename(tmpFilename);
        return mapperFacade.map(fileInfo, FileInfoView.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Integer countFiles(UserView userView) {
        if (userView == null) {
            throw new RuntimeException("(Custom) Error -> userView can't be null");
        }
        User user = userService.getUser(userView.getUsername());
        return fileInfoRepository.countFileInfosByUser(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void store(MultipartFile file, UserView owner){
        if (file == null || owner == null) {
            throw new RuntimeException("(Custom) Error -> file and owner can't be null");
        }
        try {
            String originalFileName = file.getOriginalFilename();
            String randomUUID = UUID.randomUUID().toString();
            Path tmpPath = this.rootLocation.toAbsolutePath();

            File tempFile = Files.createTempFile(tmpPath, randomUUID, "-" + originalFileName).toFile();
            file.transferTo(tempFile);
            String str = tempFile.getName();

            User user = mapperFacade.map(owner, User.class);
            fileInfoRepository.save(new FileInfo(originalFileName, tempFile.getName(), file.getSize(), user, 0));
        } catch (Exception e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Resource downloadFile(String tmpFilename) {
        if (StringUtils.isEmpty(tmpFilename)) {
            throw new RuntimeException("(Custom) Error -> tmpFilename can't be null");
        }
        try {
            FileInfo fileInfo = fileInfoRepository.findByTmpFilename(tmpFilename);
            Path file = rootLocation.resolve(tmpFilename);
            Resource resource = new UrlResource(file.toUri());
            if(resource.exists() || resource.isReadable()) {
                fileInfo.setDownloadCount(fileInfo.getDownloadCount() + 1);
                fileInfoRepository.save(fileInfo);
                return resource;
            }else{
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error! -> message = " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteFile(String tmpFilename) {
        if (StringUtils.isEmpty(tmpFilename)) {
            throw new RuntimeException("(Custom) Error -> tmpFilename can't be null");
        }
        Path path = rootLocation.resolve(tmpFilename);
        if(!path.toAbsolutePath().toFile().delete()) {
            throw new RuntimeException("Error -> can't delete file");
        }
        FileInfo fileInfo = fileInfoRepository.findByTmpFilename(tmpFilename);
        fileInfoRepository.delete(fileInfo);
    }
}
