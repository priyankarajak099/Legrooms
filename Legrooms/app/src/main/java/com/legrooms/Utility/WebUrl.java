package com.legrooms.Utility;

public class WebUrl {


	public static final String BASE = "https://app.legrooms.com/api/";
	public static final String LOGIN =BASE+ "authenticate";
	public static final String REGISTER =BASE+ "register";
	public static final String SEARCH_LISTING =BASE+ "listing";
	public static final String GET_USER_INFO =BASE+ "user";
	public static final String ADD_BOOKING =BASE+ "booking";



    public static void ShowLog(String s) {
		System.out.println(s);
    }

	public static String convertIntoDateFormat(String date){
		if (date.length()>0) {


			String [] arrDate=date.split("[-]");
			String day=arrDate[0];
			int mm=Integer.parseInt(arrDate[1]);
			String yyyy=arrDate[2];
			String month="";
			switch (mm) {

				case 1:
					month="Jan";
					break;
				case 2:
					month="Feb";
					break;
				case 3:
					month="Mar";
					break;
				case 4:
					month="Apr";
					break;
				case 5:
					month="May";
					break;
				case 6:
					month="Jun";
					break;
				case 7:
					month="Jul";
					break;
				case 8:
					month="Aug";
					break;
				case 9:
					month="Sept";
					break;
				case 10:
					month="Oct";
					break;
				case 11:
					month="Nov";
					break;
				case 12:
					month="Dec";
					break;

				default:
					break;
			}


			return day+" "+month+","+yyyy;

		}
		return "";

	}

}
