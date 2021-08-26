package domaci.databinding;

import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Menu {
	private Double amountMax;
	private Double amountMin;
	private String category;
	private String currency;
	private List<Instant> dateSeen;
	private String description; //32
	private String name;
	private List<String> urls; //34
}
