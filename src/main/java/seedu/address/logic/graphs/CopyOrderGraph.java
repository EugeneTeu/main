package seedu.address.logic.graphs;

import java.util.List;

import seedu.address.logic.GraphWithPreamble;
import seedu.address.model.Model;
import seedu.address.model.order.Order;

/**
 * Represents a {@code Graph} used to support autocomplete for {@code CopyOrderCommand}.
 */
public class CopyOrderGraph extends GraphWithPreamble {

    public CopyOrderGraph(Model model) {
        super(model);
    }

    @Override
    protected void build(Model model) {
        List<Order> orderList = model.getFilteredOrderList();
        setDataList(orderList);
    }

}