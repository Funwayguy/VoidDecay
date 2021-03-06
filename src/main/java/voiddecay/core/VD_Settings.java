package voiddecay.core;

import java.util.ArrayList;

/**
 * A container for all the configurable settings in the mod
 */
public class VD_Settings
{
	public static boolean hideUpdates = false;
	public static int decaySpeed = 100;
	public static int infectedChunks = 100;
	public static int decayCap = 20;
	public static boolean fastDecay = false;
	public static boolean decayScaleTime = false;
	public static boolean decayScaleDist = false;
	public static int relativeDist = 100;
	public static int relativeTime = 30;
	public static boolean voidMeteor = true;
	public static boolean fastRender = false;
	public static ArrayList<String> blockBlacklist = new ArrayList<String>();
	public static ArrayList<Integer> dimBlacklist = new ArrayList<Integer>();
	public static boolean dimWhitelist = false;
}
