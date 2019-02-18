package challenges.hackerrank;

import java.io.*;
import java.util.*;
import java.util.stream.*;

public class PersonalizedCoupons {
    

	private final static String CATEGORY = "category";
	private final static String COUPON_AMOUNT = "couponAmount";
	private final static String ITEM_PRICE = "itemPrice";
	private final static String CODE = "code";
	
    static List<Map<String, Object>> personalizeCoupons(List<Map<String, Object>> coupons, 
                                                        List<String> preferredCategories) {
    	
    	// create a new list for our results, since we do not want to make changes to the master coupons list that was passed in
    	List<Map<String, Object>> topTenCoupons = coupons.stream()
    			// filter out all coupons that are not in the list of preferred categories
    			.filter(couponMap -> preferredCategories.contains(couponMap.get(CATEGORY)))
		    	// sort by percentage off (coupon amount / item price)
		    	.sorted(Comparator.comparing(couponMap -> (float)couponMap.get(COUPON_AMOUNT)/(float)couponMap.get(ITEM_PRICE), Comparator.reverseOrder()))
		    	// only keep the first 10
		    	.limit(10).collect(Collectors.toList());
    	
    	// remove the "code" field
    	topTenCoupons.forEach(couponMap -> couponMap.remove(CODE));

    	// return the result
    	return topTenCoupons;
    }

    public static void main(String[] args) throws IOException{
        Scanner input = new Scanner(System.in);
        List<String> preferredCategories = Arrays.asList(input.nextLine().split(","));
        List<Map<String, Object>> coupons = new ArrayList<>();
        int lines = Integer.parseInt(input.nextLine());
        IntStream.range(0, lines).forEach(i -> coupons.add(readCoupon(input)));
        List<Map<String, Object>> personalizedCoupons = personalizeCoupons(coupons, preferredCategories);
        personalizedCoupons.stream().forEach(PersonalizedCoupons::printCoupon);
    }

    public static Map<String, Object> readCoupon(Scanner input) {
        String[] couponItems = input.nextLine().split(",");
        Map<String,Object> coupon = new HashMap<>();
        coupon.put("upc", couponItems[0]);
        coupon.put("code", couponItems[1]);
        coupon.put("category", couponItems[2]);
        coupon.put("itemPrice", Float.parseFloat(couponItems[3]));
        coupon.put("couponAmount", Float.parseFloat(couponItems[4]));
        return coupon;
    }

    public static void printCoupon(Map<String, Object> coupon)
    {
        System.out.print("{");
        System.out.print("\"couponAmount\":" +  coupon.get("couponAmount") + ",");
        System.out.print("\"upc\":\"" +  coupon.get("upc") + "\",");
        if(coupon.containsKey("code")) {
            System.out.print("\"code\":\"" +  coupon.get("code") + "\",");
        }
        System.out.print("\"itemPrice\":" +  coupon.get("itemPrice") + ",");
        System.out.println("\"category\":\"" +  coupon.get("category") + "\"}");
    }
}