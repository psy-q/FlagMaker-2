package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayMozambique extends OverlayPath
{
	private final String Path = "m -97.512676,64.951471 12.393368,14.530859 c 1.455353,0.867946 2.748077,0.812518 4.0408,0 l 18.367333,-22.040044 7.714268,-9.551189 c 1.194465,-1.414665 1.575782,-2.993093 1.469336,-4.407757 l 14.748034,-13.104857 c 1.206433,0.08314 1.924977,0.219191 3.131409,0.301073 -1.425372,-0.369098 -2.47119,-1.063203 -1.348907,-2.625255 l 3.305881,-2.571086 2.571086,3.305502 c 0,0 -3.672836,4.775595 -4.04017,4.775595 -0.367334,0 -4.040801,0 -4.040801,0 l -7.714267,6.980103 3.372268,2.980496 5.076667,13.917375 6.245309,-4.409016 -4.041305,-14.325525 8.8169,-9.55119 -3.306763,-5.143432 2.204509,-2.938925 c 0,0 30.5129138,19.210715 42.268611,14.068542 0.317449,0.114635 0.710481,-13.700704 0.710481,-13.700704 0,0 -31.591232,-3.305503 -32.326908,-9.551189 -0.734416,-6.2444271 6.980103,-6.9788431 6.980103,-6.9788431 l -3.306763,-4.775595 0.735676,-2.572346 5.5100119,6.980103 L 4.5136048,-6.1092781 77.981676,77.645659 c 4.010945,-1.626297 4.867554,-2.607618 5.143432,-6.612265 -0.103297,-0.100777 -71.999239,-82.652683 -71.999239,-82.652683 l 5.510011,-5.876589 c 1.085877,-1.224447 1.467572,-1.744711 1.468832,-3.6746 l 8.448935,-7.346429 c 2.540854,0.872859 4.160852,2.395733 5.510011,4.407504 l 23.227959,-19.685502 c 0.612223,0.612223 2.471569,1.224446 3.733807,0.535884 l 38.402537,-36.861017 -41.854167,29.562331 -1.468832,-1.102002 c 0,-1.224573 1.517961,-1.527409 0,-3.673467 -1.626298,-1.95143 -4.041179,1.836797 -4.407757,1.836797 -0.367838,0 -6.057989,-2.011016 -7.305111,-4.558671 l -0.409408,6.762675 -10.653444,9.918398 -8.081097,-0.367334 -11.755698,11.387607 -1.468832,4.407631 1.83667,3.6746 c 0,0 -6.2456862,5.510011 -6.2456862,5.142173 0,-0.366578 -1.262238,-1.623777 -1.316406,-1.786281 l 5.3575846,-4.825984 0.7344156,-3.30588 -1.7875402,-2.791286 c -0.54168,0.393663 -7.395811,7.567258 -7.76364903,6.831583 -0.612223,-0.685288 -19.83553577,-22.40763 -19.83553577,-22.40763 l 1.100995,-4.040801 -12.488855,-13.591864 c -4.553254,-1.572003 -11.755445,-1.83667 -13.224782,8.081601 -1.144202,2.329599 -10.653065,0.367335 -10.653065,0.367335 l -5.142803,1.102128 -29.020399,41.142674 16.163203,19.4689569 33.061199,-41.8770899 0.981447,-11.86353 6.937021,7.756468 c 2.312844,0.297167 4.516092,0.323244 6.612265,-0.734668 l 19.5873714,21.863305 -3.2614125,3.18205 c 1.1022542,1.224447 1.860605,2.006732 2.9628591,3.231179 1.1022541,-0.734416 2.15412,-1.616219 3.256374,-2.351895 0.367838,0.49129 0.980061,1.422223 1.34789877,1.912253 C -2.418629,-10.873535 -3.570012,-9.6843601 -5.2114259,-8.7912201 -7.83794,-10.503178 -10.373755,-12.634622 -10.181018,-16.027045 l -11.020022,9.1833509 -0.367838,1.83667 -32.693361,27.1834761 -2.938799,0.367838 -0.734668,8.448936 21.306005,-17.632287 0,-2.572346 2.204131,1.83667 16.530033,-13.22327006 c 0,0 1.102254,1.46883196 0.735676,1.46883196 -0.367838,0 -14.694623,13.2245301 -14.694623,13.2245301 l -0.366578,1.468832 -2.571842,2.204509 -1.469463,-1.102255 -19.836669,17.632288 -2.938672,0 -11.0204,11.021281 c -2.84243,0.246905 -5.305432,0.547978 -7.714267,2.203249 z";
	private final Vector PathSize = new Vector(198, 162);

	public OverlayMozambique(int maximumX, int maximumY)
	{
		super("mozambique", maximumX, maximumY);
		Constructor(Path, PathSize);
	}
}
