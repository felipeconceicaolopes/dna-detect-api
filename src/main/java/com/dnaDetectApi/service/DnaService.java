package com.dnaDetectApi.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dnaDetectApi.dto.DnaDto;
import com.dnaDetectApi.entity.Dna;
import com.dnaDetectApi.repository.DnaRepository;


@Service
public class DnaService {
	
	@Autowired
	private DnaRepository dnaRepository;
	
	public Boolean isSimian(String[] dna) {
		
		for(String base : dna) {
			if(base.length() != 6)
				return Boolean.FALSE;
		}
		
		char[][] matrixDna = arraySequencesToMatrix(dna);
		if(matrixDna.length == 6) {
			if(validatingHorizontallyIfSimian(matrixDna))
				return Boolean.TRUE;
			
			if(validatingVerticallyIfSimian(matrixDna))
				return Boolean.TRUE;
			
			if(validatingDiagonallyIfSimian(matrixDna))
				return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}
	
	public Boolean hasNitrogenBase(String[] dna) {
		Pattern pattern = Pattern.compile("[ATCG]");
		
		for(String base : dna) {
			Matcher matcher = pattern.matcher(base);
			
			int count = 0;
			while(matcher.find()) {
				count++;
			}
			
			if(count < base.length())
				return Boolean.FALSE;
		}
		
		return Boolean.TRUE;
	}

	public char[][] arraySequencesToMatrix(String[] dna) {
		StringBuilder baseM = new StringBuilder();
		char[][] m = new char[dna.length][dna.length];
		int count = 0;
		
		for(String base : dna) {
			baseM.append(base);
		}
		
		for(int i = 0; i < dna.length; i++) {
			for(int z = 0; z < dna.length; z++) {
				m[i][z] = baseM.charAt(count);
				count++;
			}
		}
		
		return m;
	}
	
	public Boolean validatingHorizontallyIfSimian(char dna[][]) {
		
		for(int line = 0; line < dna.length; line++) {
			int count = 0;
			char letraBase = dna[line][0];
			for(int column = 1; column < dna.length; column++) {
				if(letraBase == dna[line][column]) {
					count++;
					if(count >= 3) {	
						return Boolean.TRUE;
					}
				}else {
					letraBase = dna[line][column];
					count = 0;
				}
			}
		}
		
		return Boolean.FALSE;
	}
	
	public Boolean validatingVerticallyIfSimian(char dna[][]) {
		
		for(int column = 0; column < dna.length; column++) {
			int count = 0;
			char letraBase = dna[0][column];
			for(int line = 1; line < dna.length; line++) {
				if(letraBase == dna[line][column]) {
					count++;
					if(count >= 3) {	
						return Boolean.TRUE;
					}
				}else {
					letraBase = dna[line][column];
					count = 0;
				}	
			}	
		}
		
		return Boolean.FALSE;
	}
	
	public Boolean validatingDiagonallyIfSimian(char dna[][]) {
				
		for(int line = 0 ; line < dna.length ; ++line){
			int count = 0;
			char letraBase = dna[line][0];
		    for(int column = 1 ; column < dna.length - line ; ++column){
		        if(letraBase == dna[column][column+line]) {
					count++;
					if(count >= 3) {	
						return Boolean.TRUE;
					}
				}else {
					letraBase = dna[column][column+line];
					count = 0;
				}
		    }
		}
		
		return Boolean.FALSE;
	}	
	
	public String getStats() {
		List<Dna> listDnas = dnaRepository.findAll();
		
		Long mutant = listDnas.stream().filter(dna -> isSimian(stringToArray(dna.getDna()))).count();
		Long human = listDnas.stream().filter(dna -> !isSimian(stringToArray(dna.getDna()))).count();
		Long ratio = new Long(0);
		
		if(mutant > 0 && human > 0)
			ratio = mutant/human;
		
		return "{count_mutant_dna: "+mutant+", count_human_dna: "+human+", ratio: "+ratio+"}";
	}
	
	public void save(DnaDto dnaDto) {
		Dna dna = dtoForEntity(dnaDto);
		Dna dna2 = dnaRepository.findByDna(dna.getDna()); 
		
		if(dna2 != null) {
			if(dna2.getDna() != null) {
				if(!dna.getDna().equals(dnaRepository.findByDna(dna.getDna()).getDna()))
					dnaRepository.save(dna);	
			}
		}else {
			dnaRepository.save(dna);
		}
			
	}
	
	private String[] stringToArray(String dnaString) {
		
		String[] dnaArray = dnaString.split("-");
		
		return dnaArray;
	}
	
	private Dna dtoForEntity(DnaDto dto) {
		Dna entity = new Dna();
		StringBuilder baseSeq = new StringBuilder();
		
		for(String base : dto.getDna()) {
			baseSeq.append(base).append("-");
		}
		baseSeq.deleteCharAt(baseSeq.length() - 1);
		
		entity.setDna(baseSeq.toString());
		
		return entity;
	}
}
