package gdg.nat.ksc.service;

import gdg.nat.ksc.R;

import java.util.Random;

public class GetImage {
	public static int getImage() {
		int resource = 0;
		Random random = new Random();
		switch (random.nextInt() % 13) {
			case 0:
				resource = R.drawable.dummy1;
				break;
			case 1:
				resource = R.drawable.dummy2;
				break;
			case 2:
				resource = R.drawable.dummy3;
				break;
			case 3:
				resource = R.drawable.dummy4;
				break;
			case 4:
				resource = R.drawable.dummy5;
				break;
			case 5:
				resource = R.drawable.dummy6;
				break;
			case 6:
				resource = R.drawable.dummy7;
				break;
			case 7:
				resource = R.drawable.dummy8;
				break;
			case 8:
				resource = R.drawable.dummy4;
				break;
			case 9:
				resource = R.drawable.dummy1;
				break;
			case 10:
				resource = R.drawable.dummy2;
				break;
			case 11:
				resource = R.drawable.dummy3;
				break;
			default:
				resource = R.drawable.dummy;
				break;
		}
		return resource;
	}
}