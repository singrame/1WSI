package com.wsi.data;

import java.util.Comparator;

public class ZipCodeRageComparator implements Comparator<ZipCodeRange> {

	@Override
	public int compare(ZipCodeRange o1, ZipCodeRange o2) {
		if (o1.getZipCodeStart() > o2.getZipCodeStart())
			return 1;
		if (o1.getZipCodeStart() < o2.getZipCodeStart())
			return -1;

		return 0;
	}

}
