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
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

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
	@ApiResponses(value = { 
 	@ApiResponse(code = 200, message = "OK"),
 	@ApiResponse(code = 403, message = "Forbidden"),
	@ApiResponse(code = 409, message = "Conflict")})
	@PostMapping("/simian")
	public ResponseEntity<Void> getSimian(@RequestBody Dna dna){
		
		if(dnaService.hasNitrogenBase(dna.getDna())) {
			return dnaService.isSimian(dna.getDna()) == Boolean.TRUE ? ResponseEntity.status(200).build()
					: ResponseEntity.status(403).build();
		}else {
			return ResponseEntity.status(409).build();
		}
	}
}
