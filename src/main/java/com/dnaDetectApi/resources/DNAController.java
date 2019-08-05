package com.dnaDetectApi.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dnaDetectApi.entity.Dna;
import com.dnaDetectApi.service.DnaService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController()
@RequestMapping("/")
@Api(value="DNA Detect API")
public class DNAController {
	
	@Autowired
	private DnaService dnaService;
	
	@ApiOperation(value = "Find DNA teste")
	@GetMapping
	public ResponseEntity<?> getDNA() {
		return ResponseEntity.ok("teste DNA");
	}
	
	@ApiOperation(value = "Comparation Simian")
	@PostMapping("/simian")
	public ResponseEntity<Boolean> getSimian(@RequestBody Dna dna){
		return ResponseEntity.ok(dnaService.isSimian(dna.getDna()));
	}
}
