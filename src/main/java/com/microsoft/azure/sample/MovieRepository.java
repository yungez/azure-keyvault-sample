package com.microsoft.azure.sample;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Created by yungez on 7/13/2017.
 */
@RepositoryRestResource(collectionResourceRel = "movies", path = "movies")
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
    /**
     * Provides find movie by id API.
     *
     * @param id movie id
     * @return movie object
     */
    Movie findOne(@Param("id") Long id);
}
