package adminmenu.dao;

import common.dto.InventoryDTO;
import common.dto.SalesDTO;

import java.util.List;

public interface AdminDAO {

    void deleteRegisterInfo(String id);

    List<InventoryDTO> getAllInventory();

    void updatePrice(String name, double price);

    List<SalesDTO> getAllSales();

    void updateDailyInventory(String name, int inventory);

    void refreshInventory();
}
