package com.dnaDetectApi.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;


@Service
public class DnaService {
	
	public Boolean isSimian(String[] dna) {
		return hasNitrogenBase(dna);
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
}
