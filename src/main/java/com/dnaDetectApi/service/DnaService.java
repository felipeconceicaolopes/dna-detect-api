package com.dnaDetectApi.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;


@Service
public class DnaService {
	
	public Boolean isSimian(String[] dna) {
		
		if(hasNitrogenBase(dna)) {
			char[][] matrixDna = arraySequencesToMatrix(dna);
			
			if(validatingHorizontallyIfSimian(matrixDna))
				return Boolean.TRUE;
			
			if(validatingVerticallyIfSimian(matrixDna))
				return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}
	
	private Boolean hasNitrogenBase(String[] dna) {
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

	private static char[][] arraySequencesToMatrix(String[] dna) {
		StringBuilder baseM = new StringBuilder();
		char[][] m = new char[6][6];
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
	
	private static Boolean validatingHorizontallyIfSimian(char dna[][]) {

		for(int i = 0; i < dna.length; i++) {
			int count = 0;
			StringBuilder teste = new StringBuilder();
			for(int z = 0; z < dna.length - 1; z++) {
				int x = z + 1;
				int y = 0;
				if(z > dna.length)
					y = z - 1;
				
				if(dna[i][z] == dna[i][x] && dna[i][y] == dna[i][x]) {
					count++;
					teste.append(dna[i][z]);
				}
			}
			if(count >= 3) {	
				return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;
	}
	
	private static Boolean validatingVerticallyIfSimian(char dna[][]) {
		
		for(int z = 0; z < dna.length; z++) {
			int count = 0;
			StringBuilder teste = new StringBuilder();
			for(int i = 0; i < dna.length - 1; i++) {
				int x = i + 1;
				int y = 0;
				if(i > dna.length)
					y = i - 1;
				
				if(dna[i][z] == dna[x][z] && dna[y][z] == dna[x][z]) {
					count++;
					teste.append(dna[i][z]);
				}
			}
			if(count >= 3) {
				return Boolean.TRUE;
			}			
		}
		
		return Boolean.FALSE;
	}
}
