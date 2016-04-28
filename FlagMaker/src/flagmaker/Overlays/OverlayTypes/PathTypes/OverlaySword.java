package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlaySword extends OverlayPath
{
	private final String Path = "m 91.850522,-18.250908 c -0.008,0.004 -0.0234,0.0258 -0.0312,0.0312 -5.9277,1.521 -5.7433,7.6749 -1.9375,10.3750004 -62.594,0 -158.650005,-1.0625 -179.250002,-1.0625 -11.525,0 -47.987,-1.3750004 -48.688,-1.3750004 7.8196,11.4370006 19.147,13.7660006 33.969,14.0000006 27.801997,0 156.620002,-0.34375 193.280002,-0.34375 -2.6062,4.6275 0.23645,12.0739994 2.2812,13.4379994 0.0434,0.0279 0.11369,0.0715 0.15625,0.0937 0.0763,0.038 0.17693,0.0749 0.25,0.0937 0.0242,0.006 0.07,-0.003 0.0937,0 2.4271,0.5725 3.4951,-1.1846 4.3125,-2.4688 3.937198,0.34703 26.096998,0.66757 28.124998,-0.0312 0.007,-0.003 0.0245,-0.0284 0.0312,-0.0312 1.3718,2.0189 2.7332,3.9264 5.3438,3.5938 4.569,-0.97972 8.1674,-1.477 8.2812,-10.7499994 0,0 -0.46704,-15.8020002 -10.844,-14.8120002 -2.4553,0.37092 -9.3438,1.0625 -9.3438,1.0625 -7.9718,-0.94847 -13.819,-1.0454 -22.780998,-1.375 0.82723,-1.0502 2.287798,-5.3034004 0.5,-6.8438004 -0.46622,-0.38767 -1.1617,-0.60352 -2.125,-0.53125 -0.007,5e-4 -0.0244,-8e-5 -0.0312,0 -0.0136,-6.8e-4 -0.0492,0.002 -0.0625,0 -0.007,-0.002 -0.0248,-0.0293 -0.0312,-0.0312 -0.0257,-0.009 -0.0692,-0.0161 -0.0937,-0.0312 -0.0486,-0.033 -0.11173,-0.10294 -0.15625,-0.15625 -0.011,-0.0139 -0.0517,-0.0475 -0.0625,-0.0625 -0.45322,-0.65987 -0.70068,-2.2777 -1,-2.6875 -0.004,-0.004 -0.0277,-0.0271 -0.0312,-0.0312 -0.004,-0.003 -0.0276,-0.0282 -0.0312,-0.0312 -0.0109,-0.007 -0.0514,-0.0277 -0.0625,-0.0312 -0.004,-3.6e-4 -0.0275,-6e-5 -0.0312,0 -0.004,9.1e-4 -0.0274,-0.001 -0.0312,0 z m 4.25,22.5940006 c 0.0109,-0.005 0.0202,0.005 0.0312,0 9.131898,0.44551 17.711998,0.0857 26.843998,0.53125 1.4473,1.2303 0.72345,4.0484 -0.21875,4.5625 -0.0422,0.0211 -0.11339,0.051 -0.15625,0.0625 -0.0344,0.008 -0.0905,-10e-4 -0.125,0 -0.0345,-7e-4 -0.0905,0.008 -0.125,0 -2.9694,-0.0746 -4.6869,-0.14417 -7.6562,-0.21875 -0.005,-0.14772 -0.0215,-0.30707 -0.0625,-0.4375 -0.70371,-2.0856 -5.8008,-1.9461 -7,-0.3125 -0.0166,0.0235 -0.0475,0.0696 -0.0625,0.0937 -0.0192,0.0325 -0.0463,0.0915 -0.0625,0.125 -0.0193,0.0422 -0.0482,0.11238 -0.0625,0.15625 -0.0187,0.0619 -0.0229,0.15372 -0.0312,0.21875 -0.008,0.0748 -0.0379,0.17113 -0.0312,0.25 -4.0086,0.4818 -7.6164,-0.13309 -11.624998,-0.28125 -1.1761,-1.4641 -1.0388,-4.1134 0.34375,-4.75 z";
	private final Vector PathSize = new Vector(278, 38);

	public OverlaySword(int maximumX, int maximumY)
	{
		super("sword", maximumX, maximumY);
		Constructor(Path, PathSize);
	}
}
