package ru.bellintegrator.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bellintegrator.dto.Analytics;
import ru.bellintegrator.service.AnalyticsService;
import ru.bellintegrator.service.FileService;
import ru.bellintegrator.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * {@inheritDoc}
 */
@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    /**
     * {@inheritDoc}
     */
    @Autowired
    private UserService userService;

    /**
     * {@inheritDoc}
     */
    @Autowired
    private FileService fileService;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Analytics> getAllUserStat() {
        return userService.getUserViewList().stream()
                .map(uv -> new Analytics(uv, fileService.countFiles(uv)))
                .collect(Collectors.toList());
    }
}
