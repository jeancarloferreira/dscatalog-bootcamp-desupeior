package com.project1.dscatalog.services;

import com.project1.dscatalog.dto.CategoryDTO;
import com.project1.dscatalog.dto.ProductDTO;
import com.project1.dscatalog.entities.Category;
import com.project1.dscatalog.entities.Product;
import com.project1.dscatalog.repositories.CategoryRepository;
import com.project1.dscatalog.repositories.ProductRepository;
import com.project1.dscatalog.services.exceptions.DatabaseException;
import com.project1.dscatalog.services.exceptions.ResourceEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAllPaged(Pageable pageable){
        Page<Product> list = repository.findAll(pageable);
        Page<ProductDTO> listDto = list.map(x -> new ProductDTO(x));
        return listDto;
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Optional<Product> obj = repository.findById(id);
        Product entity = obj.orElseThrow(() -> new ResourceEntityNotFoundException("Entity not found"));
        return new ProductDTO(entity, entity.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getOne(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);

            return new ProductDTO(entity);
        } catch (EntityNotFoundException e) {
            throw new ResourceEntityNotFoundException("Id not found "+ id);
        }
    }

    public void delete(Long id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceEntityNotFoundException("Id not found "+ id);
        } catch (DataIntegrityViolationException e){
            throw new DatabaseException("Integraty violation");
        }
    }

    private void copyDtoToEntity(ProductDTO dto, Product entity){
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDate(dto.getDate());
        entity.setImgUrl(dto.getImgUrl());
        entity.setPrice(dto.getPrice());

        entity.getCategories().clear();
        for (CategoryDTO catDto : dto.getCategories()){
            try {
                Category category = categoryRepository.getOne(catDto.getId());
                entity.getCategories().add(category);
            } catch (EntityNotFoundException e) {
                throw new ResourceEntityNotFoundException("Category id not found "+ catDto.getId());
            }

        }
    }
}
