package com.ispan.eeit188_final.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ispan.eeit188_final.dto.PostulateDTO;
import com.ispan.eeit188_final.model.House;
import com.ispan.eeit188_final.model.Postulate;
import com.ispan.eeit188_final.service.PostulateService;

@RestController
@RequestMapping("/postulate")
public class PostulateController {

	@Autowired
	private PostulateService postulateService;

	@GetMapping("/{id}")
	public ResponseEntity<Postulate> findById(@PathVariable String id) {
		if (id != null && id.length() != 0) {
			UUID uuid = UUID.fromString(id);
			Postulate postulate = postulateService.findById(uuid);
			if (postulate != null) {
				return ResponseEntity.ok(postulate);
			}
		}
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/findAll")
	public ResponseEntity<Page<Postulate>> findAll(@ModelAttribute PostulateDTO postulateDTO) {
		Page<Postulate> postulates = postulateService.findAll(postulateDTO);
		return ResponseEntity.ok(postulates);
	}

	@PostMapping("/")
	public ResponseEntity<Postulate> create(@RequestBody PostulateDTO postulateDTO) {
		Postulate postulate = Postulate.builder()
				.name(postulateDTO.getName())
				.icon(postulateDTO.getIcon())
				.build();
		Postulate create = postulateService.create(postulate);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(create.getId())
				.toUri();
		return ResponseEntity.created(location).body(create);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Postulate> update(@PathVariable String id, @RequestBody Postulate postulate) {
		if (id != null && id.length() != 0) {
			UUID uuid = UUID.fromString(id);
			postulate.setId(uuid);
			Postulate modify = postulateService.update(postulate);
			if (modify != null) {
				return ResponseEntity.ok(modify);

			}
		}
		return ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable String id) {
		if (id != null && id.length() != 0) {
			UUID uuid = UUID.fromString(id);
			postulateService.deleteById(uuid);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

}