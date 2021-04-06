package com.shop.shop.common;

import org.modelmapper.ModelMapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


public class ModelMapperUtils {

    private final static ModelMapper MODEL_MAPPER = new ModelMapper();

    private ModelMapperUtils() {
    }

    public static <D, T> D map(final T entity, final Class<D> outClass) {
        return MODEL_MAPPER.map(entity, outClass);
    }

    public static <D, T> List<D> mapAll(final Collection<T> entityList, final Class<D> outCLass) {
        return entityList.stream().map(entity -> map(entity, outCLass)).collect(Collectors.toList());
    }

    public static <S, D> D map(final S source, final D destination) {
        MODEL_MAPPER.map(source, destination);
        return destination;
    }
}