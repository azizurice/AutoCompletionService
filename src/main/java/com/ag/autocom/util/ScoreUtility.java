package com.ag.autocom.util;

import java.util.LinkedList;

import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch;
import org.bitbucket.cowwoc.diffmatchpatch.DiffMatchPatch.Diff;

public class ScoreUtility {

	public static double getDistance(double latitudeOne, double longitudeOne, double latitudeTwo, double longitudeTwo) {
		double theta = longitudeOne - longitudeTwo;
		double dist = Math.sin(degreeToRadian(latitudeOne)) * Math.sin(degreeToRadian(latitudeTwo))
				+ Math.cos(degreeToRadian(latitudeOne)) * Math.cos(degreeToRadian(latitudeTwo))
						* Math.cos(degreeToRadian(theta));
		dist = Math.acos(dist);
		dist = radianToDegree(dist);
		dist = dist * 60 * 1.1515; // Miles

		return dist;
	}

	private static double degreeToRadian(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private static double radianToDegree(double rad) {
		return (rad * 180.0 / Math.PI);
	}

	public static double textMatchingScore(String textOne, String textTwo) {

		DiffMatchPatch dmp = new DiffMatchPatch();
		LinkedList<Diff> diff = dmp.diffMain(textOne, textTwo, false);

		String matchCharacters = diff.stream().filter(e -> e.operation == DiffMatchPatch.Operation.EQUAL)
				.map(e -> e.text).reduce("", String::concat);

		String misMatchCharacters = diff.stream().filter(
				e -> e.operation == DiffMatchPatch.Operation.DELETE || e.operation == DiffMatchPatch.Operation.INSERT)
				.map(e -> e.text).reduce("", String::concat);

		return ((double) matchCharacters.length() / (double) textTwo.length())
				- ((double) misMatchCharacters.length() / (double) textTwo.length());

	}

	/**
	 * 
	 * Convert a number into a value between 0 and 1, given that the number is
	 * between min and max. 0 means value = max, and 1 means value = min.
	 *
	 * @param value the number that needs to be normalized
	 * @param min   the smallest number in the range.
	 * @param max   the largest number in the range.
	 * @return a double value between 0 to 1 scale.
	 */
//	public static double normalizeScore(double value, double min, double max) {
//		return 1 - ((value - min) / (max - min));
//	}

	public static double normalizeScore(double originalValue, double minOriginalRange, double maxOriginalRange,
			double minNewRange, double maxNewRange) {

		// MATH_SCALE_VALUES
		// Converts a value from one range into another
		// (maxNewRange - minNewRange)(originalValue - minOriginalRange)
		// y = ----------------------------------------------------------- + minNewRange
		// (maxOriginalRange - minOriginalRange)

		return minNewRange + (((maxNewRange - minNewRange) * (originalValue - minOriginalRange))
				/ (maxOriginalRange - minOriginalRange));

	}

}
