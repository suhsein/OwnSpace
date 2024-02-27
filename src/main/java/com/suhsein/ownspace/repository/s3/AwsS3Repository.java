package com.suhsein.ownspace.repository.s3;

import com.suhsein.ownspace.domain.s3.AwsS3;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AwsS3Repository {
    private final EntityManager em;


    @Transactional
    public void save(AwsS3 awsS3) {
        em.persist(awsS3);
    }

    public AwsS3 findOne(Long id) {
        return em.find(AwsS3.class, id);
    }

    public List<AwsS3> findAll(){
        return em.createQuery("select a from AwsS3 a", AwsS3.class)
                .getResultList();
    }

    public void removeAll(List<Long> ids) {
        em.createQuery("delete from AwsS3 a where a.id in :ids")
                .setParameter("ids", ids)
                .executeUpdate();
    }

    public void remove(Long id){
        em.remove(findOne(id));
    }
}
