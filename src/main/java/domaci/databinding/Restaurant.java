package domaci.databinding;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
//46
@Getter
@Setter 
@NoArgsConstructor
@ToString
public class Restaurant {
	
	private String id;
	private Instant dateAdded;
	private Instant dateUpdated;
	private String address;
	private List<String> categories;
	private List<String> primaryCategories;
	private String city;
	private String claimed;
	private String country;
	private List<String> cousines;
	private Description description;
	private String  facebookPageUrl; //14 n
	private Map<String, String> features;	
	private Hour hour;//17 18 19
	private List<String> imageUrls; //20
	private Boolean isClosed;
	private List<String> keys;
	private List<String> languagesSpoken; //23
	private Double latitude;
	private Double longitude; //25
	private String menuPageUrl;//26
	private Menu menu;
	private String name; //35
	private List<String> paymentTypes;
	private String phone;
	private String postalCode;
	private PriceRange priceRange;
	private String province;//42
	private Integer sic;
	private List<String> sourceUrls;
	private String twitter;
	private List<String> websites;
	private Integer yearOpened;
	
	@Override
	public boolean equals(Object obj) {
		return this.id.equals(((Restaurant) obj).getId());
	}
	
	@Override
	public int hashCode() {
		return 0;
	}
}
