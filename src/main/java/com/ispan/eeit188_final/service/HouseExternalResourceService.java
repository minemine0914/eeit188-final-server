package com.ispan.eeit188_final.service;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ispan.eeit188_final.dto.HouseExternalResourceDTO;
import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.HouseExternalResource;
import com.ispan.eeit188_final.repository.HouseExternalResourceRepository;
import com.ispan.eeit188_final.repository.HouseRepository;

@Service
public class HouseExternalResourceService {
    // 預設值
    private static final Integer PAGEABLE_DEFAULT_PAGE = 0;
    private static final Integer PAGEABLE_DEFAULT_LIMIT = 50;

    @Autowired
    private HouseExternalResourceRepository houseExternalResourceRepo;
    @Autowired
    private HouseRepository houseRepo;

    public List<HouseExternalResource> uploadMultipleFile(UUID houseId, MultipartFile[] files) throws IOException {
        if (houseId != null && files != null) {
            Optional<House> findHouse = houseRepo.findById(houseId);
            if (findHouse.isPresent()) {
                List<HouseExternalResource> houseExternalResources = new ArrayList<HouseExternalResource>();
                for (MultipartFile file : files) {
                    HouseExternalResource houseExternalResource = HouseExternalResource.builder()
                            .image(file.getBytes())
                            .house(findHouse.get())
                            .type(file.getContentType())
                            .build();
                    houseExternalResources.add(houseExternalResource);
                }
                return houseExternalResourceRepo.saveAll(houseExternalResources);
            }
        }
        return null;
    }

    public HouseExternalResource findById(UUID id) {
        Optional<HouseExternalResource> optional = houseExternalResourceRepo.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        }
        return null;

    }

    public Page<HouseExternalResource> findByHouseId(UUID houseId, HouseExternalResourceDTO dto) {
        // 頁數 限制 排序
        Integer page = Optional.ofNullable(dto.getPage()).orElse(PAGEABLE_DEFAULT_PAGE);
        Integer limit = Optional.ofNullable(dto.getLimit()).orElse(PAGEABLE_DEFAULT_LIMIT);
        Boolean dir = Optional.ofNullable(dto.getDir()).orElse(false);
        String order = Optional.ofNullable(dto.getOrder()).orElse(null);
        // 是否排序
        Sort sort = (order != null) ? Sort.by(dir ? Direction.DESC : Direction.ASC, order) : Sort.unsorted();
        if ( houseId != null ) {
            Optional<House> findHouse = houseRepo.findById(houseId);
            if ( findHouse.isPresent() ) {
                return houseExternalResourceRepo.findByHouse(findHouse.get(), PageRequest.of(page, limit, sort));
            }
        }
        return null;

    }

    public Boolean deleteById(UUID id) {
        if ( houseExternalResourceRepo.existsById(id) ) {
            houseExternalResourceRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public HouseExternalResource modify(HouseExternalResource her) {
        return houseExternalResourceRepo.save(her);
    }


    public Page<HouseExternalResource> findAllHer(int pageNumber, int pageSize) {
        Pageable p = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, "createdAt"));
        Page<HouseExternalResource> page = houseExternalResourceRepo.findAll(p);
        return page;
    }
}