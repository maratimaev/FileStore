package ru.bellintegrator.entity.mapper;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.stereotype.Component;
import ru.bellintegrator.dto.FileInfoView;
import ru.bellintegrator.dto.UserView;
import ru.bellintegrator.entity.FileInfo;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * {@inheritDoc}
 */
@Component
public class MapperFacadeImpl implements MapperFacade {

    private MapperFactory mapperFactory;

    @PostConstruct
    private void init(){
        mapperFactory = new DefaultMapperFactory.Builder().build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <S, D> D map(S sourceObject, Class<D> destinationClass) {
        return mapperFactory.getMapperFacade().map(sourceObject, destinationClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <S, D> void map(S sourceObject, D destinationObject) {
        mapperFactory.getMapperFacade().map(sourceObject, destinationObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <S, D> List<D> mapAsList(Iterable<S> source, Class<D> destinationClass) {
        return mapperFactory.getMapperFacade().mapAsList(source, destinationClass);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FileInfoView> mapToFileInfoViewList(List<FileInfo> fileInfoList){
        List<FileInfoView> fivList = new ArrayList<>();
        for(FileInfo fi : fileInfoList){
            FileInfoView fiv = new FileInfoView();
            mapperFactory.getMapperFacade(FileInfo.class, FileInfoView.class).map(fi, fiv);
            fiv.setUserView(mapperFactory.getMapperFacade().map(fi.getUser(), UserView.class));
            fivList.add(fiv);
        }
        return fivList;
    }
}
