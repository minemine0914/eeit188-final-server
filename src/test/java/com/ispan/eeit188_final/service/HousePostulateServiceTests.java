package com.ispan.eeit188_final.service;

import java.util.List;
import java.util.UUID;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ispan.eeit188_final.model.HousePostulate;
import com.ispan.eeit188_final.model.composite.HousePostulateId;

@SpringBootTest
public class HousePostulateServiceTests {

	@Autowired
	private HousePostulateService housePostulateService;
	
	// @Test
	public void testCreate() {
		
		Object objNull = null;
//		JSONObject json = new JSONObject().put("houseId",UUID.randomUUID()).put("postulateId",UUID.randomUUID());
		JSONObject json = new JSONObject().put("houseId",UUID.fromString("02DFD239-CEC9-4BE4-B041-A391A2D54EC7")).put("postulateId",UUID.fromString("1019B625-4A03-47D1-976A-4C1194AB6920"));
//		JSONObject json = new JSONObject().put("houseId",UUID.randomUUID()).put("postulateId",UUID.fromString("1019B625-4A03-47D1-976A-4C1194AB6920"));
//		JSONObject json = new JSONObject().put("houseId",objNull).put("postulateId",UUID.fromString("1019B625-4A03-47D1-976A-4C1194AB6920"));
		housePostulateService.create(json.toString());
		System.out.println();
	}
	
	// @Test
	public void testFindAll() {
		List<HousePostulate> list = housePostulateService.findAll();
		for (HousePostulate housePostulate : list) {
			System.out.println(housePostulate);
		}
	}
	
	// @Test
	public void testFindById() {
		JSONObject json = new JSONObject().put("houseId",UUID.fromString("02DFD239-CEC9-4BE4-B041-A391A2D54EC7")).put("postulateId",UUID.fromString("1019B625-4A03-47D1-976A-4C1194AB6920"));
		UUID houseUuid = (UUID)json.get("houseId");
		UUID postulateUuid = (UUID)json.get("postulateId");
		HousePostulateId id = new HousePostulateId();
		id.setHouseId(houseUuid);
		id.setPostulateId(postulateUuid);
		
		HousePostulate housePostulate = housePostulateService.findById(id);
		System.out.println(housePostulate);
	}
	

////	@Test
//	public void testFindAllPage() {
//		 Integer pageNum = 1;
//		 Integer pageSize = 3;
//		 Boolean desc = false;
//		 String orderBy = "id";
//		
//		JSONObject obj = new JSONObject()
//				.put("pageNum", pageNum)
//				.put("pageSize", pageSize)
//				.put("desc", desc)
//				.put("orderBy", orderBy);
//
//		String json = obj.toString();
//		Page<HousePostulate> page = housePostulateService.findAll(json);
//
//		System.out.printf("pageNum=%d, pageSize=%d, desc=%b, orderBy=%s\r\n", pageNum, pageSize, desc,
//				orderBy);
//		for (HousePostulate housePostulate : page) {
//			System.out.println(housePostulate.getId());
//		}
//	}
}
