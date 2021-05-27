package com.project1.dscatalog.services;

import com.project1.dscatalog.dto.CategoryDTO;
import com.project1.dscatalog.entities.Category;
import com.project1.dscatalog.repositories.CategoryRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAllPaged(Pageable pageable){
        Page<Category> list = repository.findAll(pageable);
        Page<CategoryDTO> listDto = list.map(x -> new CategoryDTO(x));
        return listDto;
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = repository.findById(id);
        Category entity = obj.orElseThrow(() -> new ResourceEntityNotFoundException("Entity not found"));
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
         Category entity = new Category();
         entity.setName(dto.getName());
         entity = repository.save(entity);
         return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        try {
            Category entity = repository.getOne(id);
            entity.setName(dto.getName());
            entity = repository.save(entity);

            return new CategoryDTO(entity);
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
}
