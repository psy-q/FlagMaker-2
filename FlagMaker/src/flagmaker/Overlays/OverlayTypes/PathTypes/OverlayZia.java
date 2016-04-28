package flagmaker.Overlays.OverlayTypes.PathTypes;

import flagmaker.Data.Vector;

public class OverlayZia extends OverlayPath
{
	private final String Path = "m -12.875267,-199.99908 a 8.5010168,8.5009139 0 0 0 -8.375164,8.62507 l 0,125.063443 c -2.937228,0.943517 -5.777544,2.097025 -8.531418,3.406275 l 0,-94.438208 a 8.5010168,8.5009139 0 0 0 -8.625169,-8.62507 8.5010168,8.5009139 0 0 0 -8.375164,8.62507 l 0,105.813294 c -1.649483,1.4995 -3.250574,3.100573 -4.750094,4.750035 l -105.814564,0 a 8.5010168,8.5009139 0 1 0 0,17.000128 l 94.439341,0 c -1.309265,2.753841 -2.462788,5.594123 -3.406317,8.531314 l -125.064944,0 a 8.5010168,8.5009139 0 1 0 0,17.0001277 l 121.908632,0 c -0.08584,1.4122606 -0.156253,2.8165612 -0.156253,4.250032 0,1.4334707 0.07041,2.8377713 0.156253,4.2500319 l -121.908632,0 a 8.5010168,8.5009139 0 1 0 0,17.0001274 l 125.064944,0 c 0.943529,2.937193 2.097052,5.777474 3.406317,8.531315 l -94.439341,0 a 8.5010168,8.5009139 0 1 0 0,17.000127 l 105.814564,0 c 1.49952,1.649463 3.100611,3.250535 4.750094,4.750037 l 0,105.813291 a 8.5010168,8.5009139 0 1 0 17.000333,0 l 0,-94.438206 c 2.753874,1.309249 5.59419,2.462758 8.531418,3.406276 l 0,125.06343 a 8.5010168,8.5009139 0 1 0 17.0003333,0 l 0,-121.907156 c 1.4122777,0.08584 2.8165953,0.156251 4.250083417427,0.156251 C 1.4334738,69.627955 2.8377914,69.557545 4.2500691,69.471704 l 0,121.907156 a 8.5010168,8.5009139 0 1 0 17.0003339,0 l 0,-125.06343 c 2.937227,-0.943518 5.777543,-2.097027 8.531417,-3.406276 l 0,94.438206 a 8.5010168,8.5009139 0 1 0 17.000334,0 l 0,-105.813291 c 1.649482,-1.499502 3.250574,-3.100574 4.750093,-4.750037 l 105.814583,0 a 8.5010168,8.5009139 0 1 0 0,-17.000127 l -94.43936,0 c 1.309266,-2.753841 2.462788,-5.594122 3.406317,-8.531315 l 125.064963,0 a 8.5010168,8.5009139 0 1 0 0,-17.0001274 l -121.908651,0 c 0.08584,-1.4122606 0.156253,-2.8165612 0.156253,-4.2500319 0,-1.4334708 -0.07041,-2.8377714 -0.156253,-4.250032 l 121.908651,0 a 8.5010168,8.5009139 0 1 0 0,-17.0001277 l -125.064963,0 c -0.943529,-2.937191 -2.097051,-5.777473 -3.406317,-8.531314 l 94.43936,0 a 8.5010168,8.5009139 0 1 0 0,-17.000128 l -105.814583,0 c -1.499519,-1.649462 -3.100611,-3.250535 -4.750093,-4.750035 l 0,-105.813294 a 8.5010168,8.5009139 0 0 0 -8.62517,-8.62507 8.5010168,8.5009139 0 0 0 -8.375164,8.62507 l 0,94.438208 c -2.753874,-1.30925 -5.59419,-2.462758 -8.531417,-3.406275 l 0,-125.063443 a 8.5010168,8.5009139 0 0 0 -8.62517,-8.62507 8.5010168,8.5009139 0 0 0 -8.3751639,8.62507 l 0,121.907169 C 2.8377914,-69.552681 1.4334738,-69.623092 -1.4282573e-5,-69.623092 -1.4335024,-69.623092 -2.83782,-69.552682 -4.2500977,-69.466841 l 0,-121.907169 a 8.5010168,8.5009139 0 0 0 -8.6251693,-8.62507 z M -1.4282573e-5,-59.185514 C 32.695887,-59.185514 59.188647,-32.693074 59.188647,0.0024307 59.188647,32.697937 32.695887,59.190376 -1.4282573e-5,59.190376 -32.695916,59.190376 -59.188676,32.697937 -59.188676,0.0024307 c 0,-32.6955047 26.49276,-59.1879447 59.188661717427,-59.1879447 z";
	private final Vector PathSize = new Vector(402, 402);
	
	public OverlayZia(int maximumX, int maximumY)
	{
		super("zia", maximumX, maximumY);
		Constructor(Path, PathSize);
	}	
}
