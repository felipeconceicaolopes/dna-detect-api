package com.dnaDetectApi.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;


@Service
public class DnaService {
	
	public Boolean isSimian(String[] dna) {
		
		char[][] matrixDna = arraySequencesToMatrix(dna);
		
		if(validatingHorizontallyIfSimian(matrixDna))
			return Boolean.TRUE;
		
		if(validatingVerticallyIfSimian(matrixDna))
			return Boolean.TRUE;
		
		if(validatingDiagonallyIfSimian(matrixDna))
			return Boolean.TRUE;
		
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
}
