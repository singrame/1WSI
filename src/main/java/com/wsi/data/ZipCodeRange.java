package com.wsi.data;

public class ZipCodeRange {
	private int zipCodeStart;
	private int zipCodeEnd;

	public ZipCodeRange(int zipCodeStart, int zipCodeEnd) {
		this.zipCodeStart = zipCodeStart;
		this.zipCodeEnd = zipCodeEnd;
	}

	public int getZipCodeStart() {
		return zipCodeStart;
	}

	public void setZipCodeStart(int zipCodeStart) {
		this.zipCodeStart = zipCodeStart;
	}

	public int getZipCodeEnd() {
		return zipCodeEnd;
	}

	public void setZipCodeEnd(int zipCodeEnd) {
		this.zipCodeEnd = zipCodeEnd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + zipCodeEnd;
		result = prime * result + zipCodeStart;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ZipCodeRange other = (ZipCodeRange) obj;
		if (zipCodeEnd != other.zipCodeEnd)
			return false;
		if (zipCodeStart != other.zipCodeStart)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[" + zipCodeStart + ", " + zipCodeEnd + "]";
	}

}
