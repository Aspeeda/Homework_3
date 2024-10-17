package dto.order;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@AllArgsConstructor
public class OrderDTO {
    public int id;
    public int petId;
    public int quantity;
    public Date shipDate;
    public String status;
    public boolean complete;
}
