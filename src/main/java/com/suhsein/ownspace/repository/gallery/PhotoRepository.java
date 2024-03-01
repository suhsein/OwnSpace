package com.suhsein.ownspace.repository.gallery;

import com.suhsein.ownspace.domain.gallery.Photo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PhotoRepository {
    private final EntityManager em;

    public void save(Photo photo) {
        em.persist(photo);
    }

    public Photo findOne(Long id) {
        return em.find(Photo.class, id);
    }

    public List<Photo> findAll() {
        return em.createQuery("select p from Photo p order by p.id desc", Photo.class)
                .getResultList();
    }

    public void remove(Long id) {
        em.remove(findOne(id));
    }

}
