
package dto.order;

import lombok.Data;

import java.util.Date;

@Data
public class OrderResponseDTO {

    public int id;
    public int petId;
    public int quantity;
    public Date shipDate;
    public String status;
    public boolean complete;
}
