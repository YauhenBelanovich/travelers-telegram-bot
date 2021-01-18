package com.gmail.yauhen2012.repository.impl;

import java.lang.invoke.MethodHandles;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import com.gmail.yauhen2012.repository.CityInformationRepository;
import com.gmail.yauhen2012.repository.model.CityInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository
public class CityInformationRepositoryImpl extends GenericDAOImpl<Long, CityInfo> implements CityInformationRepository {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public CityInfo findByCityName(String cityName) {
        String queryString = "FROM " + entityClass.getSimpleName() + " AS c" +
                " WHERE c.cityName=:cityName";
        Query query = entityManager.createQuery(queryString);
        query.setParameter("cityName", cityName);
        try {
            return (CityInfo) query.getSingleResult();
        } catch (NoResultException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

}
