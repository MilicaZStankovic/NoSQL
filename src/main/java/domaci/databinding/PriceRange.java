package domaci.databinding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor 
@ToString
public class PriceRange {
	private String currency;
	private Integer min;
	private Integer max;
}
