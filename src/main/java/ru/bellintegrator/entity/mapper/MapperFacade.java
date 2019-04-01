package ru.bellintegrator.entity.mapper;

import ru.bellintegrator.dto.FileInfoView;
import ru.bellintegrator.entity.FileInfo;

import java.util.List;

/**
 * Фасад для преобразования между моделями БД и фронта
 */
public interface MapperFacade {

    /**
     * Преобразование sourceObject в экземпляр класса destinationClass
     *
     * @param sourceObject     исходный объект
     * @param destinationClass класс, в который надо преобразовать объект
     * @param <S>              тип исходного объекта
     * @param <D>              тип объекта, к который надо преобразовать исходный объект
     * @return экземпляр класса D с данными из sourceObject
     */
    <S, D> D map(S sourceObject, Class<D> destinationClass);

    /**
     * Запись занных из sourceObject в destinationObject
     *
     * @param sourceObject
     * @param destinationObject
     * @param <S>
     * @param <D>
     */
    <S, D> void map(S sourceObject, D destinationObject);

    /**
     * Преобразование коллекции оъектов
     *
     * @param source            исходный объект
     * @param destinationClass  класс, в который надо преобразовать объект
     * @param <S>               тип исходного объекта
     * @param <D>               тип объекта, к которому надо преобразовать исходный объект
     * @return                  список объектов класса D
     */
    <S, D> List<D> mapAsList(Iterable<S> source, Class<D> destinationClass);

    /** Преобразование списка entity FileInfo в список dto FileInfo
     * @param fileInfoList список entity
     * @return список dto
     */
    List<FileInfoView> mapToFileInfoViewList(List<FileInfo> fileInfoList);
}
