package com.dnaDetect.dna.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dnaDetectApi.DnaDetectApiApplication;
import com.dnaDetectApi.service.DnaService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DnaDetectApiApplication.class)
public class DnaServiceTest {
	
	@Autowired
	private DnaService dnaService;
	
	private static final String[] DNA_SIMIAN = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
	private static final String[] DNA_NOT_SIMIAN = {"ATGCGA", "CAGTGC", "TTATGT", "AGACCG", "CCACTA", "CACCTG"};
	private static final String[] DNA_HORIZONTALLY = {"ATGCGA", "CAGTGC", "TTATGT", "AGACCG", "CCACTA", "CCCCTG"};
	private static final String[] DNA_VERTICALLY = {"ATGCGA", "TAGTGC", "CTATGT", "CGACCG", "CCACTA", "CACCTG"};
	private static final String[] DNA_DIAGONALLY = {"ATGCGA", "TAGTGC", "CTATGT", "CGCACG", "CCACTA", "TACCTG"};
	private static final String[] DNA_ALL_DIRECTIONS = {"ATGCGA", "TAGTGC", "CTATGT", "CGCACG", "CCCCTA", "CACCTG"};
	
	private static final String[] DNA_NITROGEN_BASE_OK = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
	private static final String[] DNA_NITROGEN_BASE_NOT_OK = {"CZGAGA", "CTVAGC", "TAKTGT", "AGAGGG", "CCCCTA", "TCACTG"};
	
	private char[][] dnaHorz;
	private char[][] dnaVert;
	private char[][] dnaDiag;
	private char[][] dnaAll;
	
	@Before
	public void init() {
		dnaHorz = dnaService.arraySequencesToMatrix(DNA_HORIZONTALLY);
		dnaVert = dnaService.arraySequencesToMatrix(DNA_VERTICALLY);
		dnaDiag = dnaService.arraySequencesToMatrix(DNA_DIAGONALLY);
		dnaAll =  dnaService.arraySequencesToMatrix(DNA_ALL_DIRECTIONS);
	}
	
	@Test
	public void validSimian_ifDnaIsSimian_true() {		
		assertTrue(dnaService.isSimian(DNA_SIMIAN));
	}
	
	@Test
	public void validNotSimian_ifDnaNotSimian_false() {		
		assertFalse(dnaService.isSimian(DNA_NOT_SIMIAN));
	}
	
	@Test
	public void validSimian_ifOnlyHorizontallyMatch_true() {
		assertTrue(dnaService.isSimian(DNA_HORIZONTALLY));
	}
	
	@Test
	public void validSimian_ifOnlyVertcallyMatch_true() {
		assertTrue(dnaService.isSimian(DNA_VERTICALLY));
	}
	
	@Test
	public void validSimian_ifOnlyDiagonallyMatch_true() {
		assertTrue(dnaService.isSimian(DNA_DIAGONALLY));
	}
	
	@Test
	public void validHorizontally_ifHorizontallyMatch_true() {
		
		assertTrue(dnaService.validatingHorizontallyIfSimian(dnaHorz));
		assertFalse(dnaService.validatingHorizontallyIfSimian(dnaVert));
		assertFalse(dnaService.validatingHorizontallyIfSimian(dnaDiag));
	}
	
	@Test
	public void validVertically_ifVerticallyMatch_true() {
		
		assertTrue(dnaService.validatingVerticallyIfSimian(dnaVert));
		assertFalse(dnaService.validatingVerticallyIfSimian(dnaHorz));
		assertFalse(dnaService.validatingVerticallyIfSimian(dnaDiag));
	}
	
	@Test
	public void validDiagnally_ifDiagnallyMatch_true() {
		
		assertTrue(dnaService.validatingDiagonallyIfSimian(dnaDiag));
		assertFalse(dnaService.validatingDiagonallyIfSimian(dnaHorz));
		assertFalse(dnaService.validatingDiagonallyIfSimian(dnaVert));
	}
	
	@Test
	public void validAllMatchs_whenAllMatchs_true() {
		assertTrue(dnaService.validatingHorizontallyIfSimian(dnaAll));
		assertTrue(dnaService.validatingVerticallyIfSimian(dnaAll));
		assertTrue(dnaService.validatingDiagonallyIfSimian(dnaAll));
	}
	
	@Test
	public void validationArraySequencesToMatrixTest() {
		char matrix[][] = dnaService.arraySequencesToMatrix(DNA_SIMIAN);
		
		assertNotEquals(DNA_SIMIAN.toString().charAt(0), matrix[0][0]);
		
		StringBuilder dnaSeq = new StringBuilder();
		for(String base : DNA_SIMIAN) {
			dnaSeq.append(base);
		}
		
		assertEquals(dnaSeq.toString().charAt(0), matrix[0][0]);
	}
	
	@Test
	public void validationNitrogenBase_whenNitrogenValid_true() {
		
		assertTrue(dnaService.hasNitrogenBase(DNA_NITROGEN_BASE_OK));
		assertFalse(dnaService.hasNitrogenBase(DNA_NITROGEN_BASE_NOT_OK));
	}
}
