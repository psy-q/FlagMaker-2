package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayBanner extends OverlayPath
{
	private final String Path = "M -263.05964,-56.53856 C -279.61793,-57.61947 -319,-59.84538 -319,-59.84538 c 0,0 60.69855,48.017114 60.69855,48.843824 0,0.8267 -13.98575,19.00625 -17.09584,24.45998 7.67155,5.85782 39.26716,27.205091 76.12774,45.05145 113.809681,53.735816 240.437281,64.622086 347.08222,29.07378 40.99939,-13.36639 93.90851,-43.201289 128.08692,-71.0443 -10.24639,-15.60539 -13.93872,-19.54414 -13.93872,-20.09528 0,-0.55114 13.71547,-12.19853 28.04502,-24.32353 14.05399,-12.12501 19.26859,-16.92757 28.59884,-26.18429 -18.79963,0.48821 -41.03319,0.13905 -52.02213,0.28086 l 12.54233,-53.460224 -13.36905,9.36931 c -37.2017,28.38353 -75.97006,60.819104 -83.50247,67.099554 6.01282,7.25156 11.49766,13.42135 16.4738,20.67291 -31.93018,20.63916 -48.98556,29.8952 -87.8407,40.64235 -26.178994,7.164781 -52.543799,9.807801 -87.668939,10.440791 -31.731959,0.56518 -83.39074,-5.34146 -135.748701,-21.324411 -31.97121,-10.54512 -72.04653,-28.24178 -82.88578,-35.82653 3.18096,-5.10994 12.67615,-20.99816 15.5789,-26.31544 -5.18347,-4.41439 -86.31299,-59.281664 -96.1813,-66.005224 4.65702,21.9619 8.70812,34.83712 12.95967,51.95124 z m 76.33241,12.125004 -48.93789,6.27381 3.07186,2.93009 47.93412,-5.89973 c 0,0 -13.64195,22.87482 -14.46866,22.87482 -0.8267,0 -36.66649,-19.91711 -36.66649,-19.91711 l -19.96146,23.98241 c 0,0 -50.03833,-39.49589 -52.70973,-41.40879 6.77754,0.38911 44.09359,2.48542 50.37601,2.77093 -2.01112,-7.178684 -11.64939,-42.788584 -12.89342,-47.691194 l 84.25566,56.084764 z M 272.30129,-96.70859 c 0,-0.48556 -7.24462,30.25005 -11.4374,47.130124 11.89498,-0.27757 47.60508,-0.34646 47.60508,-0.34646 l -12.125,10.74717 c -6.33808,5.78692 -17.63637,15.43182 -24.52557,21.21875 l -12.95172,10.74716 c 0,0 -18.37895,-25.27609 -22.23955,-28.59352 -5.48797,1.20496 -15.6054,4.83438 -6.2778,3.9315 -9.19644,7.38801 -22.741,16.04058 -27.56476,19.70444 -2.96834,-3.93944 -3.7162,-4.41174 -7.2986,-9.09639 l -6.82097,-8.6135 44.29735,-1.96402 c 0,0 6.09706,2.53671 1.56895,-4.27766 -10.5409,0.23513 -41.24458,1.90082 -41.24458,1.90082 0,0 72.44798,-57.987234 79.01457,-62.488414 z m -476.16671,79.851864 c 104.59132,62.701581 258.811561,80.19037 358.29173,36.65059 11.02272,-4.68468 31.96591,-16.80967 46.8466,-26.73013 14.88069,-9.92046 32.04476,-22.26076 34.2493,-23.6386 5.79488,6.29634 26.00542,31.8003 34.57983,46.85456 -7.59535,5.71871 -44.57912,31.622121 -74.34049,46.50281 -100.306863,50.428996 -220.179079,55.113656 -340.87796,13.77842 -32.79263,-11.2983 -81.01708,-34.446029 -106.92049,-51.2557 0,0 -11.40333,-8.3642 -18.20544,-12.55734 5.39532,-7.71791 30.19837,-40.26261 34.97979,-46.72277 6.01017,3.93944 14.21154,6.81554 31.39713,17.11816 z";
	private final Vector PathSize = new Vector(638, 233);
	
	public OverlayBanner(int maximumX, int maximumY)
	{
		super("banner", maximumX, maximumY);
		Constructor(Path, PathSize);
	}	
}
