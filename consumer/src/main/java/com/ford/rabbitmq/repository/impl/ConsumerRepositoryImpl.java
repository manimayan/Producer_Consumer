package com.ford.rabbitmq.repository.impl;

import com.ford.rabbitmq.model.OutputData;
import com.ford.rabbitmq.repository.ConsumerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Slf4j
public class ConsumerRepositoryImpl implements ConsumerRepository{

	@PersistenceContext 
	private EntityManager entityManager;
	private OutputData output;

	@Override
	@Transactional
	public void saveOperation(OutputData output) {
		entityManager.persist(output);
		log.info("Saved data : "+"No1: "+output.getNumberOne()+" No2: "+ output.getNumberTwo()+" Op: "+output.getOperator());
	}

	@Override
	@Cacheable(value = "operationresult")
	public List<OutputData> getAllData() {
		TypedQuery<OutputData> query = entityManager.createQuery(" FROM OutputData ", OutputData.class); 
		return query.getResultList(); 
	}

	@Override
	public List<OutputData> getAllDataDB() {
		TypedQuery<OutputData> query = entityManager.createQuery(" FROM OutputData ", OutputData.class);
		return query.getResultList();
	}

	@Override
	@Cacheable(value = "operationresult", key="#id")
	public OutputData getDataByid(int id) {
		long convertedId = id;
		TypedQuery<OutputData> query =
				entityManager.createQuery("FROM OutputData where id=:id", OutputData.class)
						.setParameter("id", convertedId);
		return query.getSingleResult();
	}

	@Override
	@Transactional
	@CachePut(value="operationresult",key="#input.id")
	public OutputData updateData(OutputData input) {
		entityManager.merge(input);
		long convertedId = input.getId();
		TypedQuery<OutputData> query =
				entityManager.createQuery("FROM OutputData where id=:id", OutputData.class)
						.setParameter("id", convertedId);
		return query.getSingleResult();
	}

	@Override
	@Transactional
	@CacheEvict(value="operationresult",allEntries=false,key="#id")
	public Boolean deleteData(int id) {
		long convertedId = id;
		Query query =
				entityManager.createQuery(" delete from OutputData where id = :id");
		query.setParameter("id", convertedId);
		query.executeUpdate();
		return true;
	}
}
