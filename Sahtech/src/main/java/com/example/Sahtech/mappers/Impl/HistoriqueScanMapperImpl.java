package com.example.Sahtech.mappers.Impl;

import com.example.Sahtech.Dto.HistoriqueScanDto;
import com.example.Sahtech.entities.HistoriqueScan;
import com.example.Sahtech.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class HistoriqueScanMapperImpl implements Mapper<HistoriqueScan, HistoriqueScanDto> {
    private final ModelMapper modelMapper;

    public HistoriqueScanMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public HistoriqueScanDto mapTo(HistoriqueScan historiqueScan) {
        return modelMapper.map(historiqueScan, HistoriqueScanDto.class);
    }

    @Override
    public HistoriqueScan mapFrom(HistoriqueScanDto historiqueScanDto) {
        return modelMapper.map(historiqueScanDto, HistoriqueScan.class);
    }
} 