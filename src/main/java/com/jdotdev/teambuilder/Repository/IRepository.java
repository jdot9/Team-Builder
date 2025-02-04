package com.jdotdev.teambuilder.Repository;

import java.util.List;
import java.util.Optional;

public interface IRepository<T> 
{
    int save(T entity);
    Optional<T> findById(Integer id);
    List<T> findAll();
    int update(T entity, Integer id);
    int deleteById(Integer id);   
}
