package flagmaker.RandomFlag;

import flagmaker.Overlays.OverlayTypes.SpecialTypes.OverlayFlag;
import flagmaker.Divisions.*;
import flagmaker.Flag;
import flagmaker.LocalizationHandler;
import flagmaker.Overlays.Overlay;
import flagmaker.Overlays.OverlayFactory;
import flagmaker.Overlays.OverlayTypes.*;
import flagmaker.Overlays.OverlayTypes.PathTypes.OverlayPath;
import flagmaker.Overlays.OverlayTypes.ShapeTypes.*;
import flagmaker.Ratio;
import java.io.File;
import java.util.ArrayList;
import javafx.scene.paint.Color;

public class RandomFlagFactory
{
	private Ratio _ratio;
	private Ratio _gridSize;
	private DivisionTypes _divisionType;
	private Division _division;
	private ArrayList<Overlay> _overlays;
	private ColorScheme _colorScheme;
	private boolean _canHaveCanton;
	
	private final Overlay[] _emblems = OverlayFactory.GetEmblems();
	
	public Flag GenerateFlag()
	{
		return GenerateFlag(new ColorScheme());
	}
	
	public Flag GenerateFlag(ColorScheme colorScheme)
	{
		_colorScheme = new ColorScheme();
		_canHaveCanton = true;
		GetRatio();
		_overlays = new ArrayList<>();
		_division = GetDivision();
		
		Overlay[] overlays = new Overlay[]{};
		return new Flag(LocalizationHandler.Get("Random"), _ratio, _gridSize, _division, _overlays.toArray(overlays));
	}
	
	private void GetRatio()
	{
		_ratio = new Ratio[]
				 {
					 new Ratio(3, 2),
					 new Ratio(5, 3),
					 new Ratio(7, 4),
					 new Ratio(2, 1)
				 }[Randomizer.RandomWeighted(new int[] { 6, 2, 3, 4 })];
		_gridSize = new Ratio(_ratio.Width * 8, _ratio.Height * 8);
	}
	
	private Division GetDivision()
	{
		// Roughly based on real-life usage
		// 206 flags surveyed
		_divisionType = DivisionTypes.values()[Randomizer.RandomWeighted(new int[]
																  {
																	  9, // stripes
																	  22, // pales
																	  66, // fesses
																	  38, // blank
																	  21, // horizontal halves
																	  6, // vertical halves
																	  10, // diagonal
																	  12, // stripe
																	  9, // cross
																	  3, // x
																	  1, // ray
																	  0 // 11 // other
																  })];

		switch (_divisionType)
		{
			case Stripes:
				return GetStripes();
			case Pales:
				return GetPales();
			case Fesses:
				return GetFesses();
			case Blank:
				return GetBlank();
			case Horizontal:
				return GetHorizontal();
			case Vertical:
				return GetVertical();
			case Diagonal:
				return GetDiagonal();
			case Stripe:
				return GetStripe();
			case Cross:
				return GetCross();
			case X:
				return GetX();
			case Ray:
				return GetRay();
			default:
				return null;
				//throw new Exception("No valid type selection");
		}
	}
	
	private DivisionGrid GetStripes()
	{
		int stripeCount = Randomizer.Clamp(Randomizer.NextNormalized(10, 3), 5, 15, true);

		Color stripeOuterColor = _colorScheme.Color1();
		Color stripeInnerColor = _colorScheme.Metal();
		Color cantonColor = _colorScheme.Color2();

		if (Randomizer.ProbabilityOfTrue(0.125))
		{
			int width = HoistElementWidth(true);
			AddTriangle(1.0, 1.0, width, cantonColor, stripeInnerColor);
		}
		else
		{
			boolean isMainColorMetal = Randomizer.ProbabilityOfTrue(0.142857);
			if (isMainColorMetal)
			{
				stripeOuterColor = _colorScheme.Metal();
				stripeInnerColor = _colorScheme.Color1();
				cantonColor = _colorScheme.Metal();
			}
			else if (Randomizer.ProbabilityOfTrue(0.16667))
			{
				cantonColor = stripeOuterColor;
			}

			double width = HoistElementWidth(false);
			double cantonHeight = _gridSize.Height * ((double)((int)(stripeCount / 2.0) + 1) / stripeCount);
			if (width < cantonHeight) width = cantonHeight;

			_overlays.add(new OverlayBox(cantonColor, 0, 0, width, cantonHeight, _gridSize.Width, _gridSize.Height));

			if (Randomizer.ProbabilityOfTrue(0.142857))
			{
				AddRepeater(width / 2, cantonHeight / 2, width * 3 / 4.0, cantonHeight * 3 / 4.0, stripeInnerColor, false);
			}
			else
			{
				AddEmblem(1.0, width / 2, cantonHeight / 2, stripeInnerColor, false, Color.WHITE, false);
			}
		}

		return new DivisionGrid(stripeOuterColor, stripeInnerColor, 1, stripeCount);
	}
	
	private DivisionPales GetPales()
	{
		Color c1 = _colorScheme.Color1();
		Color c2;
		Color c3;
		Color emblemColor;
		boolean isBalanced = true;
		boolean emblemInCenter = true;
		double probabilityOfEmblem;

		if (Randomizer.ProbabilityOfTrue(0.13636))
		{
			c2 = _colorScheme.Color2();

			if (Randomizer.ProbabilityOfTrue(0.333))
			{
				c3 = _colorScheme.Color3();
				emblemColor = _colorScheme.Metal();
				probabilityOfEmblem = 1.0;
			}
			else if (Randomizer.ProbabilityOfTrue(0.5))
			{
				c3 = _colorScheme.Metal();
				emblemColor = _colorScheme.Metal();
				probabilityOfEmblem = 1.0;
			}
			else
			{
				c3 = _colorScheme.Color1();
				emblemInCenter = false;
				emblemColor = _colorScheme.Metal();
				probabilityOfEmblem = 1.0;
			}
		}
		else
		{
			c2 = _colorScheme.Metal();
			emblemColor = Randomizer.ProbabilityOfTrue(0.5) ? _colorScheme.Color1() : _colorScheme.Color2();

			if (Randomizer.ProbabilityOfTrue(0.2632))
			{
				c3 = _colorScheme.Color2();
				probabilityOfEmblem = 0.357;
			}
			else
			{
				c3 = c1;
				probabilityOfEmblem = 0.6;
			}

			if (Randomizer.ProbabilityOfTrue(0.1052))
			{
				isBalanced = false;
				probabilityOfEmblem = 1.0;
			}
		}

		AddEmblem(probabilityOfEmblem, emblemInCenter ? _gridSize.Width / 2.0 : _gridSize.Width / 6.0, _gridSize.Height / 2.0, emblemColor, false, Color.WHITE, false);
		return new DivisionPales(c1, c2, c3, 1, isBalanced ? 1 : 2, 1);
	}

	private DivisionFesses GetFesses()
	{
		Color c1;
		Color c2;
		Color c3;
		Color emblemColor;
		Color hoistColor;
		boolean isSpanish = false;
		boolean isLatvian = false;
		boolean isColombian = false;
		double probabilityOfEmblem;
		double probabilityOfHoist;

		if (Randomizer.ProbabilityOfTrue(0.166667))
		{
			c1 = _colorScheme.Metal();
			c2 = _colorScheme.Color1();
			c3 = _colorScheme.Color2();
			hoistColor = c2;
			probabilityOfHoist = 0.0909;
			probabilityOfEmblem = 0.5454;
			isColombian = Randomizer.ProbabilityOfTrue(0.1818182);
			emblemColor = isColombian ? c3 : c1;
		}
		else
		{
			c1 = _colorScheme.Color1();

			if (Randomizer.ProbabilityOfTrue(0.29))
			{
				c2 = _colorScheme.Color2();

				if (Randomizer.ProbabilityOfTrue(0.25))
				{
					c3 = _colorScheme.Color1();
					isLatvian = Randomizer.ProbabilityOfTrue(0.5);
					isSpanish = !isLatvian;
					probabilityOfEmblem = isSpanish ? 1.0 : 0.0;
					probabilityOfHoist = 0;
					hoistColor = Color.TRANSPARENT;
					emblemColor = _colorScheme.Metal();
				}
				else if (Randomizer.ProbabilityOfTrue(0.5))
				{
					c3 = _colorScheme.Color3();
					probabilityOfHoist = 0.0;
					probabilityOfEmblem = 0.833333;
					boolean hasFimbriations = Randomizer.ProbabilityOfTrue(0.5);
					isSpanish = !hasFimbriations && Randomizer.ProbabilityOfTrue(0.2);
					hoistColor = Color.TRANSPARENT;
					emblemColor = _colorScheme.Metal();

					if (hasFimbriations)
					{
						_overlays.add(new OverlayLineHorizontal(_colorScheme.Metal(), _gridSize.Width / 20.0, _gridSize.Width / 3.0, _gridSize.Width, _gridSize.Width));
						_overlays.add(new OverlayLineHorizontal(_colorScheme.Metal(), _gridSize.Width / 20.0, 2 * _gridSize.Width / 3.0, _gridSize.Width, _gridSize.Width));
					}
				}
				else
				{
					c3 = _colorScheme.Metal();
					hoistColor = _colorScheme.Color3();
					emblemColor = _colorScheme.Metal();
					probabilityOfHoist = 0.166667;
					probabilityOfEmblem = 0.166667;
				}
			}
			else
			{
				c2 = _colorScheme.Metal();

				if (Randomizer.ProbabilityOfTrue(0.2564))
				{
					c3 = _colorScheme.Color1();
					isSpanish = Randomizer.ProbabilityOfTrue(0.3);
					isLatvian = !isSpanish && Randomizer.ProbabilityOfTrue(0.1429);
					hoistColor = _colorScheme.Color2();
					emblemColor = _colorScheme.Color2();
					probabilityOfHoist = 0.2;
					probabilityOfEmblem = 0.7;
				}
				else
				{
					c3 = _colorScheme.Color2();
					hoistColor = _colorScheme.Color3();
					emblemColor = Randomizer.ProbabilityOfTrue(0.5) ? c1 : c3;
					isColombian = Randomizer.ProbabilityOfTrue(0.0345);
					probabilityOfHoist = 0.2414;
					probabilityOfEmblem = 0.6552;
				}
			}
		}

		if (isSpanish) { probabilityOfEmblem = 1.0; }
		else if (isLatvian) { probabilityOfEmblem = 0.0; }
		else if (isColombian) { probabilityOfHoist = 0.0; }

		if (Randomizer.ProbabilityOfTrue(probabilityOfHoist))
		{
			emblemColor = _colorScheme.Metal();
			AddTriangle(1.0, probabilityOfEmblem, HoistElementWidth(true), hoistColor, emblemColor);
		}
		else
		{
			AddEmblem(probabilityOfEmblem, _gridSize.Width / 2.0, _gridSize.Height / 2.0, emblemColor, false, Color.TRANSPARENT, false);
		}

		return new DivisionFesses(c1, c2, c3, isLatvian || isColombian ? 2 : 1, isSpanish ? 2 : 1, isLatvian ? 2 : 1);
	}

	private DivisionGrid GetCross()
	{
		boolean backgroundIsMetal = Randomizer.ProbabilityOfTrue(0.25);
		boolean fimbriate = !backgroundIsMetal && Randomizer.ProbabilityOfTrue(0.4286);

		Color background = backgroundIsMetal ? _colorScheme.Metal() : _colorScheme.Color1();
		Color mainColor = backgroundIsMetal ? _colorScheme.Color1() : fimbriate ? _colorScheme.Color2() : _colorScheme.Metal();
		Color fimbriation = _colorScheme.Metal();
		double center = _gridSize.Height / 2.0;

		int crossWidth = Randomizer.Clamp(Randomizer.NextNormalized(_gridSize.Width / 8.0, _gridSize.Width / 20.0), 2, _gridSize.Width / 4, false) - (fimbriate ? 1 : 0);
		int fimbriationWidth = crossWidth + 2;

		boolean canSaltire = false;

		double intersection = _gridSize.Width / 2.0;
		if (Randomizer.ProbabilityOfTrue(0.555556))
		{
			intersection = _gridSize.Width / 3.0;
		}
		else
		{
			if (Randomizer.ProbabilityOfTrue(0.25))
			{
				_overlays.add(new OverlayCross(_colorScheme.Metal(), crossWidth, intersection, center, _gridSize.Width, _gridSize.Height));
				return new DivisionGrid(_colorScheme.Color1(), _colorScheme.Color2(), 2, 2);
			}

			canSaltire = !backgroundIsMetal;
		}

		if (canSaltire && Randomizer.ProbabilityOfTrue(0.5))
		{
			if (fimbriate)
			{
				_overlays.add(new OverlaySaltire(fimbriation, fimbriationWidth, _gridSize.Width, _gridSize.Height));
				_overlays.add(new OverlayHalfSaltire(mainColor, crossWidth + 2, _gridSize.Width, _gridSize.Height));
			}
			else
			{
				_overlays.add(new OverlaySaltire(_colorScheme.Color2(), crossWidth, _gridSize.Width, _gridSize.Height));
			}
		}

		if (fimbriate)
		{
			_overlays.add(new OverlayCross(fimbriation, fimbriationWidth, intersection, center, _gridSize.Width, _gridSize.Height));
		}

		_overlays.add(new OverlayCross(mainColor, crossWidth, intersection, center, _gridSize.Width, _gridSize.Height));

		return new DivisionGrid(background, background, 1, 1);
	}

	private DivisionGrid GetBlank()
	{
		Color color = _colorScheme.Color1();

		switch (new int[]
				{
					1,
					2,
					3,
					4
				}[Randomizer.RandomWeighted(new int[] { _canHaveCanton ? 10 : 0, 26, 2, 1 })])
		{
			case 1:
				// Canton
				if (Randomizer.ProbabilityOfTrue(0.6))
				{
					AddFlag(new RandomFlagFactory().GenerateFlag(_colorScheme.Swapped()));
					AddEmblem(1.0, 3 * _gridSize.Width / 4.0, _gridSize.Height / 2.0, _colorScheme.Metal(), true, _colorScheme.Color2(), false);
				}
				else
				{
					Color cantonColor = Randomizer.ProbabilityOfTrue(0.5) ? _colorScheme.Color2() : _colorScheme.Metal();
					_overlays.add(new OverlayBox(cantonColor, 0, 0, _gridSize.Width / 2.0, _gridSize.Height / 2.0, _gridSize.Width, _gridSize.Height));


					if (Randomizer.ProbabilityOfTrue(0.5))
					{
						AddRepeater(_gridSize.Width / 4.0, _gridSize.Height / 4.0, _gridSize.Width / 3.0, _gridSize.Height / 3.0, cantonColor == _colorScheme.Metal() ? _colorScheme.Color1() : _colorScheme.Metal(), false);
					}
					else
					{
						AddEmblem(1.0, _gridSize.Width / 4.0, _gridSize.Height / 4.0, cantonColor == _colorScheme.Metal() ? _colorScheme.Color1() : _colorScheme.Metal(), true, cantonColor == _colorScheme.Metal() ? _colorScheme.Metal() : _colorScheme.Color1(), false);
					}
				}
				break;
			case 2:
				// Center emblem
				color = GetCenterEmblemForBlank();
				break;
			case 3:
				// Triangle
				int width = HoistElementWidth(true);
				if (Randomizer.ProbabilityOfTrue(0.5))
				{
					AddTriangle(1.0, 0.0, width <= _gridSize.Width / 2.0 ? width * 2 : _gridSize.Width, _colorScheme.Metal(), _colorScheme.Metal());
				}
				else
				{
					AddTriangle(1.0, 0.0, width + 2, _colorScheme.Metal(), _colorScheme.Metal());
				}
				AddTriangle(1.0, 1.0, width, _colorScheme.Color2(), _colorScheme.Metal());
				break;
			case 4:
				// Rays
				_overlays.add(new OverlayRays(_colorScheme.Metal(), _gridSize.Width / 2.0, _gridSize.Height / 2.0,
						Randomizer.Clamp(Randomizer.NextNormalized(_gridSize.Width * 3 / 4.0, _gridSize.Width / 10.0), 4, 20, false), 0, _gridSize.Width, _gridSize.Height));
				AddCircleEmblem(1.0, _gridSize.Width / 2.0, _gridSize.Height / 2.0, _colorScheme.Metal(), _colorScheme.Color1(), _colorScheme.Metal());
				break;
		}

		return new DivisionGrid(color, _colorScheme.Color2(), 1, 1);
	}

	private Color GetCenterEmblemForBlank()
	{
		switch (new int[] { 1, 2, 3, 4, 5 }[Randomizer.RandomWeighted(new int[] { 20, 3, 1, _canHaveCanton ? 3 : 0, 2 })])
		{
			case 1:
				// Plain
				boolean isInverted = Randomizer.ProbabilityOfTrue(0.1);
				boolean useColor2 = Randomizer.ProbabilityOfTrue(.11);
				AddEmblem(1.0, _gridSize.Width / 2.0, _gridSize.Height / 2.0,
					useColor2 ? _colorScheme.Color2() : (isInverted ? _colorScheme.Color1() : _colorScheme.Metal()),
					!isInverted, useColor2 ? _colorScheme.Metal() : _colorScheme.Color2(), false);
				return isInverted ? _colorScheme.Metal() : _colorScheme.Color1();
			case 2:
				// Circled
				AddCircleEmblem(1.0, _gridSize.Width / 2.0, _gridSize.Height / 2.0, _colorScheme.Metal(), _colorScheme.Color1(), _colorScheme.Metal());
				return _colorScheme.Color1();
			case 3:
				// Repeater
				AddRepeater(_gridSize.Width / 2.0, _gridSize.Height / 2.0, _gridSize.Height, 0, _colorScheme.Metal(), true);
				return _colorScheme.Color1();
			case 4:
				// Border
				_overlays.add(new OverlayBorder(_colorScheme.Color2(), _gridSize.Width / 8.0, _gridSize.Width, _gridSize.Height));
				AddEmblem(1.0, _gridSize.Width / 2.0, _gridSize.Height / 2.0, _colorScheme.Metal(), true, _colorScheme.Color2(), false);
				return _colorScheme.Color1();
			default:
				// Stripes
				_overlays.add(new OverlayLineHorizontal(_colorScheme.Color1(), _gridSize.Height / 8.0, _gridSize.Height * (1 / 6.0), _gridSize.Width, _gridSize.Height));
				_overlays.add(new OverlayLineHorizontal(_colorScheme.Color1(), _gridSize.Height / 8.0, _gridSize.Height * (5 / 6.0), _gridSize.Width, _gridSize.Height));
				AddEmblem(1.0, _gridSize.Width / 2.0, _gridSize.Height / 2.0, _colorScheme.Color1(), false, _colorScheme.Color2(), false);
				return _colorScheme.Metal();
		}
	}

	private DivisionGrid GetVertical()
	{
		switch (new int[]
				{
					1,
					2,
					3
				}[Randomizer.RandomWeighted(new int[] { 2, 1, 3 })])
		{
			case 1:
				// Color 1 / Metal
				AddEmblem(1.0, Randomizer.ProbabilityOfTrue(0.5) ? _gridSize.Width / 2.0 : 3 * _gridSize.Width / 4.0, _gridSize.Height / 2.0, _colorScheme.Color2(), false, _colorScheme.Metal(), false);
				return new DivisionGrid(_colorScheme.Color1(), _colorScheme.Metal(), 2, 1);
			case 2:
				// Color 1 / Color 2
				AddEmblem(1.0, _gridSize.Width / 2.0, _gridSize.Height / 2.0, _colorScheme.Metal(), false, _colorScheme.Metal(), false);
				return new DivisionGrid(_colorScheme.Color1(), _colorScheme.Color2(), 2, 1);
			default:
				// Metal / Color 1
				AddEmblem(0.5, _gridSize.Width / 4.0, _gridSize.Height / 4.0, _colorScheme.Color2(), false, _colorScheme.Metal(), false);
				return new DivisionGrid(_colorScheme.Metal(), _colorScheme.Color1(), 2, 1);
		}
	}

	private DivisionGrid GetHorizontal()
	{
		switch (new int[]{ 1, 2, 3 }[Randomizer.RandomWeighted(new int[] { 4, 11, 6 })])
		{
			case 1:
				// Color 1 / Metal
				AddEmblem(0.25, _gridSize.Width / 4.0, _gridSize.Height / 4.0, _colorScheme.Metal(), false, _colorScheme.Metal(), false);
				return new DivisionGrid(_colorScheme.Color1(), _colorScheme.Metal(), 1, 2);
			case 2:
				// Color 1 / Color 2

				if (Randomizer.ProbabilityOfTrue(0.181818))
				{
					double width = _gridSize.Width / 3.0;
					AddTriangle(1.0, 0.5, (int)width, Color.BLACK, _colorScheme.Metal());
					_overlays.add(new OverlayPall(_colorScheme.Metal(), width, _gridSize.Width / 8.0, _gridSize.Width, _gridSize.Height));
					_overlays.add(new OverlayPall(_colorScheme.Color3(), width, _gridSize.Width / 5.0, _gridSize.Width, _gridSize.Height));
				}
				else
				{
					if (Randomizer.ProbabilityOfTrue(0.1111))
					{
						AddHoistForHorizontal(false, true);
					}
					else if (Randomizer.ProbabilityOfTrue(0.375))
					{
						boolean isTriangleMetal = Randomizer.ProbabilityOfTrue(0.6667);
						AddTriangle(1.0, 1.0, HoistElementWidth(true), isTriangleMetal ? _colorScheme.Metal() : _colorScheme.Color3(),
							isTriangleMetal ? _colorScheme.Color1() : _colorScheme.Metal());
					}
					else
					{
						// Plain with emblem
						double offset = Randomizer.ProbabilityOfTrue(0.25) ? 4.0 : 2.0;
						AddEmblem(1.0, _gridSize.Width / offset, _gridSize.Height / offset, _colorScheme.Metal(), false, _colorScheme.Metal(), false);
					}
				}

				return new DivisionGrid(_colorScheme.Color1(), _colorScheme.Color2(), 1, 2);
			default:
				// Metal / Color 1
				if (Randomizer.ProbabilityOfTrue(0.333))
				{
					AddHoistForHorizontal(Randomizer.ProbabilityOfTrue(0.5), false);
				}
				return new DivisionGrid(_colorScheme.Metal(), _colorScheme.Color1(), 1, 2);
		}
	}

	private void AddHoistForHorizontal(boolean isHalfSize, boolean isMetal)
	{
		int width = HoistElementWidth(false);
		_overlays.add(new OverlayBox(isMetal ? _colorScheme.Metal() : _colorScheme.Color2(), 0, 0, width,
			isHalfSize ? _gridSize.Height / 2.0 : _gridSize.Height, _gridSize.Width, _gridSize.Height));
		AddEmblem(1.0, width / 2.0, isHalfSize ? _gridSize.Height / 4.0 : _gridSize.Height / 2.0, isMetal ? _colorScheme.Color1() : _colorScheme.Metal(), false, _colorScheme.Metal(), false);
	}

	private Division GetDiagonal()
	{
		boolean isForward = Randomizer.ProbabilityOfTrue(0.7);

		Color[][] schemes = new Color[][]
					  {
						  new Color[] { _colorScheme.Metal(), _colorScheme.Color1(), Color.TRANSPARENT, Color.TRANSPARENT, _colorScheme.Color2() },
						  new Color[] { _colorScheme.Color1(), _colorScheme.Color2(), Color.TRANSPARENT, Color.TRANSPARENT, _colorScheme.Metal() },
						  new Color[] { _colorScheme.Color1(), _colorScheme.Color1(), _colorScheme.Color2(), _colorScheme.Metal(), _colorScheme.Metal() },
						  new Color[] { _colorScheme.Color1(), _colorScheme.Color2(), _colorScheme.Color3(), _colorScheme.Metal(), _colorScheme.Metal() },
						  new Color[] { _colorScheme.Metal(), _colorScheme.Metal(), _colorScheme.Color1(), Color.TRANSPARENT, _colorScheme.Color2() },
						  new Color[] { _colorScheme.Color1(), _colorScheme.Color2(), _colorScheme.Metal(), Color.TRANSPARENT , _colorScheme.Metal() }
					  };
		Color[] scheme = schemes[Randomizer.RandomWeighted(new int[] { 1, 1, 2, 3, 1, 2 })];

		Color topColor = scheme[0];
		Color bottomColor = scheme[1];
		Color stripeColor = scheme[2];
		Color fimbriationColor = scheme[3];
		Color emblemColor = scheme[4];

		boolean hasStripe = stripeColor != Color.TRANSPARENT;
		boolean hasFimbriation = fimbriationColor != Color.TRANSPARENT;

		if (hasStripe)
		{
			if (hasFimbriation)
			{
				if (isForward)
				{
					_overlays.add(new OverlayFimbriationForward(fimbriationColor, _gridSize.Width / 3.0, _gridSize.Width, _gridSize.Height));
				}
				else
				{
					_overlays.add(new OverlayFimbriationBackward(fimbriationColor, _gridSize.Width / 3.0, _gridSize.Width, _gridSize.Height));
				}
			}
			if (isForward)
			{
				_overlays.add(new OverlayFimbriationForward(stripeColor, _gridSize.Width / 4.0, _gridSize.Width, _gridSize.Height));
			}
			else
			{
				_overlays.add(new OverlayFimbriationBackward(stripeColor, _gridSize.Width / 4.0, _gridSize.Width, _gridSize.Height));
			}
		}

		double emblemLeft = hasStripe
			? (isForward ? 1.0 : 5.0) * _gridSize.Width / 6.0
			: _gridSize.Width / 2.0;
		double emblemTop = hasStripe
			? _gridSize.Height / 4.0
			: _gridSize.Height / 2.0;
		AddEmblem(1.0, emblemLeft, emblemTop, emblemColor, false, emblemColor, false);

		if (isForward)
		{
			return new DivisionBendsForward(topColor, bottomColor);
		}

		return new DivisionBendsBackward(topColor, bottomColor);
	}

	private DivisionGrid GetStripe()
	{
		return Randomizer.ProbabilityOfTrue(0.58333) 
			? GetSingleStripe()
			: GetMultiStripe();
	}

	private DivisionGrid GetSingleStripe()
	{
		if (Randomizer.ProbabilityOfTrue(0.142))
		{
			_overlays.add(new OverlayLineHorizontal(_colorScheme.Metal(), _gridSize.Height / 6.0, _gridSize.Height / 2.0, _gridSize.Width, _gridSize.Height));
			AddEmblem(0.75, _gridSize.Width / 3.0, _gridSize.Height / 1.333, _colorScheme.Metal(), false, _colorScheme.Metal(), false);
		}
		else
		{
			boolean isThick = Randomizer.ProbabilityOfTrue(0.66667);
			_overlays.add(new OverlayLineHorizontal(_colorScheme.Metal(), _gridSize.Height / (isThick ? 1.5 : 3.0), _gridSize.Height / 2.0, _gridSize.Width, _gridSize.Height));
			_overlays.add(new OverlayLineHorizontal(_colorScheme.Color2(), _gridSize.Height / (isThick ? 3.0 : 4.0), _gridSize.Height / 2.0, _gridSize.Width, _gridSize.Height));
			AddEmblem(isThick ? 0.5 : 0.0, _gridSize.Width / 2.0, _gridSize.Height / 2.0, _colorScheme.Metal(), false, _colorScheme.Metal(), false);
		}

		return new DivisionGrid(_colorScheme.Color1(), _colorScheme.Color2(), 1, 1);
	}

	private DivisionGrid GetMultiStripe()
	{
		if (Randomizer.ProbabilityOfTrue(0.2))
		{
			// Symmetric
			boolean swap = Randomizer.ProbabilityOfTrue(0.5);
			_overlays.add(new OverlayLineHorizontal(swap ? _colorScheme.Color2() : _colorScheme.Metal(), _gridSize.Height / 1.4, _gridSize.Height / 2.0, _gridSize.Width, _gridSize.Height));
			_overlays.add(new OverlayLineHorizontal(swap ? _colorScheme.Metal() : _colorScheme.Color2(), _gridSize.Height / 2.3333, _gridSize.Height / 2.0, _gridSize.Width, _gridSize.Height));
			_overlays.add(new OverlayLineHorizontal(_colorScheme.Color3(), _gridSize.Height / 7.0, _gridSize.Height / 2.0, _gridSize.Width, _gridSize.Height));
		}
		else if (Randomizer.ProbabilityOfTrue(0.75))
		{
			// Asymmetric
			boolean swap = Randomizer.ProbabilityOfTrue(0.3333);
			_overlays.add(new OverlayBox(swap ? _colorScheme.Color2() : _colorScheme.Metal(), 0, _gridSize.Height / 4.0, _gridSize.Width, _gridSize.Height * 3 / 4.0, _gridSize.Width, _gridSize.Height));
			_overlays.add(new OverlayBox(swap ? _colorScheme.Metal() : _colorScheme.Color2(), 0, _gridSize.Height / 2.0, _gridSize.Width, _gridSize.Height * 2.0, _gridSize.Width, _gridSize.Height));
			_overlays.add(new OverlayBox(_colorScheme.Color3(), 0, _gridSize.Height * 3/4.0, _gridSize.Width, _gridSize.Height / 4.0, _gridSize.Width, _gridSize.Height));
		}
		else
		{
			// Uganda
			_overlays.add(new OverlayLineHorizontal(_colorScheme.Metal(), _gridSize.Height / 6.0, _gridSize.Height * 3 / 12.0, _gridSize.Width, _gridSize.Height));
			_overlays.add(new OverlayLineHorizontal(_colorScheme.Color2(), _gridSize.Height / 6.0, _gridSize.Height * 5 / 12.0, _gridSize.Width, _gridSize.Height));
			_overlays.add(new OverlayLineHorizontal(_colorScheme.Metal(), _gridSize.Height / 6.0, _gridSize.Height * 9 / 12.0, _gridSize.Width, _gridSize.Height));
			_overlays.add(new OverlayLineHorizontal(_colorScheme.Color2(), _gridSize.Height / 6.0, _gridSize.Height * 11 / 12.0, _gridSize.Width, _gridSize.Height));
		}

		if (Randomizer.ProbabilityOfTrue(0.4))
		{
			AddTriangle(1.0, 1.0, HoistElementWidth(true), _colorScheme.Metal(), _colorScheme.Color1());
		}
		else
		{
			AddCircleEmblem(0.5, _gridSize.Width / 2.0, _gridSize.Height / 2.0, _colorScheme.Metal(), _colorScheme.Color1(), _colorScheme.Metal());
		}

		return new DivisionGrid(_colorScheme.Color1(), _colorScheme.Color2(), 1, 1);
	}

	private Division GetX()
	{
		switch (new int[]
				{
					1,
					2,
					3
				}[Randomizer.RandomWeighted(new int[] { 2, 1, 1 })])
		{
			case 1:
				// Two-color, fimbriation
				_overlays.add(new OverlaySaltire(_colorScheme.Metal(), _gridSize.Width / 6.0, _gridSize.Width, _gridSize.Height));
				AddCircleEmblem(0.5, _gridSize.Width / 2.0, _gridSize.Height / 2.0, _colorScheme.Metal(), _colorScheme.Color1(), _colorScheme.Color1());
				return new DivisionX(_colorScheme.Color1(), _colorScheme.Color2());
			case 2:
				// Two-color, no fimbriation
				AddCircleEmblem(1.0, _gridSize.Width / 2.0, _gridSize.Height / 2.0, _colorScheme.Color2(), _colorScheme.Metal(), _colorScheme.Metal());
				if (Randomizer.ProbabilityOfTrue(0.5))
				{
					_overlays.add(new OverlayBorder(_colorScheme.Color2(), _gridSize.Width / 8.0, _gridSize.Width, _gridSize.Height));
				}
				return new DivisionX(_colorScheme.Color1(), _colorScheme.Metal());
			default:
				// One-color
				_overlays.add(new OverlaySaltire(_colorScheme.Metal(), _gridSize.Width / 6.0, _gridSize.Width, _gridSize.Height));
				return new DivisionGrid(_colorScheme.Color1(), _colorScheme.Color1(), 1, 1);
		}
	}

	private DivisionGrid GetRay()
	{
		_overlays.add(new OverlayRays(_colorScheme.Color1(), _gridSize.Width / 2.0, _gridSize.Height / 2.0, 20, 0, _gridSize.Width, _gridSize.Height));
		AddCircleEmblem(1.0, _gridSize.Width / 2.0, _gridSize.Height / 2.0, _colorScheme.Metal(), _colorScheme.Color1(), _colorScheme.Metal());
		return new DivisionGrid(_colorScheme.Metal(), _colorScheme.Metal(), 1, 1);
	}

	private int HoistElementWidth(boolean isTriangle)
	{
		return (int)(_gridSize.Width * Randomizer.NextNormalized(isTriangle ? 0.45 : 0.35, 0.05));
	}

	private void AddTriangle(double probability, double probabilityOfEmblem, int width, Color color, Color emblemColor)
	{
		if (!Randomizer.ProbabilityOfTrue(probability)) return;
		_overlays.add(new OverlayTriangle(color, 0, 0, width, _gridSize.Height / 2.0, 0, _gridSize.Height, _gridSize.Width, _gridSize.Height));

		if (Randomizer.ProbabilityOfTrue(probabilityOfEmblem))
		{
			AddEmblem(1.0, width / 3.0, _gridSize.Height / 2.0, emblemColor, false, Color.WHITE, false);
		}
	}

	private void AddRepeater(double x, double y, double width, double height, Color color, boolean forceRadial)
	{
//		boolean big = forceRadial;
//		if (!forceRadial && Randomizer.ProbabilityOfTrue(0.5))
//		{
//			_overlays.add(new OverlayRepeaterLateral(x, y, width, height,
//				Randomizer.Clamp(Randomizer.NextNormalized(5, 2), 2, 8, false),
//				Randomizer.Clamp(Randomizer.NextNormalized(4, 2), 2, 8), _gridSize.Width, _gridSize.Height, false));
//		}
//		else
//		{
//			big = true;
//			_overlays.add(new OverlayRepeaterRadial(x, y, width / 3.0,
//				Randomizer.Clamp(Randomizer.NextNormalized(12, 4), 4, 25, false),
//				Randomizer.ProbabilityOfTrue(0.5) ? 0 : _gridSize.Width, _gridSize.Width, _gridSize.Height));
//		}
//
//		AddEmblem(1, 0, 0, color, false, color, big);
	}

	private void AddCircleEmblem(double probability, double x, double y, Color circleColor, Color emblemColor, Color colorIfStroke)
	{
		if (!Randomizer.ProbabilityOfTrue(probability)) return;

		_overlays.add(new OverlayEllipse(circleColor, x, y, _gridSize.Width / 4.0, 0.0, _gridSize.Width, _gridSize.Height));

		AddEmblem(1.0, x, y, emblemColor, true, colorIfStroke, false);
	}

	private void AddEmblem(double probability, double x, double y, Color color, boolean canStroke, Color colorIfStroked, boolean isBig)
	{
		if (probability < 1 && !Randomizer.ProbabilityOfTrue(probability)) return;

		OverlayPath emblem = (OverlayPath)_emblems[Randomizer.Next(_emblems.length)];
		emblem.SetMaximum(_gridSize.Width, _gridSize.Height);
		emblem.SetAttribute("X", x);
		emblem.SetAttribute("Y", y);
		emblem.SetAttribute("Size", _gridSize.Width / (isBig ? 3.0 : 6.0));
		emblem.SetAttribute("Rotation", 0.0);

		if (canStroke && Randomizer.ProbabilityOfTrue(0.1))
		{
			emblem.SetAttribute("Color", colorIfStroked);
			emblem.SetAttribute("Stroke", 2.0);
			emblem.SetAttribute("StrokeColor", color);
			emblem.SetAttribute("StrokeCurved", _gridSize.Width);
		}
		else
		{
			emblem.SetAttribute("Color", color);
			emblem.SetAttribute("Stroke", 0.0);
		}

		_overlays.add(emblem);
	}

	private void AddFlag(Flag flag)
	{
		_overlays.add(new OverlayFlag(flag, new File(""), 0, 0, _gridSize.Width / 2.0, _gridSize.Height / 2.0, _gridSize.Width, _gridSize.Height));
	}
}
