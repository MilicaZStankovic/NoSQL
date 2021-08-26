package domaci.main;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.io.FileReader;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.util.HashMap;
import java.util.HashSet;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import domaci.databinding.Description;
import domaci.databinding.Hour;
import domaci.databinding.Menu;
import domaci.databinding.PriceRange;
import domaci.databinding.Restaurant;

public class CreateJson {
	public static Set<Restaurant> createJson() {

		try {
			FileReader filereader = new FileReader(
					"C:\\Users\\Milica\\eclipse-workspace\\ParseAirBnb\\src\\main\\java\\domaci\\main\\"
							+ "restaurants.csv");
			CSVReader csvReader = new CSVReaderBuilder(filereader).withSkipLines(1).build();

			String[] nextRecord;
			

			Set<Restaurant> restaurants = new HashSet<>();

			while ((nextRecord = csvReader.readNext()) != null) {
				Restaurant restaurant = new Restaurant();

				restaurant.setId(nextRecord[0]);

				restaurant.setDateAdded(checkDate(nextRecord[1]));
				restaurant.setDateUpdated(checkDate(nextRecord[2]));
				restaurant.setAddress(nextRecord[3]);
				restaurant.setCategories(splitIntoList(nextRecord[4]));
				restaurant.setPrimaryCategories(splitIntoList(nextRecord[5]));
				restaurant.setCity(nextRecord[6]);
				restaurant.setClaimed(nextRecord[7]);
				restaurant.setCountry(nextRecord[8]);
				restaurant.setCousines(splitIntoList(nextRecord[9]));
				restaurant.setDescription(createDescriptoon(nextRecord[10], nextRecord[11], nextRecord[12]));
				restaurant.setFacebookPageUrl(nextRecord[13]);
				restaurant.setFeatures(createMap(nextRecord[14], nextRecord[15]));
				restaurant.setHour(createHour(nextRecord[16], nextRecord[17], nextRecord[18]));
				restaurant.setImageUrls(splitIntoList(nextRecord[19]));
				restaurant.setIsClosed(isTrue(nextRecord[20]));
				restaurant.setKeys(splitIntoList(nextRecord[21]));
				restaurant.setLanguagesSpoken(splitIntoList(nextRecord[22]));
				restaurant.setLatitude(checkDouble(nextRecord[23]));
				restaurant.setLongitude(checkDouble(nextRecord[24]));
				restaurant.setMenuPageUrl(nextRecord[25]);
				restaurant.setMenu(createMenu(nextRecord[26], nextRecord[27], nextRecord[28], nextRecord[29],
						nextRecord[30], nextRecord[31], nextRecord[32], nextRecord[33]));
				restaurant.setName(nextRecord[34]);
				restaurant.setPaymentTypes(splitIntoList(nextRecord[35]));
				restaurant.setPhone(nextRecord[36]);
				restaurant.setPostalCode(nextRecord[37]);
				restaurant.setPriceRange(createPriceRange(nextRecord[38], nextRecord[39], nextRecord[40]));
				restaurant.setProvince(nextRecord[41]);
				restaurant.setSic(checkInteger(nextRecord[42]));
				restaurant.setSourceUrls(splitIntoList(nextRecord[43]));
				restaurant.setTwitter(nextRecord[44]);
				restaurant.setWebsites(splitIntoList(nextRecord[45]));
				restaurant.setYearOpened(checkInteger(nextRecord[46]));

				restaurants.add(restaurant);
				System.out.println(restaurant);

				
			}

			csvReader.close();

			return restaurants;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	private static Instant checkDate(String date) {
		return date != null && !date.equals("") ? Instant.parse(date) : null;
	}

	private static List<String> splitIntoList(String list) {
		String[] array = list.split(",");
		return Arrays.asList(array);
	}

	private static Description createDescriptoon(String date, String list, String value) {
		Description description = new Description();
		description.setDate(checkDate(date));
		description.setUrls(splitIntoList(list));
		description.setValue(value);

		return description;
	}

	private static Map<String, String> createMap(String key, String value) {
		Map<String, String> map;
		if (!isNullOrEmpty(key) && !isNullOrEmpty(value)) {
			map = new HashMap<String, String>();
			map.put(key, value);
		} else {
			map = Collections.emptyMap();
		}
		return map;
	}

	private static Hour createHour(String day, String dept, String hour) {
		Hour h = new Hour();
		h.setDay(day);
		h.setDept(dept);
		h.setHour(hour);

		return h;
	}

	private static boolean isNullOrEmpty(String value) {
		return value == null || value.equals("");
	}

	private static Boolean isTrue(String value) {
		return value.equalsIgnoreCase("yes") ? Boolean.TRUE : Boolean.FALSE;
	}

	private static Double checkDouble(String value) {
		return value != null && !value.equals("") ? Double.parseDouble(value) : null;
	}

	private static Menu createMenu(String amauntMax, String amountMin, String category, String currency,
			String dateSeenlist, String description, String name, String urls) {
		Menu menu = new Menu();
		menu.setAmountMax(checkDouble(amauntMax));
		menu.setAmountMin(checkDouble(amountMin));
		menu.setCategory(category);
		menu.setCurrency(currency);
		menu.setDateSeen(createInstantList(dateSeenlist));
		menu.setDescription(description);
		menu.setName(name);
		menu.setUrls(splitIntoList(urls));

		return menu;
	}

	private static List<Instant> createInstantList(String list) {
		List<String> listString = splitIntoList(list);
		List<Instant> instantList = new ArrayList<>();
		for (String date : listString) {
			Instant instant = checkDate(date);
			instantList.add(instant);
		}

		return instantList;
	}

	private static PriceRange createPriceRange(String currency, String min, String max) {
		PriceRange priceRange = new PriceRange();
		priceRange.setCurrency(currency);
		priceRange.setMin(checkInteger(min));
		priceRange.setMax(checkInteger(max));

		return priceRange;
	}

	private static Integer checkInteger(String value) {
		return value != null && !value.equals("") ? Integer.parseInt(value) : null;
	}

	public static boolean writeToDb(Set<Restaurant> restaturants) {
		try {
			ConnectionString connectionString = new ConnectionString("mongodb://nastava.is.pmf.uns.ac.rs");
			CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
			CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
					pojoCodecRegistry);
			MongoClientSettings clientSettings = MongoClientSettings.builder().applyConnectionString(connectionString)
					.codecRegistry(codecRegistry).build();
			MongoClient mongoClient = MongoClients.create(clientSettings);
			MongoDatabase db = mongoClient.getDatabase("restoranMilica");
			MongoCollection<Restaurant> col = db.getCollection("Milicarestoran", Restaurant.class);
			int i = 1;
			for (Restaurant l : restaturants) {
				System.out.println("Inserting listing " + i);
				col.insertOne(l);
				System.out.println("Imena" + mongoClient.listDatabaseNames());
				i++;
			}

			mongoClient.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) {
		Set<Restaurant> restaurants = createJson();

		writeToDb(restaurants);

	}
}
