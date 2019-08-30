package ru.romanow.heisenbug.warehouse.web;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.romanow.heisenbug.warehouse.model.ItemsFullInfoResponse;
import ru.romanow.heisenbug.warehouse.model.PageableItemsResponse;
import ru.romanow.heisenbug.warehouse.service.WarehouseService;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;
import static java.util.stream.IntStream.range;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

public class BaseItemsControllerTest
        extends BaseWebTest {
    private static final int LIST_SIZE = 10;
    private static final List<ItemsFullInfoResponse> ITEMS =
            range(0, LIST_SIZE)
                    .mapToObj(BaseItemsControllerTest::buildItem)
                    .collect(toList());

    @Autowired
    private WarehouseController warehouseController;

    @MockBean
    private WarehouseService warehouseService;

    @BeforeEach
    public void init() {
        when(warehouseService.items(anyInt(), anyInt())).thenAnswer((i) -> {
            int page = i.getArgument(0);
            int size = i.getArgument(1);
            return buildItemsResponse(page, size);
        });
    }

    @Override
    protected Object controller() {
        return warehouseController;
    }

    private PageableItemsResponse buildItemsResponse(int page, int size) {
        final List<ItemsFullInfoResponse> items = ITEMS.subList(size * (page - 1), size * page);
        return new PageableItemsResponse()
                .setPage(page)
                .setPageSize(size)
                .setTotalSize(ITEMS.size())
                .setItems(items);
    }

    private static ItemsFullInfoResponse buildItem(int i) {
        final ItemsFullInfoResponse response = new ItemsFullInfoResponse(i + 1);
        response.setItemUid(UUID.randomUUID());
        response.setName("Lego Technic " + (42090 + i));
        return response;
    }
}
