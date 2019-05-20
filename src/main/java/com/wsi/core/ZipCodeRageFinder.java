package com.wsi.core;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.wsi.data.ZipCodeRageComparator;
import com.wsi.data.ZipCodeRange;
import com.wsi.exception.InvalidInputException;

/**
 * @author rames Core class to find unique zip code ranges from the given input
 *         set of zip code ranges It used a TreeSet to store the ranges in
 *         ascending order of their start range. The method findMinumRanges has
 *         the implementation for the same. See the documentation for the method
 *         for more details
 */
public class ZipCodeRageFinder {

	private static final Logger logger = LogManager.getLogger(ZipCodeRageFinder.class);
	/**
	 * Regular Expression for 5 digit zip Code
	 */

	private ZipCodeRageComparator comparator = new ZipCodeRageComparator();
	private static final Pattern ZIP_CODE_PATTERN = Pattern.compile("(\\[)(\\d{5})([ ]*,[ ]*)(\\d{5})(\\])");

	/**
	 * 
	 * @param input
	 *            : List of ranges of zip code e.g. [94133,94133] [94200,94299]
	 *            [94226,94399]
	 * @return Set of ZipCodeRange, which are not overlapping and still cover
	 *         the all range given in the input [94133,94133] [94200,94399]
	 * @throws InvalidInputException
	 *             : Throws this exception if unable to parse the input
	 */

	public Set<ZipCodeRange> findMinumRanges(String input) throws InvalidInputException {
		logger.info("Received Input :{}", input);
		if (input == null || input.trim().equals("")) {

			throw new InvalidInputException("Input String is empty");
		}
		// Update the range delimiter from space to tab
		input = input.replaceAll("\\][ ]\\[", "]\t[");

		Set<ZipCodeRange> existingRanges = new TreeSet<ZipCodeRange>(comparator);
		String[] rangeArray = input.split("\t");
		for (String elem : rangeArray) {
			if (elem == null || elem.trim().equals(""))
				throw new InvalidInputException("Input has multiple consecitive spaces");
			Matcher zipCodePatternMatcher = ZIP_CODE_PATTERN.matcher(elem);
			if (zipCodePatternMatcher.find()) {
				ZipCodeRange range = new ZipCodeRange(Integer.parseInt(zipCodePatternMatcher.group(2)),
						Integer.parseInt(zipCodePatternMatcher.group(4)));
				ZipCodeRange clonedRange = new ZipCodeRange(range.getZipCodeStart(), range.getZipCodeEnd());
				ZipCodeRange updatedRange = processZipCodeRange(clonedRange, existingRanges);
				if (updatedRange != null) {
					existingRanges.add(updatedRange);
				}
				logger.info("After processing Range {} Merged Ranges : {}", range, existingRanges);
			} else {
				throw new InvalidInputException("Input has invalid range : " + elem);
			}
		}
		return existingRanges;

	}

	/**
	 * This is the helper method, to adjust the ranges compiled so for with the
	 * next range from the input
	 */
	private ZipCodeRange processZipCodeRange(ZipCodeRange currentRange, Set<ZipCodeRange> existingRanges) {
		Deque<ZipCodeRange> colnedRangeList = new ArrayDeque<>(existingRanges);

		while (!colnedRangeList.isEmpty()) {
			ZipCodeRange existingRange = colnedRangeList.removeFirst();
			if (currentRange.getZipCodeEnd() < existingRange.getZipCodeStart()
					|| currentRange.getZipCodeStart() > existingRange.getZipCodeEnd()) {
				logger.debug("Continue checking with the next exising Range as range {} is outside of {}", currentRange,
						existingRange);
				continue;
			}
			if (currentRange.getZipCodeStart() >= existingRange.getZipCodeStart()
					&& currentRange.getZipCodeEnd() <= existingRange.getZipCodeEnd()) {
				logger.debug("Ignoring current Range {} as covered in existing range {}", currentRange, existingRange);

				return null;
			}
			if (currentRange.getZipCodeStart() <= existingRange.getZipCodeStart()
					&& currentRange.getZipCodeEnd() >= existingRange.getZipCodeEnd()) {
				existingRanges.remove(existingRange);
				logger.debug("Continue checking next elements of existing range with merged range {}", currentRange);
				continue;
			}
			if (currentRange.getZipCodeStart() < existingRange.getZipCodeStart()
					&& currentRange.getZipCodeEnd() < existingRange.getZipCodeEnd()) {
				existingRanges.remove(existingRange);
				currentRange.setZipCodeEnd(existingRange.getZipCodeEnd());
				logger.debug("Continue checking next elements of existing range with merged range {}", currentRange);

				continue;
			}

			if (currentRange.getZipCodeStart() > existingRange.getZipCodeStart()
					&& currentRange.getZipCodeEnd() > existingRange.getZipCodeEnd()) {
				existingRanges.remove(existingRange);
				currentRange.setZipCodeStart(existingRange.getZipCodeStart());
				logger.debug("Continue checking next elements of existing range with merged range {}", currentRange);
				continue;
			}

		}
		return currentRange;
	}
}
