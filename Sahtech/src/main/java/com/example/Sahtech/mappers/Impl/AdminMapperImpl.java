package com.example.Sahtech.mappers.Impl;

import com.example.Sahtech.Dto.AdminDto;
import com.example.Sahtech.entities.Admin;
import com.example.Sahtech.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AdminMapperImpl implements Mapper<Admin, AdminDto> {

    private final ModelMapper modelMapper;

    public AdminMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public AdminDto mapTo(Admin admin) {
        return modelMapper.map(admin, AdminDto.class);
    }

    @Override
    public Admin mapFrom(AdminDto adminDto) {
        return modelMapper.map(adminDto, Admin.class);
    }
} 