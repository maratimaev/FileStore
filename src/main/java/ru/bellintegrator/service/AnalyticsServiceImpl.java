package ru.bellintegrator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bellintegrator.dto.Analytics;
import ru.bellintegrator.dto.UserView;

import java.util.ArrayList;
import java.util.List;

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
        List<Analytics> statistics = new ArrayList<>();
        for(UserView uv : userService.getUserViewList()) {
            statistics.add(new Analytics(uv, fileService.countFiles(uv)));
        }
        return statistics;
    }
}
