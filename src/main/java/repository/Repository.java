package repository;

import exception.RepositoryException;

import java.util.List;

public interface Repository<T> {

    void save(T entity) throws RepositoryException;

    List<T> findAll() throws RepositoryException;
}
