package com.chn.exam.common.mapper;

import org.springframework.data.domain.Page;

import com.chn.exam.common.dto.PagedResponseDTO;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PageMapper {
    public <T> PagedResponseDTO<T> toPagedResponse(Page<T> page) {
        return new PagedResponseDTO<>(
                page.getContent(),
                page.getTotalElements(),
                page.getNumber(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious());
    }
}
