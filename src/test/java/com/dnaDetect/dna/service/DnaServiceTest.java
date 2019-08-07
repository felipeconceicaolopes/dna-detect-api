package com.dnaDetect.dna.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.dnaDetectApi.entity.Dna;
import com.dnaDetectApi.repository.DnaRepository;
import com.dnaDetectApi.service.DnaService;

@RunWith(MockitoJUnitRunner.class)
public class DnaServiceTest {
	
	@InjectMocks
	private DnaService dnaService;
	
	@Mock
	private DnaRepository dnaRepository;
	
	private static final String[] DNA_SIMIAN = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
	private static final String[] DNA_NOT_SIMIAN = {"ATGCGA", "CAGTGC", "TTATGT", "AGACCG", "CCACTA", "CACCTG"};
	private static final String[] DNA_HORIZONTALLY = {"ATGCGA", "CAGTGC", "TTATGT", "AGACCG", "CCACTA", "CCCCTG"};
	private static final String[] DNA_VERTICALLY = {"ATGCGA", "TAGTGC", "CTATGT", "CGACCG", "CCACTA", "CACCTG"};
	private static final String[] DNA_DIAGONALLY = {"ATGCGA", "TAGTGC", "CTATGT", "CGCACG", "CCACTA", "TACCTG"};
	private static final String[] DNA_ALL_DIRECTIONS = {"ATGCGA", "TAGTGC", "CTATGT", "CGCACG", "CCCCTA", "CACCTG"};
	
	private static final String[] DNA_NITROGEN_BASE_OK = {"CTGAGA", "CTGAGC", "TATTGT", "AGAGGG", "CCCCTA", "TCACTG"};
	private static final String[] DNA_NITROGEN_BASE_NOT_OK = {"CZGAGA", "CTVAGC", "TAKTGT", "AGAGGG", "CCCCTA", "TCACTG"};
	private static final String   RETURN_STATS = "{count_mutant_dna: 1, count_human_dna: 1, ratio: 1}";
	
	private char[][] dnaHorz;
	private char[][] dnaVert;
	private char[][] dnaDiag;
	private char[][] dnaAll;
	private List<Dna> listDnas = new ArrayList<Dna>();
	
	@Before
	public void init() {
		dnaHorz = dnaService.arraySequencesToMatrix(DNA_HORIZONTALLY);
		dnaVert = dnaService.arraySequencesToMatrix(DNA_VERTICALLY);
		dnaDiag = dnaService.arraySequencesToMatrix(DNA_DIAGONALLY);
		dnaAll =  dnaService.arraySequencesToMatrix(DNA_ALL_DIRECTIONS);
		
		Dna dna1 = new Dna();
		dna1.setId(new Long(1));
		dna1.setDna("CTGAGA-CTGAGC-TATTGT-AGAGGG-CCCCTA-TCACTG");
		
		Dna dna2 = new Dna();
		dna2.setId(new Long(2));
		dna2.setDna("ATGCGA-CAGTGC-TTATGT-AGACCG-CCACTA-CACCTG");
		
		listDnas.add(dna1);
		listDnas.add(dna2);
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
	
	@Test
	public void getStatsTest() {
		Mockito.when(dnaRepository.findAll()).thenReturn(listDnas);
		
		assertNotNull(dnaService.getStats());
		assertEquals(dnaService.getStats(), RETURN_STATS);
	}
}
