package com.ispan.eeit188_final.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ispan.eeit188_final.model.Postulate;
import com.ispan.eeit188_final.repository.PostulateRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PostulateService {

	@Autowired
	private PostulateRepository postulateRepository;

	public Postulate findById(UUID id) {
		if (id != null) {
			Optional<Postulate> optional = postulateRepository.findById(id);
			if (optional.isPresent()) {
				return optional.get();
			}
		}
		return null;
	}

	public List<Postulate> findAll() {
		return postulateRepository.findAll();
	}

	public void deleteById(UUID id) {
		if (id != null) {
			postulateRepository.deleteById(id);
		}
	}

	public void deleteAll() {
		postulateRepository.deleteAll();
	}

	public Postulate create(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			String postulate = obj.isNull("postulate") ? null : obj.getString("postulate");

			Postulate insert = new Postulate();
			insert.setPostulate(postulate);

			return postulateRepository.save(insert);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Postulate update(String json) {
		try {
			JSONObject obj = new JSONObject(json);
			UUID id = obj.isNull("id") ? null : UUID.fromString(obj.getString(json));
			Postulate dbData = findById(id);
			if (dbData != null) {
				dbData.setPostulate(obj.isNull("postulate") ? null : obj.getString("postulate"));
				return dbData;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
