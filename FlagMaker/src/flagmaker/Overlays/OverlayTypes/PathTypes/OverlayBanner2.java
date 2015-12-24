package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayBanner2 extends OverlayPath
{
	private final String Path = "M -263.05964,-56.53856 C -279.61793,-57.61947 -319,-59.84538 -319,-59.84538 c 0,0 60.69855,48.017114 60.69855,48.843824 0,0.8267 -13.98575,19.00625 -17.09584,24.45998 7.67155,5.85782 39.26716,27.205091 76.12774,45.05145 113.809681,53.735816 240.437281,64.622086 347.08222,29.07378 40.99939,-13.36639 93.90851,-43.201289 128.08692,-71.0443 -10.24639,-15.60539 -13.93872,-19.54414 -13.93872,-20.09528 0,-0.55114 13.71547,-12.19853 28.04502,-24.32353 14.05399,-12.12501 19.26859,-16.92757 28.59884,-26.18429 -18.79963,0.48821 -41.03319,0.13905 -52.02213,0.28086 l 12.54233,-53.460224 -13.36905,9.36931 c -37.2017,28.38353 -75.97006,60.819104 -83.50247,67.099554 6.01282,7.25156 11.49766,13.42135 16.4738,20.67291 -31.93018,20.63916 -48.98556,29.8952 -87.8407,40.64235 -26.178994,7.164781 -52.543799,9.807801 -87.668939,10.440791 -31.731959,0.56518 -83.39074,-5.34146 -135.748701,-21.324411 -31.97121,-10.54512 -72.04653,-28.24178 -82.88578,-35.82653 3.18096,-5.10994 12.67615,-20.99816 15.5789,-26.31544 -5.18347,-4.41439 -86.31299,-59.281664 -96.1813,-66.005224 4.65702,21.9619 8.70812,34.83712 12.95967,51.95124 z";
	private final Vector PathSize = new Vector(638, 233);
	
	public OverlayBanner2(int maximumX, int maximumY)
	{
		super("banner 2", maximumX, maximumY);
		Constructor(Path, PathSize);
	}	
}