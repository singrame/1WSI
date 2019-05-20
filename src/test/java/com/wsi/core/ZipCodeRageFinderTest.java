package com.wsi.core;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.Test;

import com.wsi.data.ZipCodeRageComparator;
import com.wsi.data.ZipCodeRange;

public class ZipCodeRageFinderTest {
	private static final Logger logger = LogManager.getLogger(ZipCodeRageFinderTest.class);
	private ZipCodeRageComparator comparator = new ZipCodeRageComparator();
	static {
		LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
		File file = new File("src/test/resources/log4j2.xml");
		context.setConfigLocation(file.toURI());
	}

	@Test
	public void testCase1() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("entering testCase1()");
		}
		String input = "[94133,94133] [94200,94299] [94600,94699]";
		ZipCodeRageFinder codeRageFinder = new ZipCodeRageFinder();
		Set<ZipCodeRange> actualOutput = codeRageFinder.findMinumRanges(input);
		Set<ZipCodeRange> expectedOutput = new TreeSet<>(comparator);
		expectedOutput.add(new ZipCodeRange(94133, 94133));
		expectedOutput.add(new ZipCodeRange(94200, 94299));
		expectedOutput.add(new ZipCodeRange(94600, 94699));
		assertEquals(actualOutput.size(), expectedOutput.size());
		assertEquals(actualOutput.stream().map(x -> x.toString()).collect(Collectors.joining(" ")),
				expectedOutput.stream().map(x -> x.toString()).collect(Collectors.joining(" ")));
		if (logger.isDebugEnabled()) {
			logger.debug("exiting testCase1()");
		}
	}

	@Test
	public void testCase3() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("entering testCase3()");
		}
		String input = "[49679, 52015] [49800, 50000] [51500, 53479] [45012, 46937] [54012, 59607] [45500, 45590] [45999, 47900] [44000, 45000] [43012, 45950]";
		ZipCodeRageFinder codeRageFinder = new ZipCodeRageFinder();
		Set<ZipCodeRange> actualOutput = codeRageFinder.findMinumRanges(input);
		Set<ZipCodeRange> expectedOutput = new TreeSet<>(comparator);
		expectedOutput.add(new ZipCodeRange(43012, 47900));
		expectedOutput.add(new ZipCodeRange(49679, 53479));
		expectedOutput.add(new ZipCodeRange(54012, 59607));
		assertEquals(actualOutput.size(), expectedOutput.size());
		assertEquals(actualOutput.stream().map(x -> x.toString()).collect(Collectors.joining(" ")),
				expectedOutput.stream().map(x -> x.toString()).collect(Collectors.joining(" ")));
		if (logger.isDebugEnabled()) {
			logger.debug("exiting testCase3()");
		}
	}

	@Test
	public void testCase2() throws Exception {
		if (logger.isDebugEnabled()) {
			logger.debug("entering testCase2()");
		}
		String input = "[94133,94133] [94200,94299] [94226,94399]";
		ZipCodeRageFinder codeRageFinder = new ZipCodeRageFinder();
		Set<ZipCodeRange> actualOutput = codeRageFinder.findMinumRanges(input);
		Set<ZipCodeRange> expectedOutput = new TreeSet<>(comparator);
		expectedOutput.add(new ZipCodeRange(94133, 94133));
		expectedOutput.add(new ZipCodeRange(94200, 94399));
		assertEquals(actualOutput.size(), expectedOutput.size());
		assertEquals(actualOutput.stream().map(x -> x.toString()).collect(Collectors.joining(" ")),
				expectedOutput.stream().map(x -> x.toString()).collect(Collectors.joining(" ")));
		if (logger.isDebugEnabled()) {
			logger.debug("exiting testCase2()");
		}
	}
}
