package com.ispan.eeit188_final.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ispan.eeit188_final.model.HousePostulate;
import com.ispan.eeit188_final.model.composite.HousePostulateId;
import com.ispan.eeit188_final.service.HousePostulateService;

@RestController
@RequestMapping("/housePostulate")
public class HousePostulateController {

	@Autowired
	private HousePostulateService housePostulateService;

	@GetMapping("/{houseId}/{postulateId}")
	public ResponseEntity<HousePostulate> findById(@PathVariable("houseId") String houseId, @PathVariable("postulateId") String postulateId) {
		if (houseId != null && houseId.length() != 0 && postulateId != null && postulateId.length() != 0) {
			UUID houseUuid = UUID.fromString(houseId);
			UUID postulateUuid = UUID.fromString(postulateId);
			HousePostulateId id = new HousePostulateId();
			id.setHouseId(houseUuid);
			id.setPostulateId(postulateUuid);
			
			HousePostulate housePostulate = housePostulateService.findById(id);
			if (housePostulate != null) {
				return ResponseEntity.ok(housePostulate);
			}
		}
		return ResponseEntity.noContent().build();
	}

//	@GetMapping("/findAll")
//	public ResponseEntity<Page<HousePostulate>> findAll(@RequestBody String json) {
//		Page<HousePostulate> housePostulates = housePostulateService.findAll(json);
//		return ResponseEntity.ok(housePostulates);
//	}
	
	@GetMapping("/findAll")
	public ResponseEntity<List<HousePostulate>> findAll(@RequestBody String json) {
		List<HousePostulate> housePostulates = housePostulateService.findAll();
		return ResponseEntity.ok(housePostulates);
	}

	@PostMapping("/")
	public ResponseEntity<HousePostulate> create(@RequestBody String json) {
		HousePostulate create = housePostulateService.create(json);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{houseId}/{postulateId}").buildAndExpand(create.getId().getHouseId(),create.getId().getPostulateId())
				.toUri();
		return ResponseEntity.created(location).body(create);
	}

//	@PutMapping("/{id}")
//	public ResponseEntity<HousePostulate> update(@PathVariable String id, @RequestBody HousePostulate housePostulate) {
//		if (id != null && id.length() != 0) {
//			UUID uuid = UUID.fromString(id);
//			housePostulate.setId(uuid);
//			HousePostulate modify = housePostulateService.update(housePostulate);
//			if (modify != null) {
//				return ResponseEntity.ok(modify);
//
//			}
//		}
//		return ResponseEntity.notFound().build();
//	}
//
//	@DeleteMapping("/{id}")
//	public ResponseEntity<?> deleteById(@PathVariable String id) {
//		if (id != null && id.length() != 0) {
//			UUID uuid = UUID.fromString(id);
//			housePostulateService.deleteById(uuid);
//			return ResponseEntity.noContent().build();
//		}
//		return ResponseEntity.notFound().build();
//	}

}