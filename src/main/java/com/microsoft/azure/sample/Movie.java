package com.microsoft.azure.sample;

/**
 * Created by yungez on 7/13/2017.
 */

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
public class Movie {
    @Id
    private Long id;
    private String name;
    private String description;
    private Double rating;
    private String imageUri;

    /**
     * Get movie id.
     *
     * @return movie id
     */
    public Long getId() {
        return this.id;
    }

    /**
     * Set movie id.
     *
     * @param id movie id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Get movie name.
     *
     * @return movie name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set movie name.
     *
     * @param name movie name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get movie description.
     *
     * @return movie description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Set movie description.
     *
     * @param description movie description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get movie rating.
     *
     * @return movie rating
     */
    public Double getRating() {
        return this.rating;
    }

    /**
     * Set movie rating.
     *
     * @param rating movie rating
     */
    public void setRating(Double rating) {
        this.rating = rating;
    }

    /**
     * Get image uri.
     *
     * @return image uri
     */
    public String getImageUri() {
        return this.imageUri;
    }

    /**
     * Set image uri.
     *
     * @param imageUri image uri
     */
    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
